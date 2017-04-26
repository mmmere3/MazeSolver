package cs2114.mazesolver;

import android.widget.CheckBox;
import android.view.View;
import android.widget.Toast;
import android.view.MotionEvent;
import sofia.graphics.Color;
import sofia.graphics.RectangleShape;
import sofia.app.ShapeScreen;

// -------------------------------------------------------------------------
/**
 * Write a one-sentence summary of your class here. Follow it with additional
 * details about its purpose, what abstraction it represents, and how to use it.
 *
 * @author (your name here)
 * @version (type the date in the format yyyy.mm.dd)
 */
public class MazeSolverScreen
    extends ShapeScreen
{
    // ~ Fields ................................................................

    private float              shapeHeight;
    private float              shapeWidth;
    private float              sizeOfMaze;
    private float              cellSize;
    private boolean            drawOrEraseWalls;
    private MazeAdapter        mazeBoard;
    private RectangleShape[][] guiBoard;


    // ~ Public methods ........................................................

    // ----------------------------------------------------------
    // ----------------------------------------------------------
    /**
     * Creates the maze and the cells to go in it.
     */
    public void initialize()
    {
        guiBoard = new RectangleShape[7][7];
        mazeBoard = new MazeAdapter();
        mazeBoard.createMaze(7);
        shapeHeight = getShapeView().getHeight();
        shapeWidth = getShapeView().getWidth();
        sizeOfMaze = Math.min(shapeHeight, shapeWidth);
        cellSize = sizeOfMaze / 7;
        for (int x = 0; x < sizeOfMaze; x++)
        {
            for (int y = 0; y < sizeOfMaze; y++)
            {
                guiBoard[x][y] =
                    new RectangleShape(
                        x * cellSize,
                        y * cellSize,
                        cellSize,
                        cellSize);
                guiBoard[x][y].setColor(Color.oldLace);
                guiBoard[x][y].setFilled(true);
                add(guiBoard[x][y]);

            }
        }
    }


    /**
     * Create a new MazeSolverScreen object.
     */
    public MazeSolverScreen()
    {
        initialize();
    }


    /**
     * This gets the maze from MazeAdapter
     *
     * @return mazeBoard the board containing the maze
     */
    public MazeAdapter getMaze()
    {
        return this.mazeBoard;
    }


    // ----------------------------------------------------------
    /**
     * What happens when the finger touches the screen, whether the button
     * "Draw Walls?" is clicked or not
     *
     * @param e
     *            when there is movement
     */
    public void onTouchDown(MotionEvent e)
    {
        shapeHeight = getShapeView().getHeight();
        shapeWidth = getShapeView().getWidth();

        sizeOfMaze = Math.min(shapeHeight, shapeWidth);
        cellSize = sizeOfMaze / 7;

        int x = (int)(e.getX() / cellSize);
        int y = (int)(e.getY() / cellSize);

        if (drawOrEraseWalls)
        {
            this.mazeBoard.setCell(x, y, MazeCell.WALL);
            this.guiBoard[x][y].setColor(Color.maroon);
            this.guiBoard[x][y].setFilled(true);
            add(guiBoard[x][y]);

        }

        else
        {
            this.mazeBoard.destroyWall(x, y);
            this.guiBoard[x][y].setColor(Color.oldLace);
            this.guiBoard[x][y].setFilled(true);
            add(guiBoard[x][y]);
        }

    }


    // ----------------------------------------------------------
    /**
     * When the touch moves across the screen, and what it should do if the
     * button "Draw Walls?" is clicked
     *
     * @param e
     *            when there is movement
     */
    public void onTouchMove(MotionEvent e)
    {
        shapeHeight = getShapeView().getHeight();
        shapeWidth = getShapeView().getWidth();

        sizeOfMaze = Math.min(shapeHeight, shapeWidth);
        cellSize = sizeOfMaze / 7;

        int x = (int)(e.getX() / cellSize);
        int y = (int)(e.getY() / cellSize);

        if (drawOrEraseWalls)
        {
            this.mazeBoard.setCell(x, y, MazeCell.WALL);
            this.guiBoard[x][y].setColor(Color.maroon);
            this.guiBoard[x][y].setFilled(true);
            add(guiBoard[x][y]);
        }

        else
        {
            this.mazeBoard.destroyWall(x, y);
            this.guiBoard[x][y].setColor(Color.oldLace);
            this.guiBoard[x][y].setFilled(true);
            add(guiBoard[x][y]);
        }
    }


    // ----------------------------------------------------------
    /**
     * Gets the GUI board
     *
     * @return the GUI board
     */
    public RectangleShape[][] getBoard()
    {
        return guiBoard;
    }


    // ----------------------------------------------------------
    /**
     * This checks to see if CheckBox is clicked
     *
     * @param view
     *            the screen
     */
    public void onCheckBoxClicked(View view)
    {
        drawOrEraseWalls = ((CheckBox)view).isChecked();

    }


    // ----------------------------------------------------------
    /**
     * This solves the maze on the screen and changes the colors of the cells to
     * fit with the solution
     *
     * @param view
     *            the view of the screen
     */
    public void solveMaze(View view)
    {
        mazeBoard.solveMaze();
        for (int x = 0; x < this.sizeOfMaze; x++)
        {
            for (int y = 0; y < this.sizeOfMaze; y++)
            {
                MazeCell cell = mazeBoard.getCell(x, y);
                if (cell == MazeCell.WALL)
                {
                    guiBoard[x][y].setColor(Color.maroon);
                    guiBoard[x][y].setFilled(true);
                    add(guiBoard[x][y]);
                }
                if (cell == MazeCell.PATH)
                {
                    guiBoard[x][y].setColor(Color.orange);
                    guiBoard[x][y].setFilled(true);
                    add(guiBoard[x][y]);
                }
                if (cell == MazeCell.VISITED)
                {
                    guiBoard[x][y].setColor(Color.black);
                    guiBoard[x][y].setFilled(true);
                    add(guiBoard[x][y]);
                }
            }
        }
        if (mazeBoard.solveMaze() == null)
        {
            Toast.makeText(this, "There is no solution", Toast.LENGTH_LONG)
                .show();
        }
        else
        {
            Toast.makeText(
                this,
                "The solution is " + mazeBoard.toString(),
                Toast.LENGTH_LONG).show();
        }
    }


    // ----------------------------------------------------------
    /**
     * This will clear the maze if the button is clicked
     *
     * @param view
     *            the view of the screen
     */
    public void clearMaze(View view)
    {
        for (int x = 0; x < this.sizeOfMaze; x++)
        {
            for (int y = 0; y < this.sizeOfMaze; y++)
            {
                mazeBoard.setCell(x, y, MazeCell.PATH);
            }
        }
    }

}
