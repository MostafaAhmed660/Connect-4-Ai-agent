package AI.hello;

public class Tuple {
    private int[][] state ;
    private int utility ;

    public Tuple(int[][] state, int utility) {
        this.state = state;
        this.utility = utility;
    }

    public int[][] getState() {
        return state;
    }

    public void setState(int[][] state) {
        this.state = state;
    }

    public int getUtility() {
        return utility;
    }

    public void setUtility(int utility) {
        this.utility = utility;
    }
}
