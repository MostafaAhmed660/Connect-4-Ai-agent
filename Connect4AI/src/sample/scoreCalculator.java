package sample;

public class scoreCalculator {

    public static int playerOneScore(int [][]state){
        int n = state.length, m = state[0].length, score = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (state[i][j] == 1){
                    score+= scoreHorizontal(state,i,j);
                    score+= scoreVertical(state,i,j);
                    score+= scoreRightDiagonal(state,i,j);
                    score+= scoreLeftDiagonal(state,i,j);
                }
            }
        }

        return score ;
    }

    public static int playerTwoScore(int [][]state){
        int n = state.length, m = state[0].length, score = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (state[i][j] == 2){
                    score+= scoreHorizontal(state,i,j);
                    score+= scoreVertical(state,i,j);
                    score+= scoreRightDiagonal(state,i,j);
                    score+= scoreLeftDiagonal(state,i,j);
                }
            }
        }
        return score ;
    }

    public static int scoreHorizontal(int [][]state,int i,int j){
        int m = state[0].length ;
        //check 4 connected
        if (j <= m-4)
            if (state[i][j] == state[i][j+1] && state[i][j+1] == state[i][j+2] && state[i][j+1] == state[i][j+3])
                return 1 ;

        return 0 ;
    }


    public static int scoreVertical(int [][]state,int i,int j){
        int n = state.length ;
        if (i <= n-4)
            //check 4 connected
            if (state[i][j] == state[i + 1][j] && state[i + 1][j] == state[i + 2][j] && state[i + 2][j] == state[i + 3][j])
                return 1;

        return 0 ;
    }


    public static int scoreRightDiagonal(int [][]state,int i,int j){
        int n = state.length , m = state[0].length;

        if (i <= n-4 && j >= 3)
            //check 4 connected
            if (state[i][j] == state[i + 1][j - 1] && state[i + 1][j - 1] == state[i + 2][j - 2] && state[i + 2][j - 2] == state[i + 3][j - 3])
                return 1;

        return 0 ;
    }

    public static int scoreLeftDiagonal(int [][]state,int i,int j){
        int n = state.length , m = state[0].length;

        if (i <= n-4 && j <= m-4)
            //check 4 connected
            if (state[i][j] == state[i + 1][j + 1] && state[i + 1][j + 1] == state[i + 2][j + 2] && state[i + 2][j + 2] == state[i + 3][j + 3])
                return 1;

        return 0 ;
    }

}
