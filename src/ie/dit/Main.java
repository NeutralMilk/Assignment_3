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
	int nrKeys;
	                         
	int sampleRate = 44100;
	
	boolean[] keys = new boolean[3];
	
	
	public void settings() 
	{
		fullScreen();
	}
	
	public void setup()
	{
		smooth();
		hero = new Hero (this);
		hero.position = new PVector(400, hero.ground);
		hero.direction = 1;
		hero.velocity = new PVector(0, 0);
		hero.jumpSpeed = 10;
		hero.walkSpeed = 4;

		//frameRate(120);
		
	}
	
	public void draw()
	{
		background(51,204,255);
		fill(50,255,100);
		stroke(50,255,100);
		rect(0,height/2,width, height/2);
		stroke(255);	
		
		nrKeys = 0;
		
		hero.update();
		pushMatrix();
		   
		translate(hero.position.x, hero.position.y);
		   
		  // Always scale after translate and rotate.
		  // We're using oldGuy.direction because a -1 scale flips the image in that direction.
		scale(hero.direction, 1);
		   
		hero.render();
		
		   
		popMatrix();
		
		
		
		
		
	}
	
	public void keyPressed()
	{
	  if (key == 'd')
	  {
	    hero.r = 1;
	    hero.direction = -1;
	  }
	  if (key == 'a')
	  {
		  hero.l = -1;
	      hero.direction = 1;
	  }
	  if (key == ' ')
	  {
		  hero.up = -1;
	  }
	  if (key == 's')
	  {
		  hero.down = 1;
	  }
	}
	 
	public void keyReleased()
	{
	  if (key == 'd')
	  {
		  hero.r = 0;
	  }
	  if (key == 'a')
	  {
		  hero.l = 0;
	  }
	  if (key == ' ')
	  {
		  hero.up = 0;
	  }
	  if (key == 's')
	  {
		  hero.down = 0;
	  }
	}

	
	/*public void keyPressed()
	{	  
		if (key == 'd') 
		{	  
			keys[0]=true;
		}//end if
		  
		if (key == 'a') 
		{	  
			keys[1]=true;
		}//end if
		
		if (key == ' ') 
		{
			keys[2]=true;
		}
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
		
		if (key == ' ') 
		{
			keys[2] = false;
		}
	}*/
		
	public static void main(String[] args)
	{		
		PApplet.main(Main.class.getName());	
	}
}
