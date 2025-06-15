package com.job_connect.service;

import com.job_connect.model.s3.UrlDto;
import org.springframework.web.multipart.MultipartFile;

public interface S3Service {
    String LOGO_FOLDER = "logos/";

    UrlDto uploadFile(String bucketName, String location, MultipartFile file);

    UrlDto uploadToPublicBucket(String type, MultipartFile file);

}
