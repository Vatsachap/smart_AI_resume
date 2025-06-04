package com.shrivatsa.matcher;
import java.util.*;
import java.util.stream.Collectors;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVector;
import com.shrivatsa.parser.JobParser;

public class MatcherEngine {

    // Tokenizes text into lowercase words
    public static List<String> tokenize(String text) {
        return Arrays.asList(text.toLowerCase().split("\\s+"));
    }

    // Converts tokens to a frequency vector
    public static RealVector toVector(List<String> tokens, Set<String> vocabulary) {
        double[] vector = new double[vocabulary.size()];
        Map<String, Integer> tokenFreq = new HashMap<>();
        for (String token : tokens) {
            tokenFreq.put(token, tokenFreq.getOrDefault(token, 0) + 1);
        }

        int i = 0;
        for (String word : vocabulary) {
            vector[i++] = tokenFreq.getOrDefault(word, 0);
        }

        return new ArrayRealVector(vector);
    }

    // Computes cosine similarity
    public static double cosineSimilarity(RealVector v1, RealVector v2) {
        double dotProduct = v1.dotProduct(v2);
        double normProduct = v1.getNorm() * v2.getNorm();
        return normProduct == 0 ? 0.0 : dotProduct / normProduct;
    }

    // Matches resume against jobs and returns top N jobs
    public static List<JobParser.Job> match(String resumeText, List<JobParser.Job> jobs, int topN) {
        List<String> resumeTokens = tokenize(resumeText);
        Set<String> vocabulary = new HashSet<>(resumeTokens);
        for (JobParser.Job job : jobs) {
            vocabulary.addAll(tokenize(job.description));
        }

        RealVector resumeVector = toVector(resumeTokens, vocabulary);

        Map<JobParser.Job, Double> scoreMap = new HashMap<>();
        for (JobParser.Job job : jobs) {
            RealVector jobVector = toVector(tokenize(job.description), vocabulary);
            double score = cosineSimilarity(resumeVector, jobVector);
            scoreMap.put(job, score);
        }

        return scoreMap.entrySet()
                .stream()
                .sorted((e1, e2) -> -Double.compare(e1.getValue(), e2.getValue()))
                .limit(topN)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
}
