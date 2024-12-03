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
    public static double distForwardforSamples = 58;
    public static double distForwardforYellowSamples1 = 23.5;
    public static double distForwardforYellowSamples2 = 23.5;
    public static double distLeftforYellowSamples1 = 37;
    public static double distLeftforYellowSamples2 = 48.5;

    public static double YELLOW_SAMPLE_1_X = -49.5;
    public static double YELLOW_SAMPLE_2_X = -59.5;
    public static double YELLOW_SAMPLE_3_X = -53;

    public static double YELLOW_SAMPLE_1_Y = -45.5;
    public static double YELLOW_SAMPLE_2_Y = -45.5;
    public static double YELLOW_SAMPLE_3_Y = -26;
    public static double YELLOW_SAMPLE_3_HEADING = Math.toRadians(180);


    public static double BASKET_X = -60;
    public static double BASKET_Y = -60;
    public static double LEFT_PARK_X = -24;
    public static double LEFT_PARK_Y = 0;
    public static double RED_LEFT_START_X = -39.5;
    public static double RED_LEFT_START_Y = -63.25;
   public static double RED_LEFT_START_HEADING =  Math.toRadians(180);
    public static double RED_RIGHT_START_X = 99;
    public static double RED_RIGHT_START_Y = 99;
    public static double RED_LEFT_actualSTART_X = 100;
    public static double RED_LEFT_actualSTART_Y = 100;
    public static double RED_RIGHT_actualSTART_X = 99;
    public static double RED_RIGHT_actualSTART_Y = 99;
    public static double RED_RIGHT_START_HEADING = Math.toRadians(90);
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
//You are in a dark room, what will you do?
        //I will turn the lights on :)
        //Vincent is a coding star.
        //You go blind... forever dun dun dunnnnnn :(
        //Vincent is amazing at coding. [:
        IntoTheDeepActions intoTheDeepActions = new IntoTheDeepActions(drive, lift, claw, hang);
        // vision here that outputs position
        TrajectoryActionBuilder tabLeft = drive.actionBuilder(new Pose2d(RED_LEFT_START_X,RED_LEFT_START_Y,RED_LEFT_START_HEADING))

                //Deposit Block 1
                .stopAndAdd(new SequentialAction(lift.moveToPositionAction(IntoTheDeep.ElevatorHeight.TOPBASKET)))
                .strafeToLinearHeading(new Vector2d(BASKET_X  ,BASKET_Y ),Math.toRadians(225))//Basket position
                .stopAndAdd(new SleepAction( .5))
                .stopAndAdd(new SequentialAction(claw.openAction()))
                .stopAndAdd(new SleepAction( .5))

                //Grab Block 1
                .strafeToLinearHeading(new Vector2d(YELLOW_SAMPLE_1_X,YELLOW_SAMPLE_1_Y),Math.toRadians(90)) //Block 1 (Right most)
                .stopAndAdd(new SequentialAction(lift.moveToPositionAction(IntoTheDeep.ElevatorHeight.BOTTOM_PLUS)))
                .stopAndAdd(claw.moveToDownAction())
                .stopAndAdd(new SleepAction( .5))
                .stopAndAdd(new SequentialAction(claw.closeAction()))
                .stopAndAdd(new SleepAction( .5))
                .stopAndAdd(claw.moveToUpAction())

                //Deposit Block 1
                .stopAndAdd(new SequentialAction(lift.moveToPositionAction(IntoTheDeep.ElevatorHeight.TOPBASKET)))
                .strafeToLinearHeading(new Vector2d(BASKET_X,BASKET_Y),Math.toRadians(225))//Basket position
                .stopAndAdd(new SleepAction( .5))
                .stopAndAdd(new SequentialAction(claw.openAction()))
                .stopAndAdd(new SleepAction( .5))



                //Grab Block 2
                .strafeToLinearHeading(new Vector2d(YELLOW_SAMPLE_2_X,YELLOW_SAMPLE_2_Y ),initialPose.heading) //Block 1 (Right most)
                .stopAndAdd(new SequentialAction(lift.moveToPositionAction(IntoTheDeep.ElevatorHeight.BOTTOM_PLUS)))
                .stopAndAdd(claw.moveToDownAction())
                .stopAndAdd(new SleepAction( .5))
                .stopAndAdd(new SequentialAction(claw.closeAction()))
                .stopAndAdd(new SleepAction( .5))
                .stopAndAdd(claw.moveToUpAction())
// Start position = dpad slection
                //Deposit Block 2
                .stopAndAdd(new SequentialAction(lift.moveToPositionAction(IntoTheDeep.ElevatorHeight.TOPBASKET)))
                .strafeToLinearHeading(new Vector2d(BASKET_X ,BASKET_Y),Math.toRadians(225))//Basket position
                .stopAndAdd(new SleepAction( .5))
                .stopAndAdd(new SequentialAction(claw.openAction()))
                .stopAndAdd(new SleepAction( .5))

                //Grab Block 3
                .strafeToLinearHeading(new Vector2d(YELLOW_SAMPLE_3_X,YELLOW_SAMPLE_3_Y ),YELLOW_SAMPLE_3_HEADING) //Block 1 (Right most)
                .stopAndAdd(new SequentialAction(lift.moveToPositionAction(IntoTheDeep.ElevatorHeight.BOTTOM_PLUS)))
                .stopAndAdd(claw.moveToDownAction())
                .stopAndAdd(new SleepAction( .5))
                .stopAndAdd(new SequentialAction(claw.closeAction()))
                .stopAndAdd(new SleepAction( .5))
                .stopAndAdd(claw.moveToUpAction())
// Start position = dpad slection
                //Deposit Block 2
                .stopAndAdd(new SequentialAction(lift.moveToPositionAction(IntoTheDeep.ElevatorHeight.TOPBASKET)))
                .strafeToLinearHeading(new Vector2d(BASKET_X ,BASKET_Y),Math.toRadians(225))//Basket position
                .stopAndAdd(new SleepAction( .5))
                .stopAndAdd(new SequentialAction(claw.openAction()))
                .stopAndAdd(new SleepAction( .5))

                .strafeToLinearHeading(new Vector2d(LEFT_PARK_X,LEFT_PARK_Y),initialPose.heading)//Basket position
                .stopAndAdd(new SequentialAction(lift.moveToPositionAction(IntoTheDeep.ElevatorHeight.BOTTOM_PLUS)))




                ;
                //isaac is a software star and will always be :D
                // Jake is a software star and always will be because he is software
        //Coach Joe is a software star.
        ;
        TrajectoryActionBuilder tabRight= drive.actionBuilder(new Pose2d(RED_RIGHT_START_X, RED_RIGHT_START_Y, Math.toRadians(90)))
                .stopAndAdd(new SequentialAction(claw.closeAction()))
                .stopAndAdd(new SequentialAction(lift.moveToPositionAction(IntoTheDeep.ElevatorHeight.SpecimenReadyToBeCliped)))
                .strafeTo(new Vector2d(0+RED_RIGHT_START_X, distForward+RED_RIGHT_START_Y))
                .stopAndAdd(new SleepAction(1))
                .stopAndAdd(new SequentialAction(lift.moveToPositionAction(IntoTheDeep.ElevatorHeight.SpecimenClipedOnHighb)))
                .stopAndAdd(new SleepAction( 1))
                .stopAndAdd(new SequentialAction(claw.openAction()))
                .strafeTo(new Vector2d(0+RED_RIGHT_START_X,0+RED_RIGHT_START_Y))
                .strafeTo(new Vector2d(distRight+RED_RIGHT_START_X,0+RED_RIGHT_START_Y))
                .strafeTo(new Vector2d(distRight+RED_RIGHT_START_X,distForwardforSamples+RED_RIGHT_START_Y))
                .strafeTo(new Vector2d(32+RED_RIGHT_START_X,distForwardforSamples+RED_RIGHT_START_Y))
                .strafeTo(new Vector2d(32+RED_RIGHT_START_X, 0+RED_RIGHT_START_Y))
                .strafeTo(new Vector2d(32+RED_RIGHT_START_X,distForwardforSamples+RED_RIGHT_START_Y))
                .strafeTo(new Vector2d(41+RED_RIGHT_START_X,distForwardforSamples+RED_RIGHT_START_Y))
                .strafeTo(new Vector2d(41+RED_RIGHT_START_X,0+RED_RIGHT_START_Y))
                //.strafeTo(new Vector2d(45,50))
                //.strafeTo(new Vector2d(54,50))
                .strafeTo(new Vector2d(distPark+RED_RIGHT_START_X,0+RED_RIGHT_START_Y));//go to parking space
                /*.stopAndAdd(new SequentialAction(lift.moveToPositionAction(IntoTheDeep.ElevatorHeight.SpecimenReadyToBeCliped)))
                .strafeTo(new Vector2d(-15, distForward))
                .stopAndAdd(new SleepAction(1))
                .stopAndAdd(new SequentialAction(lift.moveToPositionAction(IntoTheDeep.ElevatorHeight.SpecimenClipedOnHighb)))
                .stopAndAdd(new SequentialAction(claw.openAction()))

                .strafeTo(new Vector2d(12, -60))*/
                //.strafeTo(new Vector2d(78, -60));


        Action trajectoryActionChosen;
        if (left == 1){
            drive.pose = new Pose2d(RED_LEFT_START_X,RED_LEFT_START_Y,RED_LEFT_START_HEADING);
            trajectoryActionChosen = tabLeft.build();
        } else{
            drive.pose = new Pose2d(RED_RIGHT_START_X,RED_RIGHT_START_Y,RED_RIGHT_START_HEADING);
            trajectoryActionChosen = tabRight.build();
        }
        waitForStart();

        if (isStopRequested()) return;
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
//Jedidiah is a software star
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