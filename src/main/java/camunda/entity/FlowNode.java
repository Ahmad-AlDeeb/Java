package camunda.entity;

import camunda.enums.FlowNodeState;
import camunda.enums.FlowNodeType;

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
