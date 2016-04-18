package ie.dit;

import processing.core.*;

public class Fog extends PApplet
{

    Main main;

    int size;
    int[][] coloursFog;
    int w;
    int h;
    int hplus1;

    public Fog (Main _main)
    {
        main = _main;

        w = main.w;
        h = main.h;
        hplus1 = h+1;

        coloursFog = new int[32][17];
        size = main.width/w;

        for(int i = 0; i < w; i ++)
        {
            for(int j = 0; j < h; j ++)
            {
                coloursFog[i][j] = (int)random(125,150);
            }//end for
        }//end for
    }

    public void update()
    {

    }

    public void render()
    {
        for(int i = 0; i < w; i ++)
        {
            for(int j = 0; j < h; j ++)
            {
                /*if(main.visited[i][j] == false)
                {
                    main.fill(coloursFog[i][j]);
                    main.stroke(255);
                    main.rect(i*main.width/w, j*main.height/hplus1, size, size);
                }//end if*/
            }//end for
        }//end for
    }//end render()
}
