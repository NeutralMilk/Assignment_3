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
	
	//center position of each tile
	//PVector[] cPos = new PVector[2];
	int[] cPosX;
	int[] cPosY;
	
	//variables for making the ships
	boolean[] types = new boolean[2];
	boolean[] keys = new boolean[4];
	
	
	public void settings() 
	{
		fullScreen();
	}
	
	public void setup()
	{
		smooth();
		battlefield = new Battlefield(this);	

		for(int i = 0; i < 32; i ++)
		{
			for(int j = 0; j < 17; j ++)
			{		
				int a = (i * width/32) + width/64;
				int b = (j * height/18) + height/36;
				println(a,b);
				cPosX[i] = a;
				cPosY[j] = b;
			}   
		}  
	}
	
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
		}
		
		//spawn subs
		for(int i = 0; i < subs.size(); i++)
		{
			subs.get(i).render();			
			subs.get(i).update();
		}
			
		//sub.render();			
		//sub.update();
		
		if(types[0] == true)
		{
			shipCreate();
		}
		
		if(types[1] == true)
		{
			subCreate();
		}
		
	}
	
	public void shipCreate()
	{
		
		int i = numShips;
		Ship ship = new Ship(this);
		ships.add(ship); 
		ships.get(i).spawn = true;
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
		for(int i = 1; i < 3; i ++)
		{
			if(mouseX < i * battlefield.size && mouseX > i-1 * battlefield.size && mouseY < height && mouseY > height - battlefield.size)
			{	
				types[i-1] = true;
				println(types[i-1]);
				break;
			}
		}
	}
	
	public void mouseReleased()
	{
		for(int i = 0; i < ships.size(); i++)
		{
			if(ships.get(i).spawn == true)
			{
				ships.get(i).spawn = false;
				break;
			}
		}
		for(int i = 0; i < subs.size(); i++)
		{
			if(subs.get(i).spawn == true)
			{
				subs.get(i).spawn = false;
				break;
			}
		}
	}
	
		
	public static void main(String[] args)
	{		
		PApplet.main(Main.class.getName());	
	}
}
