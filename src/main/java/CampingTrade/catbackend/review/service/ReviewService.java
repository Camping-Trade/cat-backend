package CampingTrade.catbackend.review.service;

import CampingTrade.catbackend.member.entity.Member;
import CampingTrade.catbackend.member.repository.MemberRepository;
import CampingTrade.catbackend.oauth.service.AuthService;
import CampingTrade.catbackend.review.dto.ImageDto;
import CampingTrade.catbackend.review.dto.ReviewRequestDto;
import CampingTrade.catbackend.review.dto.ReviewResponseDto;
import CampingTrade.catbackend.review.entity.Image;
import CampingTrade.catbackend.review.entity.Review;
import CampingTrade.catbackend.review.repository.ImageRepository;
import CampingTrade.catbackend.review.repository.ReviewQuerydslRepository;
import CampingTrade.catbackend.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewQuerydslRepository reviewQuerydslRepository;
    private final MemberRepository memberRepository;
    private final AuthService authService;
    private final ImageRepository imageRepository;

    /* CREATE Review */
    @Transactional
    public void createReview(String token, Long campingId, ReviewRequestDto reviewRequestDto) {
        Long memberId = authService.getMemberId(token);
        Member member = memberRepository.findMemberById(memberId);

        MultipartFile multipartFile = reviewRequestDto.getImage();

        // 현재 시간
        LocalDateTime now = LocalDateTime.now();
        int year = now.getYear();
        int month = now.getMonthValue();
        int day = now.getDayOfMonth();
        int hour = now.getHour();
        int minute = now.getMinute();
        int second = now.getSecond();
        int millis = now.get(ChronoField.MILLI_OF_SECOND);

        // 폴더 및 파일명 생성
        String absolutePath = new File("${spring.servlet.multipart.location}").getAbsolutePath() + "/";
        String newFileName = "image" + hour + minute + second + millis;
        String fileExtension = '.' + multipartFile.getOriginalFilename().replaceAll("^.*₩₩.(.*)$", "$1");
        String path = "/images/local" + year + "/" + month + "/" + day;

        Image image = Image.builder().build();

        try {
            if (!multipartFile.isEmpty()) {
                File file = new File(absolutePath + path);

                if (!file.exists()) {
                    file.mkdirs();
                }

                file = new File(absolutePath + path + "/" + newFileName + fileExtension);
                multipartFile.transferTo(file);

                image = Image.builder()
                        .originImageName(multipartFile.getOriginalFilename())
                        .imageName(newFileName + fileExtension)
                        .imagePath(path)
                        .build();

                imageRepository.save(image);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        Review review = new Review(member,
                reviewRequestDto.getContent(),
                reviewRequestDto.getRating(),
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm")),
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy:MM.dd HH:mm")),
                campingId,
                image);

        reviewRepository.save(review);

    }

    /* UPDATE Review */
    @Transactional
    public void updateReview(String token, Long campingId, Long reviewId, ReviewRequestDto reviewRequestDto) {
        Long memberId = authService.getMemberId(token);
        Member member = memberRepository.findMemberById(memberId);


        if (member.getId() != reviewRepository.findById(reviewId).get().getWriter().getId()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "리뷰 작성자가 아닙니다.");
        }

        Review review = reviewQuerydslRepository.findByCampingIdAndReviewId(campingId, reviewId);
        if (review == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 리뷰가 존재하지 않습니다.");
        }

       reviewQuerydslRepository.updateReview(reviewRequestDto.getContent(),
               reviewRequestDto.getRating(), campingId, reviewId);

    }


    /* DELETE Review */
    @Transactional
    public void deleteReview(String token, Long campingId, Long reviewId) {
        Long memberId = authService.getMemberId(token);
        Member member = memberRepository.findMemberById(memberId);

        if (member.getId() != reviewRepository.findById(reviewId).get().getWriter().getId()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "리뷰 작성자가 아닙니다.");
        }

        Review review = reviewQuerydslRepository.findByCampingIdAndReviewId(campingId, reviewId);
        if (review == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 리뷰가 존재하지 않습니다.");
        }

        reviewRepository.delete(review);
    }

    /* 리뷰 목록 반환 */
    public List<ReviewResponseDto> getReviewList(Long campingId) {
        List<ReviewResponseDto> reviewList = reviewQuerydslRepository.findReviewListByCampingId(campingId);
        return reviewList;
    }

    @Transactional
    public void saveImage(MultipartFile multipartFile) {

        // 현재 시간
        LocalDateTime now = LocalDateTime.now();
        int year = now.getYear();
        int month = now.getMonthValue();
        int day = now.getDayOfMonth();
        int hour = now.getHour();
        int minute = now.getMinute();
        int second = now.getSecond();
        int millis = now.get(ChronoField.MILLI_OF_SECOND);

        // 폴더 및 파일명 생성
        String absolutePath = new File("${spring.servlet.multipart.location}").getAbsolutePath() + "/";
        String newFileName = "image" + hour + minute + second + millis;
        String fileExtension = '.' + multipartFile.getOriginalFilename().replaceAll("^.*₩₩.(.*)$", "$1");
        String path = "/images/local" + year + "/" + month + "/" + day;

        try {
            if (!multipartFile.isEmpty()) {
                File file = new File(absolutePath + path);

                if (!file.exists()) {
                    file.mkdirs();
                }

                file = new File(absolutePath + path + "/" + newFileName + fileExtension);
                multipartFile.transferTo(file);

                Image image = Image.builder()
                                .originImageName(multipartFile.getOriginalFilename())
                                .imageName(newFileName + fileExtension)
                                .imagePath(path)
                                .build();

                imageRepository.save(image);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
