package org.firstinspires.ftc.teamcode.components;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Hang {
    public final double HANG_WEAK_POWER = 0.3;
    public DcMotorEx hangMotor;
    public Hang(HardwareMap hardwareMap) {
        hangMotor = hardwareMap.get(DcMotorEx.class, "hangMotor");
        hangMotor.setDirection(DcMotorEx.Direction.FORWARD);
        hangMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        hangMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        hangMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        //hangMotor.setTargetPosition();
        //hangMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        //hangMotor.setPower(0.2);
        hangMotor.setMotorEnable();
        //zeroize();
    }
    public void retract() {
        hangMotor.setTargetPosition(0);
        hangMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        hangMotor.setPower(1.0);

    }
    public void setPower(double power){hangMotor.setPower(power);}
    public Action hangPower(double power) {
        return new Action() {
            private boolean initialized = false;

            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                if (!initialized) {
                    hangMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                    setPower(power);
                    initialized = true;
                }
                telemetryPacket.addLine("hangPower");
                return false;
            }
        };
    }

}
