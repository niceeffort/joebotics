/* Copyright (c) 2019 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

 package org.firstinspires.ftc.teamcode;

 import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.robot.Robot;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
 import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
 import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
 import java.util.List;
 import org.firstinspires.ftc.robotcore.external.ClassFactory;
 import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
 import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection;
 import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
 import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
 
 /**
  * This 2022-2023 OpMode illustrates the basics of using the TensorFlow Object Detection API to
  * determine which image is being presented to the robot.
  *
  * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
  * Remove or comment out the @Disabled line to add this OpMode to the Driver Station OpMode list.
  *
  * IMPORTANT: In order to use this OpMode, you need to obtain your own Vuforia license key as
  * is explained below.
  */
 @Autonomous(name = "Autonomous Camera")
 //@Disabled
 public class AutonomousCamera extends LinearOpMode {
    private Controller RobotController;
    float SPEED = 0.5f;
     /*
      * Specify the source for the Tensor Flow Model.
      * If the TensorFlowLite object model is included in the Robot Controller App as an "asset",
      * the OpMode must to load it using loadModelFromAsset().  However, if a team generated model
      * has been downloaded to the Robot Controller's SD FLASH memory, it must to be loaded using loadModelFromFile()
      * Here we assume it's an Asset.    Also see method initTfod() below .
      */
     //private static final String TFOD_MODEL_ASSET = "PowerPlay.tflite";
     //private static final String TFOD_MODEL_FILE  = "model_20230307_162929.tflite";
     //private static final String TFOD_MODEL_FILE  = "model_20230421_113718.tflite";
     private static final String TFOD_MODEL_FILE  = "model_20230425_194446.tflite";

     private static final String[] LABELS = {
       "plus",
       "star",
       "triangle"
     };
 
     /*
      * IMPORTANT: You need to obtain your own license key to use Vuforia. The string below with which
      * 'parameters.vuforiaLicenseKey' is initialized is for illustration only, and will not function.
      * A Vuforia 'Development' license key, can be obtained free of charge from the Vuforia developer
      * web site at https://developer.vuforia.com/license-manager.
      *
      * Vuforia license keys are always 380 characters long, and look as if they contain mostly
      * random data. As an example, here is a example of a fragment of a valid key:
      *      ... yIgIzTqZ4mWjk9wd3cZO9T1axEqzuhxoGlfOOI2dRzKS4T0hQ8kT ...
      * Once you've obtained a license key, copy the string from the Vuforia web site
      * and paste it in to your code on the next line, between the double quotes.
      */
     private static final String VUFORIA_KEY =
             "AdkdphD/////AAABmUaDyKrZGUM5qRCsBXD2PKYN32rTlDNk8RWpJBPdpM47LQuH0j4RUb/g0pdiMDQ3u5eOM91I4j6nNWGSoUMunVtrs1H5hVkN0CYHKJeXZqunLOyE/njW4P2Fe3YflKhW8c3sismnjFi+jl2rdVo2ag2me7rZ1XyyvGaZx+/4izQPrUCiAyAYGksohce7dc7L8+Tpb1qMDmhcv2Hw97sK9wCjhpN3OQb4RgX2k9zlB8JRylycxDPbUpS/UF7zf/doY602XcQN4cIeeEauf/sxb7qYZ36rMF5Yn5MVcbYNfwrNXK1K9Vm5AXJraUlxAj2RZLpJkmifMpM0M85jjfK1bjFDFSeOnt+CxO/7l93k54+P";
 
     /**
      * {@link #vuforia} is the variable we will use to store our instance of the Vuforia
      * localization engine.
      */
     private VuforiaLocalizer vuforia;
 
     /**
      * {@link #tfod} is the variable we will use to store our instance of the TensorFlow Object
      * Detection engine.
      */
     private TFObjectDetector tfod;
    
     private ElapsedTime runtime = new ElapsedTime();
    
     @Override
     public void runOpMode() {
         // The TFObjectDetector uses the camera frames from the VuforiaLocalizer, so we create that
         // first.
         initVuforia();
         initTfod();

         RobotController = new Controller(this);
         RobotController.initialize();
         float SPEED = 1.0f;

         /**
          * Activate TensorFlow Object Detection before we wait for the start command.
          * Do it here so that the Camera Stream window will have the TensorFlow annotations visible.
          **/
         if (tfod != null) {
             tfod.activate();
 
             // The TensorFlow software will scale the input images from the camera to a lower resolution.
             // This can result in lower detection accuracy at longer distances (> 55cm or 22").
             // If your target is at distance greater than 50 cm (20") you can increase the magnification value
             // to artificially zoom in to the center of image.  For best results, the "aspectRatio" argument
             // should be set to the value of the images used to create the TensorFlow Object Detection model
             // (typically 16/9).
             tfod.setZoom(1.0, 16.0/9.0);
         }
 
         /** Wait for the game to begin */
         telemetry.addData(">", "Press Play to start op mode");
         telemetry.update();
         waitForStart();

         boolean objectDetected = false; 
         boolean moveIfNotSeen = true;
         boolean moveStart = true;
         int MOVE_FORWARD_TIME = 750;
         int MOVE_SIDEWAYS_TIME = 1250;
         int MOVE_FORWARD_START_TIME = 75;
         
         
         
         
         
         if (opModeIsActive()) {
             runtime.reset();
             
             while (opModeIsActive()) {
                 if (tfod != null) {
                     // getUpdatedRecognitions() will return null if no new information is available since
                     // the last time that call was made.
                     List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
                     if (updatedRecognitions != null) {
                         telemetry.addData("# Objects Detected", updatedRecognitions.size());
                            
                         if(moveStart==true){
                             RobotController.drive(-SPEED, 0.0f, 0.0f);
                             sleep(MOVE_FORWARD_START_TIME);
                             RobotController.drive(0.0f, 0.0f, 0.0f);
                             moveStart = false;
                             }
                             
                        
                        if((runtime.seconds() > 20.0) && (objectDetected == false) && (moveIfNotSeen == true)){
                            
                            telemetry.addData("runtime",runtime.seconds());
                            RobotController.drive(-SPEED, 0.0f, 0.0f);
                            sleep(MOVE_FORWARD_TIME);
                            RobotController.drive(0.0f, 0.0f, 0.0f);
                            moveIfNotSeen = false;
                        }
                        
                         if(objectDetected==false) {
                                    telemetry.addLine("Nothing Found");
                         }
                         // step through the list of recognitions and display image position/size information for each one
                         // Note: "Image number" refers to the randomized image orientation/number
                         for (Recognition recognition : updatedRecognitions) {
                             double col = (recognition.getLeft() + recognition.getRight()) / 2 ;
                             double row = (recognition.getTop()  + recognition.getBottom()) / 2 ;
                             double width  = Math.abs(recognition.getRight() - recognition.getLeft()) ;
                             double height = Math.abs(recognition.getTop()  - recognition.getBottom()) ;
 
                             telemetry.addData(""," ");
                             telemetry.addData("Image", "%s (%.0f %% Conf.)", recognition.getLabel(), recognition.getConfidence() * 100 );
                             telemetry.addData("- Position (Row/Col)","%.0f / %.0f", row, col);
                             telemetry.addData("- Size (Width/Height)","%.0f / %.0f", width, height);
                             telemetry.addData("objectDetected",objectDetected);
                            
                             
                             
                             if(objectDetected==false){
                                String label = recognition.getLabel();
                                objectDetected = true;
                                
                                if(label == LABELS[0]) {
                                        telemetry.addLine(LABELS[0]);
                                        RobotController.drive(0.0f, SPEED, 0.0f);
                                        sleep(MOVE_SIDEWAYS_TIME);
                                        RobotController.drive(-SPEED, 0.0f, 0.0f);
                                        sleep(MOVE_FORWARD_TIME);
                                        RobotController.drive(0.0f, 0.0f, 0.0f);
                                }
                                else if(label == LABELS[1]){
                                        telemetry.addLine(LABELS[1]);
                                        RobotController.drive(-SPEED, 0.0f, 0.0f);
                                        sleep(MOVE_FORWARD_TIME);
                                        RobotController.drive(0.0f, 0.0f, 0.0f);
                                }
                                else if(label == LABELS[2]){
                                        telemetry.addLine(LABELS[2]);
                                        RobotController.drive(0.0f, -SPEED, 0.0f);
                                        sleep(MOVE_SIDEWAYS_TIME);
                                        RobotController.drive(-SPEED, 0.0f, 0.0f);
                                        sleep(MOVE_FORWARD_TIME);
                                        RobotController.drive(0.0f, 0.0f, 0.0f);
                               }
                             }
                         }
                         telemetry.update();
                     }
                 }
             }
         }
     }
 
     /**
      * Initialize the Vuforia localization engine.
      */
     private void initVuforia() {
         /*
          * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
          */
         VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();
 
         parameters.vuforiaLicenseKey = VUFORIA_KEY;
         parameters.cameraDirection = CameraDirection.BACK;
 
         //  Instantiate the Vuforia engine
         vuforia = ClassFactory.getInstance().createVuforia(parameters);
     }
 
     /**
      * Initialize the TensorFlow Object Detection engine.
      */
     private void initTfod() {
         int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
             "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
         TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
         tfodParameters.minResultConfidence = 0.75f;
         tfodParameters.isModelTensorFlow2 = true;
         tfodParameters.inputSize = 300;
         tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
 
         // Use loadModelFromAsset() if the TF Model is built in as an asset by Android Studio
         // Use loadModelFromFile() if you have downloaded a custom team model to the Robot Controller's FLASH.
         //tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABELS);
         tfod.loadModelFromFile(TFOD_MODEL_FILE, LABELS);
     }
 }
 
