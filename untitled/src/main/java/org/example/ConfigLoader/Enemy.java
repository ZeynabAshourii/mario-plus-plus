package org.example.ConfigLoader;

import java.io.Serializable;
public class Enemy implements Serializable {
    public  int x;
    public int y;
    public String type;

    @Override
    public String toString() {
        return "Enemy{" +
                "x=" + x +
                ", y=" + y +
                ", type='" + type + '\'' +
                '}';
    }
}
