/**
 * Created by William Madgwick on 7/19/2017.
 * The game board
 */
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class GameBoard extends JPanel implements ActionListener
{
    //Board width and height, that we want to access from other classes
    public static final int BOARD_WIDTH = 1200;
    public static final int BOARD_HEIGHT = 600;

    //The timer delay
    private final int DELAY = 10;

    private Timer gameTimer;
    private Timer jumpTimer;

    //The player object
    private Player playerBlock;
    Obstacle obstacle;

    private static final String JUMP = "JUMP";

    public GameBoard()
    {
        //Set up key bindings
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("UP"), JUMP);
        getActionMap().put(JUMP, new JumpAction());
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("SPACE"), JUMP);
        getActionMap().put(JUMP, new JumpAction());

        playerBlock = new Player();
        obstacle = new Obstacle();
        initBoard();
    }

    //Initializes our game board
    private void initBoard()
    {
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
        setDoubleBuffered(true);

        gameTimer = new Timer(DELAY, this);
        gameTimer.start();

        jumpTimer = new Timer(DELAY, new JumpTimerHandler());
    }

    @Override
    public void paintComponent(Graphics graphics)
    {
        super.paintComponent(graphics);
        Graphics2D g = (Graphics2D) graphics;

        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        //Draw the rectangle (Our player)
        g.setColor(Color.WHITE);
        g.fillRect(playerBlock.getXStartPos(), playerBlock.getYPos(), playerBlock.getWIDTH(), playerBlock.getHEIGHT());

        Shape shape = new Rectangle(obstacle.getXPos(), obstacle.getYPos(), obstacle.getSize(), obstacle.getSize());
        g.draw(shape);

        Toolkit.getDefaultToolkit().sync();
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        //The code for the obstacles
        moveObstacle();

        //Repaint the panel to show updates to our block
        repaint();
    }

    private class JumpAction extends AbstractAction
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            jumpTimer.start();
        }
    }

    //The timer handle for jumping
    private class JumpTimerHandler implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            int yPos = playerBlock.getYPos();
            int yVelocity = playerBlock.getYVelocity();

            yPos+=yVelocity;

            //This will determine how high the player jumps
            if(yPos == playerBlock.getJUMP_HEIGHT())
            {
                playerBlock.setYVelocity(4);
            }

            if(yPos == playerBlock.getYStartPos())
            {
                jumpTimer.stop();
                playerBlock.setYVelocity(-4);
            }

            playerBlock.setYPos(yPos);
        }
    }

    private void moveObstacle()
    {
        int xPos = obstacle.getXPos();
        int xVel = obstacle.getXVelocity();

        xPos += xVel;

        if(xPos == 0)
        {
            obstacle.setXVelocity(0);
        }

        obstacle.setXPos(xPos);
    }

}
