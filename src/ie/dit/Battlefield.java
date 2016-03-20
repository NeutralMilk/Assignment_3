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
	PImage[] images = new PImage[3];
	

	public Battlefield(Main _main)
	{
		main = _main;	
		x = 0;
		y = 0;
		size = main.width/32;
		
		for ( int i = 0; i< images.length; i++ )
		{
			images[i] = main.loadImage( i + ".png" );
		}
	}
	
	public void update()
	{

	}
		
	public void render()
	{
		for(int i = 0; i < main.width; i += main.width/32)
		{
			for(int j = 0; j < main.height - main.height/18; j += main.height/18)
			{		
				/*if (bits[i] == true)
			    {
					f = color(100);
			    }
			    else
			    {
					f = color(51,204,255);
			    }*/
				main.fill(f);
				main.stroke(s);
				main.rect(x+i, y+j, size, size);
			}   
		}  
		
		for(int i = 0; i < 32; i ++)
		{
			main.imageMode(CENTER);
			main.fill(255);
			main.stroke(255);
			main.rect(i * main.width/32, main.height - size, size, size);
			if(i < images.length)
			{
				main.image(images[i], i * main.width/32 - size/2, main.height - size/2);
			}
		}
	}
}