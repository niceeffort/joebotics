package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

public class Controller {

  private DcMotor fr_rt;
  private DcMotor fr_lt;
  private DcMotor bk_lt;
  public DcMotor bk_rt;
  public DcMotor lift; 
  public Servo claw; 
  private ColorSensor color_sensor;
  private boolean enable = true;
  private LinearOpMode robotOpMode = null;
  static final float POWER_FACTOR = .25f; 
  static final int LIFT_MAX = -2525;
  static final int LIFT_MIN = 0;

public Controller (LinearOpMode opMode){
    robotOpMode = opMode;
}

public void initialize() {
     //Map the names from the configuration to the variables
    fr_rt = robotOpMode.hardwareMap.get(DcMotor.class, "fr_rt");
    fr_lt = robotOpMode.hardwareMap.get(DcMotor.class, "fr_lt");
    bk_lt = robotOpMode.hardwareMap.get(DcMotor.class, "bk_lt");
    bk_rt = robotOpMode.hardwareMap.get(DcMotor.class, "bk_rt");
    lift = robotOpMode.hardwareMap.get(DcMotor.class, "lift");
    claw = robotOpMode.hardwareMap.get(Servo.class, "claw");
   // color_sensor = robotOpMode.hardwareMap.get(ColorSensor.class, "color_sensor");
    
    //setting the directions for the motors
    fr_rt.setDirection(DcMotorSimple.Direction.REVERSE);
    bk_rt.setDirection(DcMotorSimple.Direction.REVERSE);
    
    //setting the lift to use encoders 
    lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    
    //color_sensor.enableLed(false);
    
    //fr_rt.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    //fr_rt.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
}

public void drive(float leftStickY,float leftStickX, float rightStickX) {
    float powerFrontLeft;
    float powerBackLeft;
    float powerFrontRight;
    float powerBackRight;
    
    //Set the power values based on stick input
    powerFrontLeft = leftStickY + -leftStickX + rightStickX;
    powerBackLeft = leftStickY + leftStickX + rightStickX;
    powerFrontRight = leftStickY + (leftStickX - rightStickX);
    powerBackRight = leftStickY + (-leftStickX - rightStickX);

    //Set the power to the motors
    if (enable==true){
        fr_rt.setPower(POWER_FACTOR * powerFrontRight);
        fr_lt.setPower(POWER_FACTOR * powerFrontLeft);
        bk_lt.setPower(POWER_FACTOR * powerBackLeft);
        bk_rt.setPower(POWER_FACTOR * powerBackRight);
    }
    else {
        fr_rt.setPower(0f);
        fr_lt.setPower(0f);
        bk_lt.setPower(0f);
        bk_rt.setPower(0f);
    }
    
    //Print data to the screen
    robotOpMode.telemetry.addData("leftStickY", leftStickY);
    robotOpMode.telemetry.addData("leftStickX", leftStickX);
    robotOpMode.telemetry.addData("rightStickX", rightStickX);
    robotOpMode.telemetry.addData("powerFrontLeft", powerFrontLeft);
    robotOpMode.telemetry.addData("powerBackLeft", powerBackLeft);
    robotOpMode.telemetry.addData("powerFrontRight", powerFrontRight);
    robotOpMode.telemetry.addData("powerBackRight", powerBackRight);
    robotOpMode.telemetry.update();
  }
  
public void liftUp(){
    robotOpMode.telemetry.addLine("liftUp");
  runLift(LIFT_MAX);
} 
public void liftDown(){
    robotOpMode.telemetry.addLine("liftDown");
    runLift(LIFT_MIN);
}
public void liftStop(){
    robotOpMode.telemetry.addLine("liftStop");
    lift.setPower(0);
}

private void runLift(int position){
    lift.setTargetPosition(position);
    lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    lift.setPower(1);
}

public void clawOpen(){
    robotOpMode.telemetry.addLine("clawOpen");
    claw.setPosition(0.1);
}
public void clawClose(){
    robotOpMode.telemetry.addLine("clawClose");
    claw.setPosition(0);
}

public void toggleEnable(){
    enable = !enable;
}

    //color sensor 
 public void color(){
  //   color_sensor.enableLed(false);
     int red = 0;
     red = color_sensor.red(); 
     robotOpMode.telemetry.addData("color: ", red);
 }
}
