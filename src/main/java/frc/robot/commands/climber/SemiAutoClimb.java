/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.climber;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.ClimberTalon;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class SemiAutoClimb extends SequentialCommandGroup {
  /**
   * Creates a new SemiAutoClimb.
   */

  public SemiAutoClimb(ClimberTalon climberTalon) {
    // Add your commands in the super() call, e.g.
    // super(new FooCommand(), new BarCommand());
    super(new MoveClimberArm(climberTalon, 243438, climberTalon.getUpperArm()),
          new MoveClimberArm(climberTalon, -245639,climberTalon.getLowerArm()));

  }
}
