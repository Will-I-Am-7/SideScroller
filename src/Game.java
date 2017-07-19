/**
 * Created by William Madgwick on 7/19/2017.
 * This class will contain the main method and get everything started
 */
import java.awt.EventQueue;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;

public class Game extends JFrame
{
    public Game()
    {
        initGame();
    }

    //This method initializes the game with the JFrame and all
    private void initGame()
    {
        add(new GameBoard());
        setResizable(false);
        pack();

        setTitle("Simple Side Scroller Game");
        setLocationRelativeTo(null); //Centers the frame
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public static void main(String[] args)
    {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new Game();
                frame.setVisible(true);
            }
        });
    }
}
