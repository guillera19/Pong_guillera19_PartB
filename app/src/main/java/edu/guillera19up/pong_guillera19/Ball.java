package edu.guillera19up.pong_guillera19;

import java.util.Random;

/**
 * This class is used to create Ball objects in the AnimatorImplementation class. Each ball has
 * a random velocity and a random start position. Each ball also has a fixed radius of 50 pixels.
 *
 * @Author: Avery Guillermo
 * Created by avery Guillermo on 3/18/18.
 */
public class Ball {

    // instance variables
    private int velocity; // the speed that the ball travels
    private int xCount; // counts the number of logical x clock ticks
    private int yCount; // counts the number of logical y clock ticks
    private boolean goXBackwards; // whether x clock is ticking backwards
    private boolean goYBackwards; // whether y clock is ticking backwards
    private int radius; // the radius of the ball
    //use this to make a random number
    protected Random gen = new Random();

    /*
     * This is the constructor for the ball class. Upon creation of a ball, it is given
     * a random velocity and starting position, as required by the assignment specifications.
     * The numbers used in this method were obtained through careful calculations and inequalities
     * involving the ranges that each ball could possibly traverse.
     */
    public Ball() {
        this.velocity = gen.nextInt(11)+ 15; //make the velocity a random speed between 15 and 25
        this.xCount = gen.nextInt(60) +12; //make the starting x position a random number between 12 and 71
        this.yCount = gen.nextInt(26)+11; // make the starting y position a random number between 11 and 36
        this.goXBackwards = false;
        this. goYBackwards = false;
        this.radius = 50; //fix the radius to be a constant 50 pixels
    }

    /*
     * Shown below are all the getter and setter methods for the ball class. Each getter and
     * setter method is obvious in what they return or set and are trivial to comment.
     *
     */
    public int getVelocity() {
        return velocity;
    }

    public int getxCount() {
        return xCount;
    }

    public int getyCount() {
        return yCount;
    }

    public boolean isGoXBackwards() {
        return goXBackwards;
    }

    public boolean isGoYBackwards() {
        return goYBackwards;
    }

    public int getRadius() {
        return this.radius;
    }

    public void setxCount(int xCount) {
        this.xCount = xCount;
    }

    public void setyCount(int yCount) {
        this.yCount = yCount;
    }

    public void setGoXBackwards(boolean goXBackwards) {
        this.goXBackwards = goXBackwards;
    }

    public void setGoYBackwards(boolean goYBackwards) {
        this.goYBackwards = goYBackwards;
    }
}
