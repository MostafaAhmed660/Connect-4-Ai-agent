package AI.hello;

public class heuristic_Function {

    public static int eval_state(int [][]state) {
        //get size of the array
        int n = state.length, m = state[0].length, heuristic = 0;
        int flag = 1 ;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (state[i][j] != 0) {
                    flag = state[i][j] == 1 ? 1 : -1 ;
                    heuristic += flag*checkHorizontal(state,i,j);
                    heuristic += flag*checkVertical(state,i,j);
                    heuristic += flag*checkRightDiagonal(state,i,j);
                    heuristic += flag*checkLeftDiagonal(state,i,j);

                }
            }
        }

        //player one score = agent score
        //player two score = human score
        int playerOneScore = scoreCalculator.playerOneScore(state),
                playerTwoScore = scoreCalculator.playerTwoScore(state);

        heuristic = heuristic - 4*playerOneScore + 4*playerTwoScore ;
        return heuristic ;
    }



    //start ---
    public static int checkHorizontal(int [][]state,int i,int j){
        //base case
        int m = state[0].length ;
        if (j > m-3)
            return 0 ;

        //check 4 connected
        if (j <= m-4)
            if (state[i][j] == state[i][j+1] && state[i][j+1] == state[i][j+2] && state[i][j+1] == state[i][j+3])
                return 5 ;


        int numberOfThrees = 0 ;
        //check 3 connected
        if(j <= m-3) {
            if (state[i][j] == state[i][j + 1] && state[i][j + 1] == state[i][j + 2]){

                // check if state like :1 1 1 0
                if (j-1 >= 0 && state[i][j-1] == 0 && (i == 0 || state[i-1][j-1] != 0))
                    numberOfThrees++;

                //check if state like :0 1 1 1
                if (j+3 < m && state[i][j+3] == 0 && (i == 0 || state[i-1][j+3] != 0))
                    numberOfThrees++;
            }
            return numberOfThrees;
        }
        return 0 ;
    }


    // ||
    public static int checkVertical(int [][]state,int i,int j){
        //base case
        int n = state.length ;
        if (i >= n-3)
            return 0 ;

        if (i <= n-4) {
            //check 4 connected
            if (state[i][j] == state[i + 1][j] && state[i + 1][j] == state[i + 2][j] && state[i + 2][j] == state[i + 3][j])
                return 5;

            //check 3 connected
            if (state[i][j] == state[i + 1][j] && state[i + 1][j] == state[i + 2][j] && state[i + 3][j] == 0)
                return 1;

        }
        return 0 ;
    }


    // /\
    public static int checkRightDiagonal(int [][]state,int i,int j) {
        int n = state.length , m = state[0].length;

        if (i <= n-4 && j >= 3) {
            //check 4 connected
            if (state[i][j] == state[i + 1][j - 1] && state[i + 1][j - 1] == state[i + 2][j - 2] && state[i + 2][j - 2] == state[i + 3][j - 3])
                return 5;

            //check 3 connected
            if (state[i][j] == state[i + 1][j - 1] && state[i + 1][j - 1] == state[i + 2][j - 2] && state[i+3][j-3] == 0 && state[i+2][j-3] != 0)
                return 1;
        }
        return 0 ;
    }

    public static int checkLeftDiagonal(int [][]state,int i,int j) {
        int n = state.length , m = state[0].length;

        if (i <= n-4 && j <= m-4) {
            //check 4 connected
            if (state[i][j] == state[i + 1][j + 1] && state[i + 1][j + 1] == state[i + 2][j + 2] && state[i + 2][j + 2] == state[i + 3][j + 3])
                return 5;

            //check 3 connected
            if (state[i][j] == state[i + 1][j + 1] && state[i + 1][j + 1] == state[i + 2][j + 2] && state[i + 3][j + 3] == 0 && state[i + 2][j + 3] != 0)
                return 1;
        }

        return 0 ;
    }

}
