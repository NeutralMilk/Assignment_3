package ie.dit;

import java.util.ArrayList;

import ddf.minim.Minim;
import processing.core.*;

public class EnemyShip extends GameObject
{
    public EnemyShip(Main _main)
    {
        super(_main);
        unit = main.loadImage("1.png");
        int w = unit.width * main.width/2560;
        int h = unit.height * main.height/1440;
        unit.resize(w,h);
        pos = new PVector(0,0);
        pos = initialSpawn();
    }

    private PVector initialSpawn()
    {
        int position = (int)random(1,4);
        PVector pos = new PVector();
        //position 1 chooses a random box along the top to spawn in
        if(position == 1)
        {
            int x = (int)random(1,32);
            int y = 0;

            x = main.cPosX[x] + main.width/64;
            y = main.cPosY[y] + main.width/64;
            pos.x = x;
            pos.y = y;
        }

        //position 2 chooses a random box along the bottom
        if(position == 2)
        {
            int x = (int)random(1,32);
            int y = 16;

            x = main.cPosX[x] + main.width/64;
            y = main.cPosY[y] + main.width/64;
            pos.x = x;
            pos.y = y;
        }

        //position 3 chooses a box along the left
        if(position == 3)
        {
            int y = (int)random(1,17);
            int x = 0;

            x = main.cPosX[x] + main.width/64;
            y = main.cPosY[y] + main.width/64;
            pos.x = x;
            pos.y = y;
        }

        //positino 4 chooses a box along the right
        if(position == 4)
        {
            int y = (int)random(1,17);
            int x = 31;

            x = main.cPosX[x] + main.width/64;
            y = main.cPosY[y] + main.width/64;
            pos.x = x;
            pos.y = y;
        }

        if(checkPos(pos) == false)
        {
            initialSpawn();
        }
        return pos;
    }

    public boolean checkPos(PVector pos2)
    {
        boolean valid = true;
        for(int i = 0; i < main.enemyUnits.size(); i++)
        {
            //change the position to the centre
            pos2 = main.centerPos(pos2);

            GameObject go = main.enemyUnits.get(i);
            float size = main.battlefield.size/2;

            //if the position is the same as any unit other than itself then you cannot place it
            if(pos2.x == go.pos.x && pos2.y == go.pos.y)
            {
                valid = false;
            }//end if
        }//end for
        return valid;
    }//end checkPos

    public void update()
    {

        int direction = (int)random(1,4);
        //position 1 chooses a random box along the top to spawn in
        int q = main.width/w;
        if(direction == 1)
        {
            pos.x += q;
            pos.y += q;
        }

        //position 2 chooses a random box along the bottom
        if(direction == 2)
        {
            pos.x -= q;
            pos.y += q;
        }

        //position 3 chooses a box along the left
        if(direction == 3)
        {
            pos.x += q;
            pos.y -= q;
        }

        //positino 4 chooses a box along the right
        if(direction == 4)
        {
            pos.x -= q;
            pos.y -= q;
        }

        if(pos.x > main.width)
        {
            pos.x = q/2;
        }

        if(pos.x < 0)
        {
            pos.x = main.width - q/2;
        }

        if(pos.y > main.height - q)
        {
            pos.y = q/2;
        }

        if(pos.y < 0)
        {
            pos.y = (main.height - q) - (q/2);
        }
    }//end update()

    public void render()
    {
        main.rectMode(CENTER);
        main.stroke(255, 0, 0);
        main.strokeWeight(.5f);
        main.noFill();
        main.rect(pos.x,pos.y, main.width/32, main.height/18);
        main.pushMatrix();
        main.translate(pos.x, pos.y);
        main.imageMode(CENTER);
        main.image(unit, 0, 0);
        main.popMatrix();
        main.rectMode(CORNER);
    }//end render
}