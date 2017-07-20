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

    private static final String JUMP = "JUMP";

    private int elapsedTime = 0;

    public GameBoard()
    {
        //Set up key bindings
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("UP"), JUMP);
        getActionMap().put(JUMP, new JumpAction());
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("SPACE"), JUMP);
        getActionMap().put(JUMP, new JumpAction());

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
            if(collisionPlayer(ob, count))
            {
                System.out.println("Collision");
                gameTimer.stop();
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

        int xVel = (random.nextInt(2) + 1) * -1;
        int size = (random.nextInt(5) + 1) * 10; //Meaning 5 different obstacle sizes

        return new Obstacle(xVel, size, playerBlock.getYStartPos() + playerBlock.getHEIGHT() - size);
    }

    private boolean collisionPlayer(Obstacle obstacle, int obIndex)
    {
        boolean yColl = false;
        boolean xColl = false;

        //We match the lower left corner from the player with the upper right hand corner from the obstacle
        int playerLowerLeftCornerY = playerBlock.getYPos() + playerBlock.getHEIGHT();

        int playerUpperRightX = playerBlock.getXStartPos() + playerBlock.getWIDTH();

        if(playerUpperRightX == obstacle.getXPos()) //>
        {
            xColl = true;

            if(playerLowerLeftCornerY > obstacle.getYPos()) //<
            {
                yColl = true;
            }
        }

        return (xColl && yColl);
    }

}
