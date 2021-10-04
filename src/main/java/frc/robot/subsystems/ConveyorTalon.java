/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.playingwithfusion.TimeOfFlight;

public class ConveyorTalon extends SubsystemBase {
  /**
   * Creates a new ConveyorTalon.
   */
  private TimeOfFlight sensor;
  private TalonFX indexer; 
  private boolean ignore;
  private boolean hasSeen;
  private boolean on = false;;

  public ConveyorTalon() {
    sensor = new TimeOfFlight(2);// to change ID for TOF sensor go to...      ipOfRoboRio:5812
    sensor.setRangingMode(TimeOfFlight.RangingMode.Short, 0.24); //0.24 incorrect ?
    indexer = new TalonFX(Constants.CONVEYOR_TALON);
    indexer.config_kP(0, .2, 0);
    indexer.config_kI(0, .0001, 0);
    ignore = false;
    hasSeen = false;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    printStuff();
  }

  private void printStuff() {
    SmartDashboard.putBoolean("Sensor State", getStatus());
    SmartDashboard.putNumber("Ticks", indexer.getSelectedSensorPosition());
    SmartDashboard.putNumber("Distance", getDistance());
    SmartDashboard.putBoolean("Ignored", isIgnored());
    SmartDashboard.putNumber("Conveyor Current", indexer.getStatorCurrent());
    SmartDashboard.putBoolean("Has Seen", hasSeen);
  }

  // checks if there is a powercell in the conveyor within 5.3 inches from the sensor
  public boolean getStatus() {
    return getDistance() < 5.3; 
  }

  public boolean isIgnored() {
    return ignore;
  }

  public void toggleIgnore(boolean toggle)
  {
    System.out.println("toggling ignore, old value = " + toggle);
    ignore = toggle;
  }

  //* @return Distance to powercell in inches
  // TO DO: find the mounting position and direction of the sensor
  public double getDistance()
  {
    return sensor.getRange()*0.0393701;
  }

  public void setConveyorPosition(double ticks){
    indexer.set(TalonFXControlMode.Position, ticks);
  }

  public void setConveyorSpeed(double speed){
    indexer.set(ControlMode.PercentOutput, speed);
  }

  public double getPosition(){
    return indexer.getSelectedSensorPosition();
  }

  public boolean getHasSeen() {
    return hasSeen;
  }

  public void setHasSeen(boolean bol) {
      hasSeen = bol;
  }

  public void toggleConveyor(double speed) {
    on = !on;
    if(on) {
      setConveyorSpeed(speed);
    }
    else {
      setConveyorSpeed(0);
    }
  }

}
