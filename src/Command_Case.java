public class Command_Case {
    public static Courser courser_original = new Courser();

    public static void Delete_To_end_term() {
        int temp_pos = courser_original.position;
        Piece temp_piece = Vim.Current_Piece;
        Courser.increase_courser(courser_original);
        StringBuilder sb;
        while (Vim.Current_Piece.text().charAt(courser_original.position - 1) != '\n') {
            int place = Vim.Current_Piece.start + courser_original.position;
            if (Vim.Current_Piece.type.equals(Piece_Type.original)) {
                sb = new StringBuilder(Vim.original_text);
                sb.deleteCharAt(place - 1);
                Vim.original_text = sb.toString();
            }
            if (Vim.Current_Piece.type.equals(Piece_Type.add)) {
                sb = new StringBuilder(Vim.add);
                sb.deleteCharAt(place - 1);
                Vim.add = sb.toString();
            }
            Vim.Current_Piece.length--;
            Vim.Current_Piece.end--;
            if (Vim.Current_Piece.length == 0) {
                Piece temp = Vim.Current_Piece.next_piece;
                Vim.piece_table.remove(Vim.Current_Piece);
                Command_Case.courser_original.position = 1;
                Vim.Current_Piece = temp;
                if (temp == null)
                    break;
            }
            if (courser_original.position > Vim.Current_Piece.length) {
                if (Vim.Current_Piece.next_piece != null) {
                    Vim.Current_Piece = Vim.Current_Piece.next_piece;
                    courser_original.position = 1;
                } else {
                    break;
                }
            }
        }
        Vim.Current_Piece = temp_piece;
        Command_Case.courser_original.position = temp_pos;
    }

    public static void Command(String input) {
        switch (input) {
            case "D":
                Delete_To_end_term();
                break;
            case "DD":
                Courser.First_of_term(courser_original);
                Courser.decrease_courser(courser_original);
                Delete_To_end_term();
                break;

        }
        if (input.matches("/" + ".*")) {
            // create input
            StringBuilder sb;
            sb = new StringBuilder(input);
            sb.deleteCharAt(0);
            input = sb.toString();
            //
            int line = 1;
            int counter = 0;
            int temp_pos = courser_original.position;
            Piece temp_piece = Vim.Current_Piece;
            courser_original.position = 0;
            Vim.Current_Piece = Vim.piece_table.First_Piece;
            //
            Trie_Table temp = new Trie_Table();
            temp.root = new TrieNode();
            StringBuilder str = new StringBuilder();
            while (true) {
                courser_original.position++;
                if (courser_original.position > Vim.Current_Piece.length) {
                    if (Vim.Current_Piece.next_piece != null) {
                        Vim.Current_Piece = Vim.Current_Piece.next_piece;
                        courser_original.position = 1;
                    } else {
                        temp.insert(str.toString());
                        if (temp.search(input)) {
                            System.out.print(" Line : " + line + "  ");
                            counter++;
                        }
                        courser_original.position--;
                        break;
                    }
                }
                char current_char = Vim.Current_Piece.text().charAt(courser_original.position - 1);
                if (current_char != ' ' && current_char != '\n')
                    str.append(current_char);
                else {
                    temp.insert(str.toString());
                    str = new StringBuilder();
                }
                if (current_char == '\n') {
                    if (temp.search(input)) {
                        System.out.print("Line : " + line);
                        counter++;
                    }
                    line++;
                    temp = new Trie_Table();
                    temp.root = new TrieNode();
                    str = new StringBuilder();
                }
            }
            System.out.println("\nnumbers: " + counter);
            courser_original.position = temp_pos;
            Vim.Current_Piece = temp_piece;
        }
        if (input.equals("i")) {
            Vim.Current_Type = Type.insert;
        } else
            Courser.Courser_Move(input, courser_original);
    }
}
