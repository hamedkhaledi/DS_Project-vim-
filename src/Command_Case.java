class Command_Case {
    static Courser courser_original = new Courser();
    private static String Copy_Text = "";
    private static int Add_remove_start = -10;
    private static int Original_remove_start = -10;
    private static int Add_remove_length;
    private static int Original_remove_length;

    private static void Copy() {
        Copy_Text = "";
        int temp_pos = courser_original.position;
        Piece temp_piece = Vim.Current_Piece;
        Courser.First_of_term(courser_original);
        Courser.increase_courser(courser_original);
        if (courser_original.position == 0) {
            Courser.increase_courser(courser_original);
        }
        char current_char = Vim.Current_Piece.text().charAt(courser_original.position - 1);
        while (current_char != '\n') {
            Copy_Text += Character.toString(current_char);
            courser_original.position++;
            if (courser_original.position > Vim.Current_Piece.length) {
                if (Vim.Current_Piece.next_piece != null) {
                    Vim.Current_Piece = Vim.Current_Piece.next_piece;
                    courser_original.position = 1;
                } else {
                    courser_original.position--;
                    break;
                }
            }
            current_char = Vim.Current_Piece.text().charAt(courser_original.position - 1);
        }
        Vim.Current_Piece = temp_piece;
        courser_original.position = temp_pos;
        System.out.println(courser_original.position);
    }

    static void Command(String input) {
        switch (input) {
            case "D":
                Delete_To_end_term();
                break;
            case "DD":
                Courser.First_of_term(courser_original);
                Delete_To_end_term();
                break;
            case "Y":
                Copy();
                break;
            case "YY":
                Copy();
                break;
            case "P":
                Insert_Case.Insert(Copy_Text);
                break;
            case "V":
                statistics.init();
                break;

        }
        if (input.matches("/" + ".*")) {
            input = Search(input);
        }
        if (input.equals("i")) {
            Vim.Current_Type = Type.insert;
        } else
            Courser.Courser_Move(input, courser_original);
    }

    private static String Search(String input) {
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
        return input;
    }


    private static void reduce_piece_start() {
        if (Vim.Current_Piece.type.equals(Piece_Type.original)) {
            if (Original_remove_start != -10 && Vim.Current_Piece.start > Original_remove_start) {
                Vim.Current_Piece.start -= Original_remove_length;
                Vim.Current_Piece.end -= Original_remove_length;
            }
        }
        if (Vim.Current_Piece.type.equals(Piece_Type.add)) {
            if (Add_remove_start != -10 && Vim.Current_Piece.start > Add_remove_start) {
                Vim.Current_Piece.start -= Add_remove_length;
                Vim.Current_Piece.end -= Add_remove_length;
            }
        }
    }

    private static void Delete_To_end_term() {
        Add_remove_start = -10;
        Original_remove_start = -10;
        Add_remove_length = 0;
        Original_remove_length = 0;
        Piece temp_piece2 = Vim.Current_Piece.next_piece;
        if (courser_original.position == Vim.Current_Piece.length)
            if (temp_piece2 == null)
                return;
        int temp_pos = courser_original.position;
        Piece temp_piece = Vim.Current_Piece;
        Courser.increase_courser(courser_original);
        StringBuilder sb;
        while (Vim.Current_Piece.text().charAt(courser_original.position - 1) != '\n') {
            int place = Vim.Current_Piece.start + courser_original.position;
            if (Vim.Current_Piece.type.equals(Piece_Type.original)) {
                sb = new StringBuilder(Vim.original_text);
                if (Original_remove_start == -10)
                    Original_remove_start = place - 1;
                Original_remove_length++;
                sb.deleteCharAt(place - 1);
                Vim.original_text = sb.toString();
            }
            if (Vim.Current_Piece.type.equals(Piece_Type.add)) {
                sb = new StringBuilder(Vim.add_text);
                if (Add_remove_start == -10)
                    Add_remove_start = place - 1;
                Add_remove_length++;
                sb.deleteCharAt(place - 1);
                Vim.add_text = sb.toString();
            }
            Vim.Current_Piece.length--;
            Vim.Current_Piece.end--;
            temp_piece2 = Vim.Current_Piece.next_piece;
            if (Vim.Current_Piece.length == 0) {
                Piece temp = Vim.Current_Piece.next_piece;
                Vim.piece_table.remove(Vim.Current_Piece);
                Command_Case.courser_original.position = 1;
                Vim.Current_Piece = temp;
                if (temp == null)
                    break;
                reduce_piece_start();
            }
            if (courser_original.position > Vim.Current_Piece.length) {
                if (temp_piece2 != null) {
                    Vim.Current_Piece = temp_piece2;
                    courser_original.position = 1;
                    reduce_piece_start();
                } else {
                    break;
                }
            }
        }
        if (Vim.Current_Piece != null) {
            if (Vim.Current_Piece.length == 0) {
                Piece temp = Vim.Current_Piece.next_piece;
                Vim.piece_table.remove(Vim.Current_Piece);
                Command_Case.courser_original.position = 1;
                Vim.Current_Piece = temp;
                reduce_piece_start();
            }
        }
        Vim.Current_Piece = Vim.piece_table.First_Piece;
        while (Vim.Current_Piece != null) {
            if (Vim.Current_Piece.next_piece != null) {
                reduce_piece_start();
                Vim.Current_Piece = Vim.Current_Piece.next_piece;
            } else {
                reduce_piece_start();
                break;
            }
        }

        Vim.Current_Piece = temp_piece;
        Command_Case.courser_original.position = temp_pos;
    }
}