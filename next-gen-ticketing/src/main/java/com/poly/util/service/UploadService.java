package com.poly.util.service;

import java.io.File;

import org.springframework.web.multipart.MultipartFile;

public interface UploadService {
	String save(MultipartFile file, String folder);
}
