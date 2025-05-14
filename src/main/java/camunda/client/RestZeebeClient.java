package camunda.client;

import camunda.entity.ActivatedJobs;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.time.Instant;
import java.util.Map;

@Component
public class RestZeebeClient {
    @Value("${CAMUNDA_CLIENT_AUTH_CLIENT_ID}")
    private String clientId;
    @Value("${CAMUNDA_CLIENT_AUTH_CLIENT_SECRET}")
    private String clientSecret;
    @Value("${CAMUNDA_CLIENT_ZEEBE_REST_ADDRESS}")
    private String zeebeRestAddress;
    private String zeebeToken;
    private Instant zeebeTokenExpiryTime;
    private static final String ZEEBE_AUTHORIZATION_SERVER_URL = "https://login.cloud.camunda.io/oauth/token";
    private static final String ZEEBE_TOKEN_AUDIENCE = "zeebe.camunda.io";
    private RestClient authClient;
    private RestClient zeebeClient;

    @PostConstruct
    public void buildRestClient() {
        authClient = RestClient.builder()
                .baseUrl(ZEEBE_AUTHORIZATION_SERVER_URL)
                .build();
        zeebeClient = RestClient.builder()
                .baseUrl(zeebeRestAddress)
                .build();
    }

    public void updateZeebeToken() {
        Map<String, Object> tokenResponse = authClient
                .post()
                .body(Map.of(
                        "client_id", clientId,
                        "client_secret", clientSecret,
                        "audience", ZEEBE_TOKEN_AUDIENCE))
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });

        zeebeToken = (String) tokenResponse.get("access_token");
        zeebeTokenExpiryTime = Instant.now().plusSeconds((Integer) tokenResponse.get("expires_in"));
    }

    public void checkAndUpdateToken() {
        if (zeebeToken == null || Instant.now().isAfter(zeebeTokenExpiryTime)) {
            updateZeebeToken();
        }
    }

    public void startProcessInstance(Map<String, Object> requestBody) {
        checkAndUpdateToken();
        zeebeClient.post()
                .uri("/v2/process-instances")
                .body(requestBody)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });
    }

    public ActivatedJobs activateJobs(Map<String, Object> requestBody) {
        checkAndUpdateToken();
        return zeebeClient.post()
                .uri("/v2/jobs/activation")
                .body(requestBody)
                .retrieve()
                .body(ActivatedJobs.class);
    }

    public void completeJob(Long jobKey, Map<String, Object> requestBody) {
        checkAndUpdateToken();
        zeebeClient.post()
                .uri("/v2/jobs/{jobKey}/completion", jobKey)
                .body(requestBody)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });
    }

    public void publishMessage(Map<String, Object> requestBody) {
        checkAndUpdateToken();
        zeebeClient.post()
                .uri("/v2/messages/publication")
                .body(requestBody)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });
    }

    public void completeUserTask(Long userTaskKey, Map<String, Object> requestBody) {
        checkAndUpdateToken();
        zeebeClient.post()
                .uri("/v2/user-tasks/{userTaskKey}/completion", userTaskKey)
                .body(requestBody)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });
    }
}
