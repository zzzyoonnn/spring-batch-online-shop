package com.batch.online_shop;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.JobExecution;
import org.springframework.batch.core.job.parameters.JobParameters;
import org.springframework.batch.core.job.parameters.JobParametersBuilder;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminBatchController {
  private static final int MAX_EXECUTIONS = 20;
  private static final DateTimeFormatter DATE_TIME_FORMATTER =
          DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

  private final JobOperator jobOperator;
  private final Job helloJob;
  private final JobRepository jobRepository;

  @GetMapping
  public String dashboard(Model model) {
    model.addAttribute("executions", recentExecutions());
    return "admin/dashboard";
  }

  @PostMapping("/jobs/hello")
  public String runHello() throws Exception {
    JobParameters jobParameters = new JobParametersBuilder()
            .addLong("runAt", System.currentTimeMillis())
            .toJobParameters();

    jobOperator.run(helloJob, jobParameters);
    return "redirect:/admin";
  }

  private List<ExecutionView> recentExecutions() {
    List<JobExecution> executions = new ArrayList<>();

    for (String jobName : jobRepository.getJobNames()) {
      jobRepository.getJobInstances(jobName, 0, MAX_EXECUTIONS)
              .forEach(instance -> executions.addAll(jobRepository.getJobExecutions(instance)));
    }

    return executions.stream()
            .sorted(Comparator.comparingLong(JobExecution::getId).reversed())
            .limit(MAX_EXECUTIONS)
            .map(ExecutionView::from)
            .toList();
  }

  public record ExecutionView(
          String jobName,
          String status,
          String statusClass,
          String startTime,
          String endTime) {

    private static ExecutionView from(JobExecution execution) {
      String status = execution.getStatus().name();
      String statusClass = switch (status) {
        case "COMPLETED" -> "status-completed";
        case "FAILED", "ABANDONED" -> "status-failed";
        default -> "status-running";
      };

      return new ExecutionView(
              execution.getJobInstance().getJobName(),
              status,
              statusClass,
              format(execution.getStartTime()),
              format(execution.getEndTime()));
    }

    private static String format(LocalDateTime value) {
      return value == null ? "-" : value.format(DATE_TIME_FORMATTER);
    }
  }
}
