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
	PImage friendlyCity = new PImage();
	PImage oil = new PImage();

	boolean highlight = false;



	public Battlefield(Main _main)
	{
		main = _main;
		x = 0;
		y = 0;
		size = main.width/32;

		friendlyCity = main.loadImage("city.png");

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
			int w = images[i].width * main.width/2560;
			int h = images[i].height * main.height/1440;
			images[i].resize(w,h);
		}//end for

		int w = friendlyCity.width * main.width/2560;
		int h = friendlyCity.height * main.height/1440;
		friendlyCity.resize(w,h);

		w = oil.width * main.width/2560;
		h = oil.height * main.height/1440;
		oil.resize(w,h);
	}

	boolean colourGreen = false;
	boolean colourRed = false;
	boolean hover = false;
	int turnCount = 0;
	public void update()
	{
		for(int i = 0; i < 32; i++)
		{
			for(int j = 0; j < 17; j++)
			{
				//if the mouse position is within the bounds of a box change the colour and keep the oil picture displaying
				if(main.mouseX < (main.cPosX[i] + main.width/32) && main.mouseX > main.cPosX[i] && main.mouseY < (main.cPosY[j] + main.height/18) && main.mouseY > main.cPosY[j])
				{
                    if(colourGreen == true)
                    {
                        s = color(0,255,0);
                    }
                    else if(colourRed == true)
                    {
                        s = color(255,0,0);
                    }
                    else
                    {
                        s = color(255);
                    }

					main.stroke(s);
					main.strokeWeight(3);
					main.noFill();
					main.rect(main.cPosX[i], main.cPosY[j], size, size);
                    colourGreen = false;
                    colourRed = false;
				}//end if
			}//end for
		}//end for

		if(hover == true)
		{
			main.textAlign(CENTER);
			main.fill(255, 0 , 0);
			main.textSize(main.height/48);
			main.text("Next Turn", main.width-size, main.height - size/2 + 5);
		}
		//main.imageMode(CORNER);


	}

	public void render()
	{
		for(int i = 0; i < 32; i ++)
		{
			for(int j = 0; j < 17; j ++)
			{

				main.fill(f);
				s = color(255);
				main.stroke(s);
				main.rect(i*main.width/32, j*main.height/18, size, size);
				main.imageMode(CENTER);
				if(placeOil[i*j] == true)
				{
					main.image(oil, i*main.width/32 + main.width/64, j*main.height/18 + main.height/36);
				}//end if

			}//end for
		}//end for

		for(int i = 0; i < 32; i ++)
		{
			main.imageMode(CENTER);
			main.fill(255);
			main.stroke(255);
			main.rect(i * main.width/32, main.height - size, size, size);
			if(i < images.length)
			{
				main.image(images[i], i * main.width/32 - size/2, main.height - size/2);
			}//end if
		}//end for

		main.imageMode(CORNER);
		main.image(friendlyCity, main.width/2 - size, main.height-size*2);

		main.textAlign(CENTER);
		main.fill(0);
		main.textSize(main.height/48);
		main.text("Next Turn", main.width-size, main.height - size/2 + 5);
        main.fill(0);
        main.text("Turn: " + turnCount, main.width-size*4, main.height - size/2 + 5);
	}
}