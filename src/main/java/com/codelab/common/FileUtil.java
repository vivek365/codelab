package com.codelab.common;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLConnection;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.codelab.configuration.CodelabRuntimeException;

@Service
public class FileUtil implements Constant {
	static final Logger LOGGER = LoggerFactory.getLogger(FileUtil.class);

	public static MultipartFile uploadFileInDir(MultipartFile file, String setFileName, String uploadFilePath)
			throws Exception {
		LOGGER.debug("Execute method : uploadFileInDir()");
		if (file != null) {
			File localDir = new File(uploadFilePath);
			if (!localDir.exists()) {
				localDir.mkdirs();
			}
			File localFile = new File(uploadFilePath + File.separator + setFileName);
			try (FileOutputStream fos = new FileOutputStream(localFile)) {
				fos.write(file.getBytes());
				fos.close();
			} catch (Exception e) {
				LOGGER.error("Error in uploadFileInDir()" + e);
				throw new CodelabRuntimeException("Error in uploadFileInDir()", e);
			}
		}
		return file;
	}

	public void downloadFile(HttpServletResponse response, String filePath) throws Exception {
		LOGGER.debug("Execute method : downloadFile()");
		File downloadFile = new File(filePath);
		try (InputStream in = FileUtils.openInputStream(downloadFile); OutputStream out = response.getOutputStream()) {
			if (downloadFile.exists()) {
				String mimeType = URLConnection.guessContentTypeFromName(downloadFile.getName());
				if (mimeType == null) {
					mimeType = MIME_TYPE;
				}
				response.setContentType(MIME_TYPE);
				response.setHeader(CONTENT_DISPOSITION,
						String.format(ATTACHMENT + SEMICOLON + FILE_NAME + "=\"%s\"", downloadFile.getName()));
				response.setHeader(FILE_NAME, downloadFile.getName());
				response.setContentLength((int) downloadFile.length());

				FileCopyUtils.copy(in, out);
			}
		} catch (Exception e) {
			LOGGER.error("Error in downloadFile()" + e);
			throw new CodelabRuntimeException("Error in downloadFile()", e);
		}

	}
	
	public static File convert(MultipartFile file) throws IOException
	{    
		System.out.println("FileUtil : convert");
		try{
			System.out.println("FILE : "+ file.getOriginalFilename());
			//File convFile = new File(file.getOriginalFilename());
			File tmpFile = File.createTempFile(file.getOriginalFilename(), null);
			//convFile.createNewFile();
		    
		    System.out.println("convFile>>"+tmpFile.getAbsolutePath());
		    FileOutputStream fos = new FileOutputStream(tmpFile); 
		    fos.write(file.getBytes());
		    fos.close(); 
		    return tmpFile;
		}catch (Exception e) {
			System.out.println("Error in convert()" + e);
			LOGGER.error("Error in convert()" + e);
			throw new CodelabRuntimeException("Error in convert()", e);
		}
	}
}
