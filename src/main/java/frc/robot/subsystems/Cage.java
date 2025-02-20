// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Cage extends SubsystemBase {
  /** Creates a new ExampleSubsystem. */
  private final SparkMaxConfig left_cageConfig;
  private final SparkMax left_cageMotor;

  private final SparkMaxConfig right_cageConfig;
  private final SparkMax right_cageMotor;
  private final AbsoluteEncoder right_cageAbsoluteEncoder;
  
  public Cage() {
    left_cageConfig = new SparkMaxConfig();
    left_cageConfig.idleMode(IdleMode.kBrake);
    left_cageConfig.inverted(false);

    right_cageConfig = new SparkMaxConfig();
    right_cageConfig.idleMode(IdleMode.kBrake);
    right_cageConfig.inverted(true);

    left_cageMotor = new SparkMax(Constants.CageConstants.kLeftCageMotorID, MotorType.kBrushless);
    left_cageMotor.configure(left_cageConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

    right_cageMotor = new SparkMax(Constants.CageConstants.kRightCageMotorID, MotorType.kBrushless);
    right_cageAbsoluteEncoder = right_cageMotor.getAbsoluteEncoder();
    right_cageMotor.configure(right_cageConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
  }
  public void prepareToHang() {
    right_cageMotor.set(-0.5);
  }
  public void hang() {
    right_cageMotor.set(1);

    if (right_cageAbsoluteEncoder.getPosition() > 0.5) {
      left_cageMotor.set(1);
    }
  }
  public void stop() {
    right_cageMotor.set(0);
    left_cageMotor.set(0);
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
