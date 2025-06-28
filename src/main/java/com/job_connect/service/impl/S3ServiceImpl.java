package com.job_connect.service.impl;

import com.job_connect.exception.BusinessException;
import com.job_connect.model.s3.UrlDto;
import com.job_connect.service.S3Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;



@Service
public class S3ServiceImpl implements S3Service {


    private final S3Client s3Client;
    private final String publicBucket;

    public S3ServiceImpl(@Value("${config.aws_s3.region}") String regionName,
                         @Value("${config.aws_s3.public_bucket}") String publicBucket) {
        this.publicBucket = publicBucket;
        this.s3Client = S3Client.builder()
                    .build();
    }

    @Override
    public UrlDto uploadFile(String bucketName, String location, MultipartFile file)  {
        try {
            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(location)
                    .build();
            RequestBody requestBody = RequestBody.fromBytes(file.getBytes());
            s3Client.putObject(request, requestBody);
            return new UrlDto(location);
        }catch (Exception e) {
            throw new BusinessException(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public UrlDto uploadToPublicBucket(String type, MultipartFile file) {
        String location = type + file.getOriginalFilename();
        return uploadFile(publicBucket, location, file);
    }
}
