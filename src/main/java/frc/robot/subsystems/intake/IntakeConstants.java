package frc.robot.subsystems.intake;

import com.ctre.phoenix6.configs.MotionMagicConfigs;
import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.signals.InvertedValue;

public class IntakeConstants {
    private static final double kA = 0.01;
    private static final double kD = 0.1;
    private static final double kI = 0;
    private static final double kP = 4.8;
    private static final double kS = 0.25;
    private static final double kV = 0.12;

    private static final double kMotionMagicCruiseVelocity = 80;
    private static final double kMotionMagicAcceleration = 160;
    private static final double kMotionMagicJerk = 1600;

    private static final Slot0Configs kPivotSlot0Configs = new Slot0Configs()
        .withKA(kA).withKD(kD).withKI(kI).withKP(kP).withKS(kS).withKV(kV);

    private static final MotionMagicConfigs kPivotMotionMagicConfigs = new MotionMagicConfigs()
        .withMotionMagicCruiseVelocity(kMotionMagicCruiseVelocity)
        .withMotionMagicAcceleration(kMotionMagicAcceleration)
        .withMotionMagicJerk(kMotionMagicJerk);

    public static final TalonFXConfiguration kPivotRightConfig = new TalonFXConfiguration()
        .withSlot0(kPivotSlot0Configs).withMotionMagic(kPivotMotionMagicConfigs)
        .withMotorOutput(new MotorOutputConfigs().withInverted(InvertedValue.CounterClockwise_Positive));

    public static final TalonFXConfiguration kPivotLeftConfig = new TalonFXConfiguration()
        .withSlot0(kPivotSlot0Configs).withMotionMagic(kPivotMotionMagicConfigs)
        .withMotorOutput(new MotorOutputConfigs().withInverted(InvertedValue.Clockwise_Positive));
    
    public static final TalonFXConfiguration kRollerConfig = new TalonFXConfiguration();

    public enum PivotState {
        ZERO(0),
        SCORE(15),
        GROUND(30);

        public final double position;

        PivotState(final double position) {
            this.position = position;
        }
    }

    public enum RollerState {
        INTAKE(-12),
        ZERO(0),
        OUTTAKE(9.5);

        public final double voltage;

        RollerState(final double voltage) {
            this.voltage = voltage;
        }
    }

    public static final int kPivotRightCAN = 20;
    public static final int kPivotLeftCAN = 21;
    public static final int kRollerCAN = 22;

    //String keys for SmartDashboard
    public static final String kPivotRightKey = "PivotRightPosition";
    public static final String kPivotLeftKey = "PivotLeftPosition";
    public static final String kRollerVoltageKey = "RollerVoltage";
    public static final String kPivotStateKey = "PivotState";
    public static final String kRollerStateKey = "RollerState";
}
