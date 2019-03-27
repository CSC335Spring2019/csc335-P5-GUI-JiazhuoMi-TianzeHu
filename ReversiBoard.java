
import java.io.Serializable;
import java.util.Arrays;

/**
 * The class that saves chessboard  data, implements Serializable, can be serialized to save
 */
public class ReversiBoard implements Serializable {

    private static final long serialVersionUID = 1L;

    private MyObservable[][] myObservables;

    private boolean isBlackPlay;

    public ReversiBoard(MyObservable[][] myObservables, boolean isBlackPlay) {
        this.myObservables = myObservables;
        this.isBlackPlay = isBlackPlay;
    }

    public MyObservable[][] getMyObservables() {
        return myObservables;
    }

    public void setMyObservables(MyObservable[][] myObservables) {
        this.myObservables = myObservables;
    }

    public boolean isBlackPlay() {
        return isBlackPlay;
    }

    public void setBlackPlay(boolean blackPlay) {
        isBlackPlay = blackPlay;
    }

    @Override
    public String toString() {
        return "ReversiBoard{" +
            "myObservables=" + Arrays.toString(myObservables) +
            ", isBlackPlay=" + isBlackPlay +
            '}';
    }
}
