package UI;

import java.io.IOException;
import java.util.Scanner;

public class Table {
    private Player [][] rows = new Player [3][3];
    private Player[] player = Player.values();
    private int turn;
    private int [] winLine = new int[3];
    private boolean endedGame = false;

    public void printTable(){
        int i = 0;
        String [] draw = new String[9];
        for (Player[] row : rows) {
            for (Player p : row) {
                draw[i] = p == null ? " " : p.symbol();
                if(this.winLine != null && endedGame)
                    for (int win : this.winLine) {
                        draw[i] = win == i ? Color.inverted(draw[i]) : draw[i];
                    }

                i++;
            }
        }

        String table = String.format(
            "   1   2   3     \n"+
            "a  %s │ %s │ %s  \n"+
             "  ───┼───┼───   \n"+
            "b  %s │ %s │ %s  \n" +
             "  ───┼───┼───   \n"+
            "c  %s │ %s │ %s  \n",

            (Object[]) draw
        );

        System.out.println(table);
    }
    @SuppressWarnings("resource")
    public void play(){
        Scanner scan = new Scanner(System.in);
        Player p;
        Player winner = null;
        String warning = "";
        this.turn = 0;

        while(turn < 9) {
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
            
            if(turn >= 5) winner = searchWin();
            if(winner != null) break;
        }
        clear();
        if(winner != null){
            this.endedGame = true;
            System.out.println(Color.inverted(Color.colorText(winner.color(),"  WINNER " + winner + "!!!")));
        }
        else
            System.out.println(Color.inverted("-  DRAW  -"));

        printTable();
    }

    private void position(String pos, Player p){
        if(pos.length() != 2)
            throw new IllegalArgumentException("Invalid position format: " + pos);
        int a = pos.charAt(0) - 97;
        int b = pos.charAt(1) - 49;
        
        position(a, b, p);
    }
    private void position(int a, int b, Player p){
        
        if(a < 0 || a > 2)
            throw new IllegalArgumentException("Invalid Row");
        if(b < 0 || b > 2)
            throw new IllegalArgumentException("Invalid column");
        if(this.rows[a][b] != null)
            throw new IllegalArgumentException("This position isn't blank!");

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
        int j = 0;

        //search for each player (X & O)
        for (Player p : this.player){
            j = 0;
            //Search horizontally
            for (Player[] row : rows) {
                if(p == row[0] && p == row[1] && p == row[2]){
                    this.winLine[0] = j;
                    this.winLine[1] = j+1;
                    this.winLine[2] = j+2;
                    return p;
                }
                j+=3;
            }
                
            //Search vertically
            for (int i = 0; i < 3; i++) {
                if(p == rows[0][i] && p == rows[1][i] && p == rows[2][i]){
                    this.winLine[0] = i;
                    this.winLine[1] = i+3;
                    this.winLine[2] = i+6;
                    return p;
                }
            }

            //Search diagonally
            //First, evaluate if have one in the center
            if(p == rows[1][1]){
                this.winLine[1] = 4;
                //search if is \
                if(p == rows[0][0] && p == rows[2][2]){
                    this.winLine[0] = 0;
                    this.winLine[2] = 8;
                    return p;   
                }
                //search if is /
                if(p == rows[0][2] && p == rows[2][0]){
                    this.winLine[0] = 2;
                    this.winLine[2] = 8;
                    return p;
                }
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