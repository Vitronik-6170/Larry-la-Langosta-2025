// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
  public static class OperatorConstants {
    public static final int kDriverControllerPort = 0;
    public static final int kMechanismsControllerPort = 1;
  }
  public static class DriveConstants {
    public static final int kFrontLeftDriveID = 2;
    public static final int kFrontRightDriveID = 4;
    public static final int kRearLeftDriveID = 6;
    public static final int kRearRightDriveID = 8;

    public static final int kFrontLeftAngleID = 3;
    public static final int kFrontRightAngleID = 5;
    public static final int kRearLeftAngleID = 7;
    public static final int kRearRightAngleID = 9;
   
    public static final int kFrontLeftEncoderID = 1;
    public static final int kFrontRightEncoderID = 2;
    public static final int kRearLeftEncoderID = 3;
    public static final int kRearRightEncoderID = 4;

    public static final double kOffsetFrontLeft = -0.35205078125;
    public static final double kOffsetFrontRight = -0.30126953125;
    public static final double kOffsetRearLeft = -0.419189453125;
    public static final double kOffsetRearRight = 0.438720703125;

    public static final double kTicksFrontLeft = 21;
    public static final double kTicksFrontRight = 21;
    public static final double kTicksRearLeft = 21;
    public static final double kTicksRearRight = 21;
  }
  public static class LiftConstants {
    public static final int kLeftLiftMotorID = 10;
    public static final int kRightLiftMotorID = 11;
    public static final double kLiftPower = 0.9;

    public static final double kLiftReef_L1 = 1;
    public static final double kLiftReef_L2 = 2;
    public static final double kLiftReef_L3 = 3;
  }
  public static class IntakeConstants {
    public static final int kIntakeMotorID = 12;
    public static final double kIntakePower = 0.9;
  }
  public static class WristConstants {
    public static final int kWristMotorID = 13;
    public static final double kWristEncoderOffset = 0;
    public static final double kWristPower = 0.3;
  }
  public static class ArmConstants {
    public static final int kArmMotorID = 14;
    public static final double kArmEncoderOffset = 0.71;
    public static final double kArmPower = 0.2;

    public static final double kArmFloor = 0;
    public static final double kArmHuman = 0;

    public static final double kArmReef_L1 = 0;
    public static final double kArmReef_L2 = 0;
    public static final double kArmReef_L3 = 0;
  }
  public static class CageConstants {
    public static final int kLeftCageMotorID = 15;
    public static final int kRightCageMotorID = 16;
    public static final double kCagePower = 0.9;
  }
  
}
