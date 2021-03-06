package bashinggame;

import com.golden.gamedev.GameEngine;
import com.golden.gamedev.GameObject;
import com.golden.gamedev.object.Sprite;
import com.golden.gamedev.object.font.SystemFont;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/*Inputs: Q W E R U I O P */
public class MakeGame extends GameObject {

    private int gameStatus;
    private int life;
    private int blockSpeed;
    private int score;
    private int timer;
    private int delay;
    private int delayMod;
    private int maxBlocks;
    private boolean newHighscore;
    private final Random rand;
    private final SystemFont text;
    private final HighScore highScore;
    private final GameSettings settings;
    ArrayList<Sprite> collision_box;
    ArrayList<Sprite> entity;
    ArrayList<Integer> pressed;

    public MakeGame(GameEngine ge, GameSettings settings, HighScore highScore) {
        super(ge);
        text = new SystemFont(new Font("Courier", Font.PLAIN, 20), Color.white);
        changeDifficulty(settings.getDifficulty());
        this.highScore = highScore;
        this.settings = settings;
        rand = new Random();
    }

    @Override
    public void initResources() {
        collision_box = new ArrayList();
        entity = new ArrayList();
        pressed = new ArrayList();

        resetGame();

        setCollisionArea();
    }

    @Override
    public void update(long l) {
        if (gameStatus == 0) {
            /*spawn entity ONE AT A TIME FOR NOW*/
            if (life == 0) {
                gameStatus = 1;
                if (highScore.getHighscore(settings.getDifficulty()) < score) {
                    highScore.setHighScore(score, settings.getDifficulty());
                    newHighscore = true;
                }
            }
            timer++;
            if (timer % delay == 0) {
                spawn_entity();
                timer = 0;
                delay = rand.nextInt(20) + delayMod;
            }

            /*Input*/
            listenInput();

            /*move all entities*/
            for (Iterator<Sprite> sIter = entity.iterator(); sIter.hasNext();) {
                Sprite e = sIter.next();
                if (e.getY() > 640) {
                    sIter.remove();
                    score--;
                    life = life - 10 >= 0 ? life - 10 : 0;
                } else {
                    e.setY(e.getY() + blockSpeed);
                }
            }
        } else {
            listenInput();
        }
    }

    @Override
    public void render(Graphics2D gd) {
        gd.setColor(Color.LIGHT_GRAY);
        gd.fillRect(0, 0, getWidth(), 640);

        for (Sprite b : collision_box) {
            b.render(gd);
        }
        for (Sprite b : entity) {
            b.render(gd);
        }

        gd.setColor(Color.BLACK);
        gd.fillRect(0, 0, getWidth(), 40);

        text.drawString(gd, "Score: " + score, 10, 5);

        text.drawString(gd, "Performance: ", 250, 5);

        gd.setColor(Color.WHITE);
        gd.fillRect(375, 5, 200, 30);

        if (life > 125) {
            gd.setColor(Color.GREEN);
        } else if (life > 50) {
            gd.setColor(Color.YELLOW);
        } else {
            gd.setColor(Color.RED);
        }
        gd.fillRect(380, 10, life, 20);

        if (gameStatus == 1) {
            //If the game is over display game over screen.
            gd.setColor(Color.BLACK);
            gd.fillRect(0, 320, getWidth(), 90);
            text.drawString(gd, "GAME OVER", 245, 323);
            text.drawString(gd, "Press [Enter] to restart. Press [Escape] to Exit", 100, 353);
            text.setColor(Color.GREEN);

            if (newHighscore) {
                text.drawString(gd, "NEW! High Score: " + highScore.getHighscore(settings.getDifficulty()), 195, 383);
            } else {
                text.drawString(gd, "High Score: " + highScore.getHighscore(settings.getDifficulty()), 225, 383);
            }
            text.setColor(Color.WHITE);
        } else if (gameStatus == 2) {
            //If game is paused display pause screen
            gd.setColor(Color.BLACK);
            gd.fillRect(0, 320, getWidth(), 90);
            text.drawString(gd, "  PAUSED  ", 245, 323);
            text.drawString(gd, "Press [Escape] to resume. Press [Enter] to exit.", 100, 353);
            text.setColor(Color.GREEN);
            text.drawString(gd, "High Score: " + highScore.getHighscore(settings.getDifficulty()), 225, 383);
            text.setColor(Color.WHITE);
        }
    }

    private void changeDifficulty(int difficulty) {
        switch (difficulty) {
            case 0:
                delayMod = 40;
                blockSpeed = 4;
                maxBlocks = 1;
                break;
            case 1:
                delayMod = 25;
                blockSpeed = 5;
                maxBlocks = 1;
                break;
            case 2:
                delayMod = 25;
                blockSpeed = 7;
                maxBlocks = 2;
                break;
        }
    }

    private void resetGame() {
        entity.clear();
        pressed.clear();
        timer = score = 0;
        gameStatus = 0;
        delay = 1;
        life = 190;
        newHighscore = false;
    }

    public void setCollisionArea() {
        for (int i = 0; i < 640; i += 80) {
            collision_box.add(new Sprite(getImage("collisionBlock.png"), i, 560));
        }
    }

    private void spawn_entity() {
        int nBlocks = rand.nextInt(maxBlocks);
        int temp, index;
        index = -1;

        for (int i = 0; i <= nBlocks; i++) {
            do {
                temp = rand.nextInt(8);
            } while (temp == index);
            
            index = temp;
            
            switch (index) {
                case 0:
                    entity.add(new Sprite(getImage("eBlock.png"), 2 * 80, 0));
                    break;
                case 1:
                    entity.add(new Sprite(getImage("rBlock.png"), 3 * 80, 0));
                    break;
                case 2:
                    entity.add(new Sprite(getImage("uBlock.png"), 4 * 80, 0));
                    break;
                case 3:
                    entity.add(new Sprite(getImage("iBlock.png"), 5 * 80, 0));
                    break;
                case 4:
                    entity.add(new Sprite(getImage("wBlock.png"), 1 * 80, 0));
                    break;
                case 5:
                    entity.add(new Sprite(getImage("oBlock.png"), 6 * 80, 0));
                    break;
                case 6:
                    entity.add(new Sprite(getImage("qBlock.png"), 0, 0));
                    break;
                case 7:
                    entity.add(new Sprite(getImage("pBlock.png"), 7 * 80, 0));
                    break;
            }
        }
    }

    private void checkInput(int input) {
        Iterator<Sprite> sIter = entity.iterator();
        boolean checkCollision = false;
        
        while (sIter.hasNext() && !checkCollision) {
            Sprite e = sIter.next();
            /*check collision*/
            if (e.getY() > 480 && e.getY() <= 640 && input == ((int) e.getX() / 80)) {
                System.out.println("Block caught!");
                checkCollision = true;
                sIter.remove();
            }
        }

        if (checkCollision) {
            score++;
            life = life + 2 <= 190 ? life + 2 : 190;
        } else {
            score--;
            life = life - 10 >= 0 ? life - 10 : 0;
        }
    }

    private void listenInput() {
        switch (gameStatus) {
            case 0:
                if (keyPressed(KeyEvent.VK_Q)) {
                    checkInput(0);
                }
                if (keyPressed(KeyEvent.VK_W)) {
                    checkInput(1);
                }
                if (keyPressed(KeyEvent.VK_E)) {
                    checkInput(2);
                }
                if (keyPressed(KeyEvent.VK_R)) {
                    checkInput(3);
                }
                if (keyPressed(KeyEvent.VK_U)) {
                    checkInput(4);
                }
                if (keyPressed(KeyEvent.VK_I)) {
                    checkInput(5);
                }
                if (keyPressed(KeyEvent.VK_O)) {
                    checkInput(6);
                }
                if (keyPressed(KeyEvent.VK_P)) {
                    checkInput(7);
                }
                if (keyPressed(KeyEvent.VK_ESCAPE)) {
                    gameStatus = 2;
                }
                break;
            case 1:
                if (keyPressed(KeyEvent.VK_ESCAPE)) {
                    parent.nextGameID = 0;
                    finish();
                }
                if (keyPressed(KeyEvent.VK_ENTER)) {
                    gameStatus = 0;
                    resetGame();
                }
                break;
            case 2:
                if (keyPressed(KeyEvent.VK_ESCAPE)) {
                    gameStatus = 0;
                }
                if (keyPressed(KeyEvent.VK_ENTER)) {
                    parent.nextGameID = 0;
                    finish();
                }
                break;
        }
    }
}
