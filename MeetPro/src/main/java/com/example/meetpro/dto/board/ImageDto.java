package com.example.meetpro.dto.board;

import lombok.AllArgsConstructor;
import lombok.Data;
import com.example.meetpro.entity.board.Image;

@Data
@AllArgsConstructor
public class ImageDto {

    private int id;
    private String originName;
    private String uniqueName;

    public static ImageDto toDto(Image image) {
        return new ImageDto(image.getId(), image.getOriginName(), image.getUniqueName());
    }
}
