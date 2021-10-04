/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.climber;

import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ClimberTalon;

public class MoveClimberArm extends CommandBase {
  /**
   * Creates a new MoveClimberArm.
   */
  private double initPos;
  private double targetPosition;
  private TalonFX arm;
  private double ticks;
  public MoveClimberArm(ClimberTalon climberTalon, double ticks, TalonFX arm) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.arm = arm;
    this.ticks = ticks;
    addRequirements(climberTalon);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    initPos = arm.getSelectedSensorPosition();
    targetPosition = initPos + ticks; // 1 inch = 6.03 ticks
    arm.set(TalonFXControlMode.Position, targetPosition);
    //arm.getPIDController().setReference(targetPosition, ControlType.kPosition);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    System.out.println("Actual Pos:" + this.arm.getSelectedSensorPosition());
    System.out.println("Expected Pos:" + targetPosition);
    System.out.println("Diff " + (targetPosition - this.arm.getSelectedSensorPosition()));
  }


  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    
    return Math.abs(targetPosition - arm.getSelectedSensorPosition()) < 500;

  }
}
