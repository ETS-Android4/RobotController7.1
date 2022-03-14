package org.firstinspires.ftc.teamcode.autonomii;

import static android.os.SystemClock.sleep;

import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.GeluRobot;


public class AutoCL extends GeluRobot {
    private int  level;
    public void loop(){
        level = levelRecognitionThreeObjects();

        goOX(1);
        runtime.reset();
        while(runtime.seconds()<=1){

        }
        goOY(0,0.4);//turn
        runtime.reset();
        while(runtime.seconds()<=1){

        }
        goOX(1);
        runtime.reset();
        while(runtime.seconds()<=1){

        }
        raiseTo(level);
        waitRaiseToFinish();
        scoreElement();
        goOX(-1);
        runtime.reset();
        while(runtime.seconds()<=1){

        }
        goOY(0,0.5);//turn
        goOX(1);//storage unit

        runtime.reset();
        while(runtime.seconds()<=1){

        }
        stop();
    }

}
