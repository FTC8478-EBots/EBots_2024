package org.firstinspires.ftc.teamcode.teleop;




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
            /*drive.setDrivePowers(
                    new PoseVelocity2d(-gamepad1.left_stick_y, -gamepad1.left_stick_x, -gamepad1.right_stick_x)
            );*/
            telemetry.addData("Joystick 1 leftX", gamepad1.left_stick_x);
            telemetry.update();
            if (gamepad2.dpad_right) {
                claw.open();
                telemetry.update();
            }
            if (gamepad2.dpad_left){
                claw.close();
                telemetry.update();
                }

        /*    if (gamepad2.square) {
                //Grabber.open
                telemetry.addData("opening pixel grabber, 2square", "");
                telemetry.update();
            }
            if (gamepad2.triangle) {
                //Airplanelauncher.launch
                telemetry.addData("launching airplane, 2triangle", "");
                telemetry.update();


            }
            if (gamepad2.dpad_up) {
                //Lift.setHeight(300);
                telemetry.addData("raising pixel arm, 2up", "");
                telemetry.update();
            }
            if (gamepad2.dpad_down) {
                telemetry.addData("lowering pixel arm, 2down", "");
                telemetry.update();
                //PixelArm.lower
            }
            if (gamepad1.circle) {
                //HangArm.hang
                telemetry.addData("hanging, 1circle","");
                telemetry.update();

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
/*            drive.update();
            Pose2d poseEstimate = drive.getPoseEstimate();
            telemetry.addData("x", poseEstimate.getX());
            telemetry.addData("y", poseEstimate.getY());
            telemetry.addData("heading", poseEstimate.getHeading());*/
            telemetry.update();
        }
    }
}

