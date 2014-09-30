package bashinggame;

import com.golden.gamedev.GameEngine;
import com.golden.gamedev.GameLoader;
import com.golden.gamedev.GameObject;
import java.awt.Dimension;

public class BashingGame extends GameEngine {

    @Override
    public GameObject getGame(int i) {
        switch (i) {
            case 0:
                return new MakeGame(this, 3);

        }

        return null;
    }

    public static void main(String[] args) {
        // GameEngine class creation is equal with Game class creation
        GameLoader game = new GameLoader();
        game.setup(new BashingGame(), new Dimension(640, 640), false);
        game.start();
    }
}
