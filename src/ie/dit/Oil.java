package ie.dit;

import java.util.ArrayList;

import ddf.minim.Minim;
import processing.core.*;

public class Oil extends GameObject
{
    public Oil(Main _main)
    {
        super(_main);
        unit = main.loadImage("rig.png");

        //this scales the units to fit any screen size
        int w = unit.width * main.width/2560;
        int h = unit.height * main.height/1440;
        unit.resize(w,h);
        pos = new PVector();
        mouseBox = new PVector();
        easing = .7f;
        madeMove = false;
        initialHealth = 50;
        currentHealth = 50;
        clicks = 0;
    }

    public void update()
    {
        if (move == 0) {
            float targetX = main.mouseX;
            float dx = targetX - pos.x;
            pos.x += dx * easing;

            float targetY = main.mouseY;
            float dy = targetY - pos.y;
            pos.y += dy * easing;
        }//end if 0

        if (move == 1)
        {
            for (int i = 0; i < w; i++)
            {
                for (int j = 0; j < h; j++)
                {
                    if (pos.x < (main.cPosX[i] + main.width / w + 1) && pos.x > main.cPosX[i] && pos.y < (main.cPosY[j] + main.height / hplus1 + 1) && pos.y > main.cPosY[j])
                    {
                        main.visited[i][j]= true;

                        pos.x = main.cPosX[i] + main.width / 64;
                        pos.y = main.cPosY[j] + main.height / 36;
                    }//end if
                }//end for
            }//end for
        }//end if 1
    }//end update()

    //this function checks to see if the mouse is within two boxes up, down, left or right of the unit
    /*public boolean validTiles()
    {
        for(int i = 0; i < w; i++)
        {
            for(int j = 0; j < h; j++)
            {
                if(main.battlefield.placeOil[i][j] == true)
                {
                    validTile = checkPos(mouseBox);
                }//end f
            }//end for
        }//end for

        return validTile;
    }//end validTiles()*/

    boolean enemy= false;
    //checks the position of every other ship
    public boolean checkPos(PVector mouse)
    {
        boolean valid = true;
        for(int i = 0; i < main.units.size(); i++)
        {
            //change the position to the centre
            mouse = main.centerPos(mouse);

            GameObject go = main.units.get(i);
            float size = main.battlefield.size/2;

            //if the position is the same as any unit other than itself then you cannot place it
            if(mouse.x == go.pos.x && mouse.y == go.pos.y)
            {
                valid = false;
            }//end if
        }//end for

        for(int i = 0; i < main.enemyUnits.size(); i++)
        {
            //change the position to the centre
            mouse = main.centerPos(mouse);

            GameObject go = main.enemyUnits.get(i);
            float size = main.battlefield.size/2;

            //if the position is the same as any unit other than itself then you cannot place it
            if(mouse.x == go.pos.x && mouse.y == go.pos.y)
            {
                enemy = true;
                enemyIndex = i;
            }//end if
        }//end for
        return valid;
    }//end checkPos

    public void render()
    {
        switch(move)
        {
            case 0:
            {
                main.pushMatrix();
                main.translate(main.mouseX, main.mouseY);
                main.imageMode(CENTER);
                main.image(unit, 0, 0);
                main.popMatrix();
                break;
            }//end case 0

            case 1:
            {
                main.pushMatrix();
                main.translate(pos.x, pos.y);
                main.imageMode(CENTER);
                main.image(unit, 0, 0);
                main.popMatrix();
                break;
            }//end case 1
        }//end switch
    }//end render
}