package camunda.client;

import camunda.job.ActivatedJobs;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Component
public class RestZeebeClient {
    @Value("${CAMUNDA_CLIENT_ZEEBE_REST_ADDRESS}")
    private String zeebeRestAddress;

    @Value("${ZEEBE_TOKEN}")
    private String zeebeToken;

    private RestClient restClient;

    @PostConstruct
    public void buildRestClient() {
        restClient = RestClient.builder()
                .baseUrl(zeebeRestAddress)
                .defaultHeader("Authorization", "Bearer " + zeebeToken)
                .build();
    }

    public void startProcessInstance(Map<String, Object> requestBody) {
        restClient.post()
                .uri("/v2/process-instances")
                .body(requestBody)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });
    }

    public ActivatedJobs activateJobs(Map<String, Object> requestBody) {
        return restClient.post()
                .uri("/v2/jobs/activation")
                .body(requestBody)
                .retrieve()
                .body(ActivatedJobs.class);
    }

    public void completeJob(Long jobKey, Map<String, Object> requestBody) {
        restClient.post()
                .uri("/v2/jobs/{jobKey}/completion", jobKey)
                .body(requestBody)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });
    }

    public void publishMessage(Map<String, Object> requestBody) {
        restClient.post()
                .uri("/v2/messages/publication")
                .body(requestBody)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });
    }

    public void completeUserTask(Long userTaskKey, Map<String, Object> requestBody) {
        restClient.post()
                .uri("/v2/user-tasks/{userTaskKey}/completion", userTaskKey)
                .body(requestBody)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });
    }
}
