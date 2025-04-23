package camunda.client;

import camunda.flownode.FoundFlowNodes;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Component
public class OperateClient {
    @Value("${OPERATE_BASE_URL}")
    private String operateBaseUrl;

    @Value("${OPERATE_TOKEN}")
    private String operateToken;

    private RestClient restClient;

    @PostConstruct
    public void buildRestClient() {
        restClient = RestClient.builder()
                .baseUrl(operateBaseUrl)
                .defaultHeader("Authorization", "Bearer " + operateToken)
                .build();
    }

    public FoundFlowNodes searchFlowNodes(Map<String, Object> requestBody) {
        return restClient.post()
                .uri("/v1/flownode-instances/search")
                .body(requestBody)
                .retrieve()
                .body(FoundFlowNodes.class);
    }
}
