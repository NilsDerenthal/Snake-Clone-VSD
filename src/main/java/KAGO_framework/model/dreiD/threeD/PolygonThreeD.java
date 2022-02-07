package KAGO_framework.model.dreiD.threeD;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PolygonThreeD{

    private PointThreeD[] points;
    private Color color;

    public PolygonThreeD(Color color,PointThreeD... points){
        this.points = new PointThreeD[points.length];
        for(int i=0;i< points.length;i++){
            PointThreeD p = points[i];
            this.points[i]=new PointThreeD(p.x,p.y,p.z);
        }
        this.color=color;
    }

    public void draw(Graphics2D g,double x,double y){
        Polygon poly=new Polygon();
        for(int i=0;i< points.length;i++){
            Point p = PointConverter.convertPoint(points[i]);
            poly.addPoint((int) (p.x+x), (int) (p.y+y));
        }
        g.setColor(color);
        g.fillPolygon(poly);
    }

    public double getAverageX(){
        double sum=0;
        for(PointThreeD value:points){
            sum+=value.x;
        }
        return sum/ points.length;
    }

    public void rotate(boolean cW,double xDegrees,double yDegrees,double zDegrees){
        for(PointThreeD value:points){
            PointConverter.rotateAxisX(value,cW,xDegrees);
            PointConverter.rotateAxisY(value,cW,yDegrees);
            PointConverter.rotateAxisZ(value,cW,zDegrees);
        }
    }

    public void setColor(Color color){
        this.color=color;
    }

    public static PolygonThreeD[] sortPolygons(PolygonThreeD[] p){
        List<PolygonThreeD> polygonList=new ArrayList<PolygonThreeD>();

        for(PolygonThreeD value:p){
            polygonList.add(value);
        }

        Collections.sort(polygonList, (p1, p2) -> p2.getAverageX()-p1.getAverageX()<0?1:-1);

        for(int i=0;i<p.length;i++){
            p[i] = polygonList.get(i);
        }
        return p;
    }

}
