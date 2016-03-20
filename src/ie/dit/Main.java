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
	Ship ship;
	Sub sub;
	Battlefield battlefield;
	                         
	int sampleRate = 44100;
	
	boolean[] keys = new boolean[4];
	
	
	public void settings() 
	{
		fullScreen();
	}
	
	public void setup()
	{
		smooth();
		ship = new Ship(this);
		sub = new Sub(this);
		battlefield = new Battlefield(this);
		//frameRate(120);
		
	}
	
	public void draw()
	{
		background(51,204,255);
		//fill(,204,255);
		strokeWeight(.25f);

		battlefield.render();
		battlefield.update();
		
		ship.render();			
		ship.update();	
		sub.render();			
		sub.update();
	}
	
	boolean press = false;
	public void mousePressed()
	{
		press = true;
	}
	
		
	public static void main(String[] args)
	{		
		PApplet.main(Main.class.getName());	
	}
}
