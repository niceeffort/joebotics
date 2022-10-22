package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;


@Autonomous(name="Basic: Linear OpMode", group="Linear Opmode")

public class Autonomous_1 extends LinearOpMode {
   
   private Controller RobotController;
  
   public void runOpMode() {

   
    RobotController = new Controller(this);
    RobotController.initialize();
    

    waitForStart();

    //This is the main loop. I will call the drive function on a loop until you stop the robot
    if (opModeIsActive()) {
      while (opModeIsActive()){
        //telemtry.addLine("hello world");
      }
    }
   }
}
