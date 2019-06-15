/* Alex Wang
 * Period 4 Tully
 * 8/22/2016
 */
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
public class WumpusFrame extends JFrame  
{
	public WumpusFrame(String frameName)
	{
		super(frameName);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		pack();
			
		WumpusPanel p = new WumpusPanel();
		Insets frameInsets = getInsets();
		int frameWidth = p.getWidth() + (frameInsets.left + frameInsets.right);
		int frameHeight = p.getHeight() + (frameInsets.top + frameInsets.bottom);
		setPreferredSize(new Dimension(frameWidth, frameHeight));
	
		setLayout(null);
		add(p);
		pack();
		setVisible(true);
	}
}