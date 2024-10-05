package org.firstinspires.ftc.teamcode.components;
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
        return 0;

        //throw new RuntimeException("Unimplemented");
    }
    void setPosition(int position) {
        throw new RuntimeException("Unimplemented");
    }
    private void updateTelemetry() {
        telemetry.addData("Elevator Position", elevatorMotor.getCurrentPosition());
        telemetry.addData("ZeroSensor", zeroLimitSwitch.getState());
    }
    void init(LinearOpMode opMode) {
        telemetry = opMode.telemetry;
        this.opMode = opMode;
        this.hardwareMap = this.opMode.hardwareMap;
        isZeroed = false;
        this.elevatorMotor = this.hardwareMap.get(DcMotorEx.class, "elevatorMotor");
        this.zeroLimitSwitch = this.hardwareMap.get(DigitalChannel.class, "elevatorZero");
        updateTelemetry();
        telemetry.update();
        elevatorMotor.setTargetPosition(0);
    }
    void zeroElevator() {
        throw new RuntimeException("Unimplemented");

    }

}
