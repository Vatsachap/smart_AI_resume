import com.shrivatsa.matcher.JobParser;
import com.shrivatsa.matcher.ResumeParser;

import java.util.List;

public class App {
 public static void main(String[] args) {
        String resumePath = "src/main/resources/resume.txt";
        String jobsPath = "src/main/resources/jobs.csv";

        String resumeText = ResumeParser.parseResume(resumePath);
        List<JobParser.Job> jobs = JobParser.loadJobs(jobsPath);

        System.out.println("📄 Matching jobs for resume...\n");

        List<JobParser.Job> topMatches = MatcherEngine.match(resumeText, jobs, 3);

        for (JobParser.Job job : topMatches) {
            System.out.println(job.toString());
        }
    }
}
