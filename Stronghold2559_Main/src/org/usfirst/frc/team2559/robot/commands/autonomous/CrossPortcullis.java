package org.usfirst.frc.team2559.robot.commands.autonomous;

import org.usfirst.frc.team2559.robot.Robot;
import org.usfirst.frc.team2559.robot.RobotMap;
import org.usfirst.frc.team2559.robot.commands.GetReadyToRumble;
import org.usfirst.frc.team2559.robot.commands.arm.PIDSetArm;
import org.usfirst.frc.team2559.robot.commands.arm.PortcullisTwitch;
import org.usfirst.frc.team2559.robot.commands.drive.DriveForDistance;
import org.usfirst.frc.team2559.robot.commands.drive.PIDVisionTurn;
import org.usfirst.frc.team2559.robot.commands.shooter.DumbShoot;
import org.usfirst.frc.team2559.robot.commands.shooter.FireServo;
import org.usfirst.frc.team2559.robot.commands.shooter.PIDSetShooter;
import org.usfirst.frc.team2559.robot.commands.shooter.PIDStayOnTarget_Shooter;
import org.usfirst.frc.team2559.robot.commands.shooter.PIDVisionShooter;
import org.usfirst.frc.team2559.robot.commands.shooter.SetShooter;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class CrossPortcullis extends CommandGroup {

    public CrossPortcullis() {
	// Add Commands here:
	// e.g. addSequential(new Command1());
	// addSequential(new Command2());
	// these will run in order.

	// To run multiple commands at the same time,
	// use addParallel()
	// e.g. addParallel(new Command1());
	// addSequential(new Command2());
	// Command1 and Command2 will run in parallel.

	// A command group will require all of the subsystems that each member
	// would require.
	// e.g. if Command1 requires chassis, and Command2 requires arm,
	// a CommandGroup containing them would require both the chassis and the
	// arm.

	// set our arms and shooter to portcullis in the event we aren't in starting config while driving to outerworks
	addSequential(new GetReadyToRumble(RobotMap.PORTCULLIS_ID));
	addSequential(new DriveForDistance(0.65, RobotMap.DISTANCE_TO_OUTERWORKS * 2));	
//	addSequential(new Command() {
//
//	    protected void initialize() {
//		Robot._arm.setAdjusterSpeed(-1);
//	    }
//
//	    protected void execute() {}
//
//	    protected boolean isFinished() {
//		return true;
//	    }
//
//	    protected void end() {}
//
//	    protected void interrupted() {
//		end();
//	    }
//	});
//	addSequential(new WaitCommand(0.7));
//	addSequential(new Command() {
//
//	    protected void initialize() {
//		Robot._arm.setAdjusterSpeed(0);
//	    }
//
//	    protected void execute() {}
//
//	    protected boolean isFinished() {
//		return true;
//	    }
//
//	    protected void end() {}
//
//	    protected void interrupted() {
//		end();
//	    }
//	});
//	addSequential(new WaitCommand(0.2));
//	addSequential(new DriveForDistance(0.7, RobotMap.DISTANCE_TO_OUTERWORKS / 2), 2);
//	// turn after crossing
////	addSequential(new PIDAutonTurn((int)Robot.autonTurnDirection.getSelected()));
	addParallel(new PIDSetArm(RobotMap.ARM_INTAKE_ANGLE));
	addSequential(new PIDAutonTurn());
	/** vision **/
	addSequential(new PIDVisionTurn(), 4);
	addSequential(new PIDVisionShooter());

	// add shooting logic here later
	addParallel(new Command() {

	    protected void initialize() {
		Robot._shooter.setShootingStatus(true);
	    }

	    protected void execute() {}

	    protected boolean isFinished() {
		return true;
	    }

	    protected void end() {}

	    protected void interrupted() {
		end();
	    }
	});
	addSequential(new SetShooter(1, 1));
	addSequential(new WaitCommand(RobotMap.SMARTSHOOT_SPINUP_DELAY));
	addSequential(new FireServo());
	addSequential(new WaitCommand(RobotMap.SMARTSHOOT_SPINUP_DELAY));
	addSequential(new SetShooter(0, 0));
	addParallel(new Command() {

	    protected void initialize() {
		Robot._shooter.setShootingStatus(false);
	    }

	    protected void execute() {}

	    protected boolean isFinished() {
		return true;
	    }

	    protected void end() {}

	    protected void interrupted() {
		end();
	    }
	});

    }
}
