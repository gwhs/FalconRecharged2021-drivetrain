/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.controlpanel;

import frc.robot.Constants;

import java.util.HashMap;
import java.util.Map;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Color.ColorPanelSpinner;
import frc.robot.subsystems.Color.ColorSensor;

/**
 * rbb: This command is broken - the initialize command has while loops that might not end
 * They loop in place while the spinner is going, rather than starting the spin and looking for position
 * on each execute cycle
 */
public class SpinToMid extends CommandBase {
  /**
   * Creates a new SenseColorTest.
   */

  private String currentColor;
  private String expectedColor;
  private String prevColor;
  private String[] expectedColorArray;
  private int arraySize;
  private int prevIndex;
  private Map<String, Integer> colorDictionary;
  private double segmentLength;
  private double targetPos;
  private String gameData;
  private String data;
  private double forwardPos;
  private double finalPos;

  private ColorSensor colorSensor;
  private ColorPanelSpinner colorPanelSpinner;

  private Map<String, String> impossible;

  public SpinToMid(ColorSensor colorSensor, ColorPanelSpinner colorPanelSpinner) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(colorSensor, colorPanelSpinner);
    this.colorSensor = colorSensor;
    this.colorPanelSpinner = colorPanelSpinner;
    colorPanelSpinner.resetEncoder();
    colorPanelSpinner.setPID();
    //pairs colors with colors that it can't reach so we can detect if the sensor jumps
    impossible = new HashMap<String, String>();
    impossible.put("Yellow", "Green");
    impossible.put("Green", "Yellow");
    impossible.put("Blue", "Red");
    impossible.put("Red", "Blue");
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    String startColor;
    // colorPanelSpinner.resetEncoder();
    data =  DriverStation.getInstance().getGameSpecificMessage();

    //matches input color to target color
    if (data == null) {
      gameData = "Unknown";
    } else if (data.length() == 0) {
      gameData = "Unknown";
    } else if (data.toUpperCase().charAt(0) == 'G') {
      gameData = "Yellow";
    } else if (data.toUpperCase().charAt(0) == 'B') {
      gameData = "Red";
    } else if (data.toUpperCase().charAt(0) == 'Y') {
      gameData = "Green";
    } else if (data.toUpperCase().charAt(0) == 'R') {
      gameData = "Blue";
    } else {
      gameData = "Unknown";
    }
    expectedColorArray = new String[] { "Yellow", "Red", "Green", "Blue" };
    arraySize = expectedColorArray.length;

    // Mapping colors to index numbers
    colorDictionary = new HashMap<String, Integer>();
    colorDictionary.put("Yellow", Integer.valueOf(0));
    colorDictionary.put("Red", Integer.valueOf(1));
    colorDictionary.put("Green", Integer.valueOf(2));
    colorDictionary.put("Blue", Integer.valueOf(3));

    currentColor = colorSensor.getColor();
    startColor = gameData;

    SmartDashboard.putString("mid target color", startColor);


    //setting color and indexes going forward and back
    if (Constants.FORWARD) {
      prevIndex = (colorDictionary.get(startColor) - 1) >= 0 ? colorDictionary.get(startColor) - 1 : arraySize - 1;
      prevColor = expectedColorArray[prevIndex];
      expectedColor = expectedColorArray[(colorDictionary.get(startColor) + 1) % arraySize];
    } else {
      prevIndex = (colorDictionary.get(startColor) + 1) % arraySize;
      prevColor = expectedColorArray[prevIndex];
      expectedColor = expectedColorArray[(colorDictionary.get(startColor) - 1) >= 0
          ? colorDictionary.get(startColor) - 1
          : arraySize - 1];
      
    }

    SmartDashboard.putString("previousColor", prevColor);
    SmartDashboard.putString("expColor", expectedColor);
    if(gameData.equals("Red") || gameData.equals("Blue")) {
      blueOrRed();
    }
    else if(gameData.equals("Yellow") || gameData.equals("Green")) {
      findMid();
    }
  }

  //If we see an impossible color, we don't change currentColor
  public void updateColor() {  
 
    String wrongColor = impossible.get(currentColor);

    String detected = colorSensor.getColor();
    if (!detected.equals(wrongColor)) {
      currentColor = detected;
    }
    SmartDashboard.putString("currentColor", currentColor);
    
  }

  public void blueOrRed() {
    String wrongColor = impossible.get(gameData);

    while(!currentColor.equals(wrongColor))  {//"Blue"
      colorPanelSpinner.spin(Constants.SPINNER_SPEED);
      updateColor();
    }
    colorPanelSpinner.spin(0);
    forwardPos = colorPanelSpinner.getPosition();
    colorPanelSpinner.printPosition();
    SmartDashboard.putNumber("forward pos", forwardPos);
    while(!currentColor.equals(gameData)) { //"Red"
      colorPanelSpinner.spin(-Constants.SPINNER_SPEED);
      updateColor();
    }
    while(!currentColor.equals(wrongColor)) { //"Blue"
      colorPanelSpinner.spin(-Constants.SPINNER_SPEED);
      updateColor();
    }
    colorPanelSpinner.spin(0);


    finalPos = colorPanelSpinner.getPosition();
    colorPanelSpinner.printPosition();
    segmentLength = Math.abs(forwardPos - finalPos);
    SmartDashboard.putNumber("backward pos", finalPos);

    //double midPos = segmentLength / 3 + (segmentLength / 3) * Constants.SPINNER_POSITION_PERCENT;
    double midPos = segmentLength * Constants.SPINNER_POSITION_PERCENT;
    targetPos = finalPos + midPos;

    //moves to middle
    SmartDashboard.putNumber("Target pos", targetPos);
    SmartDashboard.putNumber("Segment length", segmentLength);
    colorPanelSpinner.setPosition(targetPos);
    colorPanelSpinner.printPosition();

    updateColor();
    SmartDashboard.putString("currentColor", currentColor);
  }


//moves to middle of color segment
  public void findMid() {
    SmartDashboard.putNumber("init pos", colorPanelSpinner.getPosition());
    updateColor();
    SmartDashboard.putString("currentColor", currentColor);

    //detects first color change
    while (currentColor!= expectedColor) {

      colorPanelSpinner.spin(Constants.SPINNER_SPEED);
      updateColor();
      colorPanelSpinner.printPosition();
    }
    colorPanelSpinner.spin(0);
    forwardPos = colorPanelSpinner.getPosition();
    colorPanelSpinner.printPosition();
    SmartDashboard.putNumber("forward pos", forwardPos);
    
    //detects next color change spinning back
    while (currentColor != prevColor) {
      SmartDashboard.putString("currentColor", currentColor);
      colorPanelSpinner.spin(-Constants.SPINNER_SPEED);
      updateColor();
      colorPanelSpinner.printPosition();
    }

    colorPanelSpinner.spin(0);

    finalPos = colorPanelSpinner.getPosition();
    colorPanelSpinner.printPosition();
    segmentLength = Math.abs(forwardPos - finalPos);
    SmartDashboard.putNumber("backward pos", finalPos);

    double midPos = segmentLength * Constants.SPINNER_POSITION_PERCENT;
    targetPos = finalPos + midPos;

    //moves to middle
    SmartDashboard.putNumber("Target pos", targetPos);
    SmartDashboard.putNumber("Segment length", segmentLength);
    colorPanelSpinner.setPosition(targetPos);
    colorPanelSpinner.printPosition();

    updateColor();
    SmartDashboard.putString("currentColor", currentColor);
  }

  // Called every time the scheduler runs while the command is scheduled. v b-day = 3/6 hello  //viv bday = 3/6
  @Override
  public void execute() {


  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    //return (Math.abs(colorPanelSpinner.getPosition() - targetPos) < 0.01) || colorPanelSpinner.getPosition() < forwardPos - 5.1;
    return Math.abs(colorPanelSpinner.getPosition() - targetPos) < 0.01;
  }

  @Override
  public void end(final boolean interrupted) {
    colorPanelSpinner.spin(0);
  }
}
