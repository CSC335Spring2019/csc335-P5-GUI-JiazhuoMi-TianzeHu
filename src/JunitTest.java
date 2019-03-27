import org.junit.Test;

public class JunitTest {




    @Test
    public static void test() {

        MainPane mainPane = new MainPane();


        // implement
        mainPane.test(0, 0);

        // judge this five
        int whiteChessCount = mainPane.getWhiteChess();
        int blackChessCount = mainPane.getBlackChess();
        ReversiModel[][] reversiModels = mainPane.getReversiModels();
        boolean isBlackPlay = mainPane.isBlackPlay();
        boolean gameover = mainPane.isGameover();

    }




}
