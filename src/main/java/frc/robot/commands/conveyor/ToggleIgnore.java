/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.conveyor;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.ConveyorTalon;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class ToggleIgnore extends InstantCommand {
  /**
   * This command is used to control whether or not the conveyer will move the power cells when a cell is sensed.
   * 
   * This command is used for backing up power cells when preparing to shoot power cells into the power port. 
   * 
   * 
   */
  private String toggle;
  private ConveyorTalon conveyorTalon;
  public ToggleIgnore(ConveyorTalon conveyorTalon, boolean b) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.conveyorTalon = conveyorTalon;
    addRequirements(conveyorTalon);
    if(b)
    {
      toggle = "true";
    }
    else 
    {
      toggle = "false";
    }
    
  }

  public ToggleIgnore() {
    toggle = "";
  }

// Called when the command is initially scheduled.
  @Override
  public void initialize() {
    if(toggle.equals("true"))
    {
      conveyorTalon.toggleIgnore(true);
    }
    else if( toggle.equals("false"))
    {
      conveyorTalon.toggleIgnore(false);
    }
    else 
    {
      conveyorTalon.toggleIgnore(!conveyorTalon.isIgnored());
    }
    
  }
}
