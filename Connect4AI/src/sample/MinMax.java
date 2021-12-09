package sample;

public class MinMax {


    public static Tuple Minimize(Node node,int oldHeuristic,boolean isPruning,int alpha,int beta){
        if (node.check_terminal()) {
            Tuple ans = new Tuple(null,Huristic_Function.eval_state(node.getState(),oldHeuristic));
            return ans;
        }

        int[][] minChild = null;
        int minUtility = 10000;

        node.generate_children(Board.PLAYER2);

        for (Node child : node.getChildren()){
            int utility = Maximize(child,oldHeuristic,isPruning,alpha,beta).getUtility();
            Board.explored_nodes++;
            child.setUtility(utility);
            if (utility < minUtility){
                minChild = child.getState() ;
                minUtility = utility ;
            }
            if (isPruning){
                if (minUtility <= alpha)
                    break;
                if (minUtility < beta)
                    beta = minUtility ;
            }
        }

        if (Board.getBranching_depth_k() > 3)
            node.clear_children_list();

        node.setUtility(minUtility);

        return new Tuple(minChild,minUtility);

    }

    public static Tuple Maximize(Node node,int oldHeuristic,boolean isPruning,int alpha,int beta){
        if (node.check_terminal()) {
            Tuple ans = new Tuple(null,Huristic_Function.eval_state(node.getState(),oldHeuristic));
            return ans;
        }

        int[][] maxChild = null;
        int maxUtility = -10000;

        node.generate_children(Board.PLAYER1);

        for (Node child : node.getChildren()){
            int utility = Minimize(child,oldHeuristic,isPruning,alpha,beta).getUtility();
            child.setUtility(utility);
            Board.explored_nodes++;
            if (utility > maxUtility){
                maxChild = child.getState() ;
                maxUtility = utility ;
            }

            if (isPruning){
                if (maxUtility >= beta)
                    break;
                if (maxUtility > alpha)
                    alpha = maxUtility ;
            }
        }

        if (Board.getBranching_depth_k() > 3)
            node.clear_children_list();

        node.setUtility(maxUtility);

        return new Tuple(maxChild,maxUtility);
    }
}