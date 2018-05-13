/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shutterearth.characters;

import java.util.ArrayList;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import shutterearth.Game;
import shutterearth.Media;
import shutterearth.screens.Scene;

/**
 *
 * @author mr.blissfulgrin
 */
public class Inventory extends Scene
{
    private final int maxGun;
    private final ArrayList <Gun> inventory;
    private int index;
    private final ArrayList <Shot> shots;
    private final ArrayList <Shot> toRemove;
    private final Charact hero;
    private final int delay;
    private int counter;
    
    public Inventory (ArrayList <int[]> guns, Charact hero)
    {
        this.hero = hero;
        this.shots = new ArrayList <>();
        this.toRemove = new ArrayList <>();
        maxGun = guns.size();
        index = 0;
        this.inventory = new ArrayList<>();
        guns.forEach((gun) ->
        {
            if (gun[1]>0)
                inventory.add(new Gun(gun[0],gun[1],0));
        });
        this.delay = 40;
        this.counter = 0;
    }
    
    public Inventory (int[] gun, Charact hero, int delay)
    {
        this.hero = hero;
        this.shots = new ArrayList <>();
        this.toRemove = new ArrayList <>();
        maxGun = 1;
        index = 0;
        this.inventory = new ArrayList<>();
        inventory.add(new Gun(gun[0],gun[1],1));
        this.delay = delay;
        this.counter = 0;
    }
    
    public void rightGun()
    {
        if (index < inventory.size()-1)
            index++;
    }
    public void lefttGun()
    {
        if (index > 0)
            index--;
    }
    public int getGunID()
    {
        return inventory.get(index).getID();
    }
    public int getCost()
    {
        return inventory.get(index).getConsume();
    }
    
    public void shot(int n)
    {
        if (counter <= 0)
        {
            int offset = (int)(hero.getH()/(n+1));
            for (int j = 0; j < n; j++)
            {
                shots.add(new Shot (inventory.get(index),hero,offset+offset*j));
            }
            counter = delay;
        }
    }
    
    public void end ()
    {
        Game.removeSence(this);
    }

    @Override
    public void Render(GameContainer gc, Graphics g) throws SlickException
    {
        shots.forEach((s) ->
        {
            if (s.isDwable())
                Game.getMedia().getImage(s.getFace()?Media.IMAGE.BULLET_R:Media.IMAGE.BULLET_L).draw(s.getX(),s.getY(),s.getW(),s.getH());
        });
    }

    @Override
    public void Update(GameContainer gc, float t) throws SlickException
    {
        shots.forEach((s) ->
        {
            s.update(t);
            if (s.isDwable()&&s.ended())
            {
                toRemove.add(s);
            }
        });
        toRemove.forEach((s)->
        {
            shots.remove(s);
        });
        toRemove.clear();
        shots.forEach((s) ->
        {
            if (s.isDwable())
            {
                hero.getEnemys().forEach((e)->
                {
                    if (s.getBox().intersects(e.getBox()))
                    {
                        e.getDamage(s.getDamage());
                        toRemove.add(s);
                        if (e.getInfo() == 0)
                        {
                            Game.getMedia().getSound(Media.SOUND.HITED).play();
                        }
                        else
                        {
                            Game.getMedia().getSound(Media.SOUND.HITED_ALIEN).play();
                            if (hero.getInfo()==0)
                            {
                                hero.setHudAlien(e, e.getCurrentHealth()+s.getDamage());
                            }
                        }
                        if(hero.getInfo()==0 && !e.isAlive())
                        {
                            hero.hasKilled(e.getHealthMax());
                        }
                    }
                });
            }
        });
        toRemove.forEach((s)->
        {
            shots.remove(s);
        });
        toRemove.clear();
        if (counter >= 0)
            counter -= 1*t;
    }

    @Override
    public void init(GameContainer gc) throws SlickException
    {
        
    }
    
    public ArrayList<int[]> save()
    {
        ArrayList <int[]> data = new ArrayList<>();
        int correction = 0;
        for (int j = 0; j<maxGun; j++)
        {
            if ((inventory.size()>j-correction)&&(inventory.get(j-correction).getID()==j))
            {
                data.add(new int[]{inventory.get(j-correction).getID(),inventory.get(j-correction).getLevel()});
            }
            else
            {
                data.add(new int[]{j,0});
                correction++;
            }
        }
        return data;
    }
    public int getNumberOfGuns()
    {
        return maxGun;
    }
    public void die()
    {
        Game.removeSence(this);
    }
}
