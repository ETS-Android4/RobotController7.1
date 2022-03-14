package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;




@TeleOp(name="TestSuprem", group="Iterative Opmode")


public class Numero1 extends GeluRobot {
    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void loop() {

        telemetry.clearAll();
        /*telemetry.addData("left front MOTOR",frontLeftMotor.getPower());
        telemetry.addData("left back MOTOR",rearLeftMotor.getPower());
        telemetry.addData("right front MOTOR",frontRightMotor.getPower());
        telemetry.addData("right back MOTOR",rearRightMotor.getPower());*/
        telemetry.addData("duck motor",duckScoreMotor.getPower());

        double leftStY = gamepad1.left_stick_y;
        double leftStX = gamepad1.left_stick_x;
        double rightStY = gamepad1.right_stick_y;
        double rightStX = gamepad1.right_stick_x;
        double RT1 = gamepad1.right_trigger;
        double LT1 = gamepad1.left_trigger;
        double RT2 = gamepad2.right_trigger;
        double LT2 = gamepad2.left_trigger;
        boolean X = gamepad1.x;
        boolean Y = gamepad1.y;
        boolean A = gamepad1.a;
        boolean B = gamepad1.b;
        boolean A2 = gamepad2.a;
        boolean X2 = gamepad2.x;
        boolean Y2 = gamepad2.y;
        boolean B2 = gamepad2.b;
        boolean UP = gamepad1.dpad_up;
        boolean DOWN = gamepad1.dpad_down;
        boolean LEFT = gamepad1.dpad_left;
        boolean RIGHT = gamepad1.dpad_right;
        boolean rightDP = gamepad2.dpad_right;
        boolean leftDP = gamepad2.dpad_left;

        double limit = 12/getBatteryVoltage();

        //Orientation angles;

        //angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);


        //telemetry.addData("Heading=Z", angles.firstAngle);
        //telemetry.addData("Roll=Y", angles.secondAngle);
        //telemetry.addData("Pitch=X", angles.thirdAngle);

        double angle = Math.atan2(leftStY,leftStX);
        double power = Math.sqrt(leftStX*leftStX + leftStY*leftStY);// * limit;
        double turn = 0;

        if(angle<0)
        {
            angle=Math.PI*2+angle;
        }
        telemetry.addData("unghi",angle/Math.PI*180);
        telemetry.addData("x",leftStX);
        telemetry.addData("y",leftStY);

        if(rightStX!=0)
            turn = Math.sqrt(rightStY*rightStY + rightStX*rightStX) * Math.abs(rightStX)/rightStX;
        telemetry.addData("turn",turn);

        if(angle>Math.PI/4 && angle<3*Math.PI/4){
            {
                //backward
                goOY(-power,turn);
            }
        }else
        if(angle>5*Math.PI/4 && angle<7*Math.PI/4)
        {
            //forward
            goOY(power,turn);
        }else
        if(angle>3*Math.PI/4 && angle<5*Math.PI/4)
        {
            if(Math.abs(turn)>=0.05){
                //rotate
                goOY(0,turn);
            }
            else{
                //left
                goOX(-power);
            }
        }else {
            if (Math.abs(turn) >= 0.05) {
                //rotate
                goOY(0, turn);
            } else {
                //right
                goOX(power);
            }
        }
        if(UP)
        {
            //runtime.reset();
            //while(runtime.seconds()<=0.5) {
                slideMotor1.setPower(1);
                slideMotor2.setPower(1);
            //}
            //urca;
        }
        else
        {
            slideMotor1.setPower(0);
            slideMotor2.setPower(0);
        }
        if(DOWN)
        {
            //runtime.reset();
            //while(runtime.seconds()<=0.5) {
                slideMotor1.setPower(-1);
                slideMotor2.setPower(-1);
            //}
            //coboara;
        }
        else
        {
            slideMotor1.setPower(0);
            slideMotor2.setPower(0);
        }

        if(LEFT) {
            s1.setPosition(0.7);
        }
        else{
            if(RIGHT)
                s1.setPosition(0.3);
            else{
                s1.setPosition(0.5);
            }
        }


        if(LT2>0)
        {
            duckScoreMotor.setPower(0.28);
        }else{
            if(RT2>0)
            {
                duckScoreMotor.setPower(-0.28);
            }
            else{
                duckScoreMotor.setPower(0);
            }
        }
        if(LT1>0)
        {
            intakeMotor.setPower(1);
        }
        else{
            if(RT1>0)
            {
                intakeMotor.setPower(-1);
            }
            else{
                intakeMotor.setPower(0);
            }
        }


        telemetry.addData("Status", "Run Time: " + runtime);
    }

}
