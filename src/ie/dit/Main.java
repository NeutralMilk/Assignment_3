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
	Hero hero;
	                         
	int sampleRate = 44100;
	
	boolean[] keys = new boolean[4];
	
	
	public void settings() 
	{
		fullScreen();
	}
	
	public void setup()
	{
		smooth();
		hero = new Hero(this);
		//frameRate(120);
		
	}
	
	public void draw()
	{
		background(51,204,255);
		fill(50,255,100);
		stroke(50,255,100);
		stroke(255);			
		hero.update();	 
		hero.render();
		
		if(hero.pos.x >= width)
		{
			hero.charX = width;
		}
		
		if(hero.pos.x <= 0)
		{
			hero.charX = 0;
		}
		
		if(hero.pos.y >= height)
		{
			hero.charY = height;
		}
		
		if(hero.pos.y <= 0)
		{
			hero.charY = 0;
		}
	}

	
	public void keyPressed()
	{	  
		if (key == 'd') 
		{	  
			keys[0]=true;
		}//end if
		  
		if (key == 'a') 
		{	  
			keys[1]=true;
		}//end if
		
		if (key == 'w') 
		{	  
			keys[2]=true;
		}//end if
		
		if (key == 's') 
		{	  
			keys[3]=true;
		}//end if
	}
	
	public void keyReleased()
	{
		if (key == 'd') 
		{	  
			keys[0] = false;
		}//end if
		  
		if (key == 'a') 
		{	  
			keys[1] = false;
		}//end if
		
		if (key == 'w') 
		{	  
			keys[2] = false;
		}//end if
		
		if (key == 's') 
		{	  
			keys[3] = false;
		}//end if
		
	}
		
	public static void main(String[] args)
	{		
		PApplet.main(Main.class.getName());	
	}
}
