package CampingTrade.catbackend.review.service;

import CampingTrade.catbackend.review.component.FileStore;
import CampingTrade.catbackend.review.entity.Attachment;
import CampingTrade.catbackend.review.entity.Review;
import CampingTrade.catbackend.review.repository.AttachmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class AttachmentService {

    private final AttachmentRepository attachmentRepository;
    private final FileStore fileStore;

    public List<Attachment> saveAttachments(List<MultipartFile> multipartFileList) throws IOException {
        List<Attachment> imageFiles = fileStore.storeFiles(multipartFileList);
        List<Attachment> result = Stream.of(imageFiles)
                .flatMap(f -> f.stream())
                .collect(Collectors.toList());

        return result;
    }

    public List<Attachment> findAttachments() {
        List<Attachment> attachments = attachmentRepository.findAll();
        List<Attachment> result = attachments.stream()
                .collect(Collectors.toList());

        return result;
    }
}
