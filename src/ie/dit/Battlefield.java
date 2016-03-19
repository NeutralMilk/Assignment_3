package ie.dit;

import java.util.ArrayList;

import ddf.minim.Minim;
import processing.core.*;

public class Battlefield extends PApplet
{
	Main main;
	int f = color(51,204,255);
	int s = color(255);
	int x;
	int y;
	int size;
	boolean[] bits = new boolean[576];


	public Battlefield(Main _main)
	{
		main = _main;	
		x = 0;
		y = 0;
		size = main.width/32;
	}
	
	public void update()
	{
		for (int i = 0 ; i < bits.length ; i ++)
		  {
		    float x2 = i * size;
		    float y2 = y;
		    if (bits[i])
		    {
		    	f = color(100);
		    	println("works");
		    }
		    else
		    {
		    	f = color(51,204,255);
		    }
		    main.rect(x2,y2, size, size);
		  }
	}
	
	public void render()
	{
		
		
		for(int i = 0; i < main.width; i += main.width/32)
		{
			for(int j = 0; j < main.height; j += main.height/18)
			{
				main.fill(f);
				main.stroke(s);
				main.rect(x+i, y+j, size, size);
			}   
		}     
	}
}