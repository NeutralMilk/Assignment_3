package ie.dit;

import java.util.ArrayList;

import ddf.minim.Minim;
import processing.core.*;

public class Sub extends PApplet
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
	
	public Sub(Main _main)
	{
		main = _main;	
		s = main.loadImage("1.png");
		charX = 0;
		charY = main.height/2-main.width/100;
		pos = new PVector(charX, charY);
		easing = 0.05f;
		int w = (int)((s.width)*(1920/2560));
		
		println(w);
		//ship2.resize(w, 0);
	}

	public void update()
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

		}
	}
	
	public void render()
	{
		main.imageMode(CENTER);
	    main.image(s, pos.x, pos.y);
	}
}