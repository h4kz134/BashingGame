/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bashinggame;

import com.golden.gamedev.GameEngine;
import com.golden.gamedev.GameObject;
import com.golden.gamedev.object.Sprite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 *
 * @author Miguel
 */
public class MainMenu extends GameObject {

    private Sprite easyButton;
    private Sprite mediumButton;
    private Sprite hardButton;
    private Sprite multiplayerButton;
    private BufferedImage easyButtonNormal;
    private BufferedImage easyButtonHighlight;
    private BufferedImage mediumButtonNormal;
    private BufferedImage mediumButtonHighlight;
    private BufferedImage hardButtonHighlight;
    private BufferedImage hardButtonNormal;
    private BufferedImage multiplayerButtonNormal;
    private BufferedImage multiplayerButtonHighlight;
    private final GameSettings settings;

    public MainMenu(GameEngine ge, GameSettings settings) {
        super(ge);
        this.settings = settings;
    }

    @Override
    public void initResources() {
        initializeImages();
        easyButton = new Sprite(easyButtonNormal, 128, 100);
        mediumButton = new Sprite(mediumButtonNormal, 128, 200);
        hardButton = new Sprite(hardButtonNormal, 128, 300);
        multiplayerButton = new Sprite(multiplayerButtonNormal, 128, 400);
    }

    @Override
    public void update(long l) {
        //Check if the mouse cursor is over the buttons
        checkButtons(l); 

        if (click()) {
            if (checkPosMouse(easyButton, true)) {
                parent.nextGameID = 1;
                settings.setDifficulty(0);
                finish();
            } else if (checkPosMouse(mediumButton, true)) {
                parent.nextGameID = 1;
                settings.setDifficulty(1);
                finish();
            } else if (checkPosMouse(hardButton, true)) {
                parent.nextGameID = 1;
                settings.setDifficulty(2);
                finish();
            } else if (checkPosMouse(multiplayerButton, true)) {
                parent.nextGameID = 2;
                finish();
            }
        }
    }

    @Override
    public void render(Graphics2D gd) {
        gd.setColor(Color.LIGHT_GRAY);
        gd.fillRect(0, 0, getWidth(), getHeight());
        easyButton.render(gd);
        mediumButton.render(gd);
        hardButton.render(gd);
        multiplayerButton.render(gd);
    }
    
    private void checkButtons(long l) {
        if (checkPosMouse(easyButton, true)) {
            easyButton.setImage(easyButtonHighlight);
            easyButton.update(l);
        } else {
            easyButton.setImage(easyButtonNormal);
            easyButton.update(l);
        }
        if (checkPosMouse(mediumButton, true)) {
            mediumButton.setImage(mediumButtonHighlight);
            mediumButton.update(l);
        } else {
            mediumButton.setImage(mediumButtonNormal);
            mediumButton.update(l);
        }
        if (checkPosMouse(hardButton, true)) {
            hardButton.setImage(hardButtonHighlight);
            hardButton.update(l);
        } else {
            hardButton.setImage(hardButtonNormal);
            hardButton.update(l);
        }
        if (checkPosMouse(multiplayerButton, true)) {
            multiplayerButton.setImage(multiplayerButtonHighlight);
            multiplayerButton.update(l);
        } else {
            multiplayerButton.setImage(multiplayerButtonNormal);
            multiplayerButton.update(l);
        }
    }

    private void initializeImages() {
        //Button Images
        easyButtonNormal = getImage("easyButton.png");
        mediumButtonNormal = getImage("mediumButton.png");
        hardButtonNormal = getImage("hardButton.png");
        multiplayerButtonNormal = getImage("multiplayerButton.png");
        
        //Button Highlights
        easyButtonHighlight = getImage("easyButtonHighlight.png");
        mediumButtonHighlight = getImage("mediumButtonHighlight.png");
        hardButtonHighlight = getImage("hardButtonHighlight.png");
        multiplayerButtonHighlight = getImage("multiplayerButtonHighlight.png");
    }
}
