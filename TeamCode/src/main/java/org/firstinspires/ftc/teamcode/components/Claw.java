package org.firstinspires.ftc.teamcode.components;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Claw {
    public Servo clawServo;

    public Claw(HardwareMap hardwareMap) {
        clawServo = hardwareMap.get(Servo.class, "clawServo");
        clawServo.setDirection(Servo.Direction.FORWARD);
    }

    public void open() {
        clawServo.setPosition(1);
    }

    public void close() {
        clawServo.setPosition(0);
    }

    void init() {
        throw new RuntimeException("Unimplemented");
    }


}