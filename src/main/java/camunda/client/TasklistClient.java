package camunda.client;

import camunda.task.Task;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;

@Component
public class TasklistClient {

    @Value("${TASKLIST_BASE_URL}")
    private String tasklistBaseUrl;

    @Value("${TASKLIST_TOKEN}")
    private String tasklistToken;

    private RestClient restClient;

    @PostConstruct
    public void buildRestClient() {
        restClient = RestClient.builder()
                .baseUrl(tasklistBaseUrl)
                .defaultHeader("Authorization", "Bearer " + tasklistToken)
                .build();
    }

    public List<Task> searchTasks(Map<String, Object> requestBody) {
        return restClient.post()
                .uri("/v1/tasks/search")
                .body(requestBody)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });
    }
}