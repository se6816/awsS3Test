package com.example.demo.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

@Component
public class FileUtil {

	private final AmazonS3 amazonS3;

	@Value("${cloud.aws.s3.bucket}")
	private String bucket;
	
	@Value("${cloud.aws.s3.image.path}")
	private String filePath;
	public FileUtil(AmazonS3 amazonS3) {
		this.amazonS3 = amazonS3;
	}
	
	public String upload(MultipartFile file,String dirname, String filename)  {
		
		String contentType=file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.'));
		String fullFilePath=filePath+"/"+dirname+"/"+filename+contentType;
		
		
		ObjectMetadata objectMetadata=new ObjectMetadata();
		objectMetadata.setContentLength(file.getSize());
		objectMetadata.setContentType(file.getContentType());
		
		try(InputStream inputStream=file.getInputStream()){
			amazonS3.putObject(new PutObjectRequest(bucket, fullFilePath, inputStream, objectMetadata));
		} catch (IOException e) {
			System.out.println("파일 에러!!");
		}
		return fullFilePath;
	}
	
}
