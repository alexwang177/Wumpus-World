/* Alex Wang
 * Period 4 Tully
 * 8/22/2016
 */
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
public class WumpusPanel extends JPanel implements KeyListener
{
	public static final int PLAYING = 0;
	public static final int DEAD =  1;
	public static final int WON =  2;
	
	private int status;
	private boolean cheat;
	private int scream;
	private WumpusPlayer player;
	private WumpusMap map;
	private BufferedImage buffer;
	private BufferedImage arrow;
	private BufferedImage gold;
	private BufferedImage fog;
	private BufferedImage ladder;
	private BufferedImage pit;
	private BufferedImage breeze;
	private BufferedImage wumpus;
	private BufferedImage deadWumpus;
	private BufferedImage stench;
	private BufferedImage playerUp;
	private BufferedImage playerDown;
	private BufferedImage playerLeft;
	private BufferedImage playerRight;
	private BufferedImage floor;
	private BufferedImage black;
	
	public WumpusPanel()
	{
		super();
		setSize(600,670);
		addKeyListener(this);
		buffer = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
		 
		try
		{
			arrow = ImageIO.read((new File("Wumpus World Images//arrow.gif")));
			//fog = ImageIO.read((new File("Wumpus World Images\\fog.gif")));
			gold = ImageIO.read((new File("Wumpus World Images//gold.gif")));
			ladder = ImageIO.read((new File("Wumpus World Images//ladder.gif")));
			pit = ImageIO.read((new File("Wumpus World Images//pit.gif")));
			breeze = ImageIO.read((new File("Wumpus World Images//breeze.gif")));
			wumpus = ImageIO.read((new File("Wumpus World Images//wumpus.gif")));
			deadWumpus = ImageIO.read((new File("Wumpus World Images//deadwumpus.gif")));
			stench = ImageIO.read((new File("Wumpus World Images//stench.gif")));
			playerUp = ImageIO.read((new File("Wumpus World Images//playerUp.png")));
			playerDown = ImageIO.read((new File("Wumpus World Images//playerDown.png")));
			playerLeft = ImageIO.read((new File("Wumpus World Images//playerLeft.png")));
			playerRight = ImageIO.read((new File("Wumpus World Images//playerRight.png")));
			fog = ImageIO.read((new File("Wumpus World Images//black.gif")));
			floor = ImageIO.read((new File("Wumpus World Images//Floor.gif")));
			System.out.println("All images were loaded properly");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("Error Loading Images: " + e.getMessage());
		}
		reset();
	}
	
	public void reset()
	{
		status = PLAYING;
		
		map = new WumpusMap();
		player = new WumpusPlayer();
		player.setRowPosition(map.getLadderRow());
		player.setColPosition(map.getLadderCol());
		cheat = false;
	}
	
	public void paint(Graphics g)
	{
		Graphics bg = buffer.getGraphics();
        // fills in a black background
		bg.setColor(Color.GRAY);
		bg.fillRect(0,0,getWidth(),getHeight());
		bg.setColor(Color.BLACK);
		//bg.fillRect(50,25,500,500);
		bg.fillRect(0,570,180,130);
		bg.fillRect(186,570,414,130);
		
		// draws both images to the screen
		//bg.drawImage(floor, 50,50,null);
		for(int r=0;r<10;r++)
		{
			for(int c=0;c<10;c++)
			{
				//if(map.getSquare(r,c).getVisited())
					bg.drawImage(floor,(r+1)*50,(c+1)*50-25,null);
			}				
		} 
			
		bg.drawImage(ladder,(map.getLadderRow()+1)*50, (map.getLadderCol()+1)*50-25,null);
		
		//gold
		if(player.getGold()==false)
			bg.drawImage(gold,(map.getGoldRow()+1)*50, (map.getGoldCol()+1)*50-25,null);
		
		//wumpus/deadWumpus
		if(map.getSquare(map.getWumpusRow(),map.getWumpusCol()).getWumpus())
			bg.drawImage(wumpus,(map.getWumpusRow()+1)*50, (map.getWumpusCol()+1)*50-25,null);
		if(map.getSquare(map.getWumpusRow(),map.getWumpusCol()).getDeadWumpus())
			bg.drawImage(deadWumpus,(map.getWumpusRow()+1)*50, (map.getWumpusCol()+1)*50-25,null);
			
		for(int r=0;r<10;r++)
		{
			for(int c=0;c<10;c++)
			{
				if(map.getSquare(r,c).getStench())
					bg.drawImage(stench,(r+1)*50,(c+1)*50-25,null);	
			}				
		} 
		
		//pits+breeze
		for(int r=0;r<10;r++)
		{
			for(int c=0;c<10;c++)
			{
				if(map.getSquare(r,c).getPit())
					bg.drawImage(pit,(r+1)*50,(c+1)*50-25,null);	
				if(map.getSquare(r,c).getBreeze())
					bg.drawImage(breeze,(r+1)*50,(c+1)*50-25,null);	
			}				
		} 
			
		//fog of war
		for(int r=0;r<10;r++)
		{
			for(int c=0;c<10;c++)
			{
				    if(player.getRowPosition() == r && player.getColPosition() == c)
				    	map.getSquare(r,c).setVisited(true);
			}				
		}
		
		if(cheat == false)
		{
			for(int r=0;r<10;r++)
			{
				for(int c=0;c<10;c++)
				{
					    if(map.getSquare(r,c).getVisited()==false)
					    	bg.drawImage(fog,(r+1)*50,(c+1)*50-25,null);
				}				
			}
		}
		
		//player
		int pc = player.getColPosition();
		int pr = player.getRowPosition();
		if(/*pr==map.getWumpusRow() && pc==map.getWumpusCol()*/map.getSquare(pr,pc).getWumpus() || map.getSquare(pr,pc).getPit())
			status = DEAD;
		
		if(player.getDirection() == player.NORTH)
			bg.drawImage(playerUp,(pr+1)*50,(pc+1)*50-25,null);	
		if(player.getDirection() == player.SOUTH)
			bg.drawImage(playerDown,(pr+1)*50,(pc+1)*50-25,null);	
		if(player.getDirection() == player.EAST)
			bg.drawImage(playerRight,(pr+1)*50,(pc+1)*50-25,null);	
		if(player.getDirection() == player.WEST)
			bg.drawImage(playerLeft,(pr+1)*50,(pc+1)*50-25,null);	
				
		//Messages/Inventory
		bg.setColor(Color.RED);
		Font f = new Font("Times New Roman",Font.BOLD,25);
		Font f2 = new Font("Times New Roman",Font.BOLD,15);
		bg.setFont(f);
		bg.drawString("Inventory:",30,590);
		bg.drawString("Messages:",200,590);
		
		if(player.getArrow())
			 bg.drawImage(arrow,30,605,null);
		if(player.getGold())
			 bg.drawImage(gold,80,605,null);
				
		bg.setColor(Color.BLUE);
		bg.setFont(f2);
		
		int counter = 0;
		
		if((map.getSquare(pr,pc).getStench() && status != WON) || map.getSquare(pr,pc).getDeadWumpus())
		{
			bg.drawString("You smell a stench.",200,605 + (20*counter));
			counter++;
		}
			
		if(map.getSquare(pr,pc).getBreeze() && status != WON)
		{
			bg.drawString("You feel a breeze.",200,605 + (20*counter));
			counter++;
		}
			
		if(pr == map.getGoldRow() && pc == map.getGoldCol())
		{
			bg.drawString("You see a glimmer. P to pick up.",200,605 + (20*counter));
			counter++;
		}
			
		if(map.getSquare(pr,pc).getLadder() && status != WON)
		{
			if(player.getGold())
				bg.drawString("You bump into a ladder. C to climb.",200,605 + (20*counter));
			else
				bg.drawString("You bump into a ladder.",200,605 + (20*counter));
		    counter++;
		}
		    
		if(map.getSquare(pr,pc).getPit())
		{
			bg.drawString("You fell down a pit to your death. N for new game.",200,605 + (20*counter));
		    counter++;
		}
		    
		if(map.getSquare(pr,pc).getWumpus())
		{
			bg.drawString("You are eaten by the Wumpus. N for new game.",200,605 + (20*counter));
			counter++;
		}
		//You hear a scream
		if(scream==1)
		{
			bg.drawString("You hear a scream.",200,605 + (20*counter));
		    counter++;
		}
		
		if(status == WON)
			bg.drawString("You win. N for new game.",200,605);
        
        g.drawImage(buffer,0,0,null);
        repaint();
        
        // code to draw to the screen using Graphics Commands
	}
	
	public void keyPressed(KeyEvent e)
	{
		//Not used
	}
	
    public void keyReleased(KeyEvent e)
    {
    	//Not used
    }
	
	public void keyTyped(KeyEvent e)
	{
		 char key = e.getKeyChar();
		 
		 int wumpusC = map.getWumpusCol(); 
		 int wumpusR = map.getWumpusRow();
		 int playerC = player.getColPosition();
		 int playerR = player.getRowPosition();
		 
		 if(status==PLAYING)
		 {
		 	 if(key == 'w' && player.getColPosition()>0)
		 	 {
		 		player.setColPosition(player.getColPosition()-1);//row
		 		player.setDirection(player.NORTH);
		 		if(scream==1)
		 			scream--;
			 }
		 	 if(key == 'a' && player.getRowPosition()>0)
		 	 {
		 		player.setRowPosition(player.getRowPosition()-1);//col
		 		player.setDirection(player.WEST);
		 		if(scream==1)
		 			scream--;
			 }
		 	 if(key == 's' && player.getColPosition()<9)
			 {
		 		player.setColPosition(player.getColPosition()+1);//row
		 		player.setDirection(player.SOUTH);
		 		if(scream==1)
		 			scream--;
			 }
			 if(key == 'd' && player.getRowPosition()<9)
			 {
			 	player.setRowPosition(player.getRowPosition()+1);//col
			 	player.setDirection(player.EAST);
			 	if(scream==1)
			 		scream--;
			 }
			 
			 /*int wumpusC = map.getWumpusCol(); 
			 int wumpusR = map.getWumpusRow();
			 int playerC = player.getColPosition();
			 int playerR = player.getRowPosition();*/
			 //shooting the arrow
			 if(key == 'i' && player.getArrow())
			 {
			 	player.setArrow(false);
			 	player.setDirection(player.NORTH);
			 	if(wumpusR==playerR && wumpusC<playerC)
			 	{
			 		map.getSquare(wumpusR,wumpusC).setWumpus(false);
			 		map.getSquare(wumpusR,wumpusC).setDeadWumpus(true);
			 		scream++;
			 	}
			 }
			 if(key == 'k' && player.getArrow())
			 {
			 	player.setArrow(false);
			 	player.setDirection(player.SOUTH);
			 	if(wumpusR==playerR && wumpusC>playerC)
			 	{
			 		map.getSquare(wumpusR,wumpusC).setWumpus(false);
			 		map.getSquare(wumpusR,wumpusC).setDeadWumpus(true);
			 		scream++;
			 	}
			 }
			 if(key == 'j' && player.getArrow())
			 {
			 	player.setArrow(false);
			 	player.setDirection(player.WEST);
			    if(wumpusC==playerC && wumpusR<playerR)
			 	{
			 		map.getSquare(wumpusR,wumpusC).setWumpus(false);
			 		map.getSquare(wumpusR,wumpusC).setDeadWumpus(true);
			 		scream++;
			 	}
			 }
			 if(key == 'l' && player.getArrow())
			 {
			 	player.setArrow(false);
			 	player.setDirection(player.EAST);
			    if(wumpusC==playerC && wumpusR>playerR)
			 	{
			 		map.getSquare(wumpusR,wumpusC).setWumpus(false);
			 		map.getSquare(wumpusR,wumpusC).setDeadWumpus(true);
			 		scream++;
			 	}
			 }
		 }
		 
		 //climb ladder
		 if(key == 'c' && player.getGold() && map.getSquare(playerR,playerC).getLadder() /*&& playerC==map.getLadderCol() && playerR==map.getLadderRow()*/)
		 	status = WON;
		 	
		 if(key == 'p' && playerR == map.getGoldRow() && playerC == map.getGoldCol()/*map.getSquare(playerR,playerC).getGold()*/)
		 {
		 	player.setGold(true);
		 	map.getSquare(playerR,playerC).setGold(false);
		 }
		 	
		 if(key == 'n' && (status == WON || status == DEAD))
		 	reset();
		 
		 
		 if(key == '*')
		 {
		 	//figure it out
		 	if(scream==1)
		 		scream--;
		 	if(cheat == false)
		 		cheat = true;
		 	else
		 		cheat = false;
		 		
		 }
	}
	
    public void addNotify()
    {
    	super.addNotify();
    	requestFocus();
    }
}