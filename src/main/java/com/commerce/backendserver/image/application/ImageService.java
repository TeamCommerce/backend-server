package com.commerce.backendserver.image.application;

import com.commerce.backendserver.image.infra.ImageManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageManager imageManager;

    public List<String> uploadImages(List<MultipartFile> files, String type) {
        return imageManager.uploadFiles(files, type);
    }
}
