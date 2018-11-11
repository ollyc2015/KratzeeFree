package uk.co.oliverbcurtis.Kratzee.model;

public class Score {

    private static int score = 0;

    //So that this class can't be instantiated
    private Score () {
    }

    //Return current score
    public static int getScore() {
        return score;
    }

    //Increment score by 4
    public static void increaseScoreByFour(){
        score = score + 4;
    }

    //Increment score by 1
    public static void increaseScoreByOne() {
        score = score+1;
    }

    //Increment score by 2
    public static void increaseScoreByTwo() {
        score = score+2;
    }

    //Decrement score by 1
    public static void decreaseScoreByOne() {
        score = score-1;
    }

    //Decrement score by 2
    public static void decreaseScoreByTwo(){
        score = score -2;
    }

    public static void resetScore() {
        score = 0;
    }

    //No change to current score
    public static void noChangeToScore(){
        score = score;
    }
}
