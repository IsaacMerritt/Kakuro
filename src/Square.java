/** A square on a Kakuro board.
 * @author Isaac Merritt */
abstract class Square {

    /** Says whether this square is playable. */
    abstract boolean isPlayable();

    /** Says whether this square is a SumSquare. */
    abstract boolean isSumSquare();

    /** For Sum Squares, the letter given at reading. */
    abstract char letter();

    /** Number represented by this square, 0 if currently undefined.
     * -1 indicates an unplayable square
     * 0 indicates an unsolved playable square
     * a number > 0 indicates either a Sum Square or a solved Playable Square. */
    int _n;

    /** Accessor method for _n. */
    int n() {
        return _n;
    }

    /** Modifier method for _n. */
    void setN(int i) {
        _n = i;
    }

    /** Row this square is in. */
    private int _row;

    /** Column this square is in. */
    private int _col;

    /** Board this square is on. */
    Board _board;

    /** Set the board this square is on to b. */
    void setBoard(Board b) {
        _board = b;
    }

    /** Accessor method for _board. */
    Board board() {
        return _board;
    }

    /** True if square has been solved, false if not. */
    boolean _solved;

    /** Accessor method for _solved. */
    boolean solved() {
        return _solved;
    }

}
