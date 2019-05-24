/** Helper Class for parsing input files. */
public class SumSquareDual {

    /** Constructor. */
    SumSquareDual(String s) {
        try{
            boolean flip = false;
            String horizontalString = "";
            String verticalString = "";
            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                if (c == '/') {
                    flip = true;
                } else {
                    if (flip) {
                        verticalString += c;
                    } else {
                        horizontalString += c;
                    }
                }
            }
            if (horizontalString.length() > 0) {
                _horizontal = Integer.parseInt(horizontalString);
            } else {
                _horizontal = 0;
            }
            if (verticalString.length() > 0) {
                _vertical = Integer.parseInt(verticalString);
            } else {
                _vertical = 0;
            }
        } catch (Exception e) {
            System.out.printf("SumSquare Dual error: Invalid string given as input");
            throw new IllegalArgumentException();
        }
    }

    /** Accessor method for _horizontal. */
    int horizontal() {
        return _horizontal;
    }

    /** Accessor method for _vertical. */
    int vertical() {
        return _vertical;
    }

    /** Horizontal sum of this SumSquare. */
    private int _horizontal;

    /** Vertical sum of this Sum Square */
    private int _vertical;

}
