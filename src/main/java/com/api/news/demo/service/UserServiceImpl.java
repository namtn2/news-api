package com.api.news.demo.service;

import com.api.news.demo.dto.ResultDTO;
import com.api.news.demo.model.LogType;
import com.api.news.demo.model.User;
import com.api.news.demo.repository.UserRepository;
import com.api.news.demo.utils.Constants;
import com.api.news.demo.utils.StringUtils;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;

@Transactional
@Service
@Log4j
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    LogService logService;

    @Value("${security.oauth2.client.clientId:null}")
    private String clientId;

    @Value("${google.captcha.siteVerify:null}")
    private String siteVerify;

    @Value("${google.captcha.secretKey:null}")
    private String secretKey;

    @Override
    public ResultDTO login(User user) {
        ResultDTO resultDTO = userRepository.login(user);
        if (Constants.RESULT.FAIL.equals(resultDTO.getKey())) {
            logService.save(resultDTO, 0L, LogType.LOGIN);
            return resultDTO;
        }
        logService.save(resultDTO, ((User) resultDTO.getObject()).getId(), LogType.LOGIN);
        return resultDTO;
    }

    @Override
    public ResultDTO createOrUpdateUser(User user) {
        ResultDTO resultDTO = userRepository.createOrUpdateUser(user);
        if (Constants.RESULT.FAIL.equals(resultDTO.getKey())) {
            logService.save(resultDTO, 0L, user.getId() == null ? LogType.REGISTER : LogType.LOGIN);
            return resultDTO;
        }
        logService.save(resultDTO, ((User) resultDTO.getObject()).getId(), user.getId() == null ? LogType.REGISTER : LogType.LOGIN);
        return resultDTO;
    }

    public ResultDTO getUserGoogle(String idTokenString) {
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), JacksonFactory.getDefaultInstance())
                .setAudience(Collections.singletonList(clientId))
                .build();

        ResultDTO resultDTO;
        try {
            GoogleIdToken idToken = verifier.verify(idTokenString);
            if (idToken != null) {
                Payload payload = idToken.getPayload();

                String email = payload.getEmail();
                Object name = payload.get("name") != null ? payload.get("name") : (payload.get("family_name") != null ? payload.get("family_name") : payload.get("given_name"));

                User u = new User();
                u.setEmail(email);
                u.setName(name == null ? email : (String) name);
                resultDTO = userRepository.searchUser(u);
                if (resultDTO != null && resultDTO.getObject() == null) {
                    resultDTO.setObject(u);
                }
            } else {
                return new ResultDTO();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultDTO();
        }
        return resultDTO;
    }

    @Override
    public ResultDTO loginGoogle(String token) {
        ResultDTO resultDTO = getUserGoogle(token);
        if (Constants.RESULT.FAIL.equals(resultDTO.getKey())) {
            return resultDTO;
        }
        resultDTO = userRepository.createOrUpdateUser((User) resultDTO.getObject());
        logService.save(resultDTO, ((User) resultDTO.getObject()).getId(), LogType.LOGIN_WITH_GOOGLE);
        return resultDTO;
    }

    @Override
    public ResultDTO registerGoogle(String token) {
        ResultDTO resultDTO = getUserGoogle(token);
        logService.save(resultDTO, ((User) resultDTO.getObject()).getId(), LogType.REGISTER_WITH_GOOGLE);
        return resultDTO;
    }

    @Override
    public ResultDTO findUserById(Long id) {
        if (StringUtils.isLongNullOrZero(id)) {
            return new ResultDTO();
        }
        return userRepository.findUserById(id);
    }

    @Override
    public ResultDTO validateCaptcha(String token) {
        ResultDTO resultDTO = new ResultDTO();
        if (token == null || "".equals(token)) {
            return resultDTO;
        }
        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();

            UriComponentsBuilder builder = UriComponentsBuilder
                    .fromHttpUrl(siteVerify).queryParam("secret", secretKey)
                    .queryParam("response", token);
            HttpEntity<Object> entity = new HttpEntity<>(headers);
            ResponseEntity<Object> response = restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.GET, entity, Object.class);
            LinkedHashMap linkedHashMap = ((LinkedHashMap) response.getBody());

            boolean b = (boolean) linkedHashMap.get("success");
            resultDTO.setKey(b ? Constants.RESULT.SUCCESS : Constants.RESULT.FAIL);

            if (!b) {
                String errorCode = (String) ((ArrayList) linkedHashMap.get("error-codes")).get(0);
                resultDTO.setMessage("Google Captcha Message: " + errorCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultDTO();
        }
        return resultDTO;
    }
}
