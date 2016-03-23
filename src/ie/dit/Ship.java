package ie.dit;

import java.util.ArrayList;

import ddf.minim.Minim;
import processing.core.*;

public class Ship extends PApplet
{
	//PImage image;
	Main main;
	PVector pos;
	PVector mouseBox;
	PImage s;
	float easing;
	int move;
	boolean placed = false;
	
	float angle = 0;
	float oldAngle = 0;

	public Ship(Main _main)
	{
		main = _main;	
		s = main.loadImage("1.png");
		pos = new PVector();
		mouseBox = new PVector();
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
					  }//end if 
					  
					  if(main.mouseX < (main.cPosX[i] + main.width/32) && main.mouseX > main.cPosX[i] && main.mouseY < (main.cPosY[j] + main.height/18) && main.mouseY > main.cPosY[j])
					  {
						  println("works");
						  mouseBox.x = main.cPosX[i] + main.width/64;
						  mouseBox.y = main.cPosY[j] + main.height/36;
					  }//end if	
					  
					  if(main.mousePressed)
					  {
						  easing = .05f;
						  float targetX = mouseBox.x;
					      float dx = targetX - pos.x;
					      pos.x += dx * easing;
					   			    
					      float targetY = mouseBox.y;
					      float dy = targetY - pos.y;
					      pos.y += dy * easing;
					      easing = .7f;
					  }
				  }//end for			
			 }//end for
		  }//end case 2
		}//end switch
	}//end update()
	
	public void render()
	{
		
		switch(move) 
		{
		  case 0:
		  {
			  main.pushMatrix();
			  main.translate(main.mouseX, main.mouseY);
			  main.imageMode(CENTER);
			  main.image(s, 0, 0);
			  main.popMatrix();
			  break;
		  }
		  
		  case 1: 
		  {
			  main.pushMatrix();
			  main.translate(pos.x, pos.y);
			  main.imageMode(CENTER);			
			  main.image(s, 0, 0);
			  main.popMatrix();
			  break;
		  }
		  
		  case 2: 
		  {
			  main.pushMatrix();
			  angle = atan2(-(pos.x - mouseBox.x), -(pos.y - mouseBox.y));
			  main.translate(pos.x, pos.y);
			  main.rotate(-angle+PI);
			  main.imageMode(CENTER);			
			  main.image(s, 0, 0);
			  main.popMatrix();
			  break;
		  }

		}//end switch
	}
}