package org.firstinspires.ftc.teamcode.components;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.LED;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import static java.lang.Thread.sleep;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
@Config
public class Claw {
    public static int ARM_DOWN_POSITION = 1250;//-y
    public static int ARM_UP_POSITION = 0; //-x
    public static int ARM_SPECIMIN_POSITION = 1100;
    public static int ARM_HANG_PREPARE_POSITION = 2225;
    public final double ARM_WEAK_POWER = 0.1;
    public final double ARM_NORMAL_POWER = 1;
    int cycleCount = 0;    //Telemetry telemetry = new Telemetry();
    boolean busy = false;

    private Servo clawServo;
    private DcMotorEx armMotor;
    private DigitalChannel busyLED;
    private static Claw theClaw;
    double maxArmCurrent = 0;

    Handler runner;

    public static Claw getClaw(HardwareMap hardwareMap) {
        //if (theClaw == null) theClaw = new Claw(hardwareMap);
        //return theClaw;
        return new Claw(hardwareMap);
    }

    private Claw(HardwareMap hardwareMap) {
        Looper.prepare();
        runner = new Handler();

       // runner = new Executors.newSingleThreadExecutor();
        clawServo = hardwareMap.get(Servo.class, "clawServo");
        clawServo.setDirection(Servo.Direction.FORWARD);
        armMotor = hardwareMap.get(DcMotorEx.class, "armMotor");
        armMotor.setDirection(DcMotorEx.Direction.REVERSE);
        busyLED = hardwareMap.get(DigitalChannel.class, "busyLED");
        busyLED.setMode(DigitalChannel.Mode.OUTPUT);
        busyLED.setState(true);

        //busyLED.on();
        zeroize(2);
    }

    public void zeroize(double movingTime) {
        if (busy) return;
        busy = true;
        busyLED.setState(false);

        new Thread(
        new Runnable() {
            @Override
            public void run() {
                armMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                armMotor.setPower(.1);
                Actions.runBlocking(new SleepAction(movingTime / 4.0));
                armMotor.setPower(-.1);
                Actions.runBlocking(new SleepAction(movingTime));
                armMotor.setPower(0);
                Actions.runBlocking(new SleepAction(.2));
                armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                armMotor.setTargetPosition(ARM_UP_POSITION);
                armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                armMotor.setPower(ARM_NORMAL_POWER);
                armMotor.setCurrentAlert(5, CurrentUnit.AMPS);
                busyLED.setState(true);
                busy = false;
            }
        }).start();
    }



    public void updateTelemetry(Telemetry t) {
        maxArmCurrent = Math.max(maxArmCurrent, armMotor.getCurrent(CurrentUnit.AMPS));

        t.addData("arm Target", armMotor.getTargetPosition());
        t.addData("arm position", armMotor.getCurrentPosition());
        t.addData("arm current", armMotor.getCurrent(CurrentUnit.AMPS));
        t.addData("Max Arm Current", maxArmCurrent);
    }

    void open() {
        clawServo.setPosition(.1);
    }


    public void init(LinearOpMode opMode) {

        //this.zeroLimitSwitch = this.hardwareMap.get(DigitalChannel.class, "elevatorZero");
        // updateTelemetry();
        //elevatorMotor.setTargetPosition(0);
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

    public Action powerAction(double power) {
        return new Action() {
            private boolean initialized = false;

            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                if (!initialized) {
                    armMotor.setPower(power);
                    initialized = true;
                }
                telemetryPacket.addLine("raiseClaw");
                return false;
            }
        };
    }
    public Action moveToDownAction() {
    return moveToPositionAction(ARM_DOWN_POSITION);
    }
    public Action moveToUpAction() {
        return moveToPositionAction(ARM_UP_POSITION);
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
        cycleCount++;
        if (cycleCount > 5) {
            // zeroize(0.1);
            cycleCount = 0;
        }
        armMotor.setTargetPosition(ARM_DOWN_POSITION);
    }


    public double driveSpeedScaling() {
        if (armMotor.getCurrentPosition() > ARM_DOWN_POSITION / 4)
            return .4;
        else
            return 1;
    }

}
