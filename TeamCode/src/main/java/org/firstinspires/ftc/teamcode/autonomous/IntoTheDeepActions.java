package org.firstinspires.ftc.teamcode.autonomous;

import android.drm.DrmStore;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.SequentialAction;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.components.Claw;
import org.firstinspires.ftc.teamcode.components.Elevator;
import org.firstinspires.ftc.teamcode.constants.IntoTheDeep;

public class IntoTheDeepActions {
    MecanumDrive mecanumDrive; Elevator elevator; Claw claw;
    public IntoTheDeepActions(MecanumDrive mecanumDrive, Elevator elevator, Claw claw){this.elevator = elevator; this.claw = claw; this.mecanumDrive = mecanumDrive;}
  public Action getHighSpecimenScoringAction(){

        return new SequentialAction(
                //mecanumDrive.actionBuilder(mecanumDrive.pose).lineToY(-4).build(),
                elevator.moveToPositionAction(IntoTheDeep.ElevatorHeight.SpecimenReadyToBeCliped ),
                //mecanumDrive.actionBuilder(mecanumDrive.pose).lineToY(4).build(),
                elevator.moveToPositionAction(IntoTheDeep.ElevatorHeight.SpecimenClipedOnHighb ),
                claw.openAction()
        );
    }
    public Action getHighSampleScoringAction() {

        return new SequentialAction(
                elevator.moveToPositionAction(IntoTheDeep.ElevatorHeight.SpecimenReadyToBeCliped),
                //mecanumDrive.actionBuilder(mecanumDrive.pose).lineToY(4).build(),
                elevator.moveToPositionAction(IntoTheDeep.ElevatorHeight.SpecimenClipedOnHighb),
                claw.openAction()
        );
        }
    }
