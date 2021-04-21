package com.Rest.Book.helper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileUploadHelper {

	// private final String UPLOAD_DIR = "D:\\codes\\Spring
	// Boot\\BootRestBook\\src\\main\\resources\\static\\image";
	private final String UPLOAD_DIR = new ClassPathResource("static/image").getFile().getAbsolutePath();

	public FileUploadHelper() throws IOException {

	}

	// upload file
	public boolean fileUpload(MultipartFile file) {
		boolean flag = false;
		try {

//			InputStream inputStream = file.getInputStream();
//			// get all data
//			byte[] data = new byte[inputStream.available()];
//			// read all data
//			inputStream.read(data);
//
//			// write file
//			FileOutputStream fout = new FileOutputStream(UPLOAD_DIR + File.separator + file.getOriginalFilename());
//			fout.write(data);
//
//			fout.flush();
//			fout.close();
//			flag = true;

			/*
			 * * Another Way to imput file
			 */
			Files.copy(file.getInputStream(), Paths.get(UPLOAD_DIR + File.separator + file.getOriginalFilename()),
					StandardCopyOption.REPLACE_EXISTING);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return flag;
	}
}
