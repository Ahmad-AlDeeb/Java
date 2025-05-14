package ai;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Component
public class RequestyClient implements AiClient {
    @Value("${REQUESTY_API_URL}")
    private String requestyApiUrl;

    @Value("${REQUESTY_API_KEY}")
    private String requestyApiKey;

    private RestClient restClient;
    private static final String MODEL = "openai/chatgpt-4o-latest";

    @PostConstruct
    @Override
    public void buildRestClient() {
        restClient = RestClient.builder()
                .baseUrl(requestyApiUrl)
                .defaultHeader("Authorization", "Bearer " + requestyApiKey)
                .defaultHeader("Content-Type", APPLICATION_JSON_VALUE)
                .build();
    }

    @Override
    public String sendPromptToAi(String prompt) {
        RequestyResponse response = restClient.post()
                .body(Map.of(
                        "model", MODEL,
                        "messages", List.of(Map.of("role", "user", "content", prompt))
                ))
                .retrieve()
                .body(RequestyResponse.class);

        return response.choices().getFirst().message().content();
    }
}
