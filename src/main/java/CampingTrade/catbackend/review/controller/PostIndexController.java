package CampingTrade.catbackend.review.controller;

import CampingTrade.catbackend.common.payload.ApiResponse;
import CampingTrade.catbackend.review.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class PostIndexController {

    /**
     * 화면 연결 Controller
     */

    private final PostService postService;

    /* 글 상세보기 */
    @GetMapping("/post/details/{id}")
    public ResponseEntity<Void> read(@PathVariable Long id, HttpServletRequest request) {

        return ApiResponse.success(null);

    }


}
