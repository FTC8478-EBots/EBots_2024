package org.firstinspires.ftc.teamcode.autonomous;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.Vector2d;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.components.Claw;
import org.firstinspires.ftc.teamcode.components.Elevator;
import org.firstinspires.ftc.teamcode.constants.IntoTheDeep;

public class IntoTheDeepActions {
    MecanumDrive mecanumDrive;
    Elevator elevator;
    Claw claw;

    public IntoTheDeepActions(MecanumDrive mecanumDrive, Elevator elevator, Claw claw) {
        this.elevator = elevator;
        this.claw = claw;
        this.mecanumDrive = mecanumDrive;
    }

    public Action getHighSpecimenScoringAction() {
//save current position, store dummy position, do moves, go back to current position
        return new SequentialAction(
               // mecanumDrive.actionBuilder(new Pose2d(0,0,0)).strafeTo(new Vector2d(0, -4)).build(),
               // elevator.moveToPositionAction(IntoTheDeep.ElevatorHeight.SpecimenReadyToBeCliped),
               // mecanumDrive.actionBuilder(new Pose2d(0,0,0)).strafeTo(new Vector2d(0, 4)).build(),
                elevator.moveToPositionAction(IntoTheDeep.ElevatorHeight.SpecimenClipedOnHighb),
                claw.openAction()
        );
    }

    public Action getHighSampleScoringAction() {

        return new SequentialAction(
                elevator.moveToPositionAction(IntoTheDeep.ElevatorHeight.TOPBASKET),
                //mecanumDrive.actionBuilder(mecanumDrive.pose).lineToY(4).build(),
                elevator.moveToPositionAction(IntoTheDeep.ElevatorHeight.SpecimenClipedOnHighb),
                claw.openAction()
        );
    }


    public Action grabClimber() {
        return new SequentialAction(
                claw.openAction(),
                elevator.moveToPositionAction(IntoTheDeep.ElevatorHeight.GrabBracket),
                claw.moveToPositionAction(IntoTheDeep.ArmAngles.GrabBracket),
                claw.closeAction()
        );
    }

}