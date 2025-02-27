// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Lift;
import frc.robot.subsystems.SwerveDrive;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;

/** An example command that uses an example subsystem. */
public class AutoDerechaRanking extends Command {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final SwerveDrive m_Swerve;
  private final Arm m_Arm;
  private final Lift m_Lift;

  private final Timer timer;

  /**
  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public AutoDerechaRanking(SwerveDrive m_Swerve, Arm m_Arm ,Lift m_Lift) {
    this.m_Swerve = m_Swerve;
    this.m_Arm = m_Arm;
    this.m_Lift = m_Lift;
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
    }else if (timer.get() > 1 && timer.get() < 2) {
      m_Lift.liftOut();
    }else if(timer.get() > 2.1 && timer.get() < 4.1){
      m_Arm.armOut();
    }else if(timer.get() > 4.2 && timer.get() < 5.5 ){
      m_Lift.goToFloor();
      m_Swerve.driveOdometri(1);
    }else if (timer.get() > 5.6 && timer.get() < 5.7){
      m_Swerve.resetDrive();
      m_Swerve.driveOdometri(0);
    }else if(timer.get() > 5.8 && timer.get() < 6.5){
      m_Swerve.angleOdometri(0.25);
    }else if(timer.get() > 6.6 && timer.get() < 8.5){
      m_Swerve.driveOdometri(1);
    }else if (timer.get() > 8.6 && timer.get() < 8.7){
      m_Swerve.angleOdometri(0);
    }else if(timer.get() > 8.8 && timer.get() < 9.9){
      m_Swerve.odometriTurn(0);
    }else if(timer.get() > 10 && timer.get() < 15){
      m_Swerve.reducespeedAuto(0.2);
      m_Swerve.driveOdometri(-5);
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
