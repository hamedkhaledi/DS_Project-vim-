public class statistics {
    public static String Text = "";
    public static int number_of_words;
    public static int number_of_terms;
    public static String[] long_words = new String[11];
    public static String[] short_words = new String[11];

    public static void init() {
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

    public static void count() {
        number_of_words = 0;
        number_of_terms = 0;
        Text = Text.replace("  ", " ");
        String str = "";
        for (int i = 0; i < Text.length(); i++) {
            if (Text.charAt(i) == '\n') {
                number_of_terms++;
                number_of_words++;
                long_words[10] = str;
                short_words[10] = str;
                str = "";
                for (int j = 10; j > 0; j--) {
                    if (long_words[j - 1] == null || long_words[j].length() > long_words[j - 1].length()) {
                        String temp = long_words[j];
                        long_words[j] = long_words[j - 1];
                        long_words[j - 1] = temp;
                    }
                }
                for (int j = 10; j > 0; j--) {
                    if (short_words[j - 1] == null || short_words[j].length() < short_words[j - 1].length()) {
                        String temp = short_words[j];
                        short_words[j] = short_words[j - 1];
                        short_words[j - 1] = temp;
                    }
                }
            } else if (Text.charAt(i) == ' ') {
                number_of_words++;
                long_words[10] = str;
                short_words[10] = str;
                str = "";
                for (int j = 10; j > 0; j--) {
                    if (long_words[j - 1] == null || long_words[j].length() > long_words[j - 1].length()) {
                        String temp = long_words[j];
                        long_words[j] = long_words[j - 1];
                        long_words[j - 1] = temp;
                    }
                }
                for (int j = 10; j > 0; j--) {
                    if (short_words[j - 1] == null || short_words[j].length() < short_words[j - 1].length()) {
                        String temp = short_words[j];
                        short_words[j] = short_words[j - 1];
                        short_words[j - 1] = temp;
                    }
                }
            } else {

                str += Text.charAt(i);
            }
        }
        number_of_terms++;
        number_of_words++;
        long_words[10] = str;
        short_words[10] = str;
        for (int j = 10; j > 0; j--) {
            if (long_words[j - 1] == null || long_words[j].length() > long_words[j - 1].length()) {
                String temp = long_words[j];
                long_words[j] = long_words[j - 1];
                long_words[j - 1] = temp;
            }
        }
        for (int j = 10; j > 0; j--) {
            if (short_words[j - 1] == null || short_words[j].length() < short_words[j - 1].length()) {
                String temp = short_words[j];
                short_words[j] = short_words[j - 1];
                short_words[j - 1] = temp;
            }
        }
        System.out.println("Number Of Words : " + number_of_words + " Number of sentences : " + number_of_terms);
        System.out.println("Long words and short words");
        for (int j = 0; j < 10; j++) {
            System.out.println(long_words[j] + "    " + short_words[j]);
        }
    }
}
