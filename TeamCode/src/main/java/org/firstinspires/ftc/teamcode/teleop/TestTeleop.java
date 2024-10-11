package org.firstinspires.ftc.teamcode.teleop;




import static org.firstinspires.ftc.teamcode.constants.Common.Rotate;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
//import com.acmerobotics.roadrunner.ftc.
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.components.Claw;


//@Disabled

@TeleOp(group = "drive")
public class TestTeleop extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        MecanumDrive drive = new MecanumDrive(hardwareMap, new Pose2d(new Vector2d(0, 0), 0));
        Claw claw = new Claw(hardwareMap);
        Telemetry telemetry = new MultipleTelemetry(this.telemetry, FtcDashboard.getInstance().getTelemetry());
//        drive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        waitForStart();
        while (!isStopRequested()) {
            drive.updatePoseEstimate();
            Pose2d poseEstimate = drive.pose;
            drive.setDrivePowers(
                    new PoseVelocity2d(Rotate(new Vector2d(-gamepad1.left_stick_y, -gamepad1.left_stick_x),0),

                            -gamepad1.right_stick_x)
            );
            telemetry.addData("Joystick 1 leftX", gamepad1.left_stick_x);

            if (gamepad2.dpad_right) {
                claw.open();

            }
            if (gamepad2.dpad_left){
                claw.close();

                }
            if (gamepad2.dpad_up) {
                claw.up();

            }
            if (gamepad2.dpad_down){
                claw.down();

            }
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
            telemetry.addData("heading", poseEstimate.heading.real);
            claw.updateTelemetry(telemetry);
            telemetry.update();
        }
    }
}

//drive.setDrivePowers(new PoseVelocity2d(new Vector2d(gamepad1.left_stick_x, gamepad1.left_stick_y), gamepad1.right_stick_x == 0 ? 0 : Math.atan(gamepad1.right_stick_y/gamepad1.right_stick_x)));
//the magical fix to driving?????????