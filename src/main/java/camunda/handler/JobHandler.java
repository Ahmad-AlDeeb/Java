package camunda.handler;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import org.springframework.stereotype.Component;
import service.Service;

@Component
public class JobHandler {
    private final Service service;

    public JobHandler(Service service) {
        this.service = service;
    }

    @JobWorker(type = "CompleteJob")
    public void completeJob(JobClient client, ActivatedJob job) {
        service.completeJob();
    }

    @JobWorker(type = "CompleteJobWithVariables")
    public void completeJobWithVariables(JobClient client, ActivatedJob job) {
        String var = (String) job.getVariable("var");
        service.completeJobWithVariables(var);

        client.newCompleteCommand(job.getKey())
                .variable("var", var)
                .send()
                .join();
    }
}