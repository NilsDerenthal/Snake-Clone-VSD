package KAGO_framework.model.dreiD.threeD;

import java.awt.*;

public class PointConverter {

    private static double scale = 1;

    /**
     * convertiert einen 3D Punkt zu einem 2D Punkt
     */
    public static Point convertPoint(PointThreeD p){
        double x3d = p.y*scale;
        double y3d = p.z*scale;
        double depth = p.x*scale;
        double[] newVal = scale(x3d,y3d,depth);
        int x2d = (int) newVal[0];
        int y2d = (int) newVal[1];

        Point point = new Point(x2d,y2d);
        return point;
    }

    public static double[] scale(double x3d,double y3d,double depth){
        double dist=Math.sqrt(x3d*x3d+y3d*y3d);
        double theta=Math.atan2(y3d,x3d);
        double depth2=15-depth;
        double localScale=Math.abs(1400/(depth2+1400));
        dist*=localScale;
        double[] newVal = new double[2];
        newVal[0]=dist*Math.cos(theta);
        newVal[1]=dist*Math.sin(theta);
        return newVal;
    }

    /**
     * rotiert einen Punkt um die X-Achse
     */
    public static void rotateAxisX(PointThreeD p,boolean cW,double degrees){
        double radius=Math.sqrt(p.y*p.y+p.z*p.z);
        double theta=Math.atan2(p.z,p.y);
        theta+=2*Math.PI/360*degrees*(cW?-1:1);
        p.y=radius*Math.cos(theta);
        p.z=radius*Math.sin(theta);
    }

    /**
     * rotiert einen Punkt um die Y-Achse
     */
    public static void rotateAxisY(PointThreeD p,boolean cW,double degrees){
        double radius=Math.sqrt(p.x*p.x+p.z*p.z);
        double theta=Math.atan2(p.x,p.z);
        theta+=2*Math.PI/360*degrees*(cW?-1:1);
        p.x=radius*Math.sin(theta);
        p.z=radius*Math.cos(theta);
    }

    /**
     * rotiert einen Punkt um die Z-Achse
     */
    public static void rotateAxisZ(PointThreeD p,boolean cW,double degrees){
        double radius=Math.sqrt(p.y*p.y+p.x*p.x);
        double theta=Math.atan2(p.y,p.x);
        theta+=2*Math.PI/360*degrees*(cW?-1:1);
        p.y=radius*Math.sin(theta);
        p.x=radius*Math.cos(theta);
    }

}
