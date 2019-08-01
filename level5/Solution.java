import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Solution {

    private static  List<Jail> ones = setOnesJail();
    private static  List<Jail> zeroes = setZeroesJail();

    static Map<Jail,Integer> map = generateMap();


    private static Map<Jail, Integer> generateMap() {
        Map<Jail,Integer> map = new HashMap<>();

        map.put(setUp(0,1),1);
        map.put(setUp(0,2),1);
        map.put(setUp(1,0),1);
        map.put(setUp(2,0),1);

        map.put(setUp(0,0),0);
        map.put(setUp(0,3),0);
        map.put(setUp(1,1),0);
        map.put(setUp(1,2),0);
        map.put(setUp(1,3),0);
        map.put(setUp(2,1),0);
        map.put(setUp(2,2),0);
        map.put(setUp(2,3),0);
        map.put(setUp(3,0),0);
        map.put(setUp(3,1),0);
        map.put(setUp(3,2),0);
        map.put(setUp(3,3),0);

        return map;
    }

    private static List<Jail> setOnesJail() {
        List<Jail> ones = new ArrayList<>();
        ones.add(setUp(0,1));
        ones.add(setUp(0,2));
        ones.add(setUp(1,0));
        ones.add(setUp(2,0));

        return ones;

    }

    private static List<Jail> setZeroesJail() {
        List<Jail> zeroes = new ArrayList <>();
        zeroes.add(setUp(0,0));
        zeroes.add(setUp(0,3));
        zeroes.add(setUp(1,1));
        zeroes.add(setUp(1,2));
        zeroes.add(setUp(1,3));
        zeroes.add(setUp(2,1));
        zeroes.add(setUp(2,2));
        zeroes.add(setUp(2,3));
        zeroes.add(setUp(3,0));
        zeroes.add(setUp(3,1));
        zeroes.add(setUp(3,2));
        zeroes.add(setUp(3,3));
        return zeroes;
    }

    private static Jail setUp(int a , int b) {
        Jail jail = new Jail();
        jail.first = a;
        jail.last= b;

        List<Integer> list = new ArrayList <>();
        list.add(a);
        list.add(b);

        jail.setColumns(list);

        return jail;
    }


    public static int solution(boolean[][] g) {
        // Your code here

        List<Jail> result  = new ArrayList <>();

        Map<Integer,List<Jail>> dp = new HashMap<>();

        Map<Integer, Integer> reverse = new HashMap<>();

        for (int i = 0; i < g[0].length; i++) {

            boolean[] column = new boolean[g.length];
            for (int j = 0; j < g.length; j++) {
                column[j] = g[j][i];
            }

            List<Jail> preImage = new ArrayList <>();
            if (i == 0) {
                result = crunchJail(combineJail(column,preImage));
                dp.put(i,result);
            } else {
                preImage = combineJailFirst(column,result,g.length-1);

                List<Jail> first = crunchJail(preImage);
                dp.put(i,first);
            }
        }


        List<Jail> images = new ArrayList<>(dp.get(0));

        for (Jail b: images) {
            if (reverse.containsKey(b.last)) {
                int count = reverse.get(b.last) + 1;
                reverse.put(b.last,count);
            } else {
                reverse.put(b.last,1);
            }
        }

        for (int i = 1; i < g[0].length; i++) {
            Map<Integer, Integer> second = new HashMap<>();

            List<Jail> zCat = new ArrayList<>(dp.get(i));
            for (Jail b: zCat) {

                if (reverse.containsKey(b.first)){

                    if (second.containsKey(b.last)) {
                        int count = second.get(b.last) + reverse.get(b.first);
                        second.put(b.last,count);
                    } else {
                        int count = reverse.get(b.first);
                        second.put(b.last,count);
                    }
                }

            }
            reverse = second;

        }

        return finalCount(reverse);
    }


    private static List<Jail> combineJail (boolean[] col, List<Jail> preImage) {

        for (int i = 0; i < col.length; i++) {

            List<Jail> current;
            if (col[i]) {
                current = ones;
            } else {
                current = zeroes;
            }
            preImage = mergeJail(preImage,current);
        }

        return preImage;

    }

    private static List<Jail> combineJailFirst(boolean[] col, List<Jail> preImage, int length) {

        List<Jail> begin = combine(col[0]);

        for (int n = 1; n < col.length; n++) {

            begin = filterPreImage(col[n], begin);

        }
        return begin;
    }

    private static List<Jail> filterPreImage(boolean b, List<Jail> begin) {

        int[] rows = {0,1,2,3};

        Set<Jail> holder = new HashSet<>();


        for (Jail key: begin) {

            for (int row: rows) {

                int val = b ? 1 : 0;

                if (map.get(setUp(key.last,row)) == val) {

                    Jail tmp = new Jail(key);
                    tmp.last = row;

                    List<Integer> columns = new ArrayList<>(tmp.getColumns());
                    columns.add(row);
                    tmp.setColumns(columns);
                    holder.add(tmp);

                }

            }

        }

        return new ArrayList<>(holder);
    }

    private static List <Jail> combine(boolean b) {
        List <Jail> current;
        if (b) {
            current = ones;
        } else {
            current = zeroes;
        }
        return current;
    }


    private static List<Jail> mergeJail(List<Jail> preImage, List<Jail> current) {

        List<Jail> results = new ArrayList <>();

        if (preImage.isEmpty()) {
            return current;
        }

        for (Jail aPreImage : preImage) {

            List <Jail> tempMatches = searchOptimized(current, aPreImage);
            List<Jail> allMatches = buildJail(tempMatches, aPreImage);
            results.addAll(allMatches);
        }

        return results;

    }

    private static List <Jail> searchOptimized(List <Jail> current, Jail aPreImage) {
        Map <Integer, List <Jail>> container = new HashMap <>();
        List<Jail> tempMatches;

        if (!container.containsKey(aPreImage.last)) {
            tempMatches = searchJail(current, aPreImage.last);
            List<Jail> clone = new ArrayList<>(tempMatches);
            container.put(aPreImage.last,clone);
        } else {
            tempMatches = container.get(aPreImage.last);
        }
        return tempMatches;
    }

    private static List<Jail> searchJail (List<Jail> current, int value ) {

        List<Jail> matches = new ArrayList <>();

        for (Jail aCurrent : current) {

            if (aCurrent.first == value) {
                matches.add(aCurrent);
            }

        }
        return matches;
    }

    private static List<Jail> buildJail(List<Jail> matches, Jail preImage) {
        List<Jail> result = new ArrayList <>();

        for (Jail a: matches) {
            Jail temp =  new Jail(preImage);
            List<Integer> list = new ArrayList <>(temp.getColumns());
            list.add(a.last);

            temp.last = a.last;
            temp.setColumns(list);

            result.add(temp);

        }

        return result;
    }

    private static List<Jail> crunchJail(List<Jail> column) {

        List<Jail> crunch = new ArrayList <>();
        for (Jail a: column) {

            crunch.add(parse(a));

        }
        return crunch;
    }


    private static int finalCount(Map<Integer,Integer> result) {

        int count = 0;
        for (Integer a: result.keySet()) {
            count += result.get(a);
        }

        return count;
    }

    private static Jail parse(Jail a) {

        int first = 0;
        int second = 0;

        for (Integer v: a.getColumns()) {
            first= first << 1;
            second = second << 1;

            switch (v) {
                case 0:
                    first = modifyBit(first,0);
                    second = modifyBit(second,0);
                    break;
                case 1:
                    first = modifyBit(first,0);
                    second = modifyBit(second,1);
                    break;
                case 2:
                    first = modifyBit(first,1);
                    second = modifyBit(second,0);
                    break;
                default:
                    first = modifyBit(first,1);
                    second = modifyBit(second,1);
                    break;
            }

        }

        return setUp(first,second);
    }

    private static int modifyBit(int n, int b) {
        int p = 0;
        int mask = 1 << p;
        return (n & ~mask) | ((b << p) & mask);
    }

    public static class Jail {

        private int first;
        private int last;

        private List<Integer> columns;

        public Jail(Jail other) {
            this.first = other.first;
            this.last = other.last;
            this.columns = other.columns;
        }

        public Jail() {
        }

        public int getFirst() {
            return first;
        }

        public void setFirst(int first) {
            this.first = first;
        }

        public int getLast() {
            return last;
        }

        public void setLast(int last) {
            this.last = last;
        }

        public List <Integer> getColumns() {
            return columns;
        }

        public void setColumns(List <Integer> columns) {
            this.columns = columns;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Jail jail = (Jail) o;

            if (first != jail.first) return false;
            if (last != jail.last) return false;
            return columns.equals(jail.columns);
        }

        @Override
        public int hashCode() {
            int result = first;
            result = 31 * result + last;
            result = 31 * result + columns.hashCode();
            return result;
        }
    }
}
