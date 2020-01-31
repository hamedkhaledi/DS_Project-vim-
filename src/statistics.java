class statistics {
    private static String Text = "";
    private static String[] long_words = new String[11];
    private static String[] short_words = new String[11];

    static void init() {
        int temp_pos = Command_Case.courser_original.position;
        Piece temp_piece = Vim.Current_Piece;
        Vim.Current_Piece = Vim.piece_table.First_Piece;
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
                Text += current_char;
            }
            System.out.println();
            Command_Case.courser_original.position = temp_pos;
            Vim.Current_Piece = temp_piece;
            count();
        }
    }

    private static void count() {
        int number_of_words = 0;
        int number_of_terms = 0;
        Text = Text.replace("  ", " ");
        String str = "";
        boolean is_exist_on_shorts;
        boolean is_exist_on_longs;

        for (int i = 0; i < Text.length(); i++) {
            if (Text.charAt(i) == '\n' && (i == 0 || Text.charAt(i - 1) != ' ')) {
                number_of_terms++;
                number_of_words++;
                long_words[10] = str;
                short_words[10] = str;
                is_exist_on_longs = Is_exist_on(str, long_words);
                is_exist_on_shorts = Is_exist_on(str, short_words);
                str = "";
                if (!is_exist_on_longs) {
                    Select_index(long_words);
                }
                if (!is_exist_on_shorts) {
                    Select_index(short_words);
                }
            } else if (Text.charAt(i) == ' ' && (i == 0 || Text.charAt(i - 1) != '\n')) {
                number_of_words++;
                long_words[10] = str;
                short_words[10] = str;
                is_exist_on_longs = Is_exist_on(str, long_words);
                is_exist_on_shorts = Is_exist_on(str, short_words);
                str = "";
                if (!is_exist_on_longs) {
                    Select_index(long_words);
                }
                if (!is_exist_on_shorts) {
                    Select_index(short_words);
                }
            } else if (Text.charAt(i) != '\n' && Text.charAt(i) != ' ')
                str += Text.charAt(i);

        }
        number_of_terms++;
        number_of_words++;
        long_words[10] = str;
        short_words[10] = str;
        System.out.println("Number Of Words : " + number_of_words + " Number of sentences : " + number_of_terms);
        System.out.println("Long words : ");
        for (int j = 0; j < 10; j++) {
            if (long_words[j] != null)
                System.out.print(long_words[j] + "  ");

        }
        System.out.println();
        System.out.println("Short words : ");
        for (int j = 0; j < 10; j++) {
            if (long_words[j] != null)
                System.out.print(short_words[j] + "  ");

        }
        System.out.println();
    }

    private static boolean Is_exist_on(String str, String[] words) {
        boolean is_exist_on;
        is_exist_on = false;
        for (int j = 9; j >= 0; j--) {
            if (str.equalsIgnoreCase(words[j])) {
                is_exist_on = true;
                break;
            }
        }
        return is_exist_on;
    }

    private static void Select_index(String[] words) {
        for (int j = 10; j > 0; j--) {
            if (words[j - 1] == null || words[j].length() > words[j - 1].length()) {
                String temp = words[j];
                words[j] = words[j - 1];
                words[j - 1] = temp;
            }
        }

    }
}
