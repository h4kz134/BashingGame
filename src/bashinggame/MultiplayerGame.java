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
public class MultiplayerGame extends GameObject {

    private int gameStatus;
    private int player1Score;
    private int player2Score;
    private int timer;
    private int player1Combo;
    private int player2Combo;
    private int delay;
    private final Random rand;
    private final SystemFont text;
    ArrayList<Sprite> collision_box;
    ArrayList<Sprite> entity;
    ArrayList<Integer> pressed;

    public MultiplayerGame(GameEngine ge) {
        super(ge);
        text = new SystemFont(new Font("Courier", Font.PLAIN, 20), Color.white);
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
            if (player1Score == 180 || player2Score == 180) {
                gameStatus = 1;
            }
            timer++;
            if (timer % delay == 0) {
                spawn_entity();
                timer = 0;
                delay = rand.nextInt(20) + 25;
            }

            /*Input*/
            listenInput();
            
            /*move all entities*/
            for (Iterator<Sprite> sIter = entity.iterator(); sIter.hasNext();) {
                Sprite e = sIter.next();
                if (e.getY() > 640) {
                    sIter.remove();
                } else {
                    e.setY(e.getY() + 5);
                }
            }
        } else {
            listenInput();
        }
    }

    @Override
    public void render(Graphics2D gd) {
        gd.setColor(Color.LIGHT_GRAY);
        gd.fillRect(0, 0, getWidth() / 2, 640);
        gd.setColor(Color.DARK_GRAY);
        gd.fillRect(getWidth() / 2, 0, getWidth() / 2, 640);

        for (Sprite b : collision_box) {
            b.render(gd);
        }
        for (Sprite b : entity) {
            b.render(gd);
        }

        gd.setColor(Color.BLACK);
        gd.fillRect(0, 0, getWidth(), 40);

        text.drawString(gd, "Player 1: ", 10, 5);

        gd.setColor(Color.WHITE);
        gd.fillRect(105, 5, 190, 30);

        if (player1Score > 125) {
            gd.setColor(Color.GREEN);
        } else if (player1Score > 50) {
            gd.setColor(Color.YELLOW);
        } else {
            gd.setColor(Color.RED);
        }
        gd.fillRect(110, 10, player1Score, 20);

        text.drawString(gd, "Player 2: ", getWidth() / 2 + 10, 5);

        gd.setColor(Color.WHITE);
        gd.fillRect(getWidth() / 2 + 105, 5, 190, 30);

        if (player2Score > 125) {
            gd.setColor(Color.GREEN);
        } else if (player2Score > 50) {
            gd.setColor(Color.YELLOW);
        } else {
            gd.setColor(Color.RED);
        }
        gd.fillRect(getWidth() / 2 + 110, 10, player2Score, 20);

        if (gameStatus == 1) {
            //If the game is over display game over screen.
            gd.setColor(Color.BLACK);
            gd.fillRect(0, 320, getWidth(), 60);
            if (player1Score == 180) {
                text.drawString(gd, "PLAYER 1 WINS!", 225, 323);
            } else {
                text.drawString(gd, "PLAYER 2 WINS!", 225, 323);
            }
            text.drawString(gd, "Press [Enter] to restart. Press [Escape] to Exit", 100, 353);
        } else if (gameStatus == 2) {
            //If game is paused display pause screen
            gd.setColor(Color.BLACK);
            gd.fillRect(0, 320, getWidth(), 60);
            text.drawString(gd, "  PAUSED  ", 245, 323);
            text.drawString(gd, "Press [Escape] to resume. Press [Enter] to exit.", 100, 353);
        }
    }

    private void resetGame() {
        entity.clear();
        pressed.clear();
        timer = player1Score = player2Score = 0;
        gameStatus = 0;
        delay = 1;
    }

    public void setCollisionArea() {
        for (int i = 0; i < 320; i += 80) {
            collision_box.add(new Sprite(getImage("collisionBlock.png"), i, 560));
        }
        for (int i = 320; i < 640; i += 80) {
            collision_box.add(new Sprite(getImage("collisionBlock2.png"), i, 560));
        }
    }

    private void spawn_entity() {
        int index;
        index = rand.nextInt(4);
        switch (index) {
            case 0:
                entity.add(new Sprite(getImage("qBlock.png"), index * 80, 0));
                entity.add(new Sprite(getImage("uBlock.png"), (index + 4) * 80, 0));
                break;
            case 1:
                entity.add(new Sprite(getImage("wBlock.png"), index * 80, 0));
                entity.add(new Sprite(getImage("iBlock.png"), (index + 4) * 80, 0));
                break;
            case 2:
                entity.add(new Sprite(getImage("eBlock.png"), index * 80, 0));
                entity.add(new Sprite(getImage("oBlock.png"), (index + 4) * 80, 0));
                break;
            case 3:
                entity.add(new Sprite(getImage("rBlock.png"), index * 80, 0));
                entity.add(new Sprite(getImage("pBlock.png"), (index + 4) * 80, 0));
                break;
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
                if (input >= 0 && input <= 3) {
                    player1Combo++;
                    player1Score += 2;
                } else if (input >= 4 && input <= 7) {
                    player2Combo++;
                    player2Score += 2;
                }
            }
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
