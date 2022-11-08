package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name="Autonomous_forward", group="Linear Opmode")

public class Autonomous_forward extends LinearOpMode{
        private Controller RobotController;
  
   public void runOpMode() {

   
    RobotController = new Controller(this);
    RobotController.initialize();
    float SPEED = 0.5f; 

    waitForStart();
    
    //This is the main loop. I will call the drive function on a loop until you stop the robot
    if (opModeIsActive()) {
        RobotController.drive(-SPEED, 0.0f, 0.0f);
        sleep(1000);
        RobotController.drive(0.0f, 0.0f, 0.0f);
    }
   }
}
