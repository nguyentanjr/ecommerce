package com.example.ecommerce;

import java.util.*;

public class method {
    public int subarraySum(int[] nums, int k) {
        int sum = 0, count = 0;
        for(int i = 0 ; i < nums.length; i++) {
            sum += nums[i];
            if(sum == k) {
                count++;
                if(i != nums.length - 1 && nums[i + 1] != nums[i])
                sum = nums[i];
                else sum = 0;
            }
        }
        return count;
        HashSet
    }
}
