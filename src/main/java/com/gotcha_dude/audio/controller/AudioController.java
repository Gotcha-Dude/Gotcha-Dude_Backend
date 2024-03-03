package com.gotcha_dude.audio.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api")
public class AudioController {
    @PostMapping("/testapi")
    public String getString(@RequestBody String data){
        System.out.println(data);
        return data;
    }
}
