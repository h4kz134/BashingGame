/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bashinggame;

/**
 *
 * @author Miguel
 */
public class GameSettings {

    private int difficulty;
    private int nButtons;

    public int getnButtons() {
        return nButtons;
    }

    public void setnButtons(int nButtons) {
        this.nButtons = nButtons;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }
}
