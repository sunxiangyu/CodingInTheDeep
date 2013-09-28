public class Solution {
    public int maxProfit(int[] prices) {
        // Start typing your Java solution below
        // DO NOT write main() function
        int len = prices.length;
        if (len <= 1){
            return 0;
        }
        
        int min = prices[0];
        int maxProfit = 0;
        int profit = 0;
        for(int i = 0; i < len; i++){
            int curr = prices[i];
            if (curr > min){
                profit = curr- min;
                maxProfit = Math.max(profit, maxProfit);
            }
            else if (curr < min){
                min = curr;
            }
        }
        
        return maxProfit;
        
    }
}

// #2
public class Solution {
    public int maxProfit(int[] prices) {
        // Start typing your Java solution below
        // DO NOT write main() function
        if (prices == null || prices.length <= 1)
            return 0;
        int min = prices[0];
        int maxProf = 0;
        // error in case [2,1]: int maxProf = Integer.MIN_VALUE;
        for (int i = 1; i < prices.length; i++){
            if (prices[i] > min)
                maxProf = Math.max(maxProf, prices[i] - min);
            else
                min = prices[i];
        }
        return maxProf;
    }
}