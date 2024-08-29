package org.example.Model.Entity.Items;

import org.example.Controller.Handler;
import org.example.Model.Id;
import org.example.Resources.Resources;
import java.awt.*;
public class MagicFlower extends Item{
    public MagicFlower(int x, int y, int width, int height, Id id, Handler handler) {
        super(x, y, width, height, id, handler);
    }
    @Override
    public void paint(Graphics g) {
        try {
            g.drawImage(Resources.getFlowerPic().getSheet(), getX(), getY(), getWidth(), getHeight(), null);
        }
        catch (Exception e){}
    }

    @Override
    public void update() {
    }
}
