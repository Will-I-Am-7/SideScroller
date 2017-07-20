/**
 * Created by William Madgwick on 7/19/2017.
 * The game board
 */
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
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

    private static final String JUMP = "JUMP";

    public GameBoard()
    {
        //Set up key bindings
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("UP"), JUMP);
        getActionMap().put(JUMP, new JumpAction());
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("SPACE"), JUMP);
        getActionMap().put(JUMP, new JumpAction());

        playerBlock = new Player();
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

        //Draw the rectangle (Our player)
        graphics.setColor(Color.WHITE);
        graphics.fillRect(playerBlock.getXStartPos(), playerBlock.getYPos(), playerBlock.getWIDTH(), playerBlock.getHEIGHT());
        Toolkit.getDefaultToolkit().sync();
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
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

}
