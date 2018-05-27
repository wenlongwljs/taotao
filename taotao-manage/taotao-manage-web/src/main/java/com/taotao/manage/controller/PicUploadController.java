package com.taotao.manage.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.manage.bean.PicUploadResult;

/**
 * 图片上传
 * 
 * @author mwlbj
 *
 */
@Controller
@RequestMapping("pic")
public class PicUploadController {
	private static final Logger LOGGER = LoggerFactory.getLogger(PicUploadController.class);

	/**
	 * 上传文件格式
	 */
	private static final String[] IMAGE_TYPE = new String[] {".jpg",".jpeg",".png",".gif",".bmp"};
	
	private static final String BASE_UPLOAD_DIR = "E:\\taotao-upload";
	
	private static final String IMAGE_HOST_NAME = "http://image.taotao.com";
	
	
	@ResponseBody
	@RequestMapping(value = "upload", method = RequestMethod.POST, produces =  MediaType.TEXT_PLAIN_VALUE)
	public String upload(@RequestParam("uploadFile") MultipartFile uploadFile, HttpServletResponse response) throws IllegalStateException, IOException {
		
		//校验图片格式
		
		boolean isLegal = false;
		
		//文件原始路径
		String originalName = uploadFile.getOriginalFilename();
		
		for(String type : IMAGE_TYPE) {
			if(StringUtils.endsWithIgnoreCase(originalName, type)) {
				isLegal = true;
				break;
			}
		}
		
		//封装Result对象
		
		PicUploadResult result = new PicUploadResult();
		
		//状态
		result.setError(isLegal ? 0 : 1);
		
		
		//文件新路径
		String filePath = getFilePath(originalName);
		
		LOGGER.debug("filePath"+filePath);
		if(LOGGER.isDebugEnabled()) {
			LOGGER.debug("Pic file upload .[{}] to [{}] .", originalName, filePath);
		}
		
		//生成图片的相对引用路径
		String picUrl = StringUtils.replace(StringUtils.substringAfter(filePath, BASE_UPLOAD_DIR), "\\", "/");
		//设置图片上传后的网络访问路径，通过nginx代理网络访问。
		result.setUrl(IMAGE_HOST_NAME+picUrl);
		
		//将文件写入磁盘
		File desc = new File(filePath);
		uploadFile.transferTo(desc);
		
		isLegal = false;
		
		try {
			BufferedImage  image = ImageIO.read(desc);
			if(image != null) {
				result.setWidth(image.getWidth());
				result.setHeight(image.getHeight());
				isLegal = true;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		result.setError(isLegal ? 0 : 1);
		
		if(!isLegal) {
			//文件不合法，将磁盘中的文件删除
			desc.delete();
		}
		
		//将result对象序列化为json对象
		
		ObjectMapper objMapper = new ObjectMapper();
		
		return objMapper.writeValueAsString(result);
		
	}
	
	public String getFilePath(String sourceFileName) {
		
		String baseFolder  = BASE_UPLOAD_DIR + File.separator + "image";
		
		Date nowDate = new Date();
		
		//yyyy/mm/dd
		DateTime dt = new DateTime(nowDate);
		
		String fileFolder  = baseFolder  + File.separator + dt.toString("yyyy") + File.separator +dt.toString("MM") + File.separator + dt.toString("dd");
		
		File file = new File(fileFolder);
		
		if(!file.isDirectory()) {
			//不存在，则创建文件夹
			file.mkdirs();
		}
		
		//生成新的文件名
		String fileName = dt.toString("yyyyMMddHHmmssSSSS") + RandomUtils.nextInt(100, 9999) + "." + StringUtils.substringAfterLast(sourceFileName, ".");
		
		return fileFolder + File.separator + fileName;
		
	}
}
