package com.commerce.backendserver.image.stub;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.commerce.backendserver.image.application.ImageService;

public class ImageServiceStub extends ImageService {

	public ImageServiceStub() {
		super(null);
	}

	@Override
	public List<String> uploadImages(
		final List<MultipartFile> files,
		final String type
	) {
		return List.of("hello1.jpg", "hello2.jpg");
	}
}
