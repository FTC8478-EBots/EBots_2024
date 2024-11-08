package org.firstinspires.ftc.teamcode.autonomous;
/*import androidx.annotation.NonNull;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.MecanumDrive;

import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

@Config
@Autonomous(name = "Yippee", group = "Autonomous")
public class Yippee extends LinearOpMode {
    public class Lift {
 //
        //       private DcMotorEx lift;

        public Lift(HardwareMap hardwareMap) {
  //          lift = hardwareMap.get(DcMotorEx.class, "liftMotor");
            //lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            //lift.setDirection(DcMotorSimple.Direction.FORWARD);
        }

        public class LiftUp implements Action {
            private boolean initialized = false;

            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                if (!initialized) {
              //      lift.setPower(0.8);
                    initialized = true;
                }

                //double pos = lift.getCurrentPosition();
                //packet.put("liftPos", pos);
                //if (pos < 3000.0) {
                    return true;
                //} else {
                  //  lift.setPower(0);
                  //  return false;
                //}
            }
        }
        public Action liftUp() {
            return new LiftUp();
        }

        public class LiftDown implements Action {
            private boolean initialized = false;

            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                if (!initialized) {
                 //  lift.setPower(-0.8);
                    initialized = true;
                }

               double pos = 0;//lift.getCurrentPosition();
                packet.put("liftPos", pos);
                if (pos > 100.0) {
                    return true;
                } else {
                  // lift.setPower(0);
                    return false;
                }
            }
        }
        public Action liftDown(){
            return new LiftDown();
        }
    }

   // public class Claw {
   //     private Servo claw;


    @Override
    public void runOpMode() {
        Pose2d initialPose = new Pose2d(11.8, 61.7, Math.toRadians(90));
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);
     //   Claw claw = new Claw(hardwareMap);
        Lift lift = new Lift(hardwareMap);

        // vision here that outputs position
        int visionOutputPosition = 1;

        TrajectoryActionBuilder tab1 = drive.actionBuilder(initialPose)
                .lineToYSplineHeading(33, Math.toRadians(0))
                .waitSeconds(2)
                .setTangent(Math.toRadians(90))
                .lineToY(48)

                .setTangent(Math.toRadians(0))
                .lineToX(32)
                .strafeTo(new Vector2d(44.5, 30))
                .turn(Math.toRadians(180))
                .lineToX(47.5)
                .waitSeconds(3);
        TrajectoryActionBuilder tab2 = drive.actionBuilder(initialPose)
                .lineToY(37)
                .setTangent(Math.toRadians(0))
                .lineToX(18)
                .waitSeconds(3)
                .setTangent(Math.toRadians(0))
                .lineToXSplineHeading(46, Math.toRadians(180))
                .waitSeconds(3);
        TrajectoryActionBuilder tab3 = drive.actionBuilder(initialPose)
                .lineToYSplineHeading(33, Math.toRadians(180))
                .waitSeconds(2)
                .strafeTo(new Vector2d(46, 30))
                .waitSeconds(3);
        Action trajectoryActionCloseOut = tab1.fresh()
                .strafeTo(new Vector2d(48, 12))
                .build();

        // actions that need to happen on init; for instance, a claw tightening.
        //Actions.runBlocking(claw.closeClaw());


        while (!isStopRequested() && !opModeIsActive()) {
            int position = visionOutputPosition;
            telemetry.addData("Position during Init", position);
            telemetry.update();
        }

        int startPosition = visionOutputPosition;
        telemetry.addData("Starting Position", startPosition);
        telemetry.update();
        waitForStart();

        if (isStopRequested()) return;

        Action trajectoryActionChosen;
        if (startPosition == 1) {
            trajectoryActionChosen = tab1.build();
        } else if (startPosition == 2) {
            trajectoryActionChosen = tab2.build();
        } else {
            trajectoryActionChosen = tab3.build();
        }

        Actions.runBlocking(
                new SequentialAction(
                        trajectoryActionChosen,
     //                   lift.liftUp(),
 //                      claw.openClaw(),
   //                    lift.liftDown(),
                        trajectoryActionCloseOut
                )
        );
    }
}

 */
import androidx.annotation.NonNull;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.components.Claw;
import org.firstinspires.ftc.teamcode.components.Elevator;
import org.firstinspires.ftc.teamcode.teleop.TestTeleop;

import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

@Config
@Autonomous(preselectTeleOp = "TestTeleop")
public class RoyalOak extends LinearOpMode {
    int red = 1;
    int left = 1;
    int seconds = 0;

    @Override
    public void runOpMode() {
        Pose2d initialPose;
        if (red == 1) {
            if (left == 1) {
            initialPose = new Pose2d(-100, -61, Math.toRadians(90));
            } else {
                initialPose = new Pose2d(-15, -61, Math.toRadians(90));
            }
        } else {
            if (left == 1) {
                initialPose = new Pose2d(100, 61, Math.toRadians(-90));
            } else {
                initialPose = new Pose2d(15, 61, Math.toRadians(-90));
            }
        }
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);
        Claw claw = Claw.getClaw(hardwareMap);
        Elevator lift = Elevator.getElevator(this);
        //Pose2d positionBeforeSample = new Pose2d(8, -34, Math.toRadians(90));

        IntoTheDeepActions intoTheDeepActions = new IntoTheDeepActions(drive, lift, claw);

        // vision here that outputs position
        TrajectoryActionBuilder tab;
if (red == 1) {
    tab = drive.actionBuilder(initialPose)
            .strafeTo(new Vector2d(48, -60));
} else {
    tab = drive.actionBuilder(initialPose)
            .strafeTo(new Vector2d(-48, 60));
}
        //TrajectoryActionBuilder tab1 = drive.actionBuilder(initialPose)

                /*.strafeTo(new Vector2d(8,-55))
                .strafeTo(new Vector2d(8,-34))
            .stopAndAdd(intoTheDeepActions.getHighSpecimenScoringAction())
            .strafeTo(new Vector2d(8, -34));*/
                //.strafeTo(new Vector2d())



                ;

        //.strafeTo(new Vector2d(-34,-50))
                //.strafeTo(new Vector2d(-64,-64))
                //.turn(Math.toRadians(135))
                //.turn(Math.toRadians(-135))
                //.strafeTo(new Vector2d(-48,-36))
                //.strafeTo(new Vector2d(-55,-55))
                //.turn(Math.toRadians(135));

        // actions that need to happen on init; for instance, a claw tightening.
        //Actions.runBlocking(claw.closeClaw());


        //telemetry.addData(":( Your PC ran into a problem and needs to restart. We're just collecting some0 error info, and then we'll restart for you. If you'd like to know more, you can search online later for this error code, AUTON_FAILURE", "");

        while (!isStarted() && !isStopRequested()) {
            boolean upPressed = false;
            boolean downPressed = false;
            if (gamepad1.dpad_up && !upPressed) {
                upPressed = true;
            } else if (!gamepad1.dpad_up && upPressed) {
                upPressed = false;
                seconds = Math.max(15, seconds+1);
            }
            if (gamepad1.dpad_down && !downPressed) {
                downPressed = true;
            } else if (!gamepad1.dpad_down && downPressed) {
                downPressed = false;
                seconds = Math.min(0, seconds-1);
            }

            if (gamepad1.a) {
                red = 1;
            }
            if (gamepad1.b) {
                red = 0;
            }
            if (gamepad1.x) {
                left = 1;
            }
            if (gamepad1.y) {
                left = 0;
            }

            telemetry.addData("a/cross is red, b/circle is blue, x/square is left, y/triangle is right, change # of seconds delay is dpad up/down", "");
            telemetry.addData("red", red);
            telemetry.addData("left", left);
            telemetry.addData("currentSelection", red == 1 ? "red" : "blue");
            telemetry.addData("" , left == 1 ? "left" : "right");
            telemetry.addData("Seconds of delay", seconds);

            telemetry.update();
        }
        waitForStart();

        if (isStopRequested()) return;
        Action trajectoryActionChosen = tab.build();
        Actions.runBlocking(trajectoryActionChosen);

        /*Actions.runBlocking(
                new SequentialAction(
                        lift.liftUp(),
                        claw.openClaw(),
                        lift.liftDown(),
                        trajectoryActionCloseOut
                )
        );*/
    }
}
