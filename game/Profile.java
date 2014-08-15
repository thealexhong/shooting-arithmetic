/*
Alexander Hong
June 13, 2007
Profile.java
This class is the user's profile.
*/

/*
Variable Dictionary
rank: the rank of the user
username: the name of the user
level: the level of the user
score: the score of the user
*/

/**
Profile.java
@author Danny Dinh, Alexander Hong
@version 3.1, June 13, 2007
*/
public class Profile
{
    // Class Variable
    String username;
    int level;
    int score;

    /**
    Creates a user profile
    */
    public Profile (String username, int level, int score)
    {
	this.username = username;
	this.level = level;
	this.score = score;
    }


    /**
    Gets the name
    @return the name
    */
    public String getName ()
    {
	return username;
    }


    /**
    Gets the score
    @return the score
    */
    public int getScore ()
    {
	return score;
    }


    /**
    Gets the level
    @return the level
    */
    public int getLevel ()
    {
	return level;
    }
}
