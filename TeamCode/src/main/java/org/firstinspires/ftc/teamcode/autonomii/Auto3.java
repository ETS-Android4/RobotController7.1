package org.firstinspires.ftc.teamcode.autonomii;

import static org.firstinspires.ftc.teamcode.treamcode.RobotMovement.followCurve;

import org.checkerframework.checker.units.qual.C;
import org.firstinspires.ftc.teamcode.GeluRobot;
import org.firstinspires.ftc.teamcode.treamcode.CurvePoint;

import java.util.ArrayList;

public class Auto3 extends GeluRobot {
    private int level;


    public void loop(){
        level = levelRecognitionThreeObjects();
        ArrayList<CurvePoint> allPoints = new ArrayList<>();//traseu start-shipping hub
        allPoints.add(new CurvePoint(0,0,1.0,1.0,50,Math.toRadians(50),1.0));
        allPoints.add(new CurvePoint(180,180,1.0,1.0,50,Math.toRadians(50),1.0));
        allPoints.add(new CurvePoint(220,180,1.0,1.0,50,Math.toRadians(50),1.0));
        allPoints.add(new CurvePoint(280,50,1.0,1.0,50,Math.toRadians(50),1.0));
        allPoints.add(new CurvePoint(180,0,1.0,1.0,50,Math.toRadians(50),1.0));
        followCurve(allPoints,Math.toRadians(50));
        raiseTo(level);
        waitRaiseToFinish();
        scoreElement();
        for(int i=0;i<1;i++){//repeats x times
            ArrayList<CurvePoint> PointsWarehouse = new ArrayList<>();//traseu shipping hub-warehouse
            PointsWarehouse.add(new CurvePoint(0,0,1.0,1.0,50,Math.toRadians(50),1.0));
            PointsWarehouse.add(new CurvePoint(180,180,1.0,1.0,50,Math.toRadians(50),1.0));
            PointsWarehouse.add(new CurvePoint(220,180,1.0,1.0,50,Math.toRadians(50),1.0));
            PointsWarehouse.add(new CurvePoint(280,50,1.0,1.0,50,Math.toRadians(50),1.0));
            PointsWarehouse.add(new CurvePoint(180,0,1.0,1.0,50,Math.toRadians(50),1.0));
            followCurve(PointsWarehouse,Math.toRadians(0));
            //pick up
            ArrayList<CurvePoint> PointsShipping = new ArrayList<>();//traseu warehouse-shipping hub
            PointsShipping.add(new CurvePoint(0,0,1.0,1.0,50,Math.toRadians(50),1.0));
            PointsShipping.add(new CurvePoint(345,678,1.0,1.0,50,Math.toRadians(50),1.0));
            scoreElement();
        }
        stop();
    }
}
