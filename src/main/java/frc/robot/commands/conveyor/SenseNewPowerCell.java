// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.conveyor;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ConveyorTalon;

public class SenseNewPowerCell extends CommandBase { // do we use this class? get rid of ??
  /** Creates a new newBallSeen. */
  
  private ConveyorTalon conveyorTalon;

  public SenseNewPowerCell(ConveyorTalon cvt) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.conveyorTalon = cvt;
    addRequirements(conveyorTalon);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(conveyorTalon.getStatus()) {
      conveyorTalon.setHasSeen(true);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
