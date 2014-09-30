package bashinggame;

import com.golden.gamedev.Game;
import com.golden.gamedev.GameEngine;
import com.golden.gamedev.GameObject;
import com.golden.gamedev.object.Sprite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;


/*Inputs: Q W E R U I O P */
public class MakeGame extends GameObject {

    private int blockSpeed;
    private int difficulty;
    private Sprite qBlock;
    private Sprite wBlock;
    private Sprite eBlock;
    private Sprite rBlock;
    private Sprite uBlock;
    private Sprite iBlock;
    private Sprite oBlock;
    private Sprite pBlock;
    ArrayList<Sprite> collision_box;
    ArrayList<Sprite> entity;
    ArrayList<Integer> pressed;

    public MakeGame(GameEngine ge, int difficulty) {
        super(ge);
        this.difficulty = difficulty;
    }

    @Override
    public void initResources() {
        collision_box = new ArrayList();
        entity = new ArrayList();
        pressed = new ArrayList();
        qBlock = new Sprite(getImage("qBlock.png"));
        wBlock = new Sprite(getImage("wBlock.png"));
        eBlock = new Sprite(getImage("eBlock.png"));
        rBlock = new Sprite(getImage("rBlock.png"));
        uBlock = new Sprite(getImage("uBlock.png"));
        iBlock = new Sprite(getImage("iBlock.png"));
        oBlock = new Sprite(getImage("oBlock.png"));
        pBlock = new Sprite(getImage("pBlock.png"));

        blockSpeed = 0;

        setCollisionArea();
    }

    @Override
    public void update(long l) {
        /*spawn entity ONE AT A TIME FOR NOW*/
        if (entity.isEmpty()) {
            spawn_entity();
        }

        /*Input*/
        listenInput();
        Iterator<Sprite> sIter = entity.iterator();
        while (sIter.hasNext()) {
            Sprite e = sIter.next();
            /*check collision*/
            if (e.getY() > 560 && e.getY() <= 640 && pressed.contains((int) e.getX() / 80)) {
                System.out.println("Block caught!");
                sIter.remove();
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

        for (Sprite b : collision_box) {
            b.render(gd);
        }
        for (Sprite b : entity) {
            b.render(gd);
        }
    }

    public void setCollisionArea() {
        for (int i = 0; i < 640; i += 80) {
            collision_box.add(new Sprite(getImage("collisionBlock.png"), i, 560));
        }
    }

    private void spawn_entity() {
        Random rand = new Random();
        int nBlocks = rand.nextInt(difficulty);
        int temp, index;
        index = -1;
        blockSpeed = 4 + rand.nextInt(4);

        System.out.println(nBlocks);
        for (int i = 0; i <= nBlocks; i++) {
            do {
                temp = rand.nextInt(8);
            } while (temp == index);
            index = temp;
            switch (index) {
                case 0:
                    qBlock.setX(index * 80);
                    qBlock.setY(40);
                    entity.add(qBlock);
                    break;
                case 1:
                    wBlock.setX(index * 80);
                    wBlock.setY(40);
                    entity.add(wBlock);
                    break;
                case 2:
                    eBlock.setX(index * 80);
                    eBlock.setY(40);
                    entity.add(eBlock);
                    break;
                case 3:
                    rBlock.setX(index * 80);
                    rBlock.setY(40);
                    entity.add(rBlock);
                    break;
                case 4:
                    uBlock.setX(index * 80);
                    uBlock.setY(40);
                    entity.add(uBlock);
                    break;
                case 5:
                    iBlock.setX(index * 80);
                    iBlock.setY(40);
                    entity.add(iBlock);
                    break;
                case 6:
                    oBlock.setX(index * 80);
                    oBlock.setY(40);
                    entity.add(oBlock);
                    break;
                case 7:
                    pBlock.setX(index * 80);
                    pBlock.setY(40);
                    entity.add(pBlock);
                    break;
            }
        }
    }

    private void listenInput() {
        if (keyDown(KeyEvent.VK_Q)) {
            pressed.add(0);
        } else if (!keyDown(KeyEvent.VK_Q)) {
            pressed.remove(Integer.valueOf(0));
        }

        if (keyDown(KeyEvent.VK_W)) {
            pressed.add(1);
        } else if (!keyDown(KeyEvent.VK_W)) {
            pressed.remove(Integer.valueOf(1));
        }

        if (keyDown(KeyEvent.VK_E)) {
            pressed.add(2);
        } else if (!keyDown(KeyEvent.VK_E)) {
            pressed.remove(Integer.valueOf(2));
        }

        if (keyDown(KeyEvent.VK_R)) {
            pressed.add(3);
        } else if (!keyDown(KeyEvent.VK_R)) {
            pressed.remove(Integer.valueOf(3));
        }

        if (keyDown(KeyEvent.VK_U)) {
            pressed.add(4);
        } else if (!keyDown(KeyEvent.VK_U)) {
            pressed.remove(Integer.valueOf(4));
        }

        if (keyDown(KeyEvent.VK_I)) {
            pressed.add(5);
        } else if (!keyDown(KeyEvent.VK_I)) {
            pressed.remove(Integer.valueOf(5));
        }

        if (keyDown(KeyEvent.VK_O)) {
            pressed.add(6);
        } else if (!keyDown(KeyEvent.VK_O)) {
            pressed.remove(Integer.valueOf(6));
        }

        if (keyDown(KeyEvent.VK_P)) {
            pressed.add(7);
        } else if (!keyDown(KeyEvent.VK_P)) {
            pressed.remove(Integer.valueOf(7));
        }
    }
}