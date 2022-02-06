package my_project.model.menu;

import KAGO_framework.control.ViewController;
import KAGO_framework.model.GraphicalObject;
import KAGO_framework.view.DrawTool;
import my_project.Config;
import my_project.control.ProgramController;
import my_project.control.SceneConfig;
import my_project.model.visual_ds.VisualList;
import java.awt.*;

import static my_project.control.SceneConfig.*;

public class Menu extends GraphicalObject {

    private final VisualList<MenuOption> leftList;
    private final ViewController viewController;
    private final ProgramController p;

    public Menu(ViewController viewController, ProgramController p){
        this.viewController=viewController;
        this.p = p;
        viewController.draw(this, SceneConfig.MENU_SCENE);
        leftList = new VisualList<>(0, 50, 20, 40);
        createMenu();
    }

    private void createMenu (){
        addOption("Control", new MenuSubOption[] {
                newOption("Start", p::startNewGame, Color.GREEN),
                newOption("Continue", () -> {
                    if(p.isGameExists()) {
                        p.showScene(SceneConfig.GAME_SCENE);
                        p.setIsRunning(true);
                    }
                }, Color.YELLOW),
                newOption("New Name", () -> p.showScene(NAME_SCENE), Color.CYAN),
                newOption("Leader Board",() -> p.showScene(LEADERBOARD_SCENE), Color.orange),
                newOption("Exit", () -> System.exit(0), Color.RED)
        });


        addOption("Colors", new MenuSubOption[] {
                newOption("Blue", () -> p.setPlayerColor(Color.BLUE), Color.BLUE),
                newOption("Green", () -> p.setPlayerColor(Color.GREEN), Color.GREEN),
                newOption("Black", () -> p.setPlayerColor(Color.BLACK), Color.BLACK)
        });

        addOption("difficulty", new MenuSubOption[] {
            newOption("easy", () -> {
                p.setTwoEnemys(false);
                p.setDifficult(false);
            }, Color.GREEN),
            newOption("easy two Enemys",()->{
                p.setTwoEnemys(true);
                p.setDifficult(false);
            },Color.YELLOW),
            newOption("hard", () ->{
                p.setTwoEnemys(false);
                p.setDifficult(true);
            }, Color.RED),
            newOption("hard two Enemys",()-> {
                p.setTwoEnemys(true);
                p.setDifficult(true);
            },new Color(100,0,0,255))
        });

        addOption("Sound",new MenuSubOption[]{
                newOption("on",p::soundOn,Color.GREEN),
                newOption("off",p::soundOff,Color.red)
        });

        leftList.toFirst();
        leftList.getCurrent().changeUp(true);
    }

    private void addOption (String name, MenuSubOption[] subOptions) {
        MenuOption option = new MenuOption(Config.WINDOW_HEIGHT / 2f - 150, Config.WINDOW_HEIGHT + 200, viewController, leftList, name);

        for (var subOption : subOptions) {
            option.append(subOption);
        }

        subOptions[0].setSelected(true);
        leftList.append(option);
    }

    private MenuSubOption newOption (String name, MenuSubOption.OnClickExecutor executor, Color color) {
        return new MenuSubOption(30, 100, executor, color, name);
    }

    @Override
    public void draw(DrawTool drawTool){
        // background
        drawTool.setCurrentColor(Color.DARK_GRAY);
        drawTool.drawFilledRectangle(0, 0, Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT);

        drawTool.setCurrentColor(Color.GRAY);
        drawTool.drawFilledPolygon(
                30, 10,
                10, 30,
                10, Config.WINDOW_HEIGHT - 70,
                30, Config.WINDOW_HEIGHT - 50,
                110, Config.WINDOW_HEIGHT - 50,
                130, Config.WINDOW_HEIGHT - 70,
                130, 30,
                110, 10
        );
        drawTool.setCurrentColor(Color.BLACK);
        drawTool.drawPolygon(
                30, 10,
                10, 30,
                10, Config.WINDOW_HEIGHT - 70,
                30, Config.WINDOW_HEIGHT - 50,
                110, Config.WINDOW_HEIGHT - 50,
                130, Config.WINDOW_HEIGHT - 70,
                130, 30,
                110, 10
        );
    }

    public void next(){
        if(leftList.getCurrent()!=null) {
            MenuOption current = leftList.getCurrent();
            leftList.getCurrent().changeUp(false);
            leftList.next();
            if (leftList.getCurrent()==null) leftList.currentTo(current);
            leftList.getCurrent().changeUp(true);
        }
    }

    public void previous(){
        if(leftList.getCurrent()!=null) leftList.getCurrent().changeUp(false);
        leftList.previous();
        if (leftList.getCurrent() != null) leftList.getCurrent().changeUp(true);
    }

    public void left(){
        leftList.getCurrent().getList().getCurrent().setSelected(false);
        if(leftList.getCurrent() != null)
            leftList.getCurrent().getList().previous();
        leftList.getCurrent().getList().getCurrent().setSelected(true);
    }

    public void right(){
        MenuSubOption current = leftList.getCurrent().getList().getCurrent();
        current.setSelected(false);
        if(leftList.getCurrent().getList().getCurrent()!=null) leftList.getCurrent().getList().next();
        if(leftList.getCurrent().getList().getCurrent()==null) leftList.getCurrent().getList().currentTo(current);
        leftList.getCurrent().getList().getCurrent().setSelected(true);
    }

    public void clickOn(){
        if(leftList.getCurrent()!=null) leftList.getCurrent().getList().getCurrent().clickOn();
    }
}
