package org.firstinspires.ftc.teamcode.autonomii;

import org.firstinspires.ftc.teamcode.treamcode.RobotMovement.*;

import org.firstinspires.ftc.teamcode.GeluRobot;
import org.firstinspires.ftc.teamcode.treamcode.CurvePoint;

import java.util.ArrayList;

public class Auto2 extends GeluRobot {
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
        ArrayList<CurvePoint> PointsCarousel = new ArrayList<>();//traseu shipping hub-carousel
        PointsCarousel.add(new CurvePoint(0,0,1.0,1.0,50,Math.toRadians(50),1.0));
        PointsCarousel.add(new CurvePoint(180,180,1.0,1.0,50,Math.toRadians(50),1.0));
        PointsCarousel.add(new CurvePoint(220,180,1.0,1.0,50,Math.toRadians(50),1.0));
        PointsCarousel.add(new CurvePoint(280,50,1.0,1.0,50,Math.toRadians(50),1.0));
        PointsCarousel.add(new CurvePoint(180,0,1.0,1.0,50,Math.toRadians(50),1.0));
        followCurve(PointsCarousel,Math.toRadians(0));
        scoreDuck();
        waitScoreDuck();
        //take ratusca
        ArrayList<CurvePoint> PointsWarehouse = new ArrayList<>();//traseu carousel-shipping hub
        PointsWarehouse.add(new CurvePoint(0,0,1.0,1.0,50,Math.toRadians(50),1.0));
        PointsWarehouse.add(new CurvePoint(180,180,1.0,1.0,50,Math.toRadians(50),1.0));
        PointsWarehouse.add(new CurvePoint(220,180,1.0,1.0,50,Math.toRadians(50),1.0));
        PointsWarehouse.add(new CurvePoint(280,50,1.0,1.0,50,Math.toRadians(50),1.0));
        PointsWarehouse.add(new CurvePoint(180,0,1.0,1.0,50,Math.toRadians(50),1.0));
        followCurve(PointsWarehouse,Math.toRadians(0));
        scoreElement();
        ArrayList<CurvePoint> PointsS = new ArrayList<>();//traseu shipping hub-storage hub
        PointsS.add(new CurvePoint(0,0,1.0,1.0,50,Math.toRadians(50),1.0));
        PointsS.add(new CurvePoint(180,0,1.0,1.0,50,Math.toRadians(50),1.0));
        followCurve(PointsS,Math.toRadians(0));
        stop();
    }
}
