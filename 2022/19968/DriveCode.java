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
  
  private DcMotor fr_rt;
  private DcMotor fr_lt;
  private DcMotor bk_lt;
  private DcMotor bk_rt;
  private DcMotor lift;
  private Servo claw;

  double powerFactor;
  float leftStickY;
  float leftStickX;
  float rightStickX;

  
  /**
   * This function is executed when this Op Mode is selected from the Driver Station.
   */
  @Override
  public void runOpMode() {

    //Map the names from the configuration to the variables
    fr_rt = hardwareMap.get(DcMotor.class, "fr_rt");
    fr_lt = hardwareMap.get(DcMotor.class, "fr_lt");
    bk_lt = hardwareMap.get(DcMotor.class, "bk_lt");
    bk_rt = hardwareMap.get(DcMotor.class, "bk_rt");
    lift = hardwareMap.get(DcMotor.class, "lift");
    claw = hardwareMap.get(Servo.class, "claw");
    RobotController = new Controller();
    RobotController.initialize(fr_rt, fr_lt, bk_lt, bk_rt, lift, claw, telemetry);
    
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
        telemetry.addData("servoPosition", claw.getPosition());
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

