package my_project.model.visual_ds;

import KAGO_framework.model.GraphicalObject;
import KAGO_framework.model.abitur.datenstrukturen.List;


public class VisualList<T extends GraphicalObject & VisualList.AnimableList> {

    public interface AnimableList {
        /**
         * gibt zurück ob ein Objekt gelöscht werden kann
         */
        boolean tryToDelete();
    }

    private final List<T> list=new List();
    private final double xAbstand;
    private final double yAbstand;
    private final double x;
    private final double y;
    private T current;

    /**
     * @param xAbstand abstand zwischen den Objekten in der List, positiv = rechts negativ = links vom vorherigen Objekt
     * @param yAbstand abstand zwischen den Objekten in der List, positiv = unten negativ = oben vom vorherigen Objekt
     * @param x koordinate der ersten Objektes
     * @param y koordinate der ersten Objektes
     */

    public VisualList(double xAbstand, double yAbstand, double x, double y){
        this.xAbstand=xAbstand;
        this.yAbstand=yAbstand;
        this.x=x;
        this.y=y;
        list.toFirst();
    }

    /**
     * hängt ein Objekt an die Liste an
     * @param t ist das neue Objekt das angehängt wird
     */

    public void append(T t){
        if(!list.isEmpty()&&t!=null) {
            list.append(t);
            t.setX(getPrevious(t).getX() + xAbstand);
            t.setY(getPrevious(t).getY() + yAbstand);
        }else{
            list.append(t);
            t.setX(x);
            t.setY(y);
        }
    }

    /**
     * fügt ein Objekt vor dem aktuellem in die Liste ein
     * @param t ist das neue Objekt das angehängt wird
     */

    public void insert(T t){
        if(!list.isEmpty()) {
            toCurrent();
            list.insert(t);
            t.setX(getPrevious(t).getX() + xAbstand);
            t.setY(getPrevious(t).getY() + yAbstand);
            while (list.getContent() != null) {
                toCurrent();
                list.getContent().setX(getPrevious(t).getX() + xAbstand);
                list.getContent().setY(getPrevious(t).getY() + yAbstand);
            }
        }else{
            list.append(t);
            t.setX(x);
            t.setY(y);
        }
    }

    /**
     * löscht das aktuelle objekt
     */

    public void delete(){
        if(!list.isEmpty()) {
            toCurrent();
            if (list.getContent().tryToDelete()) {
                list.remove();
            }
        }
    }

    /**
     * Pointer wird auf das erste Objekt in der Liste gerichtet
     */

    public void toFirst(){
        list.toFirst();
        current = list.getContent();
    }

    /**
     * Pointer wird um eines weiter geschoben
     */

    public void next(){
        if(list.hasAccess()) {
            toCurrent();
            list.next();
            current = list.getContent();
        }
    }

    /**
     * Pointer wird ein Objekt zurück geschoben
     */

    public void previous(){
        if(list.hasAccess()) {
            list.toFirst();
            if(list.getContent()!=current) {
                int i=0;
                while(list.getContent()!=current) {
                    list.next();
                    i++;
                }
                list.toFirst();
                for(int j=0;j<i-1;j++) list.next();
                current = list.getContent();
            }
        }
    }

    /**
     * list Pointer wird auf das current objekt gerichtet
     */

    private void toCurrent(){
        if(list.hasAccess()){
            while(list.getContent()!=current){
                list.next();
                if(list.getContent()==null)
                    list.toFirst();
            }
        }
    }

    /**
     * gibt das aktuelle Objekt zurück
     */

    public T getCurrent(){
        return list.getContent();
    }

    /**
     * Gibt das folgende Objekt zurück
     * @param t ist das Objekt dessen next zurück gegeben wird
     */

    public T getNext(T t){
        list.toFirst();
        while(!list.equals(t)&&list.getContent()!=null){
            list.next();
        }
        if(list.equals(t)){
            list.next();
            return list.getContent();
        }
        toCurrent();
        return null;
    }

    /**
     * Gibt das vorherige Objekt zurück
     * @param t ist das Objekt dessen privious zurück gegeben wird
     */

    public T getPrevious(T t){
        list.toFirst();
        if(!list.getContent().equals(t)) {
            int i=0;
            while(list.getContent().equals(t)) {
                list.next();
                i++;
            }
            list.toFirst();
            for(int j=0;j<i;j++) list.next();
            return list.getContent();
        }
        toCurrent();
        return null;
    }

    /**
     *gibt zurück ob die List leer ist
     */

    public boolean isEmpty(){ return list.isEmpty(); }


    public interface Command {
        void execute(double d);
    }
    public void forEach(Command c,double d){
        list.toFirst();
        while(list.getContent()!=null) {
            c.execute(d);
            list.next();
        }
    }
}

