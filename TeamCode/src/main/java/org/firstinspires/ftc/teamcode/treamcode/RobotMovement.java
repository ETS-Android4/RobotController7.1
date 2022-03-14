package org.firstinspires.ftc.teamcode.treamcode;

//import com.company.ComputerDebugging;

import org.firstinspires.ftc.teamcode.GeluRobot;
import org.firstinspires.ftc.teamcode.RobotUtilities.MovementVars;
import  org.firstinspires.ftc.teamcode.treamcode.Robot.*;
import  org.firstinspires.ftc.teamcode.treamcode.Robot;

import static org.firstinspires.ftc.teamcode.RobotUtilities.MovementVars.*;

import java.util.ArrayList;

import org.firstinspires.ftc.teamcode.RobotUtilities.Range;

import  org.firstinspires.ftc.teamcode.treamcode.MathFunctions;

public class RobotMovement extends GeluRobot{


    public void followCurve(ArrayList<CurvePoint> allPoints, double followAngle){
        for( int i = 0; i < allPoints.size()- 1;i++){
            //ComputerDebugging.sendLine(new FloatPoint(allPoints.get(i).x,allPoints.get(i).y),
                    //new FloatPoint(allPoints.get(i+1).x,allPoints.get(i+1).y));
        }

        CurvePoint followMe = getFollowPointPath(allPoints,new Point(Robot.worldXPosition,Robot.worldYPosition),
                allPoints.get(0).followDistance);

        //ComputerDebugging.sendKeyPoint(new FloatPoint(followMe.x,followMe.y));

        goToPosition(followMe.x,followMe.y,followMe.moveSpeed,followAngle,followMe.turnSpeed);
    }



    public CurvePoint getFollowPointPath(ArrayList<CurvePoint> pathPoints, Point robotLocation, double followRadius){
        CurvePoint followMe = new CurvePoint(pathPoints.get(0));

        for(int i = 0; i < pathPoints.size() - 1; i++){
            CurvePoint startLine = pathPoints.get(i);
            CurvePoint endLine = pathPoints.get(i+1);

            ArrayList<Point> intersections = MathFunctions.lineCircleIntersection(robotLocation,followRadius,startLine.toPoint(),
                    endLine.toPoint());

            double closestAngle = 10000000;

            for(Point thisIntersection : intersections){
                double angle = Math.atan2(thisIntersection.y - Robot.worldYPosition, thisIntersection.x - Robot.worldXPosition);
                double deltaAngle = Math.abs(MathFunctions.AngleWrap(angle - Robot.worldAngle_rad));

                if(deltaAngle < closestAngle){
                    closestAngle = deltaAngle;
                    followMe.setPoint(thisIntersection);
                }
            }
        }

        return followMe;
    }

    /**
     *
     * @param x
     * @param y
     * @param movementSpeed
     */
    public void  goToPosition(double x, double y, double movementSpeed, double preferredAngle, double turnSpeed){
        double distanceToTarget = Math.hypot(x - Robot.worldXPosition,y - Robot.worldYPosition);//sqrt(x*x+y*y)

        double absoluteAngleToTarget = Math.atan2(y - Robot.worldYPosition,x - Robot.worldXPosition);

        double relativeAngleToPoint = MathFunctions.AngleWrap(absoluteAngleToTarget - (Robot.worldAngle_rad -Math.toRadians(90)));

        double relativeXToPoint = Math.cos(relativeAngleToPoint) * distanceToTarget;
        double relativeYToPoint = Math.sin(relativeAngleToPoint) * distanceToTarget;

        double MovementXPower = relativeXToPoint / (Math.abs(relativeXToPoint) + Math.abs(relativeYToPoint));
        double MovementYPower = relativeYToPoint / (Math.abs(relativeXToPoint) + Math.abs(relativeYToPoint));

        MovementVars.movement_x = MovementXPower * movementSpeed;
        MovementVars.movement_y = MovementYPower * movementSpeed;
        go(1,1,1);

        double relativeTurnAngle = relativeAngleToPoint -Math.toRadians(180) + preferredAngle;//180 angle in program
        MovementVars.movement_turn = Range.clip( relativeTurnAngle / Math.toRadians(30),-1 ,1) * turnSpeed;

        if(distanceToTarget < 10){
            MovementVars.movement_turn = 0;
        }
    }
}
