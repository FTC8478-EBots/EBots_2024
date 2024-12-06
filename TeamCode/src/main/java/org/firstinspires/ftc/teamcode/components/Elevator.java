package org.firstinspires.ftc.teamcode.components;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;


import androidx.annotation.NonNull;

import org.firstinspires.ftc.teamcode.constants.IntoTheDeep;
@Config
public class Elevator {

    private LinearOpMode opMode;
    HardwareMap hardwareMap;
    public DcMotorEx elevatorMotor;
    private     DigitalChannel zeroLimitSwitch;
    public boolean isZeroed = false;
    private int elevatorHeight = 0;
    private int MIN_HEIGHT = 0;
    private int MAX_HEIGHT = 2;
    public static float power = 0.5f;
    public static float velocity = 3000;
    public static int T = 5;
    public static float I = 0;
    public static float D = 0;
    public static float F = 0;

    boolean busy = false;
    private Elevator(){};
    private static Elevator theElevator;
    DigitalChannel busyLED;
    public static Elevator getElevator(LinearOpMode opMode) {
       // if (theElevator == null) {
            theElevator = new Elevator();
            theElevator.init(opMode);
        //}
        return theElevator;
    }

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
    elevatorMotor.setPower(power);
    elevatorMotor.setVelocity(velocity);
    elevatorMotor.setTargetPositionTolerance(T);
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

    public void zeroize() {
        if (busy) return;
        busy = true;
        busyLED.setState(false);

       // new Thread(
                new Runnable() {
                    @Override
                    public void run() {

                        elevatorMotor.setPower(0);
                        elevatorMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                        elevatorMotor.setPower(-.4);

                        Actions.runBlocking(new SleepAction(1));
                        elevatorMotor.setPower(0);
                        Actions.runBlocking(new SleepAction(.2));

                        elevatorMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

                        elevatorMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                        elevatorMotor.setPower(power);
                        elevatorMotor.setVelocity(velocity);
                        busy = false;
                        //elevatorMotor.setPIDFCoefficients(DcMotor.RunMode.RUN_TO_POSITION,new PIDFCoefficients(1,0,0,0));
                    }
                }.run();
          //      ).start();

    }
    int getPosition() {
        return elevatorMotor.getCurrentPosition();

        //throw new RuntimeException("Unimplemented");
    }

    public void updateTelemetry() {
        telemetry.addData("elevatorHeight", elevatorHeight);
        telemetry.addData("Elevator Position", elevatorMotor.getCurrentPosition());
        telemetry.addData("Elevator Target", elevatorMotor.getTargetPosition());
        telemetry.addData("Elevator Velocity", elevatorMotor.getVelocity());
      //  telemetry.addData("ZeroSensor", zeroLimitSwitch.getState());
    }
    public void init(LinearOpMode opMode) {
        telemetry = opMode.telemetry;
        this.opMode = opMode;
        this.hardwareMap = this.opMode.hardwareMap;
        isZeroed = false;
        this.elevatorMotor = this.hardwareMap.get(DcMotorEx.class, "elevatorMotor");
        busyLED = hardwareMap.get(DigitalChannel.class, "busyLED");
        busyLED.setMode(DigitalChannel.Mode.OUTPUT);
        busyLED.setState(true);

        elevatorMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        elevatorMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        elevatorMotor.setTargetPosition(0);
        elevatorMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        elevatorMotor.setPower(0.2);
        //this.zeroLimitSwitch = this.hardwareMap.get(DigitalChannel.class, "elevatorZero");
        updateTelemetry();
        zeroize();
        //elevatorMotor.setTargetPosition(0);
        //if elevatorHeight == 2 set position(3608)
        //if elevatorHeight == 1 set position(1616)
        //if elevatorHeight == o set position(0)
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
        int cycles = 0;
        private boolean initialized = false;
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            if  (!initialized) {
                elevatorMotor.setTargetPosition(targetPosition);
                initialized = true;
            }
            cycles++;
            telemetryPacket.put("elevatorTarget", targetPosition);
            telemetryPacket.put("elevatorCycles",cycles);
            return !((Math.abs(elevatorMotor.getCurrentPosition()-targetPosition)< 4 * elevatorMotor.getTargetPositionTolerance()));

        }
    };

    }

    public Action moveToPositionActionDontWait (int position){return new Action() {
        int targetPosition = position;
        int cycles = 0;
        private boolean initialized = false;
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            if  (!initialized) {
                elevatorMotor.setTargetPosition(targetPosition);
                initialized = true;
            }
            cycles++;
            telemetryPacket.put("elevatorTarget", targetPosition);
            telemetryPacket.put("elevatorCycles",cycles);
            return false;//!((Math.abs(elevatorMotor.getCurrentPosition()-targetPosition)< 4 * elevatorMotor.getTargetPositionTolerance()));

        }
    };

    }


       public double getDriveSpeedScaling(){

        if (elevatorMotor.getCurrentPosition() > 50)
        return .4;
    else
        return 1; }
}
