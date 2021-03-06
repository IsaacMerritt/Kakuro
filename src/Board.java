import java.util.Arrays;
import java.util.ArrayList;
import java.io.PrintStream;

/** A Kakuro board.
 * @author Isaac Merritt */
public class Board {

    /** Constructor. */
    Board(int rows, int cols, String rowRepresentation, String colRepresentation) {
        _rows = rows;
        _cols = cols;
        _rowSquares = new Square[_rows][_cols];
        _colSquares = new Square[_cols][_rows];
        _sumSquares = new ArrayList<>();
        _horizontalSumSquares = new ArrayList<>();
        _verticalSumSquares = new ArrayList<>();
        _pnl = new PossibleNumberLists();
        read(rowRepresentation, colRepresentation);
    }

    /** Hash letters for Sum Square string interpretation */
    private int hashChar(char c) {
        if (c - 'A' + 1 > 26) {
            return c - 'A' - 5;
        } else {
            return c - 'A' + 1;
        }
    }

    /** A constructor for this board.
     * Set the board up to be solved by grouping squares by sum.
     * A letter ([A-Z][a-z]) indicates a sumSquare, to be hashed to its value.
     * A '-' indicates an unsolved, playable square.
     * A '#' indicates a standard unplayable square. */
    private void read(String rows, String cols) {

        //Row parsing
        HorizontalSumSquare currH = null;
        int row = 0, col = 0;
        for (int i = 0; i < rows.length(); i++) {
            if (i == 85) {
                boolean here = true;
            }
            char c = rows.charAt(i);
            if (c == '#') {
                currH = null;
                UnplayableSquare u = new UnplayableSquare();
                _rowSquares[row][col] = u;
            } else if (c == '-') {
                PlayableSquare p = new PlayableSquare();
                p.setBoard(this);
                if (currH == null) {
                    System.out.println("Error: Sum Square values inconsistent with board");
                    throw new IllegalArgumentException();
                }
                currH.addChild(p);
                p.setHorizontalSumSquare(currH);
                _rowSquares[row][col] = p;
            } else {
                int sumVal = hashChar(c);
                currH = new HorizontalSumSquare(sumVal);
                currH.setLetter(c);
                _horizontalSumSquares.add(currH);
                _sumSquares.add(currH);
                _rowSquares[row][col] = currH;
            }
            col += 1;
            if (col == _cols) {
                row++;
                col = 0;
            }
        }
        for (HorizontalSumSquare h : _horizontalSumSquares) {
            h.setBoard(this);
        }

        //Column parsing
        VerticalSumSquare currV = null;
        row = 0;
        col = 0;
        for (int j = 0; j < cols.length(); j++) {
            char c = cols.charAt(j);
            if (c == '#') {
                currV = null;
                UnplayableSquare u = new UnplayableSquare();
                _colSquares[col][row] = u;
            } else if (c == '-') {
                PlayableSquare p = (PlayableSquare) _rowSquares[row][col];
                p.setBoard(this);
                currV.addChild(p);
                p.setVerticalSumSquare(currV);
                _colSquares[col][row] = p;
            } else {
                int sumVal = hashChar(c);
                currV = new VerticalSumSquare(sumVal);
                currV.setLetter(c);
                _verticalSumSquares.add(currV);
                _sumSquares.add(currV);
                _colSquares[col][row] = currV;
            }
            row += 1;
            if (row == _rows) {
                col++;
                row = 0;
            }
        }
        for (VerticalSumSquare v : _verticalSumSquares) {
            v.setBoard(this);
        }
    }

    /** Convert the board into a string. */
    public String toString() {
        String result = "";
        for (int i = 0; i < _rows; i++) {
            if (i != 0) {
                result += "\n";
            }
            for (int j = 0; j < _cols; j++) {
                Square grabRow = _rowSquares[i][j];
                Square grabCol = _colSquares[j][i];
                if (grabRow.isPlayable() && grabCol.isPlayable()) {
                    if (grabRow.n() == grabCol.n()) {
                        result += Integer.toString(grabRow.n()); //solved Playable Square
                    } else {
                        result += '~'; // unsolved Playable Square
                    }
                } else {
                    result += ' '; // Unplayable Square or Sum Square not necessary for solution
                }
                result += ' '; // Put a space between each character
            }
        }
        return result;
    }

    /** Print out the board to printstream p. */
    void printBoard(PrintStream p) {
        String boardString = toString();
        p.println(boardString);
    }

    /** Solve the board in its current state.
     * First strategy: comb the board for forces (Shallow solve).
     * Second strategy: force-solve every Sum Square (Deep Solve). */
    void solve() {
        _solvedSS = new ArrayList<>();
        boolean changed;
        boolean shallowSolve = true;
        while (_sumSquares.size() != _solvedSS.size() && shallowSolve) {
            changed = false;
            for (SumSquare s : _sumSquares) {
                if (!_solvedSS.contains(s)) {
                    if (s.solved()) {
                        _solvedSS.add(s);
                        changed = true;
                    } else {
                        s.comb();
                    }
                }
            }
            if (!changed) {
                shallowSolve = false;
            }
        }
        while (_solvedSS.size() != _sumSquares.size()) {
            deepSolve();
        }
        printBoard(System.out);
    }

    /** Helper method for solve(). Force solves each Sum Square. */
    private void deepSolve() {
        for (SumSquare s : _sumSquares) {
            if (!s.solved()) {
                whittle(s);
                s.comb();
                if (s.solved() && !_solvedSS.contains(s)) {
                    _solvedSS.add(s);
                }
            } else {
                if (!_solvedSS.contains(s)) {
                    _solvedSS.add(s);
                }
            }
        }
    }

    /** Eliminate bad numbers from Sum Square. */
    private void whittle(SumSquare s) {
        for (int w = 1; w < 10; w++) {
            if (!s.possibleForChildren(w)) {
                s.crossOff(w);
            }
        }
        int[] impNums = s.impossibleNumbers();
        for (int i = 1; i < 10; i++) {
            if (impNums[i] == 0) {
                int unsolved = s.childrenSquares().size() - s.solvedChildren();
                if (!keep(i, impNums, s.tempN(), unsolved)) {
                    s.crossOff(i);
                }
            }
        }
    }

    /** True if i can go in a Sum Square with impossible numbers a
     * and tempN n. */
    private boolean keep(int i, int[] a, int n, int numLeft) {
        if (i == n) {
            return a[i] == 0 && numLeft == 1;
        } else if (a[i] == 1 || i > n) {
            return false;
        } else {
            int[] copy = Arrays.copyOf(a, a.length);
            copy[i] = 1;
            for (int j = 1; j < 10; j++) {
                if (copy[j] == 0) {
                    if (keep(j, copy, n - i, numLeft - 1)) {
                        return true;
                    }
                }
            }
            return false;
        }
    }

    /** Accessor method for _pnl. */
    PossibleNumberLists pnl() {
        return _pnl;
    }

    /** Number of rows on this board. */
    private final int _rows;

    /** Number of columns on this board. */
    private final int _cols;

    /** 2D array of all row squares on this board. */
    private Square[][] _rowSquares;

    /** 2D array of all col squares on this board. */
    private Square[][] _colSquares;

    /** List of all SumSquares on this board. */
    private ArrayList<SumSquare> _sumSquares;

    /** List of all Horizontal Sum Squares on this board. */
    private ArrayList<HorizontalSumSquare> _horizontalSumSquares;

    /** List of all Vertical Sum Squares on this board. */
    private ArrayList<VerticalSumSquare> _verticalSumSquares;

    /** Possible number finder. */
    private PossibleNumberLists _pnl;

    /** List of all solved Sum Squares. */
    private ArrayList<SumSquare> _solvedSS;

}
