package ie.dit;

import java.util.ArrayList;

import processing.core.*;
import ddf.minim.AudioInput;
import ddf.minim.Minim;
import ddf.minim.analysis.FFT;
import ddf.minim.analysis.WindowFunction;

public class Main extends PApplet
{
	Minim minim;
	AudioInput in;
	float min;
	float max;	
	ArrayList<Ship> ships = new ArrayList<Ship>();
	Battlefield battlefield;	
	int numShips = 0;	 
	int numSubs = 0;
	int sampleRate = 44100;	
	boolean[] occupied = new boolean[544];
	boolean move = false;
	boolean moveAgain = false;
	
	//center position of each tile
	int[] cPosX = new int[32];
	int[] cPosY = new int[17];
	
	//variables for making the ships
	boolean[] types = new boolean[2];
	boolean[] keys = new boolean[4];
	boolean release = false;
	
	
	public void settings() 
	{
		fullScreen();
	}//end settings
	
	public void setup()
	{
		smooth();
		battlefield = new Battlefield(this);	

		for(int i = 0; i < 32; i ++)
		{
			for(int j = 0; j < 17; j ++)
			{		
				int a = (i * width/32);
				int b = (j * height/18);

				cPosX[i] = a;
				cPosY[j] = b;
			}//end for   
		}  //end for
		
		
		for( int i = 0; i < occupied.length; i++ )
		{
			occupied[i] = false;
		}//end for
	}//end setup
	
	public void draw()
	{
		for( int i = 0; i < occupied.length; i++ )
		{
			println(i, occupied[i]);
		}//end for
		background(51,120,255);
		//fill(,204,255);
		strokeWeight(.25f);

		battlefield.render();
		battlefield.update();
		
		
		
		if(mousePressed)
		{
				
		}//end if
		
		//spawn ships
		for(int i = 0; i < ships.size(); i++)
		{
			ships.get(i).render();			
			ships.get(i).update();
		}//end for
		
		if(types[0] == true)
		{
			shipCreate();
		}//end if

		
	}
	
	//creates a ship
	public void shipCreate()
	{
		//increase the number of ships
		int i = numShips;
		//create a ship
		Ship ship = new Ship(this);
		ships.add(ship); 
		//set it so that the ship follows the mouse
		ships.get(i).move = 0;
		numShips++;
		types[0] = false;	
	}

	int clicked;
	
	public void mouseDragged()
	{
		
	}
	public void mouseClicked()
	{			
		for(int i = 0; i < occupied.length; i++)
		{
			if(occupied[i] == true)
			{
				for(int j = 0; j < ships.size(); j++)
				{
					if(ships.get(j).move == 0)
					{
						ships.get(j).move = 1;
						occupied[i] = true;
						clicked = 2;
						break;			
					}//end if					
				}//end for
			}//end if
		}//end for	
		
 		switch(clicked)
		{
			//choose which one to spawn
			case 0:
			{
				for(int i = 1; i < 3; i ++)
				{
					if(mouseX < i * battlefield.size && mouseX > i-1 * battlefield.size && mouseY < height && mouseY > height - battlefield.size)
					{	
						types[i-1] = true;
						clicked = 1;
						break;
					}//end if
				}//end for	
				
				/*for(int i = 1; i < ships.size(); i ++)
				{
					if(ships.get(i).pos.x == mouseX && ships.get(i).pos.y == mouseY)
					{	
						ships.get(i).move = 2;
					}//end if
				}//end for*/
			}//end case 0
			
			case 1:
			{
				for(int i = 0; i < occupied.length; i++)
				{
					if(occupied[i] == false)
					{
						for(int j = 0; j < ships.size(); j++)
						{
							if(ships.get(j).move == 0)
							{
								ships.get(j).move = 1;
								occupied[i] = true;
								clicked = 0;
								break;			
							}//end if					
						}//end for
					}//end if
				}//end for	
			}//end case 1
			
			case 2:
			{
				for(int j = 0; j < ships.size(); j++)
				{
					if(ships.get(j).move == 1)
					{
						ships.get(j).move = 2;
						clicked = 0;
						break;			
					}//end if					
				}//end for
			}//end case 2
		}//end switch
	}//end mousePressed
	
	/*public void mouseReleased()
	{
		
		
	}//end mouseReleased*/
	
		
	public static void main(String[] args)
	{		
		PApplet.main(Main.class.getName());	
	}
}
