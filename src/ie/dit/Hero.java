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
	float speed = 50;
	float theta = 0.0f;
	
	public Hero(Main _main)
	{
		main = _main;	
		charX = 0;
		charY = main.height/2-main.width/100;
		pos = new PVector(charX, charY);
		easing = 0.1f;
		image = main.loadImage("ship.png");
	}

	public void update()
	{
		pos.x = - cos(theta);
	    pos.y = - sin(theta);
	    pos.mult(speed);
		if(main.keyPressed)
		{
			
			if (main.keys[0] == true) 
			{	  
				charX += speed;
				theta += 0.125f;
			}//end if
			  
			if (main.keys[1] == true) 
			{	  
				charX -= speed;
				theta -= 0.125f;
			}//end if
			
			if (main.keys[2] == true) 
			{	  
				charY -= speed;
			}//end if
			
			if (main.keys[3] == true) 
			{	  
				charY += speed;
			}//end if
		}
		
	}
	
	public void render()
	{
		float targetX = charX;
	    float dx = targetX - pos.x;
	    pos.x += dx * easing;
	    
	    //jumping
	    
	    float targetY = charY;
	    float dy = targetY - pos.y;
	    pos.y += dy * easing;
	    
	    main.image(image, pos.x, pos.y);
	    
	    
		//main.rect(pos.x, pos.y, main.width/100, main.width/100);
	}
}