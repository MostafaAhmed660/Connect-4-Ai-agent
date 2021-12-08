package AI.hello;

public class MinMax {


    public static Tuple Minimize(Node node,int oldHeuristic,boolean isPruning,int[] alphaBeta){
        if (node.check_terminal()) {
            Tuple ans = new Tuple(null,Huristic_Function.eval_state(node.getState(),oldHeuristic));
            return ans;
        }

        int[][] minChild = null;
        int minUtility = 10000;

        node.generate_children(Board.PLAYER2);

        for (Node child : node.getChildren()){
            int utility = Maximize(child,oldHeuristic,isPruning,alphaBeta).getUtility();

            if (utility < minUtility){
                minChild = child.getState() ;
                minUtility = utility ;
            }
            if (isPruning){
                if (minUtility <= alphaBeta[0])
                    break;
                if (minUtility < alphaBeta[1])
                    alphaBeta[1] = minUtility ;
            }
        }

        node.setUtility(minUtility);

        return new Tuple(minChild,minUtility);

    }

    public static Tuple Maximize(Node node,int oldHeuristic,boolean isPruning,int[] alphaBeta){
        if (node.check_terminal()) {
            Tuple ans = new Tuple(null,Huristic_Function.eval_state(node.getState(),oldHeuristic));
            return ans;
        }

        int[][] maxChild = null;
        int maxUtility = -10000;

        node.generate_children(Board.PLAYER1);

        for (Node child : node.getChildren()){
            int utility = Minimize(child,oldHeuristic,isPruning,alphaBeta).getUtility();

            if (utility > maxUtility){
                maxChild = child.getState() ;
                maxUtility = utility ;
            }

            if (isPruning){
                if (maxUtility >= alphaBeta[1])
                    break;
                if (maxUtility > alphaBeta[0])
                    alphaBeta[0] = maxUtility ;
            }
        }

        node.setUtility(maxUtility);

        return new Tuple(maxChild,maxUtility);
    }
}
