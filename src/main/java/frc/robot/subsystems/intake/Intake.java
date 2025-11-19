package frc.robot.subsystems.intake;

import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.intake.IntakeConstants.PivotState;
import frc.robot.subsystems.intake.IntakeConstants.RollerState;

public class Intake extends SubsystemBase {
    private final TalonFX m_pivotRight = new TalonFX(IntakeConstants.kPivotRightCAN);
    private final TalonFX m_pivotLeft = new TalonFX(IntakeConstants.kPivotLeftCAN);
    private final TalonFX m_roller = new TalonFX(IntakeConstants.kRollerCAN);

    private final MotionMagicVoltage m_request = new MotionMagicVoltage(0);

    private PivotState m_pivotState = PivotState.ZERO;
    private RollerState m_rollerState = RollerState.ZERO;

    public Intake() {
        m_pivotRight.getConfigurator().apply(IntakeConstants.kPivotRightConfig);
        m_pivotLeft.getConfigurator().apply(IntakeConstants.kPivotLeftConfig);   
        m_roller.getConfigurator().apply(IntakeConstants.kRollerConfig);

        m_pivotRight.setNeutralMode(NeutralModeValue.Brake);
        m_pivotLeft.setNeutralMode(NeutralModeValue.Brake);
    }

    public Command setState(PivotState pivotState) {
        return Commands.runOnce(()->{
            m_pivotState = pivotState;
        });
    }

    public Command setState(RollerState rollerState) {
        return Commands.runOnce(()->{
            m_rollerState = rollerState;
        });
    }

    public Command setState(PivotState pivotState, RollerState rollerState) {
        return Commands.runOnce(()->{
            m_pivotState = pivotState;
            m_rollerState = rollerState;
        });
    }

    @Override
    public void periodic() {
        m_pivotRight.setControl(m_request.withPosition(m_pivotState.position));
        m_pivotLeft.setControl(m_request.withPosition(m_pivotState.position));
        m_roller.setVoltage(m_rollerState.voltage);

        SmartDashboard.putNumber(IntakeConstants.kPivotRightKey, m_pivotRight.getPosition().getValueAsDouble());
        SmartDashboard.putNumber(IntakeConstants.kPivotLeftKey, m_pivotLeft.getPosition().getValueAsDouble());
        SmartDashboard.putNumber(IntakeConstants.kRollerVoltageKey, m_roller.getSupplyVoltage().getValueAsDouble());
        SmartDashboard.putString(IntakeConstants.kPivotStateKey, m_pivotState.toString());
        SmartDashboard.putString(IntakeConstants.kRollerStateKey, m_rollerState.toString());
    }
}
