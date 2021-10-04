/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.conveyor;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ConveyorTalon;

public class SenseCell extends CommandBase {
  /**
   * Creates a new SenseCell.
   */
  private boolean seen;

  private ConveyorTalon conveyorTalon;

  public SenseCell(ConveyorTalon conveyorTalon) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(conveyorTalon);
    this.conveyorTalon = conveyorTalon;
    seen = false;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    seen = false;
    conveyorTalon.setHasSeen(false);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    seen = conveyorTalon.getStatus() && !conveyorTalon.isIgnored();
    //System.out.println("Checking if conveyor works: " + seen); //testing for bug in conveyor
    if(seen)
    {
      conveyorTalon.setHasSeen(true);
      conveyorTalon.setConveyorSpeed(-.5);
    }
    else
    {
      conveyorTalon.setConveyorSpeed(0);
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
