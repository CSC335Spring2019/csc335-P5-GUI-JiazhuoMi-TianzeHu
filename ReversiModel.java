import javafx.scene.paint.Color;

import java.io.Serializable;
import java.util.Observable;

/**
 * chess pieces model, i and j represent the position information of the chess pieces,
 *
 * color represents the color of the current chess pieces, black is the black, white is the white,
 *
 * green is the current no-seed.
 */
public class ReversiModel extends Observable implements Serializable {

    private static final long serialVersionUID = 12L;

    private int i;

    private int j;

    private int color;

    public ReversiModel(int i, int j, int color) {
        this.i = i;
        this.j = j;
        this.color = color;
    }

    public ReversiModel(int i, int j, Color color) {
        this.i = i;
        this.j = j;
        setChessColor(color);
    }


    public void updateObserver(Color color) {
        setChessColor(color);
        setChanged();
        notifyObservers(getChessColor());
    }

    public void updateObserver(ReversiModel reversiModel) {
        this.i = reversiModel.getI();
        this.j = reversiModel.getJ();
        this.color = reversiModel.getColor();
        setChanged();
        notifyObservers(getChessColor());
    }

    public Color getChessColor() {
        if(this.color == -1) {
            return Color.WHITE;
        } else if (this.color == 0) {
            return Color.GREEN;
        }
        return Color.BLACK;
    }

    public void setChessColor(Color color) {
        if(color.equals(Color.WHITE)) {
            this.color = -1;
        } else if(color.equals(Color.GREEN)) {
            this.color = 0;
        } else {
            this.color = 1;
        }
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public int getJ() {
        return j;
    }

    public void setJ(int j) {
        this.j = j;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "ReversiModel{" +
            "i=" + i +
            ", j=" + j +
            ", color=" + color +
            '}';
    }
}
