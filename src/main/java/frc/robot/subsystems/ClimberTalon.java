/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ClimberTalon extends SubsystemBase {
  /**
   * Creates a new ClimberTalon.
   */
  private TalonFX lowerArm;
  private TalonFX upperArm;
  private DoubleSolenoid climberGearLock;
  private DigitalInput leftLimit;
  private DigitalInput rightLimit;

  public ClimberTalon() {
    upperArm = new TalonFX(Constants.CLIMBER1_TALON); //left is upper
    lowerArm = new TalonFX(Constants.CLIMBER2_TALON); //right lower
    climberGearLock = new DoubleSolenoid(Constants.CLIMBERFORWARD_SOLENOID,Constants.CLIMBERREVERSE_SOLENOID);
    leftLimit = new DigitalInput(1);
    rightLimit = new DigitalInput(2);
    climberGearLock.set(Value.kReverse);  
    lowerArm.config_kP(0, .08, 0);
    lowerArm.config_kI(0, 0.00, 0);
    lowerArm.config_kD(0, 0, 0);
    lowerArm.config_kF(0, 0, 0);
    upperArm.config_kP(0, .08, 0);
    upperArm.config_kI(0, 0.000, 0);
    upperArm.config_kD(0, 0, 0);
    upperArm.config_kF(0, 0, 0);

    lowerArm.configForwardSoftLimitThreshold(-8469); //8469
    lowerArm.configReverseSoftLimitThreshold(-245639); //245639

    upperArm.configForwardSoftLimitThreshold(243438);
    upperArm.configReverseSoftLimitThreshold(10263);

    lowerArm.configForwardSoftLimitEnable(true);
    lowerArm.configReverseSoftLimitEnable(true);

    upperArm.configForwardSoftLimitEnable(true);
    upperArm.configReverseSoftLimitEnable(true);

    lowerArm.setSelectedSensorPosition(0);
    upperArm.setSelectedSensorPosition(0);
  }

  public void toggleClimberGearLock() {
    if(climberGearLock.get() == Value.kReverse)
    {
      climberGearLock.set(Value.kForward);
    }
    else {
      climberGearLock.set(Value.kReverse);
    }
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putBoolean("Left Limit", leftLimit.get());
    SmartDashboard.putBoolean("Right Limit", rightLimit.get());
    SmartDashboard.putNumber("Left Encoder Pos", upperArm.getSelectedSensorPosition());
    SmartDashboard.putNumber("Right Encoder Pos", lowerArm.getSelectedSensorPosition());
  }

  public void moveLowerArm(double speed){
    lowerArm.set(ControlMode.PercentOutput, speed);
  }

  public void moveUpperArm(double speed){
    upperArm.set(ControlMode.PercentOutput, speed);
  }

  public TalonFX getLowerArm(){
    return lowerArm;
  }

  public TalonFX getUpperArm(){
    return upperArm;
  }

 
}
