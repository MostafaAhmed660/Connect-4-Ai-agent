package com.company;

public class MinMax_Bruning {
    public static Node Minimize(Node current_node,int[]alpha,int[]beta){
        current_node.generate_children(Board.PLAYER2);

        if(current_node.check_terminal()){
            current_node.setUtility(Huristic_Function.eval_state(current_node.getState()));
            return current_node;
        }
        int min_utility=Integer.MAX_VALUE;
        Node Min_child=null;
        System.out.print("Minimizer ");
        for(int i=0;i<current_node.getChildren().size();i++){
            Node child=current_node.getChildren().get(i);
            int utility=Maximize(child,alpha,beta).getUtility();
            System.out.print(utility+" ");
            if(utility<min_utility){
                //child.setUtility(utility);
                min_utility=utility;
                Min_child=child;
            }
            if(min_utility<=alpha[0])break;

            if(min_utility<beta[0])
                beta[0]=min_utility;
        }
        current_node.clear_children_list();
        System.out.println("\nminimizer choose :"+Min_child.getUtility());
        return Min_child;
    }

    public static Node Maximize(Node current_node,int[]alpha,int[]beta){
        current_node.generate_children(Board.PLAYER1);
        if(current_node.check_terminal()){
            current_node.setUtility(Huristic_Function.eval_state(current_node.getState()));
            return current_node;
        }
        int max_utility=Integer.MIN_VALUE;
        Node Max_child=null;
        System.out.print("Minimizer ");
        for(int i=0;i<current_node.getChildren().size();i++){
            Node child=current_node.getChildren().get(i);
            int utility=Minimize(child,alpha,beta).getUtility();
            if(utility>max_utility){
                child.setUtility(utility);
                max_utility=utility;
                Max_child=child;
            }
            if(max_utility>=beta[0])break;

            if(max_utility>alpha[0])
                alpha[0]=max_utility;
        }
        current_node.clear_children_list();
        System.out.println(Max_child.getUtility());
        System.out.println("\nmaximizer choose :"+Max_child.getUtility());
        return Max_child;
    }
}
