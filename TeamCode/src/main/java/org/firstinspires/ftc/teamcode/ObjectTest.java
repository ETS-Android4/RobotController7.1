
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.GeluRobot;


@TeleOp(name="Object Recognition TEst", group="Iterative Opmode")
//@Disabled
public class ObjectTest extends GeluRobot {


    @Override
    public void loop() {
        telemetry.addLine(Integer.toString(levelRecognitionThreeObjects()));
    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
    }

}
