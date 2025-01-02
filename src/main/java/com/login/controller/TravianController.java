package com.login.controller;

import com.login.dto.ApiResponse;
import com.login.dto.TkDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.MalformedURLException;
import java.net.URL;

@RestController
public class TravianController {

    private final WebClient.Builder builder;

    @Autowired
    public TravianController(WebClient.Builder builder){
        this.builder = builder;
    }

    @PostMapping("/travian")
    public ResponseEntity<ApiResponse> tkRequest(@RequestBody String serverUrl){
        try {
            new URL(serverUrl);
            String apiUrl = serverUrl + "/api/external.php?action=requestApiKey";
            TkDto tkDto = new TkDto("vuolo.vince@outlook.it", "prova-angular-render",
                    "https://prova-angular-render.onrender.com", true);
            WebClient webClient = builder.baseUrl(apiUrl).build();
            String response = webClient.post()
                    .bodyValue(tkDto)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
	    System.out.println("Risposta dal servizio esterno: " + response);
            return ResponseEntity.ok(new ApiResponse(response, true));
        }
        catch (MalformedURLException e){
            return ResponseEntity.badRequest().body(new ApiResponse("URL non valido", false));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage(), false));
        }
    }
}
