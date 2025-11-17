package com.jaeshop.modules.image.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.jaeshop.global.exception.CustomException;
import com.jaeshop.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageUploadServiceImpl implements ImageUploadService{

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Override
    public String upload(MultipartFile file) {

        String fileName = createFileName(file.getOriginalFilename());

        try {
            amazonS3.putObject(
                    new PutObjectRequest(bucket, fileName, file.getInputStream(), null)
                            .withCannedAcl(CannedAccessControlList.PublicRead)
            );
        } catch (IOException e) {
            throw new CustomException(ErrorCode.S3_UPLOAD_FAILED);
        }

        return amazonS3.getUrl(bucket, fileName).toString();
    }

    private String createFileName(String originalName) {

        if (originalName == null || originalName.isBlank()) {
            return "product/" + UUID.randomUUID() + ".png";
        }

        String ext = "";

        int idx = originalName.lastIndexOf(".");
        if (idx > -1) {
            ext = originalName.substring(idx);
        } else {
            ext = ".png"; // 확장자 없으면 기본 png
        }

        return "product/" + UUID.randomUUID() + ext;
    }
}
