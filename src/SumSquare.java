import java.util.ArrayList;

/** Unplayable Square with a sum in it
 * @author Isaac Merritt */
abstract class SumSquare extends UnplayableSquare {

    SumSquare(int n) {
        _n = n;
        _tempN = n;
        _childrenSquares = new ArrayList<>();
        _impossibleNumbers = new int[10];
        _impossibleNumbers[0] = 1;
        _solvedChildren = 0;
        _solved = false;
    }

    /** All SumSqures are SumSquares. */
    boolean isSumSquare() {
        return true;
    }

    /** True for horizontal sum squares, false for vertical sum squares. */
    abstract boolean isHorizontal();

    /** Solve myself to the best of my ability. */
    void comb() {
        for (PlayableSquare child : _childrenSquares) {
            if (!child.solved()) {
                int count = 0;
                int possibleMatch = 0;
                for (int i = 1; i < 10; i++) {
                    if (child.possibleNumbers()[i] == 1) {
                        if (count == 0) {
                            possibleMatch = i;
                        }
                        count++;
                    }
                }
                if (count == 1) {
                    child.solve(possibleMatch);
                    child.wipe();
                }
            }
        }
    }

    @Override
    void setBoard(Board b) {
        _board = b;
        for (int i = 1; i < 10; i++) {
            ArrayList<Integer> poss = b.pnl().possibleNumbers(_tempN, _childrenSquares.size());
            if (!poss.contains(i)) {
                crossOff(i);
            }
        }
    }

    /** Modifier for _letter. */
    void setLetter(char c) {
        _letter = c;
    }

    /** Accessor method for _letter. */
    char letter() {
        return _letter;
    }

    /** Add a number to impossibleNumbers for all children. */
    void crossOff(int i) {
        _impossibleNumbers[i] = 1;
        for (PlayableSquare child : _childrenSquares) {
            child.add(i);
        }
    }

    /** Increment solved children by 1. */
    void graduateChild() {
        if (_solvedChildren < childrenSquares().size()) {
            _solvedChildren += 1;
            if (_solvedChildren == childrenSquares().size()) {
                _solved = true;
            }
        }
    }

    /** Add a PlayableSquare to my children. */
    void addChild(PlayableSquare child) {
        _childrenSquares.add(child);
    }

    /** Accessor method for _childrenSquares. */
    ArrayList<PlayableSquare> childrenSquares() {
        return _childrenSquares;
    }

    /** Accessor method for _impossibleNumbers. */
    int[] impossibleNumbers() {
        return _impossibleNumbers;
    }

    /** Accessor for _solvedChildren. */
    int solvedChildren() {
        return _solvedChildren;
    }

    /** Modifier for _tempN. */
    void setTempN(int i) {
        _tempN = i;
    }

    /** Accessor method for _tempN. */
    int tempN() {
        return _tempN;
    }

    /** List of all sqaures this square dictates. */
    private ArrayList<PlayableSquare> _childrenSquares;

    /** List of possible numbers for unassigned children. */
    private int[] _impossibleNumbers;

    /** Letter corresponding to this Sum Square's N value. */
    private char _letter;

    /** Number of solved children. */
    private int _solvedChildren;

    /** N value that changes as this Sum Square gets solved. */
    private int _tempN;

}