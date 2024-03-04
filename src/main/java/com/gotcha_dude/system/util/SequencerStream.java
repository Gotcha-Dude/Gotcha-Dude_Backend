package com.gotcha_dude.system.util;

import javax.sound.sampled.AudioInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class SequencerStream extends InputStream {
    private final List<AudioInputStream> streams;
    private final long noiseStartFrame;
    private long currentFrame = 0;
    private int currentIndex = 0;  // currentIndex 필드 추가

    public SequencerStream(List<AudioInputStream> streams, long noiseStartFrame) {
        this.streams = streams;
        this.noiseStartFrame = noiseStartFrame;
    }

    @Override
    public int read() throws IOException {
        int value = streams.get(0).read();

        if (value == -1) {
            currentIndex++;

            if (currentIndex >= streams.size()) {
                return -1;
            }

            return read();
        }

        if (currentFrame == noiseStartFrame) {
            currentIndex++;
        }

        currentFrame++;

        return value;
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        // 현재 인덱스의 스트림에서 바이트 배열을 읽음
        int bytesRead = streams.get(0).read(b, off, len);

        // 현재 스트림이 끝나면 다음 스트림으로 전환
        if (bytesRead == -1) {
            currentIndex++;

            // 모든 스트림이 끝났을 때 -1 반환
            if (currentIndex >= streams.size()) {
                return -1;
            }

            // 다음 스트림으로 전환 후 나머지 바이트 배열을 읽음
            return read(b, off, len);
        }

        // 노이즈 시작 프레임에 도달하면 노이즈 스트림으로 전환
        if (currentFrame == noiseStartFrame) {
            currentIndex++;
        }

        currentFrame += bytesRead;

        return bytesRead;
    }
}

