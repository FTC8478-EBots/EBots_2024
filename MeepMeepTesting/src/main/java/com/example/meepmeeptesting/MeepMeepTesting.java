import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;

import org.rowlandhall.meepmeep.MeepMeep;
import org.rowlandhall.meepmeep.core.colorscheme.scheme.ColorSchemeBlueDark;
import org.rowlandhall.meepmeep.core.colorscheme.scheme.ColorSchemeBlueLight;
import org.rowlandhall.meepmeep.roadrunner.DefaultBotBuilder;
import org.rowlandhall.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(650);

        RoadRunnerBotEntity testRedLeft = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .followTrajectorySequence(drive -> drive.trajectorySequenceBuilder(new Pose2d(-34, -61, Math.toRadians(90)))
                        //.strafeRight(30)
                        .strafeTo(new Vector2d(-55,-55))
                        .turn(Math.toRadians(135))
                        .turn(Math.toRadians(-135))
                        .strafeTo(new Vector2d(-48,-36))
                        .strafeTo(new Vector2d(-55,-55))
                        .turn(Math.toRadians(135))
                        // .strafeTo(new Vector2d(-34,-70))
                        .build());

        RoadRunnerBotEntity testRedRight  = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .followTrajectorySequence(drive -> drive.trajectorySequenceBuilder(new Pose2d(34, -61, Math.toRadians(90)))
                        .strafeTo(new Vector2d(55,-55))
                        .turn(Math.toRadians(135))
                        .turn(Math.toRadians(-135))
                        .strafeTo(new Vector2d(48,-36))
                        .strafeTo(new Vector2d(55,-55))
                        .turn(Math.toRadians(135))
                        .build());

        RoadRunnerBotEntity testBlueLeft  = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .followTrajectorySequence(drive -> drive.trajectorySequenceBuilder(new Pose2d(34, 61, Math.toRadians(270)))
                        .strafeTo(new Vector2d(55,55))
                        .turn(Math.toRadians(135))
                        .turn(Math.toRadians(-135))
                        .strafeTo(new Vector2d(48,36))
                        .strafeTo(new Vector2d(55,55))
                        .turn(Math.toRadians(135))
                        .build());

        RoadRunnerBotEntity testBlueRight = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .followTrajectorySequence(drive -> drive.trajectorySequenceBuilder(new Pose2d(-34, 61, Math.toRadians(270)))
                        .strafeTo(new Vector2d(-55,55))
                        .turn(Math.toRadians(135))
                        .turn(Math.toRadians(-135))
                        .strafeTo(new Vector2d(-48,36))
                        .strafeTo(new Vector2d(-55,55))
                        .turn(Math.toRadians(135))
                        .build());


        RoadRunnerBotEntity myBot8478 = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .followTrajectorySequence(drive -> drive.trajectorySequenceBuilder(new Pose2d(34, -53, 0))
                        .forward(30)
                        .turn(Math.toRadians(90))
                        .forward(30)
                        .turn(Math.toRadians(90))
                        .forward(30)
                        //.turn(Math.toRadians(90))
                        //.forward(30)
                        //.turn(Math.toRadians(90))
                        .build());


        meepMeep.setBackground(MeepMeep.Background.FIELD_INTOTHEDEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(testRedLeft)
                .addEntity(testRedRight)
                .addEntity(testBlueLeft)
                .addEntity(testBlueRight)
                //            .addEntity(myBot8478)

                .start();

   /*     meepMeep.setBackground(MeepMeep.Background.FIELD_INTOTHEDEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .start();

    */

    }



}
//.forward(90)
//.turn(Math.toRadians(90))
//.forward(20)
//.turn(Math.toRadians(90))
//.forward(33)
//.turn(Math.toRadians(90))
//.forward(20)
//.turn(Math.toRadians(90))
//.forward(20)
//.turn(Math.toRadians(90))
//.strafeLeft(60)
//.turn(Math.toRadians(180))
//.forward(20)