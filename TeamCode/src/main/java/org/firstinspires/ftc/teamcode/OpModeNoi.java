package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

import java.nio.channels.Pipe;
import java.sql.Time;

@TeleOp(name="Mecanum Mk1", group="Iterative Opmode")
//@Disabled
public class OpModeNoi extends OpMode
{
    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor LBMOTOR;
    private DcMotor LFMOTOR;
    private DcMotor RBMOTOR;
    private DcMotor RFMOTOR;
    private DcMotor GLIS;
    private DcMotor SOCA;
    private Servo LBOXSERVO;
    private Servo RBOXSERVO;
    BNO055IMU imu;

    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
        telemetry.addData("Status", "Initialized");

        // Initialize the hardware variables.
        LBMOTOR = hardwareMap.get(DcMotor.class, "LBM");
        LFMOTOR = hardwareMap.get(DcMotor.class, "LFM");
        RBMOTOR = hardwareMap.get(DcMotor.class, "RBM");
        RFMOTOR = hardwareMap.get(DcMotor.class, "RFM");
        //GLIS = hardwareMap.get(DcMotor.class,"GLIS");
        //SOCA = hardwareMap.get(DcMotor.class,"SOC");

        //LBOXSERVO = hardwareMap.get(Servo.class, "LBS");
        //RBOXSERVO = hardwareMap.get(Servo.class, "RBS");

        // Most robots need the motor on one side to be reversed to drive forward
        // Reverse the motor that runs backwards when connected directly to the battery

        LBMOTOR.setDirection(DcMotor.Direction.REVERSE);
        LFMOTOR.setDirection(DcMotor.Direction.REVERSE);
        RBMOTOR.setDirection(DcMotor.Direction.FORWARD);
        RFMOTOR.setDirection(DcMotor.Direction.FORWARD);
        GLIS.setDirection(DcMotor.Direction.REVERSE);

        //LBOXSERVO.setDirection(Servo.Direction.REVERSE);
        //RBOXSERVO.setDirection(Servo.Direction.FORWARD);
        //LBOXSERVO.setPosition(0.5);
        //RBOXSERVO.setPosition(0.5);


        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;

        imu = hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);
        // Tell the driver that initialization is complete.
        telemetry.addData("Status", "Initialized");

    }


    @Override
    public void init_loop() {
    }


    @Override
    public void start() {
        /*runtime.reset();

        LBOXSERVO.setPosition(0.065);
        RBOXSERVO.setPosition(0.065);


        while(runtime.seconds()<=0.6)
        {
            GLIS.setPower(0.2);
        }
        GLIS.setPower(0);
        runtime.reset();
        while(runtime.seconds()<=1.71){
            goOY(0.5, 0);
        }
        goOY(0,0);

        runtime.reset();
        while(runtime.seconds()<=0.6)
        {
            GLIS.setPower(-0.2);
        }
        GLIS.setPower(0);

        LBOXSERVO.setPosition(0.5);
        RBOXSERVO.setPosition(0.5);

        //goOY(0,0.5);
        //while(runtime.seconds()<1){}
        //goOY(0,0);*/
    }


    @Override
    public void loop() {

        telemetry.clearAll();
        telemetry.addData("left front MOTOR",LFMOTOR.getPower());
        telemetry.addData("left back MOTOR",LBMOTOR.getPower());
        telemetry.addData("right front MOTOR",RFMOTOR.getPower());
        telemetry.addData("right back MOTOR",RBMOTOR.getPower());
        double leftStY = gamepad1.left_stick_y;
        double leftStX = gamepad1.left_stick_x;
        double angle = Math.atan2(leftStY,leftStX);
        double power = Math.sqrt(leftStX*leftStX + leftStY*leftStY);
        double rightStY = gamepad1.right_stick_y;
        double rightStX = gamepad1.right_stick_x;
        boolean X = gamepad1.x;
        boolean Y = gamepad1.y;
        boolean A = gamepad1.a;
        boolean B = gamepad1.b;
        boolean UP = gamepad1.dpad_up;
        boolean DOWN = gamepad1.dpad_down;
        //double voltage = hardwareMap.voltageSensor.get("Motor Controller 1").getVoltage();
       // double powerlimit = 12/voltage;
        Orientation angles;

        angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

        telemetry.addData("Heading=Z", angles.firstAngle);
        telemetry.addData("Roll=Y", angles.secondAngle);
        telemetry.addData("Pitch=X", angles.thirdAngle);


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
        if(angle>Math.PI/4 && angle<3*Math.PI/4) {
            //back
            goOY(-power,turn);
        }
        if(angle>5*Math.PI/4 && angle<7*Math.PI/4) {
            //forward
            goOY(power,turn);
        }
        if(angle>3*Math.PI/4 && angle<5*Math.PI/4) {
            if(Math.abs(turn)>=0.05){
                //rotate
                goOY(0,turn);
            }
            else{
                //left
                goOX(-power);
            }
        }
        if(angle>7*Math.PI/4 || angle<Math.PI/4) {
            if(Math.abs(turn)>=0.05){
                //rotate
                goOY(0,turn);
            }
            else {
                //right
                goOX(power);
            }
        }
        if(X) {
            //catch
            LBOXSERVO.setPosition(0.065);
            RBOXSERVO.setPosition(0.065);
        }
        if(Y) {
            //release
            LBOXSERVO.setPosition(0.5);
            RBOXSERVO.setPosition(0.5);
        }
        if (UP) {
            //slide upwards
            runtime.reset();
            while(runtime.seconds()<=0.5) {
                //GLIS.setPower(0.2);
            }
        }
        else {
            //stop slide
            GLIS.setPower(0);
        }
        if (DOWN) {
            //slide downwards
            runtime.reset();
            while(runtime.seconds()<=0.5) {
                GLIS.setPower(-0.2);
            }
        }
        else {
            //stop slide
            GLIS.setPower(0);
        }
        if(A){
            SOCA.setPower(0.75);
        }
        if(B){
            SOCA.setPower(0);
        }
        // Show the elapsed game time and wheel power.
        telemetry.addData("pozitie servo st",LBOXSERVO.getPosition());
        telemetry.addData("pozitie servo dr",RBOXSERVO.getPosition());
        telemetry.addData("Status", "Run Time: " + runtime.toString());
    }
    private void goOX(double power)
    {
        LFMOTOR.setPower(power);
        RFMOTOR.setPower(-power);
        LBMOTOR.setPower(-power);
        RBMOTOR.setPower(power);
    }
    private void goOY(double power,double turn)
    {
        LFMOTOR.setPower(Range.clip(power+turn,-1,1));
        RFMOTOR.setPower(Range.clip(power-turn,-1,1));
        LBMOTOR.setPower(Range.clip(power+turn,-1,1));
        RBMOTOR.setPower(Range.clip(power-turn,-1,1));
    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
        LBOXSERVO.setPosition(0);
        RBOXSERVO.setPosition(0);
        goOY(0,0);
    }

}
