package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.ColorSensor;

public class ExtendColorSensor {
    private final ColorSensor cs;

    public ExtendColorSensor(ColorSensor cs) {
        this.cs = cs;
    }

    public boolean ballPresent(int greenVal, int blueVal) {
        return this.cs.green() >= greenVal || this.cs.blue() >= blueVal;
    }

    public int green() {return cs.green();}
    public int blue() {return cs.blue();}
}
