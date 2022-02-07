package my_project.model.visual_ds;

import KAGO_framework.control.ViewController;
import KAGO_framework.model.GraphicalObject;
import KAGO_framework.model.abitur.datenstrukturen.Stack;

public class VisualStack<T extends GraphicalObject & VisualStack.Animated> extends GraphicalObject{

    public interface Animated {

        void comeIn();
        void goOut();
    }

    private Stack<T> stack;
    private ViewController viewController;
    private int counter;

    public VisualStack(ViewController viewController){
        counter = 1;
        stack = new Stack<>();
        this.viewController = viewController;
    }

    /**
     * Die Methode pushInVisual() ist ziemlich ähnlich zu der Methode push() von der Datenstruktur Stack.
     * Es wird geprüft, ob das Objekt, welches hinzugefügt werden soll (@param contentType) null ist, wenn nicht, dann wird es:
     * in den Stack getan,
     * comeIn() aufgerufen,
     * x-Koordinate in abhängigkeit von counter gesetzt
     * gezeichnet + der counter um 1 erhöht,
     * Methode comeIn() aufgerufen, die optional was machen kann
     */
    public void pushInVisual(T contentType) {
        if (contentType != null) {
            stack.push(contentType);
            contentType.comeIn();
            // 431 für die Koordinaten an denen es starten muss
            contentType.setX(431+counter * contentType.getWidth());
            viewController.draw(contentType);
            counter++;
        }
    }

    /**
     * Die Methode popVisual ist ähnlich zu pop() in Stack.
     * Es wird erst geprüft, ob stack leer ist, wenn ja, dann passiert nichts, sonst:
     * wird goOut() aufgerufen
     * wird die "oberste" Zeichnung entfernt,
     * wird das oberste Objekt von Stack entfernt
     * und der counter -1 gerechnet
     */
    public void popVisual(){
        if(!stack.isEmpty()){
            stack.top().goOut();
            viewController.removeDrawable(stack.top());
            stack.pop();
            counter--;
        }
    }

    public boolean isEmptyVisual(){
        return stack.isEmpty();
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }
}