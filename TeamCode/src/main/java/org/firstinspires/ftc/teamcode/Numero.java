package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;




@TeleOp(name="Test8dir", group="Iterative Opmode")


public class Numero extends GeluRobot {
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
        boolean upDP = gamepad2.dpad_up;


        double limit = 12/getBatteryVoltage();

        //Orientation angles;

        //angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);


       // telemetry.addData("Heading=Z", angles.firstAngle);
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

        if(angle>Math.PI/8 && angle<3*Math.PI/8){
            //SE
            go135(-power);
        }else if(angle>3*Math.PI/8 && angle<5*Math.PI/8) {
            //S
            goOY(power,turn);
        }else if(angle>5*Math.PI/8 && angle<7*Math.PI/8){
            //SW
            go45(-power);
        }
        else if(angle>7*Math.PI/8 && angle<9*Math.PI/8) {
            if(Math.abs(turn)>=0.05){
                //rotate
                goOY(0,turn);
            }
            else{
                //left; W
                goOX(-power);
            }
        }else if(angle>9*Math.PI/8 && angle<11*Math.PI/8){
            //NW
            go135(power);
        }
        else if(angle>11*Math.PI/8 && angle<13*Math.PI/8){
            //N
            goOY(power,0);
        }
        else if(angle>13*Math.PI/8 && angle<15*Math.PI/8){
            //NE
            go45(power);
        }
        else {
            if (Math.abs(turn) >= 0.05) {
                //rotate
                goOY(0, turn);
            } else {
                //right;  E

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
        if(A2){
            runtime.reset();
            while(runtime.seconds()<3) {
                duckScoreMotor.setPower(-0.25);
            }

        }
        if(B2) {
            runtime.reset();
            while(runtime.seconds()<3) {
                duckScoreMotor.setPower(-0.28);
            }
        }
        if(Y2){
            runtime.reset();
            while(runtime.seconds()<3) {
                duckScoreMotor.setPower(-0.30 );
            }
        }

        if(LT2>0)
        {
            duckScoreMotor.setPower(LT2);
        }else{
            if(RT2>0)
            {
                duckScoreMotor.setPower(-RT2);
            }
            else{
                duckScoreMotor.setPower(0);
            }
        }
        if(LT1>0)
        {
            intakeMotor.setPower(LT1);
        }
        else{
            if(RT1>0)
            {
                intakeMotor.setPower(-RT1);
            }
            else{
                intakeMotor.setPower(0);
            }
        }
        /*if(X){
            frontLeftMotor.setVelocity(wfrontleft * gearWheels, AngleUnit.RADIANS);
            frontRightMotor.setVelocity(wfrontleft * gearWheels, AngleUnit.RADIANS);
            rearLeftMotor.setVelocity(wfrontleft * gearWheels, AngleUnit.RADIANS);
            rearRightMotor.setVelocity(wfrontleft * gearWheels, AngleUnit.RADIANS);
        }*/

        telemetry.addData("Status", "Run Time: " + runtime);
    }

}
