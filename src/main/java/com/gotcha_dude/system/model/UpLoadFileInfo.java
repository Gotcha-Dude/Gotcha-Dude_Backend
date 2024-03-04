package com.gotcha_dude.system.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpLoadFileInfo {
    String originalName;
    String fileId;
    String extension;
    long size;
}
