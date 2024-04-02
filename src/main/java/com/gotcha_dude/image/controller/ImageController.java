package com.gotcha_dude.image.controller;

import com.gotcha_dude.image.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/image")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @GetMapping("/check/userId")
    public List<String> getItems(){
        List<String> items = new ArrayList<>();
        items.add("aaa");
        items.add("bbb");
        items.add("ccc");

        return items;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file, @RequestParam("imageCount") int imageCount) throws IOException {
        // 이미지 업로드 서비스 호출
        String uploadFileInfo = imageService.editImages(file, imageCount);
        // 업로드 결과를 클라이언트에 반환
        return ResponseEntity.ok(uploadFileInfo);
    }





}
