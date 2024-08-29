package org.example.Model.Tile.Pipes;

import org.example.Controller.Handler;
import org.example.Model.Id;
import java.awt.*;
public class SimplePipe extends PipeGame {
    public SimplePipe(int x, int y, int width, int height, Id id, Handler handler) {
        super(x, y, width, height, id, handler);
    }
    @Override
    public void paint(Graphics g) {
        g.setColor(new Color(128 , 128 , 128));
        g.fillRect(getX() , getY() , getWidth() , getHeight());
    }
    @Override
    public void update() {

    }
}
