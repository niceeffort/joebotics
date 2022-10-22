package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import org.firstinspires.ftc.teamcode.DriveCode;

@TeleOp(name = "_19968DriverControl")
public class DriveCode extends LinearOpMode {
  
  private Controller RobotController;
  

  double powerFactor;
  float leftStickY;
  float leftStickX;
  float rightStickX;

  
  /**
   * This function is executed when this Op Mode is selected from the Driver Station.
   */
  @Override
  public void runOpMode() {

   
    RobotController = new Controller(this);
    RobotController.initialize();
    
    //Initialize the variables
    leftStickX = 0;
    leftStickY = 0;
    rightStickX = 0;

    waitForStart();

    //This is the main loop. I will call the drive function on a loop until you stop the robot
    if (opModeIsActive()) {
      while (opModeIsActive()) {
        //drive();
        //Get stick inputs
        leftStickY = gamepad1.left_stick_y;
        leftStickX = -gamepad1.left_stick_x;
        rightStickX = gamepad1.right_stick_x;
        telemetry.addData("servoPosition", RobotController.claw.getPosition());
        telemetry.addData("liftPosition", RobotController.lift.getCurrentPosition());
        //claw.setPosition(1);
        RobotController.drive(leftStickY, leftStickX, rightStickX); 
        
        //Lift Control
        if (gamepad2.dpad_up){
          RobotController.liftUp();
        }
        else if (gamepad2.dpad_down){
          RobotController.liftDown();
        }
        else {
          RobotController.liftStop();
        }
        
        //claw control
        if (gamepad2.right_bumper){
          RobotController.clawClose();
        }
        if (gamepad2.left_bumper){
          RobotController.clawOpen();
        }
        
      }
    }
  }
}

