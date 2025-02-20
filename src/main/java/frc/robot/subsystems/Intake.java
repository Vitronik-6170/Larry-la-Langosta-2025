// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Intake extends SubsystemBase {
  /** Creates a new ExampleSubsystem. */

  private final SparkMaxConfig intakeConfig;
  private final SparkMax intakeMotor;

  private int powerLevel=0;
  private static final double[] POWER_LEVELS = {0, 0.2, 1.0}; // Define power levels

  public Intake() {
    intakeConfig = new SparkMaxConfig();
    intakeConfig.idleMode(IdleMode.kCoast);
    intakeConfig.inverted(false);

    intakeMotor = new SparkMax(Constants.IntakeConstants.kIntakeMotorID, MotorType.kBrushless);
    intakeMotor.configure(intakeConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
  }
  // public void grabGamePiece(boolean isPressed){
  //   intakeMotor.set(Constants.IntakeConstants.kIntakePower);
  // }
  // public void releaseGamePiece(){
  //   intakeMotor.set(-Constants.IntakeConstants.kIntakePower);
  // }
  // public void stopIntake(){
  //   intakeMotor.set(0);
  // }

  public void increasePowerLevel() {
    if (powerLevel < POWER_LEVELS.length - 1) {
      powerLevel++;
    }
    setPowerLevel();
  }

  public void decreasePowerLevel() {
    if (powerLevel > 0) {
      powerLevel--;
    }
    setPowerLevel();
  }

  private void setPowerLevel() {
    double power = POWER_LEVELS[powerLevel];
    intakeMotor.set(power);
  }

  /**
   * Example command factory method.
   *
   * @return a command
   */
  public Command exampleMethodCommand() {
    // Inline construction of command goes here.
    // Subsystem::RunOnce implicitly requires `this` subsystem.
    return runOnce(
        () -> {
          /* one-time action goes here */
        });
  }

  /**
   * An example method querying a boolean state of the subsystem (for example, a digital sensor).
   *
   * @return value of some boolean subsystem state, such as a digital sensor.
   */
  public boolean exampleCondition() {
    // Query some boolean state, such as a digital sensor.
    return false;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
