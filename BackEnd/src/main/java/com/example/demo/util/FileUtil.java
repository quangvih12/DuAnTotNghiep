package com.example.demo.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

public class FileUtil {

    public static String fileToBase64(MultipartFile file) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        byte[] fileByte = file.getBytes();
        String encode = Base64.getEncoder().encodeToString(fileByte);
        return encode;
    }

}
