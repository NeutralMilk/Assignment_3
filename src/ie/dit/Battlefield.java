package ie.dit;

import java.util.ArrayList;

import ddf.minim.Minim;
import processing.core.*;

public class Battlefield extends PApplet
{
	Main main;
	int f = color(68,193,235);
	int s = color(255);
	int x;
	int y;
	int size;
	boolean[] placeOil = new boolean[544];
	PImage[] images = new PImage[3];
	PImage oil = new PImage();
	
	

	public Battlefield(Main _main)
	{
		main = _main;	
		x = 0;
		y = 0;
		size = main.width/32;

		for(int i = 0; i < placeOil.length; i++)
		{
			int ran = (int)random(1,30);
			if(ran == 1)
			{
				placeOil[i] = true;
			}
			else
			{
				placeOil[i] = false;
			}
		}
		oil = main.loadImage("oil.png");

		for ( int i = 0; i< images.length; i++ )
		{
			images[i] = main.loadImage( i + ".png" );
		}//end for
	}
	
	public void update()
	{
        main.rectMode(CENTER);
		for(int i = 0; i < 32; i++)
		{
			for(int j = 0; j < 17; j++)
			{
				int a = main.cPosX[i];
				int b = main.cPosY[j];
				//if the mouse position is within the bounds of a box change the colour and keep the oil picture displaying
				if(main.mouseX < (a + main.width/32 + 1) && main.mouseX > a && main.mouseY < (b + main.height/18 + 1) && main.mouseY > b)
				{							
					if(main.occupied[i*j] == true)
					{
						s = color(0,255,0); 
					}//end if
								
					main.stroke(s);
					main.strokeWeight(3);
					main.fill(f);
					main.rect(main.cPosX[i], main.cPosY[j], size, size);
					if(placeOil[i*j] == true)
					{
						main.image(oil, i*main.width/32 + main.width/64, j*main.height/18 + main.height/36);
					}//end if
				}//end if			
			}//end for
		}//end for
	}
		
	public void render()
	{
        main.rectMode(CENTER);
		for(int i = 0; i < 32; i ++)
		{
			for(int j = 0; j < 17; j ++)
			{
				main.fill(f);
				s = color(255);
				main.stroke(s);
                main.rect(main.cPosX[i], main.cPosY[j], size, size);
				main.imageMode(CENTER);
				if(placeOil[i*j] == true)
				{
					main.image(oil, main.cPosX[i] + main.width/64, main.cPosY[j] + main.height/36);
				}//end if
				
			}//end for
		}//end for
		
		for(int i = 0; i < 32; i ++)
		{
			main.imageMode(CENTER);
			main.fill(255);
			main.stroke(255);
			main.rect(main.cPosX[i], main.height - size, size, size);
			if(i < images.length)
			{
				main.image(images[i], i * main.width/32 - size/2, main.height - size/2);
			}//end if
		}//end for
	}
}