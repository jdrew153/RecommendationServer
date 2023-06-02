package com.example.instaserverv3.recommendation;

import org.springframework.stereotype.Service;

import java.util.Arrays;


// planned to be used for inter profile similarity
@Service
public class EuclideanDistanceService {
    // formula sqrt(SUM((p_2 - p_1)^2 + (p_n+1 - p_n)^2))

    public float euclideanDistance(float[] arr1, float arr2[]) {
        System.out.println(Arrays.toString(arr1));
        System.out.println(Arrays.toString(arr2));
        if (arr1.length != arr2.length) {
            return (float) -1.0;
        }

        float sum = 0;
        int pointer = 0;

        while (pointer < arr1.length) {
            float diffSqrd = (float) Math.pow((arr2[pointer] - arr1[pointer]), 2);
            sum += diffSqrd;
            pointer++;
        }
        return (float) Math.pow(sum, 0.5);
    }
}
