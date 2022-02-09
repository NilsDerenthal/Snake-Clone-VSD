package KAGO_framework.model.dreiD.threeD;

import java.awt.*;

public class PointConverter {

    private static double scale = 1;

    /**
     * convertiert einen 3D Punkt zu einem 2D Punkt
     */
    public static Point convertPoint(PointThreeD p){
        double x3d = p.getY()*scale;
        double y3d = p.getZ()*scale;
        double depth = p.getX()*scale;
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
        double radius=Math.sqrt(p.getY()*p.getY()+p.getZ()*p.getZ());
        double theta=Math.atan2(p.getZ(),p.getY());
        theta+=2*Math.PI/360*degrees*(cW?-1:1);
        p.setY(radius*Math.cos(theta));
        p.setZ(radius*Math.sin(theta));
    }

    /**
     * rotiert einen Punkt um die Y-Achse
     */
    public static void rotateAxisY(PointThreeD p,boolean cW,double degrees){
        double radius=Math.sqrt(p.getX()*p.getX()+p.getZ()*p.getZ());
        double theta=Math.atan2(p.getX(),p.getZ());
        theta+=2*Math.PI/360*degrees*(cW?-1:1);
        p.setX(radius*Math.sin(theta));
        p.setZ(radius*Math.cos(theta));
    }

    /**
     * rotiert einen Punkt um die Z-Achse
     */
    public static void rotateAxisZ(PointThreeD p,boolean cW,double degrees){
        double radius=Math.sqrt(p.getY()*p.getY()+p.getX()*p.getX());
        double theta=Math.atan2(p.getY(),p.getX());
        theta+=2*Math.PI/360*degrees*(cW?-1:1);
        p.setY(radius*Math.sin(theta));
        p.setX(radius*Math.cos(theta));
    }

}
