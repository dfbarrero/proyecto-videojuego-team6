
package shutterearth.characters;

import java.util.ArrayList;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import shutterearth.Game;
import shutterearth.Media;
import shutterearth.map.Field;

/**
 *
 * @author mr.blissfulgrin
 */
public class Hero extends CharactX
{
    private final String user;
    private final String pswd;
    private final Boolean permission;
    private int stage;
    private int bullets;
    private int kills;
    
    public Hero(SavedHero hero)
    {
        this.user = hero.getUser();
        this.pswd = hero.getPswd();
        this.permission = hero.getPermission();
        this.bullets = hero.getBullets();
        this.healthMax = hero.getHealthMax();
        this.healthCurrent = hero.getHealthMax();
        this.stage = hero.getStage();
        this.kills = hero.getKills();
        this.inventory = new Inventory (hero.getInventory(),this);
        
        this.h = Game.getY()/11;
        this.w = (h*9)/17;
        this.jumpUp = false;
        this.jumpDown = false;
        this.over = false;
        this.xPos = Game.getxVel();
        animationTime = 50;
        counterAnimation = 0;
        animation = false;
    }
    
    @Override
    public void goUp()
    {
        if (!jumping())
        {
            yVel = Game.getyVelUp();
            this.setY(yPos-1);
            jumpUp = true;
        }
    }
    @Override
    public void goLeft()
    {
        xVel = -Game.getxVel();
    }
    @Override
    public void goRight()
    {
        xVel = Game.getxVel();
    }
    @Override
    public void goDown()
    {
        if (!jumping())
        {
            yVel = Game.getyVelDown();
            this.setY(yPos-1);
            jumpDown = true;
        }
    }

    @Override
    public void shot()
    {
        if (!jumping())
        {
            if (bullets >= inventory.getCost())
            {
                bullets -= inventory.getCost();
                inventory.shot(1);
            }
        }
    }
    
    @Override
    public void Render(GameContainer gc, Graphics g) throws SlickException
    {        
        if (Game.debug())
        {
            for (Rectangle rect : this.debug())
            {
                Game.getMedia().getImage(Media.IMAGE.GREY).draw(rect.getX(),rect.getY(),rect.getWidth(),rect.getHeight());
            }
        } 
        Game.getMedia().getSprit(xVel > 0? Media.SPRITE.BASE_DER : Media.SPRITE.BASE_IZQ).draw(xPos,yPos,w,h);
        if (animation)
        {
            Game.getMedia().getImage(this.getFace()?Media.IMAGE.FIRE_R:Media.IMAGE.FIRE_L).draw(xPos+(this.getFace()?-10:0),yPos,w+10,h);
        }
    }

    @Override
    public void Update(GameContainer gc, float t) throws SlickException
    {
        this.setX(this.xPos + this.xVel*t);
        if (yVel < Game.getGravityMax())
            yVel += Game.getGravity()*t;
        this.setY(this.yPos + this.yVel*t);
        
        if (jumpUp)
        { 
            if (over)
            {
                if (yPos >= floor)
                {
                    jumpUp = false;
                    over = false;
                    yPos = floor;
                }  
            }
            else
            {
                over = (yPos < floor);
            }
        }
        else if(jumpDown)
        {
            if (yPos >= floor)
            {
                jumpDown = false;
                yPos = floor;
            }
        }
        else if (yPos >= floor)
        {
            this.setY(floor);
        }
        
        if (xPos <= colum.getX())
        {
            this.goRight();
        }
        if (xPos+w >= colum.getX()+colum.getWidth())
        {
            this.goLeft();
        }
        
        if (animation)
        {
            if (counterAnimation > animationTime)
            {
                animation = false;
                counterAnimation = 0;
            }
            counterAnimation+=1*t;
        }
    }

    @Override
    public void init(GameContainer gc) throws SlickException{}
    
    public boolean jumping ()
    {
        return jumpUp || jumpDown;
    }
    @Override
    public void place (float floor, int left, int right)
    {
        this.xPos = left;
        this.yPos = floor - h;
        this.floor = floor - h;
        line = new Rectangle (0,yPos,Game.getX(),floor+h-yPos);
        colum = new Rectangle (left,0,right-left,Game.getY());
        box = new Rectangle (xPos,yPos,w,h);
    }
    @Override
    public void setBounds (float left, float right, float floor)
    {
        colum.setX(left);
        colum.setWidth(right-left);
        this.floor = floor + h;
    }
    
    @Override
    protected void setX(float x)
    {
        this.xPos = x;
        box.setX(x);
    }
    @Override
    protected void setY(float y)
    {
        this.yPos = y;
        box.setY(y);
        line.setY(y);
        line.setHeight(floor+h-y);
    }
    
    @Override
    public void hasKilled(int money)
    {
        bullets += money;
        kills++;
    }
        public void inventroyLeft()
    {
        inventory.lefttGun();
    }
    public void inventoryRight()
    {
        inventory.rightGun();
    }
    
    public void setStage (int stage)
    {
        if (this.stage < stage)
            this.stage = stage;
    }
    
    public String getUser ()
    {
        return user;
    }
    
    public String getPswd ()
    {
        return pswd;
    }
    
    public int getHealthCurrent ()
    {
        return healthCurrent;
    }
    
    public int getStage ()
    {
        return stage;
    }
    
    public int getBullets ()
    {
        return bullets;
    }
    
    public int getKills()
    {
        return kills;
    }
    
    public SavedHero save()
    {
        SavedHero saved = new SavedHero(this);
        Game.save(saved);
        return saved;
    }
    
    public Boolean getPermission ()
    {
        return permission;
    }
    public ArrayList<int[]> saveInventory()
    {
        return inventory.save();
    }
    
    public int getNumberOfGuns()
    {
        return inventory.getNumberOfGuns();
    }
    
    public void setField(Field field)
    {
        this.field = field;
    }
    
    @Override
    public int getInfo ()
    {
        return 0;
    }
    
    @Override
    public void doShotAnimation()
    {
        Game.getMedia().getSound(Media.SOUND.SHOT).play();
        animation = true;
    }
    @Override
    public void setHudAlien (Charact enemy, int LastLive)
    {
        field.setHudAlien(enemy, LastLive);
    }
}
