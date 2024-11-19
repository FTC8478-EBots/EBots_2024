package org.firstinspires.ftc.teamcode.components;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import static java.lang.Thread.sleep;

import androidx.annotation.NonNull;

public class Claw {
    final int ARM_DOWN_POSITION = 400;//-y
    final int ARM_UP_POSITION = 35; //-x
    //Telemetry telemetry = new Telemetry();


    public Servo clawServo;
    public DcMotorEx armMotor;
    private static Claw theClaw;
    public static Claw getClaw(HardwareMap hardwareMap) {
        //if (theClaw == null) theClaw = new Claw(hardwareMap);
        //return theClaw;
        return new Claw(hardwareMap);
    }

    private Claw(HardwareMap hardwareMap) {
        clawServo = hardwareMap.get(Servo.class, "clawServo");
        clawServo.setDirection(Servo.Direction.FORWARD);
        armMotor = hardwareMap.get(DcMotorEx.class, "armMotor");
        armMotor.setDirection(DcMotorEx.Direction.FORWARD);
        armMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armMotor.setTargetPosition(ARM_UP_POSITION);
        armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        armMotor.setPower(0.3);
        armMotor.setMotorEnable();
        zeroize();
    }

    public void zeroize() {
        armMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        armMotor.setPower(-.4);
        Actions.runBlocking(new SleepAction(1.5));
        armMotor.setPower(0);
        Actions.runBlocking(new SleepAction(.1));
        armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armMotor.setTargetPosition(ARM_UP_POSITION);
        armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        armMotor.setPower(1);
        armMotor.setCurrentAlert(5, CurrentUnit.AMPS);
    }

    public void rezeroize() {
        armMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        armMotor.setPower(-.6);
        Actions.runBlocking(new SleepAction(1.5));
        armMotor.setPower(0);
        Actions.runBlocking(new SleepAction(.1));
        armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armMotor.setTargetPosition(ARM_UP_POSITION);
        armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        armMotor.setPower(1);
        armMotor.setCurrentAlert(5, CurrentUnit.AMPS);
    }


    public void updateTelemetry(Telemetry t) {
        t.addData("arm Target", armMotor.getTargetPosition());
        t.addData("arm position", armMotor.getCurrentPosition());
        t.addData("arm current", armMotor.getCurrent(CurrentUnit.AMPS));
    }

    void open() {
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

    private void moveToPosition(int position) {
        armMotor.setTargetPosition(position);
    }


    public void close() {
        clawServo.setPosition(0.36);
    }

    public Action openAction() {
        return new Action() {
            private boolean initialized = false;

            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                if (!initialized) {
                    open();
                    initialized = true;
                }
                telemetryPacket.addLine("openingClaw");
                return false;
            }
        };
    }

    public Action closeAction() {
        return new Action() {
            private boolean initialized = false;

            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                if (!initialized) {
                    close();
                    initialized = true;
                }
                telemetryPacket.addLine("openingClaw");
                return false;
            }
        };
    }

    public Action downAction() {
        return new Action() {
            private boolean initialized = false;

            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                if (!initialized) {
                    down();
                    initialized = true;
                }
                telemetryPacket.addLine("lowerClaw");
                return false;
            }
        };
    }

    public Action upAction() {
        return new Action() {
            private boolean initialized = false;

            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                if (!initialized) {
                    up();
                    initialized = true;
                }
                telemetryPacket.addLine("raiseClaw");
                return false;
            }
        };
    }
    public Action moveToPositionAction(int position) {
        return new Action() {
            int targetPosition = position;
            private boolean initialized = false;

            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                if (!initialized) {
                    armMotor.setTargetPosition(targetPosition);
                    initialized = true;
                }
                telemetryPacket.put("elevatorTarget", targetPosition);
                return !(Math.abs(armMotor.getCurrentPosition() - targetPosition) < 2 * armMotor.getTargetPositionTolerance());

            }
        };
    }

    public void up() {
        armMotor.setTargetPosition(ARM_UP_POSITION);
    }

    public void down() {
        armMotor.setTargetPosition(ARM_DOWN_POSITION);
    }


    public double driveSpeedScaling() {
        if (armMotor.getCurrentPosition() > ARM_DOWN_POSITION / 4)
            return .4;
        else
            return 1;
    }

}
