package imageView;

import annotation.Author;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

@Author
public class cImageView extends ImageView{
    public int newX,newY;
    public cImageView(int x, int y, Image image) {
        this.setImage(image);
        this.setFitHeight(45);
        this.setFitWidth(70);
        this.newX = x;
        this.newY = y;
    }
    public void reSetImage(Image image) {
        this.setImage(image);
        this.setFitHeight(45);
        this.setFitWidth(70);
    }
    public void reLocate(int x, int y) {
        newX = x;
        newY = y;
    }
}
