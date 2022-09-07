package CampingTrade.catbackend.review.dto;

import CampingTrade.catbackend.review.entity.Image;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ImageDto {

    private String originImageName;
    private String imageName;
    private String imagePath;

    public Image toEntity() {
        Image image = Image.builder()
                .originImageName(originImageName)
                .imageName(imageName)
                .imagePath(imagePath)
                .build();
        return image;
    }

    @Builder
    public ImageDto(String originImageName, String imageName, String imagePath) {
        this.originImageName = originImageName;
        this.imageName = imageName;
        this.imagePath = imagePath;
    }
}
