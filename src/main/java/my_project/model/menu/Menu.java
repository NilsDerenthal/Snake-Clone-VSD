package my_project.model.menu;

import KAGO_framework.control.ViewController;
import KAGO_framework.model.GraphicalObject;
import KAGO_framework.view.DrawTool;
import my_project.Config;
import my_project.control.ProgramController;
import my_project.control.SceneConfig;
import my_project.model.visual_ds.VisualList;
import java.awt.*;
import java.util.Map;

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
                    p.showScene(SceneConfig.GAME_SCENE);
                    p.setIsRunning(true);
                }, Color.YELLOW),
                newOption("Exit", () -> System.exit(0), Color.RED)
        });

        Map<String, Color> colors = Map.of(
                "Blue", Color.BLUE,
                "Green", Color.GREEN,
                "Orange", Color.ORANGE
        );

        MenuSubOption[] colorOptions = new MenuSubOption[colors.size()];
        int index = 0;
        for (var colorPair : colors.entrySet()) {
            colorOptions[index] = newOption(
                    colorPair.getKey(),
                    () -> p.setPlayerColor(colorPair.getValue()),
                    colorPair.getValue()
            );
            index++;
        }

        addOption("Colors", colorOptions);

        addOption("difficulty", new MenuSubOption[] {
            newOption("easy", () -> p.setDifficult(false), Color.YELLOW),
            newOption("hard", () -> p.setDifficult(true), Color.RED)
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
