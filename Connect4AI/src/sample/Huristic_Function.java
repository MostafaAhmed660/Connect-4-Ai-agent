package sample;
public class Huristic_Function {

    public static int eval_state(int [][]state,int oldHeuristic) {
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

        heuristic = heuristic - oldHeuristic ;
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

            int numberOfThrees = 0 ;
            //check 3 connected
            if (state[i][j] == state[i + 1][j - 1] && state[i + 1][j - 1] == state[i + 2][j - 2]){
                if (state[i+3][j-3] == 0 && state[i+2][j-3] != 0)
                    numberOfThrees++;

                if (i-1 >= 0 && j+1 < m && state[i-1][j+1] == 0 && (i-1 == 0 || (i-2 >= 0 && state[i-2][j+1] != 0)))
                    numberOfThrees++;

                return numberOfThrees ;
            }
        }
        return 0 ;
    }

    public static int checkLeftDiagonal(int [][]state,int i,int j) {
        int n = state.length , m = state[0].length;

        if (i <= n-4 && j <= m-4) {
            //check 4 connected
            if (state[i][j] == state[i + 1][j + 1] && state[i + 1][j + 1] == state[i + 2][j + 2] && state[i + 2][j + 2] == state[i + 3][j + 3])
                return 5;

            int numberOfThrees = 0 ;
            //check 3 connected
            if (state[i][j] == state[i + 1][j + 1] && state[i + 1][j + 1] == state[i + 2][j + 2]){
                if (state[i + 3][j + 3] == 0 && state[i + 2][j + 3] != 0)
                    numberOfThrees++;

                if (i-1 >= 0 && j-1 >= 0 && state[i-1][j-1] == 0 && (i-1 == 0 || (i-2 >= 0 && state[i-2][j-1] != 0)))
                    numberOfThrees++;

                return numberOfThrees ;
            }
        }

        return 0 ;
    }

    public static int huristic2(int[][]state,int coco){
        int score=0;                //total score for that state
        //iterate columns
        for(int k=1;k<=2;k++) {
            int player = k;        //player that I compute score in this iteration
            int competitor = Math.abs(3 - k); // tha competitor player
            int player_score = 0;     //current player score
            int counter = 0;      //counts the number of player cells
            boolean lock = true; //indicator for the previous cell(belongs to whom)
            boolean empty_indicator = false; //indicator if the prev cell wasn't belongs to any one
            //check columns
            for (int i = 0; i < state[0].length; i++) {
                for (int j = 0; j < state.length; j++) {
                    //if the current cell belongs to current player
                    if (state[j][i] == player) {
                        counter++;
                        //if we are in the last cell
                        if (j == state.length && counter >= 4) {
                            player_score += (counter * counter);
                        }
                    }
                    //if the current cell belongs to competitor player
                    else if (state[j][i] == competitor) {
                        if (counter >= 4) {
                            player_score += (counter * counter);
                        }
                        counter = 0;// reset counter
                    }
                    //if it doesn't belong to any player
                    else {
                        player_score += (counter * counter);
                        counter = 0;

                    }
                }
                counter = 0;
            }

            // check rows
            for (int i = 0; i < state.length; i++) {
                for (int j = 0; j < state[0].length; j++) {
                    // current cell belongs to current player
                    if (state[i][j] == player) {
                        // exception for the first cell in each row
                        if (j != 0) {
                            if (state[i][j - 1] == competitor) {
                                lock = false;
                            }
                        }
                        counter++;
                        // we are in the last cell
                        if (j == state[0].length && (counter >= 4)) {
                            player_score += (counter * counter);
                        }
                    }
                    // current cell belongs to competitor
                    else if (state[i][j] == competitor) {
                        //number of adjacent cells >= 4 we will compute that
                        if (counter >= 4) {
                            player_score += (counter * counter);
                        }
                        // number of adjacent cells <4 then it will be considered if lock is true
                        else if (lock) {
                            player_score += (counter * counter);
                        }
                        // reset lock to true again
                        lock = true;
                        // reset counter to 0 (begin to count another adjacent cells)
                        counter = 0;
                    }
                    //current cell doesn't belong to any player
                    else {
                        player_score += (counter * counter);
                        counter = 0;
                    }
                }
                counter = 0;
            }
            // check diagonal \\
            for (int i = 0; i < (state.length - 3)+(state[0].length-3); i++) {
                int row_counter;
                int column_counter;
                if(i<state.length-3){
                    row_counter = i;
                    column_counter = state[0].length-1;
                }
                else {
                    row_counter=0;
                    column_counter=(state[0].length-1)-(i-(state.length-3));
                }
                while ((column_counter >= 0 )&& (row_counter < state.length)) {
                    // current cell belongs to current player
                    if (state[row_counter][column_counter] == player) {
                        // exception for the first cell in each row
                        if ((column_counter != Board.getCols_range() - 1)&&(row_counter>0)) {
                            //previous cell belongs to competitor
                            if (state[row_counter - 1][column_counter + 1] == competitor) {
                                lock = false;
                            }
                        }
                        counter++;
                        // we are in the last cell
                        if ((row_counter == state.length - 1) && (counter >= 4)) {
                            player_score += (counter * counter);
                        }
                    }
                    // current cell belongs to competitor
                    else if (state[row_counter][column_counter] == competitor) {
                        //number of adjacent cells >= 4 we will compute that
                        if (counter >= 4) {
                            player_score += (counter * counter);
                        }
                        // number of adjacent cells <4 then it will be considered if lock is true
                        else if (lock) {
                            player_score += (counter * counter);
                        }
                        // reset lock to true again
                        lock = true;
                        // reset counter to 0 (begin to count another adjacent cells)
                        counter = 0;
                    }
                    //current cell doesn't belong to any player
                    else {
                        player_score += (counter * counter);
                        counter = 0;
                    }
                    row_counter+=1;
                    column_counter-=1;
                }
            }
            // check diagonal //
            for (int i = 0; i < (state.length - 3)+(state[0].length-3); i++) {
                int row_counter;
                int column_counter;
                if(i<state.length-3){
                    row_counter = i;
                    column_counter = 0;
                }
                else {
                    row_counter=0;
                    column_counter=(i-(state.length-3));
                }
                while ((column_counter <state[0].length )&& (row_counter<state.length)) {
                    // current cell belongs to current player
                    if (state[row_counter][column_counter] == player) {
                        // exception for the first cell in each row
                        if ((column_counter !=0)&&(row_counter!=0)) {
                            //previous cell belongs to competitor
                            if (state[row_counter-1][column_counter - 1] == competitor) {
                                lock = false;
                            }
                        }
                        counter++;
                        // we are in the last cell
                        if ((row_counter == state.length - 1) && (counter >= 4)) {
                            player_score += (counter * counter);
                        }
                    }
                    // current cell belongs to competitor
                    else if (state[row_counter][column_counter] == competitor) {
                        //number of adjacent cells >= 4 we will compute that
                        if (counter >= 4) {
                            player_score += (counter * counter);
                        }
                        // number of adjacent cells <4 then it will be considered if lock is true
                        else if (lock) {
                            player_score += (counter * counter);
                        }
                        // reset lock to true again
                        lock = true;
                        // reset counter to 0 (begin to count another adjacent cells)
                        counter = 0;
                    }
                    //current cell doesn't belong to any player
                    else {
                        player_score += (counter * counter);
                        counter = 0;
                    }
                    row_counter+=1;
                    column_counter+=1;
                }
            }
            // AI agent
            if(player==1){
                score+=player_score;
            }
            else {
                score-=player_score;
            }
            player_score=0;
        }
        return score;
    }



}