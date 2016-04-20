package ie.dit;

import java.util.ArrayList;

import ddf.minim.Minim;
import processing.core.*;

public class EnemyShip extends GameObject {
    public EnemyShip(Main _main) {
        super(_main);
        unit = main.loadImage("1.png");
        int w = unit.width * main.width / 2560;
        int h = unit.height * main.height / 1440;
        unit.resize(w, h);
        pos = new PVector(0, 0);
        pos = initialSpawn();
        initialHealth = (int)random(50,120);
        currentHealth = initialHealth;
        q = main.width/32;
    }
    PVector center = new PVector(main.width/2, main.height/2);
    private PVector initialSpawn()
    {
        int position = (int) random(1, 4);
        PVector pos = new PVector();
        //position 1 chooses a random box along the top to spawn in
        if (position == 1)
        {
            int x = (int) random(1, 32);
            int y = 0;

            x = main.cPosX[x] + main.width / 64;
            y = main.cPosY[y] + main.width / 64;
            pos.x = x;
            pos.y = y;
        }

        //position 2 chooses a random box along the bottom
        if (position == 2)
        {
            int x = (int) random(1, 32);
            int y = 16;

            x = main.cPosX[x] + main.width / 64;
            y = main.cPosY[y] + main.width / 64;
            pos.x = x;
            pos.y = y;
        }

        //position 3 chooses a box along the left
        if (position == 3)
        {
            int y = (int) random(1, 17);
            int x = 0;

            x = main.cPosX[x] + main.width / 64;
            y = main.cPosY[y] + main.width / 64;
            pos.x = x;
            pos.y = y;
        }

        //positino 4 chooses a box along the right
        if (position == 4)
        {
            int y = (int) random(1, 17);
            int x = 31;

            x = main.cPosX[x] + main.width / 64;
            y = main.cPosY[y] + main.width / 64;
            pos.x = x;
            pos.y = y;
        }

        if (checkPos(pos) == false)
        {
            initialSpawn();
        }
        return pos;
    }//end initialPos

    public boolean checkPos(PVector pos2)
    {
        boolean valid = true;
        for (int i = 0; i < main.enemyUnits.size(); i++)
        {
            //change the position to the centre
            pos2 = main.centerPos(pos2);

            GameObject go = main.enemyUnits.get(i);
            float size = main.battlefield.size / 2;

            //if the position is the same as any unit other than itself then you cannot place it
            if (pos2.x == go.pos.x && pos2.y == go.pos.y)
            {
                valid = false;
            }//end if
        }//end for
        return valid;
    }//end checkPos

    public void update()
    {
        //follows the closest friendly ship

        //checkDistance finds the closest ship
        checkDistance(pos);

        //relativePos locates where that ship is in relation to the enemy and then move it towards it.
        relativePos();

        //keep them on the screen
        if(pos.x > main.width)
        {
            pos.x = main.width - q/2;
        }

        if(pos.x < 0)
        {
            pos.x = q/2;
        }//end if

        if(pos.y > main.height - q)
        {
            pos.y = (main.height - q) - (q/2);
        }//end if

        if(pos.y < 0)
        {
            pos.y = q/2;
        }//end if

    }//end update()

    public void checkDistance(PVector pos2)
    {
        for(int i = 0; i < main.units.size(); i++)
        {
            //closest is the distance between the enemy and the closest friendly unit
            float closest = pos2.dist(main.units.get(0).pos);

            //compare every friendly unit
            GameObject go = main.units.get(i);

            //find the closest unit
            //friendlyIndex is the index of the closest unit
            //set the closest to be the first one initially
            friendlyIndex = 0;
            if(pos2.dist(center) < main.battlefield.size*4)
            {
                //make friendly index a number bigger than the max amount of friendlies
                friendlyIndex = 600;
            }
            else if(pos2.dist(go.pos) < closest)
            {
                friendlyIndex = i;
            }//end if
        }//end for
    }//end checkdistance

    public void relativePos()
    {
        if(friendlyIndex == 600)
        {
            if(pos.dist(center) > main.width/16)
            {
                if(pos.x < center.x)
                {
                    pos.x += q;
                }//end if
                if(pos.x > center.x)
                {
                    pos.x -= q;
                }//end if
                //check the y values
                if(pos.y < center.y)
                {
                    pos.y += q;
                }//end if
                if(pos.y > center.y)
                {
                    pos.y -= q;
                }//end if
            }//end if
            else
            {
                main.city.currentHealth -= (int)random(10,15);
            }
        }//end if
        else
        {
            for(int i = 0; i < main.units.size(); i ++)
            {
                GameObject go = main.units.get(friendlyIndex);

                //check the x values
                if(pos.dist(go.pos) > main.width/32)
                {
                    if(pos.x < go.pos.x)
                    {
                        pos.x += q;
                    }//end if
                    if(pos.x > go.pos.x)
                    {
                        pos.x -= q;
                    }//end if
                    //check the y values
                    if(pos.y < go.pos.y)
                    {
                        pos.y += q;
                    }//end if
                    if(pos.y > go.pos.y)
                    {
                        pos.y -= q;
                    }//end if
                }//end if
            }//end for
        }

    }//end relativePos

    public void render()
    {
        main.ellipseMode(CENTER);
        main.stroke(255, 0, 0);
        main.strokeWeight(.5f);
        main.noFill();
        main.ellipse(pos.x,pos.y, main.width/32, main.height/18);
        main.imageMode(CENTER);
        main.image(unit, pos.x, pos.y);
    }//end render
}