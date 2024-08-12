import UI.Table;

public class App {
    public static void main(String[] args) throws Exception {
        //System.out.println("┬"+ "┴"+ "├"+ "┤"+ "┼" + "│" + "─");
        Table.clear();
        Table table = new Table();
        table.play();
        //table();
    }
    public static void table(){
        System.out.println(
            "   │   │ \n"+
            "───┼───┼─── \n"+
            "   │   │ \n" +
            "───┼───┼─── \n"+
            "   │   │ \n" 
        );
    }
}
