package camunda;

import camunda.handler.UserTaskHandler;
import io.camunda.zeebe.client.ZeebeClient;
import org.springframework.stereotype.Component;

@Component
public class Zeebe {
    private final ZeebeClient client;
    private static final String PROCESS_ID = "Process_Processing";

    public Zeebe(ZeebeClient client, UserTaskHandler userTaskHandler) {
        this.client = client;
        userTaskHandler.registerWorkers();
    }

    public void startProcessInstance() {
        client.newCreateInstanceCommand()
                .bpmnProcessId(PROCESS_ID)
                .latestVersion()
                .send()
                .join();
    }
}
