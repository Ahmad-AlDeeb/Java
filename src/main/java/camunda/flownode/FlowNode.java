package camunda.flownode;

public record FlowNode(
        long key,
        long processInstanceKey,
        long processDefinitionKey,
        String startDate,
        String flowNodeId,
        String flowNodeName,
        FlowNodeType type,
        FlowNodeState state,
        boolean incident,
        String tenantId
) {
}
