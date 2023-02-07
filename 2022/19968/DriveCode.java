package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.robot.Robot;
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
  private boolean gamepad2_aPressed = false;

  
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
        //telemetry.addData("liftPosition", RobotController.lift.getCurrentPosition());
        //claw.setPosition(1);
        RobotController.drive(leftStickY, leftStickX, rightStickX); 
        
        telemetry.addData("GamePad2 Up:", gamepad2.dpad_up);
        telemetry.addData("GamePad2 Down:", gamepad2.dpad_down);

        //Lift Control
        if (gamepad2.dpad_up){
          telemetry.addLine("liftUp_Driver");
          RobotController.liftUp();
        }
        else if (gamepad2.dpad_down){
          telemetry.addLine("liftDown_Driver");
          RobotController.liftDown();
        }
        else {
          RobotController.liftStop();
        }
        
        telemetry.addData("liftPower", RobotController.lift.getPower());
        
        //claw control
        if (gamepad2.right_bumper){
          RobotController.clawClose();
        }
        if (gamepad2.left_bumper){
          RobotController.clawOpen();
        }
        
        if (gamepad2.a && gamepad2_aPressed==false){
          RobotController.toggleEnable();
          gamepad2_aPressed = true;
        }
        else if (gamepad2.a == false){
          gamepad2_aPressed = false;
        }
        
        //telemetry.addData("desiredLiftPos", RobotController.desiredLiftPos);
        telemetry.addData("currentLiftPos", RobotController.lift.getCurrentPosition());
        
        //RobotController.color();

        telemetry.addData("BackRightPos", RobotController.bk_rt.getCurrentPosition());
        
      }
    }
  }
}
