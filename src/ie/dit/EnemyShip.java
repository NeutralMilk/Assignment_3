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
        relativePos();

        //relativePos finds what directions the friendly ship is in
        //movement moves the enemy in the direction of the friendly
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
            float closest = pos2.dist(main.units.get(0).pos);

            GameObject go = main.units.get(i);

            //find the closest unit
            //friendlyIndex is the index of the closest unit
            //set the closest to be the first one initially
            friendlyIndex = 0;

            if(pos2.dist(go.pos) < closest)
            {
                friendlyIndex = i;
            }//end if
        }//end for
    }//end checkdistance

    public void relativePos()
    {
        for(int i = 0; i < main.units.size(); i ++)
        {
            //change the position to the centre
            GameObject go = main.units.get(friendlyIndex);

            //set pos2 x and y to either 0 or 1 to indicate if i is above, below, right or left of the unit
            //x = 1 means right, x = 0 means left, x = -1 means same x value
            //y = 1 means above, y = 0 means below, y = - 1 means same y value

            //check the x values
            if(pos.x < go.pos.x)
            {
                movement(1);
            }//end if
            if(pos.x > go.pos.x)
            {
                movement(2);
            }//end if
            //check the y values
            if(pos.y < go.pos.y)
            {
                movement(3);
            }//end if
            if(pos.y > go.pos.y)
            {
                movement(4);
            }//end if
        }//end for
    }//end relativePos

    public void movement(int direction)
    {
        //ifs for movement

        //moves right
        if(direction == 1)
        {
            pos.x += q;
        }//end if

        //moves left
        if(direction == 2)
        {
            pos.x -= q;
        }//end if

        //moves up
        if(direction == 3)
        {
            pos.y += q;
        }//end if

        //moves down
        if(direction == 4)
        {
            pos.y -= q;
        }//end if
    }//end movement
    /*public void movement(PVector relPos)
    {
        float a = pos.x + q;
        float b = pos.x - q;
        float c = pos.y + q;
        float d = pos.y - q;

        if(relPos.x == 1)
        {
            if(relPos.y == 1)
            {
                pos.set(a, d);
            }

            if(relPos.y == 0)
            {
                pos.set(a, c);
            }

        }//end if

        if(relPos.x == 0)
        {
            if(relPos.y == 1)
            {
                pos.set(b, d);
            }

            if(relPos.y == 0)
            {
                pos.set(b, c);
            }

        }//end if

        if(relPos.x == -1)
        {
            if(relPos.y == 1)
            {
                pos.set(pos.x, d);
            }

            if(relPos.y == 0)
            {
                pos.set(pos.x, c);
            }
        }//end if

    }//end movement*/

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