/*
 *@Author: Avery Guillermo
 *
 **********************PART B ************ PART B *************************************
 *
 * Note to Grader: The enhancements for PART B I have done are:
 *
 * [15%] Make the game more like the original Pong by removing the wall opposite the player's paddle
 * and replacing it with a second paddle that is controlled by the computer. The computer should
 * miss sometimes.
 *  -------> This enhancement is shown with the computer paddle on the right side of the screen.
 *  -------> It moves the paddle up and down on its own by reacting to the closest approaching ball.
 *  -------> In addition, when the user changes the paddle size to either beginner, intermediate or
 *  -------> expert, the Computer paddle also changes size and the level of difficulty of the AI paddle
 *  -------> increases because the paddle speed from which the AI paddle can react increases. Also,
 *  -------> the paddle misses the ball more frequently when in beginner mode, less frequently in
 *  -------> intermediate mode, and the least frequent when in expert mode.
 *
 * 
 *  [5%] Keep a running score, which the user can see. For example the user might gain a point each
 *  time the ball is batted back but lose 5 points each time the ball escapes.
 *  -------> This enhancement is shown on the animation. Every time the human fails to rebound
 *  -------> the ball, the computer scores a point and every time the computer fails to rebound
 *  -------> the ball, the human scores a point.
 *
 *
 *
 *********************PART A ************ PART A **************************************
 * Note to Grader: The enhancements for PART A I have done are:
 *
 * [10%] Allow an arbitrary number of balls to be in play at once. Additional balls can be
 * created by user control (a tap or button press) or can appear at regular intervals.
 *-------->This enhancement is shown by pressing the Add Ball Method
 *
 * [5%] When a ball leaves the field of play, don't add a new ball until the user indicates
 * she is ready by tapping the screen or a button for that purpose.
 * -------> This enhancement is shown when the last ball leaves the screen. In order for the user
 * -------> to add another ball to the screen to play more, she needs to press the "Add Ball" button
 * -------> when she is ready
 *
 * [5%] Allow the user to change the size of the paddle (for “beginner” vs. “expert”
 * mode) in some manner.
 *--------> This is shown with the Radio Buttons located on the Bottom right of the screen. The user
 * -------> can choose "beginner" "intermediate" and "expert" and the paddle sizes change accordingly
 *
 *
 * Note: The part on the screen that says "Current Score:" is not implemented yet and will be
 * implemented as an enhancement in Part B of this assignment.
 *
 */

package edu.guillera19up.pong_guillera19;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class Pong_guillera19 extends AppCompatActivity {


    /**
     * creates an AnimationSurface containing the AnimatorImplementation class that I created.
     *
     * @Author: Avery Guillermo
     *
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pong_guillera19);

        //create an instance of the AnimatorImplementation class
        AnimatorImplementation listener = new AnimatorImplementation();

        /**
         External Citation
         Date:     18 March 2018
         Problem:  AnimationSurface was not taught in class, so this site was used to understand how to use it.
         Resource: https://stackoverflow.com/questions/27130628/animation-on-surface-view
         Solution: I used the example code and information from this website and was able to use
         and understand how AnimationSurfaces work.
         *
         */
        //Connect the animation surface with the animator
        AnimationSurface mySurface = (AnimationSurface) this
                .findViewById(R.id.animationSurface);
        mySurface.setAnimator(listener);

        //initialize the RadioGroup so that Beginner is always checked when the App first runs
        RadioGroup radGroup = (RadioGroup) findViewById(R.id.RadioGroup_Select_Paddle_Size);
        radGroup.check(R.id.radioButton_Beginner);


        /**
         External Citation
         Date:     18 March 2018
         Problem:  Wanted to use Radio Buttons to change the size of the Paddle.
         Resource: https://developer.android.com/guide/topics/ui/controls/radiobutton.html
         Solution: I used the example code and information from this website and was able to use
         Radio buttons
         *
         */
        //declare the beginner, intermediate, and expert radioButtons and set them to listen to OnClick Events
        RadioButton beginnerButton = (RadioButton) this.findViewById(R.id.radioButton_Beginner);
        beginnerButton.setOnClickListener(listener);
        RadioButton intermediateButton = (RadioButton) this.findViewById(R.id.radioButton_Intermediate);
        intermediateButton.setOnClickListener(listener);
        RadioButton expertButton = (RadioButton) this.findViewById(R.id.radioButton_Expert);
        expertButton.setOnClickListener(listener);

        //declare the Add Ball button and set it to listen to OnClick Events
        Button addBallButton = (Button) this.findViewById(R.id.button_Add_Ball);
        addBallButton.setOnClickListener(listener);
    }
}