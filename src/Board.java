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
            char c = rows.charAt(i);
            if (c == '#') {
                currH = null;
                UnplayableSquare u = new UnplayableSquare();
                _rowSquares[row][col] = u;
            } else if (c == '-') {
                PlayableSquare p = new PlayableSquare();
                p.setBoard(this);
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
                        result += Integer.toString(grabRow.n());
                    } else {
                        result += '~';
                    }
                } else if (grabRow.isSumSquare() || grabCol.isSumSquare()) {
                    if (grabRow.letter() == 0 && grabCol.letter() == 0) {
                        result += '~';
                    } else if (grabRow.isSumSquare()) {
                        result += '.';
                    } else {
                        result += '.';
                    }
                } else {
                    result += '#';
                }
                result += ' ';
            }
        }
        return result;
    }

    /** Print out the board. */
    void printBoard(PrintStream p) {
        String boardString = toString();
        p.println(boardString);
    }

    /** Solve the board in its current state.
     * First strategy: comb the board for forces (Shallow solve).
     * Second strategy: force-solve every Sum Square (Deep Solve). */
    void solve() {
        ArrayList<SumSquare> solvedSS = new ArrayList<>();
        //double count = 0;
        boolean changed;
        boolean shallowSolve = true;
        while (_sumSquares.size() != solvedSS.size() && shallowSolve) {
            changed = false;
            for (SumSquare s : _sumSquares) {
                if (!solvedSS.contains(s)) {
                    if (s.solved()) {
                        solvedSS.add(s);
                        changed = true;
                        //count += 1;
                        //System.out.println(count/ _sumSquares.size());
                    } else {
                        s.comb();
                    }
                }
            }
            if (!changed) {
                shallowSolve = false;
            }
        }
        //deepSolve();
        //printBoard(System.out);
    }

    /** Helper method for solve(). Force solves each Sum Square. */
    void deepSolve() {
        for (SumSquare s : _sumSquares) {

        }
    }

    /** Eliminate bad numbers from Sum Square. */
    void whittle(SumSquare s) {
        int[] impNums = s.impossibleNumbers();
        for (int i = 1; i < 10; i++) {
            if (impNums[i] == 0) {
                if (!keep(i, impNums, s.tempN())) {
                    s.crossOff(i);
                }
            }
        }
    }

    /** True if i can go in a Sum Square with impossible numbers a
     * and tempN n. */
    boolean keep(int i, int[] a, int n) {
        if (i == n) {
            return a[i] == 0;
        } else if () {

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

}
