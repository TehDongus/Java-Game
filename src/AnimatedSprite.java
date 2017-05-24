import java.awt.image.BufferedImage;

/**
 * Created by Victor on 5/9/2017.
 *
 * Takes a SpriteSheet and Rectangle[] (representing the positions x and y relative to the sprite sheet and width
 * and height off each image section) to loop through sections of the sprite sheet relative to their positions on said
 * sheet.
 *
 * SpriteSheet will have a run right sequence of images, next row down will be a run left sequence and so on.
 *
 */


public class AnimatedSprite extends Sprite implements GameObject{
    
    private Sprite[] sprites;
    private int currentSprite = 0;
    private int speed;
    private int counter = 0;

    private int startSprite = 0;
    //private int standingStillSprite = 0;
    private int endSprite;


    public AnimatedSprite(SpriteSheet sheet, Rectangle[] positions, int speed){
        sprites = new Sprite[positions.length];
        this.speed = speed;

        for (int i = 0; i < positions.length; i++) {
            sprites[i] = new Sprite(sheet, positions[i].x, positions[i].y, positions[i].w, positions[i].h);
        }
    }

    public AnimatedSprite(SpriteSheet sheet, int speed){
        sprites = sheet.getLoadedSprites();
        this.speed = speed;
        this.endSprite = sprites.length - 1;
    }

    //@param speed is represents how many frames pass until the sprite changes
    public AnimatedSprite(BufferedImage[] images, int speed) {
        sprites = new Sprite[images.length];
        this.speed = speed;
        this.startSprite = images.length - 1;

        for (int i = 0; i < images.length; i++) {
            sprites[i] = new Sprite(images[i]);
        }

    }

    //Render is dealt specifically with the Layer class.
    public void render(RenderHandler renderer, int xZoom, int yZoom) {
        renderer.renderSprite(this,0, 0, xZoom, yZoom, false);
    }

    //Calls at 60 fps
    public void update(Game game) {
        counter++;
        if(counter >= speed){
            counter = 0;
            incrementSprite();
        }
    }

    @Override
    public void handleMouseClick(Rectangle mouseRectangle, Rectangle camera, int xZoom, int yZoom) {

    }


    public void reset(){
        counter = 0;
        //currentSprite = standingStillSprite;
        currentSprite = startSprite;
    }

    public void setAnimationRange(int startSprite, int endSprite){
        this.startSprite = startSprite;
        this.endSprite = endSprite;
        reset();
    }

    public int getWidth() {
        return sprites[currentSprite].getWidth();
    }

    public int getHeight() {
        return sprites[currentSprite].getHeight();
    }

    public int[] getPixels() {
        return sprites[currentSprite].getPixels();
    }

    public void incrementSprite(){
        currentSprite++;
        if(currentSprite >= endSprite) currentSprite = startSprite;
    }
}
