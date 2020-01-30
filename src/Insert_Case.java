public class Insert_Case {

    public static Piece Original1, Original2;

    public static void Insert(String input) {
        if (input.equals("!")) {
            Vim.Current_Type = Type.command;
        } else {
            if (input.equals(">") || input.equals("<") || input.equals("2") || input.equals("8"))
                Courser.Courser_Move(input, Command_Case.courser_original);
            else {
                if (Command_Case.courser_original.position > 0) {
                    Original1 = new Piece(Vim.Current_Piece.start, Vim.Current_Piece.start + Command_Case.courser_original.position, Vim.Current_Piece.type);
                    Vim.piece_table.add(Original1, Vim.Current_Piece.previous_piece);
                } else
                    Original1 = null;
                if (Vim.Current_Piece.end > Vim.Current_Piece.start + Command_Case.courser_original.position) {
                    Original2 = new Piece(Vim.Current_Piece.start + Command_Case.courser_original.position, Vim.Current_Piece.end, Vim.Current_Piece.type);
                    Vim.piece_table.add(Original2, Vim.Current_Piece);
                } else {
                    Original1.next_piece = Vim.Current_Piece.next_piece;
                    if (Original1.next_piece != null)
                        Original1.next_piece.previous_piece = Original1;
                }
                Vim.piece_table.remove(Vim.Current_Piece);
                System.out.println(Vim.piece_table.First_Piece.text());
                input = input.replace("\\n", "\n");
                input = input.toLowerCase();
                Vim.add_text += input;
                Piece piece = new Piece(Vim.add_text.length() - input.length(), Vim.add_text.length(), Piece_Type.add);
                Vim.Current_Piece = piece;
                Command_Case.courser_original.position = piece.length;
                Vim.piece_table.add(piece, Original1);
            }
        }
    }
}
