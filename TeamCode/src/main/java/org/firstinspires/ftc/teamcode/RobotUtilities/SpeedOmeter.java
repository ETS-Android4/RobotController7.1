package org.firstinspires.ftc.teamcode.RobotUtilities;

import org.firstinspires.ftc.teamcode.treamcode.MathFunctions;

import org.firstinspires.ftc.teamcode.treamcode.Robot;
import org.firstinspires.ftc.teamcode.treamcode.Robot.*;

public class SpeedOmeter {
    private  long lastUpdateStartTime = 0;
    private  double currSpeedY = 0.0;
    private  double currSpeedX = 0.0;

    //min time between updates to make sure our speed is accurate
    public  int timeBetweenUpdates = 25;
    public  double yDistTraveled = 0;
    public  double xDistTraveled = 0;


    public  double lastAngle = 0;

    public  double angularVelocity = 0;

    //calculates our current velocity every update
    public  void update(){
        long currTime = System.currentTimeMillis();

        //return if no change in telemetry
        if(Math.abs(yDistTraveled) < 0.000000001 && Math.abs(xDistTraveled) < 0.000000001 &&
                Math.abs(angularVelocity) < 0.000001){
            return;
        }


        if(currTime - lastUpdateStartTime > timeBetweenUpdates){
            //elapsedTime in seconds
            double elapsedTime = (double) (currTime - lastUpdateStartTime)/1000.0;
            double speedY = yDistTraveled / elapsedTime;
            double speedX = xDistTraveled / elapsedTime;

            if(speedY < 200 && speedX < 200){//I can assure you our robot can't go 200 m/s
                currSpeedY = speedY;
                currSpeedX = speedX;
            }


            angularVelocity = MathFunctions.AngleWrap(Robot.worldAngle_rad-lastAngle) / elapsedTime;
            lastAngle = Robot.worldAngle_rad;

            yDistTraveled = 0;
            xDistTraveled = 0;
            lastUpdateStartTime = currTime;
        }
    }

    /**gets relative y speed in cm/s*/
    public  double getSpeedY(){
        return currSpeedY;
    }
    /**gets relative x speed = cm/s*/
    public  double getSpeedX(){
        return currSpeedX;
    }

    public  double getDegPerSecond() {
        return Math.toDegrees(angularVelocity);
    }
    public  double getRadPerSecond(){
        return angularVelocity;
    }

    public  double scalePrediction = 1.0;
    //amount robot slips (cm) while going forwards 1 centimeter per second
    public  double ySlipDistanceFor1CMPS = 0.14 * scalePrediction;//0.169;
    public  double xSlipDistanceFor1CMPS = 0.153 * scalePrediction;//0.117;
    //radians the robot slips when going 1 radian per second
    public  double turnSlipAmountFor1RPS = 0.09 * scalePrediction;//0.113;


    /** Gives the current distance (cm) the robot would slip if power is set to 0 */
    public  double currSlipDistanceY(){
        return SpeedOmeter.getSpeedY() * ySlipDistanceFor1CMPS;
    }

    public  double currSlipDistanceX(){
        return SpeedOmeter.getSpeedX() * xSlipDistanceFor1CMPS;
    }

    /** Gives the number of radians the robot would turn if power was cut now*/
    public  double currSlipAngle(){
        return getRadPerSecond() * turnSlipAmountFor1RPS;
    }
}
