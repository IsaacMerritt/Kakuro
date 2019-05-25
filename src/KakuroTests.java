import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/** Tests fot the Kakuro solver.
 * @author Isaac Merritt */
public class KakuroTests {

    private PlayableSquare _s;

    /** Print an array of numbers. */
    String arrayString(int[] a) {
        String result = "";
        for (int i = 1; i < a.length - 1; i++) {
            result += Integer.toString(a[i]) + ", ";
        }
        result += Integer.toString(a[a.length - 1]);
        return result;
    }

    @Before
    public void setUp() {
        _s = new PlayableSquare();
    }

    @Test
    public void toggleListTest() {
        System.out.println(arrayString(_s.possibleNumbers()));
        System.out.println(arrayString(_s.impossibleNumbers()));
        System.out.println(" ");
    }
    @Test
    public void validityTest() {
        _s.add(4);
        _s.add(1);
        System.out.println(arrayString(_s.impossibleNumbers()));
        System.out.println(arrayString(_s.possibleNumbers()));
        System.out.println(" ");
        assertTrue(_s.possibleValue(2));
        assertTrue(_s.possibleValue(5));
        assertFalse(_s.possibleValue(1));
        assertFalse(_s.possibleValue(4));
    }

    @Test
    public void sumTest() {
        Board b = new Board(3, 3, "", "");
        ArrayList<Integer> result = b.pnl().possibleNumbers(3, 2);
        System.out.println("3, 2: " + result);
        result = b.pnl().possibleNumbers(16, 2);
        System.out.println("16, 2: " + result);
        result = b.pnl().possibleNumbers(7, 3);
        System.out.println("7, 3: " + result);
        result = b.pnl().possibleNumbers(24, 3);
        System.out.println("24, 3: " + result);
        result = b.pnl().possibleNumbers(10, 4);
        System.out.println("10, 3: " + result);
        result = b.pnl().possibleNumbers(29, 4);
        System.out.println("29, 4: " + result);
        result = b.pnl().possibleNumbers(16, 5);
        System.out.println("16, 5: " + result);
        result = b.pnl().possibleNumbers(35, 5);
        System.out.println("35, 5: " + result);
        result = b.pnl().possibleNumbers(21, 6);
        System.out.println("21, 6: " + result);
        result = b.pnl().possibleNumbers(38, 6);
        System.out.println("38, 6: " + result);
        result = b.pnl().possibleNumbers(29, 7);
        System.out.println("29, 7: " + result);
        result = b.pnl().possibleNumbers(42, 7);
        System.out.println("42, 7: " + result);
        result = b.pnl().possibleNumbers(36, 8);
        System.out.println("36, 8: " + result);
        result = b.pnl().possibleNumbers(43, 8);
        System.out.println("43, 8: " + result);
        result = b.pnl().possibleNumbers(45, 9);
        System.out.println("45, 9: " + result);
        System.out.println(" ");
    }

    @Test
    public void basicBoardTest() {
        // #3-
        Board b = new Board(1, 3, "#C-", "#C-");
        b.printBoard(System.out);
        System.out.println(" ");
    }


    @Test
    public void basicBoardSolveTest() {
        // #CG --> #CG
        // D-- --> D13
        // F-- --> F24
        Board b = new Board(3, 3, "###D--F--", "###C--G--");
        b.printBoard(System.out);
        System.out.println(" ");
        b.solve();
        System.out.println(" ");
    }

    @Test
    public void smallBoardSolveTest() {
        // #PK##HD# --> #PK##HD#
        // Q--#C--# --> Q98#C21#
        // J--#I--# --> J73#I63#
        // ######## --> ########
        // #DI##HQ# --> #DI##HQ#
        // J--#I--# --> J37#I18#
        // C--#P--# --> C12#P79#
        Board b = new Board(7, 8 , "########Q--#C--#J--#I--#################J--#I--#C--#P--#", "#######P--#D--K--#I--##############H--#H--D--#Q--#######");
        b.printBoard(System.out);
        System.out.println(" ");
        b.solve();
        System.out.println(" ");
    }

    @Test
    public void mediumBoardSolveTest() {
        // #############     #Dc#EPS###MPQ
        // K--U---##W---     #--#---#Te---
        // C--K---R-----     #--W---#-----
        // #P--D--c----#     #G--h--Q----#
        // W-----X---###     #-----P---PUN
        // d----P--K---- and #----I--c----
        // ###P---h-----     ##MJ---X-----
        // #c----P--N--#     #O----#--H--P
        // P-----W---Q--     #-----#---#--
        // M---##O---I--     #---###---#--
        Board b = new Board(10, 13, "#############K--U---##W---C--K---R-----#P--D--c----#W-----X---###d----P--K----###P---h-----#c----P--N--#P-----W---Q--M---##O---I--", "##########D--G--#O--c-----M---##W---J---E--h-----#P----I---#S---P--######Q--X---#T---c----#e-----H--M---P---##P---U-----Q--#N--P--");
        b.printBoard(System.out);
        System.out.println(" ");
        b.solve();
        System.out.println(" ");
    }

}

