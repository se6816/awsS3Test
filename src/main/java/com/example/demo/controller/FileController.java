package com.example.demo.controller;

import java.nio.file.Files;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class FileController {

	private final FileUtil fileUtil;
	
	
	public FileController(FileUtil fileUtil) {
		this.fileUtil = fileUtil;
	}


	@PostMapping("/upload")
	public @ResponseBody String upload(@RequestParam String content,@RequestPart(name = "files",required = false) MultipartFile[] files ) {
		System.out.println(content);
		if(files[0].isEmpty()) {
			System.out.println("파일 없음");
		}else {
			for(MultipartFile file: files) {
				System.out.println(file.getOriginalFilename());
				fileUtil.upload(file, "20230517",UUID.randomUUID().toString());
			}
		}
		
		return "success";
	}
}
