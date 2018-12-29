package com.codelab.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codelab.beans.User;
import com.codelab.beans.general.AbstractBO;
import com.codelab.beans.general.AbstractDTO;
import com.codelab.beans.general.SearchDTO;
import com.codelab.common.CommonUtility;
import com.codelab.common.Constant;
import com.codelab.common.DTOEntityMapper;
import com.codelab.common.ObjUtillity;
import com.codelab.service.GenericService;
import com.sun.media.sound.InvalidDataException;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/codelab/api/secure/general")
public class GeneralController {
	static final Logger LOGGER = LoggerFactory.getLogger(GeneralController.class);

	@Autowired
	private GenericService genericService;

	private final DTOEntityMapper dtoEntityMapper = CommonUtility.getDTOEntityMapperInstance();

	@SuppressWarnings("unchecked")
	@ApiOperation(value = "Save")
	@RequestMapping(value = {
			"/{mapTo}/save" }, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AbstractDTO> save(@RequestBody AbstractDTO abstractDTO, @PathVariable("mapTo") String mapTo,
			BindingResult bindingResult, HttpServletRequest httpRequest, HttpServletResponse httpResponse) {
		LOGGER.debug("Execute method : save()");
		try {
			if (bindingResult.hasErrors()) {
				throw new InvalidDataException(bindingResult.getObjectName());
			}

			final Class<? extends AbstractBO> classMapped = dtoEntityMapper.getBOMapping(abstractDTO.getMapTo());
			ModelMapper modelMapper = new ModelMapper();
			modelMapper.getConfiguration().setAmbiguityIgnored(true);
			//modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
			/*modelMapper.addMappings(new PropertyMap<TaskUserMappingDTO, TaskUserMapping>() {

				@Override
				protected void configure() {
					// TODO Auto-generated method stub
					skip().getUser().setFirstName(null);
					skip().getUser().setLastName(null);
					skip().getUser().setUserName(null);
				}
			});*/
			final AbstractBO inputVO = modelMapper.map(abstractDTO, classMapped);
			// inputVO.setCreatedBy(null);
			// inputVO.setUpdatedBy(null);
			Date now = new Date();
			inputVO.setCreatedOn(now);
			inputVO.setUpdatedOn(now);
			genericService.save(inputVO);
			System.out.println("Input VO is " + inputVO);
			// throw new CodelabRuntimeException("Error in login()", e);
			abstractDTO = modelMapper.map(inputVO, (Class<? extends AbstractDTO>) dtoEntityMapper.getDTOMapping(mapTo));
			return new ResponseEntity<AbstractDTO>(abstractDTO, HttpStatus.OK);
		} catch (InvalidDataException e) {
			LOGGER.error("InvalidDataException in save()", e);
			e.printStackTrace();
			return new ResponseEntity<AbstractDTO>(abstractDTO, HttpStatus.BAD_REQUEST);
		} catch (DataIntegrityViolationException e) {
			LOGGER.error("Duplicate Data", e);
			e.printStackTrace();
			return new ResponseEntity<AbstractDTO>(abstractDTO, HttpStatus.CONFLICT);
		} catch (Exception e) {
			LOGGER.error("Exception in save()", e);
			e.printStackTrace();
			return new ResponseEntity<AbstractDTO>(abstractDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@SuppressWarnings("unchecked")
	@ApiOperation(value = "Update")
	@RequestMapping(value = {
			"/{mapTo}/update" }, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AbstractDTO> update(@RequestBody AbstractDTO abstractDTO, @PathVariable("mapTo") String mapTo,
			BindingResult bindingResult, HttpServletRequest httpRequest, HttpServletResponse httpResponse) {
		LOGGER.debug("Execute method : update()");
		try {
			if (bindingResult.hasErrors()) {
				throw new InvalidDataException(bindingResult.getObjectName());
			}
			if (ObjUtillity.isNull(abstractDTO.getId())) {
				throw new InvalidDataException(Constant.EXC_NULL_ID);
			}

			final Class<? extends AbstractBO> classMapped = dtoEntityMapper.getBOMapping(abstractDTO.getMapTo());
			ModelMapper modelMapper = new ModelMapper();
			modelMapper.getConfiguration().setAmbiguityIgnored(true);
			final AbstractBO inputVO = modelMapper.map(abstractDTO, classMapped);

			// inputVO.setCreatedBy(null);
			// inputVO.setCreatedOn(Calendar.getInstance().getTime());
			// inputVO.setUpdatedBy(null);

			genericService.update(inputVO);

			// throw new CodelabRuntimeException("Error in login()", e);
			abstractDTO = modelMapper.map(genericService.getById(inputVO.getId(), classMapped), (Class<? extends AbstractDTO>) dtoEntityMapper.getDTOMapping(mapTo));
			return new ResponseEntity<>(abstractDTO, HttpStatus.OK);
		} catch (InvalidDataException e) {
			e.printStackTrace();
			LOGGER.error("InvalidDataException in update()", e);
			return new ResponseEntity<>(abstractDTO, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			LOGGER.error("Exception in update()", e);
			e.printStackTrace();
			return new ResponseEntity<>(abstractDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	

	@SuppressWarnings({ "restriction", "unchecked" })
	@ApiOperation(value = "getById")
	@RequestMapping(value = {
			"/{mapTo}/getById" }, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AbstractDTO> getById(@RequestBody AbstractDTO abstractDTO,
			@PathVariable("mapTo") String mapTo,
			@RequestParam(name="wantCrtUserData",required=false) Boolean wantCrtUserData,
			BindingResult bindingResult, HttpServletRequest httpRequest,
			HttpServletResponse httpResponse) {
		LOGGER.debug("Execute method : update()");
		try {
			if (bindingResult.hasErrors()) {
				throw new InvalidDataException(bindingResult.getObjectName());
			}
			if (ObjUtillity.isNull(abstractDTO.getId())) {
				throw new InvalidDataException(Constant.EXC_NULL_ID);
			}
			if (ObjUtillity.isNull(wantCrtUserData)) {
				wantCrtUserData = false;
			}

			ModelMapper modelMapper = new ModelMapper();
			modelMapper.getConfiguration().setAmbiguityIgnored(true);
			AbstractBO inputVO = genericService.getById(abstractDTO.getId(),dtoEntityMapper.getBOMapping(abstractDTO.getMapTo()));
			abstractDTO = modelMapper.map(inputVO, (Class<? extends AbstractDTO>) dtoEntityMapper.getDTOMapping(mapTo));
			if(wantCrtUserData && ObjUtillity.isNotBlank(inputVO.getCreatedById())){
				User u = (User) genericService.getById(inputVO.getCreatedById(), User.class);
				abstractDTO.setCreatedByFirstName(u.getFirstName());
				abstractDTO.setCreatedByLastName(u.getLastName());
				abstractDTO.setCreatedByProfilePic(u.getProfilePic());
				if(ObjUtillity.isNotBlank(inputVO.getUpdatedById()) && inputVO.getUpdatedById().equals(inputVO.getCreatedById())){
					wantCrtUserData = false;
					abstractDTO.setUpdatedByFirstName(u.getFirstName());
					abstractDTO.setUpdatedByLastName(u.getLastName());
					abstractDTO.setUpdatedByProfilePic(u.getProfilePic());
				}
			}
			if(wantCrtUserData && ObjUtillity.isNotBlank(inputVO.getUpdatedById())){
				User u = (User) genericService.getById(inputVO.getUpdatedById(), User.class);
				abstractDTO.setUpdatedByFirstName(u.getFirstName());
				abstractDTO.setUpdatedByLastName(u.getLastName());
				abstractDTO.setUpdatedByProfilePic(u.getProfilePic());
				
			}
			return new ResponseEntity<>(abstractDTO, HttpStatus.OK);

		} catch (InvalidDataException e) {
			LOGGER.error("InvalidDataException in update()", e);
			return new ResponseEntity<>(abstractDTO, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			LOGGER.error("Exception in update()", e);
			return new ResponseEntity<>(abstractDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@SuppressWarnings("unchecked")
	@ApiOperation(value = "Find")
	@RequestMapping(value = {
			"/{mapTo}/find" }, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public  ResponseEntity<List<AbstractDTO>> find(@RequestBody AbstractDTO abstractDTO, 
			@PathVariable("mapTo") String mapTo,
			BindingResult bindingResult, HttpServletRequest httpRequest, HttpServletResponse httpResponse) {
		LOGGER.debug("Execute method : save()");
		try {
			if (bindingResult.hasErrors()) {
				throw new InvalidDataException(bindingResult.getObjectName());
			}

			final Class<? extends AbstractBO> classMapped = dtoEntityMapper.getBOMapping(abstractDTO.getMapTo());
			List<AbstractDTO> data = genericService.find(abstractDTO,classMapped);
			// throw new CodelabRuntimeException("Error in login()", e);
			return new ResponseEntity<>(data, HttpStatus.OK);
		} catch (@SuppressWarnings("restriction") InvalidDataException e) {
			LOGGER.error("InvalidDataException in save()", e);
			return new ResponseEntity(null, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			LOGGER.error("Exception in save()", e);
			return new ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@SuppressWarnings("unchecked")
	@ApiOperation(value = "Query List")
	@RequestMapping(value = {
			"/{mapTo}/q/list" }, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public  ResponseEntity<List<AbstractDTO>> list(@RequestBody SearchDTO abstractDTO, 
			@PathVariable("mapTo") String mapTo,
			@RequestParam(name="wantCrtUserData",required=false) Boolean wantCrtUserData,
			BindingResult bindingResult, HttpServletRequest httpRequest, HttpServletResponse httpResponse) {
		LOGGER.debug("Execute method : q/list");
		try {
			if (bindingResult.hasErrors()) {
				throw new InvalidDataException(bindingResult.getObjectName());
			}
			if (ObjUtillity.isNull(wantCrtUserData)) {
				wantCrtUserData = false;
			}
			final Class<? extends AbstractBO> classMapped = dtoEntityMapper.getBOMapping(abstractDTO.getEntity());
			List<AbstractBO> dbData = genericService.getListByCriteria(classMapped, abstractDTO.getColumnNames(), abstractDTO.getWhereClause(),abstractDTO);
			// throw new CodelabRuntimeException("Error in login()", e);
			List<AbstractDTO> data = new ArrayList<AbstractDTO>();
			if(ObjUtillity.isNotBlank(dbData)){
				ModelMapper modelMapper = new ModelMapper();
				modelMapper.getConfiguration().setAmbiguityIgnored(true);
				final Class<? extends AbstractDTO> dtoMapped = (Class<? extends AbstractDTO>) dtoEntityMapper.getDTOMapping(abstractDTO.getEntity());
				for (AbstractBO bo : dbData) {
					AbstractDTO dto = modelMapper.map(bo, dtoMapped);
					boolean isSetUpdated = false;
					if(wantCrtUserData && ObjUtillity.isNotBlank(bo.getCreatedById())){
						User u = (User) genericService.getById(bo.getCreatedById(), User.class);
						dto.setCreatedByFirstName(u.getFirstName());
						dto.setCreatedByLastName(u.getLastName());
						dto.setCreatedByProfilePic(u.getProfilePic());
						if(ObjUtillity.isNotBlank(bo.getUpdatedById()) && bo.getUpdatedById().equals(bo.getCreatedById())){
							dto.setUpdatedByFirstName(u.getFirstName());
							dto.setUpdatedByLastName(u.getLastName());
							dto.setUpdatedByProfilePic(u.getProfilePic());
							isSetUpdated = true;
						}
					}
					if(!isSetUpdated && wantCrtUserData && ObjUtillity.isNotBlank(bo.getUpdatedById())){
						User u = (User) genericService.getById(bo.getUpdatedById(), User.class);
						dto.setUpdatedByFirstName(u.getFirstName());
						dto.setUpdatedByLastName(u.getLastName());
						dto.setUpdatedByProfilePic(u.getProfilePic());
					}
					
					data.add(dto);
				}
			}
			return new ResponseEntity<>(data, HttpStatus.OK);
		} catch (@SuppressWarnings("restriction") InvalidDataException e) {
			LOGGER.error("InvalidDataException in q/list", e);
			e.printStackTrace();
			return new ResponseEntity(null, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			LOGGER.error("Exception in q/list", e);
			e.printStackTrace();
			return new ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@ApiOperation(value = "hardDeleteById")
	@RequestMapping(value = {
			"/{mapTo}/hardDeleteById" }, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Boolean> hardDeleteById(@RequestBody AbstractDTO abstractDTO,
			@PathVariable("mapTo") String mapTo, BindingResult bindingResult, HttpServletRequest httpRequest,
			HttpServletResponse httpResponse) {
		LOGGER.debug("Execute method : update()");
		try {
			if (bindingResult.hasErrors()) {
				throw new InvalidDataException(bindingResult.getObjectName());
			}
			if (ObjUtillity.isNull(abstractDTO.getId())) {
				throw new InvalidDataException(Constant.EXC_NULL_ID);
			}

			final Class<? extends AbstractBO> classMapped = dtoEntityMapper.getBOMapping(abstractDTO.getMapTo());
			boolean isDeleted = genericService.hardDeleteById(abstractDTO.getId(), classMapped);

			return new ResponseEntity<>(isDeleted, HttpStatus.OK);

		} catch (InvalidDataException e) {
			LOGGER.error("InvalidDataException in update()", e);
			return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			LOGGER.error("Exception in update()", e);
			return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
