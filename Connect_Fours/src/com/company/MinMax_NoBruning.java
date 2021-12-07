package com.company;

public class MinMax_NoBruning {

    public static Node Minimize(Node current_node){
        current_node.generate_children(Board.PLAYER2);

        if(current_node.check_terminal()){
            current_node.setUtility(Huristic_Function.eval_state(current_node.getState()));
            return current_node;
        }
        int min_utility=Integer.MAX_VALUE;
        Node Min_child=null;
        for(int i=0;i<current_node.getChildren().size();i++){
            Node child=current_node.getChildren().get(i);
            int utility=Maximize(child).getUtility();

            if(utility<min_utility){
                child.setUtility(utility);
                min_utility=utility;
                Min_child=child;
            }

        }
        current_node.clear_children_list();
        current_node.getChildren().add(Min_child);
        return Min_child;
    }

    public static Node Maximize(Node current_node){
        current_node.generate_children(Board.PLAYER1);
        if(current_node.check_terminal()){
            current_node.setUtility(Huristic_Function.eval_state(current_node.getState()));
            return current_node;
        }
        int max_utility=Integer.MIN_VALUE;
        Node Max_child=null;
        for(int i=0;i<current_node.getChildren().size();i++){
            Node child=current_node.getChildren().get(i);
            int utility=Minimize(child).getUtility();
            if(utility>max_utility){
                child.setUtility(utility);
                max_utility=utility;
                Max_child=child;
            }

        }
        current_node.clear_children_list();
        current_node.getChildren().add(Max_child);
        return Max_child;
    }
}
