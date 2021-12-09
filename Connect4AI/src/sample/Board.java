package sample;
public class Board {
    public static final int PLAYER1 = 1;
    public static final int PLAYER2 = 2;
    //Board size
    private static int rows_range=6;
    private static int cols_range=7;
    // Max barnching depth
    private static int branching_depth_k=4;
    // current state of board
    private static int[][]state=new int[rows_range][cols_range];
    // with bruning or without bruning
    private static boolean bruning=true;
    public static int explored_nodes=0;

    public static boolean isFull(){
        for(int i=0;i<cols_range;i++){
            if(state[rows_range-1][i]==0){
                return false;
            }
        }
        return true;
    }
    public static void with_bruning(boolean with_bruning){
        bruning=with_bruning;
    }

    public static Boolean bruning(){return bruning;}

    public static void setState(int[][] grid) {
        state = grid;
    }

    public static int[][] getState() {
        return state;
    }

    public static int getRows_range() {
        return rows_range;
    }

    public static int getCols_range() {
        return cols_range;
    }

    public static void changeBoardSize(int cols,int rows){
        rows_range=rows;
        cols_range=cols;
    }

    public static void change_Branching_depth_k(int k){
        branching_depth_k=k;
    }

    public static int getBranching_depth_k(){
        return branching_depth_k;
    }

    public static int getExploredNodes(){
        return explored_nodes;
    }

    //AI agent take decision and change state of board
    public static void take_decision(){
        Node initial_node=new Node(state,null,0);
        int oldHeuristic = Huristic_Function.eval_state(state,0);
        explored_nodes=0;
        if(bruning){
            state= MinMax.Maximize(initial_node,oldHeuristic,true,-10000,10000).getState();
        }
        else {
            state= MinMax.Maximize(initial_node,oldHeuristic,false,0,0).getState();
        }
    }

    public static void copy_array(int[][]arr1,int[][] arr2){
        for(int i=0;i<arr1.length;i++){
            for(int j=0;j<arr1[0].length;j++){
                arr1[i][j]=arr2[i][j];
            }
        }
    }


    public static int getColumnNumber(int [][]intialState){
        for (int i = 0 ; i < rows_range ; i++){
            for (int j = 0 ; j < cols_range ; j++){
                if (state[i][j] != 0 && intialState[i][j] == 0)
                    return j ;
            }
        }
        return 0 ;
    }
}
