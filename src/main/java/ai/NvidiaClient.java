package ai;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Component
public class NvidiaClient implements AiClient {
    @Value("${NVIDIA_API_URL}")
    private String nvidiaApiUrl;

    @Value("${NVIDIA_API_KEY}")
    private String nvidiaApiKey;

    private RestClient restClient;
    private static final String MODEL = "qwen/qwen2.5-coder-32b-instruct";

    @PostConstruct
    @Override
    public void buildRestClient() {
        restClient = RestClient.builder()
                .baseUrl(nvidiaApiUrl)
                .defaultHeader("Authorization", "Bearer " + nvidiaApiKey)
                .defaultHeader("Accept", APPLICATION_JSON_VALUE)
                .defaultHeader("Content-Type", APPLICATION_JSON_VALUE)
                .build();
    }

    @Override
    public String sendPromptToAi(String prompt) {
        NvidiaResponse response = restClient.post()
                .body(Map.of(
                        "model", MODEL,
                        "messages", List.of(Map.of("role", "user", "content", prompt)),
                        "temperature", 0.2,
                        "top_p", 0.7,
                        "max_tokens", 1024,
                        "seed", 42,
                        "stream", false,
                        "stop", List.of("string")
                ))
                .retrieve()
                .body(NvidiaResponse.class);

        return response.choices().getFirst().message().content();
    }
}
