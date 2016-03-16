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
	                         
	int sampleRate = 44100;
	
	public void settings() 
	{
		fullScreen();
	}
	
	public void setup()
	{
		smooth();
		minim = new Minim(this);
		
	}

	float charX = 0;
	float charY = height/2-width/100;
	float x = 0;
	float y = 0;
	float easing = 0.05f;
	public void draw()
	{
		background(51,204,255);
		fill(50,255,100);
		stroke(50,255,100);
		rect(0,height/2,width, height/2);
		stroke(255);		
		
		float targetX = charX+10;
	    float dx = targetX - x;
	    x += dx * easing;
	    
	    float targetY = charY;
	    float dy = targetY - y;
	    y += dy * easing;
	    
	    println(y);
	    
	    fill(255);
		rect(x, y, width/100, width/100);


	    
	}
	
	public void keyPressed()
	{	  
	  if (key == 'd') 
	  {	  
		charX += 10;
	  }//end if
	  
	  if (key == 'a') 
	  {	  
		charX -= 10;
	  }//end if
	  if (key == ' ') 
	  {
	    charY -=50;
	  }
	}
		
	public static void main(String[] args)
	{		
		PApplet.main(Main.class.getName());	
	}
}
