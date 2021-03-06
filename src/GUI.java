import com.sun.org.apache.xpath.internal.operations.Bool;

/**
 * Created by Victor on 5/11/2017.
 */
public class GUI implements GameObject{
    private Sprite backgroundSprite;
    private GUIButton[] buttons;
    private Rectangle rect = new Rectangle();
    private Boolean fixed;

    public GUI(Sprite backgroundSprite, GUIButton[] buttons, int x, int y, boolean fixed){
        this.backgroundSprite = backgroundSprite;
        this.buttons = buttons;
        this.fixed = fixed;

        rect.x = x;
        rect.y = y;
        if (backgroundSprite != null) {
            rect.w = backgroundSprite.getWidth();
            rect.h = backgroundSprite.getHeight();
        }
    }
    public GUI(GUIButton[] buttons, int x, int y, boolean fixed) {
        this(null, buttons, x, y, fixed);
    }

    public void render(RenderHandler renderer, int xZoom, int yZoom) {
        if(backgroundSprite!=null){
            renderer.renderSprite(backgroundSprite, rect.x, rect.y, xZoom,yZoom, fixed);
        }

        if(buttons != null) {
            for (int i = 0; i < buttons.length; i++) {
                buttons[i].render(renderer, xZoom, yZoom, rect);
            }
        }
    }

    public void update(Game game) {
        if(buttons != null){
            for (int i = 0; i < buttons.length; i++) {
                buttons[i].update(game);
            }
        }
    }

    public void handleMouseClick(Rectangle mouseRectangle, Rectangle camera, int xZoom, int yZoom) {
        if(!fixed) mouseRectangle = new Rectangle(mouseRectangle.x + camera.x, mouseRectangle.y + camera.y, 1, 1);

        if(rect.w == 0 || rect.h == 0 || mouseRectangle.intersects(rect))
            for (int i = 0; i < buttons.length; i++){
                mouseRectangle.x += rect.x;
                mouseRectangle.y += rect.y;
                buttons[i].handleMouseClick(mouseRectangle, camera, xZoom, yZoom);
            }

    }
}
