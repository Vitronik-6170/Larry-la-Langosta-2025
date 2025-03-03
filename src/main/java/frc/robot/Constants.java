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
    public static final int kFrontLeftDriveID = 8;
    public static final int kFrontRightDriveID = 6;
    public static final int kRearLeftDriveID = 4;
    public static final int kRearRightDriveID = 2;

    public static final int kFrontLeftAngleID = 9;
    public static final int kFrontRightAngleID = 7;
    public static final int kRearLeftAngleID = 5;
    public static final int kRearRightAngleID = 3;
   
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
    public static final int kLeftLiftMotorID = 11;
    public static final int kRightLiftMotorID = 10;
    public static final double kLiftPower = 0.8;

    public static final double kLiftFloor = 0;

    public static final double kLEFTLiftReef_L1 = 70.81130981445;
    public static final double kLEFTLiftReef_L2 = 0;
    public static final double kLEFTLiftReef_L3 = 74.28549194335;
    public static final double kLEFTLiftAlgae = 50.85717391967;
    public static final double kLEFTLiftTop = 85.57318878173;

    public static final double kRIGHTLiftReef_L1 = 70.57318878173;
    public static final double kRIGHTLiftReef_L2 = 0;
    public static final double kRIGHTLiftReef_L3 = 74.04737472534;
    public static final double kRIGHTLiftAlgae = 50.54761886596;
    public static final double kRIGHTLiftTop = 85.57318878173;    
  }
  public static class IntakeConstants {
    public static final int kIntakeMotorID = 15;
    public static final double kIntakePower = 0.9;
  }
  public static class WristConstants {
    public static final int kWristMotorID = 16;
    public static final double kWristEncoderOffset = 0;
    public static final double kWristPower = 0.3;

    public static final double verticalWrist = 3*Math.PI/2;
    public static final double horizontalWrist = 0;

    public static final double kWristReeft_L1 = horizontalWrist;
    public static final double kWristReeft_L2 = verticalWrist;
    public static final double kWristReeft_L3 = verticalWrist;

  }
  public static class ArmConstants {
    public static final int kArmMotorID = 14;
    public static final double kArmEncoderOffset = 0.71;
    public static final double kArmPower = 0.8;

    public static final double kArmOut = 3;

    public static final double kArmFloor = Math.PI/3;
    public static final double kArmHuman = 2.778913307189941;

    public static final double kArmReef_L1 = 4.689863929748;
    public static final double kArmReef_L2 = 3.838535308837;
    public static final double kArmReef_L3 = 3.740459442138;
  }
  public static class CageConstants {
    public static final int kLeftCageMotorID = 12;
    public static final int kRightCageMotorID = 13;
    public static final double kCagePower = 0.1;

    public static final double kCageEncoderOffset = 0.5;
    public static final double kCageInit = 5.28;
    public static final double kCagePrepareToHang = 1.75;
    public static final double kCageHang =3.577;
    public static final double kCageMaxHang = 5.28;
    public static final double kCageMinHang = 1.26;

    
    public static final double kKaleb = 7;
    public static final double kRight_PowerCageHang = 0.5;
    public static final double kLeft_PowerCageHang = 0.5;

  }
  
}
