import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.FocusListener;
import java.awt.event.FocusEvent;

/**
 * Created by Victor on 5/6/2017.
 */
public class KeyBoardListener implements KeyListener, FocusListener {

    public boolean[] keys = new boolean[120];
    private Game game;
    public KeyBoardListener(Game game){
        this.game = game;
    }


    public void keyPressed(KeyEvent event) {

        int keyCode = event.getKeyCode();
        if(keyCode < keys.length) keys[keyCode] = true;

        //System.out.println(event.getKeyCode());

        if(keys[KeyEvent.VK_CONTROL]) game.handleCTRL(keys);
    }


    public void keyReleased(KeyEvent event) {
        int keyCode = event.getKeyCode();
        if(keyCode < keys.length) keys[keyCode] = false;

    }


    public void focusLost(FocusEvent event){
        for (int i = 0; i < keys.length; i++) keys[i] = false;
    }

    public void keyTyped(KeyEvent event) {}

    public void focusGained(FocusEvent event){}

    public boolean up(){
        return keys[KeyEvent.VK_W] || keys[KeyEvent.VK_UP];
    }

    public boolean down(){
        if(!keys[KeyEvent.VK_CONTROL]){
            return keys[KeyEvent.VK_S] || keys[KeyEvent.VK_DOWN];
        }else{
            return false;
        }
    }

    public boolean left(){
        return keys[KeyEvent.VK_A] || keys[KeyEvent.VK_LEFT];
    }

    public boolean right(){
        return keys[KeyEvent.VK_D] || keys[KeyEvent.VK_RIGHT];
    }

    public boolean maximize(){
        return keys[KeyEvent.VK_COMMA];
    }
    public boolean minimize(){
        return keys[KeyEvent.VK_PERIOD];
    }

//    public boolean zoomOut(){
//        return keys[KeyEvent.VK_MINUS];
//    }
//    public int zoomIn(){
//        System.out.println(keys[KeyEvent.VK_PLUS]);
//        return 1;

}

