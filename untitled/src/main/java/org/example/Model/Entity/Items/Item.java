package org.example.Model.Entity.Items;

import org.example.Controller.Handler;
import org.example.Model.Entity.Entity;
import org.example.Model.Id;
import java.awt.*;
public class Item extends Entity {
    private long startTime;
    public Item(int x, int y, int width, int height, Id id, Handler handler) {
        super(x, y, width, height, id, handler);
    }
    @Override
    public void paint(Graphics g) {

    }
    @Override
    public void update() {

    }
    public long getStartTime() {
        return startTime;
    }
    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }
}
