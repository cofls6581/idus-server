package com.example.demo.src.user;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.example.demo.config.BaseException;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import static com.example.demo.config.BaseResponseStatus.INVALID_TOKEN;

@Service
public class kakao{

    public long KakaoUserId (String token) throws BaseException {

        String access_Token = token;

        HashMap<String, Object> User = new HashMap<>();
        String reqURL = "https://kapi.kakao.com/v2/user/me";
        long userId=0;

        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");

            conn.setRequestProperty("Authorization", "Bearer " + access_Token);

            int responseCode = conn.getResponseCode();
            if(responseCode!=200){
                throw new BaseException(INVALID_TOKEN);
            }
            System.out.println("responseCode : " + responseCode);
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));


            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("response body : " + result);

            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            userId= element.getAsJsonObject().get("id").getAsLong();

            System.out.println(userId);
        }catch (IOException e) {
            e.printStackTrace();
        }

        return userId;
    }
}
