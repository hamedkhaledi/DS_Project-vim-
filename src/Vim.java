import java.util.Scanner;

public class Vim {
    static Type Current_Type = Type.command;
    static String original_text = "hhi";
    static String add_text = "";
    static Piece_Table piece_table;
    static Piece Current_Piece;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Current_Piece = new Piece(0, original_text.length(), Piece_Type.original);
        piece_table = new Piece_Table();
        piece_table.add(Current_Piece, null);
        while (true) {
            String input = scanner.nextLine();
            switch (Current_Type) {
                case command:
                    Command_Case.Command(input);
                    break;
                case insert:
                    Insert_Case.Insert(input);
                    break;
                case statistics:
                    break;
            }
            Print();
        }
    }

    private static void Print() {
        System.out.println("Text : ");
        int temp_pos = Command_Case.courser_original.position;
        Piece temp_piece = Vim.Current_Piece;
        Current_Piece = piece_table.First_Piece;
        Command_Case.courser_original.position = 0;
        if (Vim.Current_Piece != null) {
            while (true) {
                Command_Case.courser_original.position++;
                if (Command_Case.courser_original.position > Vim.Current_Piece.length) {
                    if (Vim.Current_Piece.next_piece != null) {
                        Vim.Current_Piece = Vim.Current_Piece.next_piece;
                        Command_Case.courser_original.position = 1;
                    } else {
                        Command_Case.courser_original.position--;
                        break;
                    }
                }
                char current_char = Vim.Current_Piece.text().charAt(Command_Case.courser_original.position - 1);
                System.out.print(current_char);
                if (Command_Case.courser_original.position == temp_pos && Current_Piece == temp_piece)
                    System.out.print("*");
            }
            System.out.println();
            Command_Case.courser_original.position = temp_pos;
            Current_Piece = temp_piece;
        }
    }

}