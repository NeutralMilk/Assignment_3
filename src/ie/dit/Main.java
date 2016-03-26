package ie.dit;

import java.util.ArrayList;
import processing.core.*;
import ddf.minim.AudioInput;
import ddf.minim.Minim;
import ddf.minim.analysis.FFT;
import ddf.minim.analysis.WindowFunction;

public class Main extends PApplet
{
	Minim minim;
	AudioInput in;
	float min;
	float max;	
	ArrayList<Ship> ships = new ArrayList<Ship>();
	Battlefield battlefield;	
	int numShips = 0;	 
	int numSubs = 0;
	int sampleRate = 44100;	
	boolean[] occupied = new boolean[544];
	boolean move = false;
	boolean moveAgain = false;

	int clicked;
	
	//center position of each tile
	int[] cPosX = new int[32];
	int[] cPosY = new int[17];
	
	//variables for making the ships
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
	
	public void menu()
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
	public void game()
	{
		for( int i = 0; i < occupied.length; i++ )
		{
			println(i, occupied[i]);
		}//end for
		background(51,120,255);
		//fill(,204,255);
		strokeWeight(.25f);

		battlefield.render();
		battlefield.update();		
		
		//spawn ships
		for(int i = 0; i < ships.size(); i++)
		{
			ships.get(i).render();			
			ships.get(i).update();
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
	public void shipCreate()
	{
		//increase the number of ships
		int i = numShips;
		//create a ship
		Ship ship = new Ship(this);
		ships.add(ship); 
		//set it so that the ship follows the mouse
		ships.get(i).move = 0;
		numShips++;
		types[0] = false;	
	}
	
	public void mouseDragged()
	{
		
	}
	boolean place = false;
	public void mouseClicked()
	{		
		
		if(place == true)
		{			
			for(int i = 0; i < occupied.length; i++)
			{
				if(occupied[i] == true)
				{
					println("this is working");
					for(int j = 0; j < ships.size(); j++)
					{
						if(ships.get(j).move == 1)
						{
							ships.get(j).move = 2;
							clicked = 0;
							break;			
						}//end if					
					}//end for
				}//end if
			}//end for	
		}//end if
		
		if(place == false)
		{
			switch(clicked)
			{
				//choose which one to spawn
				case 0:
				{
					for(int i = 1; i < 3; i ++)
					{
						if(mouseX < i * battlefield.size && mouseX > i-1 * battlefield.size && mouseY < height && mouseY > height - battlefield.size)
						{	
							types[i-1] = true;
							clicked = 1;
							break;
						}//end if
					}//end for	
					
					/*for(int i = 1; i < ships.size(); i ++)
					{
						if(ships.get(i).pos.x == mouseX && ships.get(i).pos.y == mouseY)
						{	
							ships.get(i).move = 2;
						}//end if
					}//end for*/
				}//end case 0
				
				case 1:
				{
					if(mouseY > height * (17/18))
					{
						for(int i = 0; i < occupied.length; i++)
						{
							if(occupied[i] == false)
							{
								for(int j = 0; j < ships.size(); j++)
								{
									if(ships.get(j).move == 0)
									{
										ships.get(j).move = 1;
										occupied[i] = true;
										clicked = 0;
										place = true;
										break;			
									}//end if					
								}//end for
							}//end if
						}//end for	
					}//end if		
				}//end case 1
			}//end switch
		}//end if 		
	}//end mouseClicked
	
	
	/*public void mouseReleased()
	{
		
		
	}//end mouseReleased*/
	
		
	public static void main(String[] args)
	{		
		PApplet.main(Main.class.getName());	
	}
}
