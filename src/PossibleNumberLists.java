import java.util.List;
import java.util.ArrayList;

/** Grid of all numbers that can go in a space with 'n' as a sum and 'length' as a length
 * @author Isaac Merritt */
class PossibleNumberLists {

    /** Constructor. */
    PossibleNumberLists() {
        _L = new ArrayList[46][10];
        for (int i = 0; i < 46; i++) {
            for (int j = 0; j < 10; j++) {
                if (i == 0 || j == 0
                        ||(i > 9 && j == 1)
                        || (i > 17 && j == 2)
                        || (i > 24 && j == 3)
                        || (i > 30 && j == 4)
                        || (i > 35 && j == 5)
                        || (i > 39 && j == 6)
                        || (i > 42 && j == 7)
                        || (i > 44 && j == 8)
                        || (i < 3 && j == 2)
                        || (i < 6 && j == 3)
                        || (i < 10 && j == 4)
                        || (i < 15 && j == 5)
                        || (i < 21 && j == 6)
                        || (i < 28 && j == 7)
                        || (i < 36 && j == 8)
                        || (i < 45 && j == 9)) {
                    _L[i][j] = null;
                } else if (i < 10 && j == 1) {
                    ArrayList<Integer> temp = new ArrayList<>();
                    temp.add(i);
                    _L[i][j] = temp;
                } else {
                    ArrayList<Integer> temp = new ArrayList<>();
                    for (int w = 1; w < 10 && w < i; w++) {
                        temp.add(w);
                    }
                    ArrayList<Integer> result = new ArrayList<>();
                    for (int k : temp) {
                        ArrayList<Integer> grab = _L[i - k][j - 1];
                        ArrayList<Integer> copy = new ArrayList<>();
                        if (grab != null) {
                            if (grab.contains(k)) {
                                for (int m : grab) {
                                    if (m != k) {
                                        copy.add(m);
                                    }
                                }
                                if (canMake(i - k, j - 1, copy)) {
                                    result.add(k);
                                }
                            } else {
                                result.add(k);
                            }
                        }
                    }
                    _L[i][j] = result;
                }
            }
        }
    }

    /** Can n be made of k digits in options, given as a set? */
    boolean canMake(int n, int k, List<Integer> options) {
        if (n == 0 || k == 0 || options == null || options.size() < k
                || (n > 9 && k == 1)
                || (n > 17 && k == 2)
                || (n > 24 && k == 3)
                || (n > 30 && k == 4)
                || (n > 35 && k == 5)
                || (n > 39 && k == 6)
                || (n > 42 && k == 7)
                || (n > 44 && k == 8)
                || (n > 45 && k == 9)
                || (n < 1 && k == 1)
                || (n < 3 && k == 2)
                || (n < 6 && k == 3)
                || (n < 10 && k == 4)
                || (n < 15 && k == 5)
                || (n < 21 && k == 6)
                || (n < 28 && k == 7)
                || (n < 36 && k == 8)
                || (n < 45 && k == 9)) {
            return false;
        } else if (options.size() == 1) {
            return k == 1 && n == options.get(0);
        } else if (k == 1) {
            return options.contains(n);
        } else {
            for (int i : options) {
                if (canMake(n - i, k - 1, options.subList(1, options.size()))) {
                    return true;
                }
            }
            return false;
        }
    }

    /** Lookup for potential numbers for a sum n of length length.
     * Uses dynamic programming. */
    ArrayList<Integer> possibleNumbers(int n, int length) {
        return _L[n][length];
    }

    /** Grid of possibilities. */
    private ArrayList<Integer>[][] _L;

}
