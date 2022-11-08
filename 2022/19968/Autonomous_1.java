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
    float SPEED = 0.5f;  
    int LIFTCOUNT = 20; 
    

    waitForStart();

    //This is the main loop. I will call the drive function on a loop until you stop the robot
    if (opModeIsActive()) {
        RobotController.drive(0.0f, -SPEED, 0.0f);
        sleep(1000);
        RobotController.drive(SPEED, 0.0f, 0.0f);
        sleep(50);
        RobotController.drive(0.0f, 0.0f, 0.0f);
        for (int i = 0; i < LIFTCOUNT; i++) {
            RobotController.liftUp();
        }
        sleep(2000);
        RobotController.clawClose();
        sleep(2000);
        RobotController.drive(-SPEED, 0.0f, 0.0f);
        sleep(200);
        RobotController.drive(0.0f, SPEED, 0.0f);
        sleep(1000);
        RobotController.drive(-SPEED, 0.0f, 0.0f);
        sleep(1000);
        RobotController.drive(0.0f, 0.0f, 0.0f);
        for (int i = 0; i < LIFTCOUNT; i++) {
            RobotController.liftDown();
        }
    }
   }
}
