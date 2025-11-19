// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix6.swerve.SwerveRequest;
import com.ctre.phoenix6.swerve.SwerveModule.DriveRequestType;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.RobotModeTriggers;
import frc.robot.subsystems.drive.CommandSwerveDrivetrain;
import frc.robot.subsystems.drive.TunerConstants;
import frc.robot.subsystems.intake.Intake;
import frc.robot.subsystems.intake.IntakeConstants.PivotState;
import frc.robot.subsystems.intake.IntakeConstants.RollerState;

public class RobotContainer {
  private final CommandXboxController m_controller = new CommandXboxController(0);
  private final CommandSwerveDrivetrain m_drivetrain = TunerConstants.createDrivetrain();
  private final SwerveRequest.FieldCentric m_drive = new SwerveRequest.FieldCentric()
    .withDeadband(Constants.kMaxSpeed * 0.1).withRotationalDeadband(Constants.kMaxAngularRate * 0.1)
    .withDriveRequestType(DriveRequestType.OpenLoopVoltage);
  private final Intake m_intake = new Intake();

  public RobotContainer() {
    configureBindings();
  }

  private void configureBindings() {
    m_drivetrain.setDefaultCommand(m_drivetrain.applyRequest(()->m_drive
        .withVelocityX(-m_controller.getLeftY() * Constants.kMaxSpeed) // Drive forward with negative Y (forward)
        .withVelocityY(-m_controller.getLeftX() * Constants.kMaxSpeed) // Drive left with negative X (left)
        .withRotationalRate(-m_controller.getRightX() * Constants.kMaxAngularRate) // Drive counterclockwise with negative X (left)
      )
    );

    //Continiously set the drive to idle whilst the robot is disabled
    final SwerveRequest idle = new SwerveRequest.Idle();
    RobotModeTriggers.disabled().whileTrue(
      m_drivetrain.applyRequest(() -> idle).ignoringDisable(true)
    );

    //Press down d-pad to zero the drivetrain
    m_controller.povDown().onTrue(m_drivetrain.runOnce(() -> m_drivetrain.seedFieldCentric()));

    final Command defaultState = m_intake.setState(PivotState.SCORE, RollerState.ZERO);
    final Command defaultPivotState = m_intake.setState(PivotState.SCORE);

    /*
     * Left trigger to intake from ground
     * Right trigger to score L1
     * X to set pivot to zero
     * Y to set pivot to ground
     */
    m_controller.leftTrigger().onTrue(m_intake.setState(PivotState.GROUND, RollerState.INTAKE)).onFalse(defaultState);
    m_controller.rightTrigger().onTrue(m_intake.setState(PivotState.SCORE, RollerState.OUTTAKE)).onFalse(defaultState);
    m_controller.x().onTrue(m_intake.setState(PivotState.ZERO)).onFalse(defaultPivotState);
    m_controller.y().onTrue(m_intake.setState(PivotState.GROUND)).onFalse(defaultPivotState);
  }

  public Command getAutonomousCommand() {
    return Commands.print("No autonomous command configured");
  }
}
