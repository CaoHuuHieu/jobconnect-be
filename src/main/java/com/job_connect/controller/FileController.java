package com.job_connect.controller;

import com.job_connect.model.s3.UrlDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

public interface FileController {


    @PostMapping("upload")
    UrlDto uploadFile(@RequestParam("file") MultipartFile file);

}
