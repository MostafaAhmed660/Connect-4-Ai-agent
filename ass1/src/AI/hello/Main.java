package AI.hello;

import java.util.ArrayList;
import java.util.Scanner;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        int [][]state=new int[][]{
                {0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0}
        };




        Board k =new Board();
        Board.with_bruning(false);
        Scanner sc=new Scanner(System.in);
        while (!Board.isFull()){
            System.out.println("Enter column you want : ");
            int col=sc.nextInt();
            Board.human_take_decision(col);
            print_array(Board.getState());
            System.out.println("-------------------------------------2");
            Board.take_decision();
            print_array(Board.getState());
        }
        System.out.println();


    }

    public static void print_array(int[][]array){
        for(int i=0;i<array.length;i++){
            for(int j=0;j<array[0].length;j++){
                System.out.print(array[i][j]+" ");
            }
            System.out.println();
        }
    }
}
