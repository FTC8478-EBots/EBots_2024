package org.firstinspires.ftc.teamcode.components;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Hang {
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
    public void setPower(float power){hangMotor.setPower(power);}
}
