/* Alex Wang
 * Period 4 Tully
 * 8/22/2016
 */
public class WumpusMap
{
	public static final int NUM_ROWS = 10;
	public static final int NUM_COLUMNS = 10;
	public static final	int NUM_PITS = 10;
	
	private WumpusSquare[][]grid;
	private int ladderC;
	private int ladderR;
	private int wumpusC;
	private int wumpusR;
	private int goldC;
	private int goldR;	
	
	public WumpusMap()
	{
		createMap();
	}
	
	public void createMap()
	{
		grid = new WumpusSquare[10][10];
		
 		for(int r=0;r<grid.length;r++)
 		{
 			for(int c=0;c<grid[0].length;c++)
 				grid[r][c]= new WumpusSquare();
 		}
			
		/*int randomR = (int)(Math.random()*10);
		int randomC = (int)(Math.random()*10);
		grid[randomR][randomC].setLadder(true);
		ladderR = randomR;
		ladderC = randomC;*/
		
		int randomR = 0;
		int randomC = 0;
		boolean placed = false;
		
		//pits
		for(int x=0;x<10;x++)
		{
		    randomR = (int)(Math.random()*10);
		    randomC = (int)(Math.random()*10);
		    
		    if(grid[randomR][randomC].getPit()==false)
		    	grid[randomR][randomC].setPit(true);
		    else
		    {
		    	x--;
		    	continue;
		    }
		    
		    if(randomR>0)
		    	//if(grid[randomR-1][randomC].getPit()==false)
		    	grid[randomR-1][randomC].setBreeze(true);
		    if(randomR<9)
		    	//if(grid[randomR+1][randomC].getPit()==false)
		    	grid[randomR+1][randomC].setBreeze(true);
		    if(randomC>0)
		    	//if(grid[randomR][randomC-1].getPit()==false)
		    	grid[randomR][randomC-1].setBreeze(true);
		    if(randomC<9)
		    	//if(grid[randomR][randomC+1].getPit()==false)
		    	grid[randomR][randomC+1].setBreeze(true);
		}
		
		for(int r=0;r<grid.length;r++)
 		{
 			for(int c=0;c<grid[0].length;c++)
 				if(grid[r][c].getPit() && grid[r][c].getBreeze())
 					grid[r][c].setBreeze(false);
 		}
		
		//gold
		do
		{
			randomR = (int)(Math.random()*10);
		    randomC = (int)(Math.random()*10);
		    if(grid[randomR][randomC].getPit() == false)
		    {
		    	grid[randomR][randomC].setGold(true);
		    	placed = true;
		    	goldR = randomR;
		    	goldC = randomC;
		    }
		}while(placed == false);
		
		placed = false;
		
		//wumpus + stench
		do
		{
			randomR = (int)(Math.random()*10);
		    randomC = (int)(Math.random()*10);
		    if(grid[randomR][randomC].getPit() == false)
		    {
		    	grid[randomR][randomC].setWumpus(true);
		    	wumpusC = randomC;
		    	wumpusR = randomR;
		    	
		    	if(randomR>0 && grid[randomR-1][randomC].getPit() == false)
		    		grid[randomR-1][randomC].setStench(true);
		    	if(randomR<9 && grid[randomR+1][randomC].getPit() == false)
		    		grid[randomR+1][randomC].setStench(true);
		    	if(randomC>0 && grid[randomR][randomC-1].getPit() == false)
		    		grid[randomR][randomC-1].setStench(true);
		    	if(randomC<9 && grid[randomR][randomC+1].getPit() == false)
		    		grid[randomR][randomC+1].setStench(true);
		    		
		    	placed = true;
		    }
		}while(placed == false);
		
		placed = false;
		
		//ladder
		do
		{
			randomR = (int)(Math.random()*10);
		    randomC = (int)(Math.random()*10);
		    if(grid[randomR][randomC].getPit() == false && grid[randomR][randomC].getGold() == false && grid[randomR][randomC].getWumpus() == false )
		    {
		    	grid[randomR][randomC].setLadder(true);
		    	placed = true;
		    }
		}while(placed == false);
		
		ladderC = randomC;
		ladderR = randomR;

	}
	
	public int getLadderCol()
	{
		return ladderC;
	}
	
    public int getLadderRow()
	{
		return ladderR;
	}
	
	public int getWumpusCol()
	{
		return wumpusC;
	}
	
    public int getWumpusRow()
	{
		return wumpusR;
	}
	
	public int getGoldCol()
	{
		return goldC;
	}
	
    public int getGoldRow()
	{
		return goldR;
	}
	
	public WumpusSquare getSquare(int row, int col)
	{
		return grid[row][col];
	}
	
	public String toString()
	{
		String s = "";
		for(WumpusSquare[] row: grid)
		{
			for(WumpusSquare a: row)
			{
				s+=a.toString();
			}
			s+="\n";
		}
		return s;
	}
}	