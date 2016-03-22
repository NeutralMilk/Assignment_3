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
	float easing;
	int move;
	boolean placed = false;

	public Ship(Main _main)
	{
		main = _main;	
		s = main.loadImage("1.png");
		pos = new PVector();
		easing = .7f;
	}

	public void update()
	{
		switch(move) 
		{
		  case 0:
		  {
			  float targetX = main.mouseX;
		      float dx = targetX - pos.x;
		      pos.x += dx * easing;
		   			    
		      float targetY = main.mouseY;
		      float dy = targetY - pos.y;
		      pos.y += dy * easing;
		      break;
		  }//end case 0
		  
		  case 1: 
		  {
			  for(int i = 0; i < 32; i++)
			  {
				  for(int j = 0; j < 17; j++)
				  {
					  if(pos.x < (main.cPosX[i] + main.width/32 + 1) && pos.x > main.cPosX[i] && pos.y < (main.cPosY[j] + main.height/18 + 1) && pos.y > main.cPosY[j])
					  {
						  if(main.occupied[i*j] == true)
						  {
							  break;
						  }
						  else
						  {
							  pos.x = main.cPosX[i] + main.width/64;
							  pos.y = main.cPosY[j] + main.height/36;
							  break;
						  }
					  }//end if
				  }//end for			
			 }//end for
		  }//end case 1
		  
		  case 2:
		  {
			  for(int i = 0; i < 32; i++)
			  {
				  for(int j = 0; j < 17; j++)
				  {
					  if(pos.x < (main.cPosX[i] + main.width/32 + 1) && pos.x > main.cPosX[i] && pos.y < (main.cPosY[j] + main.height/18 + 1) && pos.y > main.cPosY[j])
					  {
						  pos.x = main.cPosX[i] + main.width/64;
						  pos.y = main.cPosY[j] + main.height/36;
						  main.occupied[i*j] = true;
						  break;					  
					  }//end if
				  }//end for			
			 }//end for
		  }//end case 2
		  
		}
	}//end update()
	
	public void render()
	{
		
		switch(move) 
		{
		  case 0:
		  {
			  main.imageMode(CENTER);
			  main.image(s, main.mouseX, main.mouseY);
			  break;
		  }
		  
		  case 1: 
		  {
			  main.imageMode(CENTER);			
			  main.image(s, pos.x, pos.y);
			  break;
		  }
		  
		  case 2: 
		  {
			  main.imageMode(CENTER);
			  main.image(s, main.mouseX, main.mouseY);
			  break;
		  }
		}

		


	}
}