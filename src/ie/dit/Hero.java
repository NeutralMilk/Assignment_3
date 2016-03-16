package ie.dit;

import java.util.ArrayList;
import processing.core.*;

public class Hero extends PApplet
{
	Main main;
	public Hero (Main _main)
	{
		main = _main;
		
		public void update()
		{
			
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
			main.rect(main.x, main.y, width/100, width/100);
		}
		
	}
}