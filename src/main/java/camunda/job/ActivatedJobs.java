package camunda.job;

import java.util.List;

public record ActivatedJobs(List<Job> jobs) {
}
