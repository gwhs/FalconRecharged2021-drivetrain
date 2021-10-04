/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;


public class SetShooterSpeed extends CommandBase {
  /**
   * Creates a new SetShooterSpeed.
   * 
   * Sets the Speed of the Shooter.
   * speed is in RPM
   * 
   */
  private double speed; 
  private Shooter shooter;
  private double pidF;
  private double pidP;
  private double pidI;
  private double pidD;


  /**
   * Makes the ball go back into the conveyor
   * @param shooter subsystem with a motor that shoots the power cells
   */
  public SetShooterSpeed(Shooter shooter) {
    this(shooter, -6000); // rpm
  }

  /**
   * Create command to control the shooter
   *  
   * @param shooter subsystem with a motor that shoots the power cells
   * @param rpm the speed motor spins, +# shoot the ball out, -# makes the ball goes back to the conveyor
   */
  public SetShooterSpeed(Shooter shooter, double rpm) {
    SmartDashboard.putNumber("Current Shooter RPM", 0);
    SmartDashboard.putNumber("Input Shooter RPM", SmartDashboard.getNumber("Input Shooter RPM", 3600));
    SmartDashboard.putNumber("Input pidF", SmartDashboard.getNumber("Input pidF", 0.04928));
    SmartDashboard.putNumber("Input pidP", SmartDashboard.getNumber("Input pidP", 0.1));
    SmartDashboard.putNumber("Input pidI", SmartDashboard.getNumber("Input pidI", 0));
    SmartDashboard.putNumber("Input pidD", SmartDashboard.getNumber("Input pidD", 2.5));
    this.shooter = shooter;
    this.speed = rpm;
    addRequirements(shooter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

    shooter.setMotorRPM(-this.speed);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
      //this.speed = SmartDashboard.getNumber("Input Shooter RPM", 3600);
      //shooter.setMotorRPM(-speed);
      shooter.setMotorRPM(-this.speed);
      this.pidF = SmartDashboard.getNumber("Input pidF", 0);
      this.pidP = SmartDashboard.getNumber("Input pidP", 0);
      this.pidI = SmartDashboard.getNumber("Input pidI", 0);
      this.pidD = SmartDashboard.getNumber("Input pidD", 0);
      shooter.getmotor1().config_kF(0, this.pidF, 0);
      shooter.getmotor2().config_kF(0, this.pidF, 0);

      shooter.getmotor1().config_kP(0, this.pidP, 0);
      shooter.getmotor2().config_kP(0, this.pidP, 0);

      shooter.getmotor1().config_kI(0, this.pidI, 0);
      shooter.getmotor2().config_kI(0, this.pidI, 0);

      shooter.getmotor1().config_kD(0, this.pidD, 0);
      shooter.getmotor2().config_kD(0, this.pidD, 0);
      
    SmartDashboard.putNumber("Current Shooter RPM", shooter.getMotorRPM());
    SmartDashboard.putNumber("pidF", this.pidF);
    SmartDashboard.putNumber("pidP", this.pidP);
    SmartDashboard.putNumber("pidI", this.pidI);
    SmartDashboard.putNumber("pidD", this.pidD);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    shooter.setMotorRPM(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
