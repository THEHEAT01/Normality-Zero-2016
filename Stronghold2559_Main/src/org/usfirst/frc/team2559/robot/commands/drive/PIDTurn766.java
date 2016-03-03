package org.usfirst.frc.team2559.robot.commands.drive;

import org.usfirst.frc.team2559.lib.PIDController;
import org.usfirst.frc.team2559.robot.Robot;
import org.usfirst.frc.team2559.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 * Based on
 * https://github.com/Team766/2015/blob/5d027b62ec1535861083adb06a34e343caa21e7b/src/org/usfirst/frc/team766/robot/commands/Drive/DriveTurn.
 * java
 *
 */
public class PIDTurn766 extends Command {

    private static final double ANGLES_TO_DEGREES = 1;
    double angle;

   private PIDController       pid	       = new PIDController(RobotMap.PID_TURN_Kp, RobotMap.PID_TURN_Ki, RobotMap.PID_TURN_Kd, -0.5, 0.5, 2.5); // creates

    // 0.5, and .5 as min/max/tolerance

    public PIDTurn766(double angle) {
	// Use requires() here to declare subsystem dependencies
	// eg. requires(chassis);
	requires(Robot._driveTrain);
	this.angle = angle;
	pid.setSetpoint(angle);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
	Robot._driveTrain.setAuton(true);
	Robot._driveTrain.setFastDrive(false);
	Robot._driveTrain.setReverseDrive(false);
	Robot._driveTrain.setSlowDrive(false);
	Robot._driveTrain.clearGyro();
	pid.reset();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
	if (angle > 0) {
	    pid.calculate(-Robot._driveTrain.getGyroAngle(), true);

	    double power = pid.getOutput() * ANGLES_TO_DEGREES;
	    Robot._driveTrain.tankDrive(-power, power);
	} else {
	    pid.calculate(Robot._driveTrain.getGyroAngle(), true);

	    double power = pid.getOutput() * ANGLES_TO_DEGREES;
	    Robot._driveTrain.tankDrive(power, -power);
	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
	return pid.isDone();
    }

    // Called once after isFinished returns true
    protected void end() {
	Robot._driveTrain.tankDrive(0, 0);
	Robot._driveTrain.setAuton(false);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
	end();
    }
}
