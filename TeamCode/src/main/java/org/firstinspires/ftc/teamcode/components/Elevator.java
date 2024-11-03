package org.firstinspires.ftc.teamcode.components;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import android.drm.DrmStore;

import androidx.annotation.NonNull;

import org.firstinspires.ftc.teamcode.constants.IntoTheDeep;

public class Elevator {

    private LinearOpMode opMode;
    HardwareMap hardwareMap;
    private DcMotorEx elevatorMotor;
    private     DigitalChannel zeroLimitSwitch;
    private boolean isZeroed = false;
    private int elevatorHeight = 0;
    private int MIN_HEIGHT = 0;
    private int MAX_HEIGHT = 2;
    public void increaseHeight(){
        elevatorHeight ++;
        if (elevatorHeight > MAX_HEIGHT ) {
            elevatorHeight = MAX_HEIGHT;
        }


        updatePosition();



    }
    public void decreaseHeight() {
        elevatorHeight --;
        if (elevatorHeight < MIN_HEIGHT) {
            elevatorHeight = MIN_HEIGHT;

        }

        updatePosition();
    }

    private void updatePosition() {
        switch(elevatorHeight){
            case  0:
                moveToPosition(IntoTheDeep.ElevatorHeight.BOTTOM);
                break;
            case  1:
                moveToPosition(IntoTheDeep.ElevatorHeight.LOWERBASKET);
                break;
            case  2:
                moveToPosition(IntoTheDeep.ElevatorHeight.TOPBASKET);
                break;
        }
    }

    int getPosition() {
        return elevatorMotor.getCurrentPosition();

        //throw new RuntimeException("Unimplemented");
    }

    public void updateTelemetry() {
        telemetry.addData("elevatorHeight", elevatorHeight);
        telemetry.addData("Elevator Position", elevatorMotor.getCurrentPosition());
        telemetry.addData("Elevator Target", elevatorMotor.getTargetPosition());
      //  telemetry.addData("ZeroSensor", zeroLimitSwitch.getState());
    }
    public void init(LinearOpMode opMode) {
        telemetry = opMode.telemetry;
        this.opMode = opMode;
        this.hardwareMap = this.opMode.hardwareMap;
        isZeroed = false;
        this.elevatorMotor = this.hardwareMap.get(DcMotorEx.class, "elevatorMotor");
        elevatorMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        elevatorMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        elevatorMotor.setTargetPosition(0);
        elevatorMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        elevatorMotor.setPower(0.2);
        //this.zeroLimitSwitch = this.hardwareMap.get(DigitalChannel.class, "elevatorZero");
        updateTelemetry();
        //elevatorMotor.setTargetPosition(0);
        //if elevatorHeight == 2 set position(3608)
        //if elevatorHeight == 1 set position(1616)
        //if elevatorHeight == o set position(0)
    }
    void zeroElevator() {
        throw new RuntimeException("Unimplemented");
    }
    public void stop() {
        if (elevatorMotor.getMode() != DcMotor.RunMode.RUN_TO_POSITION) {
            elevatorMotor.setTargetPosition(elevatorMotor.getCurrentPosition());
            elevatorMotor.setPower(.5);
            elevatorMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }
    }
    public void runMode(DcMotor.RunMode r) {
        elevatorMotor.setMode(r);
    }
    public void setVelocity(double velocity) {
        elevatorMotor.setPower(1);
        elevatorMotor.setVelocity(velocity*312);
    }

    public void moveToPosition(int position) {elevatorMotor.setTargetPosition(position);}

    public Action moveToPositionAction (int position){return new Action() {
        int targetPosition = position;
        private boolean initialized = false;
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            if  (!initialized) {
                elevatorMotor.setTargetPosition(targetPosition);
                initialized = true;
            }
            telemetryPacket.put("elevatorTarget", targetPosition);
            return !(Math.abs(elevatorMotor.getCurrentPosition()-targetPosition)< 2 * elevatorMotor.getTargetPositionTolerance());

        }
    };

    }


       public double getDriveSpeedScaling(){

        if (elevatorMotor.getCurrentPosition() > 50)
        return .4;
    else
        return 1; }
}
