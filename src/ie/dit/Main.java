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
    City city;
	int numShips = 0;
    PVector mousePos = new PVector(mouseX, mouseY);
	int clicked;
    boolean place = false;

    PVector previousPos = new PVector();
    //position of each tile
    int[] cPosX;
    int[] cPosY;
    boolean[][] visited;
    ArrayList<PVector> posList = new ArrayList<PVector>();

	//variables for making the units
	boolean[] types = new boolean[2];
    boolean click = false;

    //variables for the menu
	boolean menu = true;
	boolean firstTime = true;

    int gold = 10000;
    int amountSubbed = 0;

    int w = 32;
    int h = 17;
    int hplus1 = h + 1;

    //variables to track occupied squares

    boolean[][] occupiedFriendly = new boolean [32][17];
    boolean[][] occupiedEnemy = new boolean [32][17];


	public void settings()
	{
		fullScreen();
        //size(1280,720);
	}//end settings

	public void setup()
	{
		smooth();
		battlefield = new Battlefield(this);
        city = new City(this);

        cPosX = new int[w];
        cPosY = new int[h];
        visited = new boolean [w][h];

		for(int i = 0; i < w; i ++)
		{
			for(int j = 0; j < h; j ++)
			{
				int a = (i * width/w);
				int b = (j * height/hplus1);
                occupiedFriendly[i][j] = false;

				cPosX[i] = a;
				cPosY[j] = b;
			}//end for
		}  //end for

	}//end setup

    //have separate methods for both the menu and the game
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

        city.render();

        checkUnit();

		for(int i = units.size()-1; i >= 0; i--)
		{
			units.get(i).render();
			units.get(i).update();
            //if(units.get(i).pos.x)
		}//end for

		if(types[0] == true && gold >= 50)
		{
			shipCreate();
		}//end if

        if(mouseX < width && mouseX > width - battlefield.size * 2 && mouseY < height && mouseY > height - battlefield.size)
        {
            battlefield.hover = true;
        }//end if)
        else
        {
            battlefield.hover = false;
        }//end else

	}//end game()

	public void keyPressed()
	{
		if (key == 'm')
		{
			firstTime = false;

            println(posList);
            //checking if occupied slots work
            /*for(int i = 0; i < w; i ++)
            {
                for(int j = 0; j < h; j ++)
                {
                    println("after"+i, j, occupiedFriendly[i][j]);
                }
            }*/
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
        gold -=50;
        amountSubbed = 50;
		//create a ship
		Ship ship = new Ship(this);
		units.add(ship);
		//set it so that the ship follows the mouse
		units.get(i).move = 0;
		numShips++;
		types[0] = false;
	}//end shipCreate()

	void checkUnit()
    {
        mousePos.x = mouseX;
        mousePos.y = mouseY;
        if(release == true && clicked == 0)
        {
            release = false;
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

                        //this prevents multiple moves per turn
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

            for(int k = 0; k < w; k++)
            {
                for (int j = 0; j < h; j++)
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
                            }//end if


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
        if(mouseY < height - (height/hplus1)*2 &&  mouseY > height - (height/hplus1) * 3 && mouseX > width/2 - battlefield.size*2 && mouseX < width/2 + battlefield.size*2 ||
                mouseY > height - (height/hplus1)*2 &&  mouseY < height - (height/hplus1) && mouseX > width/2 - battlefield.size*2 && mouseX < width/2 - battlefield.size ||
                mouseY > height - (height/hplus1)*2 &&  mouseY < height - (height/hplus1) && mouseX < width/2 + battlefield.size*2 && mouseX > width/2 + battlefield.size)
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

    boolean release = false;
    public void mouseReleased()
    {

        release = true;
        if(clicked == 1)
        {
            for(int j = 0; j < units.size(); j++)
            {
                GameObject go = units.get(j);

                //if you try to place it off the battlefield it will be deleted
                if(go.pos.y > height-battlefield.size)
                {
                    units.remove(j);
                    numShips--;
                    clicked = 0;
                    gold += amountSubbed;
                }//end else

                for( int i = 0; i < 32; i++ )
                {
                    for( int q = 0; q < 17; q++)
                    {
                        //allow it to be placed on the battlefield
                        //check if placed on left side
                        if(mouseX > width/2 - battlefield.size*2 && mouseX < width/2 - battlefield.size && mouseY > height/2 - battlefield.size*2 && mouseY < height/2 + battlefield.size*2)
                        {
                            if (go.move == 0)
                            {
                                go.move = 1;
                                PVector pv = new PVector(go.pos.x, go.pos.y);
                                posList.add(pv);
                                clicked = 0;
                                place = true;
                                go.madeMove = true;
                            }//end if
                        }//end if

                        //check if placed on right
                        if(mouseX > width/2 + battlefield.size && mouseX < width/2 + battlefield.size*2 && mouseY > height/2 - battlefield.size*2 && mouseY < height/2 + battlefield.size*2)
                        {
                            if (go.move == 0)
                            {
                                go.move = 1;
                                PVector pv = new PVector(go.pos.x, go.pos.y);
                                posList.add(pv);
                                clicked = 0;
                                place = true;
                                go.madeMove = true;
                            }//end if
                        }//end if

                        //check if placed on top
                        if(mouseX > width/2 - battlefield.size*2 && mouseX < width/2 + battlefield.size*2 && mouseY > height/2 - battlefield.size*2 && mouseY < height/2 - battlefield.size)
                        {
                            if (go.move == 0)
                            {
                                go.move = 1;
                                PVector pv = new PVector(go.pos.x, go.pos.y);
                                posList.add(pv);
                                clicked = 0;
                                place = true;
                                go.madeMove = true;
                            }//end if
                        }//end if

                        //check if placed on bottom
                        if(mouseX > width/2 - battlefield.size*2 && mouseX < width/2 + battlefield.size*2 && mouseY > height/2 + battlefield.size && mouseY < height/2 + battlefield.size*2)
                        {
                            if (go.move == 0)
                            {
                                go.move = 1;
                                PVector pv = new PVector(go.pos.x, go.pos.y);
                                posList.add(pv);
                                clicked = 0;
                                place = true;
                                go.madeMove = true;
                            }//end if
                        }//end if
                    }//end for
                }//end for
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