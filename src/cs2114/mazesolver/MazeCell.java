package cs2114.mazesolver;

// -------------------------------------------------------------------------
/**
 * This is an enum class to declare different types of Maze Cells, which will be
 * used in a MazeCell board.
 *
 * @author Meredith McGlynn
 * @version 2012.09.28
 */
public enum MazeCell
{
    /**
     * This is for path cells, which can be traveled through
     */
    PATH,
    /**
     * This is for wall cells, which cannot be passed through.
     */
    WALL,
    /**
     * This is for visited cells, which cannot be passed through twice
     */
    VISITED, ;


}
