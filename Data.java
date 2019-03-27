
import java.io.Serializable;
import java.util.Arrays;

/**
 * The class that saves chessboard data, implements Serializable, can be serialized to save
 */
public class Data implements Serializable {

    private static final long serialVersionUID = 1L;

    private ReversiModel[][] reversiModels;

    private boolean isBlackPlay;

    public Data(ReversiModel[][] reversiModels, boolean isBlackPlay) {
        this.reversiModels = reversiModels;
        this.isBlackPlay = isBlackPlay;
    }

    public ReversiModel[][] getReversiModels() {
        return reversiModels;
    }

    public void setReversiModels(ReversiModel[][] reversiModels) {
        this.reversiModels = reversiModels;
    }

    public boolean isBlackPlay() {
        return isBlackPlay;
    }

    public void setBlackPlay(boolean blackPlay) {
        isBlackPlay = blackPlay;
    }

    @Override
    public String toString() {
        return "Data{" +
            "reversiModels=" + Arrays.toString(reversiModels) +
            ", isBlackPlay=" + isBlackPlay +
            '}';
    }
}
