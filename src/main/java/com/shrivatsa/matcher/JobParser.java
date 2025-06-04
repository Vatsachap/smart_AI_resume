package com.shrivatsa.matcher;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import com.opencsv.CSVReader;

public class JobParser {
 public static class Job {
        public String id;
        public String title;
        public String description;

        public Job(String id, String title, String description) {
            this.id = id;
            this.title = title;
            this.description = description.toLowerCase().replaceAll("[^a-zA-Z0-9\\s]", "");
        }

        @Override
        public String toString() {
            return "[" + id + "] " + title;
        }
    }

    public static List<Job> loadJobs(String filePath) {
        List<Job> jobs = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            String[] line;
            reader.readNext(); // skip header
            while ((line = reader.readNext()) != null) {
                jobs.add(new Job(line[0], line[1], line[2]));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jobs;
    }
}
