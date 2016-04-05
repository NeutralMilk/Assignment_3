package ie.dit;

import java.util.ArrayList;
import processing.core.*;
import ddf.minim.AudioInput;
import ddf.minim.Minim;
import ddf.minim.analysis.FFT;
import ddf.minim.analysis.WindowFunction;

public class Main extends PApplet
{
	ArrayList<GameObject> units = new ArrayList<GameObject>();
	Battlefield battlefield;
	int numShips = 0;
    PVector mousePos = new PVector(mouseX, mouseY);
	int clicked;
    boolean place = false;

    //position of each tile
	int[] cPosX = new int[32];
	int[] cPosY = new int[17];

	//variables for making the units
	boolean[] types = new boolean[2];
    boolean click = false;

    //variables for the menu
	boolean menu = true;
	boolean firstTime = true;

    //variables to track occupied squares

    boolean[] occupiedFriendly = new boolean [544];
    boolean[] occupiedEnemy = new boolean [544];

	public void settings()
	{
		//fullScreen();
        size(1280,720);
	}//end settings

	public void setup()
	{
		smooth();
		battlefield = new Battlefield(this);

		for(int i = 0; i < 32; i ++)
		{
			for(int j = 0; j < 17; j ++)
			{
				int a = (i * width/32);
				int b = (j * height/18);

				cPosX[i] = a;
				cPosY[j] = b;
			}//end for
		}  //end for


		for( int i = 0; i < 544; i++ )
		{
			occupiedFriendly[i] = false;
            occupiedEnemy[i] = false;
		}//end for
	}//end setup

	public void draw()
	{
		if(menu == true)
		{
			menu();
		}//end if

		if(menu == false)
		{
			game();
		}//end if

	}

	private void menu()
	{
		float boxWidth = width/3;
		float boxHeight = height/5;

		float x = width/2 - boxWidth/2;
		float y = height/2 - boxHeight/2;

		stroke(255);
		strokeWeight(5);

		if(firstTime == true)
		{
			fill(68,193,235);
			rect(0, 0, width, height);
		}//end if

		if(mouseX > x && mouseX < x + boxWidth && mouseY > y - boxHeight/1.5 && mouseY < y + boxHeight/3)
		{
			fill(75,200,255);
			if(mousePressed)
			{
				menu = false;
			}//end if
		}
		else
		{
			fill(68,193,235);
		}//end else

		rect(x, (float) (y - boxHeight/1.5), boxWidth, boxHeight);


		if(mouseX > x && mouseX < x + boxWidth && mouseY > y + boxHeight/1.5 && mouseY < y + boxHeight*1.7)
		{
			fill(75,200,255);
			if(mousePressed)
			{
				exit();
			}//end if
		}
		else
		{
			fill(68,193,235);
		}//end else

		rect(x, (float) (y + boxHeight/1.5), boxWidth, boxHeight);

		textAlign(CENTER);
		fill(0);
		textSize(width/20);
		if(firstTime == true)
		{
			text("Start Game", width/2, height/2 - boxHeight/2);
		}//end if

		else
		{
			text("Resume", width/2, height/2 - boxHeight/2);
		}//end else

		text("Exit Game", width/2, (float) (height/2 + boxHeight/1.25));
		strokeWeight(1);
	}

	private void game()
	{
        background(51,120,255);
		//fill(,204,255);
		strokeWeight(.25f);

		battlefield.render();
		battlefield.update();

        if(mouseY < height - height/18)
        {
            checkUnit();
        }//end if

		for(int i = 0; i < units.size(); i++)
		{
			units.get(i).render();
			units.get(i).update();
		}//end for

		if(types[0] == true)
		{
			shipCreate();
		}//end if

        if(types[1] == true)
        {
            subCreate();
        }//end if

        if(mouseX < width && mouseX > width - battlefield.size * 2 && mouseY < height && mouseY > height - battlefield.size)
        {
            battlefield.hover = true;
        }//end if)
        else
        {
            battlefield.hover = false;
        }//end else
	}

	public void keyPressed()
	{
		if (key == 'm')
		{
			firstTime = false;

			if(menu == true)
			{
				menu = false;
			}//end if

			else
			{
				menu = true;
			}//end else
		}//end if
	}

	//creates a ship
	private void shipCreate()
	{
		//increase the number of ships
		int i = numShips;
		//create a ship
		Ship ship = new Ship(this);
		units.add(ship);
		//set it so that the ship follows the mouse
		units.get(i).move = 0;
		numShips++;
		types[0] = false;
	}//end shipCreate()

    private void subCreate()
    {
        //increase the number of ships
        int i = numShips;
        //create a ship
        Sub ship = new Sub(this);
        units.add(ship);
        //set it so that the ship follows the mouse
        units.get(i).move = 0;
        numShips++;
        types[1] = false;
    }//end shipCreate()


	void checkUnit()
    {
        mousePos.x = mouseX;
        mousePos.y = mouseY;
        if(mousePressed && clicked == 0)
        {
            for(int i = 0 ; i < units.size(); i ++)
            {
                GameObject go = units.get(i);

                if ((go.pos.x + mousePos.x) < (go.pos.x*2 + width/64) && (go.pos.x + mousePos.x) > (go.pos.x*2 - width/64) && go.nextTurn == true)
                {
                    if((go.pos.y + mousePos.y) < (go.pos.y*2 + height/36) && (go.pos.y + mousePos.y) > (go.pos.y*2 - height/36))
                    {

                        if (go.move == 1)
                        {
                            go.move = 2;
                            clicked = 0;
                        }//end if

                        if(click == true)
                        {
                            go.clicks++;
                            click = false;
                        }//end if
                    }//end if
                }//end if
            }//end for
        }//end if

        for(int i = 0 ; i < units.size(); i ++)
        {
            GameObject go = units.get(i);

            for(int k = 0; k < 32; k++)
            {
                for (int j = 0; j < 17; j++)
                {
                    if ((go.pos.x + mousePos.x) < (go.pos.x*2 + width/64) && (go.pos.x + mousePos.x) > (go.pos.x*2 - width/64))
                    {
                        if((go.pos.y + mousePos.y) < (go.pos.y*2 + height/36) && (go.pos.y + mousePos.y) > (go.pos.y*2 - height/36))
                        {
							if(go.nextTurn == true)
							{
								battlefield.colourGreen = true;
							}//end if
                            if(go.nextTurn == false)
                            {
                                battlefield.colourRed = true;
                            }//end if
                            if(validTile() == true)
                            {
                                battlefield.colourGreen = true;
                            }
                            rectMode(CENTER);
                            strokeWeight(1);
                            stroke(0);
                            fill(255,0 , 0);
                            rect(width/2, height - 40, 600, 20);
                            int healthBar = (int)map(go.currentHealth, 0, go.initialHealth, 0 ,600);
                            fill(0, 255, 0);
                            rect(width/2, height - 40, healthBar, 20);
                            strokeWeight(3);
                            fill(0);
                            text(go.currentHealth, width/2, height - 5);
                            rectMode(CORNER);
                        }//end if
                    }//end if
                }//end for
            }//end for
        }//end for
    }//end checkCollisions()

    public boolean validTile()
    {
        boolean valid = false;
        if(mouseY < height - (height/18)*2 &&  mouseY > height - (height/18) * 3 && mouseX > width/2 - battlefield.size*2 && mouseX < width/2 + battlefield.size*2 ||
                mouseY > height - (height/18)*2 &&  mouseY < height - (height/18) && mouseX > width/2 - battlefield.size*2 && mouseX < width/2 - battlefield.size ||
                mouseY > height - (height/18)*2 &&  mouseY < height - (height/18) && mouseX < width/2 + battlefield.size*2 && mouseX > width/2 + battlefield.size)
        {
           valid = true;
        }//end if
        return valid;
    }
    public void mouseClicked()
    {
        click = true;
    }//end mouseClicked()

    public void mouseDragged()
    {
        if(clicked == 0)
        {
            if(mouseX < battlefield.size && mouseX > 0 && mouseY < height && mouseY > height - battlefield.size)
            {
                types[0] = true;
                clicked = 1;
            }//end if

            if(mouseX < battlefield.size * 2 && mouseX > battlefield.size && mouseY < height && mouseY > height - battlefield.size)
            {
                types[1] = true;
                clicked = 1;
            }//end if
        }//end if
    }//end mousePressed

    public void mouseReleased()
    {

        if(clicked == 1)
        {
            for(int j = 0; j < units.size(); j++)
            {
                GameObject go = units.get(j);
                //allow it to be placed on the battlefield
                if(mouseY < height - (height/18)*2 &&  mouseY > height - (height/18) * 3 && mouseX > width/2 - battlefield.size*2 && mouseX < width/2 + battlefield.size*2 ||
                        mouseY > height - (height/18)*2 &&  mouseY < height - (height/18) && mouseX > width/2 - battlefield.size*2 && mouseX < width/2 - battlefield.size ||
                        mouseY > height - (height/18)*2 &&  mouseY < height - (height/18) && mouseX < width/2 + battlefield.size*2 && mouseX > width/2 + battlefield.size)
                {
                    println("this works 1");
                    println("this works");
                    if(go.move == 0)
                    {
                        go.move = 1;

                        clicked = 0;
                        place = true;
                        go.madeMove = true;
                    }//end if
                }//end if

                //if you try to place it off the battlefield it will be deleted
                if(go.pos.y > height - height/18)
                {
                    units.remove(go);
                    numShips--;
                    clicked = 0;
                }//end else
            }//end for
        }//end if


        if(mouseX < width && mouseX > width - battlefield.size * 2 && mouseY < height && mouseY > height - battlefield.size)
        {
            for(int i = 0 ; i < units.size(); i ++)
            {
                GameObject go = units.get(i);

                go.nextTurn = true;
                go.clicks = 0;
            }//end for
            battlefield.turnCount++;
        }//end if)
    }//end mouseReleased

	public static void main(String[] args)
	{
		PApplet.main(Main.class.getName());
	}
}