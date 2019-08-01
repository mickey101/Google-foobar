class Stair {

    public static void main(String[] args) {

        //System.out.println(stair(3));
        System.out.println(stair(9));
        System.out.println(stair(10));
        System.out.println(stair(11));
        System.out.println(stair(12));
        System.out.println(stair(13));
        System.out.println(stair(14));
        System.out.println(stair(15));
//        System.out.println(stair(10));
//        System.out.println(stair(200));
    }

    public static int stair(int n) {

        int[] table = new int[n + 1];

        table[0] = 1;
        table[1] = 1;

        for(int i = 2; i <= n; i++)
            for(int j = n; j >= i; j--)
                table[j] += table[j - i];

        return table[n] - 1;
    }
}
