/*
Alexander Hong (since v. 3.1)
Danny Dinh (coded options [feature: music/background chooser], parts of game)
June 10, 2007
MasterPanel.java
This class controls all the screens in the game: Boom! Math Shot
*/

/*
Variable Dictionary
d: dialog for highscores.
displayLevel, displayName, displayExp, displayWrong: Labels that displays a specific element in the game.
goMainMenu, goStartGame, goRules, goHighscore, goOptions, easyButton, mediumButton,
    hardButton, fireButton: buttons in game.
image: the location of the image to be placed as background.
nameField: the text field for username.
display: the text area for highscore.
audio1, audio2, audio3, audio4, audio5,
     background1, background2, background3, background4: radio buttons for in-game options.
powerField, angleField: text fields for angle and power.
musicChoice, difficulty, backgroundChoice: game options and difficulty (1=easy, 2=medium, 3=hard).
existGame: checks if game exist.
boolean firstShot: checks if user took his/her first shot.
badInput: checks user for bad input and used to skip an if structure in the program.
pause: checks if user paused the game.
projectile: an image that is shot out of the cannnon.
angle, power: the angle and power.
xPos: the position of the bullet on the x axis.
question, choice, choice2, choice3: the questions and answers in the game.
answerPlacement: the placement of the answer (1 of 3 pipes).
totalExp: exp needed to level up.
username: the name of the user.
exp, level, wrong: status of the user.
score: the final score (calculated when game over and win screen).
r: Random question generator.
text, text2, text3, text4: displays question and answers.
closeButton, clearButton, printButton: buttons in highscore dialog
scrollPane: scroll bar for text area
input: read from file
temp: temporary variable used to read
output: write to file
showScore: shows the user the score
savedData: checks if user has unsaved data.
newGame: true if it is a new game, otherwise false
*/

//Library
import java.awt.Graphics;
import javax.swing.ButtonGroup;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JFrame;
import javax.swing.ImageIcon;
import javax.sound.midi.Sequencer;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.InvalidMidiDataException;
import java.io.File;
import java.io.IOException;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JDialog;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.FileWriter;

/**
MasterPanel.java
@author Danny Dinh, Alexander Hong
@version 3.1, June 10, 2007
*/
public class MasterPanel extends JPanel implements ActionListener
{
    // Class Variables
    // Components
    String image;
    JDialog d;
    JLabel displayLevel, displayName, displayExp, displayWrong;
    JButton goMainMenu, goStartGame, goRules, goHighscore, goOptions,
	easyButton, mediumButton, hardButton, fireButton;
    JTextField nameField;
    JTextArea display;
    JRadioButton audio1, audio2, audio3, audio4, audio5,
	background1, background2, background3, background4;
    // Game variables
    JTextField powerField = new JTextField ();
    JTextField angleField = new JTextField ();
    int musicChoice = 1, difficulty = 1, backgroundChoice = 1;
    boolean existGame = false;
    boolean firstShot = false;
    boolean badInput = false;
    boolean savedData = true;
    boolean pause = false;
    boolean newGame = true;
    Image projectile = new ImageIcon ("Images/Projectile.jpg").getImage ();
    int angle, power;
    double xPos;
    JLabel question = new JLabel ();
    JLabel choice = new JLabel ();
    JLabel choice2 = new JLabel ();
    JLabel choice3 = new JLabel ();
    int answerPlacement;
    int totalExp;
    String username;
    int exp = 0, level = 1, wrong = 0;
    int score;
    RandomQuestionGenerator r;
    String text, text2, text3, text4;
    JLabel showScore;


    /**
    Default constructor
    */
    public MasterPanel ()
    {
	// reset game stats
	removeAll ();
	r = new RandomQuestionGenerator ();
	username = null;
	savedData = true;
	exp = 0;
	level = 1;
	wrong = 0;
	existGame = false;
	firstShot = false;
	image = "Images/SplashScreen.jpg";
	setLayout (null); // absolute layout
	splash (); // start the game with a splash screen
	totalExp = 50 * level;
    }


    /**
    Paints the panel's background image. Overrided.
    @param g  the graphic object
    */
    public void paintComponent (Graphics g)
    {
	super.paintComponent (g);
	// background image
	Image background = new ImageIcon (image).getImage ();
	g.drawImage (background, 0, 0, this); // draw bg
    }


    /**
    The game's splash screen
    */
    private void splash ()
    {
	// button
	goMainMenu = new JButton ("Main Menu");
	// set coord for button
	goMainMenu.setBounds (300, 450, 200, 50);
	// add it
	add (goMainMenu);
	// add actionlistener to it
	goMainMenu.addActionListener (this);
    }


    /**
    The game's main menu
    */
    private void mainMenu ()
    {
	if (existGame)
	    pause = true;
	// delete everything in panel
	removeAll ();
	// background
	image = "Images/MainMenu.jpg";
	//buttons
	goStartGame = new JButton ("Start Game");
	goRules = new JButton ("Rules");
	goHighscore = new JButton ("High Scores");
	goOptions = new JButton ("Options");
	// add button
	add (goStartGame);
	add (goRules);
	add (goHighscore);
	add (goOptions);
	// set coord for button
	goStartGame.setBounds (300, 150, 200, 50);
	goRules.setBounds (300, 250, 200, 50);
	goHighscore.setBounds (300, 350, 200, 50);
	goOptions.setBounds (300, 450, 200, 50);
	// add actionlistener to button
	goStartGame.addActionListener (this);
	goRules.addActionListener (this);
	goHighscore.addActionListener (this);
	goOptions.addActionListener (this);
    }


    /**
      The game's start game screen
      */
    public void startGame ()
    {
	// delete everything in panel
	removeAll ();
	// checks if user has an unfinished game
	if (existGame == false)
	{
	    // background
	    image = "Images/StartGame.jpg";
	    //buttons
	    easyButton = new JButton ("Piece of Cake");
	    mediumButton = new JButton ("I Love to Think");
	    hardButton = new JButton ("Brain Shot!");
	    goMainMenu = new JButton ("Main Menu");
	    // add buttons
	    add (easyButton);
	    add (mediumButton);
	    add (hardButton);
	    add (goMainMenu);
	    // add text field
	    nameField = new JTextField ();
	    add (nameField);
	    // set coord and size for components
	    nameField.setBounds (200, 170, 400, 30);
	    easyButton.setBounds (100, 400, 150, 50);
	    mediumButton.setBounds (325, 400, 150, 50);
	    hardButton.setBounds (550, 400, 150, 50);
	    goMainMenu.setBounds (300, 500, 200, 50);
	    // add actionlistener to the buttons
	    easyButton.addActionListener (this);
	    mediumButton.addActionListener (this);
	    hardButton.addActionListener (this);
	    goMainMenu.addActionListener (this);
	}
	else
	{
	    // continue current existing game
	    if (difficulty == 1)
		easy ();
	    else if (difficulty == 2)
		medium ();
	    else
		hard ();
	}
    }


    /**
      The game's rule screen
      */
    private void rules ()
    {
	// delete everything in panel
	removeAll ();
	//background
	image = "Images/rules.jpg";
	//button
	goMainMenu = new JButton ("Main Menu");
	// set coord and size for button
	goMainMenu.setBounds (300, 500, 200, 50);
	// add to panel
	add (goMainMenu);
	// add actionlistener to button
	goMainMenu.addActionListener (this);
    }


    /**
      The game's high score screen
      */
    private void highscore ()
    {
	d = new JDialog (new JFrame (), "High Score"); //constructs a dialog with a title
	d.setSize (600, 500); //sets the dimensions of the dialog window
	d.setResizable (false); //the dialog is not resizable
	d.getContentPane ().setLayout (new FlowLayout ()); //sets the layout of the dialog with a centered alignment
	JButton closeButton = new JButton ("Close"); //creates a push button
	JButton clearButton = new JButton ("Clear High Scores"); //creates a push button
	JButton printButton = new JButton ("Print High Scores"); //creates a push button
	closeButton.addActionListener (new ActionListener ()  //adds an action listener to the close button
	{
	    //performs an action when the close button is pushed
	    public void actionPerformed (ActionEvent e)
	    {
		d.dispose (); //disposes the dialog
		mainMenu ();
	    }
	}
	);
	clearButton.addActionListener (new ActionListener ()  //adds an action listener to the close button
	{
	    //performs an action when the close button is pushed
	    public void actionPerformed (ActionEvent e)
	    {
		createHighFile ();
		d.dispose ();
		highscore ();

	    }
	}
	);
	printButton.addActionListener (new ActionListener ()  //adds an action listener to the print button
	{
	    //performs an action when the print button is pushed
	    public void actionPerformed (ActionEvent e)
	    {
		// print
		PrintUtilities.printComponent (display);
	    }
	}
	);
	// set up text area
	display = new JTextArea ();
	display.setEditable (false);
	// set up scroll pane
	JScrollPane scrollPane = new JScrollPane (display);
	scrollPane.setPreferredSize (new Dimension (575, 400));
	if (!checkExistence ("HIGHSCORE.txt"))
	    createHighFile ();
	// read HIGHSCORE.txt
	try
	{
	    BufferedReader hFile = new BufferedReader (new FileReader ("HIGHSCORE.txt"));
	    String test = hFile.readLine ();
	    // test for EOF
	    while (test != null)
	    {
		display.append (test + "\n");
		test = hFile.readLine ();
	    }
	}
	catch (IOException e)
	{
	}
	// add scroll pane
	d.getContentPane ().add (scrollPane, BorderLayout.CENTER);
	d.getContentPane ().add (clearButton);  //adds the clear button to the dialog
	d.getContentPane ().add (printButton);  //adds the clear button to the dialog
	d.getContentPane ().add (closeButton);  //adds the close button to the dialog
	d.setLocationRelativeTo (this);  //sets the location of the dialog relative to this object
	d.show ();  //shows the dialog
    }


    /**
       Checks if the file already exist. Added in this version.
       @return true, if the file exist, otherwise false
       @param filename name of file
       */
    private boolean checkExistence (String filename)
    {
	String temp;
	BufferedReader input;
	// Reads data in a file
	try
	{
	    input = new BufferedReader (new FileReader (filename)); //reads the data at this location
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
       Creates Highscore file
       */
    private void createHighFile ()
    {
	PrintWriter output; // output variable
	// store data in a file
	try
	{
	    output = new PrintWriter (new FileWriter ("HIGHSCORE.txt")); //stores the data at this location
	    // writes in the file
	    output.println ("       ===   HIGH SCORES   ===");
	    output.println ("Rank / Username / Level / Score");
	    output.close (); // closes the file
	}
	catch (IOException e)
	{
	}
    }


    /**
       The game's options screen.     New Feature: music and background chooser, v.3.1 June 10, 2007
       @param musicChoice   the choice of music from 1-5
       @backgroundChoice    the choice of background from 1-4
       */
    private void options (int musicChoice, int backgroundChoice)
    {
	// clear panel
	removeAll ();
	//background
	image = "Images/options.jpg";
	//button
	goMainMenu = new JButton ("Main Menu");
	// set main menu button
	goMainMenu.setBounds (300, 500, 200, 50);
	// add it
	add (goMainMenu);
	// add actionlistener to main menu
	goMainMenu.addActionListener (this);

	// Radio buttons ******

	// user chose main theme
	if (musicChoice == 1)
	    audio1 = new JRadioButton ("Main Theme On", true);
	else
	    audio1 = new JRadioButton ("Main Theme Off", false);
	audio1.setBounds (370, 120, 120, 20); //set
	// user chose theme 2
	if (musicChoice == 2)
	    audio2 = new JRadioButton ("Theme 2 On", true);
	else
	    audio2 = new JRadioButton ("Theme 2 Off", false);
	audio2.setBounds (370, 150, 100, 20); //set

	// user chose theme 3
	if (musicChoice == 3)
	    audio3 = new JRadioButton ("Theme 3 On", true);
	else
	    audio3 = new JRadioButton ("Theme 3 Off", false);
	audio3.setBounds (370, 180, 100, 20); //set

	// user chose theme 4
	if (musicChoice == 4)
	    audio4 = new JRadioButton ("Theme 4 On", true);
	else
	    audio4 = new JRadioButton ("Theme 4 Off", false);
	audio4.setBounds (370, 210, 100, 20); //set

	// user chose theme 5
	if (musicChoice == 5)
	    audio5 = new JRadioButton ("Theme 5 On", true);
	else
	    audio5 = new JRadioButton ("Theme 5 Off", false);
	audio5.setBounds (370, 240, 100, 20); //set

	// user chose default background (includes different background for each difficulty)
	if (backgroundChoice == 1)
	    background1 = new JRadioButton ("Default Background On", true);
	else
	    background1 = new JRadioButton ("Default Background Off", false);
	background1.setBounds (370, 330, 160, 20); //set

	// user chose toxic
	if (backgroundChoice == 2)
	    background2 = new JRadioButton ("Toxic Background On", true);
	else
	    background2 = new JRadioButton ("Toxic Background Off", false);
	background2.setBounds (370, 360, 160, 20); //set

	// user chose wave
	if (backgroundChoice == 3)
	    background3 = new JRadioButton ("Wave Background On", true);
	else
	    background3 = new JRadioButton ("Wave Background Off", false);
	background3.setBounds (370, 390, 160, 20); //set

	// user chose spirit
	if (backgroundChoice == 4)
	    background4 = new JRadioButton ("Spirit Background On", true);
	else
	    background4 = new JRadioButton ("Spirit Background Off", false);
	background4.setBounds (370, 420, 160, 20); //set
	// add radio buttons
	add (audio1);
	add (audio2);
	add (audio3);
	add (audio4);
	add (audio5);
	add (background1);
	add (background2);
	add (background3);
	add (background4);

	// add actionlistener to radio button
	background1.addActionListener (this);
	background2.addActionListener (this);
	background3.addActionListener (this);
	background4.addActionListener (this);
	audio1.addActionListener (this);
	audio2.addActionListener (this);
	audio3.addActionListener (this);
	audio4.addActionListener (this);
	audio5.addActionListener (this);
    }


    /**
    Display hints in game
    */
    private void displayHints (String hint)
    {
	// Hint label
	JLabel displayHint = new JLabel (hint);
	displayHint.setFont (new Font ("Tohoma", Font.PLAIN, 20)); // change font
	add (displayHint); // add
	displayHint.setBounds (100, 50, 200, 50); // set up the label
    }


    /**
      The game's easy mode
      */
    private void easy ()
    {
	// set up screen
	setUp ();
	// show hints
	displayHints ("Use Fingers");
	// if user pause, do not change question
	if (!pause)
	{
	    // random question generator
	    r = new RandomQuestionGenerator ();
	    // generate question
	    if (level < 4)
		text = "" + r.generateEasy1 ();
	    else if (level < 8)
		text = "" + r.generateEasy2 ();
	    else
		text = "" + r.generateEasy3 ();
	    // generate answers
	    text2 = "" + r.getAnswer ();
	    text3 = "" + r.getFalseAnswer1 ();
	    text4 = "" + r.getFalseAnswer2 ();
	}
	// generate specific questions
	if (level < 4)
	    question.setText (text);
	else if (level < 8)
	    question.setText (text);
	else
	    question.setText (text);
	question.setFont (new Font ("Comic Sans MS", Font.PLAIN, 36)); // change font
	add (question); // add
	question.setBounds (375, 35, 500, 50); // set up
	// set choices
	choice.setText (text2);
	choice2.setText (text3);
	choice3.setText (text4);
	choice.setFont (new Font ("Comic Sans MS", Font.PLAIN, 20)); // change font
	choice2.setFont (new Font ("Comic Sans MS", Font.PLAIN, 20)); // change font
	choice3.setFont (new Font ("Comic Sans MS", Font.PLAIN, 20)); // change font
	add (choice); // add
	add (choice2); // add
	add (choice3); // add
	// places answer in one of 3 pipes randomly
	answerPlacement = r.answerPlacement;
	if (answerPlacement == 0)
	{
	    choice.setBounds (325, 375, 200, 50); // set up the label
	    choice2.setBounds (500, 375, 200, 50); // set up the label
	    choice3.setBounds (675, 375, 200, 50); // set up the label
	}
	else if (answerPlacement == 1)
	{
	    choice.setBounds (500, 375, 200, 50); // set up the label
	    choice2.setBounds (325, 375, 200, 50); // set up the label
	    choice3.setBounds (675, 375, 200, 50); // set up the label
	}
	else
	{
	    choice.setBounds (675, 375, 200, 50); // set up the label
	    choice2.setBounds (500, 375, 200, 50); // set up the label
	    choice3.setBounds (325, 375, 200, 50); // set up the label
	}
	pause = false;
    }


    /**
      The game's medium mode
      */
    private void medium ()
    {
	// set up screen
	setUp ();
	// display hint
	displayHints ("Pencil & Paper");
	// if user pause, do not change question
	if (!pause)
	{
	    // random question generator
	    r = new RandomQuestionGenerator ();
	    // generate question
	    if (level < 4)
		text = "" + r.generateMedium1 ();
	    else if (level < 8)
		text = "" + r.generateMedium2 ();
	    else
		text = "" + r.generateMedium3 ();
	    // generate answers
	    text2 = "" + r.getAnswer ();
	    text3 = "" + r.getFalseAnswer1 ();
	    text4 = "" + r.getFalseAnswer2 ();
	}
	// generate specific questions
	if (level < 4)
	    question.setText (text);
	else if (level < 8)
	    question.setText (text);
	else
	    question.setText (text);
	question.setFont (new Font ("Comic Sans MS", Font.PLAIN, 36)); // change font
	add (question); // add
	question.setBounds (375, 35, 500, 50); // set up
	// set choices
	choice.setText (text2);
	choice2.setText (text3);
	choice3.setText (text4);
	choice.setFont (new Font ("Comic Sans MS", Font.PLAIN, 20)); // change font
	choice2.setFont (new Font ("Comic Sans MS", Font.PLAIN, 20)); // change font
	choice3.setFont (new Font ("Comic Sans MS", Font.PLAIN, 20)); // change font
	add (choice); // add
	add (choice2); // add
	add (choice3); // add
	// places answer in one of 3 pipes randomly
	answerPlacement = r.answerPlacement;
	if (answerPlacement == 0)
	{
	    choice.setBounds (325, 375, 200, 50); // set up the label
	    choice2.setBounds (500, 375, 200, 50); // set up the label
	    choice3.setBounds (675, 375, 200, 50); // set up the label
	}
	else if (answerPlacement == 1)
	{
	    choice.setBounds (500, 375, 200, 50); // set up the label
	    choice2.setBounds (325, 375, 200, 50); // set up the label
	    choice3.setBounds (675, 375, 200, 50); // set up the label
	}
	else
	{
	    choice.setBounds (675, 375, 200, 50); // set up the label
	    choice2.setBounds (500, 375, 200, 50); // set up the label
	    choice3.setBounds (325, 375, 200, 50); // set up the label
	}
	pause = false;
    }


    /**
      The game's hard mode
      */
    private void hard ()
    {
	// set up screen
	setUp ();
	// display hint
	displayHints ("Calculator!!!");
	// if user pause, do not change question
	if (!pause)
	{
	    // random question generator
	    r = new RandomQuestionGenerator ();
	    // generate question
	    if (level < 4)
		text = "" + r.generateHard3 ();
	    else if (level < 8)
		text = "" + r.generateHard2 ();
	    else
		text = "" + r.generateHard3 ();
	    // generate answers
	    text2 = "" + r.getAnswer ();
	    text3 = "" + r.getFalseAnswer1 ();
	    text4 = "" + r.getFalseAnswer2 ();
	}
	if (level < 4)
	    question.setText (text);
	else if (level < 8)
	    question.setText (text);
	else
	    question.setText (text);
	question.setFont (new Font ("Comic Sans MS", Font.PLAIN, 36)); // change font
	add (question); // add
	question.setBounds (375, 35, 500, 50); // set up
	// set choices
	choice.setText (text2);
	choice2.setText (text3);
	choice3.setText (text4);
	choice.setFont (new Font ("Comic Sans MS", Font.PLAIN, 20)); // change font
	choice2.setFont (new Font ("Comic Sans MS", Font.PLAIN, 20)); // change font
	choice3.setFont (new Font ("Comic Sans MS", Font.PLAIN, 20)); // change font
	add (choice); // add
	add (choice2); // add
	add (choice3); // add
	// places answer in one of 3 pipes randomly
	answerPlacement = r.answerPlacement;
	if (answerPlacement == 0)
	{
	    choice.setBounds (325, 375, 200, 50); // set up the label
	    choice2.setBounds (500, 375, 200, 50); // set up the label
	    choice3.setBounds (675, 375, 200, 50); // set up the label
	}
	else if (answerPlacement == 1)
	{
	    choice.setBounds (500, 375, 200, 50); // set up the label
	    choice2.setBounds (325, 375, 200, 50); // set up the label
	    choice3.setBounds (675, 375, 200, 50); // set up the label
	}
	else
	{
	    choice.setBounds (675, 375, 200, 50); // set up the label
	    choice2.setBounds (500, 375, 200, 50); // set up the label
	    choice3.setBounds (325, 375, 200, 50); // set up the label
	}
	pause = false;
    }


    /**
    Sets up the screen to play the game.
    */
    private void setUp ()
    {
	// clear panel
	removeAll ();
	// this game exist
	existGame = true;
	// display background
	if (backgroundChoice == 1)
	    // difficulty mode background
	    if (difficulty == 1)
		image = "Images/GamePlay1Easy.jpg";
	    else if (difficulty == 2)
		image = "Images/GamePlay1Medium.jpg";
	    else
		image = "Images/GamePlay1Hard.jpg";
	// unique permanent background
	else if (backgroundChoice == 2)
	    image = "Images/GamePlay2.jpg";
	else if (backgroundChoice == 3)
	    image = "Images/GamePlay3.jpg";
	else
	    image = "Images/GamePlay4.jpg";

	// JLabels
	// removes the labels and updates them after the first shot
	if (firstShot)
	{
	    remove (displayName);
	    remove (displayLevel);
	    remove (displayExp);
	    remove (displayWrong);
	}
	else
	    if (newGame == true)
	    {
		savedData = false;
		newGame = false;
	    }
	// username
	displayName = new JLabel (username);
	displayName.setFont (new Font ("Tohoma", Font.PLAIN, 30)); //set font
	add (displayName); // add to panel
	displayName.setBounds (25, 510, 200, 50); // set coord and size
	// level
	displayLevel = new JLabel (new Integer (level).toString ());
	displayLevel.setFont (new Font ("Tohoma", Font.PLAIN, 28)); //set font
	add (displayLevel); // add to panel
	displayLevel.setBounds (100, 548, 200, 50); // set coord and size
	// exp points
	displayExp = new JLabel (new Integer (exp) + " / " + new Integer (totalExp));
	displayExp.setFont (new Font ("Tohoma", Font.PLAIN, 30)); //set font
	add (displayExp); // add to panel
	displayExp.setBounds (450, 520, 200, 50); // set coord and size
	//wrong
	displayWrong = new JLabel ("Wrong: " + new Integer (wrong));
	displayWrong.setFont (new Font ("Tohoma", Font.PLAIN, 20)); //set font
	add (displayWrong); // add to panel
	displayWrong.setBounds (650, 495, 200, 50); // set coord and size
	// angle label
	JLabel angleLabel = new JLabel ("Angle:");
	add (angleLabel);
	angleLabel.setBounds (25, 440, 50, 20);
	// power label
	JLabel powerLabel = new JLabel ("Power:");
	add (powerLabel);
	powerLabel.setBounds (25, 470, 50, 20);

	// Main Menu button
	goMainMenu = new JButton ("Main Menu");
	goMainMenu.setBounds (650, 540, 100, 40); // set coord and size
	add (goMainMenu); // add to panel
	goMainMenu.addActionListener (this); // add actionlistener

	// Fire button
	fireButton = new JButton ("Fire!");
	fireButton.setBounds (170, 435, 120, 50); // set coord and size
	add (fireButton); // add to panel
	fireButton.addActionListener (this); // add actionlistener

	// Text Fields
	// power
	add (powerField);
	powerField.setBounds (75, 470, 50, 20);
	// angle
	add (angleField);
	angleField.setBounds (75, 440, 50, 20);
    }


    /**
    Checks the username. Username must contain at least 1 character
    and cannot be more than 10 characters long.
    @return true   username is valid
	    false  username is invalid
    */
    private boolean checkUserName ()
    {
	// trims the username and stores it
	username = nameField.getText ().trim ();
	// checks if the username is valid
	if (username.equals ("") || username.length () > 10)
	{
	    // error
	    JOptionPane.showMessageDialog (null,
		    "The username is invalid. It must contain at least 1 character and cannot be more than 10 characters long.",
		    "Error - Invalid User Name !", JOptionPane.ERROR_MESSAGE);
	    nameField.setText (""); // sets to nothing
	    username = null;
	    return false; //invalid
	}
	return true; //valid
    }


    /**
    Fires the bullet and shows the trajectory. Errortraps power and angle.
    */
    private void fire ()
    {
	badInput = false;
	// errortraps angle
	try
	{
	    angle = Integer.parseInt (angleField.getText ().trim ());
	    // checks if angle is valid
	    if (angle < 0 || angle > 90)
	    {
		// error
		JOptionPane.showMessageDialog (null,
			"The angle is invalid. Angle must be from 0 to 90 degrees.",
			"Error - Invalid Angle !", JOptionPane.ERROR_MESSAGE);
		angleField.setText (""); //clear text field
		badInput = true;
		return;
	    }
	}
	catch (NumberFormatException e)
	{
	    // error
	    JOptionPane.showMessageDialog (null,
		    "The angle is invalid. Angle must be from 0 to 90 degrees.",
		    "Error - Invalid Angle !", JOptionPane.ERROR_MESSAGE);
	    angleField.setText (""); //clear text field
	    badInput = true;
	    return;
	}
	// errortraps power
	try
	{
	    power = Integer.parseInt (powerField.getText ().trim ());
	    // checks if power is valid
	    if (power < 0 || power > 100)
	    {
		// error
		JOptionPane.showMessageDialog (null,
			"The power is invalid. Power must be from 0 to 100.",
			"Error - Invalid Power !", JOptionPane.ERROR_MESSAGE);
		powerField.setText (""); //clear text field
		badInput = true;
		return;
	    }
	}
	catch (NumberFormatException e)
	{
	    // error
	    JOptionPane.showMessageDialog (null,
		    "The power is invalid. Power must be from 0 to 100.",
		    "Error - Invalid Power !", JOptionPane.ERROR_MESSAGE);
	    powerField.setText (""); //clear text field
	    badInput = true;
	    return;
	}
	// user took first shot
	firstShot = true;
	// Projectile
	double time = 0;
	Projectile p = new Projectile (power, angle); // Projectile object
	Graphics g = getGraphics (); // gets this panel's graphics

	// draw the bullet during each 0.001 second in the projectile
	while (true)
	{
	    xPos = p.getXPos (time); //get horizontal position
	    double yPos = p.getYPos (time); //get vertical position
	    // stops projectile when bullet lands
	    if (yPos <= -35 && time != 0)
		break;
	    // draw bullet
	    g.drawImage (projectile, 90 + (int) xPos, 390 - (int) yPos, this);
	    // increment time
	    time += 0.001;
	}
	// delays the projectile for 500 milliseconds
	try
	{
	    Thread.sleep (500);
	}
	catch (InterruptedException e)
	{
	}
	savedData = false;
	calculateStatus (); // calculate points
    }


    /**
    Change the user's status.
    */
    private void calculateStatus ()
    {
	// User hit target
	if (answerPlacement == 0 && xPos >= 230 && xPos <= 330 ||
		answerPlacement == 1 && xPos >= 410 && xPos <= 510 ||
		answerPlacement == 2 && xPos >= 585 && xPos <= 685)
	{
	    // add 10 exp
	    exp += 10;
	    // checks if exp match the level requirements
	    if (exp >= totalExp)
	    {
		// LEVEL UP!
		exp = 0;
		level++;
		totalExp = level * 50;
		// user wins after level 10
		if (level == 11)
		{
		    win (); // win screen
		    badInput = true;
		    return;
		}
	    }
	}
	else
	{
	    // subtract 5 exp
	    exp -= 5;
	    // EXP cant go lower than 0
	    if (exp < 0)
		exp = 0;
	    wrong++;
	    // checks if game over
	    if (wrong == 5)
	    {
		gameOver (); // lose screen
		badInput = true;
		return;
	    }
	}
    }


    /**
    User wins! The game's win screen.
    */
    private void win ()
    {
	// delete everything in panel
	removeAll ();
	//background
	image = "Images/Congratulations.jpg";
	// calculate score
	score = (level - 1) * 50 + exp * 100 - 150 * wrong;
	HighScore h = new HighScore ();
	h.readScores ();
	h.add (new Profile (username, level, score));
	h.writeScore ();
	// add score label
	showScore = new JLabel ("Score: " + score);
	add (showScore);
	showScore.setFont (new Font ("Tohoma", Font.PLAIN, 40)); // change font
	showScore.setBounds (300, 350, 500, 100);
	//reset
	newGame = true;
	username = null;
	savedData = true;
	exp = 0;
	level = 1;
	wrong = 0;
	totalExp = 50;
	existGame = false;
	firstShot = false;
	powerField.setText ("");
	angleField.setText ("");
	//button
	goMainMenu = new JButton ("Main Menu");
	// set coord and size for button
	goMainMenu.setBounds (300, 500, 200, 50);
	// add to panel
	add (goMainMenu);
	// add actionlistener to button
	goMainMenu.addActionListener (this);
    }


    /**
    User losses! Game Over screen.
    */
    private void gameOver ()
    {
	// delete everything in panel
	removeAll ();
	//background
	image = "Images/GameOver.jpg";
	// calculate score
	score = (level - 1) * 50 + exp * 100 - 150 * wrong;
	HighScore h = new HighScore ();
	h.readScores ();
	h.add (new Profile (username, level, score));
	h.writeScore ();
	// score label
	showScore = new JLabel ("Score: " + score);
	add (showScore);
	showScore.setFont (new Font ("Tohoma", Font.PLAIN, 40)); // change font
	showScore.setBounds (300, 100, 500, 100);
	//reset
	newGame = true;
	username = null;
	exp = 0;
	level = 1;
	wrong = 0;
	totalExp = 50;
	savedData = true;
	existGame = false;
	firstShot = false;
	powerField.setText ("");
	angleField.setText ("");
	//button
	goMainMenu = new JButton ("Main Menu");
	// set coord and size for button
	goMainMenu.setBounds (300, 500, 200, 50);
	// add to panel
	add (goMainMenu);
	// add actionlistener to button
	goMainMenu.addActionListener (this);
    }


    /**
    Performs an action according to the user's activity
    */
    public void actionPerformed (ActionEvent ae)
    {
	// User selected Main Menu
	if (ae.getActionCommand ().equals ("Main Menu"))
	    mainMenu ();
	// User selected Start Game
	if (ae.getActionCommand ().equals ("Start Game"))
	    startGame ();
	// User selected Rules
	if (ae.getActionCommand ().equals ("Rules"))
	    rules ();
	// User selected High scores
	if (ae.getActionCommand ().equals ("High Scores"))
	    highscore ();
	// User selected Options
	if (ae.getActionCommand ().equals ("Options"))
	    options (musicChoice, backgroundChoice);
	// User changed background music in Options
	if (ae.getActionCommand ().equals ("Main Theme Off") || ae.getActionCommand ().equals ("Theme 2 Off") ||
		ae.getActionCommand ().equals ("Theme 3 Off") || ae.getActionCommand ().equals ("Theme 4 Off") ||
		ae.getActionCommand ().equals ("Theme 5 Off"))
	{
	    // change music
	    try
	    {
		// stop the current music running
		if (GameFrame.sequencer.isRunning ())
		    GameFrame.sequencer.stop ();
		// turns on music
		if (ae.getActionCommand ().equals ("Main Theme Off"))
		{
		    musicChoice = 1;
		    GameFrame.sequencer.setSequence (MidiSystem.getSequence (new File ("Audio/BoomMathShotTheme1.mid")));  //reads the musical information from a file
		}
		// turns on music
		else if (ae.getActionCommand ().equals ("Theme 2 Off"))
		{
		    musicChoice = 2;
		    GameFrame.sequencer.setSequence (MidiSystem.getSequence (new File ("Audio/BoomMathShotTheme2.mid")));  //reads the musical information from a file
		}
		// turns on music
		else if (ae.getActionCommand ().equals ("Theme 3 Off"))
		{
		    musicChoice = 3;
		    GameFrame.sequencer.setSequence (MidiSystem.getSequence (new File ("Audio/BoomMathShotTheme3.mid")));  //reads the musical information from a file
		}
		// turns on music
		else if (ae.getActionCommand ().equals ("Theme 4 Off"))
		{
		    musicChoice = 4;
		    GameFrame.sequencer.setSequence (MidiSystem.getSequence (new File ("Audio/BoomMathShotTheme4.mid")));  //reads the musical information from a file
		}
		// turns on music
		else
		{
		    musicChoice = 5;
		    GameFrame.sequencer.setSequence (MidiSystem.getSequence (new File ("Audio/BoomMathShotTheme5.mid")));  //reads the musical information from a file
		}
		// start the music
		GameFrame.sequencer.start ();  //starts playing
	    }
	    catch (IOException e)
	    {
	    }
	    catch (InvalidMidiDataException e)
	    {
	    }
	    // update panel
	    options (musicChoice, backgroundChoice);
	}
	// User changes background in game
	if (ae.getActionCommand ().equals ("Default Background Off") ||
		ae.getActionCommand ().equals ("Toxic Background Off") ||
		ae.getActionCommand ().equals ("Wave Background Off") ||
		ae.getActionCommand ().equals ("Spirit Background Off"))
	{
	    // turn on bg
	    if (ae.getActionCommand ().equals ("Default Background Off"))
		backgroundChoice = 1;
	    // turn on bg
	    else if (ae.getActionCommand ().equals ("Toxic Background Off"))
		backgroundChoice = 2;
	    // turn on bg
	    else if (ae.getActionCommand ().equals ("Wave Background Off"))
		backgroundChoice = 3;
	    // turn on bg
	    else
		backgroundChoice = 4;
	    //update panel
	    options (musicChoice, backgroundChoice);
	}
	// User selected difficulty
	if (ae.getActionCommand ().equals ("Piece of Cake") || ae.getActionCommand ().equals ("I Love to Think")
		|| ae.getActionCommand ().equals ("Brain Shot!"))
	{
	    // checks if username is valid
	    if (checkUserName () == false)
	    {
		startGame ();
		return;
	    }
	    // User selected easy mode
	    if (ae.getActionCommand ().equals ("Piece of Cake"))
	    {
		difficulty = 1;
		// set up the screen to play
		easy ();
	    }
	    // User selected medium mode
	    if (ae.getActionCommand ().equals ("I Love to Think"))
	    {
		difficulty = 2;
		// set up the screen to play
		medium ();
	    }
	    // User selected hard mode
	    if (ae.getActionCommand ().equals ("Brain Shot!"))
	    {
		difficulty = 3;
		// set up the screen to play
		hard ();
	    }
	}
	// user selected fire
	if (ae.getActionCommand ().equals ("Fire!"))
	{
	    fire (); // FIRE!
	    // bad input, do not change screen
	    if (!badInput)
		startGame ();
	}
	updateUI (); // update this panel
    }
}


