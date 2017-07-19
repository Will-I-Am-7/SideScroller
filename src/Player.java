/**
 * Created by William Madgwick on 7/19/2017.
 * Our player will be represented by a rectangular block
 */
public class Player
{
    //The size of our block/player
    private final int WIDTH = 20;
    private final int HEIGHT = 50;
    private final int JUMP_HEIGHT = 360;

    //The starting position for our player
    //Position for the lower left-hand corner
    //Note: this position will stay the same in terms of the X position, only the Y position will change
    private final int xStartPos;
    private final int yStartPos;

    private int yPos;
    private int yVelocity = -4; //The jump velocity

    //Constructor
    public Player()
    {
        xStartPos = 100;
        yStartPos = 500;

        yPos = yStartPos;

    }

    //Getter and Setters
    public int getWIDTH()
    {
        return WIDTH;
    }

    public int getHEIGHT()
    {
        return HEIGHT;
    }

    public int getXStartPos()
    {
        return xStartPos;
    }

    public int getYStartPos()
    {
        return yStartPos;
    }

    public int getYPos()
    {
        return yPos;
    }

    public void setYPos(int yPos)
    {
        this.yPos = yPos;
    }

    public int getYVelocity()
    {
        return yVelocity;
    }

    public void setYVelocity(int yVelocity)
    {
        this.yVelocity = yVelocity;
    }

    public int getJUMP_HEIGHT()
    {
        return JUMP_HEIGHT;
    }
}
