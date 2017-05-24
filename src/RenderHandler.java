import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;


public class RenderHandler
{
    private BufferedImage view;
    private Rectangle camera;
    private int[] pixels;

    public RenderHandler(int screenWidth, int screenHeight)
    {
        //Create a BufferedImage that will represent our view.
        view = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_RGB);

        camera = new Rectangle(0,0, screenWidth, screenHeight);
        camera.x = -100;
        camera.y = 0;

        //Create an array for pixels
        pixels = ((DataBufferInt) view.getRaster().getDataBuffer()).getData();

//        //outputs horizontal lines of color one pixel high
//        for(int heightIndex = 0; heightIndex < screenHeight; heightIndex++) {
//            int randomPixelColor = (int)(Math.random() * 0xFFFFFF);
//
//            for(int widthIndex = 0; widthIndex < screenWidth; widthIndex++) {
//                pixels[heightIndex*screenWidth + widthIndex] = randomPixelColor;
//            }
//
//        }

    }

    public void render(Graphics graphics)
    {

        graphics.drawImage(view, 0, 0, view.getWidth(), view.getHeight(), null);
    }



    public void renderImage(BufferedImage image, int xPosition, int yPosition, int xZoom, int yZoom, boolean fixed) {
        int[] imagePixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
        renderArray(imagePixels, image.getWidth(), image.getHeight(), xPosition, yPosition, xZoom, yZoom, fixed);

    }

    public void renderSprite(Sprite sprite, int xPosition, int yPosition, int xZoom, int yZoom, boolean fixed){
        renderArray(sprite.getPixels(), sprite.getWidth(), sprite.getHeight(), xPosition, yPosition, xZoom, yZoom, fixed);
    }

    public void renderRectangle(Rectangle rectangle, int xZoom, int yZoom, boolean fixed){
        int[] rectanglePixels = rectangle.getPixels();
        if(rectanglePixels != null)
            renderArray(rectanglePixels, rectangle.w, rectangle.h, rectangle.x, rectangle.y, xZoom, yZoom, fixed);
    }

    public void renderArray(int[] renderPixels, int renderWidth, int renderHeight, int xPosition, int yPosition, int xZoom, int yZoom, boolean fixed){
        for (int y = 0; y < renderHeight; y++)
            for (int x = 0; x < renderWidth; x++)
                for (int yZoomPosition = 0; yZoomPosition < yZoom; yZoomPosition++)
                    for (int xZoomPosition = 0; xZoomPosition < xZoom; xZoomPosition++)
                        setPixel(renderPixels[x + y *renderWidth], ((x * xZoom) + xPosition + xZoomPosition), ((y * yZoom) + yPosition + yZoomPosition), fixed);
    }

    private void setPixel(int pixel, int x, int y, boolean fixed){
        int pixelIndex = 0;
        if(!fixed){
            if(x >= camera.x && y >= camera.y && x <= camera.w + camera.x && y <= camera.y + camera.h)
                pixelIndex = ((x - camera.x)+ (y- camera.y)* view.getWidth());
        }
        else{
            if(x >= 0 && y >= 0 && x <= camera.w && y <= camera.h){
                pixelIndex = x + y * view.getWidth();
            }
        }

        if(pixels.length > pixelIndex && pixel != Game.alpha) pixels[pixelIndex] = pixel;

    }

    public Rectangle getCamera(){
        return camera;
    }

    public void clear(){
        for (int i = 0; i < pixels.length; i++) {
            pixels[i] = 0;
        }
    }

    public int getWidth(){
        return view.getWidth();
    }

    public int getHeight(){
        return view.getHeight();
    }
}