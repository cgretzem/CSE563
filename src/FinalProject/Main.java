package FinalProject;
import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Dimension;



public class Main extends JFrame
{
    public static void main(String[] args)
    {
        JFrame frame = new JFrame("CSE360 Final Project");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		Dimension maxSize = new Dimension(800,800);
		Dimension minSize = new Dimension(500,500);
		frame.setMaximumSize(maxSize);
		frame.setMinimumSize(minSize);

		frame.setVisible(true);
    }
}
