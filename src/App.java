import java.util.Scanner;

import UI.Table;

public class App {
    public static void main(String[] args) throws Exception {
        Table.clear();
        @SuppressWarnings("resource")
        Scanner scan = new Scanner(System.in);
        do {
            Table table = new Table();
            table.play();
            System.out.print("ENTER para nuevo juego..");
            scan.nextLine();
        } while (true);
    }
}
