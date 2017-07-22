/**
 * Created by William Madgwick on 7/19/2017.
 * The game board
 */
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import java.util.Random;

public class GameBoard extends JPanel implements ActionListener
{
    //Board width and height, that we want to access from other classes
    public static final int BOARD_WIDTH = 1200;
    public static final int BOARD_HEIGHT = 600;

    //The timer delay
    private final int DELAY = 1;

    private Timer gameTimer;
    private Timer jumpTimer;

    //The player object
    private Player playerBlock;
    private List<Obstacle> obstacles = new ArrayList<>();

    //Identifiers for the different key bindings and actions
    private static final String JUMP = "JUMP";
    private static final String EXIT = "EXIT";

    private int elapsedTime = 0;

    public GameBoard()
    {
        //Set up key bindings
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("UP"), JUMP);
        getActionMap().put(JUMP, new JumpAction());
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("SPACE"), JUMP);
        getActionMap().put(JUMP, new JumpAction());
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ESCAPE"), EXIT);
        getActionMap().put(EXIT, new ExitAction());


        playerBlock = new Player();

        //Generate the initial obstacle
        obstacles.add(generateObstacle());
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

        for (Obstacle ob : obstacles)
        {
            g.draw(ob.getObstacleShape());
        }

        Toolkit.getDefaultToolkit().sync();
    }

    //Handler for the general board timer
    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(elapsedTime == 1000)
        {
            obstacles.add(generateObstacle());
            elapsedTime = 0;
        }

        //The code for the obstacles
        moveObstacle();


        //Repaint the panel to show updates to our block
        repaint();

        elapsedTime+=DELAY;//Count the time elapsed
    }

    private class JumpAction extends AbstractAction
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            jumpTimer.start();
        }
    }

    private class ExitAction extends AbstractAction
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            System.exit(0);
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
                playerBlock.setYVelocity(1);
            }

            if(yPos == playerBlock.getYStartPos())
            {
                jumpTimer.stop();
                playerBlock.setYVelocity(-1);
            }

            playerBlock.setYPos(yPos);
        }
    }

    private void moveObstacle()
    {
        int count = 0;
        for (Obstacle ob : obstacles)
        {
            if(collisionPlayer(ob))
            {
                gameTimer.stop();
            }

            if(isObstaclesOffScreen(ob, count))
            {
                System.out.print("Off screen");
            }

            int xPos = ob.getXPos();
            int xVel = ob.getXVelocity();

            xPos += xVel;

            ob.setXPos(xPos);

            count++;
        }
    }

    private Obstacle generateObstacle()
    {
        Random random = new Random();

        int xVel = (random.nextInt(1) + 1) * -1;
        int size = (random.nextInt(6) + 1) * 10; //Meaning 5 different obstacle sizes

        return new Obstacle(xVel, size, playerBlock.getYStartPos() + playerBlock.getHEIGHT() - size);
    }

    private boolean collisionPlayer(Obstacle obstacle)
    {
        boolean didCollide = false;
        int slack = 2;

        //We match the lower left corner from the player with the upper right hand corner from the obstacle
        int playerLowerLeftCornerY = playerBlock.getYPos() + playerBlock.getHEIGHT();

        int playerUpperRightX = playerBlock.getXStartPos() + playerBlock.getWIDTH();

        int xPlayerLeftObstacleRight_Dist = (obstacle.getXPos() + obstacle.getSize()) - playerBlock.getXStartPos();
        int xPlayerRightObstacleLeft_Dist = obstacle.getXPos() - playerUpperRightX;

        if(xPlayerRightObstacleLeft_Dist < slack)
        {
            if(xPlayerLeftObstacleRight_Dist > slack)
            {
                if(playerLowerLeftCornerY > obstacle.getYPos())
                {
                    didCollide = true;
                }
            }
        }

        return (didCollide);
    }

    //Checks if an object is off screen
    private boolean isObstaclesOffScreen(Obstacle obstacle, int numObstacle)
    {
        if(obstacle.getXPos() < 0)
        {
            obstacles.remove(numObstacle);
            return true;
        }
        else
        {
            return false;
        }
    }

}
