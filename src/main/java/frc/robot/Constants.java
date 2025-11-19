package frc.robot;

import static edu.wpi.first.units.Units.*;

import frc.robot.subsystems.drive.TunerConstants;

public class Constants {
    public static final int kControllerPort = 0;

    public static final double kMaxSpeed = TunerConstants.kSpeedAt12Volts.in(MetersPerSecond);
    public static final double kMaxAngularRate = RotationsPerSecond.of(7.5).in(RadiansPerSecond);
}
