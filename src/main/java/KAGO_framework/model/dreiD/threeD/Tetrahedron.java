package KAGO_framework.model.dreiD.threeD;

import java.awt.*;

public class Tetrahedron{

    private PolygonThreeD[] polygons;
    private Color color;
    private double x,y;

    public Tetrahedron(Color color,double x,double y,PolygonThreeD... polygons){
        this.color=color;
        this.x=x;
        this.y=y;
        this.polygons=polygons;
        setColor(this.color);
    }

    public Tetrahedron(double x,double y,PolygonThreeD... polygons){
        this.x=x;
        this.y=y;
        this.polygons=polygons;
    }

    /**
     * zeichnet die einzelnen Polygone
     */
    public void draw(Graphics2D g) {
        for(PolygonThreeD value:polygons){
            value.draw(g,x,y);
        }
    }

    /**
     * dreht alle Polygone
     * @param cW ob im Uhrzeigersinn gedreht wird
     */
    public void rotate(boolean cW,double xDegrees,double yDegrees,double zDegrees){
        for(PolygonThreeD value:polygons){
            value.rotate(cW, xDegrees, yDegrees, zDegrees);
        }
        sortPolygons();
    }

    /**
     * sortiert die Polygone damit diese in der richtigen Reihenfolge gezeichnet werden können
     */
    private void sortPolygons(){
        PolygonThreeD.sortPolygons(polygons);
    }

    /**
     * verändert die Farbe alle Polygone
     */
    public void setColor(Color color){
        for(PolygonThreeD value:polygons){
            value.setColor(color);
        }
    }
}
