package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "_19968DriverControl (Blocks to Java)")
public class _19968DriverControl extends LinearOpMode {

  private DcMotor spinner;
  private DcMotor frontright;
  private DcMotor frontleft;
  private DcMotor backleft;
  private DcMotor backright;
  private DcMotor lift;
  private CRServo spokes;

  double powerFactor;
  float leftStickY;
  int liftPosition;
  boolean upPressed;
  float leftStickX;
  float rightStickX;
  int liftIncrement;
  int liftMax;
  int liftMin;

  /**
   * Describe this function...
   */
  private void spinner2() {
    boolean RightBumper;
    boolean LeftBumper;

    RightBumper = gamepad2.right_bumper;
    LeftBumper = gamepad2.left_bumper;
    spinner.setPower(RightBumper - LeftBumper);
    telemetry.addData("LeftBumper", LeftBumper);
    telemetry.addData("RightBumper", RightBumper);
    telemetry.addData("spinnerpower", spinner.getPower());
  }

  /**
   * Describe this function...
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
    leftStickY = -gamepad1.left_stick_y;
    leftStickX = gamepad1.left_stick_x;
    rightStickX = gamepad1.right_stick_x;
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
   * This function is executed when this Op Mode is selected from the Driver Station.
   */
  @Override
  public void runOpMode() {
    boolean testRobot;

    spinner = hardwareMap.get(DcMotor.class, "spinner");
    frontright = hardwareMap.get(DcMotor.class, "front right");
    frontleft = hardwareMap.get(DcMotor.class, "front left");
    backleft = hardwareMap.get(DcMotor.class, "back left");
    backright = hardwareMap.get(DcMotor.class, "back right");
    lift = hardwareMap.get(DcMotor.class, "lift");
    spokes = hardwareMap.get(CRServo.class, "spokes");

    // Put initialization blocks here.
    // These are for the test robot.
    frontleft.setDirection(DcMotorSimple.Direction.REVERSE);
    frontright.setDirection(DcMotorSimple.Direction.FORWARD);
    backleft.setDirection(DcMotorSimple.Direction.REVERSE);
    backright.setDirection(DcMotorSimple.Direction.FORWARD);
    lift.setTargetPosition(0);
    lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    upPressed = false;
    liftPosition = 0;
    liftMax = 2400;
    liftMin = 0;
    liftIncrement = 800;
    leftStickX = 0;
    leftStickY = 0;
    rightStickX = 0;
    testRobot = false;
    powerFactor = 0.5;
    waitForStart();
    if (opModeIsActive()) {
      while (opModeIsActive()) {
        // Put loop blocks here.
        spinner2();
        spokes2();
        drive();
        lift2();
      }
    }
  }

  /**
   * Describe this function...
   */
  private void spokes2() {
    int spokePower;

    if (gamepad2.x) {
      spokePower = 1;
    } else if (gamepad2.a) {
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
  private void lift2() {
    double liftDOWN;
    boolean downPressed;

    lift.setPower(1);
    if (gamepad2.dpad_up && !upPressed) {
      liftPosition = liftPosition + liftIncrement;
      upPressed = true;
    } else if (!gamepad2.dpad_up && upPressed) {
      upPressed = false;
    } else {
    }
    if (gamepad2.dpad_down && !downPressed) {
      liftPosition = liftPosition - liftIncrement;
      downPressed = true;
    } else if (!gamepad2.dpad_down && downPressed) {
      downPressed = false;
    } else {
    }
    if (liftPosition > liftMax) {
      liftPosition = liftMax;
    } else if (liftPosition < liftMin) {
      liftPosition = liftMin;
    } else {
    }
    lift.setTargetPosition(liftPosition);
    telemetry.addData("liftPosition", liftPosition);
    telemetry.addData("lift down", liftDOWN);
  }
}
