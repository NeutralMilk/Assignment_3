package ie.dit;

import java.util.ArrayList;

import ddf.minim.Minim;
import processing.core.*;

public class Ship extends PApplet
{
	//PImage image;
	Main main;
	PVector pos;
	PImage s;
	float charX;
	float charY;
	float easing;
	float speed = 50;
	float theta = 0.0f;
	boolean spawn = false;
	boolean placed = false;
	
	public Ship(Main _main)
	{
		main = _main;	
		s = main.loadImage("1.png");
		charX = 0;
		charY = main.height/2-main.width/100;
		pos = new PVector(charX, charY);
		easing = .7f;
		int w = (int)((s.width)*(1920/2560));
	}

	public void update()
	{
		if(spawn == true)
		{
			if(main.mousePressed)
			{
				float targetX = main.mouseX;
			    float dx = targetX - pos.x;
			    pos.x += dx * easing;
			    
			    //jumping
			    
			    float targetY = main.mouseY;
			    float dy = targetY - pos.y;
			    pos.y += dy * easing;
			}//end if
		}//end if
		
		if(spawn == false)
		{
			for(int i = 0; i < 543; i++)
			{
				if(pos.x == main.cPos[i].x)
				{
					
				}
			}
			float targetX = ;
		    float dx = targetX - pos.x;
		    pos.x += dx * easing;
		    
		    //jumping
		    
		    float targetY = ;
		    float dy = targetY - pos.y;
		    pos.y += dy * easing;
		}
	}
	
	public void render()
	{
		
		if(spawn == true)
		{
			main.imageMode(CENTER);
		    main.image(s, main.mouseX, main.mouseY);
		}//end if
		
		if(spawn == false)
		{
			main.imageMode(CENTER);
		    main.image(s, pos.x, pos.y);
		}//end if
	}
}