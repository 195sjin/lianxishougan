package com.example.lianxishougan.controller;

import com.example.lianxishougan.pojo.Result;
import com.example.lianxishougan.utils.AliOssUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
public class FileUploadController {

    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file) throws Exception {
        String originalFilename = file.getOriginalFilename();
        String suffix = UUID.randomUUID().toString()+ originalFilename.substring(originalFilename.lastIndexOf("."));
        //file.transferTo(new File("D:\\file\\"+suffix));
        String url = AliOssUtil.uploadFile(suffix,file.getInputStream());
        return Result.success(url);
    }
}
