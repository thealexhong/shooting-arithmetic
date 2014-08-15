/*
Alexander Hong (since v.1.0; filename changed)
Danny Dinh (edited in v. 3.1)
June 12, 2007
RandomQuestionGenerator.java
This program generates a random question for each difficulty and returns the answer.
*/

/*
Variable Dictionary
generator: random generator for this class.
element: determines which operation to use.
num: the first number in the operation.
num2: the second number in the operation.
answerPlacement: the placement of the answer in the game.
answer: the answer to the question generated.
falseAns, falseAns2: bad answer.
*/

// Library
import java.util.Random;

/**
BoomMathShotApp.java
@author Danny Dinh, Alexander Hong
@version 3.1, June 12, 2007
*/
public class RandomQuestionGenerator
{
    // Class Variable
    Random generator = new Random ();
    int element, num, num2, answerPlacement;
    int answer, falseAns, falseAns2;

    /**
    Randomly chooses 1 operation to use and the answer placement.
    */
    public RandomQuestionGenerator ()
    {
	// random operation
	element = generator.nextInt (4);
	// random answer placement
	answerPlacement = generator.nextInt (3);
    }


    /**
    Generate easy question with the answer. Stage 1.
    @return   a string that contains the question.
    */
    public String generateEasy1 ()
    {
	String question = "";
	num = generator.nextInt (9) + 1; // random 1 digit number
	num2 = generator.nextInt (9) + 1; // random 1 digit number
	// find the correct operation
	switch (element)
	{
	    case 0: //add
		question = num + " + " + num2;
		answer = num + num2;
		break;
	    case 1: // subtract
		// checks if first number is less than second
		if (num < num2)
		    // makes sure that the first number is always greater than or equal to the second
		    do
		    {
			// generate
			num = generator.nextInt (9) + 1;
			num2 = generator.nextInt (9) + 1;
		    }
		    while (num < num2);
		question = num + " - " + num2;
		answer = num - num2;
		break;
	    case 2: // multiply
		question = num + " × " + num2;
		answer = num * num2;
		break;
	    case 3: // divide
		num = num * num2;
		question = num + " ÷ " + num2;
		answer = num / num2;
		break;
	}
	return question;
    }


    /**
    Generate easy question with the answer. Stage 2.
    @return   a string that contains the question.
    */
    public String generateEasy2 ()
    {
	String question = "";
	num = generator.nextInt (89) + 11; // random 2 digit number
	num2 = generator.nextInt (9) + 1; // random 1 digit number
	// find the correct operation
	switch (element)
	{
	    case 0: // add
		question = num + " + " + num2;
		answer = num + num2;
		break;
	    case 1: // subtract
		question = num + " - " + num2;
		answer = num - num2;
		break;
	    case 2: // multiply
		question = num + " × " + num2;
		answer = num * num2;
		break;
	    case 3: // divide
		num = num * num2;
		question = num + " ÷ " + num2;
		answer = num / num2;
		break;
	}
	return question;
    }


    /**
    Generateeasyquestionwiththe answer. Stage 3.
    @returnastring that contains the question.
    */
    public String generateEasy3 ()
    {
	String question = "";
	num = generator.nextInt (899) + 101; // random 3 digit number
	num2 = generator.nextInt (9) + 1; // random 1 digit number
	// find the correct operation
	switch (element)
	{
	    case 0: // add
		question = num + " + " + num2;
		answer = num + num2;
		break;
	    case 1: // subtract
		question = num + " - " + num2;
		answer = num - num2;
		break;
	    case 2: // multiply
		question = num + " × " + num2;
		answer = num * num2;
		break;
	    case 3: // divide
		num = num * num2;
		question = num + " ÷ " + num2;
		answer = num / num2;
		break;
	}
	return question;
    }


    /**
    Generate medium question with the answer. Stage 1.
    @return   a string that contains the question.
    */
    public String generateMedium1 ()
    {
	return generateEasy2 (); // same as Easy Stage 2
    }


    /**
    Generate medium question with the answer. Stage 2.
    @return   a string that contains the question.
    */
    public String generateMedium2 ()
    {
	return generateEasy3 (); // same as Easy Stage 3
    }


    /**
    Generate medium question with the answer. Stage 3.
    @return   a string that contains the question.
    */
    public String generateMedium3 ()
    {
	String question = "";
	num = generator.nextInt (89) + 11; // random 2 digit number
	num2 = generator.nextInt (89) + 11; // random 2 digit number
	// find the correct operation
	switch (element)
	{
	    case 0: // add
		question = num + " + " + num2;
		answer = num + num2;
		break;
	    case 1: // subtract
		// checks if first number is less than second
		if (num < num2)
		    // makes sure that the first number is always greater than the second
		    do
		    {
			//generate
			num = generator.nextInt (89) + 1;
			num2 = generator.nextInt (89) + 1;
		    }
		    while (num < num2);
		question = num + " - " + num2;
		answer = num - num2;
		break;
	    case 2: // multiply
		question = num + " × " + num2;
		answer = num * num2;
		break;
	    case 3: // divide
		num = num * num2;
		question = num + " ÷ " + num2;
		answer = num / num2;
		break;
	}
	return question;
    }


    /**
    Generate hard question with the answer. Stage 1.
    @return   a string that contains the question.
    */
    public String generateHard1 ()
    {
	return generateMedium3 (); // same as Medium Stage 3
    }


    /**
    Generate hard question with the answer. Stage 2.
    @return   a string that contains the question.
    */
    public String generateHard2 ()
    {
	String question = "";
	num = generator.nextInt (899) + 101; // random 3 digit number
	num2 = generator.nextInt (89) + 11; // random 2 digit number
	// find the correct operation
	switch (element)
	{
	    case 0: // add
		question = num + " + " + num2;
		answer = num + num2;
		break;
	    case 1: // subtract
		question = num + " - " + num2;
		answer = num - num2;
		break;
	    case 2: // multiply
		question = num + " × " + num2;
		answer = num * num2;
		break;
	    case 3: // divide
		num = num * num2;
		question = num + " ÷ " + num2;
		answer = num / num2;
		break;
	}
	return question;
    }


    /**
    Generate hard question with the answer. Stage 3.
    @return   a string that contains the question.
    */
    public String generateHard3 ()
    {
	String question = "";
	num = generator.nextInt (8999) + 1001; // random 4 digit number
	num2 = generator.nextInt (899) + 101; // random 3 digit number
	// find the correct operation
	switch (element)
	{
	    case 0: // add
		question = num + " + " + num2;
		answer = num + num2;
		break;
	    case 1: // subtract
		question = num + " - " + num2;
		answer = num - num2;
		break;
	    case 2: // multiply
		question = num + " × " + num2;
		answer = num * num2;
		break;
	    case 3: // divide
		num = num * num2;
		question = num + " ÷ " + num2;
		answer = num / num2;
		break;
	}
	return question;
    }


    /**
    Returns the answer to the generated question.
    @return   the answer to the generated question.
    */
    public int getAnswer ()
    {
	return answer;
    }


    /**
    Returns the incorrect answer to the generated question.
    @return   the incorrect answer to the generated question.
    */
    public int getFalseAnswer1 ()
    {
	int temp;
	// generate random integer from -10 to 10 and add it to the answer
	do
	{
	    temp = generator.nextInt (21) - 10;
	}
	while (temp == 0);
	falseAns = answer + temp;

	return falseAns;
    }


    /**
    Returns the incorrect answer to the generated question.
    @return   the incorrect answer to the generated question.
    */
    public int getFalseAnswer2 ()
    {
	int temp;
	// generate random integer from -10 to 10 and add it to the answer
	do
	{
	    temp = generator.nextInt (21) - 10;
	    falseAns2 = answer + temp;
	}
	while (falseAns2 == falseAns || temp == 0);
	return falseAns2;
    }
}


