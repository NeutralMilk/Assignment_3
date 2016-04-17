package ie.dit;

import processing.core.*;

public class Battlefield extends PApplet
{
	Main main;
	int s = color(255);
	int x;
	int y;
	int size;
	boolean[][] placeOil;
	PImage[] images;
	PImage oil;
    int[][] coloursWater ;
	int w;
	int h;
	int hplus1;

	public Battlefield(Main _main)
	{
		main = _main;

		w = main.w;
		h = main.h;
		hplus1 = h+1;
		x = 0;
		y = 0;
		size = main.width/w;

		placeOil = new boolean[w][h];
		images = new PImage[3];
		oil = new PImage();
		coloursWater = new int[w][h];

		for(int i = 0; i < w; i++)
		{
            for(int j = 0 ; j < h; j++)
            {
                if ((int)random(1,45) == 1)
                {
                    placeOil[i][j] = true;
                } //end if
                else
                {
                    placeOil[i][j] = false;
                }//end else
            }//end for
		}//end for
		oil = main.loadImage("oil.png");

		for ( int i = 0; i< images.length; i++ )
		{
			images[i] = main.loadImage( i + ".png" );
			//scaling the image
			int wi = images[i].width * main.width/2560;
			int hi = images[i].height * main.height/1440;
			images[i].resize(wi,hi);
		}//end for



		//scaling th eimage
		int wi = oil.width * main.width/2560;
		int hi = oil.height * main.height/1440;

		oil.resize(wi,hi);

		for(int i = 0; i < w; i ++)
		{
			for(int j = 0; j < h; j ++)
			{
                int r = (int)random(55,70);
                int g = (int)random(180,190);
                int b = (int)random(230,245);
                int c = color(r, g, b);
                coloursWater[i][j] = c;
			}//end for
		}//end for
	}

	boolean colourGreen = false;
	boolean colourRed = false;
	boolean hover = false;
	int turnCount = 1;
	public void update()
	{
		for(int i = 0; i < w; i++)
		{
			for(int j = 0; j < h; j++)
			{
				//if the mouse position is within the bounds of a box change the colour and keep the oil picture displaying
				if(main.mouseX < (main.cPosX[i] + main.width/32) && main.mouseX > main.cPosX[i] && main.mouseY < (main.cPosY[j] + main.height/18) && main.mouseY > main.cPosY[j])
				{
                    if(colourGreen == true)
                    {
                        s = color(0,255,0);
                    }//end if
                    else if(colourRed == true)
                    {
                        s = color(255,0,0);
                    }//end else if
                    else
                    {
                        s = color(255);
                    }//end else

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
		}//end

		main.textAlign(CENTER);
		main.fill(245, 189 , 7);
		main.textSize(main.height/48);
		main.text("Gold = " + main.gold, main.width-size*10, main.height - size/2 + 5);

	}//end upate

	public void render()
	{
		for(int i = 0; i < w; i ++)
		{
			for(int j = 0; j < h; j ++)
			{

				main.fill(coloursWater[i][j]);
				s = color(255);
				main.stroke(s);
				main.rect(i*main.width/w, j*main.height/hplus1, size, size);

				main.imageMode(CENTER);
				if(placeOil[i][j] == true)
				{
					main.image(oil, i*main.width/32 + main.width/64, j*main.height/18 + main.height/36);
                }//end if
			}//end for
		}//end for


		for(int i = 0; i < w; i ++)
		{
			main.imageMode(CENTER);
			main.fill(255);
			main.stroke(255);
			main.rect(i * main.width/w, main.height - size, size, size);
			if(i < images.length)
			{
				main.image(images[i], i * main.width/w - size/2, main.height - size/2);
			}//end if
		}//end for



		main.textAlign(CENTER);
		main.fill(0);
		main.textSize(main.height/48);
		main.text("Next Turn", main.width-size, main.height - size/2 + 5);
        main.fill(0);
        main.text("Turn: " + turnCount, main.width-size*4, main.height - size/2 + 5);
	}
}