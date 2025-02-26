// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Lift;
import frc.robot.subsystems.SwerveDrive;
import frc.robot.subsystems.Wrist;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;

/** An example command that uses an example subsystem. */
public class AutoCage2 extends Command {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final SwerveDrive m_Swerve;
  private final Arm m_Arm;
  private final Lift m_Lift;
  private final Wrist m_Wrist;
  private final Intake m_Intake;
  private final Timer timer;

  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public AutoCage2(SwerveDrive m_Swerve, Arm m_Arm ,Lift m_Lift, Wrist m_Wrist, Intake m_Intake) {
    this.m_Swerve = m_Swerve;
    this.m_Arm = m_Arm;
    this.m_Lift = m_Lift;
    this.m_Wrist = m_Wrist;
    this.m_Intake = m_Intake;
    timer = new Timer();
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(m_Swerve, m_Arm, m_Lift, m_Wrist, m_Intake);
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
    }else if (timer.get() > 1 && timer.get() < 4.5) {
      m_Lift.liftOut();
      m_Swerve.diagonalAngle(1.9, -2.8);//angulo
    }else if(timer.get() > 4.6 && timer.get() < 6.6){
      m_Arm.armOut();
    }else if(timer.get() > 6.7 && timer.get() < 8.3 ){
      m_Lift.goToFloor();
      m_Swerve.diagonalOdometri(1.9, -2.8);//distancia
    }else if (timer.get() > 8.4 && timer.get() < 8.5){
      m_Swerve.resetDrive();
      m_Swerve.driveOdometri(0);
    }else if(timer.get() > 8.6 && timer.get() < 9.6){
      m_Swerve.odometriTurn(0);
    }else if(timer.get() > 9.6 && timer.get() < 12.3){
      m_Lift.goToReef_L1();
      m_Arm.setToReef_L1();
      m_Wrist.horizontalWrist();
    }else if (timer.get() > 12.4 && timer.get() < 13.1){
      m_Intake.grabGamePiece(0.5);
    }else if(timer.get() > 13.1 && timer.get() < 15){
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
