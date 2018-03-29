/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AliensDriveMeCrazy;

import java.util.ArrayList;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.command.BasicCommand;
import org.newdawn.slick.command.Command;
import org.newdawn.slick.command.InputProvider;
import org.newdawn.slick.command.InputProviderListener;
import org.newdawn.slick.command.KeyControl;

/**
 *
 * @author mr.blissfulgrin
 */
public class Field extends Scene implements InputProviderListener
{
    private InputProvider provider;
    private final Command UP = new BasicCommand("UP");
    private final Command DOWN = new BasicCommand("DOWN");
    private final Command LEFT = new BasicCommand("LEFT");
    private final Command RIGHT = new BasicCommand("RIGHT");
    private final Command SHOT = new BasicCommand("SHOT");
    
    private final Hero hero;
    private final ArrayList <BadGuy> badGuy;
    
    public Field (Hero hero, ArrayList <BadGuy> badGuy)
    {
        this.hero = hero;
        this.badGuy = new ArrayList <>();
    }

    @Override
    public void Render(GameContainer gc, Graphics g) throws SlickException
    {
        hero.draw();
    }

    @Override
    public void Update(GameContainer gc, int t) throws SlickException
    {
        hero.move(t);
        hero.shot(t);
    }

    @Override
    public void init(GameContainer gc) throws SlickException
    {
        provider = new InputProvider(gc.getInput());
        provider.addListener(this);

        provider.bindCommand(new KeyControl(Input.KEY_UP), UP);
        provider.bindCommand(new KeyControl(Input.KEY_DOWN), DOWN);
        provider.bindCommand(new KeyControl(Input.KEY_LEFT), LEFT);
        provider.bindCommand(new KeyControl(Input.KEY_RIGHT), RIGHT);
        provider.bindCommand(new KeyControl(Input.KEY_SPACE), SHOT);
    }
    
    @Override
    public void controlPressed(Command command) 
    {
        if (command.equals(UP))
        {
            hero.UP();
        }
        else if (command.equals(DOWN))
        {
            hero.DOWN();
        }
        else if (command.equals(LEFT))
        {
            hero.LEFT();
        }
        else if (command.equals(RIGHT))
        {
            hero.RIGHT();
        }
        else if (command.equals(SHOT))
        {
            hero.SHOT();
        }
    }
    
    @Override
    public void controlReleased(Command command) {}
    
}
