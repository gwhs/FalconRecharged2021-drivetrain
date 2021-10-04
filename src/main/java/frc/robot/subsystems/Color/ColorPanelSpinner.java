
package frc.robot.subsystems.Color;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class ColorPanelSpinner extends SubsystemBase {
    private CANEncoder encoder;
    //private TalonSRX moto1;
    private CANSparkMax moto1;
    public static CANPIDController mPIDController;
    public static double mPIDControllerP = 0.2; //0.17 not neo 550
    public static double mPIDControllerI = 0.0; //0.0002 the one that works
    public static double mPIDControllerD = 0.0; //0.1 these r guud
    public DoubleSolenoid colorPanelSolenoid;

    public ColorPanelSpinner() {
        //moto1 = new TalonSRX(Constants.SPINNER_TALON);
         SmartDashboard.putNumber("PID_P", mPIDControllerP);
         SmartDashboard.putNumber("PID_I", mPIDControllerI);
         SmartDashboard.putNumber("PID_D", mPIDControllerD);

        moto1 = new CANSparkMax(Constants.SPINNER_SPARK, MotorType.kBrushless);
        //encoder = new CANEncoder(moto1, EncoderType.kQuadrature,8192); //for throughbore encoder
        encoder = moto1.getEncoder(); //for neo 550 built in motor
        colorPanelSolenoid = new DoubleSolenoid(Constants.COLORPANELFORWARD_SOLENOID, Constants.COLORPANELREVERSE_SOLENOID);
        colorPanelSolenoid.set(Value.kReverse);
        //moto1.setNeutralMode(IdleMode.kBrake);
       // encoder = moto1.getEncoder();

        mPIDController = moto1.getPIDController();
        // // mPIDControllerP = 0.0;
        mPIDController.setP(mPIDControllerP); // 0.00001 working value. we keep it.
        mPIDController.setI(mPIDControllerI); // .0000001
        mPIDController.setD(mPIDControllerD); // 0.0065

        moto1.setSmartCurrentLimit(50);
        //moto1.setInverted(true);
        // encoder.setInverted(true);
    }

    public void setMotorSpeed (double speed){
        //moto1.set(ControlMode.PercentOutput, speed);
        moto1.set(speed);
    
    }
    
    public double getMotorSpeed (){
        //moto1.set(ControlMode.PercentOutput, speed);
        return moto1.get();
    
    }
    public void spin(double speed) {
       // moto1.set(ControlMode.PercentOutput, speed);
       moto1.set(speed);
       //position_encoder.setPosition(position_encoder.getPosition() + 5);
    }

    public int inchesToEncoderTicks(double inches) {
        return (int) Math.round(inches * 64);
    }

    public void setPosition(double pos) {
        mPIDController.setReference(pos, ControlType.kPosition);
    }

    public void goToPosition(double degrees) // color panel circumference 113.0973 inches
    {
        /*
         * distance /= 2 * Math.PI * 2; // to wheel rotations distance *= 0.25; // to
         * encoder rotations distance *= 4; // to encoder ticks distance =
         * inchesToEncoderTicks(distance);
         */
        double ticks = degrees / 360.0;
        SmartDashboard.putNumber("Module Ticks ", ticks);
        
    }

    public CANPIDController getPIDController() {
        return mPIDController;
    }

    public void printPID() {
        SmartDashboard.putNumber("PID_P", mPIDControllerP);
        SmartDashboard.putNumber("PID_I", mPIDControllerI);
        SmartDashboard.putNumber("PID_D", mPIDControllerD);
    }

    public void setPID() {
        double defaultValue = 0.2;
        mPIDController.setP(SmartDashboard.getNumber("PID_P", defaultValue));
        mPIDController.setI(SmartDashboard.getNumber("PID_I", defaultValue));
        mPIDController.setD(SmartDashboard.getNumber("PID_D", defaultValue));

        printPID();

    }

    public double getPosition() {
        return encoder.getPosition();
    }

    public void printPosition() {
        SmartDashboard.putNumber("Spinner Pos", getPosition());
    }

    public void resetEncoder() {
        encoder.setPosition(0);
    }

    public void toggleSpinner() {
        if(colorPanelSolenoid.get() == Value.kReverse)
        {
            colorPanelSolenoid.set(Value.kForward);
        }
        else {
            colorPanelSolenoid.set(Value.kReverse);
        }
    }

    public void retractSpinner() {
        colorPanelSolenoid.set(Value.kReverse);
    }

    public void deploySpinner() {
        colorPanelSolenoid.set(Value.kForward);
    }

    @Override
    public void periodic() {
      printPosition();
      SmartDashboard.putNumber("Current", moto1.getOutputCurrent());
      SmartDashboard.putNumber("Temperature", moto1.getMotorTemperature());
      SmartDashboard.putNumber("Voltage",moto1.getBusVoltage());
      //printPID();
    }
}