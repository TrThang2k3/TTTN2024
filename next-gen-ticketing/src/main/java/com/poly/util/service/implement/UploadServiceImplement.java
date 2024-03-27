package com.poly.util.service.implement;

import java.io.File;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.poly.util.service.UploadService;

import jakarta.servlet.ServletContext;

@Service
public class UploadServiceImplement implements UploadService{
	@Autowired
	ServletContext app;
	
	@Override
	public File save(MultipartFile file, String folder) {
		File dir = new File(app.getRealPath("/assets/" + folder));
		if (!dir.exists()) {
			dir.mkdirs();
		}
		String str = System.currentTimeMillis() + file.getOriginalFilename();
		String name = Integer.toHexString(str.hashCode()) + str.substring(str.lastIndexOf("."));
		try {
			File savedFile = new File(dir, name);
			file.transferTo(savedFile);
			return savedFile;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
