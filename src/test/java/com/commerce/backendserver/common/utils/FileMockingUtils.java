package com.commerce.backendserver.common.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class FileMockingUtils {

	private static final String FILE_PATH = "src/test/resources/images/";
	private static final String FILE_META_NAME = "files";
	private static final String CONTENT_TYPE = "image/bmp";

	public static MultipartFile createMockMultipartFile(
		final String fileName
	) throws IOException {
		try (final FileInputStream stream = new FileInputStream(FILE_PATH + fileName)) {
			return new MockMultipartFile(FILE_META_NAME, fileName, CONTENT_TYPE, stream);
		}
	}

	public static List<MultipartFile> createMockMultipartFiles() throws IOException {
		List<MultipartFile> files = new ArrayList<>();
		for (int i = 1; i <= 2; i++) {
			files.add(createMockMultipartFile(String.format("hello%s.jpg", i)));
		}
		return files;
	}

	public static MultipartFile createEmptyFile() {
		return new MockMultipartFile(
			"file",
			"hello.png",
			"image/png",
			new byte[] {}
		);
	}
}
