import com.mazegame.cs.Model.GameController;
import com.mazegame.cs.Model.Maze;
import com.mazegame.cs.View.TextUI;

public class Main {
    public static void main(String[] args) {
        GameController gameController = new GameController();
        TextUI textUI = new TextUI(gameController);
        textUI.start();

    }
}