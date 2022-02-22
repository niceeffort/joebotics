package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Autonomous(name = "_19968AutoWarehouse (Blocks to Java)")
public class _19968AutoWarehouse extends LinearOpMode {

  private DcMotor frontleft;
  private DcMotor frontright;
  private DcMotor backleft;
  private DcMotor backright;
  private DcMotor spinner;
  private CRServo spokes;

  double powerFactor;
  int leftStickY;

  /**
   * This function is executed when this Op Mode is selected from the Driver Station.
   */
  @Override
  public void runOpMode() {
    boolean testRobot;

    frontleft = hardwareMap.get(DcMotor.class, "front left");
    frontright = hardwareMap.get(DcMotor.class, "front right");
    backleft = hardwareMap.get(DcMotor.class, "back left");
    backright = hardwareMap.get(DcMotor.class, "back right");
    spinner = hardwareMap.get(DcMotor.class, "spinner");
    spokes = hardwareMap.get(CRServo.class, "spokes");

    // Put initialization blocks here.
    // These are for the test robot.
    testRobot = false;
    if (testRobot) {
      frontleft.setDirection(DcMotorSimple.Direction.FORWARD);
      frontright.setDirection(DcMotorSimple.Direction.REVERSE);
      backleft.setDirection(DcMotorSimple.Direction.REVERSE);
      backright.setDirection(DcMotorSimple.Direction.REVERSE);
    } else {
      frontleft.setDirection(DcMotorSimple.Direction.REVERSE);
      frontright.setDirection(DcMotorSimple.Direction.FORWARD);
      backleft.setDirection(DcMotorSimple.Direction.REVERSE);
      backright.setDirection(DcMotorSimple.Direction.FORWARD);
    }
    powerFactor = 0.5;
    waitForStart();
    if (opModeIsActive()) {
      leftStickY = 0;
      drive();
      sleep(1000);
      leftStickY = 0;
      drive();
    }
  }

  /**
   * Describe this function...
   */
  private void spinner2() {
    boolean RightBumper;
    boolean LeftBumper;

    RightBumper = gamepad1.right_bumper;
    LeftBumper = gamepad1.left_bumper;
    spinner.setPower(RightBumper - LeftBumper);
    telemetry.addData("LeftBumper", LeftBumper);
    telemetry.addData("RightBumper", RightBumper);
  }

  /**
   * Describe this function...
   */
  private void drive() {
    double leftStickX;
    double rightStickX;
    double powerFrontLeft;
    double powerBackLeft;
    double powerFrontRight;
    double powerBackRight;

    powerFrontLeft = leftStickY + leftStickX + rightStickX;
    powerBackLeft = leftStickY + -leftStickX + rightStickX;
    powerFrontRight = leftStickY + (-leftStickX - rightStickX);
    powerBackRight = leftStickY + (leftStickX - rightStickX);
    frontright.setPower(powerFactor * powerFrontRight);
    frontleft.setPower(powerFactor * powerFrontLeft);
    backleft.setPower(powerFactor * powerBackLeft);
    backright.setPower(powerFactor * powerBackRight);
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
   * Describe this function...
   */
  private void spokes2() {
    int spokePower;

    if (gamepad2.left_bumper) {
      spokePower = 1;
    } else if (gamepad2.right_bumper) {
      spokePower = -1;
    } else {
      spokePower = 0;
    }
    spokes.setPower(spokePower);
    telemetry.addData("spokePower", spokePower);
  }

  /**
   * Describe this function...
   */
  private void lift() {
  }
}
