import java.util.ArrayList;

/** A playable square on a Kakuro board. */
class PlayableSquare extends Square {

    /** Constructor. */
    PlayableSquare() {
        _n = 0;
        _solved = false;
        _possibleNumbers = new int[10];
        _impossibleNumbers = new int[10];
        for (int i = 0; i < 9; i++) {
            _possibleNumbers[i + 1] = 1;
        }
    }

    /** Playable squares are playable. */
    boolean isPlayable() {
        return true;
    }

    /** Playable Squares are not ever SumSquares. */
    boolean isSumSquare() {
        return false;
    }

    /** Unplayable Squares to not have letters. */
    char letter() {
        return 0;
    }

    /** Returns an array of every one digit number not in the given a. */
    /*private int[] toggleList(int[] a) {
        int[] result = new int[a.length];
        for (int i = 1; i < a.length; i++) {
            result[i] = 1 - i;
        }
        return result;
    }*/

    /** Says whether the given i is a possible value of this square. */
    boolean possibleValue(int i) {
        return _possibleNumbers[i] == 1;
    }

    /** Add a number to the impossible numbers. */
    void add(int i) {
        if (_impossibleNumbers[i] == 0) {
            _impossibleNumbers[i] = 1;
            _possibleNumbers[i] = 0;
        }
    }

    /** When a solution is found, set N and flip the switch. */
    void solve(int i) {
        setN(i);
        _solved = true;
        tellSumSquares(i);
    }

    /** Notify the parent Sum Squares when I am solved. */
    private void tellSumSquares(int i) {
        HorizontalSumSquare h = horizontalSumSquare();
        h.graduateChild();
        h.setTempN(h.tempN() - i);
        ArrayList<Integer> newPoss = board().pnl().possibleNumbers(h.tempN(), h.childrenSquares().size() - h.solvedChildren());
        for (int j = 1; j < 10; j++) {
            if (newPoss != null) {
                if (!newPoss.contains(j)) {
                    h.crossOff(j);
                }
            }
        }
        VerticalSumSquare v = verticalSumSquare();
        v.graduateChild();
        v.setTempN(v.tempN() - i);
        newPoss = board().pnl().possibleNumbers(v.tempN(), v.childrenSquares().size() - v.solvedChildren());
        for (int j = 1; j < 10; j++) {
            if (newPoss != null) {
                if (!newPoss.contains(j)) {
                    v.crossOff(j);
                }
            }
        }
    }

    /** When an N is chosen for this square, its possible numbers are N
     * and its imposible numbers are all but N. Its neighbors are affected
     * as well. */
    void wipe() {
        _verticalSumSquare.crossOff(_n);
        _horizontalSumSquare.crossOff(_n);
        for (int i = 1; i < 10; i++) {
            if (i != _n) {
                _possibleNumbers[i] = 0;
                _impossibleNumbers[i] = 1;
            } else {
                _possibleNumbers[i] = 1;
                _impossibleNumbers[i] = 0;
            }
        }
    }

    /** Set this square's vertical sum to this sum square. */
    void setVerticalSumSquare(VerticalSumSquare vss) {
        _verticalSumSquare = vss;
    }

    /** Set this square's horizontal sum to this sum square. */
    void setHorizontalSumSquare(HorizontalSumSquare hss) {
        _horizontalSumSquare = hss;
    }

    /** Accessor method for _verticalSumSquare. */
    VerticalSumSquare verticalSumSquare() {
        return _verticalSumSquare;
    }

    /** Accessor method for _horizontalSumSquare. */
    HorizontalSumSquare horizontalSumSquare() {
        return _horizontalSumSquare;
    }

    /** Vertical SumSquare. */
    private VerticalSumSquare _verticalSumSquare;

    /** Horizontal SumSquare. */
    private HorizontalSumSquare _horizontalSumSquare;

    /** Accessor for _possibleNumbers. */
    int[] possibleNumbers() {
        return _possibleNumbers;
    }

    /** Accessor for _impossibleNumbers. */
    int[] impossibleNumbers() {
        return _impossibleNumbers;
    }

    /** Possible values of this square. */
    private int[] _possibleNumbers;

    /** Impossible values of this square. */
    private int[] _impossibleNumbers;

}
