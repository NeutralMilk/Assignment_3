package ie.dit;

import processing.core.*;

public abstract class GameObject extends PApplet
{
   //fields
	PVector pos;
	PVector mouseBox;
	Main main;
	PImage unit = new PImage();
	float easing;
	int move;
	boolean placed = false;
	float angle = 0;
	boolean validTile;
	boolean madeMove;
    float health = 100;
    boolean hasMoved = false;
	int clicks = 0;

    GameObject(Main _main)
    {
    	main = _main;
    }
  
    public abstract void update();  
    public abstract void render();
}