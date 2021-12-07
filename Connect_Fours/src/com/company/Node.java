package com.company;

import java.util.ArrayList;

public class Node {
    private int[][]State=new int[Board.getRows_range()][Board.getCols_range()];
    private Node Parent;
    private ArrayList<Node>Children=new ArrayList<>();
    private int utility;
    int level_k;
    Node(int[][]State, Node parent, int level_k){
        this.State=State;
        this.Parent=parent;
        this.level_k=level_k;
    }
    public void setState(int[][] state) {
        State = state;
    }

    public void setParent(Node parent) {
        Parent = parent;
    }

    public void setUtility(int utility) {
        this.utility = utility;
    }

    public int[][] getState() {
        return State;
    }

    public Node getParent() {
        return Parent;
    }

    public void clear_children_list(){
        this.Children.clear();
    }
    public ArrayList<Node> getChildren() {
        return Children;
    }

    public int getUtility() {
        return utility;
    }

    public int getLevel_k() {
        return level_k;
    }

    // generate childrens for the current node depending on it's state
    public void generate_children(int player_number){
       for(int i=0;i<Board.getCols_range();i++){
           for(int j=0;j<Board.getRows_range();j++){
               if(this.getState()[j][i]==0){
                       int[][]new_state=new int[Board.getRows_range()][Board.getCols_range()];
                       Board.copy_array(new_state,this.getState());
                       new_state[j][i]=player_number;
                       Node new_Node=new Node(new_state,this,this.getLevel_k()+1);
                       this.getChildren().add(new_Node);
                       break;
               }
           }
       }
    }
    public boolean check_terminal(){
        return this.level_k==Board.getBranching_depth_k() ;
    }

}
