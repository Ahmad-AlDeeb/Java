package client;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Component
public class WhatsappClient {
    @Value("${WHATSAPP_API_URL}")
    private String whatsappApiUrl;

    @Value("${WHATSAPP_TOKEN}")
    private String whatsappToken;

    @Value("${WHATSAPP_RECEIVED_PHONE_NUMBER}")
    private String whatsappReceivedPhoneNumber;

    private Map<String, Object> requestBody;
    private RestClient restClient;

    @PostConstruct
    public void buildRestClient() {
        restClient = RestClient.builder()
                .baseUrl(whatsappApiUrl)
                .defaultHeader("Authorization", "Bearer " + whatsappToken)
                .defaultHeader("Content-Type", "application/json")
                .build();

        requestBody = Map.of(
                "messaging_product", "whatsapp",
                "to", whatsappReceivedPhoneNumber,
                "type", "template",
                "template", Map.of(
                        "name", "hello_world",
                        "language", Map.of(
                                "code", "en_US"
                        )
                )
        );
    }

    public void sendMessage() {
        restClient.post()
                .uri("/messages")
                .body(requestBody)
                .retrieve()
                .body(String.class);
    }
}
