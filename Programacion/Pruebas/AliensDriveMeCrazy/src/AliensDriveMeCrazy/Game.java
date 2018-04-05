package AliensDriveMeCrazy;

import java.util.ArrayList;
import java.util.Collections;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;


public class Game extends BasicGame
{
    private static AppGameContainer app;
    private static ArrayList<Scene> scenes ;
    private static int x;
    private static int y;
    
    public Game (String gamename)
    {
        super(gamename);
        scenes = new ArrayList<>();
    }
    
    // Add a scene to the list and call the init method
    public static void addScene (Scene sence)
    {
        scenes.add(sence);
        try 
        {
            sence.init(app);
        } 
        catch (SlickException e) 
        {
            System.out.println("ERROR DE ESCENA " + e.toString());
        }
        Collections.sort(scenes);
    }

    // Removes a scene
    public static void removeSence ( Scene sence )
    {
        scenes.remove(sence);
    }

    @Override
    public void init(GameContainer gc) throws SlickException 
    {
        StartMenu startMenu = new StartMenu();
        scenes.add(startMenu);
        
        for( int i = 0 ; i < scenes.size() ; i++ )
        {
            scenes.get(i).init(gc);
        }
    }

    @Override
    public void update(GameContainer gc, int c) throws SlickException 
    {
        c *= 0.1;
        for( int i = 0 ; i < scenes.size() ; i++ )
        {
            scenes.get(i).update(gc,c);
        }
    }

    @Override
    public void render(GameContainer gc, Graphics g) throws SlickException
    {
        for( int i = 0 ; i < scenes.size() ; i++ )
        {
            scenes.get(i).render(gc, g);
        }
    }
    
    public static int getX()
    {
        return x;
    }
    
    public static int getY()
    {
        return y;
    }
    
    public static void setX(int x)
    {
        Game.x = x;
    }
    
    public static void setY(int y)
    {
        Game.y = y;
    }

    public static void main(String[] args) throws SlickException
    {
        app = new AppGameContainer(new Game ("Aliens Drive Me Crazy"));
        Game.setX(app.getScreenWidth());
        Game.setY(app.getScreenHeight()-100);
        app.setDisplayMode(app.getScreenWidth(),app.getScreenHeight()-100, false);
        app.setShowFPS(false);
        app.setTargetFrameRate(45);
        app.start();
    }
}