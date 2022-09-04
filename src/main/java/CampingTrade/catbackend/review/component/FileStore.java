package CampingTrade.catbackend.review.component;

import CampingTrade.catbackend.review.entity.Attachment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class FileStore {

    @Value("${spring.servlet.multipart.location}")
    private String fileDirPath;

    // 전체 파일 저장
    public List<Attachment> storeFiles(List<MultipartFile> multipartFiles) throws IOException {
        List<Attachment> attachments = new ArrayList<>();

        for (MultipartFile multipartFile : multipartFiles) {
            if (!multipartFile.isEmpty()) {
                attachments.add(storeFile(multipartFile));
            }
        }

        return attachments;
    }


    // 파일 저장 로직
    public Attachment storeFile(MultipartFile multipartFile) throws IOException {
        if (multipartFile.isEmpty()) {
            return null;
        }

        String originalFileName = multipartFile.getOriginalFilename();
        String storeFileName = createStoreFileName(originalFileName);
        multipartFile.transferTo(new File(createPath(storeFileName)));

        return Attachment.builder()
                .originFileName(originalFileName)
                .storePath(storeFileName)
                .build();
    }

    // 파일 경로 구성
    public String createPath(String storeFileName) {
        String viaPath = "images/";

        return fileDirPath + viaPath + storeFileName;
    }


    // 저장할 파일 이름 구성
    private String createStoreFileName(String originalFileName) {
        String uuid = UUID.randomUUID().toString();
        String ext = extractExt(originalFileName);
        String storeFileName = uuid + ext;

        return storeFileName;
    }

    // 확장자 추출
    private String extractExt(String originalFileName) {
        int idx = originalFileName.lastIndexOf(".");
        String ext = originalFileName.substring(idx);

        return ext;
    }

}
