package sample;

import javafx.scene.paint.Color;

import java.util.Observable;

public class MyObservable extends Observable {

    private int i;

    private int j;

    private Color color;

    public MyObservable(int i, int j, Color color) {
        this.i = i;
        this.j = j;
        this.color = color;
    }


    public void updateObserver(Color color) {
        this.color = color;
        setChanged();
        notifyObservers(color);
    }



    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
