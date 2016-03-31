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
	int numSubs = 0;
	int sampleRate = 44100;
	boolean[] occupied = new boolean[544];
	int[] shipPos = new int[544];

	boolean move = false;
	boolean moveAgain = false;
    PVector mousePos = new PVector(mouseX, mouseY);

	int clicked;

	//center position of each tile
	int[] cPosX = new int[32];
	int[] cPosY = new int[17];

	//variables for making the units
	boolean[] types = new boolean[2];
	boolean[] keys = new boolean[4];
	boolean release = false;


	public void settings()
	{
		fullScreen();
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


		for( int i = 0; i < occupied.length; i++ )
		{
			occupied[i] = false;
		}//end for
	}//end setup

	boolean menu = true;
	boolean firstTime = true;

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
            checkClicked();
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

    GameObject go;
	void checkClicked()
    {
        mousePos.x = mouseX;
        mousePos.y = mouseY;
        if(mousePressed)
        {
            for(int i = 0 ; i < units.size(); i ++)
            {
                go = units.get(i);

                if ((go.pos.x + mousePos.x) < (go.pos.x*2 + width/64) && (go.pos.x + mousePos.x) > (go.pos.x*2 - width/64))
                {
                    if((go.pos.y + mousePos.y) < (go.pos.y*2 + height/36) && (go.pos.y + mousePos.y) > (go.pos.y*2 - height/36))
                    {
                        if (go.move == 1)
                        {
                            go.move = 2;
                            clicked = 0;
                        }//end if
                    }//end if
                }//end if
            }//end for
        }//end if
    }//end checkCollisions()

    public void mouseClicked()
    {
        if(go != null)
        {
            if ((go.pos.x + mousePos.x) < (go.pos.x*2 + width/64) && (go.pos.x + mousePos.x) > (go.pos.x*2 - width/64))
            {
                if((go.pos.y + mousePos.y) < (go.pos.y*2 + height/36) && (go.pos.y + mousePos.y) > (go.pos.y*2 - height/36))
                {
                    go.clicks++;
                }//end if
            }//end if
        }//end if
    }//end mouseClicked()

    boolean place = false;
	boolean madeMove = false;
	int oldPos;

    public void mousePressed()
    {
        if(clicked == 0)
        {
            for(int i = 1; i < types.length + 1; i ++)
            {
                if(mouseX < i * battlefield.size && mouseX > i-1 * battlefield.size && mouseY < height && mouseY > height - battlefield.size)
                {
                    types[i-1] = true;
                    clicked = 1;

                }//end if
            }//end for
        }//end if
    }//end mousePressed

    public void mouseReleased()
    {

        if(clicked == 1)
        {
            if(mouseY < height - height/18)
            {
                for(int j = 0; j < units.size(); j++)
                {
                    if(units.get(j).move == 0)
                    {
                        units.get(j).move = 1;

                        clicked = 0;
                        place = true;
                        break;
                    }//end if
                }//end for

            }//end if
        }//end if
    }//end mouseReleased

	public static void main(String[] args)
	{
		PApplet.main(Main.class.getName());
	}
}