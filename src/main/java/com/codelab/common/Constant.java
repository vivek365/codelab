package com.codelab.common;

/**
 * This interface is used to provide constants.
 * 
 * @author Vivek Jain
 *
 */
public interface Constant {
	
	//common
	public static final long second = 1000;
	public static final long emailJobDelay = 25 * second;
	public static final boolean sentEmail = true;
	public static final String SUCCESS = "success";
	public static final String FAIL = "fail";
	public static final String BLOCKALPHA = "ABCDEFIJKLMNOPQRSTUVWXYZ";
	public static final String BLOCKNUMERIC = "1234567890";
	public static final String APPOINTMENTID = "appointmentId";
	public static final String PATIENTID = "patientId";
	public static final String HOSPITALID = "hospitalId";
	public static final String USER_ID = "userId";
	public static final String ROLE_ID = "roleId";
	public static final String TASK_ID = "taskId";
	public static final String USER_NAME = "userName";
	public static final String USER_ROLE = "USER_ROLE";
	public static final String DEFAULT_VALUE = "0";
	public static final String REMOTE_ADDR = "REMOTE_ADDR";
	public static final String USER_AGENT = "USER_AGENT";
	public static final String STATUS = "status";
	public static final String SERVER_NAME = "SERVER_NAME";
	public static final String URL_STRING = "URL_STRING";
	public static final String REQUEST_PARAMS = "REQUEST_PARAMS";
	public static final String REQUEST_START_TIME = "REQUEST_START_TIME";
	public static final String ACTION_ID = "ACTION_ID";
	public static final String REQUEST_END_TIME = "REQUEST_END_TIME";
	public static final String STATUS_IN_PROGRESS = "IN_PROGRESS";
	public static final String XAUTHTOKEN = "X-Auth-Token";
	public static final String AUTHORIZATIONTOKEN = "Autorization Token";
	public static final String STRING = "string";
	public static final String HEADER = "header";
	public static final String APPJSON = "application/json";
	public static final String JSON = "json";
	public static final String EXPIRED = "expired";
	public static final String FORMID = "formId";
	public static final String QUOTES = "'";
	public static final String AND = " AND ";
	public static final String REQUIRED_PARAMS_MSG = "Required parameters missing";
	public static final String SUPERADMIN = "SUPER_ADMIN";
	public static final String COMPANYADMIN = "Company Admin";
	

	public static final String COMPANYID = "companyId";
	public static final String PROJECTID = "projectId";
	public static final String DPRID = "dprId";
	public static final String BOQID = "boqId";
	public static final String ID = "id";
	public static final String NAME = "name";
	
	//Start : Drive Constants
	//String TASK_FOLDER_ID = "1VET6VXbQ8xIQt56cM1cNmtHlbpafgwDj";
	String TASK_FOLDER_ID = "1oQlfaeLm4JZU9OUqQSmV4beEkGiLj3SK";
	String PROFILE_PIC_FOLDER_ID = "1Jww_b4Ul3HMFoIU_wk_sYwv22AW1xG3y";
	String FILE_TYPE_KEY = "fileType";
	String FILE_TASK_ID_KEY = "taskId";
	String PROPERTY_VISIBILITY = "PRIVATE";
	public static final String DRIVE_FIELDS = "nextPageToken, items(id, title, parents, alternateLink, downloadUrl,"
			+ " properties, modifiedDate,hasThumbnail,fileSize,fileExtension,fullFileExtension,"
			+ "iconLink,thumbnail,thumbnailLink,thumbnailVersion,version,webViewLink)";
	//End : Drive Constants
	
	// Start : CommonGrid
	public static final String SUBMODULEID = "subModuleId";
	public static final String MODULEID = "moduleId";
	public static final String FLEXIBLEWORKFLOWID = "flexibleWorkFlowID";
	public static final String ISCommonGrid = "isCommonGrid";
	public static final String ORDER = "Order";
	public static final String ADDITIONALCOLUMNS = "AdditionalColumns";
	public static final String COLUMNS = "Columns";
	public static final String FROMCLAUSE = "fromClause";
	public static final String WHERE = " WHERE ";
	public static final String DISTINCTRESULT = "distinctResult";
	public static final String OBJECTID = "objectId";
	public static final String SUBOBJECTID = "subObjectId";
	public static final String ORDER_BY = " ORDER BY ";
	// End : CommonGrid

	// Start : for generic dao
	public static final String ALIAS = "alias";
	public static final String FIRSTINDEX = "firstIndex";
	public static final String UNIQUERESULT = "uniqueResult";
	public static final String MAXRESULT = "maxResult";
	public static final String MAP = "map";
	public static final String ORDERBY = "orderBy";
	public static final String PAGE = "page";
	// End : for generic dao

	public static final String REQUEST_BODY = "requestBody";

	public static final String REQPARAMMISSING = "Required parameters are missing.";
	// Strat: Temp
	public static final String CLINICCODE = "cliniccode";
	// End: Temp
	// FileUtils
	public static final String MIME_TYPE = "application/octet-stream";
	public static final String CONTENT_DISPOSITION = "Content-Disposition";
	public static final String ATTACHMENT = "attachment";
	public static final String FILE_NAME = "filename";
	public static final String SEMICOLON = ";";
	
	//Exception Messages
	public static final String EXC_NULL_ID = "Can not able to update data if id is blank";
	
	public static final int STATUS_ACTIVE = 1;
	public static final int MAIL_INVITEUSER = 1;
	
}
