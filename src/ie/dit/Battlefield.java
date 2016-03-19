package ie.dit;

import java.util.ArrayList;

import ddf.minim.Minim;
import processing.core.*;

public class Battlefield extends PApplet
{
	Main main;
	int f = color(51,204,255);
	int s = color(255);
	boolean[] bits = new boolean[576];
	int x = 0;
	int y = 0;
	int size = main.width/32;

	public Battlefield(Main _main)
	{
		main = _main;	
	}
	
	public void update()
	{
		for (int i = 0 ; i < bits.length ; i ++)
		  {
		    //float x = i * size;
		    //float y = main.height/2;
		    if (bits[i])
		    {
		      f = color(51,100,255);
		    }
		    else
		    {
		      f = color(51,204,255);
		    }
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