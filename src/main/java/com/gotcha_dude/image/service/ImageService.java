package com.gotcha_dude.image.service;

import com.gotcha_dude.system.model.UpLoadFileInfo;
import com.gotcha_dude.system.util.FileStore;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;

@RequiredArgsConstructor
@Service
public class ImageService {

    private final FileStore fileStore;

    UpLoadFileInfo upLoadFileInfo = new UpLoadFileInfo();

    public String editImages(MultipartFile file, int imageCount) {
        StringBuilder resultMessage = new StringBuilder();
        try {

            FileWriter writer = new FileWriter("C:/Users/kimkyoungwook/Desktop/보안 완료 이미지/"+file.getOriginalFilename()+".txt");
            for (int i = 1; i <= imageCount; i++) {
                // 이미지 파일을 BufferedImage로 변환
                BufferedImage originalImage = ImageIO.read(new ByteArrayInputStream(file.getBytes()));

                // Graphics2D 객체 생성
                Graphics2D g2d = originalImage.createGraphics();

                // 랜덤한 위치 생성
                Random random = new Random();
                int x = random.nextInt(originalImage.getWidth());
                int y = random.nextInt(originalImage.getHeight());

                int minWidth = 5; // 최소 너비
                int minHeight = 5; // 최소 높이
                int maxWidth = 10; // 최대 너비
                int maxHeight = 10; // 최대 높이

                int width = minWidth + random.nextInt(maxWidth - minWidth + 1);
                int height = minHeight + random.nextInt(maxHeight - minHeight + 1);

                // 랜덤한 색상 생성
                Color randomColor = getRandomColor(originalImage, x, y, width, height);

                // 랜덤한 위치와 크기에 랜덤한 색상의 사각형 그리기
                g2d.setColor(randomColor);
                g2d.fillRect(x, y, width, height);

                // BufferedImage를 byte 배열로 변환하여 반환
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(originalImage, "jpg", baos);
                byte[] imageBytes = baos.toByteArray();

                // 원본 이미지 저장
                String originalImagePath = "C:/Users/kimkyoungwook/Desktop/보안 이미지/" + file.getOriginalFilename() + ".jpg";
                File originalImageFile = new File(originalImagePath);
                ImageIO.write(originalImage, "jpg", originalImageFile);

                // 변환된 이미지 저장
                String modifiedImagePath = "C:/Users/kimkyoungwook/Desktop/보안 완료 이미지/"+ file.getOriginalFilename() + i + ".jpg";
                File modifiedImageFile = new File(modifiedImagePath);
                ImageIO.write(originalImage, "jpg", modifiedImageFile);

                // 변동된 색상값 문자열 생성
                String colorInfo = "변동된 색상값 (R, G, B): " +
                        randomColor.getRed() + ", " + randomColor.getGreen() + ", " + randomColor.getBlue();

                String message = file.getOriginalFilename() + (i) + "의  X" + x + "  Y" + y +
                        "에 랜덤한 크기와 색상의 사각형을 이미지에 그렸습니다." + colorInfo + "\n"+ "\n";

                // 결과 메시지를 파일에 작성
                writer.write(message);

                // 결과 메시지를 StringBuilder에 추가
                resultMessage.append(message);
            }

            // 파일 닫기
            writer.close();
            return ResponseEntity.ok().toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "이미지 처리 중 예외가 발생했습니다.";
        }
    }

    // 랜덤한 색상 생성 메서드
    private Color getRandomColor(BufferedImage image, int x, int y, int width, int height) {
        // 기존 이미지의 특정 영역에서 평균 색상 계산
        long redSum = 0, greenSum = 0, blueSum = 0;
        int pixelCount = 0;
        for (int i = x; i < x + width; i++) {
            for (int j = y; j < y + height; j++) {
                if (i < image.getWidth() && j < image.getHeight()) {
                    int rgb = image.getRGB(i, j);
                    Color color = new Color(rgb);
                    redSum += color.getRed();
                    greenSum += color.getGreen();
                    blueSum += color.getBlue();
                    pixelCount++;
                }
            }
        }

        int avgRed = (int) (redSum / pixelCount);
        int avgGreen = (int) (greenSum / pixelCount);
        int avgBlue = (int) (blueSum / pixelCount);

        // 기존 색상에서 조금씩 떨어진 랜덤한 값으로 변환
        Random random = new Random();
        int deviation = 70; // 색상의 편차 범위 조절
        int minDeviation = 50; // 최소 색상 편차를 설정합니다.
        int newRed = Math.max(0, Math.min(255, avgRed + minDeviation / 2 + random.nextInt(deviation - minDeviation)));
        int newGreen = Math.max(0, Math.min(255, avgGreen + minDeviation / 2 + random.nextInt(deviation - minDeviation)));
        int newBlue = Math.max(0, Math.min(255, avgBlue + minDeviation / 2 + random.nextInt(deviation - minDeviation)));


        return new Color(newRed, newGreen, newBlue);
    }

    // MultipartFile을 File로 변환하는 메서드
    private File convertMultipartFileToFile(MultipartFile multipartFile) throws IOException {
        try {
            File file = new File("C:/Users/kimkyoungwook/Desktop/보안 이미지", Objects.requireNonNull(multipartFile.getOriginalFilename()));
            multipartFile.transferTo(file);
            return file;
        } catch (IOException e) {
            System.out.println(e);
            return null;
        }
    }
}
