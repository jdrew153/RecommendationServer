package com.example.instaserverv3.recommendation;

import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.Vector;
import java.util.stream.IntStream;

@Service
public class VectorService {

    public Vector<Integer> createAttributeRankingVector(Integer vectorSize, int[] rankings) {
        Vector<Integer> rankingVec = new Vector<Integer>(vectorSize);

        for (int i=0; i < rankings.length; i++) {
            rankingVec.add(i);
        }

        return rankingVec;
    }

    // dot product formula --> (x1 * y1) + (x2 * y2)
    public Integer dotProduct(Vector<Integer> userRankingVec, Vector<Integer> postRankingVec) {
        System.out.println(userRankingVec.size());
        System.out.println(postRankingVec.size());
        if (userRankingVec.size() != postRankingVec.size()) {
            return 0;
        }
        int pointer = 0;
        int sum = 0;

        while (pointer < userRankingVec.size()) {
            Integer x = userRankingVec.get(pointer);
            Integer y = postRankingVec.get(pointer);

            sum = sum + (x * y);
            pointer++;
        }
        System.out.println(sum);
        return sum;
    }

    public double sqrtSum(Vector<Integer> vec) {
        System.out.println(vec);
        int size = vec.size();
        double[] sqrdNums = new double[size];
        double sum = 0.0;
        int pointer = 0;
        for (int i =0; i < vec.size(); i++) {
            sqrdNums[i] = (vec.get(i) * vec.get(i));
        }

        while (pointer < sqrdNums.length) {
            sum = sum + sqrdNums[pointer];
            pointer++;
        }
        System.out.println(sum);
        return Math.pow(sum, 0.5);
    }

    public double cosineSimilarity(Vector<Integer> userRankingVec, Vector<Integer> postRankingVec) {
        int dotProduct = dotProduct(userRankingVec, postRankingVec);

        double sum1 = sqrtSum(userRankingVec);
        double sum2 = sqrtSum(postRankingVec);

        return (dotProduct / (sum1 * sum2));
    }
}
