/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.climber;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.ClimberTalon;

public class MoveBothClimberArms extends CommandBase {
  /**
   * Creates a new MoveBothClimberArms.
   */
  private double distance;
  private ClimberTalon climberTalon;
  private TalonFX masterArm;
  private TalonFX slaveArm;
  private double initPos;
  private double targetPos;

  public MoveBothClimberArms(ClimberTalon climberTalon, double ticks) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(climberTalon);
    this.climberTalon = climberTalon;
    masterArm = climberTalon.getUpperArm();
    slaveArm = climberTalon.getUpperArm();
    distance = ticks;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    slaveArm.setInverted(InvertType.OpposeMaster);
    slaveArm.set(ControlMode.Follower, Constants.CLIMBER1_TALON);
    initPos = masterArm.getSelectedSensorPosition();
    targetPos = initPos + distance;
    masterArm.set(TalonFXControlMode.Position, targetPos);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return Math.abs(targetPos - masterArm.getSelectedSensorPosition()) < 600;
  }
}
