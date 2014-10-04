package bashinggame;

import com.golden.gamedev.GameEngine;
import com.golden.gamedev.GameLoader;
import com.golden.gamedev.GameObject;
import java.awt.Dimension;

public class BashingGame extends GameEngine {

    GameSettings settings;
    HighScore highScore;

    public BashingGame() {
        settings = new GameSettings();
        highScore = new HighScore();
        highScore.initializeHighScores();
    }

    @Override
    public GameObject getGame(int i) {
        switch (i) {
            case 0:
                return new MainMenu(this, settings);
            case 1:
                return new MakeGame(this, settings, highScore);
            case 2:
                return new MultiplayerGame(this);
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
