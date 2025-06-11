package com.job_connect.controller.impl;

import com.job_connect.constant.ApiConstant;
import com.job_connect.controller.FileController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(ApiConstant.FILE_API)
@CrossOrigin(origins = "http://localhost:5174")
public class FileControllerImpl implements FileController {

    @Override
    public String uploadFile(MultipartFile file) {
        return "https://s3file.com";
    }

}
