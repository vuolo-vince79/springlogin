package com.login.controller;

import com.login.dto.ApiResponse;
import com.login.dto.TkDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.reactive.function.client.WebClientAutoConfiguration;
import org.springframework.boot.autoconfigure.web.reactive.function.client.WebClientCodecCustomizer;
import org.springframework.boot.autoconfigure.web.reactive.function.client.WebClientSsl;
import org.springframework.boot.web.reactive.function.client.WebClientCustomizer;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@RestController
public class TravianController {

    private final WebClient.Builder builder;

    @Autowired
    public TravianController(WebClient.Builder builder){
        this.builder = builder;
    }

    @PostMapping("/travian")
    public ResponseEntity<ApiResponse> tkRequest(){
        String apiUrl = "https://cz4.kingdoms.com/api/external.php?action=requestApiKey";
        TkDto tkDto = new TkDto("vuolo.vince@outlook.it", "prova-angular-render",
                "https://prova-angular-render.onrender.com", true);
        WebClient webClient = builder.baseUrl(apiUrl).build();
        try {
            String response = webClient.post()
                    .bodyValue(tkDto)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
            return ResponseEntity.ok(new ApiResponse(response, true));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage(), false));
        }
    }
}
