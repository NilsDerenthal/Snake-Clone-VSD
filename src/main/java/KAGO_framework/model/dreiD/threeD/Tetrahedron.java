package KAGO_framework.model.dreiD.threeD;

import java.awt.*;

public class Tetrahedron{

    private PolygonThreeD[] polygons;
    private Color color;
    private double x=0,y=0;

    public Tetrahedron(Color color,PolygonThreeD... polygons){
        this.color=color;
        this.polygons=polygons;
    }

    public Tetrahedron(double x,double y,PolygonThreeD... polygons){
        this.x=x;
        this.y=y;
        this.polygons=polygons;
    }

    public void draw(Graphics2D g) {
        for(PolygonThreeD value:polygons){
            value.draw(g,x,y);
        }
    }

    public void rotate(boolean cW,double xDegrees,double yDegrees,double zDegrees){
        for(PolygonThreeD value:polygons){
            value.rotate(cW, xDegrees, yDegrees, zDegrees);
        }
        sortPolygons();
    }

    private void sortPolygons(){
        PolygonThreeD.sortPolygons(polygons);
    }

    public void setColor(Color color){
        for(PolygonThreeD value:polygons){
            value.setColor(color);
        }
    }
}
