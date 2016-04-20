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
    boolean colourGreen = false;
    boolean colourRed = false;
    int s = color(255);

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
        for(int i = 0; i < w; i ++)
        {
            for(int j = 0; j < h; j ++)
            {
                if(main.mouseX < (main.cPosX[i] + main.width/32) && main.mouseX > main.cPosX[i] && main.mouseY < (main.cPosY[j] + main.height/18) && main.mouseY > main.cPosY[j])
                {
                    if(colourGreen == true)
                    {
                        s = color(0,255,0);
                    }//end if
                    else if(colourRed == true)
                    {
                        s = color(255,0,0);
                    }//end else if
                    else
                    {
                        s = color(255);
                    }//end else

                    main.stroke(s);
                    main.strokeWeight(3);
                    main.noFill();
                    main.rect(main.cPosX[i], main.cPosY[j], size, size);
                    colourGreen = false;
                    colourRed = false;
                }//end if
            }//end for
        }//end for
    }

    public void render()
    {
        for(int i = 0; i < w; i ++)
        {
            for(int j = 0; j < h; j ++)
            {
                if(main.visited[i][j] == false)
                {
                    main.fill(coloursFog[i][j]);
                    main.stroke(255);
                    main.rect(i*main.width/w, j*main.height/hplus1, size, size);
                }//end if
            }//end for
        }//end for
    }//end render()
}
