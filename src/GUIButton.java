/**
 * Created by Victor on 5/11/2017.
 */
public abstract class GUIButton implements GameObject {
    protected Sprite sprite;
    protected Rectangle rect;
    protected boolean fixed;


    public GUIButton(Sprite sprite, Rectangle rect, Boolean fixed){
        this.sprite = sprite;
        this.rect = rect;
        this.fixed = fixed;
    }
    public void render(RenderHandler renderer, int xZoom, int yZoom) {
        renderer.renderSprite(sprite, rect.x, rect.y, xZoom, yZoom, fixed);
    }

    public void render(RenderHandler renderer, int xZoom, int yZoom, Rectangle interfaceRect){
        renderer.renderSprite(sprite, rect.x + interfaceRect.x, rect.y + interfaceRect.y, xZoom, yZoom, fixed);
    }


    public void update(Game game) {}


    public void handleMouseClick(Rectangle mouseRectangle, Rectangle camera, int xZoom, int yZoom) {
        if(mouseRectangle.intersects(rect)) activate();

    }

    public abstract void activate();
}
