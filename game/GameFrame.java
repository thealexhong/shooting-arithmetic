/*
Danny Dinh (version 1.0, edit v.3.1)
Alexander Hong (edit in version 2.0, 3.0, 3.1)
June 10, 2007
GameFrame.java
This class sets up the game window and menus for: Boom! Math Shot.
*/

/*
Variable Dictionary
newItem, openItem, saveItem,
    saveAsItem, exitItem, aboutItem, readMeItem: menu items. Each item does a specific action.
dialog: a dialog box (JDialog).
filename: the name of a file used for reading or writing.
fileExtension : the unique file extension that this program uses to write and read.
fileHeader: the file header that this program will create. It is used for reading a valid file.
input: a file io variable used to read the file.
output: a file io variable used to write in the file.
temp: a temporary string used to check data in the file.
fileMenu, helpMenu: menus in the menu bar.
myMenus: menu bar for this program.
closeButton: a button that closes the dialog.
logo: box contains logo image.
about: box contains about text.
display: text area.
scrollPane: scroll pane.
rmFile: used to read README.txt.
test: a temporary string testing for EOF.
sequencer: used to play music.
cancel: true when user pushes cancel, otherwise false.
option:  the result of askSave JOptionPane.
rOption: the result of replace JOptionPane .
*/

// Library
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JLabel;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import javax.sound.midi.Sequencer;
import javax.sound.midi.MidiSystem;
import java.io.File;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.InvalidMidiDataException;
import javax.swing.ImageIcon;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.KeyStroke;

/**
GameFrame.java
@author Danny Dinh, Alexander Hong
@version 3.1, June 10, 2007
*/
public class GameFrame extends JFrame implements ActionListener
{
    // Class Variables
    //++ Frame Variables
    JMenuItem newItem, openItem, saveItem,
	saveAsItem, exitItem, aboutItem, readMeItem, helpItem;
    JDialog dialog;
    //++ File IO
    String filename, tempFilename;
    final String fileExtension = ".bms", fileHeader = "<>n54fsdf@fg%$$#6htw5#%r35%%6</>";
    BufferedReader input;
    String temp;
    MasterPanel m = new MasterPanel ();
    boolean cancel;
    int option;
    static Sequencer sequencer;

    /**
    Builds the GameFrame. Default constructor.
    */
    public GameFrame ()
    {
	// Window Title
	super ("Boom! Math Shot");
	filename = null;
	// add the master panel
	getContentPane ().add (m);
	// start music
	try
	{
	    sequencer = MidiSystem.getSequencer ();  //creates a sequencer for the sequence
	    sequencer.open (); //opens the sequencer
	    //sets the current sequence on which the sequencer operates
	    sequencer.setSequence (MidiSystem.getSequence (new File ("Audio/BoomMathShotTheme1.mid"))); //reads the musical information from a file
	    sequencer.start (); //starts playing
	}
	catch (IOException e)
	{
	}
	catch (MidiUnavailableException e)
	{
	}
	catch (InvalidMidiDataException e)
	{
	}

	// Menu Items
	newItem = new JMenuItem ("New Game");
	newItem.setAccelerator (KeyStroke.getKeyStroke ("control N"));
	openItem = new JMenuItem ("Load...");
	openItem.setAccelerator (KeyStroke.getKeyStroke ("control O"));
	saveItem = new JMenuItem ("Save Game");
	saveItem.setAccelerator (KeyStroke.getKeyStroke ("control S"));
	saveAsItem = new JMenuItem ("Save As...");
	exitItem = new JMenuItem ("Exit");
	exitItem.setAccelerator (KeyStroke.getKeyStroke ("control Q"));
	helpItem = new JMenuItem ("Help");
	helpItem.setAccelerator (KeyStroke.getKeyStroke ("control H"));
	aboutItem = new JMenuItem ("About");
	aboutItem.setAccelerator (KeyStroke.getKeyStroke ("control A"));
	readMeItem = new JMenuItem ("Read Me"); // New in v.3.1 since June 9, 2007
	readMeItem.setAccelerator (KeyStroke.getKeyStroke ("control R"));

	// Menus
	JMenu fileMenu = new JMenu ("File");
	JMenu helpMenu = new JMenu ("Help");

	// Adds the menu items to the menus
	fileMenu.add (newItem);
	fileMenu.add (openItem);
	fileMenu.addSeparator ();
	fileMenu.add (saveItem);
	fileMenu.add (saveAsItem);
	fileMenu.addSeparator ();
	fileMenu.add (exitItem);
	helpMenu.add (helpItem);
	helpMenu.add (aboutItem);
	helpMenu.add (readMeItem);

	// Menu Bar
	JMenuBar myMenus = new JMenuBar ();

	// Adds the menus to the menu bar
	myMenus.add (fileMenu);
	myMenus.add (helpMenu);

	// Sets up the menu bar
	setJMenuBar (myMenus);

	// Adds an action listener to receive action events from these menu items
	newItem.addActionListener (this);
	openItem.addActionListener (this);
	saveItem.addActionListener (this);
	saveAsItem.addActionListener (this);
	exitItem.addActionListener (this);
	helpItem.addActionListener (this);
	aboutItem.addActionListener (this);
	readMeItem.addActionListener (this);

	// Window Setting - Changed since version 3.0; Resize window
	setSize (800, 650); // Window's size (in pixels)
	setLocationRelativeTo (this); // centres the Window
	setVisible (true); // visible to user
	setResizable (false); // not resizable

	// sets the close operation
	setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
    }


    /**
    Performs a specific action for each menu item accessed. Method changed since version 1.0.
    @param ae ActionEvent Object
    */
    public void actionPerformed (ActionEvent ae)
    {
	// Ask user to save before continuing
	if (ae.getActionCommand ().equals ("New Game") || ae.getActionCommand ().equals ("Load...")
		|| ae.getActionCommand ().equals ("Exit"))
	{
	    askSave ();
	    if (option == JOptionPane.CANCEL_OPTION)
		return;
	}
	// User has selected the "New" menu item
	if (ae.getActionCommand ().equals ("New Game"))
	{
	    newGame ();
	}
	// User has selected the "Load Game" menu item
	if (ae.getActionCommand ().equals ("Load..."))
	{
	    open ();
	}
	// User has selected the "Save" menu item
	if (ae.getActionCommand ().equals ("Save Game"))
	{
	    save ();
	}
	// User has selected the "Save As.." menu item
	if (ae.getActionCommand ().equals ("Save As..."))
	{
	    saveAs ();
	}
	// User has selected the "Quit" menu item
	if (ae.getActionCommand ().equals ("Exit"))
	{
	    System.exit (0); // exits the program
	}
	// User has selected the "Help" menu item
	if (ae.getActionCommand ().equals ("Read Me"))
	{
	    readMeDialog ();
	}
	// User has selected the "About" menu item
	if (ae.getActionCommand ().equals ("About"))
	{
	    aboutDialog (); //shows a about dialog
	}
	// User has selected the "Help" menu item
	if (ae.getActionCommand ().equals ("Help"))
	{
	    //run the help file
	    try
	    {
		Runtime.getRuntime ().exec ("hh.exe Boom! Math Shot Help.chm");
	    }
	    catch (IOException e)
	    { // Error Message
		JOptionPane.showMessageDialog (this, "Unable to find Help.", "Error", JOptionPane.ERROR_MESSAGE);
	    }
	}
    }


    /**
    Creates a new game. Resets all Game stats. Added in this version.
    */
    private void newGame ()
    {
	// exit method if user press cancel in JOptionPane
	if (cancel == true)
	{
	    cancel = false;
	    return;
	}
	dispose ();
	filename = null;
	sequencer.stop ();
	new GameFrame ();
    }


    /**
    Asks the user for the filename (errortraps) to save or open. Added in this version.
    @param  string Open or save as
    */
    private void askFileName (String string)
    {
	// Gets the user's input for filename in an input dialog
	filename = JOptionPane.showInputDialog ("Enter the filename to " + string + " excluding the extension (.bms): ", "My Game");

	// User has selected the Cancel option
	if (filename == null)
	{
	    return; // exits the method
	}
	// delete leading and trailing spaces
	filename = filename.trim ();
	// filename must be at least 1 character long
	if (filename.length () > 0)
	{
	    // Find invalid characters in filename
	    for (int xChar = 0 ; xChar < filename.length () ; xChar++)
	    {
		// filename cannot have the characters \, /, :, *, ?, <, >, |, "
		if (filename.charAt (xChar) == '\\' || filename.charAt (xChar) == '/'
			|| filename.charAt (xChar) == ':' || filename.charAt (xChar) == '*'
			|| filename.charAt (xChar) == '?' || filename.charAt (xChar) == '<'
			|| filename.charAt (xChar) == '>' || filename.charAt (xChar) == '|'
			|| filename.charAt (xChar) == '\"')
		{
		    // Error Message
		    JOptionPane.showMessageDialog (this, "Filenames cannot have the characters \\, /, :, *, ?, <, >, |, \".", "Error", JOptionPane.ERROR_MESSAGE);
		    filename = null; // filename is null
		    break; //exits the loop
		}
	    }
	}

	// checks if the filename is all spaces or if the filename is nothing
	if (filename.length () < 1)
	    // Error Message
	    JOptionPane.showMessageDialog (this, "The filename is invalid.", "Error", JOptionPane.ERROR_MESSAGE);
    }


    /**
    Ask user to save the game before deleting it.
    */
    private void askSave ()
    {
	// ask user to save if unsaved
	if (m.savedData == false)
	{
	    // Asks the user to save unsaved data file
	    option = JOptionPane.showConfirmDialog (this, "Do you wish to save your previous game?", "Save?", JOptionPane.YES_NO_CANCEL_OPTION);

	    // User has selected the Yes option
	    if (option == JOptionPane.YES_OPTION)
		save (); // saves the file
	}
    }


    /**
    Checks if the file already exist. Added since 2.0 version.
    @return true, if the file exist, otherwise false
    @param filename name of file
    */
    private boolean checkExistence (String filename)
    {
	// Reads data in a file
	try
	{
	    input = new BufferedReader (new FileReader (filename + fileExtension)); //reads the data at this location
	    temp = input.readLine (); //gets the input for temp
	    // Something is in the file, the file exists
	    if (temp != null)
	    {
		return (true); // returns true, there is an existing file
	    }
	}
	catch (IOException e)
	{
	}
	return (false); //returns false, there is no file with the name of filename
    }



    /**
    Asks the user for a valid filename to open. Added in this version.
    */
    private void open ()
    {
	// exit method if user press cancel in JOptionPane
	if (cancel == true)
	{
	    cancel = false;
	    return;
	}
	tempFilename = filename; // remembers filename
	askFileName ("open"); // asks the user for the filename to open
	// User has selected the Cancel option
	if (filename == null)
	{
	    filename = tempFilename;
	    cancel = true;
	    return; // exits the method
	}
	// checks if the file exist
	if (checkExistence (filename))
	{
	    // reads the file
	    try
	    {
		// checks if the file header matches
		if (!(temp.equals (fileHeader)))
		{
		    // Error Message
		    JOptionPane.showMessageDialog (this, "The file is corrupted.", "Error", JOptionPane.ERROR_MESSAGE);
		    filename = tempFilename;
		    return;
		}
		// get data
		m.username = input.readLine ();
		m.text = input.readLine ();
		m.text2 = input.readLine ();
		m.text3 = input.readLine ();
		m.text4 = input.readLine ();
		m.pause = true;
		m.existGame = true;
		try
		{
		    m.r.answerPlacement = Integer.parseInt (input.readLine ());
		    m.difficulty = Integer.parseInt (input.readLine ());
		    m.level = Integer.parseInt (input.readLine ());
		    m.exp = Integer.parseInt (input.readLine ());
		    m.wrong = Integer.parseInt (input.readLine ());
		    m.backgroundChoice = Integer.parseInt (input.readLine ());
		}
		catch (NumberFormatException e)
		{
		    // Error Message
		    JOptionPane.showMessageDialog (this, "The file is corrupted.", "Error", JOptionPane.ERROR_MESSAGE);
		    return;
		}
		m.totalExp = m.level * 50; // set exp
		m.angleField.setText ("");
		m.powerField.setText ("");
		// continue current existing game
		m.startGame ();
		m.savedData = true;
	    }
	    catch (IOException e)
	    {
	    }
	}
	else
	{
	    // Error Message
	    JOptionPane.showMessageDialog (this, "The file does not exist.", "Error", JOptionPane.ERROR_MESSAGE);
	    filename = tempFilename;
	}
    }


    /**
    Write game data to a file. Added in this version.
    */
    private void writeFile ()
    {
	PrintWriter output; // output variable
	// store data in a file
	try
	{
	    output = new PrintWriter (new FileWriter (filename + fileExtension)); //stores the data at this location

	    // writes in the file
	    output.println (fileHeader);
	    output.println (m.username);
	    output.println (m.text);
	    output.println (m.text2);
	    output.println (m.text3);
	    output.println (m.text4);
	    output.println (m.answerPlacement);
	    output.println (m.difficulty);
	    output.println (m.level);
	    output.println (m.exp);
	    output.println (m.wrong);
	    output.println (m.backgroundChoice);
	    output.close (); // closes the file
	}
	catch (IOException e)
	{
	}
    }


    /**
    Asks the user for a valid filename and stores data to the file. Added in this version.
    */
    private void saveAs ()
    {
	//checks if user has data to save
	if (m.username != null)
	{
	    tempFilename = filename; // remember filename
	    askFileName ("save as"); // asks the user for the filename to save as
	    // the user chooses to cancel
	    if (filename == null)
	    {
		filename = tempFilename;
		cancel = true;
		return; // exits the method
	    }
	    // checks if the file already exist
	    if (checkExistence (filename))
	    {
		// Asks the user to replace the existing file
		int rOption = JOptionPane.showConfirmDialog (this, "The file already exists. Do you wish to replace the existing file?", "Replace?", JOptionPane.YES_NO_OPTION);

		// User has selected the Yes option
		if (rOption == JOptionPane.YES_OPTION)
		    save (); // saves the file
		else
		    filename = tempFilename;
	    }
	    else
		save (); // saves the file
	}
	else
	    // Error Message
	    JOptionPane.showMessageDialog (this, "There is no game to save.", "Error", JOptionPane.ERROR_MESSAGE);
    }


    /**
    Saves the file. Added in this version.
    */
    private void save ()
    {
	// checks if a filename exist
	if (filename != null)
	{
	    // checks if there is a game to save
	    if (m.username != null)
	    {
		writeFile (); //writes to a file
		m.savedData = true;
	    }
	    else
		saveAs ();
	}
	else
	    saveAs (); // Asks the user for a valid filename and stores data to the file
    }


    /**
    Displays About dialog. Method changed since version 1.0. (New Feature: display our names)
    */
    private void aboutDialog ()
    {
	dialog = new JDialog (this, "About"); //constructs a dialog with a title
	dialog.setSize (350, 175); //sets the dimensions of the dialog window
	dialog.setResizable (false); //the dialog is not resizable
	dialog.getContentPane ().setLayout (new FlowLayout ()); //sets the layout of the dialog with a centered alignment
	JButton closeButton = new JButton ("Close"); //creates a push button
	closeButton.addActionListener (new ActionListener ()  //adds an action listener to the close button
	{
	    //performs an action when the close button is pushed
	    public void actionPerformed (ActionEvent e)
	    {
		dialog.dispose (); //disposes the dialog
	    }
	}
	);
	// Box in dialog
	Box logo = new Box (1);
	Box about = new Box (3);
	// add components to Box
	logo.add (new JLabel (new ImageIcon ("Images/pikaball.gif")));
	about.add (new JLabel ("Authors: Danny Dinh, Alexander Hong"));
	about.add (new JLabel ("Educational Game: Grade 12 ISP"));
	about.add (new JLabel ("(c) 2007"));
	// add box to dialog
	dialog.getContentPane ().add (logo);
	dialog.getContentPane ().add (about); //adds the about text message to the dialog
	dialog.getContentPane ().add (closeButton); //adds the close button to the dialog
	dialog.setLocationRelativeTo (this); //sets the location of the dialog relative to this object
	dialog.show (); //shows the dialog
    }


    /**
    Displays README.txt in a dialog.
    */
    private void readMeDialog ()
    {
	dialog = new JDialog (this, "Read Me"); //constructs a dialog with a title
	dialog.setSize (500, 350); //sets the dimensions of the dialog window
	dialog.setResizable (false); //the dialog is not resizable
	dialog.getContentPane ().setLayout (new FlowLayout ()); //sets the layout of the dialog with a centered alignment
	JButton closeButton = new JButton ("Close"); //creates a push button
	closeButton.addActionListener (new ActionListener ()  //adds an action listener to the close button
	{
	    //performs an action when the close button is pushed
	    public void actionPerformed (ActionEvent e)
	    {
		dialog.dispose (); //disposes the dialog
	    }
	}
	);
	// set up text area
	JTextArea display = new JTextArea ();
	display.setEditable (false);
	// set up scroll pane
	JScrollPane scrollPane = new JScrollPane (display);
	scrollPane.setPreferredSize (new Dimension (475, 275));
	// read README.txt
	try
	{
	    BufferedReader rmFile = new BufferedReader (new FileReader ("README.txt"));
	    String test = rmFile.readLine ();
	    // test for EOF
	    while (test != null)
	    {
		display.append (test + "\n");
		test = rmFile.readLine ();
	    }
	}
	catch (IOException e)
	{
	}
	// add scroll pane
	dialog.getContentPane ().add (scrollPane, BorderLayout.CENTER);
	dialog.getContentPane ().add (closeButton); //adds the close button to the dialog
	dialog.setLocationRelativeTo (this); //sets the location of the dialog relative to this object
	dialog.show (); //shows the dialog
    }
}
