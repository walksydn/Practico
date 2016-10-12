package autoCommands;

import org.usfirst.frc.team20.robot.Scorpio;
import org.usfirst.frc.team20.robot.Team20Libraries.T20Command;

import edu.wpi.first.wpilibj.Timer;

public class T20AutoCommandDriveStraightFeetLeft extends Scorpio implements T20Command {
	private boolean isFinished, isStarted;
	private double speed, distanceInFeet, heading, encCount, startCount;
	private final double COUNT_TO_FEET_NUM = 3610;

	public T20AutoCommandDriveStraightFeetLeft(double speed, double distanceInFeet) {
		this.isFinished = false;
		this.isStarted = false;
		this.speed = -speed;
		this.distanceInFeet = distanceInFeet;
	}

	@Override
	public void execute() {
		if (isFinished) {
			return;
		}

		if (!isStarted) {
			System.out
					.println("<Drive Straight At Speed: " + this.speed + " For Distance: " + this.distanceInFeet + ">");
			drivetrain.setFieldCentric();
			isStarted = !isStarted;
			heading = ahrs.ahrs.getAngle();
			this.startCount = drivetrain.getLeftSideEncVal();
			this.encCount = (this.distanceInFeet * COUNT_TO_FEET_NUM);
		}
		drivetrain.drive(this.speed, this.heading);
		if ((startCount - Math.abs(drivetrain.getLeftSideEncVal()) < this.encCount)) {
			System.out.println(
					"</Drive Straight At Speed: " + this.speed + " For Distance: " + this.distanceInFeet + ">");
			drivetrain.drive(0, this.heading);
			this.isFinished = true;
		}
	}

	@Override
	public boolean isFinished() {
		return isFinished;
	}

	@Override
	public T20Command copy() {
		// Don't use
		return new T20AutoCommandDriveStraightFeetLeft(this.speed, this.distanceInFeet);
	}

}
