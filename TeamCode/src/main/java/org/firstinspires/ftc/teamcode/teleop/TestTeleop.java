package org.firstinspires.ftc.teamcode.teleop;

import static org.firstinspires.ftc.teamcode.constants.Common.Rotate;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
//import com.acmerobotics.roadrunner.ftc.
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.autonomous.IntoTheDeepActions;
import org.firstinspires.ftc.teamcode.components.Claw;
import org.firstinspires.ftc.teamcode.components.Elevator;


//@Disabled

@TeleOp(group = "drive")
public class TestTeleop extends LinearOpMode {

    Claw claw;
    Elevator elevator;
    boolean gamepad2_prev_dpad_up = false;
    boolean gamepad2_prev_dpad_down = false;
    @Override
    public void runOpMode() throws InterruptedException {
        MecanumDrive drive = new MecanumDrive(hardwareMap, new Pose2d(new Vector2d(0, 0), 0));
        claw = new Claw(hardwareMap);
        elevator = new Elevator();
        IntoTheDeepActions intoTheDeepActions = new IntoTheDeepActions(drive,elevator,claw);
        elevator.init(this);
        claw.init(this);
        Telemetry telemetry = new MultipleTelemetry(this.telemetry, FtcDashboard.getInstance().getTelemetry());
        elevator.updateTelemetry();
        telemetry.update();
//        drive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        waitForStart();
        while (!isStopRequested()) {
            drive.updatePoseEstimate();
            Pose2d poseEstimate = drive.pose;
            drive.setDrivePowers(
                    new PoseVelocity2d(Rotate(new Vector2d(-gamepad1.left_stick_y*getDriveSpeedScaling(), -gamepad1.left_stick_x*getDriveSpeedScaling()),-drive.pose.heading.toDouble()), -gamepad1.right_stick_x*getDriveSpeedScaling())
            );
            telemetry.addData("Joystick 1 leftX", gamepad1.left_stick_x);

            if (gamepad2.dpad_right) {
                claw.down();

              //  Actions.runBlocking(claw.openAction());
            }
            if (gamepad2.dpad_left){
                claw.up();

                }

            if (gamepad2.cross) {
                 Actions.runBlocking(claw.openAction());
            }
            if (gamepad2.circle){
                claw.close();

            }

            if (gamepad2.dpad_up & !gamepad2_prev_dpad_up) {
                elevator.increaseHeight();
                gamepad2_prev_dpad_up = true;
            }
            if (!gamepad2.dpad_up) {
                gamepad2_prev_dpad_up = false;

            }

            if (gamepad2.dpad_down & !gamepad2_prev_dpad_down) {
                elevator.decreaseHeight();
                gamepad2_prev_dpad_down = true;
            }
            if (!gamepad2.dpad_down) {
                gamepad2_prev_dpad_down = false;

            }


        if (gamepad2.triangle) {


            //claw.scoreHighspecimen
            Actions.runBlocking(
                    intoTheDeepActions.getHighSpecimenScoringAction()
            );
        }



            // else {
                //claw.stop();
            //}
          /*  if ((gamepad2.left_stick_y)>.3) {
                //variable = (Variable + 1)
                elevator.moveToPosition(1600);
            } else if ((gamepad2.left_stick_y)<-.3) {
                elevator.moveToPosition(0);
            }
            */

        /*    if (gamepad2.square) {
                //Grabber.open
                telemetry.addData("opening pixel grabber, 2square", "");

            }
            if (gamepad2.triangle) {
                //Airplanelauncher.launch
                telemetry.addData("launching airplane, 2triangle", "");



            }
            if (gamepad2.dpad_up) {
                //Lift.setHeight(300);
                telemetry.addData("raising pixel arm, 2up", "");

            }
            if (gamepad2.dpad_down) {
                telemetry.addData("lowering pixel arm, 2down", "");

                //PixelArm.lower
            }
            if (gamepad1.circle) {
                //HangArm.hang
                telemetry.addData("hanging, 1circle","");


            }
            if gamepad2.Circle pressed:
            Close grabber
            if gamepad2.Square pressed:
            Open grabber
            if gamepad2.Triangle pressed:
            Launch airplane

            PixelArm.setWeightedDrivePower(
                    new Pose2d(-gamepad2.left_stick_y, -gamepad2.left_stick_x, -gamepad2.right_stick_x)
            );

            */

            telemetry.addData("x", poseEstimate.position.x);
            telemetry.addData("y", poseEstimate.position.y);
            telemetry.addData("heading", drive.pose.heading.toDouble());
            claw.updateTelemetry(telemetry);
            telemetry.update();
            elevator.updateTelemetry();

        }
    }
    double getDriveSpeedScaling(){
        return Math.min(
                claw.driveSpeedScaling(), elevator.getDriveSpeedScaling()
        );
    }
}

//drive.setDrivePowers(new PoseVelocity2d(new Vector2d(gamepad1.left_stick_x, gamepad1.left_stick_y), gamepad1.right_stick_x == 0 ? 0 : Math.atan(gamepad1.right_stick_y/gamepad1.right_stick_x)));
//the magical fix to driving?????????

