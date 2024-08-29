package org.example.Controller;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
public class SpriteSheet implements Serializable {
    private Image sheet;
    public SpriteSheet(String path){
        try {
            sheet = ImageIO.read(new File("D:\\github\\mario-plus-plus\\untitled\\src\\main\\java\\org\\example\\Resources\\images\\" + path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public Image getSheet() {
        return sheet;
    }
}