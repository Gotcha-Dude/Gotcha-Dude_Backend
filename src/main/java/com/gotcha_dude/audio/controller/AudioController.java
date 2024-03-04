package com.gotcha_dude.audio.controller;

import com.gotcha_dude.audio.service.AudioService;
import com.gotcha_dude.system.model.UpLoadFileInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/api")
@RequiredArgsConstructor
public class AudioController {

    private final AudioService audioService;

    @PostMapping("/testapi")
    public String getString(@RequestBody String data){
        System.out.println(data);
        return data;
    }

    @PostMapping("/audio")
    public ResponseEntity<UpLoadFileInfo> uploadFile(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        UpLoadFileInfo upLoadFileInfo = audioService.editAudio(multipartFile);
        return ResponseEntity.ok(upLoadFileInfo);
    }

//    @GetMapping("/file")
//    public


}
