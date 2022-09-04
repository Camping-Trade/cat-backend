package CampingTrade.catbackend.review.controller;

import CampingTrade.catbackend.common.dto.ApiResponse;
import CampingTrade.catbackend.oauth.util.JwtHeaderUtil;
import CampingTrade.catbackend.review.dto.ReviewRequestDto;
import CampingTrade.catbackend.review.dto.UploadFileResponse;
import CampingTrade.catbackend.review.service.ReviewService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import net.coobird.thumbnailator.Thumbnailator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/camping")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class ReviewController {

    @Value("${spring.servlet.multipart.location}")
    private String uploadPath;
    private final ReviewService reviewService;



    /*
    //Image Upload
    @PostMapping("/details/{id}/reviews/uploadFile")
    public ResponseEntity<List<UploadFileResponse>> uploadFile(MultipartFile[] uploadFiles) {

        List<UploadFileResponse> fileResponseList = new ArrayList<>();

        for (MultipartFile uploadFile : uploadFiles) {

            // 이미지 파일만 업로드 가능
            if (uploadFile.getContentType().startsWith("image") == false) {
                return ApiResponse.forbidden(null);
            }

            // 실제 파일 이름 IE나 Edge는 전체 경로가 들어옴
            String originalName = uploadFile.getOriginalFilename();
            String fileName = originalName.substring(originalName.lastIndexOf("\\") + 1);

            // 날짜 폴더 생성
            String folderPath = makeFolder();

            // UUID
            String uuid = UUID.randomUUID().toString();

            String saveName = uploadPath + File.separator + folderPath + File.separator + uuid + "_" + fileName;
            Path savePath = Paths.get(saveName);

            try {
                uploadFile.transferTo(savePath); // 실제 이미지 저장 (원본 파일)

                // 썸네일 생성: th_로 시작
                String thumbnailName = uploadPath + File.separator + folderPath + File.separator
                        + "th_" + uuid + "_" + fileName;
                File thumbnailFile = new File(thumbnailName);
                Thumbnailator.createThumbnail(savePath.toFile(), thumbnailFile, 100, 100);

                fileResponseList.add(new UploadFileResponse(fileName, uuid, folderPath));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return ApiResponse.success(fileResponseList);
    }

    @PostMapping("/details/{id}/reviews/deleteFile")
    public ResponseEntity<Void> deleteFile(String fileName) {
        String srcFileName = null;

        try {
            srcFileName = URLDecoder.decode(fileName, "UTF-8");
            File file = new File(uploadPath + File.separator + srcFileName);

            boolean result = file.delete();

            File thumbnail = new File(file.getParent(), "th_" + file.getName());

            result = thumbnail.delete();

            if (result == true) {
                return ApiResponse.success(null);
            }
            else {
                return ApiResponse.forbidden(null);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return ApiResponse.internalServerError(null);
        }
    }

    private String makeFolder() {

        String str = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        String folderPath = str.replace("/", File.separator);

        // 폴더 생성
        File uploadFolder = new File(uploadPath, folderPath);

        if (uploadFolder.exists() == false) {
            uploadFolder.mkdirs();
        }

        return folderPath;
    }
    */


}
