package camunda.handler;

import camunda.RequestBodyBuilder;
import camunda.client.RestZeebeClient;
import camunda.client.TasklistClient;
import camunda.task.Task;
import camunda.task.TaskState;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import service.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import static java.lang.Long.parseLong;

@Component
public class UserTaskHandler {
    private final RestZeebeClient restZeebeClient;
    private final TasklistClient tasklist;
    private final Service service;

    public UserTaskHandler(RestZeebeClient restZeebeClient, TasklistClient tasklist, Service service) {
        this.restZeebeClient = restZeebeClient;
        this.tasklist = tasklist;
        this.service = service;
    }

    @Async
    public void registerWorkers() {
        completeUserTask();
    }

    @Async
    public void completeUserTask() {
        final String taskDefinitionId = "Activity_DoUserTask";
        Map<String, Object> requestBody = RequestBodyBuilder.searchTasks(taskDefinitionId);

        Set<String> completedTasks = new HashSet<>();
        while (true) {
            List<Task> tasks = tasklist.searchTasks(requestBody);

            for (Task task : tasks) {
                if (completedTasks.contains(task.id())
                        || task.taskState() != TaskState.CREATED
                        || !Objects.equals(task.taskDefinitionId(), taskDefinitionId)) {
                    continue;
                }
                completedTasks.add(task.id());

                var var = service.doUserTask();

                requestBody = RequestBodyBuilder.completeJob(Map.of("var", var));
                restZeebeClient.completeUserTask(parseLong(task.id()), requestBody);
            }
        }
    }
}
