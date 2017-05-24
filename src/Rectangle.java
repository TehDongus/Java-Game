/**
 * Created by Victor on 5/4/2017.
 */
public class Rectangle {
    public int x,y,w,h;
    private int[] pixels;

    Rectangle(int x, int y, int w, int h){
        this.x = x;
        this.y = y;
        this.h = h;
        this.w = w;
    }

    Rectangle(){
        this(0,0,0,0);
    }

    public void generateGraphics(int color){
        pixels = new int[w*h];
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                pixels[x+y*w] = color;
            }
        }

//        Does the same thing
//        for (int i = 0; i < pixels.length; i++) {
//            pixels[i] = color;
//        }
    }

    //with a border
    public void generateGraphics(int borderWidth, int color){
        pixels = new int[w*h];
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                pixels[x+y*w] = Game.alpha;
            }
        }

        //top border
        for (int y = 0; y < borderWidth; y++) {
            for (int x = 0; x < w; x++) {
                pixels[x+y*w] = 0xFF0000;
            }
        }

        //bottom border
        for (int y = 0; y < borderWidth; y++) {
            for (int x = 0; x < w; x++) {
                pixels[(x+(y+(h-borderWidth))*w)] = 0xFF0000;
            }
        }

        //left border
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < borderWidth; x++) {
                pixels[x+y*w] = 0xFF0000;
            }
        }

        //right border
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < borderWidth; x++) {
                pixels[(x+(w-borderWidth))+y*w] = 0xFF0000;
            }
        }

    }

    public boolean intersects(Rectangle otherRectangle){
        if(x > otherRectangle.x + otherRectangle.w || otherRectangle.x > x + w) return false;

        if(y > otherRectangle.y + otherRectangle.h || otherRectangle.y > y + h) return false;

        return true;
    }

    public int[] getPixels(){
        if (pixels != null){
            return pixels;
        }else{
            System.out.println("Attempted to retrieve pixels from Rectangle without generated graphics.");
        }
        return null;
    }

}
