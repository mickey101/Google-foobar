import java.util.ArrayList;
import java.util.List;


public class Submitted {

    public static void main(String[] args) {

        int[][] sol = solution(6,3);

//        sol.forEach(p -> {
//            p.forEach(System.out::print);
//            System.out.println();
//        });

        for (int i = 0; i < sol.length; i++) {
            for (int j = 0; j < sol[i].length; j++) {
                System.out.print(sol[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static int[][] solution(int num_buns, int num_required) {
        // Your code here
        List<List<Integer>> result = new ArrayList<>();

        int[][] res = new int[num_buns][];

        List<int[]> spread = generate(num_buns,num_required);

        int total = spread.size() * num_required;
        int repeat = num_buns - num_required + 1;

        List<int[]> spread2 = generate(num_buns,repeat);

        for (int i = 0; i < num_buns; i++) {
            result.add(new ArrayList<>());
        }

        for (int i = 0; i < total/repeat ; i++) {

            int[] values = spread2.get(i);

            for (int value : values) {
                result.get(value).add(i);
            }
        }

        for (int i = 0; i < num_buns; i++) {
            res[i] = new int[result.get(i).size()];
        }

        for(int i=0; i< num_buns; i++){
            for (int j = 0; j < result.get(i).size(); j++) {
                res[i][j] = result.get(i).get(j);
            }
        }

        return res;
    }


    private static void helper(List<int[]> combinations, int data[], int start, int end, int index) {
        if (index == data.length) {
            int[] combination = data.clone();
            combinations.add(combination);
        } else {
            int max = Math.min(end, end + 1 - data.length + index);
            for (int i = start; i <= max; i++) {
                data[index] = i;
                helper(combinations, data, i + 1, end, index + 1);
            }
        }
    }
    private static List<int[]> generate(int n, int r) {
        List<int[]> combinations = new ArrayList<>();
        helper(combinations, new int[r], 0, n - 1, 0);
        return combinations;
    }
}

