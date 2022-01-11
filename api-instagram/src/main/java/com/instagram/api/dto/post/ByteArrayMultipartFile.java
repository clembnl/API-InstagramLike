package com.instagram.api.dto.post;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

public class ByteArrayMultipartFile implements MultipartFile {
	
    private final byte[] image;
    private final String imageName;

    public ByteArrayMultipartFile(byte[] image, String imageName) {
        this.image = image;
        this.imageName = imageName;
    }

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return imageName;
	}

	@Override
	public String getOriginalFilename() {
		// TODO Auto-generated method stub
		return imageName;
	}

	@Override
	public String getContentType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isEmpty() {
		return image == null || image.length == 0;
	}

	@Override
	public long getSize() {
		return image.length;
	}

	@Override
	public byte[] getBytes() throws IOException {
		return image;
	}

	@Override
	public InputStream getInputStream() throws IOException {
		return new ByteArrayInputStream(image);
	}

	@Override
	public void transferTo(File dest) throws IOException, IllegalStateException {
		new FileOutputStream(dest).write(image);

	}

}
