import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

/** Kakuro Board solver.
 * @author Isaac Merritt */
public class Main {

    public static void main(String... args) {
        try {
            new Main(args).process();
            return;
        } catch (Exception excp) {
            System.err.printf("Error: %s%n", excp.getMessage());
        }
        System.exit(1);
    }

    /** Check ARGS and open the necessary files (see comment on main). */
    Main(String[] args) {
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
        _board = readInput();
        //more shit
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
            String rowString;
            String colString;


            return new Board(rows, cols, "", "");
        } catch (Exception e) {
            System.out.println("Invalid input file");
            throw new IllegalArgumentException();
        }
    }

    /** Hash an integer to a char in the alphabet. */
    private char hashInt(int i) {
        char c;
        if (i > 26) {
            i += 70;
            c = (char)i;
        } else {
            i += 64;
            c = (char)i;
        }
        return c;
    }

    /** Source of input board information. */
    private Scanner _input;

    /** File for solved board. */
    private PrintStream _output;

    /** Board used by this Main. */
    private Board _board;

}
