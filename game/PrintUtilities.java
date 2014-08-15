/*
Alexander Hong (edited original)
Source: <http://www.apl.jhu.edu/~hall/java/Swing-Tutorial/Swing-Tutorial-Printing.html>
June 12, 2007
PrintUtilities.java
This class prints a specific component.
*/

/*
Variable Dictionary
componentToBePrinted: the component to be printed
printJob: the printer job
g2d: Graphics2D object
*/

// Library
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.RepaintManager;
import java.awt.print.Printable;
import java.awt.print.PrinterJob;
import java.awt.print.PrinterException;
import java.awt.print.PageFormat;

/**
PrintUtilities.java
@author Danny Dinh, Alexander Hong
@version 3.1, June 10, 2007
*/
public class PrintUtilities implements Printable
{
    private Component componentToBePrinted;

    /**
    Printthespecifiedcomponent.
    */
    public PrintUtilities (Component componentToBePrinted)
    {
	this.componentToBePrinted = componentToBePrinted;
    }


    /**
    Prints the component
    */
    public static void printComponent (Component c)
    {
	new PrintUtilities (c).print ();
    }


    /**
    The print dialog.
    */
    public void print ()
    {
	PrinterJob printJob = PrinterJob.getPrinterJob ();
	printJob.setPrintable (this);
	// print if user does not cancel
	if (printJob.printDialog ())
	    try
	    {
		printJob.print ();
	    }
	catch (PrinterException pe)
	{
	    System.out.println ("Error printing: " + pe);
	}
    }


    /**
    Prints the page at the specified index into the specified Graphics context in the specified format.
    */
    public int print (Graphics g, PageFormat pageFormat, int pageIndex)
    {
	// checks if page exist
	if (pageIndex > 0)
	{
	    return (NO_SUCH_PAGE);
	}
	else
	{
	    Graphics2D g2d = (Graphics2D) g;
	    g2d.translate (pageFormat.getImageableX (), pageFormat.getImageableY ());
	    disableDoubleBuffering (componentToBePrinted);
	    componentToBePrinted.paint (g2d);
	    enableDoubleBuffering (componentToBePrinted);
	    return (PAGE_EXISTS);
	}
    }


    /**
    Disables double buffering.
    */
    public static void disableDoubleBuffering (Component c)
    {
	RepaintManager currentManager = RepaintManager.currentManager (c);
	currentManager.setDoubleBufferingEnabled (false);
    }


    /**
    Enables double buffering.
    */
    public static void enableDoubleBuffering (Component c)
    {
	RepaintManager currentManager = RepaintManager.currentManager (c);
	currentManager.setDoubleBufferingEnabled (true);
    }
}

