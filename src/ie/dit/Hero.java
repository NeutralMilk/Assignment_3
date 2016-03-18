package ie.dit;

import java.util.ArrayList;

import ddf.minim.Minim;
import processing.core.*;

public class Hero extends PApplet
{
	Main main;
	float direction;
	PVector velocity;
	PVector position;
	float jumpSpeed;
	float walkSpeed;
	float l;
	float r;
	float up;
	float down;
	PVector pos;
	float charX;
	float charY;
	float easing;
	
	float gravity = 0.5f;
	float ground;
	//gravity variables
	//float down = 2;
	//float acceleration = 1.3;
	//float damp = 0.75;
	
	public Hero(Main _main)
	{
		main = _main;	
		charX = 0;
		charY = main.height/2-main.width/100;
		ground = main.height/2;
		pos = new PVector(charX, ground);
		easing = 0.05f;
	}

	public void update()
	{
		  if (position.y < ground)
		  {
		    velocity.y += gravity;
		  }
		  else
		  {
		    velocity.y = 0;
		  }
		   

		  if (position.y >= ground && up != 0)
		  {
		    velocity.y = -jumpSpeed;
		  }
		   

		  velocity.x = walkSpeed * (l + r);

		  PVector nextPosition = new PVector(position.x, position.y);
		  nextPosition.add(velocity);
		   
		  // Check collision with edge of screen and don't move if at the edge
		  float offset = 0;
		  if (nextPosition.x > offset && nextPosition.x < (width - offset))
		  {
		    position.x = nextPosition.x;
		  }
		  if (nextPosition.y > offset && nextPosition.y < (height - offset))
		  {
		    position.y = nextPosition.y;
		  }

	}
	public void render()
	{
		easing = 0.05f;
		float targetX = charX;
	    float dx = targetX - position.x;
	    position.x += dx * easing;
	    
	    //jumping
	    
	    easing = .1f;
	    float targetY = ground;
	    float dy = targetY - position.y;
	    position.y += dy * easing;
	    
	    //if()
	    main.fill(255);
		main.rect(0, 0, main.width/100, main.width/100);
	}
}