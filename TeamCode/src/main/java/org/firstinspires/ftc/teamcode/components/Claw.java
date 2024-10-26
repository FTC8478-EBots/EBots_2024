package org.firstinspires.ftc.teamcode.components;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

public class Claw {
final int ARM_DOWN_POSITION = -300;
final int ARM_UP_POSITION = 0;
    //Telemetry telemetry = new Telemetry();


    public Servo clawServo;
    public  DcMotorEx armMotor;
    public Claw(HardwareMap hardwareMap) {
        clawServo = hardwareMap.get(Servo.class, "clawServo");
        clawServo.setDirection(Servo.Direction.FORWARD);
        armMotor = hardwareMap.get(DcMotorEx.class,"armMotor");
        armMotor.setDirection(DcMotorEx.Direction.FORWARD);
     //   armMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        armMotor.setTargetPosition(0);
        armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        armMotor.setPower(0.3);
        armMotor.setPower(0.4);
        armMotor.setMotorEnable();

    }

        public void updateTelemetry(Telemetry t){
            t.addData("arm position",armMotor.getCurrentPosition());
        }
        public void open() {
        clawServo.setPosition(.1);
    }
/*public void down() {
        armMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        armMotor.setPower(.2);
            //armMotor.setTargetPosition(ARM_DOWN_POSITION);
        }
        public void up() {
            armMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            armMotor.setPower(-.4);
            //armMotor.setTargetPosition(ARM_UP_POSITION);
        }
        public void stop() {
        if (armMotor.getMode() != DcMotor.RunMode.RUN_TO_POSITION) {
            armMotor.setTargetPosition(armMotor.getCurrentPosition());
            armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }
        }*/

    public void init(LinearOpMode opMode) {

        //this.zeroLimitSwitch = this.hardwareMap.get(DigitalChannel.class, "elevatorZero");
        // updateTelemetry();
        //elevatorMotor.setTargetPosition(0);
    }
        public void moveToPosition(int position) {armMotor.setTargetPosition(position);}


    public void close() {
        clawServo.setPosition(0.36);
    }


}