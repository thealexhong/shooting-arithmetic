/*
Alexander Hong (since v.3.1)
June 10, 2007
Projectile.java
This class calculates the position of an object in a projectile.
*/

/*
Variable Dictionary
power: the power used to start the projectile
angle: the starting angle of the projectile
vx: horizontal velocity
vy: vertical velocity
*/

/**
Projectile.java
@author Danny Dinh, Alexander Hong
@version 3.1, June 10, 2007
*/
public class Projectile
{
    // Class Var
    int power;
    int angle;
    double vx;
    double vy;

    /**
    The projectile.
    @param power  the starting power of the projectile
    @param angle  the starting angle of the projectile
    */
    public Projectile (int power, int angle)
    {
	this.power = power;
	this.angle = angle;
	vx = Math.cos (Math.toRadians (angle)) * power;
	vy = Math.sin (Math.toRadians (angle)) * power;
    }


    // Accessor methods

    /**
     Get the horizontal position at a specified time during this projectile.
     @param time   the specified time of the projectile
     @return       the horizontal position of the projectile
     */
    public double getXPos (double time)
    {
	return (vx * time);
    }


    /**
     Get the vertical position at a specified time during this projectile.
     @param time   the specified time of the projectile
     @return       the vertical position of the projectile
     */
    public double getYPos (double time)
    {
	return (vy * time + .5 * (-9.81) * Math.pow (time, 2));
    }
}
