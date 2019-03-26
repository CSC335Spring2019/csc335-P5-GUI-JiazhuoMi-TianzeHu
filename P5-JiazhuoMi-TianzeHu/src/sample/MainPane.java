package sample;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.io.*;


public class MainPane extends BorderPane{

    private Label label;

    private MyStackPane[][] myStackPanes;

    private MyObservable[][] myObservables;

    private boolean isBlackPlay = true;

    boolean gameover = false;

    private int blackChess = 2;

    private int whiteChess = 2;

    public MainPane( ) {
        this.myStackPanes = new MyStackPane[8][8];
        this.myObservables = new MyObservable[8][8];
        this.setPrefSize(368, 398);
        this.setCenter(setTilePane());
        this.setBottom(setLabel());
        MyThread myThread = new MyThread();
        myThread.start();
    }

    private BorderPane setTilePane() {
        BorderPane borderPane = new BorderPane();
        borderPane.setPrefSize(368, 368);
        borderPane.setBackground(new Background(new BackgroundFill(Color.WHITE,null,null)));

        TilePane tilePane = new TilePane();
        tilePane.setPadding(new Insets(0, 8, 0, 8));
        tilePane.setPrefColumns(8);              //preferred columns
        tilePane.setPrefSize(368, 368);
        tilePane.setBackground(new Background(new BackgroundFill(Color.GREEN,null,null)));
        tilePane.setAlignment(Pos.CENTER);
        tilePane.setOnMouseClicked(this::mouseClicked);

        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                myObservables[i][j] = new MyObservable(i, j, Color.GREEN);
                myStackPanes[i][j] = new MyStackPane(i, j, myObservables[i][j]);
                tilePane.getChildren().add(myStackPanes[i][j]);
            }
        }

        myStackPanes[3][3].setCircleColor(Color.WHITE);
        myObservables[3][3].setColor(Color.WHITE);
        
        myStackPanes[3][4].setCircleColor(Color.BLACK);
        myObservables[3][4].setColor(Color.BLACK);
        
        myStackPanes[4][3].setCircleColor(Color.BLACK);
        myObservables[4][3].setColor(Color.BLACK);
        
        myStackPanes[4][4].setCircleColor(Color.WHITE);
        myObservables[4][4].setColor(Color.WHITE);

        borderPane.setCenter(tilePane);
        return borderPane;
    }

    private Label setLabel() {
        label = new Label();
        label.setPrefSize(368, 30);
        label.setBackground(new Background(new BackgroundFill(Color.WHITE,null,null)));
        updateLabel();
        return label;
    }

    private void mouseClicked(MouseEvent event) {

        System.out.println("      mouseClick      x = " + event.getX() + "    y = " + event.getY());

        double x = event.getX();
        double y = event.getY();
        int j = getIJ(x);
        int i = getIJ(y);

        System.out.println("      mouseClick      i = " + i + "    j = " + j);

        actionPerformed(i, j);
    }

    public int getIJ(double y) {
        int j = -1;
        if(y >= 8 && y < 52) {
            j = 0;
        } else if(y >= 52 && y < 96) {
            j = 1;
        } else if(y >= 96 && y < 140) {
            j = 2;
        } else if(y >= 140 && y < 184) {
            j = 3;
        } else if(y >= 184 && y < 228) {
            j = 4;
        } else if(y >= 228 && y < 272) {
            j = 5;
        } else if(y >= 272 && y < 316) {
            j = 6;
        } else if(y >= 316 && y < 360) {
            j = 7;
        }
        return j;
    }

    private void actionPerformed(int i, int j) {

        if(gameover){
            setAlert("The chess game is over. Please start again!");
        }else{
            if(!isValidPosition(i, j)){
                setAlert("Illegal location, please reset!");
            } else {
                refresh(i, j);
                changePlayer();
                if(!hasValidPosition()){
                    changePlayer();
                    if(!hasValidPosition()){
                        gameover = true;
                        whoWin();
                    }else{
                        setAlert("The other side has nowhere to go!");
                    }


                }
            }
        }

    }

    private boolean isValidPosition(int x, int y){

        if(myObservables[x][y].getColor() != Color.GREEN) {
            return false;
        }

        if(isBlackPlay){



            // Vertical Direction Judgment
            for(int i = 0; i < 8; i++){
                if(myObservables[i][y].getColor() == Color.BLACK){

                    System.out.println(" Vertical： myObservables[" + i + "][" + y + "] is BLACK");

                    if((i - x) >= 2){

                        System.out.println("Vertical： (i - x) >= 2 ,   (i - x) = " + (i - x) + "    i = " + i + "  x = " + x);

                        int count = 0;
                        for(int k = x; k < i; k++){

                            if(myObservables[k][y].getColor() == Color.WHITE){
                                count++;
                                System.out.println("Vertical： myObservables[" + k + "][" + y + "] is WHITE  , count = " + count);
                            }
                        }

                        System.out.println("Vertical：  count = " + count + "   i = " + i + "  x = " + x);
                        if(count == (i - x - 1)){
                            return true;
                        }
                    }
                    if((x - i) >= 2){
                        int count = 0;
                        for(int k = x;k > i; k--){
                            if(myObservables[k][y].getColor() == Color.WHITE){
                                count++;
                                System.out.println("Vertical： myObservables[" + k + "][" + y + "] is WHITE  , count = " + count);
                            }
                        }

                        System.out.println("Vertical：  count = " + count + "   i = " + i + "  x = " + x);
                        if(count == (x - i - 1)) {
                            return true;
                        }
                    }
                }
            }

            for(int j = 0;j < 8; j++){
                if(myObservables[x][j].getColor() == Color.BLACK){

                    if((j - y) >= 2){

                        int count = 0;
                        for(int k = y; k < j; k++){
                            if(myObservables[x][k].getColor() ==  Color.WHITE) {
                                count++;
                            }
                        }
                        if(count == (j - y - 1)) {
                            return true;
                        }
                    }
                    if((y - j) >= 2){
                        int count = 0;
                        for(int k = y;k > j; k--){
                            if(myObservables[x][k].getColor() == Color.WHITE) {
                                count++;
                            }
                        }

                        if(count == (y - j - 1)) {
                            return true;
                        }
                    }
                }
            }

            for(int i = 0;i < 8; i++){
                for(int j = 0;j < 8; j++){
                    if(myObservables[i][j].getColor() == Color.BLACK){
                        if((x - i) == (y - j) && (x - i) >= 2){
                            int yy = y;
                            int count = 0;
                            for(int k = x; k > i; k--){
                                if(myObservables[k][yy].getColor() == Color.WHITE)
                                    count++;
                                yy--;
                            }
                            if(count == (x - i - 1))
                                return true;
                        }
                        if((x - i) == (j - y) && (x - i) >= 2){
                            int yy = y;
                            int count = 0;
                            for(int k = x; k > i; k--){
                                if(myObservables[k][yy].getColor() == Color.WHITE){
                                    count++;
                                }
                                yy++;
                            }
                            if(count == (x-i-1))
                                return true;
                        }
                        if((i - x) == (y - j) && (i - x) >= 2){
                            int yy = y;
                            int count = 0;
                            for(int k = x; k < i; k++){
                                if(myObservables[k][yy].getColor() == Color.WHITE)
                                    count++;
                                yy--;
                            }
                            if(count == (i - x - 1))
                                return true;
                        }
                        if((i - x) == (j - y) && (i - x) >= 2){
                            int yy = y;
                            int count = 0;
                            for(int k = x; k < i; k++){
                                if(myObservables[k][yy].getColor() == Color.WHITE)
                                    count++;
                                yy++;
                            }
                            if(count == (i - x - 1))
                                return true;
                        }
                    }
                }
            }
            return false;
        } else{

            for (int i = 0; i < 8; i++) {
                if (myObservables[i][y].getColor() == Color.WHITE) {


                    if ((i - x) >= 2) {

                        int count = 0;
                        for (int k = x; k < i; k++) {
                            if (myObservables[k][y].getColor() == Color.BLACK) {
                                count++;
                            }

                        }
                        if (count == (i - x - 1)) {
                            return true;
                        }
                    }
                    if ((x - i) >= 2) {
                        int count = 0;
                        for (int k = x; k > i; k--) {
                            if (myObservables[k][y].getColor() == Color.BLACK) {
                                count++;
                            }
                        }
                        if (count == (x - i - 1)) {
                            return true;
                        }
                    }
                }
            }

            for (int j = 0; j < 8; j++) {
                if (myObservables[x][j].getColor() == Color.WHITE) {

                    if ((j - y) >= 2) {
                        int count = 0;
                        for (int k = y; k < j; k++) {
                            if (myObservables[x][k].getColor() == Color.BLACK) {
                                count++;
                            }
                        }
                        if (count == (j - y - 1)) {
                            return true;
                        }
                    }

                    if ((y - j) >= 2) {

                        int count = 0;
                        for (int k = y; k > j; k--) {
                            if (myObservables[x][k].getColor() == Color.BLACK) {
                                count++;
                            }
                        }
                        if (count == (y - j - 1)) {
                            return true;
                        }
                    }
                }
            }

            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                     if (myObservables[i][j].getColor() == Color.WHITE) {
                        if ((x - i) == (y - j) && (x - i) >= 2) {
                            int yy = y;
                            int count = 0;
                            for (int k = x; k > i; k--) {
                                if (myObservables[k][yy].getColor() == Color.BLACK) {
                                    count++;
                                }
                                yy--;

                            }
                            if (count == (x - i - 1))
                                return true;
                        }

                        if ((x - i) == (j - y) && (x - i) >= 2) {
                            int yy = y;
                            int count = 0;
                            for (int k = x; k > i; k--) {
                                if (myObservables[k][yy].getColor() == Color.BLACK)
                                {
                                    count++;
                                }
                                yy++;

                            }
                            if (count == (x - i - 1))
                                return true;
                        }

                        if ((i - x) == (y - j) && (i - x) >= 2) {
                            int yy = y;
                            int count = 0;
                            for (int k = x; k < i; k++) {
                                if (myObservables[k][yy].getColor() == Color.BLACK) {
                                    count++;
                                }
                                yy--;
                            }
                            if (count == (i - x - 1))
                                return true;
                        }

                        if ((i - x) == (j - y) && (i - x) >= 2) {
                            int yy = y;
                            int count = 0;
                            for (int k = x; k < i; k++) {
                                if (myObservables[k][yy].getColor() == Color.BLACK) {
                                    count++;
                                }
                                yy++;
                            }
                            if (count == (i - x - 1))
                                return true;
                        }

                    }
                }
            }
            return false;
        }
    }

    private void refresh(int x, int y)
    {
        if (isBlackPlay)
        {
            for (int i = 0; i < 8; i++)
            {
                if (myObservables[i][y].getColor() == Color.BLACK)
                {
                    if ((i - x) >= 2)
                    {
                        int count = 0;
                        for (int k = x; k < i; k++)
                        {
                            if (myObservables[k][y].getColor() == Color.WHITE)
                                count++;
                        }

                        if (count == (i - x - 1))
                        {
                            for (int k = x; k < i; k++)
                                myObservables[k][y].updateObserver(Color.BLACK);
                        }

                    }

                    if ((x - i) >= 2)
                    {
                        int count = 0;
                        for (int k = x; k > i; k--)
                        {
                            if (myObservables[k][y].getColor() == Color.WHITE)
                                count++;
                        }

                        if (count == (x - i - 1))
                        {
                            for (int k = x; k > i; k--)
                                myObservables[k][y].updateObserver(Color.BLACK);
                        }
                    }
                }
            }

            for (int j = 0; j < 8; j++)
            {
                if (myObservables[x][j].getColor() == Color.BLACK)
                {
                    if ((j - y) >= 2)
                    {
                        int count = 0;
                        for (int k = y; k < j; k++)
                        {
                            if (myObservables[x][k].getColor() == Color.WHITE)
                                count++;
                        }

                        if (count == (j - y - 1))
                        {
                            for (int k = y; k < j; k++)
                                myObservables[x][k].updateObserver(Color.BLACK);
                        }
                    }

                    if ((y - j) >= 2)
                    {
                        int count = 0;
                        for (int k = y; k > j; k--)
                        {
                            if (myObservables[x][k].getColor() == Color.WHITE)
                                count++;
                        }
                        if (count == (y - j - 1))
                        {
                            for (int k = y; k > j; k--)
                                myObservables[x][k].updateObserver(Color.BLACK);
                        }
                    }
                }
            }

            for (int i = 0; i < 8; i++)
            {
                for (int j = 0; j < 8; j++)
                {
                    if (myObservables[i][j].getColor() == Color.BLACK)
                    {
                        if ((x - i) == (y - j) && (x - i) >= 2)
                        {
                            int yy = y;
                            int count = 0;
                            for (int k = x; k > i; k--)
                            {
                                if (myObservables[k][yy].getColor() == Color.WHITE)
                                {
                                    count++;
                                }
                                yy--;

                            }
                            if (count == (x - i - 1))
                            {
                                yy = y;
                                for (int k = x; k > i; k--)
                                {
                                    myObservables[k][yy].updateObserver(Color.BLACK);
                                    yy--;
                                }
                            }
                        }

                        if ((x - i) == (j - y) && (x - i) >= 2)
                        {
                            int yy = y;
                            int count = 0;
                            for (int k = x; k > i; k--)
                            {
                                if (myObservables[k][yy].getColor() == Color.WHITE)
                                {
                                    count++;
                                }
                                yy++;

                            }
                            if (count == (x - i - 1))
                            {
                                yy = y;
                                for (int k = x; k > i; k--)
                                {
                                    myObservables[k][yy].updateObserver(Color.BLACK);
                                    yy++;
                                }
                            }
                        }

                        if ((i - x) == (y - j) && (i - x) >= 2)
                        {
                            int yy = y;
                            int count = 0;
                            for (int k = x; k < i; k++)
                            {
                                if (myObservables[k][yy].getColor() == Color.WHITE)
                                {
                                    count++;
                                }
                                yy--;

                            }
                            if (count == (i - x - 1))
                            {
                                yy = y;
                                for (int k = x; k < i; k++)
                                {
                                    myObservables[k][yy].updateObserver(Color.BLACK);
                                    yy--;
                                }
                            }
                        }

                        if ((i - x) == (j - y) && (i - x) >= 2)
                        {
                            int yy = y;
                            int count = 0;
                            for (int k = x; k < i; k++)
                            {
                                if (myObservables[k][yy].getColor() == Color.WHITE)
                                {
                                    count++;
                                }
                                yy++;

                            }
                            if (count == (i - x - 1))
                            {
                                yy = y;
                                for (int k = x; k < i; k++)
                                {
                                    myObservables[k][yy].updateObserver(Color.BLACK);
                                    yy++;
                                }
                            }
                        }

                    }
                }
            }

        }
        else
        // if it is white down
        {
            for (int i = 0; i < 8; i++)
            {
                if (myObservables[i][y].getColor() == Color.WHITE)
                {
                    if ((i - x) >= 2)
                    {
                        int count = 0;
                        for (int k = x; k < i; k++)
                        {
                            if (myObservables[k][y].getColor() == Color.BLACK)
                                count++;
                        }

                        if (count == (i - x - 1))
                        {
                            for (int k = x; k < i; k++)
                                myObservables[k][y].updateObserver(Color.WHITE);
                        }

                    }
                    if ((x - i) >= 2)
                    {
                        int count = 0;
                        for (int k = x; k > i; k--)
                        {
                            if (myObservables[k][y].getColor() == Color.BLACK)
                                count++;
                        }

                        if (count == (x - i - 1))
                        {
                            for (int k = x; k > i; k--)
                                myObservables[k][y].updateObserver(Color.WHITE);
                        }
                    }
                }
            }

            for (int j = 0; j < 8; j++)
            {
                if (myObservables[x][j].getColor() == Color.WHITE)
                {
                    if ((j - y) >= 2)
                    {
                        int count = 0;
                        for (int k = y; k < j; k++)
                        {
                            if (myObservables[x][k].getColor() == Color.BLACK)
                                count++;
                        }

                        if (count == (j - y - 1))
                        {
                            for (int k = y; k < j; k++)
                                myObservables[x][k].updateObserver(Color.WHITE);
                        }
                    }
                    if ((y - j) >= 2)
                    {
                        int count = 0;
                        for (int k = y; k > j; k--)
                        {
                            if (myObservables[x][k].getColor() == Color.BLACK)
                                count++;
                        }
                        if (count == (y - j - 1))
                        {
                            for (int k = y; k > j; k--)
                                myObservables[x][k].updateObserver(Color.WHITE);
                        }
                    }
                }
            }

            for (int i = 0; i < 8; i++)
            {
                for (int j = 0; j < 8; j++)
                {
                    if (myObservables[i][j].getColor() == Color.WHITE)
                    {
                        if ((x - i) == (y - j) && (x - i) >= 2)
                        {
                            int yy = y;
                            int count = 0;
                            for (int k = x; k > i; k--)
                            {
                                if (myObservables[k][yy].getColor() == Color.BLACK)
                                {
                                    count++;
                                }
                                yy--;
                            }
                            if (count == (x - i - 1))
                            {
                                yy = y;
                                for (int k = x; k > i; k--)
                                {
                                    myObservables[k][yy].updateObserver(Color.WHITE);
                                    yy--;
                                }
                            }
                        }

                        if ((x - i) == (j - y) && (x - i) >= 2)
                        {
                            int yy = y;
                            int count = 0;
                            for (int k = x; k > i; k--)
                            {
                                if (myObservables[k][yy].getColor() == Color.BLACK)
                                {
                                    count++;
                                }
                                yy++;

                            }
                            if (count == (x - i - 1))
                            {
                                yy = y;
                                for (int k = x; k > i; k--)
                                {
                                    myObservables[k][yy].updateObserver(Color.WHITE);
                                    yy++;
                                }
                            }
                        }
                        if ((i - x) == (y - j) && (i - x) >= 2)
                        {
                            int yy = y;
                            int count = 0;
                            for (int k = x; k < i; k++)
                            {
                                if (myObservables[k][yy].getColor() == Color.BLACK)
                                {
                                    count++;
                                }
                                yy--;

                            }
                            if (count == (i - x - 1))
                            {
                                yy = y;
                                for (int k = x; k < i; k++)
                                {
                                    myObservables[k][yy].updateObserver(Color.WHITE);
                                    yy--;
                                }
                            }
                        }
                        if ((i - x) == (j - y) && (i - x) >= 2)
                        {
                            int yy = y;
                            int count = 0;
                            for (int k = x; k < i; k++)
                            {
                                if (myObservables[k][yy].getColor() == Color.BLACK)
                                {
                                    count++;
                                }
                                yy++;
                            }
                            if (count == (i - x - 1))
                            {
                                yy = y;
                                for (int k = x; k < i; k++)
                                {
                                    myObservables[k][yy].updateObserver(Color.WHITE);
                                    yy++;
                                }
                            }
                        }
                    }
                }
            }
        }
        updateLabel();
    }

    /**
     * @return
     * Judge if there is still a place to go
     */
    private boolean hasValidPosition(){
        for(int i = 0; i < 8; i++){
            for(int j = 0;j < 8; j++){
                if(isValidPosition(i, j))
                    return true;
            }
        }
        return false;
    }

    public void changePlayer(){
        if(isBlackPlay) {
            isBlackPlay = false;
        } else {
            isBlackPlay = true;
        }
    }

    private void whoWin(){
        if(blackChess > whiteChess) {
            setAlert( "Congratulations on the victory of the Black side！");
        } else if(blackChess < whiteChess) {
            setAlert("Congratulations on the victory of the While side！");
        } else {
            setAlert("A draw.！");
        }
    }

    private void updateLabel() {
        blackChess = 0;
        whiteChess = 0;
        for(int i = 0; i < 8; i++){
            for(int j = 0;j < 8; j++){
                if(myObservables[i][j].getColor()== Color.BLACK)
                    blackChess++;
                if(myObservables[i][j].getColor()== Color.WHITE)
                    whiteChess++;
            }
        }
        label.setText("White: " + whiteChess + "  - Black: " + blackChess);
    }

    private void setAlert(String string) {
        Alert information = new Alert(Alert.AlertType.INFORMATION, string);
        information.setTitle("Message");
        information.setHeaderText("Message");
        information.showAndWait();
    }

    public void saveGame() {

        System.out.println(" ***************************    save Game");

        int[][] data = new int[8][8];
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if(myObservables[i][j].getColor()== Color.BLACK) {
                    data[i][j] = 1;
                } else if(myObservables[i][j].getColor()== Color.WHITE) {
                    data[i][j] = -1;
                } else {
                    data[i][j] = 0;
                }
            }
        }
        File file = new File("output.txt");
        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            for(int i = 0; i < 8; i++){
                for(int j = 0;j < 8; j++){
                    if(j != 0)
                        bw.write(",");
                    bw.write(String.valueOf(data[i][j]));
                }
                bw.write("\r\n");
            }
            bw.write(isBlackPlay + "\r\n");
            bw.write("0 means blank, 1 means black, and - 1 means white. True means that the black chess is gone.");
            bw.flush();
            bw.close();
        }catch (Exception e1) {
            // TODO: handle exception
            e1.printStackTrace();
        }
    }

    public class MyThread extends Thread {
        @Override
        public void run() {
            try {
                Thread.sleep(1000);
                reShow();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void reShow() {

        System.out.println(" ***************************    reshow Game");

        int[][] data = new int[8][8];
        try{
            File file = new File("output.txt");
            System.out.println(" file.exists():" + file.exists());
            if(file.exists()) {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String line = br.readLine();
                int num=0;
                while(line != null && num < 8){

                    System.out.println(" line:" + line);

                    String[] temp = line.split(",");
                    for(int i = 0; i < temp.length; i++){
                        data[num][i] = Integer.parseInt(temp[i]);
                    }
                    line = br.readLine();
                    num++;
                }
                isBlackPlay = Boolean.parseBoolean(line);
                for(int i = 0; i < 8; i++){
                    for(int j = 0; j < 8; j++){
                        if(data[i][j] == 1){
                            myObservables[i][j].updateObserver(Color.BLACK);
                        }else if(data[i][j]==-1){
                            myObservables[i][j].updateObserver(Color.WHITE);
                        }else{
                            myObservables[i][j].updateObserver(Color.GREEN);
                        }
                    }
                }
                String s;
                if(isBlackPlay) {
                    s = "Black";
                } else {
                    s = "White";
                }
                Platform.runLater(()-> {
                    updateLabel();
                    setAlert("Open successfully and then go to " + s );
                });

            }

        }catch (Exception e2) {
            // TODO: handle exception
            e2.printStackTrace();
        }
    }

}
