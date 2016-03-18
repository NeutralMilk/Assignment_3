package ie.dit;

import java.util.ArrayList;

import ddf.minim.Minim;
import processing.core.*;

public class Hero extends PApplet
{
	PImage image;
	Main main;
	PVector pos;
	float charX;
	float charY;
	float easing;
	boolean jumped = false;
	boolean down = false;
	
	public Hero(Main _main)
	{
		main = _main;	
		charX = 0;
		charY = main.height/2-main.width/100;
		pos = new PVector(charX, charY);
		easing = 0.05f;
		//image = loadImage("square.png");
	}

	public void update()
	{
		if(down == true)
		{
			charY += main.width/50;
			jumped = false;
			down = false;
		}
		if(main.keyPressed)
		{
			
			if (main.keys[0] == true) 
			{	  
				charX += 10;
			}//end if
			  
			if (main.keys[1] == true) 
			{	  
				charX -= 10;;
			}//end if
			
			if(jumped == false)
			{
				if (main.keys[2] == true) 
				{
				    charY -= main.width/50;
				    down = true;    
				}
			}
		}
		
	}
	
	public void render()
	{
		easing = .05f;
		float targetX = charX;
	    float dx = targetX - pos.x;
	    pos.x += dx * easing;
	    
	    //jumping
	    
	    easing = .5f;
	    float targetY = charY;
	    float dy = targetY -pos.y;
	    pos.y += dy * easing;
	    
	    //if()
	    main.fill(255);
		main.rect(pos.x, pos.y, main.width/100, main.width/100);
	}
}