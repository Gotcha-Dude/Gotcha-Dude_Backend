package com.gotcha_dude.audio.service;

import com.gotcha_dude.system.model.UpLoadFileInfo;
import com.gotcha_dude.system.util.AudioUtil;
import com.gotcha_dude.system.util.FileStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;


@RequiredArgsConstructor
@Service
public class AudioService {

    private final FileStore fileStore;
    private final AudioUtil audioUtil;
    public UpLoadFileInfo editAudio(MultipartFile multipartFile) {
        try {
            UpLoadFileInfo upLoadFileInfo = fileStore.uploadFile(multipartFile, "audio");
            File originalAudio = fileStore.getFile(upLoadFileInfo.getFileId());
            File noiseAudio = fileStore.getFile("202306091686309333764"); // 노이즈 파일 이름 하드코딩
            // 덮어쓸 더미 결과 파일
            UpLoadFileInfo resultFileInfo = fileStore.uploadFile(multipartFile, "audio");
            File outputFile = fileStore.getFile(resultFileInfo.getFileId());

            // 오디오 파일 읽기
            AudioInputStream originalStream = AudioSystem.getAudioInputStream(originalAudio);
            AudioInputStream noiseStream = AudioSystem.getAudioInputStream(noiseAudio);

            // 오디오 합치기
            AudioInputStream resultStream = audioUtil.mix(originalStream, noiseStream);

            // 결과 파일에 쓰기
            AudioSystem.write(resultStream, AudioFileFormat.Type.WAVE, outputFile);

            System.out.println("hererererererererererererere");

        } catch (UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }


        return null;
    }
}
