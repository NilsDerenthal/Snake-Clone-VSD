package my_project.control;
import KAGO_framework.control.Drawable;
import KAGO_framework.control.ViewController;

public class SceneController {

    private final ViewController vc;

    public SceneController(ViewController viewController){
        vc=viewController;
        vc.showScene(SceneConfig.NAME_SCENE);
        vc.createScene();
    }

    public void draw(Drawable d){
        vc.draw(d);
    }

    public void draw(Drawable d,int i){
        vc.draw(d,i);
    }

    public void showScene(int i){
        if(sceneExist(i)) vc.showScene(i);
    }

    public void showScene(String s){
        switch(s){
            case "MENU_SCENE" -> vc.showScene(SceneConfig.MENU_SCENE);
            case "GAME_SCENE" -> vc.showScene(SceneConfig.GAME_SCENE);
            case "NAME_SCENE" -> vc.showScene(SceneConfig.NAME_SCENE);
            case "DEFEAT_SCENE" -> vc.showScene(SceneConfig.DEFEAT_SCENE);
            case "LEADERBOARD_SCENE" -> vc.showScene(SceneConfig.LEADERBOARD_SCENE);
        }
    }

    public void createScene(){
        vc.createScene();
    }

    public boolean sceneExist(int i){
        return vc.getScenes().size() >= i;
    }
}