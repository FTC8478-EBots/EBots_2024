package org.firstinspires.ftc.teamcode.autonomous;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.components.Claw;
import org.firstinspires.ftc.teamcode.components.Elevator;
import org.firstinspires.ftc.teamcode.components.Hang;
import org.firstinspires.ftc.teamcode.constants.IntoTheDeep;

@Config
@Autonomous(preselectTeleOp = "TestTeleop")
public class RoyalOak extends LinearOpMode {
    int red = 1;
    int left = 1;
    int seconds = 0;

    public static double distForward = 32.75;
    public static double distRight = 24;
    public static double distPark = 48;
    public static double distForwardforSamples = 55;
    /*public Pose2d getInitialPose(int color, int side) {
        if (color == 1) {
            if (side == 1) {
                return new Pose2d(-100, -61, Math.toRadians(90));
            } else {
                return new Pose2d(-15, -61, Math.toRadians(90));
            }
        } else {
            if (side == 1) {
                return new Pose2d(100, 61, Math.toRadians(-90));
            } else {
                return new Pose2d(15, 61, Math.toRadians(-90));
            }
        }
    }*/
    @Override
    public void runOpMode() {
        //assume redRight
        Pose2d initialPose = new Pose2d(0, 0, Math.toRadians(90));

        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);
        Claw claw = Claw.getClaw(hardwareMap);
        Hang hang = new Hang(hardwareMap);
        claw.init(this);
        claw.close();
        Elevator lift = Elevator.getElevator(this);

        left = determineAreWeLeft();
        //Pose2d positionBeforeSample = new Pose2d(8, -34, Math.toRadians(90));
        //Jedidiah is really good at coding and is a coding star.
//you are in a dark room, what will you do?
        //i turn the lights on :)
        //you go blind... forever :(
        IntoTheDeepActions intoTheDeepActions = new IntoTheDeepActions(drive, lift, claw, hang);
        // vision here that outputs position
        TrajectoryActionBuilder tabLeft = drive.actionBuilder(initialPose)
                .stopAndAdd(new SequentialAction(claw.closeAction()))
                .stopAndAdd(new SequentialAction(lift.moveToPositionAction(IntoTheDeep.ElevatorHeight.SpecimenReadyToBeCliped)))
                .strafeTo(new Vector2d(0, distForward))
                .stopAndAdd(new SleepAction(1))
                .stopAndAdd(new SequentialAction(lift.moveToPositionAction(IntoTheDeep.ElevatorHeight.SpecimenClipedOnHighb)))
                .stopAndAdd(new SleepAction( 1))
                .stopAndAdd(new SequentialAction(claw.openAction()))
                .strafeTo(new Vector2d(0,0))
                .strafeTo(new Vector2d(-36,10))
                .stopAndAdd(new SequentialAction(claw.downAction()))
                //.stopAndAdd(new SequentialAction(lift.moveToPositionAction()))
                .stopAndAdd(new SequentialAction(claw.closeAction()))
                .stopAndAdd(new SequentialAction(claw.upAction()))
                .strafeToLinearHeading(new Vector2d(-48,12),Math.toRadians(135))//Basket position
                ;
                //isaac is a software star and will always be :D
                // Jake is a software star and always will be because he is software
        ;
        TrajectoryActionBuilder tabRight= drive.actionBuilder(initialPose)
                .stopAndAdd(new SequentialAction(claw.closeAction()))
                .stopAndAdd(new SequentialAction(lift.moveToPositionAction(IntoTheDeep.ElevatorHeight.SpecimenReadyToBeCliped)))
                .strafeTo(new Vector2d(0, distForward))
                .stopAndAdd(new SleepAction(1))
                .stopAndAdd(new SequentialAction(lift.moveToPositionAction(IntoTheDeep.ElevatorHeight.SpecimenClipedOnHighb)))
                .stopAndAdd(new SleepAction( 1))
                .stopAndAdd(new SequentialAction(claw.openAction()))
                .strafeTo(new Vector2d(0,0))
                .strafeTo(new Vector2d(distRight,0))
                .strafeTo(new Vector2d(distRight,distForwardforSamples))
                .strafeTo(new Vector2d(34,distForwardforSamples))
                .strafeTo(new Vector2d(34, 0))
                .strafeTo(new Vector2d(34,distForwardforSamples))
                .strafeTo(new Vector2d(43,distForwardforSamples))
                .strafeTo(new Vector2d(43,0))
                //.strafeTo(new Vector2d(45,50))
                //.strafeTo(new Vector2d(54,50))
                .strafeTo(new Vector2d(distPark,0));//go to parking space
                /*.stopAndAdd(new SequentialAction(lift.moveToPositionAction(IntoTheDeep.ElevatorHeight.SpecimenReadyToBeCliped)))
                .strafeTo(new Vector2d(-15, distForward))
                .stopAndAdd(new SleepAction(1))
                .stopAndAdd(new SequentialAction(lift.moveToPositionAction(IntoTheDeep.ElevatorHeight.SpecimenClipedOnHighb)))
                .stopAndAdd(new SequentialAction(claw.openAction()))

                .strafeTo(new Vector2d(12, -60))*/
                //.strafeTo(new Vector2d(78, -60));

        waitForStart();

        if (isStopRequested()) return;
        Action trajectoryActionChosen;
        if (left == 1){
            trajectoryActionChosen = tabLeft.build();
        } else{
            trajectoryActionChosen = tabRight.build();
        }
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

    private int determineAreWeLeft() {
        int currentlyLeft = 0;
        while (!gamepad1.cross && !isStopRequested()) {
            telemetry.addLine("if right click right bumper");
            telemetry.addLine("if left click left bumper");
            telemetry.addLine("click X to confirm");

            if (currentlyLeft == 1)
                telemetry.addLine("currently set left.");
            else
                telemetry.addLine("currently set right.");
            telemetry.update();

            if (gamepad1.right_bumper)
                currentlyLeft = 0;
            if (gamepad1.left_bumper)
                currentlyLeft = 1;

        }

        telemetry.addLine("Ready to run Auton ");
        if (currentlyLeft == 1)
            telemetry.addLine(" left.");
        else
            telemetry.addLine(" right.");
        telemetry.update();

        return currentlyLeft;
    }
}