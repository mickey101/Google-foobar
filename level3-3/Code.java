
class Code {

    public static void main(String[] args) {

        int[] pass = {1, 2, 3, 4, 5, 6};
        System.out.println(code(pass));
    }


    public static int code(int[] l) {
        int leftCount = 0;
        int rightCount = 0;
        int count = 0;

        for (int i = 0; i <l.length ; i++) {


            for (int j = 0; j < i ; j++) {

                if(l[i]%l[j] == 0) {
                    leftCount++;
                }

            }

            for (int j = i+1; j < l.length ; j++) {
                if(l[j]%l[i] == 0) {
                    rightCount++;
                }
            }

            count += leftCount * rightCount;
            leftCount = 0;
            rightCount = 0;

        }
        return count;
    }
}
