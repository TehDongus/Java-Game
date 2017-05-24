/**
 * Created by Victor on 5/6/2017.
 */
public interface GameObject {
    //Call every time physicaslly possible
    public void render(RenderHandler renderer, int xZoom, int yZoom);

    //Calls at 60 fps rate.
    public void update(Game game);

    //Call whenever mouse is clicked on Canvas
    public void handleMouseClick(Rectangle mouseRectangle, Rectangle camera, int xZoom, int yZoom);
}
