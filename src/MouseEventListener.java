import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * Created by Victor on 5/7/2017.
 */
public class MouseEventListener implements MouseListener, MouseMotionListener {
    private Game game;

    public MouseEventListener(Game game){
        this.game = game;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        System.out.println(e.getX() + " " + e.getY());
        if(e.getButton() == MouseEvent.BUTTON1) game.leftClick(e.getX(), e.getY());

        if(e.getButton() == MouseEvent.BUTTON3) game.rightClick(e.getX(), e.getY());
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
