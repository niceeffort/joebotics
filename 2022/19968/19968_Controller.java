package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import com.qualcomm.robotcore.hardware.DcMotorSimple;



public class 19968_Controller {

  private DcMotor fr_rt;
  private DcMotor fr_lt;
  private DcMotor bk_lt;
  private DcMotor bk_rt;
  private Telemetry telemetry;

public void initialize(DcMotor frontRight, DcMotor frontLeft, DcMotor backRight, DcMotor backLeft, Telemetry main_telemetry) {
    fr_rt = frontRight;
    fr_lt = frontLeft;
    bk_lt = backLeft;
    bk_rt = backRight;
    
    fr_lt.setDirection(DcMotorSimple.Direction.REVERSE);
    fr_rt.setDirection(DcMotorSimple.Direction.FORWARD);
    bk_lt.setDirection(DcMotorSimple.Direction.REVERSE);
    bk_rt.setDirection(DcMotorSimple.Direction.FORWARD);
    
    telemetry = main_telemetry;
}

public void drive(float leftStickY,float leftStickX, float rightStickX) {
    float powerFrontLeft;
    float powerBackLeft;
    float powerFrontRight;
    float powerBackRight;
    
    float powerFactor = 1.0f; 
  

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
  
public void liftUp(){
    telemetry.addLine("liftUp");
}
public void liftDown(){
    telemetry.addLine("liftDown");
}

public void clawOpen(){
    telemetry.addLine("clawOpen");
}
public void clawClose(){
    telemetry.addLine("clawClose");
}
}
