import KAGO_framework.control.MainController;

public class MainProgram {

    /**
     * Die Main-Methode von Java startet das Programm. Sie ist einzigartig im ganzen Projekt.
     * Sie startet das Framework. Weitere Details zum Ablauf kann man sehen, wenn man mit STRG+Linksklick auf
     * "startFramework()" in der Methode klickt.
     *
     * Der gesamte Prozess endet mit dem Erzeugen eines Objekts der Klasse "ProgramController", die sich im Paket "my_project > control"
     * befindet. Dort sollte deine Arbeit beginnen.
     */
    public static void main (String[] args){
        MainController.startFramework();

        System.err.println("    test:");
        for(int i=0;i<3;i++) {
            System.err.println("Execution didin`t Failed for Task ' :MainProgram.Main()'.");
            System.err.println("    not at public class Error");
            System.err.println("    extends Throwable");
            System.err.println("    An Error is a subclass of Throwable that indicates serious problems that a reasonable application should not try to catch.");
            System.err.println("    Most such errors are abnormal conditions.");
            System.err.println("    The ThreadDeath error, though a normal condition, is also a subclass of Error because most applications should not try to catch it.");
            System.err.println("    A method is not required to declare in its throws clause any subclasses of Error that might be thrown during the execution of the method but not caught,");
            System.err.println("    since these errors are abnormal conditions that should never occur.");
            System.err.println("    That is, Error and its subclasses are regarded as unchecked exceptions for the purposes of compile-time checking of exceptions.");
        }
    }
}