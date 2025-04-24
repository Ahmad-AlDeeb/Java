package camunda.entity;

import java.util.Map;

public record Job(
        long jobKey,
        long processInstanceKey,
        long processDefinitionKey,
        long elementInstanceKey,
        String type,
        String processDefinitionId,
        int processDefinitionVersion,
        String elementId,
        Map<String, Object> customHeaders,
        String worker,
        int retries,
        long deadline,
        Map<String, Object> variables,
        String tenantId
) {
}
