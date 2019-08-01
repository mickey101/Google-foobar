import java.util.LinkedList;
import java.util.Queue;


public class Solution {
    private static int[] rowMoves = {-1, 0, 0, 1};
    private static int[] colMoves = {0, 1, -1, 0};
    private static boolean[][][] visit;

    public static int solution(int[][] map) {

        int length = map.length;
        int width = map[0].length;

        Queue<Cord> cordQueue = new LinkedList<>();
        cordQueue.add(new Cord(0, 0));

        visit = new boolean[length][width][2];
        visit[0][0][0] = true;

        while (!cordQueue.isEmpty()) {

            Cord next = cordQueue.poll();
            int row = next.xCord;
            int col = next.yCord;
            int result = next.distance;
            int seen = next.color;

            if (row == length - 1 && col == width - 1) {
                return result + 2;
            }

            for (int i = 0; i < rowMoves.length; i++) {

                if (isValid(row + rowMoves[i], col + colMoves[i], length, width)) {

                    if (map[row + rowMoves[i]][col + colMoves[i]] == 0) {

                        addToQueue(cordQueue, row, col, result, seen, i);

                    } else {

                        if (seen != 1) {
                            addToQueueWithVisitedCord(cordQueue, row, col, result, seen, i);;
                        }
                    }
                }
            }
        }
        return Integer.MAX_VALUE;

    }

    private static void addToQueueWithVisitedCord(Queue<Cord> cordQueue, int row, int col, int result, int seen, int i) {
        if (!visit[row + rowMoves[i]][col + colMoves[i]][seen]) {

            visit[row + rowMoves[i]][col + colMoves[i]][seen] = true;
            Cord u = new Cord(row + rowMoves[i], col + colMoves[i]);
            u.distance = result + 1;
            u.color = 1;
            cordQueue.add(u);
        }
    }

    private static void addToQueue(Queue<Cord> cordQueue, int row, int col, int result, int seen, int i) {
        if (!visit[row + rowMoves[i]][col + colMoves[i]][seen]) {

            Cord u = new Cord(row + rowMoves[i], col + colMoves[i]);
            u.distance = result + 1;
            u.color = seen;
            cordQueue.add(u);

            visit[row + rowMoves[i]][col + colMoves[i]][seen] = true;
        }
    }

    private static boolean isValid(int r, int c, int length, int width) {
        if ((r < 0 || c < 0) || (r >= length || c >= width)) {
            return false;
        }
        return Boolean.TRUE;
    }

    private static class Cord {

        private int xCord;
        private int yCord;
        private int color;
        private int distance;

        public Cord(int xCord, int yCord) {
            this.xCord = xCord;
            this.yCord = yCord;
            this.color = 0;
            this.distance = -1;
        }

    }
}
