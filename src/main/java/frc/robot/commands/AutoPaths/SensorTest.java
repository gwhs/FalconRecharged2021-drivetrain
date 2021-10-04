// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.AutoPaths;

import edu.wpi.first.wpilibj2.command.ConditionalCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.TrajectoryHelper;
import frc.robot.commands.conveyor.SenseNewPowerCell;
import frc.robot.commands.intake.IntakeSpeed;
import frc.robot.commands.swervedrive.Autonomous;
import frc.robot.subsystems.ConveyorTalon;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Drive.SwerveDriveSubsystem;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class SensorTest extends SequentialCommandGroup {
  /** Creates a new SensorTest. */
  public SensorTest(SwerveDriveSubsystem swerveDriveSubsystem, Intake intake, ConveyorTalon conveyor) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    super(
    new InstantCommand(intake::lowerIntake, intake),
    new Autonomous(swerveDriveSubsystem, TrajectoryHelper.createForwardPath().getTrajectory(), TrajectoryHelper.createForwardPath().getAngle()).withTimeout(2)
    .raceWith(new IntakeSpeed(intake, -.5)).raceWith(new SenseNewPowerCell(conveyor)),
    new ConditionalCommand(
      new AutoPath1(swerveDriveSubsystem), 
      new AutoPath2(swerveDriveSubsystem),
      conveyor::getHasSeen
    ),
    new IntakeSpeed(intake, 0)
    );
  }
}
