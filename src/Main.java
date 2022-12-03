import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Dimension;


/**
 * This is a main class where is the frame of GUI is created.
 *
 */
public class Main extends JFrame
{
	/**
	 * This is a main method where is the frame of GUI is created.
	 *
	 */
    public static void main(String[] args)
    {
        JFrame frame = new JFrame("CSE563 Final Project");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		Dimension maxSize = new Dimension(800,800);
		Dimension minSize = new Dimension(500,500);
		frame.setMaximumSize(maxSize);
		frame.setMinimumSize(minSize);
		Controller controller = new Controller(frame);
		//System.out.println(controller);
		controller.setVisible(true);
		frame.getContentPane().add(controller, BorderLayout.WEST);
		frame.pack();
		frame.setVisible(true);
    }
}
