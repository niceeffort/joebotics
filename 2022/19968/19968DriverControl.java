package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "_19968DriverControl (Blocks to Java)")
public class _19968DriverControl extends LinearOpMode {

  private DcMotor frontright;
  private DcMotor frontleft;
  private DcMotor backleft;
  private DcMotor backright;

  double powerFactor;
  float leftStickY;
  float leftStickX;
  float rightStickX;

  /**
   * This function reads inputs from the joysticks and powers the wheels
   */
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
    leftStickY = -gamepad1.left_stick_y;
    leftStickX = gamepad1.left_stick_x;
    rightStickX = gamepad1.right_stick_x;

    //Set the power values based on stick input
    powerFrontLeft = leftStickY + leftStickX + rightStickX;
    powerBackLeft = leftStickY + -leftStickX + rightStickX;
    powerFrontRight = leftStickY + (-leftStickX - rightStickX);
    powerBackRight = leftStickY + (leftStickX - rightStickX);

    //Set the power to the motors
    frontright.setPower(powerFactor * powerFrontRight);
    frontleft.setPower(powerFactor * powerFrontLeft);
    backleft.setPower(powerFactor * powerBackLeft);
    backright.setPower(powerFactor * powerBackRight);

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

  /**
   * This function is executed when this Op Mode is selected from the Driver Station.
   */
  @Override
  public void runOpMode() {

    //Map the names from the configuration to the variables
    frontright = hardwareMap.get(DcMotor.class, "fr_rt");
    frontleft = hardwareMap.get(DcMotor.class, "fr_lt");
    backleft = hardwareMap.get(DcMotor.class, "bk_lt");
    backright = hardwareMap.get(DcMotor.class, "bk_rt");

    //Set the directions for the motors
    frontleft.setDirection(DcMotorSimple.Direction.REVERSE);
    frontright.setDirection(DcMotorSimple.Direction.FORWARD);
    backleft.setDirection(DcMotorSimple.Direction.REVERSE);
    backright.setDirection(DcMotorSimple.Direction.FORWARD);

    //Initialize the variables
    leftStickX = 0;
    leftStickY = 0;
    rightStickX = 0;
    powerFactor = 0.5;

    waitForStart();

    //This is the main loop. I will call the drive function on a loop until you stop the robot
    if (opModeIsActive()) {
      while (opModeIsActive()) {
        drive();
      }
    }
  }
}
