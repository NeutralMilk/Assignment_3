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
	ArrayList<Sub> subs = new ArrayList<Sub>();
	Sub sub;
	Battlefield battlefield;	
	int numShips = 0;	 
	int numSubs = 0;
	int sampleRate = 44100;	
	boolean[] occupied = new boolean[543];
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
		
		
		for ( int i = 0; i < 543; i++ )
		{
			occupied[i] = false;
		}//end for
	}//end setup
	
	public void draw()
	{
		background(51,120,255);
		//fill(,204,255);
		strokeWeight(.25f);

		battlefield.render();
		battlefield.update();
		
		//spawn ships
		for(int i = 0; i < ships.size(); i++)
		{
			ships.get(i).render();			
			ships.get(i).update();
		}//end for
		
		//spawn subs
		for(int i = 0; i < subs.size(); i++)
		{
			subs.get(i).render();			
			subs.get(i).update();
		}//end for
			
		//sub.render();			
		//sub.update();
		
		if(types[0] == true)
		{
			shipCreate();
		}//end if
		
		if(types[1] == true)
		{
			subCreate();
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
	
	public void subCreate()
	{
		
		int i = numSubs;
		Sub sub = new Sub(this);
		subs.add(sub); 
		subs.get(i).spawn = true;
		numSubs++;
		types[1] = false;
		
	}
	
	public void mousePressed()
	{
		//choose which one to spawn
		for(int i = 1; i < 3; i ++)
		{
			if(mouseX < i * battlefield.size && mouseX > i-1 * battlefield.size && mouseY < height && mouseY > height - battlefield.size)
			{	
				types[i-1] = true;
				break;
			}//end if
		}//end for
		
		
		if(move == true)
		{
			for(int i = 0; i < occupied.length; i++)
			{		
				if(occupied[i] = true)
				{
					for(int j = 0; j < ships.size(); j++)
					{
						ships.get(j).move = 2;
						move = false;
					}//end for					
				}//end if
			}//end for
		}//end if
	}//end mousePressed
	
	public void mouseReleased()
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
						move = true;
						break;			
					}//end if					
				}//end for
			}//end if
		}//end for	
	}//end mouseReleased
	
		
	public static void main(String[] args)
	{		
		PApplet.main(Main.class.getName());	
	}
}
