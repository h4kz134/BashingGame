package bashinggame;

import com.golden.gamedev.Game;
import com.golden.gamedev.object.Sprite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;


/*Inputs: Q W E R U I O P */
public class MakeGame extends Game{
    
    ArrayList<Sprite> collision_box;
    ArrayList<Sprite> entity;
    ArrayList<Integer> pressed;
    
    
    @Override
    public void initResources() {
        collision_box = new ArrayList();
        entity = new ArrayList();
        pressed = new ArrayList();
        
        setCollisionArea();
    }

    @Override
    public void update(long l) {
        /*spawn entity ONE AT A TIME FOR NOW*/
        if(entity.isEmpty()){
            spawn_entity();
        }
        
        /*Input*/
        listenInput();
        Iterator<Sprite> sIter = entity.iterator();
        while(sIter.hasNext()){
            Sprite e = sIter.next();
            /*check collision*/
            if(e.getY() > 560 && e.getY() <= 640 && pressed.contains((int)e.getX()/80)){
                System.out.println("Block caught!");
                sIter.remove();
            }
        }
        
        /*move all entities*/
        sIter = entity.iterator();
        while(sIter.hasNext()){
            Sprite e = sIter.next();
            if(e.getY() > 640){
                sIter.remove();
            }
            else{
                e.setY(e.getY()+5);
            }
        }
    }

    @Override
    public void render(Graphics2D gd) {
        gd.setColor(Color.LIGHT_GRAY);
        gd.fillRect(0, 0, getWidth(), 640);
        
        for(Sprite b: collision_box){
            b.render(gd);
        }
        for(Sprite b: entity){
            b.render(gd);
        }
    }
    
    public void setCollisionArea(){
        for(int i = 0; i < 640; i+=80){
            collision_box.add(new Sprite(getImage("collisionBlock.png"), i, 560));
        }
    }

    private void spawn_entity() {
        Random rand = new Random();
        int index = rand.nextInt(7);
        entity.add(new Sprite(getImage("entityBlock.png"), index*80, -40));
    }
    
    private void listenInput() {
        if(keyDown(KeyEvent.VK_Q))
            pressed.add(0);
        else if(!keyDown(KeyEvent.VK_Q))
            pressed.remove(Integer.valueOf(0));
        
        if(keyDown(KeyEvent.VK_W))
            pressed.add(1);
        else if(!keyDown(KeyEvent.VK_W))
            pressed.remove(Integer.valueOf(1));
        
        if(keyDown(KeyEvent.VK_E))
            pressed.add(2);
        else if(!keyDown(KeyEvent.VK_E))
            pressed.remove(Integer.valueOf(2));
        
        if(keyDown(KeyEvent.VK_R))
            pressed.add(3);
        else if(!keyDown(KeyEvent.VK_R))
            pressed.remove(Integer.valueOf(3));
        
        if(keyDown(KeyEvent.VK_U))
            pressed.add(4);
        else if(!keyDown(KeyEvent.VK_U))
            pressed.remove(Integer.valueOf(4));
        
        if(keyDown(KeyEvent.VK_I))
            pressed.add(5);
        else if(!keyDown(KeyEvent.VK_I))
            pressed.remove(Integer.valueOf(5));
        
        if(keyDown(KeyEvent.VK_O))
            pressed.add(6);
        else if(!keyDown(KeyEvent.VK_O))
            pressed.remove(Integer.valueOf(6));
        
        if(keyDown(KeyEvent.VK_P))
            pressed.add(7);
        else if(!keyDown(KeyEvent.VK_P))
            pressed.remove(Integer.valueOf(7));
    }
    
}