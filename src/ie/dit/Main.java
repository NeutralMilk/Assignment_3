package ie.dit;

import java.util.ArrayList;
import processing.core.*;
import ddf.minim.AudioInput;
import ddf.minim.Minim;
import ddf.minim.analysis.FFT;
import ddf.minim.analysis.WindowFunction;

public class Main extends PApplet
{
    //friendly units
	ArrayList<GameObject> units = new ArrayList<GameObject>();
    ArrayList<GameObject> oilrigs = new ArrayList<GameObject>();

    //enemy units
    ArrayList<GameObject> enemyUnits = new ArrayList<GameObject>();

	Battlefield battlefield;
    Fog fog;
    City city;
	int numShips = 0;
    int numRigs = 0;
    PVector mousePos = new PVector(mouseX, mouseY);
	int clicked;
    boolean place = false;

    //position of each tile
    int[] cPosX;
    int[] cPosY;
    boolean[][] visited;
    ArrayList<PVector> posList = new ArrayList<PVector>();

	//variables for making the units
	boolean[] types = new boolean[5];
    boolean click = false;

    //turnCount counts the turns since the last enemy spawned, a new enemy spawns every 4-8 turns
    //when turnCount = spawn a unit spawns
    int spawn = 0;
    int turnCount = 0;
    int updateCount = 0;
    int hold = updateCount+1;

    //variables for the menu
	boolean menu = true;
	boolean firstTime = true;

    //money
    int gold = 100;
    int amountSubbed = 0;

    //some dimension variables
    int w = 32;
    int h = 17;
    int hplus1 = h + 1;

	public void settings()
	{
		fullScreen();
        //size(1600,900);
	}//end settings

	public void setup()
	{
		smooth();

        fog = new Fog(this);
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

				cPosX[i] = a;
				cPosY[j] = b;
			}//end for
		}  //end for

        battlefield = new Battlefield(this);

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

        if(city.currentHealth <= 0)
        {
            textAlign(CENTER);
            fill(0);
            textSize(width/20);

            text("You Lose", width/2, height/2);
            text("Click To Exit", width/2, height*.75f);

            strokeWeight(1);
            if(mousePressed)
            {
                exit();
            }
        }//end if

        else
        {
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
        }//end else
	}

	private void game()
	{
        background(51,120,255);
		//fill(,204,255);
		strokeWeight(.25f);

		battlefield.render();
		battlefield.update();

        checkUnit();

        for(int i = 0; i < oilrigs.size(); i++)
        {
            oilrigs.get(i).render();
            oilrigs.get(i).update();
        }//end for
        for(int a = 0; a < enemyUnits.size(); a++)
        {
            enemyUnits.get(a).render();
        }//end for

        if(hold == updateCount)
        {
            for(int b = 0; b < enemyUnits.size(); b++)
            {
                enemyUnits.get(b).update();
            }//end for
            hold = 1;
            updateCount = 0;
        }//end if

        fog.render();
        fog.update();

        city.render();

        for(int i = 0; i < units.size(); i++)
        {
            units.get(i).render();
            units.get(i).update();
        }//end for



        spawnEnemy();

		if(types[0] == true && gold >= 50)
		{
            if(gold >= 50)
            {
                sloopCreate();
            }//end if
            else
            {
                clicked = 0;
            }
		}//end if

        if(types[1] == true)
        {
            if(gold >= 200)
            {
                galleonCreate();
            }

            else
            {
                clicked = 0;
            }
        }//end if

        if(types[2] == true)
        {
            if(gold >= 350)
            {
                subCreate();
            }
            else
            {
                clicked = 0;
            }
        }//end if

        if(types[3] == true)
        {
            if(gold >= 500)
            {

                WarshipCreate();
            }
            else
            {
                clicked = 0;
            }
        }//end if

        if(types[4] == true)
        {
            if(gold >= 100)
            {
                oilCreate();
            }
            else
            {
                clicked = 0;
            }
        }//end if

        for(int i = 0; i < types.length; i++)
        {
            types[i] = false;
        }

        //these display the cost of a unit
        textMode(CENTER);
        fill(0);
        if(mouseX < battlefield.size && mouseX > 0 && mouseY < height && mouseY > height - battlefield.size)
        {
            text(50,width/2,height - (battlefield.size/2));
        }//end if

        if(mouseX < battlefield.size * 2 && mouseX > battlefield.size && mouseY < height && mouseY > height - battlefield.size)
        {
            text(200,width/2,height - (battlefield.size/2));
        }//end if

        if(mouseX < battlefield.size * 3 && mouseX > battlefield.size*2 && mouseY < height && mouseY > height - battlefield.size)
        {
            text(350,width/2,height - (battlefield.size/2));
        }//end if

        if(mouseX < battlefield.size * 4 && mouseX > battlefield.size*3 && mouseY < height && mouseY > height - battlefield.size)
        {
            text(500,width/2,height - (battlefield.size/2));
        }//end if

        if(mouseX < battlefield.size * 5 && mouseX > battlefield.size*4 && mouseY < height && mouseY > height - battlefield.size)
        {
            text(100,width/2,height - (battlefield.size/2));
        }//end if

        if(mouseX < width && mouseX > width - battlefield.size * 2 && mouseY < height && mouseY > height - battlefield.size)
        {
            battlefield.hover = true;
        }//end if
        else
        {
            battlefield.hover = false;
        }//end else

        for(int i  = 0; i < units.size(); i++)
        {
            if(units.get(i).currentHealth < 0)
            {
                units.remove(i);
                numShips--;
            }
        }

        for(int i  = 0; i < enemyUnits.size(); i++)
        {
            if(enemyUnits.get(i).currentHealth < 0)
            {
                enemyUnits.remove(i);
                gold+=20;
            }
        }

        if(mouseX > width/2 - battlefield.size && mouseX < width/2 + battlefield.size)
        {
            if(mouseY > height/2 - battlefield.size && mouseY < height/2 + battlefield.size)
            {
                rectMode(CENTER);
                strokeWeight(1);
                stroke(0);
                fill(255,0 , 0);
                rect(width/2, height - 40, 600, 20);
                int healthBar = (int)map(city.currentHealth, 0, city.initialHealth, 0 ,600);
                fill(0, 255, 0);
                rect(width/2, height - 40, healthBar, 20);
                strokeWeight(3);
                fill(0);
                text(city.currentHealth, width/2, height - 5);
                rectMode(CORNER);
            }//end if
        }//end if

        if(city.currentHealth <= 0)
        {
            menu = true;
        }
	}//end game()

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
	private void sloopCreate()
	{
		//increase the number of ships
		int i = numShips;
        gold -=50;
        amountSubbed = 50;
		//create a ship
		Sloop ship = new Sloop(this);
		units.add(ship);
		//set it so that the ship follows the mouse
		units.get(i).move = 0;
		numShips++;
		types[0] = false;
	}//end shipCreate()

    private void galleonCreate()
    {
        //increase the number of ships
        int i = numShips;
        gold -=200;
        amountSubbed = 200;
        //create a ship
        Galleon ship = new Galleon(this);
        units.add(ship);
        //set it so that the ship follows the mouse
        units.get(i).move = 0;
        numShips++;
        types[1] = false;
    }//end shipCreate()

    private void subCreate()
    {
        //increase the number of ships
        int i = numShips;
        gold -=350;
        amountSubbed = 350;
        //create a ship
        Sub ship = new Sub(this);
        units.add(ship);
        //set it so that the ship follows the mouse
        units.get(i).move = 0;
        numShips++;
        types[2] = false;
    }//end shipCreate()

    private void WarshipCreate()
    {
        //increase the number of ships
        int i = numShips;
        gold -=500;
        amountSubbed = 500;
        //create a ship
        Warship ship = new Warship(this);
        units.add(ship);
        //set it so that the ship follows the mouse
        units.get(i).move = 0;
        numShips++;
        types[3] = false;
    }//end shipCreate()

    private void oilCreate()
    {
        //increase the number of ships
        int i = numRigs;
        gold -=100;
        amountSubbed = 100;
        //create a rig
        Oil rig = new Oil(this);
        oilrigs.add(rig);
        //set it so that the ship follows the mouse
        oilrigs.get(i).move = 0;
        numRigs++;
        types[4] = false;
    }//end shipCreate()


    private void spawnEnemy()
    {
        if(spawn == turnCount)
        {
            EnemyShip enemyShip = new EnemyShip(this);
            enemyUnits.add(enemyShip);
            turnCount = 0;
            spawn = (int) random(8,10);
        }//end if
    }//ennd spawnEnemy()

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

        //shows the enemy health
        for(int i = 0 ; i < enemyUnits.size(); i ++)
        {
            GameObject go = enemyUnits.get(i);

            for(int k = 0; k < w; k++)
            {
                for (int j = 0; j < h; j++)
                {
                    if ((go.pos.x + mousePos.x) < (go.pos.x*2 + width/64) && (go.pos.x + mousePos.x) > (go.pos.x*2 - width/64))
                    {
                        if((go.pos.y + mousePos.y) < (go.pos.y*2 + height/36) && (go.pos.y + mousePos.y) > (go.pos.y*2 - height/36))
                        {

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

            if(mouseX < battlefield.size * 3 && mouseX > battlefield.size*2 && mouseY < height && mouseY > height - battlefield.size)
            {
                types[2] = true;
                clicked = 1;
            }//end if

            if(mouseX < battlefield.size * 4 && mouseX > battlefield.size*3 && mouseY < height && mouseY > height - battlefield.size)
            {
                types[3] = true;
                clicked = 1;
            }//end if

            if(mouseX < battlefield.size * 5 && mouseX > battlefield.size*4 && mouseY < height && mouseY > height - battlefield.size)
            {
                types[4] = true;
                clicked = 1;
            }//end if
        }//end if
    }//end Dragged

    boolean release = false;
    boolean release2 = false;
    public void mouseReleased()
    {

        release = true;
        release2 = true;
        if(clicked == 1)
        {
            for(int j = 0; j < units.size(); j++)
            {
                GameObject go = units.get(j);

                //if you try to place it off the battlefield it will be deleted

                //if you place it off the field it wont work
                //if the position is the same as another you cant place it
                if(go.pos.y > height-battlefield.size || checkPos(go.pos, j) == false)
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
                                pv = centerPos(pv);
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
                                pv = centerPos(pv);
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
                                pv = centerPos(pv);
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
                                pv = centerPos(pv);
                                posList.add(pv);
                                clicked = 0;
                                place = true;
                                go.madeMove = true;
                            }//end if
                        }//end if
                    }//end for
                }//end for
            }//end for

            for(int j = 0; j < oilrigs.size(); j++)
            {
                GameObject go = oilrigs.get(j);

                //if you try to place it off the battlefield it will be deleted
                //if you place it off the field it wont work
                //if the position is the same as another you cant place it
                if(go.pos.y > height-battlefield.size || checkPos(go.pos, j) == false)
                {
                    oilrigs.remove(j);
                    numRigs--;
                    clicked = 0;
                    gold += amountSubbed;
                }//end else

                else
                {
                    //allow it to be placed on the battlefield
                    PVector mousePos = new PVector(mouseX, mouseY);
                    for(int q = 0; q < battlefield.oilPos.size(); q++)
                    {
                        float dist = mousePos.dist(battlefield.oilPos.get(q));
                        float a = width/64;
                        if(dist < a)
                        {
                            if (go.move == 0)
                            {
                                go.move = 1;
                                clicked = 0;
                            }//end if
                        }//end if
                    }//end for
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
            turnCount++;
            updateCount++;
            gold +=5;
            gold+= oilrigs.size()*10;
        }//end if)
    }//end mouseReleased

    public boolean checkPos(PVector pos, int j)
    {
        boolean valid = true;
        for(int i = 0; i < units.size(); i++)
        {
            //change the position to the centre
            pos = centerPos(pos);

            GameObject go = units.get(i);
            float size = battlefield.size/2;

            //if the position is the same as any unit other than itself then you cannot place it
            if(pos.x == go.pos.x && pos.y == go.pos.y && i != j)
            {
                valid = false;
            }//end if
        }//end for
        return valid;
    }//end checkPos
    public PVector centerPos(PVector pos)
    {
        for (int i = 0; i < w; i++)
        {
            for (int j = 0; j < h; j++)
            {
                if (pos.x < (cPosX[i] + (width / w) + 1) && pos.x > cPosX[i] && pos.y < (cPosY[j] + (height / hplus1) + 1) && pos.y > cPosY[j])
                {
                    //move them to the centre
                    pos.x = cPosX[i] + width / 64;
                    pos.y = cPosY[j] + height / 36;
                }//end if
            }//end if
        }//end for

        return pos;
    }//end centerPos

	public static void main(String[] args)
	{
		PApplet.main(Main.class.getName());
	}
}