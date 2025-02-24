// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

import com.studica.frc.AHRS;
import com.studica.frc.AHRS.NavXComType;

public class SwerveDrive extends SubsystemBase {
  /** Creates a new ExampleSubsystem. */
  private final AHRS navx;

  private final SwerveModule frontLeft;
  private final SwerveModule frontRight;
  private final SwerveModule rearLeft;
  private final SwerveModule rearRight;

  public SwerveDrive() {
    navx = new AHRS(NavXComType.kMXP_SPI);

    frontLeft = new SwerveModule(Constants.DriveConstants.kFrontLeftDriveID, Constants.DriveConstants.kFrontLeftAngleID, Constants.DriveConstants.kFrontLeftEncoderID, Constants.DriveConstants.kOffsetFrontLeft);
    frontRight = new SwerveModule(Constants.DriveConstants.kFrontRightDriveID, Constants.DriveConstants.kFrontRightAngleID, Constants.DriveConstants.kFrontRightEncoderID, Constants.DriveConstants.kOffsetFrontRight);
    rearLeft = new SwerveModule(Constants.DriveConstants.kRearLeftDriveID, Constants.DriveConstants.kRearLeftAngleID, Constants.DriveConstants.kRearLeftEncoderID, Constants.DriveConstants.kOffsetRearLeft);
    rearRight = new SwerveModule(Constants.DriveConstants.kRearRightDriveID, Constants.DriveConstants.kRearRightAngleID, Constants.DriveConstants.kRearRightEncoderID, Constants.DriveConstants.kOffsetRearRight);
  }

  public void drive(double x1, double y1, double x2){
    int L = 1, W = 1;
    double r = Math.sqrt((L*L)+(W*W));
    y1 *= -1;
    
    if (Math.abs(x1) < 0.15 && Math.abs(y1) < 0.15 && Math.abs(x2) < 0.15) {
        x1 = 0; y1 = 0; x2 = 0;
    }

    double angleNaVX = Math.toRadians((navx.getAngle())%360);

    double matrizR[][] = new double[2][2];
    double ejesR[][] = new double[1][2];

    matrizR[0][0] = Math.cos(angleNaVX);
    matrizR[0][1] = -Math.sin(angleNaVX);
    matrizR[1][0] = Math.sin(angleNaVX);
    matrizR[1][1] = Math.cos(angleNaVX);
    ejesR[0][0] = ((matrizR[0][0]*x1)+(matrizR[0][1]*y1));     //X
    ejesR[0][1] = ((matrizR[1][0]*x1)+(matrizR[1][1]*y1));    // Y
    double a = ejesR[0][0] - x2 * (L / r);
    double b = ejesR[0][0] + x2 * (L / r);
    double c = ejesR[0][1] - x2 * (W / r);
    double d = ejesR[0][1] + x2 * (W / r);
    // Cálculo de las velocidades
    double backLeftSpeed = Math.sqrt((a * a) + (d * d));
    double backRightSpeed = Math.sqrt((a * a) + (c * c));
    double frontLeftSpeed = Math.sqrt((b * b) + (d * d));
    double frontRightSpeed = Math.sqrt((b * b) + (c * c));

    double maxSpeed = Math.max(Math.max(backLeftSpeed, backRightSpeed), Math.max(frontLeftSpeed, frontRightSpeed));

    frontLeftSpeed = maxSpeed;
    frontRightSpeed = maxSpeed;
    backLeftSpeed = maxSpeed;
    backRightSpeed = maxSpeed;

    // Cálculo de los ángulos en radianes y normalización entre -0.5 y 0.5
    double backLeftAngle = normalizeAngle(Math.atan2(a, d) / (2 * Math.PI));
    double backRightAngle = normalizeAngle(Math.atan2(a, c) / (2 * Math.PI));
    double frontLeftAngle = normalizeAngle(Math.atan2(b, d) / (2 * Math.PI));
    double frontRightAngle = normalizeAngle(Math.atan2(b, c) / (2 * Math.PI));

    // Optimización de ángulos y ajuste de velocidades
    if (Math.abs(backRightAngle) > 0.25) {
        backRightSpeed = -backRightSpeed;
        backRightAngle = normalizeAngle(backRightAngle + 0.5);
    }
    if (Math.abs(backLeftAngle) > 0.25) {
        backLeftSpeed = -backLeftSpeed;
        backLeftAngle = normalizeAngle(backLeftAngle + 0.5);
    }
    if (Math.abs(frontRightAngle) > 0.25) {
        frontRightSpeed = -frontRightSpeed;
        frontRightAngle = normalizeAngle(frontRightAngle + 0.5);
    }
    if (Math.abs(frontLeftAngle) > 0.25) {
        frontLeftSpeed = -frontLeftSpeed;
        frontLeftAngle = normalizeAngle(frontLeftAngle + 0.5);
    }

    if (Math.abs(x2)<0.15){
      double angleProm = (backLeftAngle + backRightAngle + frontLeftAngle + frontRightAngle) / 4;
      frontLeftAngle = angleProm;
      frontRightAngle = angleProm;
      backLeftAngle = angleProm;
      backRightAngle = angleProm;
    }

    frontLeft.desiredState(frontLeftSpeed, frontLeftAngle, Constants.DriveConstants.kTicksFrontLeft);
    frontRight.desiredState(frontRightSpeed, frontRightAngle, Constants.DriveConstants.kTicksFrontRight);
    rearLeft.desiredState(backLeftSpeed, backLeftAngle, Constants.DriveConstants.kTicksRearLeft);
    rearRight.desiredState(backRightSpeed, backRightAngle, Constants.DriveConstants.kTicksRearRight);
  }

  private double normalizeAngle(double angle) {
    angle %= 1;
    if (angle > 0.5) angle -= 1.0;
    if (angle < -0.5) angle += 1.0;
    return angle;
  }
  
  public void odometriTurn(double angle){
    double error = 1.5;  // Ajusta el rango de error a un valor más pequeño
    double absoluteAngle = (navx.getAngle() % 360);
    System.out.println("Angle: " + angle);
    System.out.println("Absolute Angle: " + absoluteAngle);

    double angleDifference = ((angle - absoluteAngle + 360) % 360);
    if (angleDifference > 180) {
        angleDifference -= 360;
    }

    if (Math.abs(angleDifference) < error) {
        // Dentro del rango de error, detener el movimiento
        System.out.println("Angle within error range, stopping");
        frontLeft.desiredState(0, 0, Constants.DriveConstants.kTicksFrontLeft);
        frontRight.desiredState(0, 0, Constants.DriveConstants.kTicksFrontRight);
        rearLeft.desiredState(0, 0, Constants.DriveConstants.kTicksRearLeft);
        rearRight.desiredState(0, 0, Constants.DriveConstants.kTicksRearRight);
    } else if (angleDifference > 0) {
        // Ajuste en sentido horario
        if (Math.abs(angleDifference) < error * 10) {
            System.out.println("Small adjustment clockwise");
            frontLeft.desiredState(0, 0.0125, Constants.DriveConstants.kTicksFrontLeft);
            frontRight.desiredState(0, -0.125, Constants.DriveConstants.kTicksFrontRight);
            rearLeft.desiredState(0, -0.125, Constants.DriveConstants.kTicksRearLeft);
            rearRight.desiredState(0, 0.125, Constants.DriveConstants.kTicksRearRight);
        } else {
            System.out.println("Large adjustment clockwise");
            frontLeft.desiredState(0.4, 0.125, Constants.DriveConstants.kTicksFrontLeft);
            frontRight.desiredState(-0.4, -0.125, Constants.DriveConstants.kTicksFrontRight);
            rearLeft.desiredState(0.4, -0.125, Constants.DriveConstants.kTicksRearLeft);
            rearRight.desiredState(-0.4, 0.125, Constants.DriveConstants.kTicksRearRight);
        }
    } else {
        // Ajuste en sentido antihorario
        if (Math.abs(angleDifference) < error * 10) {
            System.out.println("Small adjustment counterclockwise");
            frontLeft.desiredState(0, 0.125, Constants.DriveConstants.kTicksFrontLeft);
            frontRight.desiredState(0, -0.125, Constants.DriveConstants.kTicksFrontRight);
            rearLeft.desiredState(0, -0.125, Constants.DriveConstants.kTicksRearLeft);
            rearRight.desiredState(0, 0.125, Constants.DriveConstants.kTicksRearRight);
        } else {
            System.out.println("Large adjustment counterclockwise");
            frontLeft.desiredState(-0.4, 0.125, Constants.DriveConstants.kTicksFrontLeft);
            frontRight.desiredState(0.4, -0.125, Constants.DriveConstants.kTicksFrontRight);
            rearLeft.desiredState(-0.4, -0.125, Constants.DriveConstants.kTicksRearLeft);
            rearRight.desiredState(0.4, 0.125, Constants.DriveConstants.kTicksRearRight);
        }
    }
}

  public void noPTR(){
    frontLeft.resetToAbsolute(Constants.DriveConstants.kTicksFrontLeft);
    frontRight.resetToAbsolute(Constants.DriveConstants.kTicksFrontRight);
    rearLeft.resetToAbsolute(Constants.DriveConstants.kTicksRearLeft);
    rearRight.resetToAbsolute(Constants.DriveConstants.kTicksRearRight);
  }
  public void setCoast(){
    frontLeft.setCoast();
    frontRight.setCoast();
    rearLeft.setCoast();
    rearRight.setCoast();
  }
  public void setBrake(){
    frontLeft.setBrake();
    frontRight.setBrake();
    rearLeft.setBrake();
    rearRight.setBrake();
  }
  public void angleOdometri(double angle){
    frontLeft.desiredAngle(angle);
    frontRight.desiredAngle(angle);
    rearLeft.desiredAngle(angle);
    rearRight.desiredAngle(angle);
  }
  public void driveOdometri(double distance){
    frontLeft.desiredDistance(distance);
    frontRight.desiredDistance(distance);
    rearLeft.desiredDistance(distance);
    rearRight.desiredDistance(distance);
  }
  public void diagonalAngle(double y, double x){
    frontLeft.desiredAngle(frontLeft.getDiagonalAngle(y,x));
    frontRight.desiredAngle(frontRight.getDiagonalAngle(y,x));
    rearLeft.desiredAngle(rearLeft.getDiagonalAngle(y,x));
    rearRight.desiredAngle(rearRight.getDiagonalAngle(y,x));
  }
  public void diagonalOdometri(double y, double x){
    frontLeft.diagonal(y, x);
    frontRight.diagonal(y, x);
    rearLeft.diagonal(y, x);
    rearRight.diagonal(y, x);
  }
  public void turn(){
    frontLeft.desiredAngle(0.125);
    frontRight.desiredAngle(-0.125);
    rearLeft.desiredAngle(-0.125);
    rearRight.desiredAngle(0.125);
  }
  public void resetDrive(){
    frontLeft.resetEncoder();
    frontRight.resetEncoder();
    rearLeft.resetEncoder();
    rearRight.resetEncoder();
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
