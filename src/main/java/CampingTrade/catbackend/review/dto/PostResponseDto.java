package CampingTrade.catbackend.review.dto;

import CampingTrade.catbackend.review.entity.Post;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class PostResponseDto {

    private Long postId;
    private String title;
    private String writer;
    private String content;

    private String createdDate, modifiedDate;

    private Long memberId;
    private List<ReviewResponseDto> reviews;

    /* Entity -> Dto */
    public PostResponseDto(Post post) {
        this.postId = post.getPostId();
        this.title = post.getTitle();
        this.writer = post.getWriter();
        this.content = post.getContent();
        this.createdDate = post.getCreatedDate();
        this.modifiedDate = post.getModifiedDate();
        this.memberId = post.getMember().getId();
        this.reviews = post.getReviews().stream().map(ReviewResponseDto::new).collect(Collectors.toList());
    }

}
