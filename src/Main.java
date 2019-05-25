import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

/** Kakuro Board solver.
 * @author Isaac Merritt */
public class Main {

    /** Helper function for Main constructor. */
    public static void main(String... args) {
        try {
            new Main(args).process();
            return;
        } catch (Exception excp) {
            System.err.printf("Error: %s%n", excp.getMessage());
        }
        System.exit(1);
    }

    /** Constructor for Main.
     * Check ARGS and open the necessary files (see comment on main). */
    private Main(String[] args) {
        if (args.length != 2) {
            System.out.println("Only 2 command-line arguments allowed");
            throw new IllegalArgumentException();
        }
        _input = getInput(args[0]);
        _output = getOutput(args[1]);
    }

    /** Return a Scanner reading from the file named NAME. */
    private Scanner getInput(String name) {
        try {
            return new Scanner(new File(name));
        } catch (Exception e) {
            System.out.printf("could not open %s", name);
            throw new IllegalArgumentException();
        }
    }

    /** Return a PrintStream writing to the file named NAME. */
    private PrintStream getOutput(String name) {
        try {
            return new PrintStream(new File(name));
        } catch (IOException excp) {
            System.out.printf("could not open %s", name);
            throw new IllegalArgumentException();
        }
    }

    /** Configure a Kakuro board from the contents of  _input, sending the
     *  results to _output. */
    private void process() {
        Board b = readInput();
        b.solve();
        b.printBoard(_output);
    }

    /** Read the input file, outputting a board that can be solved. */
    private Board readInput() {
        try {
            if (!_input.hasNext("[0-9]+")) {
                System.out.println("Invalid input file:" +
                        " number of rows invalid");
                throw new IllegalArgumentException();
            }
            int rows = Integer.parseInt(_input.next());
            if (!_input.hasNext("[0-9]+")) {
                System.out.println("Invalid input file:" +
                        " number of columns invalid");
                throw new IllegalArgumentException();
            }
            int cols = Integer.parseInt(_input.next());
            String rowString = "";
            String colString = "";
            String[] colArray = new String[cols];
            for (int w = 0; w < colArray.length; w++) {
                colArray[w] = "";
            }
            ArrayList<SumSquareDual> sumSquares = new ArrayList<>();
            String rawString = "";
            String burn = "";
            for (int i = 0; i < rows; i++) {

                try {
                    burn = _input.next();
                } catch (Exception e) {
                    System.out.printf("Input file error: Error at row %s", i);
                    throw new IllegalArgumentException();
                }
                if (!burn.equals("%")) {
                    System.out.printf("Input file error: missing begin character in row %s", i);
                    throw new IllegalArgumentException();
                }

                try {
                    rawString = _input.next();
                } catch (Exception e) {
                    System.out.printf("Input file error: Error at row %s", i);
                    throw new IllegalArgumentException();
                }
                if (rawString.length() != cols) {
                    System.out.printf("Input file error: Row %s does not have the correct number of columns.", i);
                    throw new IllegalArgumentException();
                }

                sumSquares = new ArrayList<>();
                SumSquareDual curr;
                String nextSumSquareDual = _input.next();
                while (!nextSumSquareDual.equals("*")) {
                    if (nextSumSquareDual.equals("%")) {
                        System.out.printf("Input file error: missing end character in row %s", i);
                        throw new IllegalArgumentException();
                    }
                    try {
                        curr = new SumSquareDual(nextSumSquareDual);
                    } catch (Exception e) {
                        System.out.println("Input file error: faulty Sum Square input.");
                        throw new IllegalArgumentException();
                    }
                    sumSquares.add(curr);
                    nextSumSquareDual = _input.next();
                }
                int countSumSquares = 0;
                for (int j = 0; j < rawString.length(); j++) {
                    char c = rawString.charAt(j);
                    if (c == '?') {
                        SumSquareDual ssd = sumSquares.get(countSumSquares);
                        char h = hashInt(ssd.horizontal());
                        rowString += h;
                        h = hashInt(ssd.vertical());
                        colArray[j] += h;
                        countSumSquares += 1;
                    } else {
                        rowString += c;
                        colArray[j] += c;
                    }
                }

            }
            if (_input.hasNext()) {
                System.out.println("Input file error: Invalid number of rows)");
                throw new IllegalArgumentException();
            }
            for (int k = 0; k < cols; k++) {
                colString += colArray[k];
            }
            return new Board(rows, cols, rowString, colString);
        } catch (Exception e) {
            System.out.println("Invalid input file");
            throw new IllegalArgumentException();
        }
    }

    /** Hash an integer to a char in the alphabet. */
    private char hashInt(int i) {
        if (i == 0) {
            return '#';
        }
        char c;
        if (i > 26) {
            i += 70;
            c = (char) i;
        } else {
            i += 64;
            c = (char) i;
        }
        return c;
    }

    /** Source of input board information. */
    private Scanner _input;

    /** File for solved board. */
    private PrintStream _output;

}
