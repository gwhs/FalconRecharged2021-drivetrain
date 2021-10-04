/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;


public class Shooter extends SubsystemBase {
  /**
   * Creates a new ShooterMotor.
   */
  private TalonFX motor1;
  private TalonFX motor2; 
  private double tickConversion;
  private double pidP = 0.1;
  private double pidI = 0;
  private double pidD = 2.5;
  private double pidF = 0.04928;
  private boolean maintainRPM = false;


  // The PID values below work for inner port at 3600 RPM <- Input Num
  // 3325 Works 1 Robot Back <- Input Actual RPM (3300)



  public Shooter() {
    tickConversion = 3.4133;//Calculated to convert RPM to ticks/100ms
    motor1 = new TalonFX(Constants.SHOOTER1_TALON);
    motor2 = new TalonFX(Constants.SHOOTER2_TALON);
    motor1.config_kP(0, pidP, 0);
    motor1.config_kI(0, pidI, 0);
    motor1.config_kD(0, pidD, 0);
    motor1.config_kF(0, pidF, 0);
    
    motor2.setInverted(true); //motor1 and motor2 driven in opposite directions 
    motor1.setInverted(false); 
    motor2.set(ControlMode.Follower, Constants.SHOOTER1_TALON); // motor2 follow motor1 to spin at the same speed
    
    motor1.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);
    
    //motor1.configClosedloopRamp(0.25);

    motor1.setNeutralMode(NeutralMode.Coast);
    motor2.setNeutralMode(NeutralMode.Coast);
    //motor1.configClosedloopRamp(0); // may be what we are looking for to ramp Velocity
  }

  public void toggleShooter(double rpm) {
    if(maintainRPM) { 
      maintainRPM = false;
      setMotorPower(0);
    }
    else {
      maintainRPM = true;
      setMotorRPM(rpm);
      
    }
  } 

/**
 * spins the shooter motor as a percentage of its power
 * to do: verify which way motor spins
 * @param power number between -1 and 1
 */
  public void setMotorPower(double power)
  {
    motor1.set(ControlMode.PercentOutput, -power);
    //motor2.set(ControlMode.PercentOutput, power);
    
    //motor1.set(ControlMode.MotionMagic, targetPos, DemandType.ArbitraryFeedForward, feedforward);
       
  }

  /**
   * sets the motor speed in velocity
   * to do: verify behavior of postive and negative rpm
   * @param rpm speed of motor (number between -6000, 6000?)
   */
  public void setMotorRPM(double rpm) // in rotations per minute
  {
    motor1.set(ControlMode.Velocity, (rpm*tickConversion)); // Velocity based
  }

  // public void setMagicSpeed(double speed) // in RPM
  // {
  //   motor1.configMotionCruiseVelocity((int)(speed*3.4133));
  //   motor1.configMotionAcceleration(0); // we dont know
  //   motor1.set(ControlMode.MotionMagic, 0);
  // }

  /**
   * gets the speed of the motor
   * @return rotations per minute?
   */
  public double getMotorRPM()
  {
    return (motor1.getSelectedSensorVelocity()/tickConversion);
  }

  public TalonFX getmotor1()
  {
    return this.motor1;
  }

  public TalonFX getmotor2()
  {
    return this.motor2;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

}
