package ie.dit;

import java.util.ArrayList;
import processing.core.*;

public class Hero extends PApplet
{
	Main main;
	char l;
	char r;
	char jump;
	PVector pos;
	
	public Hero(Main _main)
	{
		main = _main;	
		pos = new PVector(main.charX, main.charY);
	}

	public void update()
	{
		if(main.keyPressed)
		{
			if (key == 'd') 
			{	  
				pos.add(10);
			}//end if
			  
			if (key == 'a') 
			{	  
				pos.sub(10);
			}//end if
			
			if (key == ' ') 
			{
			    main.charY -=50;
			}
		}
	}
	public void render()
	{
		float targetX = main.charX+10;
	    float dx = targetX - main.x;
	    main.x += dx * main.easing;
	    
	    //jumping
	    
	    float targetY = main.charY;
	    float dy = targetY - main.y;
	    main.y += dy * main.easing;
	    
	    //if()
	    main.fill(255);
		main.rect(main.x, main.y, main.width/100, main.width/100);
	}
}