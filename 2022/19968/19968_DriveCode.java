package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import org.firstinspires.ftc.teamcode.DriveCode;

@TeleOp(name = "_19968DriverControl (Blocks to Java)")
public class 19968_DriveCode extends LinearOpMode {
  
  private DriveCode Driver;
  
  private DcMotor fr_rt;
  private DcMotor fr_lt;
  private DcMotor bk_lt;
  private DcMotor bk_rt;

  double powerFactor;
  float leftStickY;
  float leftStickX;
  float rightStickX;

  /**
   * This function reads inputs from the joysticks and powers the wheels
   
  private void drive() {
    float powerFrontLeft;
    float powerBackLeft;
    float powerFrontRight;
    float powerBackRight;

    if (gamepad1.a) {
      powerFactor = 1;
    }
    if (gamepad1.y) {
      powerFactor = 0.5;
    }

    //Get stick inputs
    leftStickY = gamepad1.left_stick_y;
    leftStickX = -gamepad1.left_stick_x;
    rightStickX = gamepad1.right_stick_x;

    //Set the power values based on stick input
    powerFrontLeft = leftStickY + leftStickX + rightStickX;
    powerBackLeft = leftStickY + -leftStickX + rightStickX;
    powerFrontRight = leftStickY + (-leftStickX - rightStickX);
    powerBackRight = leftStickY + (leftStickX - rightStickX);

    //Set the power to the motors
    fr_rt.setPower(powerFactor * powerFrontRight);
    fr_lt.setPower(powerFactor * powerFrontLeft);
    bk_lt.setPower(powerFactor * powerBackLeft);
    bk_rt.setPower(powerFactor * powerBackRight);

    //Print data to the screen
    telemetry.addData("leftStickY", leftStickY);
    telemetry.addData("leftStickX", leftStickX);
    telemetry.addData("rightStickX", rightStickX);
    telemetry.addData("powerFrontLeft", powerFrontLeft);
    telemetry.addData("powerBackLeft", powerBackLeft);
    telemetry.addData("powerFrontRight", powerFrontRight);
    telemetry.addData("powerBackRight", powerBackRight);
    telemetry.addData("powerFactor", powerFactor);
    telemetry.update();
  }
*/
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
    Driver = new DriveCode();
    Driver.initialize(fr_rt, fr_lt, bk_lt, bk_rt, telemetry);
    /*Set the directions for the motors
    fr_lt.setDirection(DcMotorSimple.Direction.REVERSE);
    fr_rt.setDirection(DcMotorSimple.Direction.FORWARD);
    bk_lt.setDirection(DcMotorSimple.Direction.REVERSE);
    bk_rt.setDirection(DcMotorSimple.Direction.FORWARD);
    */
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
        Driver.drive(leftStickY, leftStickX, rightStickX); 
        
        //Lift Control
        if (gamepad2.dpad_up){
          Driver.liftUp();
        }
        if (gamepad2.dpad_down){
          Driver.liftDown();
        }
        if (gamepad2.right_bumper){
          Driver.clawClose();
        }
        if (gamepad2.left_bumper){
          Driver.clawOpen();
        }
      }
    }
  }
}

