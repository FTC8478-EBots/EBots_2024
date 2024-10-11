package org.firstinspires.ftc.teamcode.components;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

public class Claw {
final int ARM_DOWN_POSITION = -74;
final int ARM_UP_POSITION = 0;
    //Telemetry telemetry = new Telemetry();


    public Servo clawServo;
    public  DcMotorEx armMotor;
    public Claw(HardwareMap hardwareMap) {
        clawServo = hardwareMap.get(Servo.class, "clawServo");
        clawServo.setDirection(Servo.Direction.FORWARD);
        armMotor = hardwareMap.get(DcMotorEx.class,"armMotor");
        armMotor.setDirection(DcMotorEx.Direction.FORWARD);
        armMotor.setTargetPosition(0);
        armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        armMotor.setMotorEnable();
    }

        public void updateTelemetry(Telemetry t){
            t.addData("motor position",armMotor.getCurrentPosition());
        }
        public void open() {
        clawServo.setPosition(.4);
    }
public void down() {
            armMotor.setTargetPosition(ARM_DOWN_POSITION);
        }
        public void up() {
            armMotor.setTargetPosition(ARM_UP_POSITION);
        }
    public void close() {
        clawServo.setPosition(0);
    }

    void init() {
        throw new RuntimeException("Unimplemented");
    }


}