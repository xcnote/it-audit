package com.it.audit.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import com.it.audit.exception.ExcelException;
import com.it.audit.exception.FileException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FileUtils {
	
	private static final String DEFAULT_FILE_PATH_BASE = "upload";
	
	/**
	 * 文件写入
	 * @param filePath
	 * @param fileName
	 * @param input
	 * @return
	 */
	public static void writeFile(String filePath, String fileName, InputStream input){
		try {
			File currfilePath = new File(DEFAULT_FILE_PATH_BASE + File.separator + filePath);
			if(!currfilePath.exists() || !currfilePath.isDirectory()){
				System.out.println(currfilePath.mkdir());
			}
			System.out.println(System.getProperty("user.dir"));
			System.out.println(currfilePath.getAbsolutePath());
			File file = new File(DEFAULT_FILE_PATH_BASE + File.separator + filePath + File.separator + fileName);
			if(!file.exists()){
				file.createNewFile();
			}
			BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(file));
			
			IOUtils.copy(input, stream);
			IOUtils.closeQuietly(stream);
			IOUtils.closeQuietly(input);
//			byte[] write = new byte[1024 * 1024];
//			while(input.read(write) > 0){
//				stream.write(write);
//			}
//			stream.close();
//			input.close();
		} catch (Exception e) {
			log.error("file upload to location error. filePath:{}, fileName:{}", filePath, fileName, e);
			throw new FileException("文件写入异常");
		}
	}

	/**
	 * 文件下载
	 * @param filePath
	 * @param fileName
	 * @param response
	 */
	public static void fileExport(String filePath, String fileName, String name, HttpServletRequest request, HttpServletResponse response){
		try {
			File file = new File(DEFAULT_FILE_PATH_BASE + File.separator + filePath + File.separator + fileName);
			if(!file.exists()){
				log.info("file is not found.  filePath:{}, fileName:{}", filePath, fileName);
				return;
			}
			if(StringUtils.isBlank(name)){
				name = fileName;
			}
			
			String userAgent = request == null? "": request.getHeader("User-Agent");
			try {
				if(userAgent.contains("MSIE") || userAgent.contains("Trident")){	//解决IE乱码
					name = URLEncoder.encode(name,"UTF8");
				} else {
					name = new String(name.getBytes("UTF-8"), "ISO-8859-1");
				}
			} catch (UnsupportedEncodingException e1) {
				throw new ExcelException("文件名转码失败");
			}
			
			BufferedInputStream stream = new BufferedInputStream(new FileInputStream(file));
			response.setContentType("application/octet-stream");  
	        response.setContentLength((int) file.length());
	        
	        String headerKey = "Content-Disposition";  
	        String headerValue = String.format("attachment; filename=%s", name);  
	        response.setHeader(headerKey, headerValue); 
			IOUtils.copy(stream, response.getOutputStream());
			response.flushBuffer();
			IOUtils.closeQuietly(stream);
		} catch (Exception e) {
			log.error("file export from location error. filePath:{}, fileName:{}", filePath, fileName, e);
			throw new FileException("文件下载异常");
		}
	}
	
	public static void fileDelete(String filePath, String fileName){
		try {
			File file = new File(DEFAULT_FILE_PATH_BASE + File.separator + filePath + File.separator + fileName);
			file.deleteOnExit();
		} catch (Exception e) {
			log.error("file delete from location error. filePath:{}, fileName:{}", filePath, fileName, e);
			throw new FileException("文件删除异常");
		}
	}
}
