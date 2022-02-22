package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Autonomous(name = "_19968AutoSURed (Blocks to Java)")
public class _19968AutoSURed extends LinearOpMode {

  private DcMotor frontleft;
  private DcMotor frontright;
  private DcMotor backleft;
  private DcMotor backright;
  private DcMotor lift;
  private DcMotor spinner;
  private CRServo spokes;

  double leftStickX;
  int spokePower;
  double leftStickY;
  int rightStickX;
  double powerFactor;

  /**
   * This function is executed when this Op Mode is selected from the Driver Station.
   */
  @Override
  public void runOpMode() {
    boolean testRobot;
    int liftDOWN;
    int liftUP;

    frontleft = hardwareMap.get(DcMotor.class, "front left");
    frontright = hardwareMap.get(DcMotor.class, "front right");
    backleft = hardwareMap.get(DcMotor.class, "back left");
    backright = hardwareMap.get(DcMotor.class, "back right");
    lift = hardwareMap.get(DcMotor.class, "lift");
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
    leftStickY = 0;
    leftStickX = 0;
    rightStickX = 0;
    powerFactor = 0.5;
    liftUP = 0;
    liftDOWN = 0;
    lift.setTargetPosition(0);
    lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    waitForStart();
    if (opModeIsActive()) {
      lift.setPower(1);
      lift.setTargetPosition(400);
      leftStickY = 1;
      drive();
      sleep(50);
      leftStickY = 0;
      drive();
      leftStickX = -0.4;
      drive();
      sleep(2000);
      leftStickX = 0;
      drive();
      spinner.setPower(-1);
      sleep(4000);
      spinner.setPower(0);
      leftStickY = 1;
      drive();
      sleep(200);
      leftStickY = 0;
      drive();
      sleep(1000);
      lift.setPower(1);
      lift.setTargetPosition(0);
      spokePower = -1;
      spokes2();
      sleep(1000);
      spokePower = 0;
      spokes2();
      leftStickY = 0.5;
      drive();
      sleep(800);
      leftStickY = 0;
      drive();
      leftStickY = -0.25;
      drive();
      sleep(650);
      leftStickY = 0;
      drive();
    }
  }

  /**
   * Describe this function...
   */
  private void spinner2() {
    double RightBumper;
    double LeftBumper;

    spinner.setPower(RightBumper - LeftBumper);
    telemetry.addData("LeftBumper", LeftBumper);
    telemetry.addData("RightBumper", RightBumper);
  }

  /**
   * Describe this function...
   */
  private void drive() {
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
    spokes.setPower(spokePower);
    telemetry.addData("spokePower", spokePower);
  }

  /**
   * Describe this function...
   */
  private void lift2() {
  }
}
