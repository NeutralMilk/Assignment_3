package ie.dit;

import java.util.ArrayList;

import ddf.minim.Minim;
import processing.core.*;

public class EnemyShip extends GameObject {
    int q;
    public EnemyShip(Main _main) {
        super(_main);
        unit = main.loadImage("1.png");
        int w = unit.width * main.width / 2560;
        int h = unit.height * main.height / 1440;
        unit.resize(w, h);
        pos = new PVector(0, 0);
        pos = initialSpawn();
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
        //if it does not detect a freindly ship near by, roam around randomly
        if (checkDistance(pos) == false)
        {
            int direction = (int) random(1, 8);
            pos = movement(direction, pos);
            println("first");
        }

        //if it detects a friendly nearby, it will start following it
        /*else
        {
            println("second");
            PVector relativePos = relativePos(pos);
            println(relativePos);

            //right, above
            if(relativePos.x == 1)
            {
                pos.x += q;
                if(relativePos.y == 1)
                {
                    pos.y -= q;
                }

                if(relativePos.y == 0)
                {
                    pos.y += q;
                }
            }//end if

            if(relativePos.x == 0)
            {
                pos.x -= q;
                if(relativePos.y == 1)
                {
                    pos.y -= q;
                }

                if(relativePos.y == 0)
                {
                    pos.y += q;
                }
            }//end if

            if(relativePos.x == -1)
            {
                pos.x += 0;
                if(relativePos.y == 1)
                {
                    pos.y -= q;
                }

                if(relativePos.y == 0)
                {
                    pos.y += q;
                }
            }//end if
        }//else*/

        //wrap them around the screen
        if(pos.x > main.width)
        {
            pos.x = q/2;
        }

        if(pos.x < 0)
        {
            pos.x = main.width - q/2;
        }//end if

        if(pos.y > main.height - q)
        {
            pos.y = q/2;
        }//end if

        if(pos.y < 0)
        {
            pos.y = (main.height - q) - (q/2);
        }//end if
    }//end update()

    public PVector movement(int direction, PVector pos2)
    {
        //ifs for movement
        //moves down and right
        if(direction == 1)
        {
            pos2.x += q;
            pos2.y -= q;
        }//end if

        //moves down and left
        if(direction == 2)
        {
            pos2.x += q;
            pos2.y += q;
        }//end if

        //moves up and right
        if(direction == 3)
        {
            pos2.x -= q;
            pos2.y -= q;
        }//end if

        //moves down and left
        if(direction == 4)
        {
            pos2.x -= q;
            pos2.y += q;
        }//end if

        if(direction == 5)
        {
            pos2.x += 0;
            pos2.y += q;
        }//end if

        if(direction == 6)
        {
            pos2.y -= 0;
            pos2.x += q;

        }//end if

        if(direction == 7)
        {
            pos2.x += 0;
            pos2.y -= q;
        }//end if

        if(direction == 8)
        {
            pos2.y += 0;
            pos2.x -= q;
        }//end if
        println("pos2 is" + pos2);
        return pos2;
    }//end movement
    public boolean checkDistance(PVector pos2)
    {
        boolean withinRange = false;
        for(int i = 0; i < main.units.size(); i++)
        {
            //change the position to the centre
            //pos2 = main.centerPos(pos2);

            GameObject go = main.units.get(i);
            float size = main.battlefield.size;

            //if the position is the same as any unit other than itself then you cannot place it
            if(pos2.x < go.pos.x + size*3 && pos2.x > go.pos.x - size*3)
            {
                if(pos2.y < go.pos.y + size*3 && pos2.y > go.pos.y - size*3)
                {
                    println("this is within ranger");
                    withinRange = true;
                }//end if
            }//end if
        }//end for
        return withinRange;
    }

    public PVector relativePos(PVector pos2)
    {
        for(int i = 0; i < main.units.size(); i++)
        {
            //change the position to the centre
            GameObject go = main.units.get(i);
            float size = main.battlefield.size/2;

            //set pos2 x and y to either 0 or 1 to indicate if i is above, below, right or left of the unit
            //x = 1 means right, x = 0 means left, x = -1 means same x value
            //y = 1 means above, y = 0 means below, y = - 1 means same y value

            //check the x values
            if(pos2.x < go.pos.x)
            {
                pos2.x = 1;
            }//end if
            if(pos2.x > go.pos.x)
            {
                pos2.x = 0;
            }//end if
            if(pos2.x == go.pos.x)
            {
                pos2.x = -1;
            }//end if

            //check the y values
            if(pos2.y < go.pos.y)
            {
                pos2.y = 1;
            }//end if
            if(pos2.y > go.pos.y)
            {
                pos2.y = 0;
            }//end if
            if(pos2.y == go.pos.y)
            {
                pos2.y = -1;
            }//end if

        }//end for
        return pos2;
    }//end relativePos

    public void render()
    {
        main.ellipseMode(CENTER);
        main.stroke(255, 0, 0);
        main.strokeWeight(.5f);
        main.noFill();
        main.ellipse(pos.x,pos.y, main.width/32, main.height/18);
        main.pushMatrix();
        main.translate(pos.x, pos.y);
        main.imageMode(CENTER);
        main.image(unit, 0, 0);
        main.popMatrix();
        main.rectMode(CORNER);
    }//end render
}