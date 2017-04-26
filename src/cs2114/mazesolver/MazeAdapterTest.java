package cs2114.mazesolver;

import cs2114.mazesolver.MazeAdapter;
import cs2114.mazesolver.MazeCell;
import student.TestCase;

// -------------------------------------------------------------------------
/**
 * This is the test class to test each method and every scenario from the first
 * phase of the MazeAdapter class
 *
 * @author Meredith McGlynn (mmmere3)
 * @version 2012.09.28
 */
public class MazeAdapterTest
    extends TestCase
{
    private MazeAdapter board;
    private MazeAdapter winBoard;
    private MazeAdapter loseBoard;


    // ----------------------------------------------------------
    /**
     * Create a new MazeAdapterTest object.
     */
    public MazeAdapterTest()
    {
        // This is supposed to be empty in test cases
    }


    /**
     * Sets up the maze for tests.
     */
    public void setUp()
    {
        board = new MazeAdapter();
        board.createMaze(6);
        winBoard = new MazeAdapter();
        winBoard.createMaze(4);
        winBoard.placeWall(0, 1);
        winBoard.placeWall(1, 1);
        winBoard.placeWall(2, 1);
        winBoard.placeWall(2, 2);
        winBoard.placeWall(2, 3);
        loseBoard = new MazeAdapter();
        loseBoard.createMaze(4);
        loseBoard.placeWall(0, 1);
        loseBoard.placeWall(1, 1);
        loseBoard.placeWall(2, 1);
        loseBoard.placeWall(3, 1);
    }


    // ----------------------------------------------------------
    /**
     * This tests the placeWall() method, as well as what happens when it is
     * attempted to place Walls where Walls shouldn't be.
     */
    public void testPlaceWall()
    {
        Exception occurred = null;
        board.placeWall(1, 1);
        assertEquals(board.getCell(1, 1), MazeCell.WALL);

        try
        {
            board.placeWall(5, 5);
        }
        catch (Exception exception)
        {
            occurred = exception;
        }
        assertTrue(occurred instanceof IllegalArgumentException);
        assertEquals(
            "This cell cannot be made into a wall",
            occurred.getMessage());
        try
        {
            board.placeWall(0, 0);
        }
        catch (Exception exception)
        {
            occurred = exception;
        }
        assertTrue(occurred instanceof IllegalArgumentException);
        assertEquals(
            "This cell cannot be made into a wall",
            occurred.getMessage());
        board.setCell(4, 1, MazeCell.PATH);
        board.placeWall(4, 1);
        assertEquals(board.getCell(4, 1), MazeCell.WALL);
        assertNotSame(board.getCell(4, 1), MazeCell.PATH);
        try
        {
            board.placeWall(6, 7);
        }
        catch (Exception exception)
        {
            occurred = exception;
        }
        assertTrue(occurred instanceof IllegalArgumentException);
        assertEquals(
            "This cell cannot be made into a wall",
            occurred.getMessage());

    }


    // ----------------------------------------------------------
    /**
     * This tests the destroyWall(int x, int y) method, both for when a wall is
     * there and when it is not.
     */
    public void testDestroyWall()
    {
        board.placeWall(1, 1);
        board.destroyWall(1, 1);
        assertEquals(board.getCell(1, 1), MazeCell.PATH);
        board.setCell(2, 0, MazeCell.WALL);
        board.destroyWall(2, 0);
        assertEquals(board.getCell(2, 0), MazeCell.PATH);
        board.setCell(3, 2, MazeCell.PATH);
        board.destroyWall(3, 2);
        assertEquals(board.getCell(3, 2), MazeCell.PATH);
        assertFalse(board.isWall(3, 2));
        board.setCell(3, 2, MazeCell.WALL);
        board.destroyWall(3, 2);
        assertFalse(board.isWall(3, 2));
    }


    // ----------------------------------------------------------
    /**
     * This tests if a cell is a Wall or not.
     */
    public void testIsWall()
    {
        board.placeWall(2, 2);
        assertTrue(board.isWall(2, 2));
        board.destroyWall(2, 2);
        assertFalse(board.isWall(2, 2));
        board.destroyWall(2, 2);
        assertFalse(board.isWall(2, 2));
    }


    // ----------------------------------------------------------
    /**
     * This tests the solveMaze() method, in the losing case
     */
    public void testSolveLosingMaze()
    {
        assertNull(loseBoard.solveMaze());
    }


    // ----------------------------------------------------------
    /**
     * This tests the solveMaze() method for the winning case
     */
    public void testSolveWinningMaze()
    {
        assertEquals(
            "(0, 0) (1, 0) (2, 0) (3, 0) (3, 1) (3, 2) (3, 3) ",
            winBoard.solveMaze());
    }


    // ----------------------------------------------------------
    /**
     * This changes the winning board to test other paths.
     */
    public void testSolveWinningMaze2()
    {
        winBoard.destroyWall(1, 1);
        winBoard.destroyWall(2, 2);
        winBoard.placeWall(0, 2);
        winBoard.placeWall(1, 3);
        assertEquals(
            "(0, 0) (1, 0) (2, 0) (3, 0) (3, 1) (3, 2) (3, 3) ",
            winBoard.solveMaze());
    }

}
