package client;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
public class QwenClient {
    @Value("${QWEN_API_URL}")
    private String qwenApiUrl;

    @Value("${QWEN_API_KEY}")
    private String qwenApiKey;

    private RestClient restClient;

    @PostConstruct
    public void init() {
        restClient = RestClient.builder()
                .baseUrl(qwenApiUrl)
                .defaultHeader("Authorization", "Bearer " + qwenApiKey)
                .defaultHeader("Accept", "application/json")
                .defaultHeader("Content-Type", "application/json")
                .build();
    }

    public String getResponse(String prompt) {
        LinkedHashMap response = restClient.post()
                .body(Map.of(
                        "model", "qwen/qwen2.5-coder-32b-instruct",
                        "messages", List.of(Map.of("role", "user", "content", prompt)),
                        "temperature", 0.2,
                        "top_p", 0.7,
                        "max_tokens", 1024,
                        "seed", 42,
                        "stream", false,
                        "stop", List.of("string")
                ))
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });

        var choices = (List<Map<String, Object>>) response.get("choices");
        var message = (Map<String, Object>) choices.get(0).get("message");
        return (String) message.get("content");
    }
}
