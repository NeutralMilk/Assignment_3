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
	
	Hero hero;
	                         
	int sampleRate = 44100;
	
	float charX;
	float charY;
	float x;
	float y;
	float easing;
	
	boolean[] keys = new boolean[512];
	
	
	public void settings() 
	{
		fullScreen();
	}
	
	public void setup()
	{
		smooth();
		hero = new Hero (this);
		minim = new Minim(this);
		charX = 0;
		charY = height/2-width/100;
		x = 0;
		y = height/2-width/100;
		easing = 0.05f;
	}
	
	public void draw()
	{
		background(51,204,255);
		fill(50,255,100);
		stroke(50,255,100);
		rect(0,height/2,width, height/2);
		stroke(255);	
		
		hero.update();
		hero.render();
		
	}
	
	
	public void keyPressed()
	{	  
		keys[keyCode] = true;
	}
	
	public void keyReleased()
	{
		keys[keyCode] = false;
	}
		
	public static void main(String[] args)
	{		
		PApplet.main(Main.class.getName());	
	}
}
