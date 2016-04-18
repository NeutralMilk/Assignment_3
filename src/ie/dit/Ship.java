package ie.dit;

import java.util.ArrayList;

import ddf.minim.Minim;
import processing.core.*;

public class Ship extends GameObject
{
	public Ship(Main _main)
	{
		super(_main);
		unit = main.loadImage("1.png");

		//this scales the units to fit any screen size
		int w = unit.width * main.width/2560;
		int h = unit.height * main.height/1440;
		unit.resize(w,h);
		pos = new PVector();
		mouseBox = new PVector();
		easing = .7f;
        madeMove = false;
		initialHealth = 100;
		currentHealth = 100;
		clicks = 0;
	}

	public void update()
	{


		if (move == 0) {
			float targetX = main.mouseX;
			float dx = targetX - pos.x;
			pos.x += dx * easing;

			float targetY = main.mouseY;
			float dy = targetY - pos.y;
			pos.y += dy * easing;
		}//end if 0

		if (move == 1)
		{
			for (int i = 0; i < w; i++)
			{
				for (int j = 0; j < h; j++)
				{
					if (pos.x < (main.cPosX[i] + main.width / w + 1) && pos.x > main.cPosX[i] && pos.y < (main.cPosY[j] + main.height / hplus1 + 1) && pos.y > main.cPosY[j])
					{
                        main.visited[i][j]= true;


                        if(i > 0 && i < 31 &&  j > 0 && j < hmin1)
                        {
                            main.visited[i - 1][j] = true;
							main.visited[i + 1][j] = true;
							main.visited[i][j - 1] = true;
							main.visited[i][j + 1] = true;
							main.visited[i + 1][j + 1] = true;
							main.visited[i - 1][j + 1] = true;
							main.visited[i + 1][j - 1] = true;
							main.visited[i - 1][j - 1] = true;
                        }//end if

                        pos.x = main.cPosX[i] + main.width / 64;
                        pos.y = main.cPosY[j] + main.height / 36;


                        break;
					}//end if
				}//end for
			}//end for

		}//end if 1

		if (move == 2)
		{
			for (int i = 0; i < w; i++)
			{
				for (int j = 0; j < h; j++)
				{
					if (pos.x < (main.cPosX[i] + main.width / w + 1) && pos.x > main.cPosX[i] && pos.y < (main.cPosY[j] + main.height / hplus1 + 1) && pos.y > main.cPosY[j])
					{
						pos.x = main.cPosX[i] + main.width / 64;
						pos.y = main.cPosY[j] + main.height / 36;
					}//end if

					if (main.mouseX < (main.cPosX[i] + main.width / w) && main.mouseX > main.cPosX[i] && main.mouseY < (main.cPosY[j] + main.height / hplus1) && main.mouseY > main.cPosY[j])
					{
						mouseBox.x = main.cPosX[i] + main.width / 64;
						mouseBox.y = main.cPosY[j] + main.height / 36;
					}//end if

					if (main.mousePressed && validTiles() && checkPos(mouseBox) == true)
					{
						pos.x = mouseBox.x;
						pos.y = mouseBox.y;

						validTile = false;
						madeMove = true;

                        //only move once per turn
                        if(clicks > 0)
                        {
                            move = 1;
							nextTurn = false;
							//main.active = false;
                        }//end if
					}//end if
				}//end for
			}//end for
		}//end if 2
	}//end update()

	//this function checks to see if the mouse is within two boxes up, down, left or right of the unit
	public boolean validTiles()
	{
		//check for the x values
		int plusX = (int)pos.x + main.width/w;
		int plus2X = (int)pos.x + main.width/hmin1;
		int minX = (int)pos.x - main.width/w;
		int min2X = (int)pos.x - main.width/hmin1;

		//check for y values

		int plusY = (int)pos.y + main.height/hplus1;
		int plus2Y = (int)pos.y + main.height/9;
		int minY = (int)pos.y - main.height/hplus1;
		int min2Y = (int)pos.y - main.height/9;

		if (mouseBox.x == plusX || mouseBox.x == plus2X || mouseBox.x == minX || mouseBox.x == min2X || mouseBox.x == pos.x)
		{
			if (mouseBox.y == plusY || mouseBox.y == plus2Y || mouseBox.y == minY || mouseBox.y == min2Y || mouseBox.y == pos.y)
			{
				validTile = true;
			}//end if
		}//end if
		return validTile;
	}//end validTiles()

	//checks the position of every other ship
	public boolean checkPos(PVector pos)
	{
		boolean valid = true;
		for(int i = 0; i < main.units.size(); i++)
		{
			//change the position to the centre
			pos = main.centerPos(pos);

			GameObject go = main.units.get(i);
			float size = main.battlefield.size/2;

			//if the position is the same as any unit other than itself then you cannot place it
			if(pos.x == go.pos.x && pos.y == go.pos.y)
			{
				valid = false;
			}//end if
		}//end for
		return valid;
	}//end checkPos

	public void render()
	{
		switch(move)
		{
			case 0:
			{
				main.pushMatrix();
				main.translate(main.mouseX, main.mouseY);
				main.imageMode(CENTER);
				main.image(unit, 0, 0);
				main.popMatrix();
				break;
			}//end case 0

			case 1:
			{
				main.pushMatrix();
				main.translate(pos.x, pos.y);
				main.imageMode(CENTER);
				main.image(unit, 0, 0);
				main.popMatrix();
				break;
			}//end case 1

			case 2:
			{
				main.pushMatrix();
				angle = atan2(-(pos.x - mouseBox.x), -(pos.y - mouseBox.y));
				main.translate(pos.x, pos.y);
				main.rotate(-angle+PI);
				main.imageMode(CENTER);
				main.image(unit, 0, 0);
				main.popMatrix();
				break;

			}//end case 2
		}//end switch
	}//end render
}