package org.firstinspires.ftc.teamcode.pedroPathing;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "test92923 (Blocks to Java)")
public class mechanism extends LinearOpMode {

    private CRServo launchTurn;
    private DcMotor leftLaunch;
    private DcMotor rightLaunch;
    private Servo launchPush;
    private DcMotor liftUp;
    private CRServo intake;
    private Servo liftPush;
    private ColorSensor sensorLauncher;

    /**
     * This sample contains the bare minimum Blocks for any regular OpMode. The 3 blue
     * Comment Blocks show where to place Initialization code (runs once, after touching the
     * DS INIT button, and before touching the DS Start arrow), Run code (runs once, after
     * touching Start), and Loop code (runs repeatedly while the OpMode is active, namely not
     * Stopped).
     */
    @Override
    public void runOpMode() {
        double configSpeedInt = 0.5;
        boolean motorMenuActivated;
        String configSpeedTxt;
        long timeSince3;
        long timeSince;
        long timeSinceMotorSpeedMenuActivate;
        long timeSince2 = 0;

        launchTurn = hardwareMap.get(CRServo.class, "launchTurn");
        leftLaunch = hardwareMap.get(DcMotor.class, "leftLaunch");
        rightLaunch = hardwareMap.get(DcMotor.class, "rightLaunch");
        launchPush = hardwareMap.get(Servo.class, "launchPush");
        liftUp = hardwareMap.get(DcMotor.class, "liftUp");
        intake = hardwareMap.get(CRServo.class, "intake");
        liftPush = hardwareMap.get(Servo.class, "liftPush");
        sensorLauncher = hardwareMap.get(ColorSensor.class, "sensorLauncher");

        // Put initialization blocks here.
        launchTurn.setDirection(CRServo.Direction.FORWARD);
        waitForStart();
        configSpeedTxt = "What speed? Press B to confirm and use dpad up/down to select";
        configSpeedInt = 1;
        if (opModeIsActive()) {
            // Put run blocks here.
            // Get the current time in milliseconds. The value returned represents
            // the number of milliseconds since midnight, January 1, 1970 UTC.
            timeSince3 = System.currentTimeMillis();
            while (opModeIsActive()) {
                // Put loop blocks here.
                telemetry.update();
                // Get the current time in milliseconds. The value returned represents
                // the number of milliseconds since midnight, January 1, 1970 UTC.
                timeSince = System.currentTimeMillis();
                if (gamepad1.b) {
                    motorMenuActivated = true;
                    // Get the current time in milliseconds. The value returned represents
                    // the number of milliseconds since midnight, January 1, 1970 UTC.
                    timeSinceMotorSpeedMenuActivate = System.currentTimeMillis();
                    while (opModeIsActive()) {
                        telemetry.addData(configSpeedTxt, configSpeedInt);
                        telemetry.addData("Tip", "Press X to exit");
                        // Get the current time in milliseconds. The value returned represents
                        // the number of milliseconds since midnight, January 1, 1970 UTC.
                        // Get the current time in milliseconds. The value returned represents
                        // the number of milliseconds since midnight, January 1, 1970 UTC.
                        if (gamepad1.dpad_up && System.currentTimeMillis() >= timeSince + 200 && !(configSpeedInt >= 1)) {
                            // Get the current time in milliseconds. The value returned represents
                            // the number of milliseconds since midnight, January 1, 1970 UTC.
                            timeSince = System.currentTimeMillis();
                            configSpeedInt += 0.1;
                        } else if (gamepad1.dpad_down && System.currentTimeMillis() >= timeSince + 200 && !(configSpeedInt <= -1)) {
                            // Get the current time in milliseconds. The value returned represents
                            // the number of milliseconds since midnight, January 1, 1970 UTC.
                            timeSince = System.currentTimeMillis();
                            configSpeedInt -= 0.1;
                        }
                        // Get the current time in milliseconds. The value returned represents
                        // the number of milliseconds since midnight, January 1, 1970 UTC.
                        if (gamepad1.b && System.currentTimeMillis() >= timeSinceMotorSpeedMenuActivate + 500) {
                            while (true) {
                                ((DcMotorEx) leftLaunch).setVelocity(-configSpeedInt * 1500);
                                ((DcMotorEx) rightLaunch).setVelocity(configSpeedInt * 1500);
                                if (gamepad1.right_bumper) {
                                    sleep(1000);
                                    // 1 is pushed to launch, 0 is open for pushing into launcher
                                    launchPush.setPosition(1);
                                    sleep(700);
                                    launchPush.setPosition(0.5);
                                } else if (gamepad1.x) {
                                    leftLaunch.setPower(0);
                                    rightLaunch.setPower(0);
                                    break;
                                }
                            }
                            break;
                        } else if (gamepad1.x) {
                            telemetry.update();
                            break;
                        }
                        telemetry.update();
                    }
                }
                // Get the current time in milliseconds. The value returned represents
                // the number of milliseconds since midnight, January 1, 1970 UTC.
                
                // Get the current time in milliseconds. The value returned represents
                // the number of milliseconds since midnight, January 1, 1970 UTC.
                // Get the current time in milliseconds. The value returned represents
                // the number of milliseconds since midnight, January 1, 1970 UTC.
                if (gamepad1.a && System.currentTimeMillis() >= 600 + timeSince3) {
                    // Get the current time in milliseconds. The value returned represents
                    // the number of milliseconds since midnight, January 1, 1970 UTC.
                    timeSince3 = System.currentTimeMillis();
                    launchTurn.setPower(0.5);
                    // Get the current time in milliseconds. The value returned represents
                    // the number of milliseconds since midnight, January 1, 1970 UTC.
                    timeSince2 = System.currentTimeMillis();
                } else if (gamepad1.y && System.currentTimeMillis() >= 600 + timeSince3) {
                    // Get the current time in milliseconds. The value returned represents
                    // the number of milliseconds since midnight, January 1, 1970 UTC.
                    timeSince3 = System.currentTimeMillis();
                    launchTurn.setPower(-0.5);
                    // Get the current time in milliseconds. The value returned represents
                    // the number of milliseconds since midnight, January 1, 1970 UTC.
                    timeSince2 = System.currentTimeMillis();
                }
                if (System.currentTimeMillis() >= 100 + timeSince2) {
                    launchTurn.setPower(0);
                }
                while (gamepad1.dpad_up) {
                    launchPush.setPosition(0.5);
                    ((DcMotorEx) liftUp).setVelocity(100);
                }
                ((DcMotorEx) liftUp).setVelocity(0);
                while (gamepad1.dpad_down) {
                    launchPush.setPosition(0.5);
                    ((DcMotorEx) liftUp).setVelocity(-100);
                }
                ((DcMotorEx) liftUp).setVelocity(0);
                while (gamepad1.dpad_left) {
                    intake.setPower(-1);
                }
                intake.setPower(0);
                while (gamepad1.dpad_right) {
                    launchPush.setPosition(0.4);
                    liftPush.setPosition(0);
                }
                liftPush.setPosition(0.7);
            }
        }
    }
}