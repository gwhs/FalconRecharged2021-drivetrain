/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.climber;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.ClimberTalon;

public class ToggleClimberGearLock extends InstantCommand {
  /**
   * Creates a new ToggleClimberGearLock.
   */
  private ClimberTalon climber;
  public ToggleClimberGearLock(ClimberTalon climber) {
    this.climber = climber;
    addRequirements(climber);
    
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    climber.toggleClimberGearLock();
  }
}