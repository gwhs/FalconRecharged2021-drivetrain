package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ConveyorTalon;

public class WaitForConveyor extends CommandBase {

    ConveyorTalon conveyorTalon;
    private long startTime;
    private final double maxWaitTime = 100;

    public WaitForConveyor(ConveyorTalon conveyor) {
        super();
        conveyorTalon = conveyor;
        addRequirements(conveyorTalon);
    }

    /**
     * The initial subroutine of a command. Called once when the command is
     * initially scheduled.
     */
    //Set the startTime to remember when the command began
    public void initialize() {
        startTime = System.currentTimeMillis();
    }

    // checks if power cell is in conveyor to finish or one second has gone by
    public boolean isFinished() {
        long currentTime = System.currentTimeMillis();
        if (conveyorTalon.getHasSeen() || (currentTime - startTime) > maxWaitTime) {
            return true;
        }
        return false;
    }

}
