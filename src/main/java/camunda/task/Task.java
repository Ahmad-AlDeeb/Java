package camunda.task;

import java.time.ZonedDateTime;
import java.util.List;

public record Task(
        String id,
        String name,
        String taskDefinitionId,
        String processName,
        ZonedDateTime creationDate,
        ZonedDateTime completionDate,
        String assignee,
        TaskState taskState,
        List<String> sortValues,
        boolean isFirst,
        String formKey,
        String formId,
        int formVersion,
        Boolean isFormEmbedded,
        String processDefinitionKey,
        String processInstanceKey,
        String tenantId,
        ZonedDateTime dueDate,
        ZonedDateTime followUpDate,
        List<String> candidateGroups,
        List<String> candidateUsers,
        List<Object> variables,
        Object context,
        String implementation,
        int priority
) {
}
