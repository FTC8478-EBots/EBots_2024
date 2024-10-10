package org.firstinspires.ftc.teamcode.constants;

import com.acmerobotics.roadrunner.Vector2d;

public class Common {

    public static Vector2d Rotate(Vector2d vector2d, double n)
    {
        double rx = (vector2d.x * Math.cos(n)) - (vector2d.y * Math.sin(n));
        double ry = (vector2d.x * Math.sin(n)) + (vector2d.y * Math.cos(n));
        return new Vector2d(rx,ry);
    }
}
