package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@Autonomous(name="Yah", group="Iterative Opmode")
public class meere extends GeluRobot{
    private ElapsedTime runtime = new ElapsedTime();
    @Override
    public void start() {
        while(runtime.seconds()<2){
        }
        runtime.reset();
        while(runtime.seconds()<0.3 )
            goOX(0.75);
        goOX(0);
        runtime.reset();
        while (runtime.seconds()<0.15)
            goOY(0.75,0);
        runtime.reset();
        while(runtime.seconds()<=5)
            duckScoreMotor.setPower(-0.25);
        duckScoreMotor.setPower(0);
        runtime.reset();
        while(runtime.seconds()<0.32)
            goOY(0.7,0);
        goOX(0);
    }


}
