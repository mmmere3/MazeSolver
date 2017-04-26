package cs2114.mazesolver;

import android.widget.Button;
import android.widget.CompoundButton;
import sofia.graphics.ShapeView;

// -------------------------------------------------------------------------
/**
 * Tests the GUI methods
 *
 * @author Meredith McGlynn
 * @version 12.10.24
 */
public class MazeSolverScreenTests
    extends student.AndroidTestCase<MazeSolverScreen>
{
    // ~ Fields ................................................................

    private ShapeView      shapeView;
    private CompoundButton drawEraseMode;
    private Button         clearMaze;
    private MazeAdapter    mazeBoard;

    // This field will store the pixel width/height of a cell in the maze.
    private int            cellSize;


    // ~ Constructors ..........................................................

    // ----------------------------------------------------------
    /**
     * Test cases that extend AndroidTestCase must have a parameterless
     * constructor that calls super() and passes it the screen/activity class
     * being tested.
     */
    public MazeSolverScreenTests()
    {
        super(MazeSolverScreen.class);
    }


    // ~ Public methods ........................................................

    // ----------------------------------------------------------
    /**
     * Initializes the text fixtures.
     */
    public void setUp()
    {
        getScreen().initialize();
        float viewSize =
            Math.min(getScreen().getWidth(), getScreen().getHeight());
        float cellSize = viewSize / 7;
        mazeBoard = new MazeAdapter();
        mazeBoard.createMaze(7);

        // TODO Add any other setup code that you need.
    }


    /**
     * Tests the draw walls function when the button "Draw Walls?" is clicked
     */
    public void testDrawWalls()
    {
        click(drawEraseMode);
        clickCell(1, 1);
        touchUp();
        MazeAdapter maze = getScreen().getMaze();
        assertEquals(maze.getCell(1, 1), MazeCell.WALL);
    }


    // ----------------------------------------------------------
    /**
     * Tests if walls are made when the finger is dragged across the screen
     */
    public void testDrawManyWalls()
    {
        click(drawEraseMode);
        touchDownCell(1, 0);
        touchMoveCell(3, 0);
        touchUp();
        MazeAdapter maze = getScreen().getMaze();
        assertEquals(maze.getCell(1, 0), MazeCell.WALL);
        //assertEquals(maze.getCell(2, 0), MazeCell.WALL);
        //assertEquals(maze.getCell(3, 0), MazeCell.WALL);
    }


    // ----------------------------------------------------------
    /**
     * Tests what happens when the "Draw Walls?" button is unclicked.
     */
    public void testNotDrawWalls()
    {
        clickCell(2, 2);
        touchUp();
        MazeAdapter maze = getScreen().getMaze();
        assertEquals(maze.getCell(2, 2), MazeCell.PATH);
    }


    // ----------------------------------------------------------
    /**
     * Tests the clearMaze button.
     */
    public void testClearMaze()
    {
        click(drawEraseMode);
        clickCell(3, 3);
        touchUp();
        click(clearMaze);
        MazeAdapter maze = getScreen().getMaze();
        assertNotSame(maze.getCell(3, 3), MazeCell.WALL);
    }


    // ~ Private methods .......................................................

    // ----------------------------------------------------------
    /**
     * Simulates touching down on the middle of the specified cell in the maze.
     *
     * @param x
     *            the x parameter of the cell
     * @param y
     *            the x parameter of the cell
     */
    public void touchDownCell(int x, int y)
    {
        touchDown(shapeView, (x + 0.5f) * cellSize, (y + 0.5f) * cellSize);
    }


    // ----------------------------------------------------------
    /**
     * Simulates moving the finger instantaneously to the middle of the
     * specified cell in the maze.
     *
     * @param x
     *            the x location of the touch
     * @param y
     *            the y location of the touch
     */
    public void touchMoveCell(int x, int y)
    {
        touchMove((x + 0.5f) * cellSize, (y + 0.5f) * cellSize);
    }


    // ----------------------------------------------------------
    /**
     * Simulates clicking the middle of the specified cell in the maze. This is
     * equivalent to calling: touchDownCell(x, y); touchUp();
     *
     * @param x
     *            the x parameter of the cell
     * @param y
     *            the y parameter of the cell
     */
    public void clickCell(int x, int y)
    {
        touchDownCell(x, y);
        touchUp();
    }

}
