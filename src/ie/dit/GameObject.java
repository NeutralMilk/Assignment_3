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
	float angle = 0;
	boolean validTile;
	boolean madeMove;
	boolean release;
	int initialHealth;
    int currentHealth;
	int clicks;
	boolean nextTurn = false;
	int w;
	int h;
	int hplus1;
	int hmin1;
	int enemyIndex;
	int friendlyIndex;
	int q;

    GameObject(Main _main)
    {
    	main = _main;
		w = main.w;
		h = main.h;
		hplus1 = main.h + 1;
		hmin1 = main.h - 1;
    }
  
    public abstract void update();  
    public abstract void render();
}