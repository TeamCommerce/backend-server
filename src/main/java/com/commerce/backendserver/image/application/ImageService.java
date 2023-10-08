package com.commerce.backendserver.image.application;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.commerce.backendserver.image.infra.ImageManager;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImageService {

	private final ImageManager imageManager;

	public List<String> uploadImages(List<MultipartFile> files, String type) {
		return imageManager.uploadFiles(files, type);
	}
}
