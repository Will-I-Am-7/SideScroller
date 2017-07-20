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

    //This will be the width and height
    private int size;

    public Obstacle()
    {
        xVelocity = -2;
        size = 50;

        initStartingPos();
    }

    public Obstacle(int xVelocity, int size)
    {
        this.xVelocity = xVelocity;
        this.size = size;

        initStartingPos();

    }

    private void initStartingPos()
    {
        xPos = GameBoard.BOARD_WIDTH - 50;
        yPos = 500;
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
}
