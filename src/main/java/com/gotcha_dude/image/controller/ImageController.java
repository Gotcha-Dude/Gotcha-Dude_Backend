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
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file) throws IOException {
        System.out.println("나여기기기기");
       /* String filePath = "/uploads/" + file.getOriginalFilename();*/
        String upLoadFileInfo = imageService.editImage(file);
        return ResponseEntity.ok(upLoadFileInfo);
    }





}
