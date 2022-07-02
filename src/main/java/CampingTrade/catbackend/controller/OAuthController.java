package CampingTrade.catbackend.controller;

import CampingTrade.catbackend.service.OAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/oauth")
public class OAuthController {

    @Autowired
    private OAuthService oauth;

    @GetMapping("/login")
    public String loginPage() {
        return "Oauth/kakaoLogin";
    }

    @GetMapping("/kakao")
    //@ResponseBody
    public String kakaoCallback(@RequestParam String code, Model model)  throws IOException {
        System.out.println("code = " + code);
        String access_token = oauth.getKakaoAccessToken(code);

        Map<String, Object> userInfo = oauth.getKakaoUserInfo(access_token);
        String agreementInfo = oauth.getAgreementKakaoInfo(access_token);

        model.addAttribute("code", code);
        model.addAttribute("access_token", access_token);
        model.addAttribute("userInfo", userInfo);
        model.addAttribute("agreementInfo", agreementInfo);

        return "list";

        /*
        //클라이언트의 이메일이 존재할 때, 세션에 해당 이메일과 토큰 등록
        if (userInfo.get("email") != "") {
            session.setAttribute("userId", userInfo.get("email"));
            session.setAttribute("access_Token", access_Token);
        }
         */
    }
}
