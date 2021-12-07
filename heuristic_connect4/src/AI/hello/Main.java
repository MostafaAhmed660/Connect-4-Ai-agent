package AI.hello;

public class Main {

    public static void main(String[] args) {

        int[][] state = new int[][] {
                new int[] {1,1,1,1,1,0,0,0},
                new int[] {0,1,2,2,2,0,0,0},
                new int[] {0,1,1,2,0,0,0,0},
                new int[] {0,1,0,1,0,0,0,0},
                new int[] {0,0,0,0,0,0,0,0},
                new int[] {0,0,0,0,0,0,0,0},
                new int[] {0,0,0,0,0,0,0,0},
                new int[] {0,0,0,0,0,0,0,0}
        };


        System.out.println(scoreCalculator.playerOneScore(state));
        System.out.println(scoreCalculator.playerTwoScore(state));
        System.out.println(heuristic_Function.eval_state(state));
    }
}
