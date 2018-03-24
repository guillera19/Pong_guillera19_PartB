package edu.guillera19up.pong_guillera19;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import java.util.ArrayList;

/**
 * This class implements the Animator Interface and the OnClickListener. It contains the
 * majority of the code for this Pong game. The coordinates used in this class were obtained
 * through guess and check and calculation methods by inputting numbers, running the program,
 * and then adjusting the coordinates as needed.
 *
 * @Author: Avery Guillermo
 * Created by Avery Guillermo on 3/17/18.
 * Updated for Part B by Avery Guillermo on 3/23/18
 *
 */
public class AnimatorImplementation implements Animator, View.OnClickListener {

    //instance variables
    ArrayList<Ball> balls; //array list to hold the balls
    private int paddleSize; // 0 means beginner, 1 means intermediate, 2 means expert
    private int topOfLeftPaddleCoord; // the top coordinate of the human Paddle
    private int bottomOfLeftPaddleCoord; // the bottom coordinate of the human Paddle
    private int yPosLeftPaddleClick; //the y position where the human finger is touching the screen
    private int yPosComputer; //the y position where the middle of the computer paddle is
    private int topOfRightPaddleCoord; //top of the computer paddle coordinate
    private int bottomOfRightPaddleCoord; //bottom of the computer paddle coordinate
    private int computerPaddleSpeed; //how fast the A.I. paddle should move
    private int humanScore; //human's score
    private int computerScore; //computer's score

    /**
     * This is the constructor for this class. It initializes the balls ArrayList and
     * adds a ball into the ArrayList, since one of the assignment specifications is that when the
     * app starts, a ball should shoot out in a random direction and random speed
     *
     */
    public AnimatorImplementation(){
        balls = new ArrayList<>();
        balls.add(new Ball());
        this.yPosLeftPaddleClick = 530; //initialze the paddles so that they start in the middle of the screen
        this.yPosComputer = 530;
        this.computerPaddleSpeed = 4; //set the computer paddle speed to 4 for beginners
        this.humanScore = 0;
        this.computerScore = 0;
    }


    /**
     * Interval between animation frames: .05 seconds (i.e., about 50 times
     * per second).
     *
     * @return the time interval between frames, in milliseconds.
     */
    @Override
    public int interval() {
        return 20;
    }


    /**
     * The background color: black to match the hot pink walls.
     *
     * @return the background color onto which we will draw the image.
     */
    @Override
    public int backgroundColor() {
        return Color.rgb(0, 0, 0);
    }


    /**
     * This method is called once every clock tick to draw the next animation-frame. This updates
     * the animation's data to reflect the passage of time. In this method, the three walls are
     * drawn, the paddle is drawn depending on the Paddle Size difficulty, and the balls are
     * repeatedly drawn.
     *
     * @param g the graphics object on which to draw
     */
    @Override
    public void tick(Canvas g) {

        //draw the walls
        Paint pinkPaint = new Paint();
        pinkPaint.setColor(Color.rgb(255,105,180));
        //draw the first top wall
        g.drawRect(160f, 40f, 1848f, 100f, pinkPaint);
        //draw the second bottom wall
        g.drawRect(160f, 961f, 1848f, 1021f, pinkPaint);

        //draw the human's score and the computer's score
        Paint whitePaint = new Paint();
        whitePaint.setColor(Color.rgb(255,255,255));
        whitePaint.setTextSize(60);
        g.drawText("Computer Score : " + this.computerScore, 1300f, 90f, whitePaint);
        g.drawText("Your Score : " + this.humanScore, 167f, 90f, whitePaint);

        //draw the paddle depending on the difficulty that the user selects
        if(paddleSize == 0) {
            drawBeginnerPaddle(g);
            drawCompBeginnerPaddle(g);
        }
        else if (paddleSize == 1) {
            drawIntermediatePaddle(g);
            drawCompIntermediatePaddle(g);
        }
        else if(paddleSize == 2){
            drawExpertPaddle(g);
            drawCompExpertPaddle(g);
        }

        //draw the pong balls by going through the array list and updating their positions
        for (Ball b : balls) {
            int xPos = (b.getxCount() * b.getVelocity());
            int yPos = (b.getyCount() * b.getVelocity());

            //check if the center of the ball touches the lower or upper wall
            if ((yPos > (961 - 10 - b.getRadius())) || (yPos < (100 + 10 + b.getRadius()))) {
                b.setGoYBackwards(!b.isGoYBackwards());
            }

            //check if the center of the ball touches the left paddle
            if(xPos < (120 + 10 + b.getRadius()) && xPos >120+30) {
                if ((yPos <= bottomOfLeftPaddleCoord) && (yPos >= topOfLeftPaddleCoord)) {
                    b.setGoXBackwards(!b.isGoXBackwards());
                }
            }

            //check if the center of the ball touches the right paddle
            if(xPos > (1908 - 10 - b.getRadius()) && xPos <1908-30) {
                if ((yPos <= bottomOfRightPaddleCoord) && (yPos >= topOfRightPaddleCoord)) {
                    b.setGoXBackwards(!b.isGoXBackwards());
                }
            }

            //check if the ball travels passed the left paddle and off the screen
            if(xPos < 0) {
                balls.remove(b);
                //computer just scored a point!
                this.computerScore++;
                break;
            }

            //check if the ball travels passed the right paddle and off the screen
            if(xPos > 2008) {
                balls.remove(b);
                //human just scored a point!
                this.humanScore++;
                break;
            }

            //update the ball positions depending on the direction they are traveling
            if (b.isGoXBackwards()) {
                b.setxCount((b.getxCount())-1);
            }
            else {
                b.setxCount((b.getxCount())+1);
            }
            if (b.isGoYBackwards()) {
                b.setyCount((b.getyCount())-1);
            }
            else {
                b.setyCount((b.getyCount())+1);
            }

            //Paint the balls on the canvas
            xPos = (b.getxCount() * b.getVelocity());
            yPos = (b.getyCount() * b.getVelocity());
            Paint redPaint = new Paint();
            redPaint.setColor(Color.RED);
            g.drawCircle(xPos, yPos, b.getRadius(), redPaint);

            //move the computer paddle
            moveComputerPaddle();
        }

    }



    /**
     * External Citation
     * Date:     23 March 2018
     * Problem:  I was confused on which MotionEvent constant to use
     * Resource: https://developer.android.com/reference/android/view/MotionEvent.html#ACTION_MOVE
     * Solution: I used the example code and information from this website and was able to use and
     * understand what MotionEvent.ACTION_MOVE means
     *
     */
    /**
     * This method allows the human player to move the paddle. Every time the human player slides
     * his/her finger across the screen, this method updates the paddle position.
     *
     *  @param event: the event that occurred
     */
    @Override
    public void onTouch(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_MOVE)
        {
            this.yPosLeftPaddleClick = (int)event.getY();
        }
    }


    /*
     * This is one of the required methods for implementing the View.OnClickListener. This class
     * changes the instance variables depending on which button was pressed.
     *
     * @param v: the button that was pressed
     */
    @Override
    public void onClick(View v) {
        Button buttonWasClicked = (Button) v;
        String buttonLabel = (String) buttonWasClicked.getText();

        //check which button was pressed and update the instance variables accordingly
        if (buttonLabel.equalsIgnoreCase("Beginner")){
            this.paddleSize = 0;
            //update the computer paddle speed so that the AI poses a challenge to the user
            this.computerPaddleSpeed = 4;
        }
        else if (buttonLabel.equalsIgnoreCase("Intermediate")){
            this.paddleSize = 1;
            //update the computer paddle speed so that the AI poses a challenge to the user
            this.computerPaddleSpeed = 10;
        }
        else if (buttonLabel.equalsIgnoreCase("Expert")){
            this.paddleSize = 2;
            //update the computer paddle speed so that the AI poses a challenge to the user
            this.computerPaddleSpeed = 18;
        }
        else if (buttonLabel.equalsIgnoreCase(("Add Ball"))){
            balls.add(new Ball());
        }
    }

    /*
     * This is a helper method to help draw the paddle. This method draws the beginner paddle
     * by drawing a blue Trapezoid. The coordinates used were obtained by guess and check methods.
     *
     * @param g: The canvas on which to draw.
     */
    public void drawBeginnerPaddle(Canvas g){
        Paint bluePaint = new Paint();
        bluePaint.setColor(Color.rgb(0, 191,255));
        Path beginnerTrapezoid = new Path();
        beginnerTrapezoid.moveTo(80f, (this.yPosLeftPaddleClick - 220));
        beginnerTrapezoid.lineTo(120f, (this.yPosLeftPaddleClick - 300));
        beginnerTrapezoid.lineTo(120f,(this.yPosLeftPaddleClick + 300));
        beginnerTrapezoid.lineTo(80f, (this.yPosLeftPaddleClick + 220));
        g.drawPath(beginnerTrapezoid, bluePaint);
        this.topOfLeftPaddleCoord = (this.yPosLeftPaddleClick - 300);
        this.bottomOfLeftPaddleCoord = (this.yPosLeftPaddleClick + 300);
    }

    /*
    * This is a helper method to help draw the paddle. This method draws the intermediate paddle
     * by drawing a green Trapezoid. The coordinates used were obtained by guess and check methods.
    *
    * @param g: The canvas on which to draw.
    */
    public void drawIntermediatePaddle(Canvas g){
        Paint neonGreenPaint = new Paint();
        neonGreenPaint.setColor(Color.rgb(13, 253,85));
        Path IntermediateTrapezoid = new Path();
        IntermediateTrapezoid.moveTo(80f, (this.yPosLeftPaddleClick - 160));
        IntermediateTrapezoid.lineTo(120f, (this.yPosLeftPaddleClick - 210));
        IntermediateTrapezoid.lineTo(120f,(this.yPosLeftPaddleClick + 210));
        IntermediateTrapezoid.lineTo(80f, (this.yPosLeftPaddleClick + 160));
        g.drawPath(IntermediateTrapezoid, neonGreenPaint);
        this.topOfLeftPaddleCoord = (this.yPosLeftPaddleClick - 210);
        this.bottomOfLeftPaddleCoord = (this.yPosLeftPaddleClick + 210);
    }

    /*
    * This is a helper method to help draw the paddle. This method draws the expert paddle
    * by drawing a purple Trapezoid. The coordinates used were obtained by guess and check methods.
    *
    * @param g: The canvas on which to draw.
    */
    public void drawExpertPaddle(Canvas g){
        Paint neonPurplePaint = new Paint();
        neonPurplePaint.setColor(Color.rgb(205,13,253));
        Path ExpertTrapezoid = new Path();
        ExpertTrapezoid.moveTo(80f, (this.yPosLeftPaddleClick - 60));
        ExpertTrapezoid.lineTo(120f, (this.yPosLeftPaddleClick - 90));
        ExpertTrapezoid.lineTo(120f,(this.yPosLeftPaddleClick + 90));
        ExpertTrapezoid.lineTo(80f, (this.yPosLeftPaddleClick + 60));
        g.drawPath(ExpertTrapezoid, neonPurplePaint);
        this.topOfLeftPaddleCoord = (this.yPosLeftPaddleClick - 90);
        this.bottomOfLeftPaddleCoord = (this.yPosLeftPaddleClick + 90);
    }


    /*
    * This is a helper method to help draw the computer paddle. This method draws the beginner
    * computer paddle by drawing a blue Trapezoid. The coordinates used were obtained by
    * careful calculations.
    *
    * @param g: The canvas on which to draw.
    */
    public void drawCompBeginnerPaddle(Canvas g){
        Paint bluePaint = new Paint();
        bluePaint.setColor(Color.rgb(0, 191,255));
        Path beginnerTrapezoid = new Path();
        beginnerTrapezoid.moveTo(1948f, (this.yPosComputer - 220));
        beginnerTrapezoid.lineTo(1908f, (this.yPosComputer - 300));
        beginnerTrapezoid.lineTo(1908f,(this.yPosComputer + 300));
        beginnerTrapezoid.lineTo(1948f, (this.yPosComputer + 220));
        g.drawPath(beginnerTrapezoid, bluePaint);
        this.topOfRightPaddleCoord = (this.yPosComputer - 300);
        this.bottomOfRightPaddleCoord = (this.yPosComputer + 300);
    }

    /*
    * This is a helper method to help draw the computer paddle. This method draws the intermediate
    *  computer paddle by drawing a green Trapezoid. The coordinates used were obtained by
    *  careful calculations.
    *
    * @param g: The canvas on which to draw.
    */
    public void drawCompIntermediatePaddle(Canvas g){
        Paint neonGreenPaint = new Paint();
        neonGreenPaint.setColor(Color.rgb(13, 253,85));
        Path IntermediateTrapezoid = new Path();
        IntermediateTrapezoid.moveTo(1948f, (this.yPosComputer - 160));
        IntermediateTrapezoid.lineTo(1908f, (this.yPosComputer - 210));
        IntermediateTrapezoid.lineTo(1908f,(this.yPosComputer + 210));
        IntermediateTrapezoid.lineTo(1948f, (this.yPosComputer + 160));
        g.drawPath(IntermediateTrapezoid, neonGreenPaint);
        this.topOfRightPaddleCoord = (this.yPosComputer - 210);
        this.bottomOfRightPaddleCoord = (this.yPosComputer + 210);
    }

    /*
    * This is a helper method to help draw the expert computer paddle. This method draws the expert
    * computer paddle by drawing a purple Trapezoid. The coordinates used were obtained by
    * careful calculations and guess and check methods.
    *
    * @param g: The canvas on which to draw.
    */
    public void drawCompExpertPaddle(Canvas g){
        Paint neonPurplePaint = new Paint();
        neonPurplePaint.setColor(Color.rgb(205,13,253));
        Path ExpertTrapezoid = new Path();
        ExpertTrapezoid.moveTo(1948f, (this.yPosComputer - 60));
        ExpertTrapezoid.lineTo(1908f, (this.yPosComputer - 90));
        ExpertTrapezoid.lineTo(1908f,(this.yPosComputer + 90));
        ExpertTrapezoid.lineTo(1948f, (this.yPosComputer + 60));
        g.drawPath(ExpertTrapezoid, neonPurplePaint);
        this.topOfRightPaddleCoord = (this.yPosComputer - 90);
        this.bottomOfRightPaddleCoord = (this.yPosComputer + 90);
    }

    /*
     * This method controls the computer paddle by responding to the closest ball. It moves the paddle
     * according to the paddle speed variable only when the ball is approaching the computer paddle.
     */
    public void moveComputerPaddle(){
        int closestBallID = getClosestBall(); //get the closest ball id
        //get the y Position of the closest ball
        int yPosClosestBall = (balls.get(closestBallID).getyCount() * balls.get(closestBallID).getVelocity());
        //if the ball is moving towards the computer paddle AND the computer paddle yPos is less than the ball
        if((balls.get(closestBallID).isGoXBackwards() == false) && (this.yPosComputer < yPosClosestBall)){
            //move the computer paddle downward
            this.yPosComputer = this.yPosComputer + this.computerPaddleSpeed;
        }
        //if the ball is moving towards the computer paddle AND the computer paddle yPos is greater than the ball
        if((balls.get(closestBallID).isGoXBackwards() == false) && (this.yPosComputer > yPosClosestBall)){
            //move the computer paddle upward
            this.yPosComputer = this.yPosComputer - this.computerPaddleSpeed;
        }
    }

    /**
     External Citation
     Date:     23 March 2018
     Problem:  Needed a quick algorithm to find the highest value in an arrayList
     Resource: https://stackoverflow.com/questions/8304767/how-to-get-maximum-value-from-the-list-arraylist
     Solution: I used the example code and information from this website and was able to successfully
     * use this method to get the closest ball to the computer paddle.
     */

    /*
     * This method gets the closest ball to the computer paddle.
     *
     * @return: closestBallID: the closest ball to the computer paddle's ID.
     */
    public int getClosestBall(){
        int closestBallID = 0;
        int max = balls.get(0).getxCount();
        for (int i =0; i<balls.size(); i++){
            if(balls.get(i).getxCount() > max){
                closestBallID = i;
            }
        }
        return closestBallID;
    }

    /**
     * Tells that we never pause.
     *
     * @return indication of whether to pause
     */
    @Override
    public boolean doPause() {
        return false;
    }

    /**
     * Tells that we never stop the animation.
     *
     * @return indication of whether to quit.
     */
    @Override
    public boolean doQuit() {
        return false;
    }

}
