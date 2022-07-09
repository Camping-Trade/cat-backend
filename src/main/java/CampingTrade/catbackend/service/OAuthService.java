package CampingTrade.catbackend.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Service
public class OAuthService {

    //카카오 로그인 API: 토큰 받아오기
    public String getKakaoAccessToken (String code) throws IOException {
        String access_Token = "";
        String refresh_Token = "";
        String reqURL = "https://kauth.kakao.com/oauth/token";

        URL url = new URL(reqURL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        try {

            //POST 요청을 위해 기본값이 false인 setDoOutPut을 true로 설정
            conn.setRequestMethod("POST");
            conn.setDoOutput(true); //데이터 기록 알려주기

            //POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");
            sb.append("&client_id=ee2936eeef64e735558ae4789db86115");
            sb.append("&redirect_uri=http://localhost:8080/oauth/kakao");
            sb.append("&code=" + code);

            bw.write(sb.toString());
            bw.flush();

            //결과 코드 == 200 -> 성공
            //결과 코드가 400번대 -> 실패
            int responseCode = conn.getResponseCode();
            System.out.println("responseCode: " + responseCode);

            //요청을 통해 얻은 JSON 타입의 Response 메세지 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("response body: " + result);

            //Gson 라이브러리에 포함된 클래스로 JSON 파싱 객체 생성
            JSONParser parser = new JSONParser();
            JSONObject element = (JSONObject) parser.parse(result);

            access_Token = element.get("access_token").toString();
            refresh_Token = element.get("refresh_token").toString();

            System.out.println("access_token: " + access_Token);
            System.out.println("refresh_token: " + refresh_Token);

            br.close();
            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return access_Token;
    }

    //발급받은 토큰으로 회원 정보 가져오기
    //이메일로 가입하지 않을 수도 있고, 유저마다 정보 제공 동의 여부가 다르기 때문에 HashMap타입으로 받음
    public Map<String, Object> getKakaoUserInfo (String access_token) throws IOException {
        Map<String, Object> userInfo = new HashMap<>();
        String host = "https://kapi.kakao.com/v2/user/me";

        try {
            URL url = new URL(host);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestProperty("Authorization", "Bearer " + access_token); //전송할 Header 내용(토큰 포함)
            conn.setRequestMethod("GET");

            //결과 코드 == 200 -> 성공
            int responseCode = conn.getResponseCode();
            System.out.println("responseCode: " + responseCode);

            //요청을 통해 얻은 JSON 타입의 Response 메세지 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("response body: " + result);

            //JSON 파싱
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            String id = element.getAsJsonObject().get("id").getAsString();

            boolean hasEmail = element.getAsJsonObject().get("kakao_account")
                            .getAsJsonObject().get("has_email").getAsBoolean();
            boolean emailAgreement = element.getAsJsonObject().get("kakao_account")
                            .getAsJsonObject().get("email_needs_agreement").getAsBoolean();

            String email = "";
            if (hasEmail && !emailAgreement) {
                email = element.getAsJsonObject().get("kakao_account")
                        .getAsJsonObject().get("email").getAsString();
            }

            userInfo.put("id", id);
            userInfo.put("email", email);

            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return userInfo;
    }


    //동의 내역 확인하기
    public String getAgreementKakaoInfo (String access_token) {
        String host = "https://kapi.kakao.com/v2/user/scopes";
        String result = "";

        try {
            URL url = new URL(host);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", "Bearer " + access_token);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }

            int responseCode = conn.getResponseCode();
            System.out.println("responseCode: " + responseCode);

            //result <- Json 포맷
            br.close();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

}
