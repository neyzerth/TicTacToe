package UI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Table {
    private Player [][] rows = new Player [3][3];
    private int turn;
    private Player[] player = Player.values();
    private ArrayList<Integer> xPos = new ArrayList<>();
    private ArrayList<Integer> oPos = new ArrayList<>();
        

    public void printTable(){
        String table = String.format(
            "   1   2   3     \n"+
            "a  %s │ %s │ %s  \n"+
             "  ───┼───┼───   \n"+
            "b  %s │ %s │ %s  \n" +
             "  ───┼───┼───   \n"+
            "c  %s │ %s │ %s  \n",

            rows[0][0], rows[0][1], rows[0][2],
            rows[1][0], rows[1][1], rows[1][2],
            rows[2][0], rows[2][1], rows[2][2]
        );
        System.out.println(table.replace("null", " "));
    }
    public void play(){
        Scanner scan = new Scanner(System.in);
        Player p;
        Player winner = null;
        String warning = "";
        this.turn = 0;

        while(turn < 9) {
            if(turn >= 5) winner = searchWin();
            if(winner != null) break;

            p = this.player[turn%2];
            
            
            clear();
            System.out.println(turnMessage(p.color(), p.symbol()));
            System.out.println(warning);

            warning = "";

            printTable();
            System.out.println();

            System.out.print("["+ p.symbol() +"] > ");

            try {
                position(scan.next(), p);
                this.turn++;
            } catch (Exception e) {
                warning = e.getMessage();
            }
        }
        clear();
        System.out.println(Color.inverted(Color.colorText(winner.color(),"  WINNER " + winner + "!!!")));
        printTable();
    }

    private void position(String pos, Player p){
        if(pos.length() != 2)
            throw new IllegalArgumentException("AAAA");
        int a = pos.charAt(0) - 97;
        int b = pos.charAt(1) - 49;
        
        position(a, b, p);
    }
    private void position(int a, int b, Player p){
        
        if(a < 0 || a > 2)
            throw new IllegalArgumentException("No valido");
        if(b < 0 || b > 2)
            throw new IllegalArgumentException("No valido");
        if(this.rows[a][b] != null)
            throw new IllegalArgumentException("otra pos");

        this.rows[a][b] = p;
    }

    private String turnMessage(String color, String symbol){
        return Color.inverted(
            color +" > "+ symbol + Color.inverted(color +" TURN "+ (this.turn + 1) +" < ")
        );
    }
    public static void clear() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (IOException | InterruptedException ex) {
            System.out.println();
        }
    }

    private Player searchWin(){
        Player winner = null;

        //search for each player (X & O)
        for (Player p : this.player){
            //Search horizontally
            for (Player[] row : rows) 
                if(p == row[0] && p == row[1] && p == row[2])
                    return p;
                
            //Search vertically
            for (int i = 0; i < 3; i++) {
                if(p == rows[0][i] && p == rows[1][i] && p == rows[2][i])
                    return p;
            }

            //Search vertically
            //First, evaluate if have one in the center
            if(p == rows[1][1]){
                //search if is \
                if(p == rows[0][0] && p == rows[2][2])
                    return p;   
                //search if is /
                if(p == rows[0][2] && p == rows[2][0])
                    return p;
            }


        }
            
        

        return winner;
    }
    

}

enum Player{
    X(Color.red("X"), Color.red),
    O(Color.blue("O"), Color.blue);

    private final String symbol;
    private final String color;

    Player(String symbol, String color){
        this.symbol = symbol;
        this.color = color;
    }

    public String symbol(){
        return this.symbol;
    }
    public String color(){
        return this.color;
    }
}