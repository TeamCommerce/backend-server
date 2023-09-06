package com.commerce.backendserver.image.infra;

import static com.commerce.backendserver.image.exception.ImageError.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.commerce.backendserver.global.exception.CommerceException;

@Component
public class ImageManager {

	private final AmazonS3Client amazonS3Client;
	private final String buketName;

	public ImageManager(
		AmazonS3Client amazonS3Client,
		@Value("${cloud.aws.s3.bucket}") String buketName
	) {
		this.amazonS3Client = amazonS3Client;
		this.buketName = buketName;
	}

	public List<String> uploadFiles(List<MultipartFile> files, String type) {
		List<String> storeUrls = new ArrayList<>();
		files.forEach(file -> storeUrls.add(uploadFile(file, type)));
		return storeUrls;
	}

	private String uploadFile(MultipartFile file, String type) {
		validateFileExist(file);

		String originalFilename = file.getOriginalFilename();
		String storeFilename = createStoreFilename(originalFilename, type);

		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentType(file.getContentType());
		metadata.setContentLength(file.getSize());

		try (InputStream inputStream = file.getInputStream()) {
			PutObjectRequest putObjectRequest = new PutObjectRequest(
				buketName,
				storeFilename,
				inputStream,
				metadata).withCannedAcl(CannedAccessControlList.PublicRead);

			amazonS3Client.putObject(putObjectRequest);
		} catch (IOException e) {
			throw CommerceException.of(UPLOAD_FAIL);
		}

		return amazonS3Client.getUrl(buketName, storeFilename).toString();
	}

	private void validateFileExist(MultipartFile file) {
		if (file == null || file.isEmpty()) {
			throw CommerceException.of(EMPTY_FILE);
		}
	}

	private String createStoreFilename(String originalFilename, String type) {
		String ext = extractExt(originalFilename);
		String filename = UUID.randomUUID() + ext;

		return BucketMetadata.generateFilename(filename, type);
	}

	private String extractExt(String originalFilename) {
		int index = originalFilename.lastIndexOf(".");
		return originalFilename.substring(index);
	}
}

