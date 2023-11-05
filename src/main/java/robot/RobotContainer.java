// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package robot;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import robot.Constants.DriveConstants.DriveMode;
import robot.commands.drive.DefaultDriveCommand;
import robot.oi.OI;
import robot.subsystems.CargoSubsystem;
import robot.subsystems.DriveSubsystem;
import robot.subsystems.HatchSubsystem;
import robot.subsystems.LiftSubsystem;
import robot.subsystems.PneumaticsSubsystem;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {

    public static final OI                   oi                  = new OI();

    // The robot's subsystems and commands are defined here...
    public static final DriveSubsystem       driveSubsystem      = new DriveSubsystem();
    public static final PneumaticsSubsystem  pneumaticsSubsystem = new PneumaticsSubsystem();
    public static final LiftSubsystem        liftSubsystem       = new LiftSubsystem();
    public static final HatchSubsystem       hatchSubsystem      = new HatchSubsystem();
    public static final CargoSubsystem       cargoSubsystem      = new CargoSubsystem();

    // All dashboard choosers are defined here...
    private final SendableChooser<DriveMode> driveModeChooser    = new SendableChooser<>();

    /**
     * The container for the robot. Contains subsystems, OI devices, and commands.
     */
    public RobotContainer() {

        // Initialize all Subsystem default commands.
        driveSubsystem.setDefaultCommand(
            new DefaultDriveCommand(
                oi.driverController, driveModeChooser,
                driveSubsystem));

        // Initialize the dashboard choosers
        initDashboardChoosers();

        // Configure the button bindings
        oi.configureButtonBindings(driveSubsystem);
    }

    private void initDashboardChoosers() {

        driveModeChooser.setDefaultOption("Tank", DriveMode.TANK);
        SmartDashboard.putData("Drive Mode", driveModeChooser);
        driveModeChooser.addOption("Dual Stick Arcade", DriveMode.DUAL_STICK_ARCADE);
        driveModeChooser.addOption("Single Stick Arcade", DriveMode.SINGLE_STICK_ARCADE);
    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {

        // Pass in all of the subsystems and all of the choosers to the auto command.
        return new InstantCommand();
    }
}
