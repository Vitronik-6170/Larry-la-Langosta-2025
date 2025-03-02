// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.PowerDistribution.ModuleType;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.commands.Drive;
import frc.robot.commands.GrabGamePiece;

/**
 * The methods in this class are called automatically corresponding to each mode, as described in
 * the TimedRobot documentation. If you change the name of this class or the package after creating
 * this project, you must also update the Main.java file in the project.
 */
public class Robot extends TimedRobot {
  private Command m_autonomousCommand;

  private final RobotContainer m_robotContainer;
  @SuppressWarnings("unused")
  private final PowerDistribution pdh;
  private final UsbCamera camara1;
  private final Command drive, grabGamePiece;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  public Robot() {
    // Instantiate our RobotContainer.  This will perform all our button bindings, and put our
    // autonomous chooser on the dashboard.
    camara1 = CameraServer.startAutomaticCapture(0);
    camara1.setBrightness(30);
    camara1.setResolution(10, 10);
    camara1.setFPS(5);

    pdh = new PowerDistribution(1, ModuleType.kRev);

    m_robotContainer = new RobotContainer();

    drive = new Drive(m_robotContainer.m_swerveDrive);
    grabGamePiece = new GrabGamePiece(m_robotContainer.m_Intake);
    m_chooser.addOption("Auto cage 3", "A");
    m_chooser.addOption("Auto cage 2", "B");
    m_chooser.addOption("Auto cage 1", "C");
    m_chooser.addOption("Auto salir", "D");
    m_chooser.addOption("Auto Derecha Ranking", "E");
    m_chooser.addOption("Auto Izquierda Ranking", "F");
    m_chooser.addOption("Auto Centro", "G");
    m_chooser.addOption("Auto Izq", "H");
    SmartDashboard.putData(m_chooser);
  }

  /**
   * This function is called every 20 ms, no matter the mode. Use this for items like diagnostics
   * that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    // Runs the Scheduler.  This is responsible for polling buttons, adding newly-scheduled
    // commands, running already-scheduled commands, removing finished or interrupted commands,
    // and running subsystem periodic() methods.  This must be called from the robot's periodic
    // block in order for anything in the Command-based framework to work.
    CommandScheduler.getInstance().run();
  }

  /** This function is called once each time the robot enters Disabled mode. */
  @Override
  public void disabledInit() {
    m_robotContainer.m_swerveDrive.setCoast();
    m_robotContainer.m_Lift.setCoast();
    m_robotContainer.m_Arm.setCoast();
  }

  @Override
  public void disabledPeriodic() {
    m_robotContainer.m_Arm.stop();
  }

  /** This autonomous runs the autonomous command selected by your {@link RobotContainer} class. */
  @Override
  public void autonomousInit() {
    m_robotContainer.m_swerveDrive.setBrake();
    m_robotContainer.m_Lift.setBrake();
    m_robotContainer.m_Arm.setBrake();
    m_autonomousCommand = m_robotContainer.getAutonomousCommand(m_chooser.getSelected());
    
    // schedule the autonomous command (example)
    if (m_autonomousCommand != null) {
       m_autonomousCommand.schedule();
    }
    //auto.schedule();
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    //auto.schedule();
    //m_robotContainer.m_swerveDrive.noPTR();

  }

  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    m_robotContainer.m_swerveDrive.setBrake();
    m_robotContainer.m_Lift.setBrake();
    m_robotContainer.m_Arm.setBrake();
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
    
    if(drive != null){
      drive.schedule();
    }
    if(grabGamePiece != null){
      grabGamePiece.schedule();
    }   
  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
    //SmartDashboard.putNumber("BRAZO", m_robotContainer.m_Arm.getArmPosition());
  }

  @Override
  public void testInit() {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();
  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}

  /** This function is called once when the robot is first started up. */
  @Override
  public void simulationInit() {}

  /** This function is called periodically whilst in simulation. */
  @Override
  public void simulationPeriodic() {}
}
