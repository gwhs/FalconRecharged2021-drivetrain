# FalconRecharged2021

*Work in Progress*

## Software To Do List

  - Create a toRadians() method to take a degree input and returns the angle in Radians(for AutoPaths)
        -to be used in TrajectoryHelper.java

## General Information



## Autonomous Path Planning

  ###### How to create a new Autonomous Path
    
    1. Create a New AutoPath in [java \ frc \ robot \ commands \ AutoPaths]
            i. Right Click "AutoPaths" on the left explorer bar
            ii. Click Create a new Class/Command
            iii. Type in and/or select "SequentialCommandGroup (New)"
            iv. Enter a name for your new AutoPath
            v. Replace the reference to the super consutructor( super(); ) with addCommands();
                1. This allows you to add many commands for the Robot to follow,
                  including many sequential AutoPaths or tasks such as 
                  picking up power cells, etc...
                2. The change from super(); to addCommands(); was decided after
                  the Autonomous Software Group ran in to various problems with super();
            vi. add the follwing imports (just for autoPath movement) and other necessary 
                classes for other tasks:

                    import frc.robot.TrajectoryHelper;
                    import frc.robot.utility.TrajectoryMaker;
                    import frc.robot.commands.swervedrive.Autonomous;
                    import frc.robot.subsystems.Drive.SwerveDriveSubsystem;
                    
            vii. add any inputs to the class declaration that may be required:
                    ex. swerveDriveSubsystem or shooter or intake

    2. Go to TrajectoryHelper.java to set up a new TrajectoryMaker
            i. *note* Each TrajectoryMaker has a Trajectory Instance Variable that can get accessed
              using the method getTrajectory()
            ii. As implied by the name TrajectoryMaker, the class makes the trajectory for you
            iii. Set up a new Trajectory Maker using the following format:
            
                    public static TrajectoryMaker trajMakerName()
                    {
                        //Add ArrayList and points here if necessary
                        return new TrajectoryMaker(*look at the following steps to pick a constructor);
                    }
                    
            iv. There are 2 Types of TrajectoryMakers(2 different constructors)
                1. The First type of TrajectoryMaker allows the Robot to move a distance in any direction.
                
                        public TrajectoryMaker(Pose2d start, Pose2d end, boolean isHyp)
                        
                    - Moves in a straight line from starting point to end point
                    - uses Pose2d start, Pose2d end, boolean isHyp as inputs
                    - If using this constructor, set isHyp to true, setting it to false may not move 
                      the robot or may produce unknown results.
                2. The Second type of TrajectoryMaker uses the SwerveDrives as if it was 
                   a TankDrive(as if the wheels cannot rotate)
                
                        public TrajectoryMaker(Pose2d start, Pose2d end, ArrayList<Translation2d> points)
                        
                    - Moves from start to end point without rotating wheels
                    - uses Pose2d start, Pose2d end, and an ArrayList of Translation2d Objects as points
                    - does not require isHyp input because it is set to false by default for this constructor.
                    - add points to an ArrayList using:
                            points.add(new Translation2d(x, y));
                        - x and y should be in METERS
                    - add points before returning the TrajectoryMaker
            v. Start and End Points are Pose2d Objects
                1. Pose2d Objects can be created with the following inputs:
                        new Pose2d(double x, double y, Rotation2d rotation)
                2. the x and y inputs for Pose2d need to be entered in METERS
                3. Angle is inputed using a Rotation2d object, which can be created using:
                        new Rotation2d(angleInRadians)
                        
    3. Go to AutoPaths and create Local Variables for TrajectoryMaker to make the code easier to 
       read and debug(place on top of addCommands();)
            i. An example of a local variable TrajectoryMaker *traj* for 
               the TrajectoryMaker createForwardPath() located in TrajectoryHelper:

                TrajectoryMaker traj = TrajectoryHelper.createForwardPath();

    4. Set Up Autonomous and other tasks in addCommands();
            i. commands can be separated by a "," except for the last command:
                    
                    addCommands(
                        new Autonomous(),
                        new Autonomous()
                    );
            ii. Set Up Autonomous Commands:

                    new Autonomous(SwerveDriveSubsystem swerveDriveSubsystem, Trajectory trajectory, double angle)

                1. for the swerveDriveSubsystem, just use "swerveDriveSubsystem"
                2. for the Trajectory, use the TrajectoryMaker local variable with the getTrajectory() method 
                3. for the angle, use the TrajectoryMaker local variable with the getAngle() method
                4. you can use more than one autonomous command in one command group




