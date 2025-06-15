package com.job_connect.controller.impl;

import com.job_connect.constant.ApiConstant;
import com.job_connect.controller.FileController;
import com.job_connect.model.s3.UrlDto;
import com.job_connect.service.S3Service;
import com.job_connect.service.impl.S3ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import static com.job_connect.service.S3Service.LOGO_FOLDER;

@RestController
@RequestMapping(ApiConstant.FILE_API)
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
public class FileControllerImpl implements FileController {

    private final S3Service s3Service;

    @Override
    public UrlDto uploadFile(MultipartFile file) {
        return s3Service.uploadToPublicBucket(LOGO_FOLDER, file);
    }

}
