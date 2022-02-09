package KAGO_framework.model.dreiD.threeD;

public class PointThreeD {

    private double x,y,z;

    /**
     * Punkt im drei dimensionalen Raum
     */
    public PointThreeD(double x,double y,double z){
        this.x=x;
        this.y=y;
        this.z=z;
    }


    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }
}
