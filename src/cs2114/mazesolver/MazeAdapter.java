package cs2114.mazesolver;

import java.util.Stack;
import android.graphics.Point;

// -------------------------------------------------------------------------
/**
 * This is part of phase 1 of the MazeAdapter, so this is implementing the
 * methods in MazeAdapter and writing the code for them, to design the data
 * model
 *
 * @author Meredith McGlynn (mmmere3)
 * @version 2012.09.28
 */
public class MazeAdapter
    extends MazeAdapterBase
{
    private int          size;
    private MazeCell[][] board;
    private Stack<Point> mazeStack;
    private String       answer;
    private String       info;
    private Point        startCell;


    // ----------------------------------------------------------
    /**
     * Create a new MazeAdapter object.
     */
    public MazeAdapter()
    {
        // This is null.
    }


    /**
     * This is the constructor that creates a new Maze, with both the height and
     * width each equal to "mazeSize"
     *
     * @param mazeSize
     *            the integer size that will be the height and width of the maze
     */
    public void createMaze(int mazeSize)
    {
        this.size = mazeSize;
        mazeBoard(size);
    }


    /**
     * This placeWall method will place a WALL cell in the given cell, as long
     * as the given cell is not the starter cell or the goal cell. If those two
     * cells are chosen, an exception will be thrown
     *
     * @param x
     *            the integer x value for the placement of the given cell
     * @param y
     *            the integer y value for the placement of the given cell
     */
    public void placeWall(int x, int y)
    {
        if (x == 0 && y == 0)
        {
            throw new IllegalArgumentException(
                "This cell cannot be made into a wall");
        }
        if (x >= size - 1 && y >= size - 1)
        {
            throw new IllegalArgumentException(
                "This cell cannot be made into a wall");
        }
        if (board[x][y] == MazeCell.PATH)
        {
            setCell(x, y, MazeCell.WALL);
        }
        if (board[x][y] == MazeCell.WALL)
        {
            setCell(x, y, MazeCell.WALL);
        }

    }


    /**
     * This will run a boolean to see if a cell is a Wall or not.
     *
     * @param x
     *            The x value of the board
     * @param y
     *            The y value of the board
     */
    public void destroyWall(int x, int y)
    {
        if (board[x][y] == MazeCell.WALL)
        {
            setCell(x, y, MazeCell.PATH);
        }
        if (board[x][y] == MazeCell.PATH)
        {
            setCell(x, y, MazeCell.PATH);
        }
    }


    /**
     * This will run a boolean to see if a cell is a Wall or not and return a
     * true or false value
     *
     * @param x
     *            The x value of the board
     * @param y
     *            The y value of the board
     * @return true or false The boolean value for the method
     */
    public boolean isWall(int x, int y)
    {
        if (board[x][y] == MazeCell.WALL)
        {
            return true;
        }
        else
        {
            return false;
        }
    }


    // ----------------------------------------------------------
    /**
     * This tests to see if the cell at the parameter can be a path
     *
     * @param x
     *            the x coordinate of the cell
     * @param y
     *            the y coordinate of the cell
     * @return true if it is a path, false if it's not a path.
     */
    public boolean isPath(int x, int y)
    {
        if (x < 0 || x >= board.length)
        {
            return false;
        }
        if (y < 0 || y >= board.length)
        {
            return false;
        }
        if (board[x][y] == MazeCell.WALL)
        {
            return false;
        }
        if (board[x][y] == MazeCell.VISITED)
        {
            return false;
        }
        else
        {
            return true;
        }
    }


    /**
     * This will solve the maze. For now, it is null.
     *
     * @return This returns null because this method isn't part of the
     *         submission yet.
     */

    public String solveMaze()
    {
        mazeStack = new Stack<Point>();
        startCell = new Point(0, 0);
        mazeStack.push(startCell);
        setCell(0, 0, MazeCell.PATH);
        while (!mazeStack.empty())
        {
            setCell(mazeStack.peek().x, mazeStack.peek().y, MazeCell.VISITED);
            if (mazeStack.peek().x == size - 1
                && mazeStack.peek().y == size - 1)
            {
                return toString();
            }
            else
            {
                if (isPath(mazeStack.peek().x + 1, mazeStack.peek().y))
                {
                    mazeStack.push(new Point(mazeStack.peek().x + 1, mazeStack
                        .peek().y));
                }
                else
                {
                    if (isPath(mazeStack.peek().x - 1, mazeStack.peek().y))
                    {
                        mazeStack.push(new Point(
                            mazeStack.peek().x - 1,
                            mazeStack.peek().y));
                    }
                    else
                    {
                        if (isPath(mazeStack.peek().x, mazeStack.peek().y + 1))
                        {
                            mazeStack.push(new Point(
                                mazeStack.peek().x,
                                mazeStack.peek().y + 1));
                        }
                        else
                        {
                            if (isPath(
                                mazeStack.peek().x,
                                mazeStack.peek().y - 1))
                            {
                                mazeStack.push(new Point(
                                    mazeStack.peek().x,
                                    mazeStack.peek().y - 1));
                            }
                            else
                            {
                                mazeStack.pop();
                            }
                        }
                    }
                }
            }
        }
        return null;
    }


    // ----------------------------------------------------------
    /**
     * Inverts the answer stack so that the bottom element is now at the top,
     * and then empties that into a string to show the path the solution took
     *
     * @return The string that contains the points in the solution.
     */
    public String toString()
    {
        answer = "";

        while (!mazeStack.empty())
        {
            info = "(" + mazeStack.peek().x + ", " + mazeStack.peek().y + ") ";
            answer = info + answer;
            mazeStack.pop();
        }
        return answer;
    }


    // ----------------------------------------------------------
    /**
     * This will change a MazeCell into either a PATH cell or a WALL cell
     *
     * @param x
     *            The x value of the board
     * @param y
     *            The y value of the board
     * @param cell
     *            The cell that the selected cell will be changed into
     */
    public void setCell(int x, int y, MazeCell cell)
    {
        board[x][y] = cell;
    }


    // ----------------------------------------------------------
    /**
     * Gets and returns the cell at the given coordinates
     *
     * @param x
     *            The x value of the cell
     * @param y
     *            The y value of the cell
     * @return The cell at that point.
     */
    public MazeCell getCell(int x, int y)
    {
        return board[x][y];
    }


    // ----------------------------------------------------------
    /**
     * This creates a maze board at a specified size and returns this board
     *
     * @param mazeSize
     *            The size of the maze. The size is the same value for the
     *            height and width
     * @return board Returns a MazeCell[][] of the specified size.
     */
    public MazeCell[][] mazeBoard(int mazeSize)
    {
        board = new MazeCell[mazeSize][mazeSize];
        for (int x = 0; x <= (size - 1); x++)
        {
            for (int y = 0; y <= (size - 1); y++)
            {
                setCell(x, y, MazeCell.PATH);
            }
        }
        return board;
    }
}
