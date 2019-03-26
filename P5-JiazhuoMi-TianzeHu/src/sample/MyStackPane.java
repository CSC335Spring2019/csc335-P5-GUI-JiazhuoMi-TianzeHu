package sample;

import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;


import java.util.Observable;
import java.util.Observer;




public class MyStackPane extends StackPane implements Observer {

    private Circle circle;

    private int i;

    private int j;

    public MyStackPane(int i, int j, MyObservable myObservable) {
        this.i = i;
        this.j = j;
        myObservable.addObserver(this );
        this.setPrefSize(44, 44);
        this.getChildren().add(setDropShadow(Color.BLACK));
        Border border = new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID,
            CornerRadii.EMPTY, BorderWidths.DEFAULT));
        this.setBorder(border);
        this.setBackground(new Background(new BackgroundFill(Color.GREEN,null,null)));
    }

    private Circle setDropShadow(Color color) {

        circle = new Circle();
        circle.setCenterX(22.0f);
        circle.setCenterY(22.0f);
        circle.setRadius(20.0f);
        circle.setFill(color);
        circle.setOpacity(0);
        return circle;
    }

    public void setCircleColor(Color color) {
        circle.setFill(color);
        circle.setOpacity(1);
    }

    @Override
    public void update(Observable o, Object arg) {
        setCircleColor((Color) arg);

        System.out.println(" mystackPane   i = " + i + "  j = " + j + "   setColor :" + ((Color) arg).toString());
    }
}
