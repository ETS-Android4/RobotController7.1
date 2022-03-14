package org.firstinspires.ftc.teamcode;
//dragos e de vina
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.VoltageSensor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.treamcode.Point.*;
import java.util.List;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.teamcode.treamcode.Point;

public class GeluRobot extends OpMode{
    //buttons

    protected DcMotor LeftEncoder;
    protected DcMotor RightEncoder;



    protected DcMotor frontLeftMotor;
    protected DcMotor frontRightMotor;
    protected DcMotor rearLeftMotor;
    protected DcMotor rearRightMotor;
    protected ElapsedTime runtime = new ElapsedTime();
    /** {@link #driveMaxPower} stores the max power  {@link #frontLeftMotor},
     * {@link #frontRightMotor},{@link #rearLeftMotor} and {@link #rearRightMotor}
     * should be set to*/
    protected double driveMaxPower=1;
    protected double motorSpeed = 0.75;
    protected double gearWheels = 2/3;
    protected double wfrontleft;
    protected double wfrontright;
    protected double wrearleft;
    protected double wrearright;

    protected static double L = 12;//distance between encoder 1 and 2 in cm
    protected static double B = 11.5;//distance between encoder 1 and 2 in cm
    protected static double R = 3.0;//wheel radius in cm
    protected static double N = 8192;//encoder ticks per revolution,rev encoder
    protected static double cm_per_tick = 2.0 * Math.PI * R / N;

    public int currentRightenc = 0;
    public int currentLeftenc = 0;
    public int currentAngle = 0;

    public int oldRightenc = 0;
    public int oldLeftenc = 0;
    public int oldAngle = 0;

    public double posxs = 213;
    public double posys = 102;
    public double poshs = -173;

    public double posx = posxs;
    public double posy = posys;
    public double posh = poshs;

    protected BNO055IMU imu;
    /** inertial measurement unit
     * #bonus=Indian Maritime University
     */

    protected DcMotor intakeMotor;
    /** {@link #intakeMaxPower} stores the max power  {@link #intakeMotor} should be set to*/
    protected double intakeMaxPower=1;


    protected DcMotor duckScoreMotor;
    /**{@link #duckScoreMotorSteps} stores the amount of steps {@link #duckScoreMotor}  has to make in order to do a full rotation*/
    protected int duckScoreMotorSteps=300;// cereti de la design
    /**{@link #duckScoreMotorGearRatio} stores the gear ratio on the {@link #duckScoreMotor} */
    protected double duckScoreMotorGearRatio=1;// cereti de la design
    /**{@link #duckScoreMotorWheelRadius} stores the radius(in centimeters) of the wheel {@link #duckScoreMotor}  is actioning */
    protected double duckScoreMotorWheelRadius=2;// cereti de la design
    /**{@carouselWheelRadius} stores the value(in centimeters) of the carousel wheel's radius*/
    protected double carouselWheelRadius=2;// cereti de la design/ cautati in game manual
    /**{@link #duckScoreMotorStepsOneSpin} stores the amount of steps {@link #duckScoreMotor} has to make in order to spin the carousel once*/
    protected double duckScoreMotorStepsOneSpin= duckScoreMotorSteps*duckScoreMotorGearRatio*duckScoreMotorWheelRadius/carouselWheelRadius;
    /**{@link #duckScoreMotor1MaxPower} stores the maximum power at which {@link #duckScoreMotor} should be set*/
    protected double duckScoreMotor1MaxPower=1;

    /**{@link #reqSlideLength1} stores the length the slide needs to extend to in order to reach level 1*/
    protected double reqSlideLength1=10;// cereti de la design
    /**{@link #reqSlideLength2} stores the length the slide needs to extend to in order to reach level 2*/
    protected double reqSlideLength2=20;// cereti de la design
    /**{@link #reqSlideLength3} stores the length the slide needs to extend to in order to reach level 3*/
    protected double reqSlideLength3=30;// cereti de la design

    protected DcMotor slideMotor1;
    /**{@link #slideMotor1Steps} stores the amount of steps {@link #slideMotor1}  has to make in order to do a full rotation*/
    protected int slideMotor1Steps=300;// cereti de la design
    /**{@link #slideMotor1GearRatio} stores the gear ratio on the {@link #slideMotor1} */
    protected double slideMotor1GearRatio=1;// cereti de la design
    /**{@link #slideMotor1WheelRadius} stores the radius(in centimeters) of the wheel {@link #slideMotor1}  is actioning */
    protected double slideMotor1WheelRadius=2;// cereti de la design
    /**{@link #slideMotor1StepsFor1Cm} stores the amount of steps {@link #slideMotor1} has to make in order to move the slide 1 cm*/
    protected double slideMotor1StepsFor1Cm= slideMotor1Steps*slideMotor1GearRatio/(Math.PI*2*slideMotor1WheelRadius);
    /**{@link #slideMotor1StepsForEachLevel} stores a list with the steps {@link #slideMotor1} has to make in order to move the slide to each level
     * numbering from 0 to 2
     * example: slideMotor1StepsForEachLevel[0] is the steps needed for level 1
     * */
    protected double slideMotor1StepsForEachLevel[]={ slideMotor1StepsFor1Cm*reqSlideLength1,
            slideMotor1StepsFor1Cm*reqSlideLength2,
            slideMotor1StepsFor1Cm*reqSlideLength3
    };
    /**{@link #slideMotor1MaxPower} stores the maximum power at which {@link #slideMotor1} should be set*/
    protected double slideMotor1MaxPower=1;


    protected DcMotor slideMotor2;
    /**{@link #slideMotor2Steps} stores the amount of steps {@link #slideMotor2} has to make in order to do a full rotation*/
    protected int slideMotor2Steps=300;// cereti de la design
    /**{@link #slideMotor2GearRatio} stores the gear ratio on the {@link #slideMotor2}*/
    protected double slideMotor2GearRatio=1;// cereti de la design
    /**{@link #slideMotor2WheelRadius} stores the radius(in centimeters) of the wheel {@link #slideMotor2} is actioning*/
    protected double slideMotor2WheelRadius=2;// cereti de la design
    /**{@link #slideMotor2StepsFor1Cm} stores the amount of steps {@link #slideMotor2} has to make in order to move the slide 1 cm*/
    protected double slideMotor2StepsFor1Cm= slideMotor2Steps*slideMotor2GearRatio/(Math.PI*2*slideMotor2WheelRadius);/**{@link #slideMotor1StepsForEachLevel} stores a list with the steps {@link #slideMotor1} has to make in order to move the slide to each level
     * numbering from 0 to 2
     * example: slideMotor1StepsForEachLevel[0] is the steps needed for level 1
     * */

    /**{@link #slideMotor2StepsForEachLevel} stores a list with the steps {@link #slideMotor2} has to make in order to move the slide to each level
     * numbering from 0 to 2
     * example: slideMotor2StepsForEachLevel[0] is the steps needed for level 1
     * */
    protected double slideMotor2StepsForEachLevel[]={ slideMotor2StepsFor1Cm*reqSlideLength1,
            slideMotor2StepsFor1Cm*reqSlideLength2,
            slideMotor2StepsFor1Cm*reqSlideLength3
    };
    /**{@link #slideMotor2MaxPower} stores the maximum power at which {@link #slideMotor2} should be set*/
    protected double slideMotor2MaxPower=1;

    protected Servo s1;
    protected Servo s2;


    /**{@link #TFOD_MODEL_ASSET} stores the assed used by TFOD*/
    private static final String TFOD_MODEL_ASSET = "FreightFrenzy_BCDM.tflite";

    /**{@link #LABELS} contains the labels used for Object Identification*/
    private static final String[] LABELS = {"Ball","Cube","Duck"/*"Marker"*/};

    /*
     * IMPORTANT: You need to obtain your own license key to use Vuforia. The string below with which
     * 'parameters.vuforiaLicenseKey' is initialized is for illustration only, and will not function.
     * A Vuforia 'Development' license key, can be obtained free of charge from the Vuforia developer
     * web site at https://developer.vuforia.com/license-manager.
     *
     * Vuforia license keys are always 380 characters long, and look as if they contain mostly
     * random data. As an example, here is a example of a fragment of a valid key:
     *      ... yIgIzTqZ4mWjk9wd3cZO9T1axEqzuhxoGlfOOI2dRzKS4T0hQ8kT ...
     * Once you've obtained a license key, copy the string from the Vuforia web site
     * and paste it in to your code on the next line, between the double quotes.
     */
    /**{@link #vuforia} stores the Vuforia Key used */
    private static final String VUFORIA_KEY = "AYdD3yn/////AAABmb3P9cIyFUNOuKQAjQt11WUTizO7mmScqJ9rVPhdz6U6mYAbgcQNvTqsJB/aAAvrTA6c7Z/6GaAw+xdIG6QeGuHr11Nui3t5QJ3necBn6oALGaJAUwgL5lTjGRfUc5oqME8EAm18HI/Ka8FPWUXwqRxzEP7JF8tDOkce0FeGQBSamSKhJ447Op4w24rYNXfeNCG3c3kigPOSvVj/j4nvJE0teuAhWSkpAJyiITm+lKz27lADjkFAna92olgL7PCDvuua4J2fiGWL00kQNzKn5muPJ5WhJdbvG5/r84QK7MEkZEZXfr/9y72Am1tKE/RooVMt3ZMq3t3aA6GC5Uwohj6BCUDJszettT1u6TddaNig";

    /** {@link #vuforia} is the variable we will use to store our instance of the Vuforia localization engine.*/
    private VuforiaLocalizer vuforia;

    /** {@link #tfod} is the variable we will use to store our instance of the TensorFlow Object Detection engine.*/
    private TFObjectDetector tfod;

    /** {@link #leftThreshold} contains the ratio below which a element is considered to sitting on the left instead of sitting on the right when using levelRecognitionTwoObjects*/
    private float leftThreshold = 0.5f;
    /** {@link #cameraWidth} represents the width of the camera used*/
    private int cameraWidth;
    /** {@link #cameraHeight} represents the height of the camera used*/
    private int cameraHeight;

    /**{@link #leftSideThreshold} contains the ratio below which a element is considered to sitting on the left side when using levelRecognitionThreeObjects*/
    private float leftSideThreshold =0.3f;
    /**{@link #rightSideThreshold} contains the ratio above which a element is considered to sitting on the right side when using levelRecognitionThreeObjects*/
    private float rightSideThreshold =0.6f;

    /** {@link #initVuforia} initializes Vuforia*/
    private void initVuforia() {
        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         */
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        //parameters.cameraName = hardwareMap.get(WebcamName.class, "Webcam 1");//daca webcam

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);
        cameraHeight= vuforia.getCameraCalibration().getSize().getHeight();
        cameraWidth= vuforia.getCameraCalibration().getSize().getWidth();
        // Loading trackables is not necessary for the TensorFlow Object Detection engine.
    }


    /** {@link #initTfod} initializes Tfod*/
    private void initTfod() {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfodParameters.minResultConfidence = 0.8f;// era 0.8 dar e mai ok pt ca scana prost
        tfodParameters.isModelTensorFlow2 = true;
        tfodParameters.inputSize = 320;
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABELS);
    }

    /**{@link #initDriveMotors} initializes the motors used for the Drive Train*/
    private void initDriveMotors(){
        frontLeftMotor = hardwareMap.get(DcMotor.class, "LFM");
        frontRightMotor = hardwareMap.get(DcMotor.class, "RFM");
        rearLeftMotor =hardwareMap.get(DcMotor.class, "LBM");
        rearRightMotor = hardwareMap.get(DcMotor.class, "RBM");
        frontLeftMotor.setDirection(DcMotor.Direction.REVERSE);
        frontRightMotor.setDirection(DcMotor.Direction.FORWARD);
        rearLeftMotor.setDirection(DcMotor.Direction.REVERSE);
        rearRightMotor.setDirection(DcMotor.Direction.FORWARD);
        frontLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rearLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rearRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        RightEncoder = frontRightMotor;
        LeftEncoder = frontLeftMotor;
    }

    /**{@link #initIntakeModule} initializes the hardware used for the Intake Module*/
    private void initIntakeModule(){
        intakeMotor = hardwareMap.get(DcMotor.class, "maturica");
        duckScoreMotor.setDirection(DcMotor.Direction.FORWARD);

    }

    /**{@link #initDuckScoreModule} initializes the hardware used for the Duck Scoring Module*/
    private void initDuckScoreModule(){
        duckScoreMotor = hardwareMap.get(DcMotor.class, "duck");
        duckScoreMotor.setDirection(DcMotor.Direction.REVERSE);
        //duckScoreMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    /**{@link #initElementScoringModule} initializes the hardware used for the Element Scoring Module*/
    private void initElementScoringModule(){
        slideMotor1 = hardwareMap.get(DcMotor.class, "slides");
        slideMotor2 = hardwareMap.get(DcMotor.class, "slided");
        slideMotor1.setDirection(DcMotor.Direction.FORWARD);
        slideMotor2.setDirection(DcMotor.Direction.REVERSE);
        slideMotor1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slideMotor1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        s1 = hardwareMap.get(Servo.class, "s1");
        //s2 = hardwareMap.get(Servo.class, "s2");
        //direction pt maturica, duck & arm;
        //direction pt s1, s1;

    }

    private void initIMU(){
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;

        imu = hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);
    }

    /**{@link #odometry} insert description here*/
    protected void odometry() {
        oldRightenc = currentRightenc;
        oldLeftenc = currentLeftenc;
        oldAngle = currentAngle;

        currentRightenc = -RightEncoder.getCurrentPosition();
        currentLeftenc = LeftEncoder.getCurrentPosition();

        int dn1 = currentRightenc - oldRightenc;
        int dn2 = currentLeftenc - oldLeftenc;

        double dtheta = currentAngle - oldAngle;
        double dx = cm_per_tick * dn1;
        double dy = cm_per_tick * (dn2 - dn1*B);

        double theta = posh + dtheta;
        posx += dx * Math.cos(theta) - dy * Math.sin(theta);
        posy += dx * Math.sin(theta) + dy * Math.cos(theta);
        posh += theta;
    }
    /**{@link #scoreElement} scores the element*/
    protected void scoreElement() {
        s1.setPosition(1);//release
        runtime.reset();
        while(runtime.seconds()<=1){

        }
        s1.setPosition(0);
    }

    /**{@link #raiseTo} raises the slider to the given level*/
    protected void raiseTo(int level) {
        if(0<=level && level>=2) {
            // if the level is between 0 and 2, as it should be, sets the positions for the motors
            slideMotor1.setTargetPosition((int) slideMotor1StepsForEachLevel[level - 1]);
            slideMotor2.setTargetPosition((int) slideMotor2StepsForEachLevel[level - 1]);
            slideMotor1.setPower(slideMotor1MaxPower);
            slideMotor2.setPower(slideMotor2MaxPower);
            slideMotor1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            slideMotor2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            telemetry.addLine("Raising slide to level "+Integer.toString(level));
            telemetry.update();
        }
        else
        {
            // if the level is not between 0 and 2, just notifies the user
            telemetry.addLine("The level is set wrong dum dum");
            telemetry.update();
        }
    }
    /** {@link #waitRaiseToFinish} causes the robot to wait until {@link #slideMotor1} and {@link #slideMotor2} finish their movements*/
    protected void waitRaiseToFinish(){
        while(slideMotor1.isBusy() || slideMotor2.isBusy())
        {

        }
    }

    /**{@link #scoreDuck} scores a duck*/
    protected void scoreDuck() {
        duckScoreMotor.setTargetPosition(duckScoreMotor.getCurrentPosition()+ (int) duckScoreMotorStepsOneSpin);
        duckScoreMotor.setPower(duckScoreMotor1MaxPower);
        duckScoreMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
    /** {@link #waitRaiseToFinish} causes the robot to wait until {@link #scoreDuck} finishes it's movement*/
    protected void waitScoreDuck(){
        while(duckScoreMotor.isBusy())
        {

        }
    }

    /** {@link #levelRecognitionTwoObjects} method used to recognize at which level should the robot score the element when using a camera which points only at 2 spots
     * @return the level at which the element should be scored, returns -1 if tfod was not properly initialized or more than 1 element has been found
     * */
    protected int levelRecognitionTwoObjects(){
        /*
         * The implementation of this differs depending on which two objects the camera points to
         * In this case the camera can only see the left spot and the middle one
         *
         */
        if (tfod != null) {
            //this is the case where tfod is properly initialized
            List<Recognition> recognitions = tfod.getRecognitions();
            int elementsDetected = recognitions.size();
            // if elementsDetected is 0 this means the camera did not find any element, thus the element is on the right, return level 3
            telemetry.addLine("There have been found "+ Integer.toString(elementsDetected)+ "elements");
            if(elementsDetected==0){
                //returns 3, meaning right, if there are no elements detected;
                telemetry.addLine("The element is on the right");
                telemetry.update();
                return 3;

            }
            else
            {
                if(elementsDetected>1){
                    // if elementsDetected is greater than 1 it means something went wrong returns -1, representing an error
                    telemetry.addLine(" Too many elements dum dum");
                    telemetry.update();
                    return -1;
                }
                else{
                    //this is the case when the number of elementsDetected is 1
                    //updatedRecognitions.get(0) returns the first element in the list of elements found. Supposing that everything is in order, there should be just 1 element found

                    telemetry.addLine("Element coordinates:" +Float.toString(recognitions.get(0).getLeft()));
                    telemetry.addLine("Camera width: " +Integer.toString( cameraWidth));
                    telemetry.addLine("Threshold: "+Float.toString(leftThreshold));
                    if(recognitions.get(0).getLeft()<leftThreshold * cameraWidth ){
                        //returns 1, meaning left, if the element is located left of the Threshold
                        telemetry.addLine("The element is on the left");
                        telemetry.update();
                        return 1;
                    }
                    else{
                        //returns 2, meaning the middle if the element is located right of the Threshold
                        telemetry.addLine("The element is in the middle");
                        telemetry.update();
                        return 2;
                    }
                }
            }

        }
        else{
            //if tfod is null it means it was not properly initialized, returns -1 representing error
            telemetry.addLine("tfod was not properly initialized dum dum");
            telemetry.update();
            return -1;
        }

    }

    /** {@link #levelRecognitionThreeObjects} method used to recognize at which level should the robot score the element when using a camera which points at all 3 spots
     * @return the level at which the element should be scored, returns -1 if tfod was not properly initialized or more or less than 1 element has been found
     * */
    protected int levelRecognitionThreeObjects() {
        if (tfod != null) {
            //this is the case where tfod is properly initialized
            // getUpdatedRecognitions() will return null if no new information is available since
            // the last time that call was made.
            List<Recognition> recognitions = tfod.getRecognitions();
            int elementsDetected = recognitions.size();

            telemetry.addLine("There have been found " + Integer.toString(elementsDetected) + " elements");
            // if elementsDetected is 0 this means the camera did not find any element, thus the element is on
            // the right, return level 3
            if (elementsDetected == 0) {
                telemetry.addLine("No elements found dum dum");
                telemetry.update();
                return -1;
            } else {
                if (elementsDetected > 1) {
                    // if elementsDetected is greater than 1 it means something went wrong returns -1,
                    // representing an error
                    telemetry.addLine(" Too many elements dum dum");
                    telemetry.update();
                    return -1;
                } else {
                    //this is the case when the number of elementsDetected is 1
                    //updatedRecognitions.get(0) returns the first element in the list of elements found.
                    // Supposing that everything is in order, there should be just 1 element found

                    telemetry.addLine("Element coordinates:" + Float.toString(recognitions.get(0).getLeft()));
                    telemetry.addLine("Camera width: " + Integer.toString(cameraWidth));
                    telemetry.addLine("Left Side Threshold: " + Float.toString(leftSideThreshold));
                    telemetry.addLine("Right Side Threshold: " + Float.toString(rightSideThreshold));


                    if (recognitions.get(0).getLeft() < leftSideThreshold * cameraWidth) {
                        //returns 1, meaning left, if the element is located left of leftSideThreshold
                        telemetry.addLine("The element is on the left");
                        telemetry.update();

                        return 1;
                    } else {
                        if (recognitions.get(0).getLeft() > rightSideThreshold * cameraWidth) {
                            //returns 3, meaning right, if the element is located right of rightSideThreshold
                            telemetry.addLine("The element is on the right");
                            telemetry.update();

                            return 3;
                        } else {
                            //returns 2,meaning center if the element is located between leftSideThreshold
                            // and rightSideThreshold
                            telemetry.addLine("The element is in the middle");
                            telemetry.update();
                            return 2;
                        }
                    }
                }
            }
        } else {
            //if tfod is null it means it was not properly initialized, returns -1 representing error
            telemetry.addLine("tfod was not properly initialized dum dum");
            telemetry.update();
            return -1;
        }
    }

    double getBatteryVoltage() {
        double result = Double.POSITIVE_INFINITY;
        for (VoltageSensor sensor : hardwareMap.voltageSensor) {
            double voltage = sensor.getVoltage();
            if (voltage > 0) {
                result = Math.min(result, voltage);
            }
        }
        return result;
    }

    protected void goOX(double power) {
        frontLeftMotor.setPower(motorSpeed*power);
        frontRightMotor.setPower(-motorSpeed*power);
        rearLeftMotor.setPower(-motorSpeed*power);
        rearRightMotor.setPower(motorSpeed*power);
    }
    protected void goOY(double power,double turn) {
        frontLeftMotor.setPower(Range.clip(power+turn,-motorSpeed,motorSpeed));
        frontRightMotor.setPower(Range.clip(power-turn,-motorSpeed,motorSpeed));
        rearLeftMotor.setPower(Range.clip(power+turn,-motorSpeed,motorSpeed));
        rearRightMotor.setPower(Range.clip(power-turn,-motorSpeed,motorSpeed));
    }
    protected void go45(double power) {
        frontLeftMotor.setPower(power);
        frontRightMotor.setPower(0);
        rearLeftMotor.setPower(0);
        rearRightMotor.setPower(power);
    }
    protected void go135(double power) {
        frontLeftMotor.setPower(0);
        frontRightMotor.setPower(power);
        rearLeftMotor.setPower(power);
        rearRightMotor.setPower(0);
    }
    protected void go(double power,double turnO,double angleO){
        double sin = Math.sin(angleO - Math.PI/4);
        double cos = Math.cos(angleO - Math.PI/4);
        double MAX = Math.max(Math.abs(sin), Math.abs(cos));

        frontLeftMotor.setPower(power * cos/MAX + turnO);
        frontRightMotor.setPower(power * sin/MAX - turnO);
        rearLeftMotor.setPower(power * sin/MAX + turnO);
        rearRightMotor.setPower(power * cos/MAX -turnO);
    }
    @Override
    public void init() {
        //initIMU();
        initDriveMotors();
        initDuckScoreModule();
        initIntakeModule();
        initElementScoringModule();
        initVuforia();
        initTfod();
        if (tfod != null){
            tfod.activate();
        }
    }

    @Override
    public void loop() {

    }

}