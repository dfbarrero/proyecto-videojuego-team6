/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shutterearth.characters;

/**
 *
 * @author mr.blissfulgrin
 */
public class Gun
{
    private final int damage;
    private final int delay;
    private final int speed;
    private final int consume;
    
    public Gun (int id, int level, int enemy)
    {
        int [][][][] gun = new int [][][][]
        {
            {
                {
                    {5,50,40,2},      //Arma Minima
                    {7,50,40,2},
                    {9,50,40,2},
                    {10,45,40,2},
                    {10,45,40,1},
                },
                {
                    {10,85,60,4},     //Arma Base
                    {12,85,60,4},
                    {13,85,60,3},
                    {14,70,60,3},
                    {15,70,60,2},
                },
                {
                    {35,150,120,10},   //Arma Fuerte
                    {38,150,120,10},
                    {40,150,120,10},
                    {40,140,120,8},
                    {40,135,115,8},
                },
                {
                    {25,100,80,8},    //Arma Rápida
                    {26,95,75,8},
                    {27,90,70,8},
                    {28,90,65,8},
                    {30,90,60,8},
                },
                {
                    {50,200,100,20},   //Arma Final
                    {55,200,100,19},
                    {60,200,100,18},
                    {60,200,90,17},
                    {60,200,90,15},
                },
            },
            {
                {
                    {10,50,200,0},    //Enemigo Base
                    {12,50,200,0},
                    {14,50,200,0},
                    {16,50,200,0},
                    {18,50,200,0},
                },
                {
                    {15,100,200,0},    //Enemigo Fuerte
                    {17,100,200,0},
                    {19,100,200,0},
                    {20,100,200,0},
                    {22,100,200,0},
                },
                {
                    {30,200,400,0},   //Nave Base
                    {32,200,400,0},
                    {34,200,400,0},
                    {36,200,400,0},
                    {38,200,400,0},
                },
                {
                    {40,250,400,0},   //Nave Fuerte
                    {42,250,400,0},
                    {44,250,400,0},
                    {46,250,400,0},
                    {48,250,400,0},
                },
                {
                    {50,150,100,0},   //Enemigo Final
                    {54,150,100,0},
                    {58,150,100,0},
                    {62,150,100,0},
                    {65,150,100,0},
                },
            }
        };
        damage = gun [enemy][id][level][0];
        delay = gun [enemy][id][level][1];
        speed = gun [enemy][id][level][2];
        consume = gun [enemy][id][level][2];
    }
    
    public int getDamage()
    {
        return damage;
    }
    public int getSpeed()
    {
        return speed;
    }
    public int getDelay()
    {
        return delay;
    }
    
    public int getConsume()
    {
        return consume;
    }
}
