package com.gotcha_dude.system.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

@Slf4j
@Component
public class AudioUtil {
    public AudioInputStream mix(AudioInputStream original, AudioInputStream noise) {
            // 원본 스트림 정보
            AudioFormat originalFormat = original.getFormat();
            long originalFrameLength = original.getFrameLength();

            // 노이즈 스트림 정보
            AudioFormat noiseFormat = noise.getFormat();
            long noiseFrameLength = noise.getFrameLength();

            // 원본 스트림과 노이즈 스트림의 샘플 속도를 일치시킵니다.
//            if (!originalFormat.matches(noiseFormat)) {
//                noise = AudioSystem.getAudioInputStream(originalFormat, noise);
//            }

            // 랜덤한 위치에 노이즈 추가
            long randomNoiseStartFrame = new Random().nextInt((int) originalFrameLength);
            long totalFrames = Math.max(originalFrameLength, randomNoiseStartFrame + noiseFrameLength);

            // 새로운 믹스된 오디오 스트림 생성
            AudioInputStream mixedStream = new AudioInputStream(
                    new AudioInputStream(new SequencerStream(Arrays.asList(original, noise), randomNoiseStartFrame), originalFormat, totalFrames),
                    originalFormat,
                    totalFrames
            );

            return mixedStream;
    }

}
