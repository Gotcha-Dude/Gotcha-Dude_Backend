package com.gotcha_dude.audio.controller;

import com.gotcha_dude.audio.service.AudioService;
import com.gotcha_dude.system.model.UpLoadFileInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AudioController {

    private final AudioService audioService;

    @GetMapping("/testapi")
    public String getString(){
        return "hihihii";
    }

    @PostMapping("/audio")
    public ResponseEntity<UpLoadFileInfo> uploadFile(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        UpLoadFileInfo upLoadFileInfo = audioService.editAudio(multipartFile);
        return ResponseEntity.ok(upLoadFileInfo);
    }

//    @GetMapping("/file")
//    public


}
