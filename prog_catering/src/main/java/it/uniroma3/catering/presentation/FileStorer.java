package it.uniroma3.catering.presentation;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.web.multipart.MultipartFile;

public class FileStorer {

	public static String uploadDirectory = System.getProperty("user.dir")+"/src/main/resources/static/images/";
	
	
	
	public static String store(MultipartFile file, String owner) {
		new File(uploadDirectory+owner).mkdir();
		Path fileNameAndPath  = Paths.get(uploadDirectory+owner, file.getOriginalFilename());
		try {
			Files.write(fileNameAndPath, file.getBytes());
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fileNameAndPath.getFileName().toString();
	}
	
	
	public static void removeImg(String owner, String name) {
		Path fileNameAndPath  = Paths.get(uploadDirectory+owner+"/"+name);
		try {
			Files.delete(fileNameAndPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
		removeDirectory(owner);
	}
	
	private static void removeDirectory(String owner) {
		new File(uploadDirectory+owner).delete();
	}
	

	
}
