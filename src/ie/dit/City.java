package ie.dit;

import processing.core.*;

public class City extends PApplet
{

    Main main;
    PImage city = new PImage();

    public City (Main _main)
    {
        main = _main;
        city = main.loadImage("city.png");

        int w = city.width * main.width/2560;
        int h = city.height * main.height/1440;
        city.resize(w,h);
    }

    public void update()
    {

    }

    public void render()
    {
        main.imageMode(CENTER);
        main.image(city, main.width/2, main.height/2);
    }//end render()
}
