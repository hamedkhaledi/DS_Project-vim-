class Insert_Case {

    static void Insert(String input) {
        if (input.equals("!")) {
            Vim.Current_Type = Type.command;
        } else {
            if (input.equals(">") || input.equals("<") || input.equals("2") || input.equals("8"))
                Courser.Courser_Move(input, Command_Case.courser_original);
            else if (input.length() > 0) {
                Piece original1;
                if (Command_Case.courser_original.position > 0) {
                    original1 = new Piece(Vim.Current_Piece.start, Vim.Current_Piece.start + Command_Case.courser_original.position, Vim.Current_Piece.type);
                    Vim.piece_table.add(original1, Vim.Current_Piece.previous_piece);
                } else
                    original1 = null;
                if (Vim.Current_Piece.end > Vim.Current_Piece.start + Command_Case.courser_original.position) {
                    Piece original2 = new Piece(Vim.Current_Piece.start + Command_Case.courser_original.position, Vim.Current_Piece.end, Vim.Current_Piece.type);
                    Vim.piece_table.add(original2, Vim.Current_Piece);
                } else {
                    if (original1 != null) {
                        original1.next_piece = Vim.Current_Piece.next_piece;
                        if (original1.next_piece != null)
                            original1.next_piece.previous_piece = original1;
                    }
                }

                Vim.piece_table.remove(Vim.Current_Piece);
                System.out.println(Vim.piece_table.First_Piece.text());
                input = input.replace("\\n", "\n");
                input = input.toLowerCase();
                Vim.add_text += input;
                Piece piece = new Piece(Vim.add_text.length() - input.length(), Vim.add_text.length(), Piece_Type.add);
                Vim.Current_Piece = piece;
                Command_Case.courser_original.position = piece.length;
                Vim.piece_table.add(piece, original1);
            }
        }
    }
}
