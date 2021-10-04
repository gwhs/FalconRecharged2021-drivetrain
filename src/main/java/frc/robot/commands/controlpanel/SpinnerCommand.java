/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.controlpanel;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Color.ColorPanelSpinner;

public class SpinnerCommand extends CommandBase {
  /**
   * Creates a new SpinnerCommand.
   */
  private ColorPanelSpinner mColorPanelSpinner;
  private XboxController mXboxController;
  public SpinnerCommand(ColorPanelSpinner cps, XboxController operatorController) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.mColorPanelSpinner = cps;
    this.mXboxController = operatorController;
    addRequirements(mColorPanelSpinner);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double rightTriggerSpeed = mXboxController.getTriggerAxis(Hand.kRight);
    double leftTriggerSpeed = mXboxController.getTriggerAxis(Hand.kLeft);
    if (leftTriggerSpeed >= 0.5 && rightTriggerSpeed <= 0.5)
    {
      mColorPanelSpinner.spin(0.25);
      //leftTriggerSpeed = mXboxController.getTriggerAxis(Hand.kLeft);
    }
    else if (rightTriggerSpeed >= 0.5 && leftTriggerSpeed <= 0.5)
    {
      mColorPanelSpinner.spin(-0.25);
    }
    else
    {
      mColorPanelSpinner.spin(0);
    }


   // mColorPanelSpinner.spin(leftTriggerSpeed);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    mColorPanelSpinner.spin(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
