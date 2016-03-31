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
		pos = new PVector();
		mouseBox = new PVector();
		easing = .7f;
        madeMove = false;
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
			for (int i = 0; i < 32; i++)
			{
				for (int j = 0; j < 17; j++)
				{
					if (pos.x < (main.cPosX[i] + main.width / 32 + 1) && pos.x > main.cPosX[i] && pos.y < (main.cPosY[j] + main.height / 18 + 1) && pos.y > main.cPosY[j])
					{
						if (main.occupied[i * j] == true)
						{
							break;
						} //end if
						else
						{
							pos.x = main.cPosX[i] + main.width / 64;
							pos.y = main.cPosY[j] + main.height / 36;
						}//end else
					}//end if
				}//end for
			}//end for
		}//end if 1

		int clickCount = 0;
		if (move == 2)
		{

			for (int i = 0; i < 32; i++)
			{
				for (int j = 0; j < 17; j++)
				{
					if (pos.x < (main.cPosX[i] + main.width / 32 + 1) && pos.x > main.cPosX[i] && pos.y < (main.cPosY[j] + main.height / 18 + 1) && pos.y > main.cPosY[j])
					{
						pos.x = main.cPosX[i] + main.width / 64;
						pos.y = main.cPosY[j] + main.height / 36;
						main.occupied[i * j] = true;
					}//end if

					if (main.mouseX < (main.cPosX[i] + main.width / 32) && main.mouseX > main.cPosX[i] && main.mouseY < (main.cPosY[j] + main.height / 18) && main.mouseY > main.cPosY[j])
					{
						mouseBox.x = main.cPosX[i] + main.width / 64;
						mouseBox.y = main.cPosY[j] + main.height / 36;
					}//end if

					if (main.mousePressed && validTiles())
					{
						pos.x = mouseBox.x;
						pos.y = mouseBox.y;

						validTile = false;
						madeMove = true;


                        if(clicks > 1)
                        {
                            move = 1;
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
		int plusX = (int)pos.x + main.width/32;
		int plus2X = (int)pos.x + main.width/16;
		int minX = (int)pos.x - main.width/32;
		int min2X = (int)pos.x - main.width/16;

		//check for y values

		int plusY = (int)pos.y + main.height/18;
		int plus2Y = (int)pos.y + main.height/9;
		int minY = (int)pos.y - main.height/18;
		int min2Y = (int)pos.y - main.height/9;

		if (mouseBox.x == plusX || mouseBox.x == plus2X || mouseBox.x == minX || mouseBox.x == min2X || mouseBox.x == pos.x)
		{
			if (mouseBox.y == plusY || mouseBox.y == plus2Y || mouseBox.y == minY || mouseBox.y == min2Y || mouseBox.y == pos.y)
			{
				validTile = true;
			}//end if
		}//end if

		return validTile;
	}

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
	}
}