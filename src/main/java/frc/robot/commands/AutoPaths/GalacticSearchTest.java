/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.AutoPaths;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.TrajectoryHelper;
import frc.robot.commands.intake.IntakeSpeed;
import frc.robot.commands.swervedrive.Autonomous;
import frc.robot.commands.swervedrive.TurnToAngleProfiled;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Drive.SwerveDriveSubsystem;


// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class GalacticSearchTest extends SequentialCommandGroup {
  /**
   * Creates a new AutoPath1.
   * 
   * 
   * This is just an Auto used for general testing. 
   */
  public GalacticSearchTest(SwerveDriveSubsystem swerveDriveSubsystem, Intake intake) {  // test forward path
   
    super(
        new InstantCommand(intake::lowerIntake, intake),
        new ParallelCommandGroup(
          new IntakeSpeed(intake, -.5),
          new SequentialCommandGroup(
            new Autonomous(swerveDriveSubsystem, TrajectoryHelper.createForwardPath().getTrajectory(), TrajectoryHelper.createForwardPath().getAngle()).withTimeout(2),
            new TurnToAngleProfiled(-45, swerveDriveSubsystem).withTimeout(2),
            new Autonomous(swerveDriveSubsystem, TrajectoryHelper.createForwardPath().getTrajectory(), TrajectoryHelper.createForwardPath().getAngle()).withTimeout(2),
            new TurnToAngleProfiled(90, swerveDriveSubsystem).withTimeout(3),
            new Autonomous(swerveDriveSubsystem, TrajectoryHelper.createForwardPath().getTrajectory(), TrajectoryHelper.createForwardPath().getAngle()).withTimeout(2),
            new TurnToAngleProfiled(-45, swerveDriveSubsystem).withTimeout(2),
            new Autonomous(swerveDriveSubsystem, TrajectoryHelper.createForwardPath().getTrajectory(), TrajectoryHelper.createForwardPath().getAngle()).withTimeout(2))
          ).withTimeout(12),
        new InstantCommand(intake::stopIntake,intake)
    );
  }
  
}
