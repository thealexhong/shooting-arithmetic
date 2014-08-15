/*
Danny Dinh (version 1.0)
Alexander Hong (edit in version 2.0)
June 6, 2007
BoomMathShotApp.java
This class runs the game: Boom! Math Shot.
*/

// Library
import javax.swing.JFrame;

/**
BoomMathShotApp.java
@author Danny Dinh, Alexander Hong
@version 3.1, June 10, 2007
*/
public class BoomMathShotApp extends JFrame
{
    // New Feature: decorated window since version 2.0
    public static void main (String[] args)
    {
	// New Decorated window since version 2.0 May 25, 2007
	setDefaultLookAndFeelDecorated (true); // Decorate Window
	new GameFrame (); // creates a new game window
    } // main method
}
