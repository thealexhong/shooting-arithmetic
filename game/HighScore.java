/*
Alexander Hong
June 13, 2007
HighScore.java
This program defines the methods in the PriorityQueue interface.
It uses a Binary Heap to create a priority queue.
*/

// Library
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.ListIterator;

/**
HighScore.java
@author Danny Dinh, Alexander Hong
@version 3.1, June 13, 2007
*/
public class HighScore
{
    // ArrayList
    private ArrayList items;

    /**
    Creates an arraylist of high scores
    */
    public HighScore ()
    {
	items = new ArrayList ();
    }


    /**
    Read in highscores
    */
    public void readScores ()
    {
	// read data in file
	try
	{
	    BufferedReader input = new BufferedReader (new FileReader ("HIGHSCORE.txt"));
	    input.readLine (); // skip first 2 lines
	    input.readLine ();
	    String line = input.readLine ();
	    // split them and store them in this arraylist
	    while (line != null)
	    {
		String[] temp = line.split ("     /     ");
		items.add (new Profile (temp [1], Integer.parseInt (temp [2]), Integer.parseInt (temp [3])));
		line = input.readLine ();
	    }
	}
	catch (IOException e)
	{
	}
	catch (NumberFormatException f)
	{
	}
    }


    /**
    Write the updated high score file
    */
    public void writeScore ()
    {
	PrintWriter output; // output variable
	// store data in a file
	try
	{
	    output = new PrintWriter (new FileWriter ("HIGHSCORE.txt")); //stores the data at this location
	    // writes in the file
	    output.println ("       ===   HIGH SCORES   ===");
	    output.println ("Rank / Username / Level / Score");
	    int counter = 0;
	    ListIterator itr = items.listIterator ();
	    // iterates through this arraylist and outputs up to 10 elements in a file
	    while (itr.hasNext ())
	    {
		Profile temp = ((Profile) itr.next ());
		output.println (new Integer (counter + 1) + "     /     " + temp.getName () + "     /     "
			+ temp.getLevel () + "     /     " + temp.getScore ());
		counter++;
		// exit iteration if more than 10 elements
		if (counter == 10)
		    break;
	    }
	    output.close (); // closes the file
	}
	catch (IOException e)
	{
	}
    }


    /**
    Adds an element to high scores
    @param x  the object being added
    */
    public void add (Object x)
    {
	items.add (x); // add
	int n = items.size (); // get size
	// Transform ArrayList into Heap
	// Reheaping the list
	for (int rootIndex = (n / 2) - 1 ; rootIndex >= 0 ; rootIndex--)
	    fixHeap (rootIndex, n - 1);
	// Sort the ArrayList from max to min
	while (n > 0)
	{
	    swap (0, n - 1); // swap items [0] with items [n - 1],
	    n--;            // items [n - 1] is in its final sorted position
	    fixHeap (0, n - 1);
	}
    }


    /**
    Transform subtrees into heap.
    @param rootIndex  the index in the arraylist of the root
    @param maxIndex   the max index of the subtree
    */
    private void fixHeap (int rootIndex, int maxIndex)
    {
	// Checks if the left child is valid
	// If it is valid, checks if the left child is smaller than the root
	// If it is smaller, swap
	if (maxIndex >= 2 * rootIndex + 1 &&
		((Comparable) new Integer ((((Profile) items.get (rootIndex))).getScore ())).compareTo
		(new Integer (((Profile) items.get (2 * rootIndex + 1)).getScore ())) > 0)
	{
	    swap (rootIndex, 2 * rootIndex + 1);
	    fixHeap (2 * rootIndex + 1, maxIndex);
	}
	// Checks if the right child is valid
	// If it is valid, checks if the right child is smaller than the root
	// If it is smaller, swap
	if (maxIndex >= 2 * rootIndex + 2 &&
		((Comparable) new Integer ((((Profile) items.get (rootIndex))).getScore ())).compareTo
		(new Integer (((Profile) items.get (2 * rootIndex + 2)).getScore ())) > 0)
	{
	    swap (rootIndex, 2 * rootIndex + 2);
	    fixHeap (2 * rootIndex + 2, maxIndex);
	}
    }


    /**
     swap the values of two elements in the priority queue
     @param index1  the index to be swapped
     @param index2  the index to be swapped
     */
    private void swap (int index1, int index2)
    {
	Object temp = items.get (index1);
	items.set (index1, items.get (index2));
	items.set (index2, temp);
    }
}


