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

    private int blockSpeed;
    private int score;
    private int timer;
    private int delay;
    private int delayMod;
    private int maxBlocks;
    private boolean scoreChecker;
    private Random rand;
    private SystemFont text;
    ArrayList<Sprite> collision_box;
    ArrayList<Sprite> entity;
    ArrayList<Integer> pressed;

    public MakeGame(GameEngine ge, int difficulty) {
        super(ge);
        text = new SystemFont(new Font("Courier", Font.PLAIN, 20), Color.white);
        changeDifficulty(difficulty);
        rand = new Random();
    }

    @Override
    public void initResources() {
        collision_box = new ArrayList();
        entity = new ArrayList();
        pressed = new ArrayList();
        scoreChecker = true;
        timer = score = 0;
        delay = 1;

        setCollisionArea();
    }

    @Override
    public void update(long l) {
        /*spawn entity ONE AT A TIME FOR NOW*/
        timer++;
        if (timer % delay == 0) {
            spawn_entity();
            timer = 0;
            delay = rand.nextInt(20) + delayMod;
        }

        /*Input*/
        listenInput();
        Iterator<Sprite> sIter = entity.iterator();
        while (sIter.hasNext()) {
            Sprite e = sIter.next();
            /*check collision*/
            if (e.getY() > 560 && e.getY() <= 640 && pressed.contains((int) e.getX() / 80)) {
                System.out.println("Block caught!");
                score++;
                sIter.remove();
                scoreChecker = true;
            }
        }

        /*move all entities*/
        sIter = entity.iterator();
        while (sIter.hasNext()) {
            Sprite e = sIter.next();
            if (e.getY() > 640) {
                sIter.remove();
            } else {
                e.setY(e.getY() + blockSpeed);
            }
        }
    }

    @Override
    public void render(Graphics2D gd) {
        gd.setColor(Color.LIGHT_GRAY);
        gd.fillRect(0, 0, getWidth(), 640);
        gd.setColor(Color.BLACK);
        gd.fillRect(0, 0, getWidth(), 40);

        text.drawString(gd, "Score: " + score, 10, 5);

        for (Sprite b : collision_box) {
            b.render(gd);
        }
        for (Sprite b : entity) {
            b.render(gd);
        }
    }

    private void changeDifficulty(int difficulty) {
        switch (difficulty) {
            case 0:
                delayMod = 40;
                blockSpeed = 3;
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
                    entity.add(new Sprite(getImage("qBlock.png"), index * 80, 40));
                    break;
                case 1:
                    entity.add(new Sprite(getImage("wBlock.png"), index * 80, 40));
                    break;
                case 2:
                    entity.add(new Sprite(getImage("eBlock.png"), index * 80, 40));
                    break;
                case 3:
                    entity.add(new Sprite(getImage("rBlock.png"), index * 80, 40));
                    break;
                case 4:
                    entity.add(new Sprite(getImage("uBlock.png"), index * 80, 40));
                    break;
                case 5:
                    entity.add(new Sprite(getImage("iBlock.png"), index * 80, 40));
                    break;
                case 6:
                    entity.add(new Sprite(getImage("oBlock.png"), index * 80, 40));
                    break;
                case 7:
                    entity.add(new Sprite(getImage("pBlock.png"), index * 80, 40));
                    break;
            }
        }
    }

    private void listenInput() {
        if (keyDown(KeyEvent.VK_Q)) {
            pressed.add(0);
            scoreChecker = false;
        } else if (!keyDown(KeyEvent.VK_Q)) {
            pressed.remove(Integer.valueOf(0));
        }

        if (keyDown(KeyEvent.VK_W)) {
            pressed.add(1);
            scoreChecker = false;
        } else if (!keyDown(KeyEvent.VK_W)) {
            pressed.remove(Integer.valueOf(1));
        }

        if (keyDown(KeyEvent.VK_E)) {
            pressed.add(2);
            scoreChecker = false;
        } else if (!keyDown(KeyEvent.VK_E)) {
            pressed.remove(Integer.valueOf(2));
        }

        if (keyDown(KeyEvent.VK_R)) {
            pressed.add(3);
            scoreChecker = false;
        } else if (!keyDown(KeyEvent.VK_R)) {
            pressed.remove(Integer.valueOf(3));
        }

        if (keyDown(KeyEvent.VK_U)) {
            pressed.add(4);
            scoreChecker = false;
        } else if (!keyDown(KeyEvent.VK_U)) {
            pressed.remove(Integer.valueOf(4));
        }

        if (keyDown(KeyEvent.VK_I)) {
            pressed.add(5);
            scoreChecker = false;
        } else if (!keyDown(KeyEvent.VK_I)) {
            pressed.remove(Integer.valueOf(5));
        }

        if (keyDown(KeyEvent.VK_O)) {
            pressed.add(6);
            scoreChecker = false;
        } else if (!keyDown(KeyEvent.VK_O)) {
            pressed.remove(Integer.valueOf(6));
        }

        if (keyDown(KeyEvent.VK_P)) {
            pressed.add(7);
            scoreChecker = false;
        } else if (!keyDown(KeyEvent.VK_P)) {
            pressed.remove(Integer.valueOf(7));
        }
    }
}