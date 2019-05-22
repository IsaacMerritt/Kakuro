/** Unplayable square on a Kakuro board.
 * @author Isaac Merritt */
class UnplayableSquare extends Square {

    /** Unplayable squares are not playable. */
    boolean isPlayable() {
        return false;
    }

    /** Unplayable squares are not necessarily SumSquares. */
    boolean isSumSquare() {
        return false;
    }

    /** Unplayable Squares to not have letters. */
    char letter() {
        return 0;
    }

    /** Invalid integer for a playable square. */
    int _n = -1;

}
