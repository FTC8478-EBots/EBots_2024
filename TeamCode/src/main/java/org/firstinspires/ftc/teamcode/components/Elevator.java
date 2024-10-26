package org.firstinspires.ftc.teamcode.components;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

public class Elevator {

    private LinearOpMode opMode;
    HardwareMap hardwareMap;
    private DcMotorEx elevatorMotor;
    private     DigitalChannel zeroLimitSwitch;
    private boolean isZeroed = false;

    int getPosition() {
        return elevatorMotor.getCurrentPosition();

        //throw new RuntimeException("Unimplemented");
    }
    void setPosition(int position) {
        throw new RuntimeException("Unimplemented");
    }
    public void updateTelemetry() {
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

}
