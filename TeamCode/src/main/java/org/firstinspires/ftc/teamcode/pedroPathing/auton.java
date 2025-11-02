package org.firstinspires.ftc.teamcode.pedroPathing;

import com.bylazar.configurables.annotations.Configurable;
import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

@Autonomous(name = "Pedro Pathing Autonomous1", group = "Autonomous")
@Configurable // Panels
public class auton extends OpMode {

  private TelemetryManager panelsTelemetry; // Panels Telemetry instance
  public Follower follower; // Pedro Pathing follower instance
  private int pathState; // Current autonomous path state (state machine)
  private Paths paths; // Paths defined in the Paths class
  private CRServo launchTurn;
  private DcMotor leftLaunch;
  private DcMotor rightLaunch;
  private Servo launchPush;
  private DcMotor liftUp;
  private CRServo intake;
  private Servo liftPush;
  long timeSince1 = System.currentTimeMillis();
  long timeSince2;
  boolean case2 = false;
  boolean done1 = false;
  @Override
  public void init() {
    launchTurn = hardwareMap.get(CRServo.class, "launchTurn");
    leftLaunch = hardwareMap.get(DcMotor.class, "leftLaunch");
    rightLaunch = hardwareMap.get(DcMotor.class, "rightLaunch");
    launchPush = hardwareMap.get(Servo.class, "launchPush");
    liftUp = hardwareMap.get(DcMotor.class, "liftUp");
    intake = hardwareMap.get(CRServo.class, "intake");
    liftPush = hardwareMap.get(Servo.class, "liftPush");
    launchTurn.setDirection(CRServo.Direction.FORWARD);
    panelsTelemetry = PanelsTelemetry.INSTANCE.getTelemetry();

    follower = Constants.createFollower(hardwareMap);
    follower.setStartingPose(new Pose(60, 10, Math.toRadians(90)));

    paths = new Paths(follower); // Build paths

    panelsTelemetry.debug("Status", "Initialized");
    panelsTelemetry.update(telemetry);
  }

  @Override
  public void loop() {
    follower.update(); // Update Pedro Pathing
    pathState = autonomousPathUpdate(); // Update autonomous state machine

    // Log values to Panels and Driver Station
    panelsTelemetry.debug("Path State", pathState);
    panelsTelemetry.debug("X", follower.getPose().getX());
    panelsTelemetry.debug("Y", follower.getPose().getY());
    panelsTelemetry.debug("Heading", follower.getPose().getHeading());
    panelsTelemetry.update(telemetry);

  }

  public static class Paths {

    public PathChain Path1;
    public PathChain Path2;
    public PathChain Path3;

    public Paths(Follower follower) {
      Path1 = follower
        .pathBuilder()
        .addPath(
          new BezierLine(new Pose(59.072, 10.055), new Pose(38.963, 35.372))
        )
        .setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(180))
        .build();

      Path2 = follower
        .pathBuilder()
        .addPath(
          new BezierLine(new Pose(38.963, 35.372), new Pose(31.601, 35.372))
        )
        .setConstantHeadingInterpolation(Math.toRadians(180))
        .build();

      Path3 = follower
        .pathBuilder()
        .addPath(
          new BezierLine(new Pose(31.601, 35.372), new Pose(60.509, 82.953))
        )
        .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(135))
        .build();
    }
  }

  public int autonomousPathUpdate() {
    switch (pathState) {
      case 0:
        follower.followPath(paths.Path1);
        setPathState(1);

        break;
      case 1:
        if (!follower.isBusy()) {
          if (!case2) {
            intake.setPower(-1);
            timeSince1 = System.currentTimeMillis();
            case2 = true;
          }

          if (System.currentTimeMillis() >= timeSince1 + 3000 && !done1) {
            intake.setPower(0); // Stop intake
            launchPush.setPosition(0.5);
            ((DcMotorEx) liftUp).setVelocity(100); // Start the lift
            timeSince2 = System.currentTimeMillis();
            done1 = true;
          }
          if (System.currentTimeMillis() >= timeSince2 + 4600) { // Lift until optimal height (about 4.6s)
            ((DcMotorEx) liftUp).setVelocity(0);
            follower.followPath(paths.Path2, 0.5, true); //Half speed because it's a short path
            setPathState(2);
            case2 = false;
            break;
          }
        }
      case 3:
        if (!follower.isBusy()) {
          if (!case2) {
            intake.setPower(-1);
            timeSince1 = System.currentTimeMillis();
            case2 = true;
          }
          if (System.currentTimeMillis() >= timeSince1 + 3000) {
            follower.followPath(paths.Path2, 0.5, true); //Half speed because it's a short path
            setPathState(2);
            break;
          }
        }

    }
    // Add your state machine Here
    // Access paths with paths.pathName
    // Refer to the Pedro Pathing Docs (Auto Example) for an example state machine
    return pathState;
  }

  private void setPathState(int i) {
    pathState =i;
  }
}
