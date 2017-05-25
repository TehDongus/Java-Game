/**
 * Created by Victor on 5/19/2017.
 */
public class SDKButton extends GUIButton {


    public SDKButton(Sprite tileSprite, Rectangle rect){
        super(tileSprite,rect, true);
        rect.generateGraphics(0xFFDB3D);
    }

    @Override
    public void render(RenderHandler renderer, int xZoom, int yZoom) {
        renderer.renderRectangle(rect, xZoom, yZoom, fixed);
        renderer.renderSprite(sprite, rect.x, rect.y, xZoom, yZoom, fixed);
    }

    public void activate() {

    }
}
