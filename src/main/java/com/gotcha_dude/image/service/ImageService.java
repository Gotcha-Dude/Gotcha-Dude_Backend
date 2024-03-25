package com.gotcha_dude.image.service;

import com.gotcha_dude.system.util.FileStore;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;


@RequiredArgsConstructor
@Service
public class ImageService {

    private final FileStore fileStore;

    public String editImage(MultipartFile file) {

        try {
            // 이미지 파일을 BufferedImage로 변환
            BufferedImage originalImage = ImageIO.read(new ByteArrayInputStream(file.getBytes()));

            // Graphics2D 객체 생성
            Graphics2D g2d = originalImage.createGraphics();

            // 랜덤한 위치 생성
            Random random = new Random();
            int x = random.nextInt(originalImage.getWidth());
            int y = random.nextInt(originalImage.getHeight());

            // 랜덤한 너비와 높이 생성
            int width = random.nextInt(10);
            int height = random.nextInt(10);

            // 랜덤한 색상 생성
            Color randomColor = new Color(random.nextInt(200) + 10, random.nextInt(200) + 10, random.nextInt(200) + 10);

            // 랜덤한 위치와 크기에 랜덤한 색상의 사각형 그리기
            g2d.setColor(randomColor);
            g2d.fillRect(x, y, width, height);

            // BufferedImage를 byte 배열로 변환하여 반환
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(originalImage, "jpg", baos);
            byte[] imageBytes = baos.toByteArray();

            // 원본 이미지 저장
            String originalImagePath = "C:/Users/kimkyoungwook/Desktop/보안 완료 이미지/image.jpg";
            File originalImageFile = new File(originalImagePath);
            ImageIO.write(originalImage, "jpg", originalImageFile);

            // 변환된 이미지 저장
            String modifiedImagePath ="C:/Users/kimkyoungwook/Desktop/보안 완료 이미지/image.jpg";
            File modifiedImageFile = new File(modifiedImagePath);
            ImageIO.write(originalImage, "jpg", modifiedImageFile);

            // 이미지 처리 후 결과 메시지 반환
            return "랜덤한 위치에 랜덤한 크기와 색상의 사각형을 이미지에 그렸습니다. " +
                    "원본 이미지가 " + originalImagePath + "에, 변환된 이미지가 " + modifiedImagePath + "에 저장되었습니다.";
        } catch (IOException e) {
            e.printStackTrace();
            return "이미지 처리 중 예외가 발생했습니다.";
        }
    }

    // MultipartFile을 File로 변환하는 메서드
    private File convertMultipartFileToFile(MultipartFile multipartFile) throws IOException {
        try {
            File file = new File("C:/Users/kimkyoungwook/Desktop/보안 이미지", Objects.requireNonNull(multipartFile.getOriginalFilename()));
            multipartFile.transferTo(file);
            return file;
        }catch (IOException e){
            System.out.println(e);
            return null;
        }

    }

}
