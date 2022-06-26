package CampingTrade.catbackend.controller;

import CampingTrade.catbackend.service.OAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/oauth")
public class OAuthController {

    @Autowired
    private OAuthService oauth;

    @GetMapping("/kakao")
    @ResponseBody
    public void kakaoCallback(@RequestParam String code) {
        String access_Token = oauth.getKakaoAccessToken(code);
        System.out.println("code: " + code);
        System.out.println("Controller access_token: " + access_Token);
    }
}
