// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.configs.CANcoderConfiguration;
import com.ctre.phoenix6.configs.MagnetSensorConfigs;
import com.ctre.phoenix6.signals.SensorDirectionValue;
import com.ctre.phoenix6.hardware.CANcoder;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class SwerveModule extends SubsystemBase {
  /** Creates a new ExampleSubsystem. */
  private final SparkMaxConfig driveConfig;
  private final SparkMaxConfig angleConfig;

  private final SparkMax driveMotor;
  private final SparkMax angleMotor;
  private final RelativeEncoder angleEncoder;
  private final RelativeEncoder driveEncoder;
  private final SparkClosedLoopController pidController;
  private final SparkClosedLoopController driveController;

  private final CANcoderConfiguration coderConfig;
  private final CANcoder absoluteEncoder;
  
  double reducespeed = 0.5;

  public SwerveModule(int driveID, int angleID, int encoderID, double offset) {
    
    driveConfig = new SparkMaxConfig();
    driveConfig.idleMode(IdleMode.kBrake);
    driveConfig.inverted(false);
    driveConfig.closedLoopRampRate(0.5);
    driveConfig.closedLoop.pid(0.1, 0.6, 1);
    driveConfig.closedLoop.iZone(0.0002);
    driveConfig.closedLoop.outputRange(-1, 1);

    angleConfig = new SparkMaxConfig();
    angleConfig.idleMode(IdleMode.kBrake);
    angleConfig.inverted(false);
    angleConfig.closedLoop.pid(0.1, 0, 0.1);
    angleConfig.closedLoop.outputRange(-1, 1);

    driveMotor = new SparkMax(driveID, MotorType.kBrushless);
    driveEncoder = driveMotor.getEncoder();
    angleMotor = new SparkMax(angleID, MotorType.kBrushless);
    angleEncoder = angleMotor.getEncoder();
    pidController = angleMotor.getClosedLoopController();
    driveController = driveMotor.getClosedLoopController();
    driveMotor.configure(driveConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    angleMotor.configure(angleConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

    coderConfig = new CANcoderConfiguration();
    coderConfig.MagnetSensor = new MagnetSensorConfigs();
    coderConfig.MagnetSensor.SensorDirection = SensorDirectionValue.CounterClockwise_Positive;
    coderConfig.MagnetSensor.AbsoluteSensorDiscontinuityPoint = 0.5;
    coderConfig.MagnetSensor.MagnetOffset = offset;

    absoluteEncoder = new CANcoder(encoderID);
    absoluteEncoder.getConfigurator().apply(coderConfig);
  }
  
  public void desiredState(double speed, double angle, double ticks){
    driveMotor.set(reducespeed * speed);
    pidController.setReference(angle*ticks, ControlType.kPosition);
  }
  public void desiredAngle(double angle){
    double ticks = 21;
    pidController.setReference(angle*ticks, ControlType.kPosition);
  }
  public void desiredDistance(double distance){
    double ticks = 6.8833, diameter = 0.1016;
    driveController.setReference((distance*ticks)/(Math.PI*diameter), ControlType.kPosition);
  }
  public void diagonal(double y, double x){
    double hipotenusa = (Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2)))*(x/Math.abs(x));
    desiredDistance(hipotenusa);
  }
  public double getDiagonalAngle(double y, double x){
    double angle = Math.toDegrees(Math.atan(y/x));
    return angle*0.5/180;
  }
  public void resetToAbsolute(double ticks){
    double offset = absoluteEncoder.getAbsolutePosition().getValueAsDouble()*ticks;
    pidController.setReference(offset, ControlType.kPosition);
    angleEncoder.setPosition(0);
  }
  public void resetEncoder(){
    driveEncoder.setPosition(0);
  }
  public void setCoast(){
    driveConfig.idleMode(IdleMode.kCoast);
    driveMotor.configure(driveConfig, com.revrobotics.spark.SparkBase.ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    angleConfig.idleMode(IdleMode.kCoast);
    angleMotor.configure(angleConfig, com.revrobotics.spark.SparkBase.ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
  }
  public void setBrake(){
    driveConfig.idleMode(IdleMode.kBrake);
    driveMotor.configure(driveConfig, com.revrobotics.spark.SparkBase.ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    angleConfig.idleMode(IdleMode.kBrake);
    angleMotor.configure(angleConfig, com.revrobotics.spark.SparkBase.ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
  }
  public void changeVelocityAuto(double speed){
    driveConfig.closedLoop.outputRange(-speed, speed);
    driveConfig.idleMode(IdleMode.kBrake);
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
