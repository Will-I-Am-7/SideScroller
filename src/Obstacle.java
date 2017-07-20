import java.awt.*;

/**
 * Created by William Madgwick on 7/20/2017.
 * The obstacles that the player will have to jump over
 * These obstacles are square shaped
 */
public class Obstacle
{
    private int xPos;
    private int yPos;
    private int xVelocity;

    //How long in milliseconds before the obstacle starts to show
    private int startTime;

    //This will be the width and height
    private int size;

    public Obstacle()
    {
        xVelocity = -3;
        size = 50;

        initStartingPos(500);
    }

    public Obstacle(int xVelocity, int size, int startYPos)
    {
        this.xVelocity = xVelocity;
        this.size = size;

        initStartingPos(startYPos);

    }

    //Initialize the starting position for all of the obstacles
    private void initStartingPos(int startYPos)
    {
        xPos = GameBoard.BOARD_WIDTH;
        yPos = startYPos;
    }

    public int getXPos()
    {
        return xPos;
    }

    public void setXPos(int xPos)
    {
        this.xPos = xPos;
    }

    public int getYPos()
    {
        return yPos;
    }

    public void setYPos(int yPos)
    {
        this.yPos = yPos;
    }

    public int getXVelocity()
    {
        return xVelocity;
    }

    public void setXVelocity(int xVelocity)
    {
        this.xVelocity = xVelocity;
    }

    public int getSize()
    {
        return size;
    }

    public void setSize(int size)
    {
        this.size = size;
    }

    //Return a new shape to be drawn based on the size and positions
    public Shape getObstacleShape()
    {
        return new Rectangle(xPos, yPos, size, size);
    }
}
