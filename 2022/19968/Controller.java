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
  private DcMotor bk_rt;
  public DcMotor lift; 
  public Servo claw; 
  private ColorSensor color_sensor;
  private LinearOpMode robotOpMode = null; 
  private ElapsedTime runtime = new ElapsedTime();
  static final int LIFT_SPEED = 200; 
  static final int LIFT_MAX = 0;
  static final int LIFT_MIN = -1900;

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
    color_sensor = robotOpMode.hardwareMap.get(ColorSensor.class, "color_sensor");
    
    //setting the directions for the motors
    fr_rt.setDirection(DcMotorSimple.Direction.REVERSE);
    bk_rt.setDirection(DcMotorSimple.Direction.REVERSE);
    
    //setting the lift to use encoders 
    lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    
    color_sensor.enableLed(false);
    
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
    robotOpMode.telemetry.addData("leftStickY", leftStickY);
    robotOpMode.telemetry.addData("leftStickX", leftStickX);
    robotOpMode.telemetry.addData("rightStickX", rightStickX);
    robotOpMode.telemetry.addData("powerFrontLeft", powerFrontLeft);
    robotOpMode.telemetry.addData("powerBackLeft", powerBackLeft);
    robotOpMode.telemetry.addData("powerFrontRight", powerFrontRight);
    robotOpMode.telemetry.addData("powerBackRight", powerBackRight);
    robotOpMode.telemetry.addData("powerFactor", powerFactor);
    robotOpMode.telemetry.update();
  }
  
public void liftUp(){
    robotOpMode.telemetry.addLine("liftUp");
    runLift(-LIFT_SPEED);
}
public void liftDown(){
    robotOpMode.telemetry.addLine("liftDown");
    runLift(LIFT_SPEED);
}
public void liftStop(){
    robotOpMode.telemetry.addLine("liftStop");
    lift.setPower(0);
}

private void runLift(int position){
    int currentPos = lift.getCurrentPosition();
    int newPosition = currentPos + position;
    double startTime = runtime.seconds();
    double elapsedTime = 0.0;
    
    if (newPosition > LIFT_MIN && newPosition < LIFT_MAX){
        
        lift.setTargetPosition(currentPos + position);
        lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lift.setPower(1);
        while (lift.isBusy() || elapsedTime < 2.0){
            elapsedTime = runtime.seconds() - startTime;
        };
        lift.setPower(0);
    }
};

public void clawOpen(){
    robotOpMode.telemetry.addLine("clawOpen");
    claw.setPosition(0.3);
}
public void clawClose(){
    robotOpMode.telemetry.addLine("clawClose");
    claw.setPosition(0);
}

    //color sensor 
 public void color(){
     color_sensor.enableLed(false);
     int red = 0;
     red = color_sensor.red(); 
     robotOpMode.telemetry.addData("color: ", red);
 }
}
