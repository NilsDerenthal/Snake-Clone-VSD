package my_project.model.visual_ds;

import KAGO_framework.control.ViewController;
import KAGO_framework.model.GraphicalObject;
import KAGO_framework.model.abitur.datenstrukturen.List;
import KAGO_framework.model.abitur.datenstrukturen.Queue;
import my_project.control.SceneConfig;

public class VisualQueue<T extends GraphicalObject & VisualQueue.Animatable> {

    /**
     * Achtet darauf, dass ich setter ung getter für tX/tY (Soll Target X/Y bedeuten)
     * richtig implementiert! Es ist zu beachten, dass beim bewegen der Objekte
     * immer diese beiden benutzt werden.
     * Die Methode isArrived() muss true wiedergeben, sonst funktioniert dequeue() nicht.
     * Soll dazu dienen Objekte erst entfernen zu können wenn ihre Animation fertig ist.
     * fadeIn() wird beim enqueue() ausgeführt.
     * fadeOut() wird beim dequeue() true weitergegeben.
     * Außerdem sollten eure Objekte mit den parametern x,y,width,height und radius welche sie von der
     * Klasse GraphicalObject erben arbeiten.
     */
    public interface Animatable {

        void fadeIn();

        void fadeOut(boolean fadeOut);

        void setTx(double tx);

        void setTy(double ty);

        double getTx();

        double getTy();

        boolean isArrived();

    }

    //Ende Interface

    private Queue<T> queue;
    private final ViewController viewController;
    private double posX;
    private double posY;
    private final String direction;
    private double frontX, frontY;
    private boolean movable;
    private boolean allowed;
    private final List<Double> helpX;
    private final List<Double> helpY;

    /**
     * Dem Konstruktor können posX und posY weitergegeben werden.
     * Diese sind die Position eures ersten Objektes.
     * Dem String direction könnt ihr nur "up" oder "right" weitergeben.
     * bei "up" werden neue Objekte oben und bei "right" rechts ran gepackt.
     * Dann gibts noch das special "movable". Da lieber keine Animationen nutzen, könnte zu fehlern führen.
     * Da wird die queue zu einer art "Schlange".
     * Mit moveQueue() kannst du die Queue dann bewegen.
     */
    public VisualQueue(ViewController viewController, double posX, double posY, String type){
        this.posX = posX;
        this.posY = posY;
        frontX = posX;
        frontY = posY;
        this.direction = type;

        if(type.equals("movable")) movable = true;

        queue = new Queue<>();

        this.viewController = viewController;
        helpX = new List<>();
        helpY = new List<>();
        allowed = true;
    }

    // still in progress

    /**
     * Es wird ein neues Objekt hinzugefügt und an die position posX/posY gepackt.
     * Danach wird posX oder posY (je nachdem ob eure Queue "up" oder "right" geht)
     * um den Radius oder die höhe/breite eures Objektes verschoben, damit das nächste
     * Objekt seine neue Position richtig verteilt bekommt.
     * Bei movable: Es kann nur ein neues Objekt hinzugefügt werden, nachdem es sich min. 1 mal nach
     * dem hinzufügen eines Objektes bewegen, damit man ein neues Objekt hinzufügen kann.
     */
    public void enqueue(T content){
        if(content != null & allowed) {
            queue.enqueue(content);
            if (!queue.isEmpty()) {
                if(movable) {
                    content.setTy(posY);
                    content.setTx(posX);
                    helpX.append(posX);
                    helpY.append(posY);
                }else{
                    if (content.getRadius() == 0) {
                        content.setY(posY);
                        content.setX(posX);
                        if (direction.equals("right")) {
                            posX += content.getWidth();
                        } else if (direction.equals("up")) {
                            posY -= content.getHeight();
                        }
                    } else {
                        if (direction.equals("right")) {
                            posY += content.getRadius();
                        } else if (direction.equals("up")) {
                            posY -= content.getRadius();
                        }
                        content.setY(posY);
                        content.setX(posX);
                        if (direction.equals("right")) {
                            posY += content.getRadius();
                        } else if (direction.equals("up")) {
                            posY -= content.getRadius();
                        }
                    }
                }
            } else {
                if(movable) {
                    content.setY(posY);
                    content.setX(posX);
                    helpX.append(posX);
                    helpY.append(posY);
                }else{
                    content.setY(posY);
                    content.setX(posX);
                    if (content.getRadius() == 0) {
                        posX += content.getWidth();
                    } else {
                        posX += content.getRadius();
                    }
                }
            }

            viewController.draw(content, SceneConfig.GAME_SCENE);
            content.fadeIn();
            if(movable) allowed = false;
        }
    }

    /**
     * Falls in der Queue Objekte sind wird das vorderste nicht mehr gezeichnet, die Methode fadeOut(),
     * der true weitergegen wird  ausgeführt (damnit ihr damit was machen könnt nachdem es entfernt wurde)
     * und danach von der Queue entfernt.
     * Hiernach werden die Positionen nach vorne hin um die breite des entfernten Objektes verschoben.
     * Dabei wird tX/tY auf die position durch setTx()/setTy() gesetzt (Nicht x/y!!)
     * Falls movable aktiv ist, wird das Visuel HINTERSTE!! gelöscht, da alles wieder nach vorne bewegt wird.
     */
    public void dequeue(){
        if(!queue.isEmpty()){
            if(queue.front().isArrived()){
                viewController.removeDrawable(queue.front());
                queue.front().fadeOut(true);
                if(movable){
                    queue.dequeue();
                    helpY.toFirst(); helpY.remove();
                    helpX.toFirst(); helpX.remove();
                    moveQueue(0,0);
                }else{
                    double radius = queue.front().getRadius();
                    double width = queue.front().getWidth();
                    double height = queue.front().getHeight();
                    queue.dequeue();
                    //Rest bewegen
                    Queue<T> newQueue = new Queue<>();
                    //Verzweigung, jenachdem ob deine Queue nach oben hin oder nach rechts hin vergrößert werden soll
                    if (direction.equals("right")) {
                        while (!queue.isEmpty()) {
                            newQueue.enqueue(queue.front());
                            if (radius == 0) {
                                queue.front().setTx(queue.front().getTx() - width);
                            } else {
                                queue.front().setTx(queue.front().getTx() - radius * 2);
                            }
                            queue.dequeue();
                        }
                        if (radius == 0) {
                            posX -= width;
                        } else {
                            posX -= radius * 2;
                        }
                    } else if (direction.equals("up")) {
                        while (!queue.isEmpty()) {
                            newQueue.enqueue(queue.front());
                            if (radius == 0) {
                                queue.front().setTy(queue.front().getTy() + height);
                            } else {
                                queue.front().setTy(queue.front().getTy() + radius * 2);
                            }
                            queue.dequeue();
                        }
                        if (radius == 0) {
                            posY += height;
                        } else {
                            posY += radius * 2;
                        }
                    }

                    queue = newQueue;
                }
            }
        }
    }

    public T getFront(){
        return queue.front();
    }

    /**
     * Beta version!
     *
     *
     * Bewegt den Kopf der Schlange in richtung der weitergegebenen werte in x oder y richtung.
     * Die teile davor nehmen die Position von dem, der vor ihm war ein.
     */
    public void moveQueue(double inX, double inY){
        if(!queue.isEmpty()) {
            frontX += inX;
            frontY += inY;

            helpX.toFirst();
            helpX.insert(frontX);

            helpY.toFirst();
            helpY.insert(frontY);

            helpX.toLast();
            helpX.remove();

            helpY.toLast();
            helpY.remove();

            Queue<T> tmp = queue;
            helpY.toFirst(); helpX.toFirst();
            while (!queue.isEmpty() && helpX.hasAccess()) {
                queue.front().setTx(helpX.getContent());
                queue.front().setTy(helpY.getContent());
                tmp.enqueue(queue.front());
                queue.dequeue();
                helpX.next(); helpY.next();
            }
            helpX.toLast();
            helpY.toLast();
            posX = helpX.getContent(); posY = helpY.getContent();

            queue = tmp;
            allowed = true;
        }
    }

    /**
     * Überprüft, ob der Kopf der Schlange mit seinem Körperteil
     * kolidieren würde, wenn es sich in x/y richtung bewegen würde.
     */

    public boolean isPlaceFree(double x, double y){
        double tmpX = queue.front().getTx() + x;
        double tmpY = queue.front().getTy() + y;
        helpX.toFirst();
        helpY.toFirst();
        while(helpX.hasAccess()){
            if(tmpX == helpX.getContent() && tmpY == helpY.getContent()){
                return false;
            }
            helpX.next();
            helpY.next();
        }
        return true;
    }

    public boolean collidesWithSnake(double x, double y){
        helpX.toFirst();
        helpY.toFirst();
        while(helpX.hasAccess()){
            if(x == helpX.getContent() && y == helpY.getContent()){
                return true;
            }
            helpX.next();
            helpY.next();
        }
        return false;
    }

    public List<Double> xPositionList(){
        return helpX;
    }

    public List<Double> yPositionList(){
        return helpY;
    }
}


