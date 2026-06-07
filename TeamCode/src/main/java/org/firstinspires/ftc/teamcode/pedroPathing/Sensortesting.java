package org.firstinspires.ftc.teamcode.pedroPathing;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;

@TeleOp(name="Test Color Sensors")
public class Sensortesting extends LinearOpMode {

    @Override
    public void runOpMode() {
        ColorSensor sensorLauncher;
        ColorSensor sensorIntake;
        ColorSensor sensorLiftTop;
        sensorLauncher = hardwareMap.get(ColorSensor.class, "csensorLauncher");
        sensorIntake = hardwareMap.get(ColorSensor.class, "sensorIntake");
        sensorLiftTop = hardwareMap.get(ColorSensor.class, "sensorLift");
        waitForStart();
        while(opModeIsActive()) {
            telemetry.addData("sensorLiftTop R", sensorLiftTop.red());
            telemetry.addData("sensorLiftTop G", sensorLiftTop.green());
            telemetry.addData("sensorLiftTop B", sensorLiftTop.blue());
            telemetry.addData("sensorIntake R", sensorIntake.red());
            telemetry.addData("sensorIntake G", sensorIntake.green());
            telemetry.addData("sensorIntake B", sensorIntake.blue());
            telemetry.addData("sensorLauncher R", sensorLauncher.red());
            telemetry.addData("sensorLauncher G", sensorLauncher.green());
            telemetry.addData("sensorLauncher B", sensorLauncher.blue());
            telemetry.update();
        }
    }
}
