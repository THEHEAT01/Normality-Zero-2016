package org.usfirst.frc.team2559.robot.subsystems;

import org.usfirst.frc.team2559.robot.RobotMap;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Arm extends Subsystem {
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	private final CANTalon 	_adjuster = new CANTalon(RobotMap.PORT_ARM_ADJUSTER),
							_intake = new CANTalon(RobotMap.PORT_ARM_INTAKE);

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public Arm() {
	_intake.setInverted(true);
    }
    
    public void setIntakeSpeed(double speed) {
    	_intake.set(speed);
    }
    
    public void setAdjusterSpeed(double speed) {
    	_adjuster.set(speed);
    }
    
    public void intakeIn() {
    	this.setIntakeSpeed(RobotMap.ARM_INTAKE_SPEED);
    }
    
    public void intakeOut() {
    	this.setIntakeSpeed(-RobotMap.ARM_INTAKE_SPEED);
    }
    
    public void intakeStop() {
    	this.setIntakeSpeed(0);
    }
    
    public double getIntakeSpeed() {
    	return _intake.get();
    }
    
    public double getAdjusterSpeed() {
    	return _adjuster.get();
    }
    
}

