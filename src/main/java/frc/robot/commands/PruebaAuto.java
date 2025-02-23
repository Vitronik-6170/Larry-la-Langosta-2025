// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.SwerveDrive;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;

/** An example command that uses an example subsystem. */
public class PruebaAuto extends Command {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final SwerveDrive m_Swerve;
  private final Timer timer;

  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public PruebaAuto(SwerveDrive m_Swerve) {
    this.m_Swerve = m_Swerve;
    timer = new Timer();
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(m_Swerve);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    timer.reset();
    timer.start();
    m_Swerve.resetDrive();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(timer.get()<1){
      m_Swerve.noPTR();
      m_Swerve.resetDrive();
      m_Swerve.driveOdometri(0);
    }else if (timer.get() > 1 && timer.get() < 1.3) {
      m_Swerve.diagonalAngle(-1, -3);
    }else if(timer.get() > 1.2 && timer.get() < 5.1){
      m_Swerve.diagonalOdometri(-1, -3);
    }else if(timer.get() > 5.2 && timer.get() < 5.3){
     m_Swerve.resetDrive();
     m_Swerve.driveOdometri(0);
    }else if (timer.get() > 5.3 && timer.get() < 5.6) {
      m_Swerve.odometriTurn(90);
    }else if(timer.get() > 5.6 && timer.get() < 8.2){
      //m_Swerve.strafe(1);
    }else if(timer.get() > 8.2 && timer.get() < 8.3){
      //m_Swerve.resetDrive();
      //m_Swerve.strafe(0);
    }else if (timer.get() > 8.3 && timer.get() < 8.6) {
      //m_Swerve.angleOdometri(0);
    }else if(timer.get() > 8.6 && timer.get() < 10.3){
      //m_Swerve.forward(-1);
    }else if(timer.get() > 10.3 && timer.get() < 10.4){
      //m_Swerve.resetDrive();
      //m_Swerve.forward(0);
    }else if (timer.get() > 10.4 && timer.get() < 10.7) {
      //m_Swerve.angleOdometri(0.25);
    }else if(timer.get() > 10.7 && timer.get() < 12.7){
      //m_Swerve.strafe(-1);
    }else if(timer.get() > 12.7 && timer.get() < 12.8){
      //m_Swerve.resetDrive();
     // m_Swerve.strafe(0);
    }else if (timer.get() > 12.8 && timer.get() < 13.1) {
      //m_Swerve.angleOdometri(0.125);
    }
    else if(timer.get() > 13.1 && timer.get() < 15){
      
    }else{
      m_Swerve.drive(0, 0, 0);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
