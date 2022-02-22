package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "_7547DriverControl (Blocks to Java)")
public class _7547DriverControl extends LinearOpMode {

  private DcMotor arm;
  private DcMotor frontleft;
  private DcMotor frontright;
  private DcMotor backleft;
  private DcMotor backright;
  private Servo armServo_Servo;
  private CRServo armServo;
  private DcMotor carousel;

  double armPower;
  double powerFactor;
  int armPosition;

  /**
   * This function is executed when this Op Mode is selected from the Driver Station.
   */
  @Override
  public void runOpMode() {
    boolean testRobot;

    arm = hardwareMap.get(DcMotor.class, "arm");
    frontleft = hardwareMap.get(DcMotor.class, "front left");
    frontright = hardwareMap.get(DcMotor.class, "front right");
    backleft = hardwareMap.get(DcMotor.class, "back left");
    backright = hardwareMap.get(DcMotor.class, "back right");
    armServo_Servo = hardwareMap.get(Servo.class, "armServo");
    armServo = hardwareMap.get(CRServo.class, "armServo");
    carousel = hardwareMap.get(DcMotor.class, "carousel");

    // Put initialization blocks here.
    // These are for the test robot.
    testRobot = false;
    powerFactor = 0.5;
    armPosition = 0;
    arm.setTargetPosition(0);
    arm.setDirection(DcMotorSimple.Direction.REVERSE);
    if (testRobot) {
      frontleft.setDirection(DcMotorSimple.Direction.FORWARD);
      frontright.setDirection(DcMotorSimple.Direction.REVERSE);
      backleft.setDirection(DcMotorSimple.Direction.REVERSE);
      backright.setDirection(DcMotorSimple.Direction.REVERSE);
    } else {
      frontleft.setDirection(DcMotorSimple.Direction.FORWARD);
      frontright.setDirection(DcMotorSimple.Direction.REVERSE);
      backleft.setDirection(DcMotorSimple.Direction.FORWARD);
      backright.setDirection(DcMotorSimple.Direction.REVERSE);
    }
    arm.setTargetPosition(0);
    arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    waitForStart();
    if (opModeIsActive()) {
      while (opModeIsActive()) {
        // Put loop blocks here.
        if (gamepad1.a) {
          powerFactor = 0.5;
        }
        if (gamepad1.b) {
          powerFactor = 1;
        }
        armMotorEncoder();
        armServo2();
        carousel2();
        driving();
        telemetry.update();
      }
    }
  }

  /**
   * Describe this function...
   */
  private void armServo2() {
    int servoPower;
    double servoPosition;

    servoPower = 2;
    if (gamepad2.x) {
      servoPosition = 0.8;
      armServo_Servo.setPosition(servoPosition);
      armServo.setPower(servoPower);
    } else if (gamepad2.y) {
      servoPosition = 0.4;
      armServo_Servo.setPosition(servoPosition);
      armServo.setPower(servoPower);
    }
    telemetry.addData("servoPosition", servoPosition);
    telemetry.addData("servoPower", servoPower);
    telemetry.addData("servoPositionActual", armServo_Servo.getPosition());
  }

  /**
   * Describe this function...
   */
  private void armMotorEncoder() {
    int armMin;
    int armMax;

    armMin = -960;
    armMax = 0;
    armPower = 0.5;
    if (gamepad2.dpad_up && armPosition <= armMax) {
      armPosition = armPosition + 10;
      arm.setTargetPosition(armPosition);
      arm.setPower(armPower);
      if (armPosition > armMax) {
        armPosition = 0;
      }
    } else if (gamepad2.dpad_down && armMin <= armPosition) {
      armPosition = armPosition - 10;
      arm.setTargetPosition(armPosition);
      arm.setPower(armPower);
      if (armPosition < armMin) {
        armPosition = -960;
      }
    }
    telemetry.addData("armPosition", armPosition);
    telemetry.addData("armPower", armPower);
  }

  /**
   * Describe this function...
   */
  private void driving() {
    float leftStickY;
    float leftStickX;
    float rightStickX;
    float powerFrontLeft;
    float powerBackLeft;
    float powerFrontRight;
    float powerBackRight;

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
    telemetry.addData("armPower", armPower);
    telemetry.addData("leftStickY", leftStickY);
    telemetry.addData("leftStickX", leftStickX);
    telemetry.addData("rightStickX", rightStickX);
    telemetry.addData("powerFrontLeft", powerFrontLeft);
    telemetry.addData("powerBackLeft", powerBackLeft);
    telemetry.addData("powerFrontRight", powerFrontRight);
    telemetry.addData("powerBackRight", powerBackRight);
    telemetry.addData("powerFactor", powerFactor);
  }

  /**
   * Describe this function...
   */
  private void carousel2() {
    double carouselPower;

    if (gamepad2.right_bumper) {
      carouselPower = 0.4;
    } else if (gamepad2.left_bumper) {
      carouselPower = -0.4;
    } else {
      carouselPower = 0;
    }
    carousel.setPower(carouselPower);
    telemetry.addData("carousel", carouselPower);
  }

  /**
   * Describe this function...
   */
  private void armMotor() {
    if (gamepad2.dpad_up) {
      armPower = 0.5;
    } else if (gamepad2.dpad_down) {
      armPower = -0.5;
    } else {
      armPower = 0;
    }
    arm.setPower(armPower);
  }
}
