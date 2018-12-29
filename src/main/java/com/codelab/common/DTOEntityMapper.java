package com.codelab.common;

import java.security.InvalidKeyException;
import java.util.HashMap;

import com.codelab.beans.UserBO;
import com.codelab.beans.UserDTO;
import com.codelab.beans.general.DirectSearchDTO;
import com.codelab.beans.general.SearchDTO;

@SuppressWarnings("rawtypes")
public final class DTOEntityMapper {

	private static HashMap<String, Class> dtos = new HashMap<>();
	private static HashMap<String, Class> bos = new HashMap<>();

	static {
		dtos.put("user", UserDTO.class);
		bos.put("user", UserBO.class);

		dtos.put("search", SearchDTO.class);
		dtos.put("directSearch", DirectSearchDTO.class);

	}

	public Class getDTOMapping(String key) {
		try {
			Class c = dtos.get(key);
			if (c != null)
				return c;
			throw new InvalidKeyException(key);
		} catch (InvalidKeyException e) {
			System.out.println("No Mapping Found For Key : " + key);
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	public Class getBOMapping(String key) {
		try {
			Class c = bos.get(key);
			if (c != null)
				return c;
			throw new InvalidKeyException(key);
		} catch (InvalidKeyException e) {
			System.out.println("No Mapping Found For Key : " + key);
		} catch (Exception e) {
		}
		return null;

	}

}
