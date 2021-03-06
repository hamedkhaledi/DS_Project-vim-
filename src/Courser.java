class Courser {
    int position;

    static void increase_courser(Courser courser) {
        courser.position++;
        if (courser.position > Vim.Current_Piece.length) {
            if (Vim.Current_Piece.next_piece != null) {
                Vim.Current_Piece = Vim.Current_Piece.next_piece;
                courser.position = 1;
            } else
                courser.position--;
        }
    }

    private static void decrease_courser(Courser courser) {
        courser.position--;
        if (courser.position <= 0) {
            if (Vim.Current_Piece.previous_piece != null) {
                Vim.Current_Piece = Vim.Current_Piece.previous_piece;
                courser.position = Vim.Current_Piece.length;
            } else
                courser.position = 0;
        }
    }

    static void First_of_term(Courser courser) {
        while (courser.position != 0 && Vim.Current_Piece.text().charAt(courser.position - 1) != '\n') {
            courser.position--;
            if (courser.position <= 0) {
                if (Vim.Current_Piece.previous_piece != null) {
                    Vim.Current_Piece = Vim.Current_Piece.previous_piece;
                    courser.position = Vim.Current_Piece.length;
                } else {
                    courser.position = 0;
                    break;
                }
            }
        }

    }

    private static void End_of_term(Courser courser) {
        if (courser.position <= 0)
            increase_courser(courser);
        if (Vim.Current_Piece.text().charAt(courser.position - 1) == '\n')
            increase_courser(courser);
        while (Vim.Current_Piece.text().charAt(courser.position - 1) != '\n') {
            courser.position++;
            if (courser.position > Vim.Current_Piece.length) {
                if (Vim.Current_Piece.next_piece != null) {
                    Vim.Current_Piece = Vim.Current_Piece.next_piece;
                    courser.position = 1;
                } else {
                    courser.position--;
                    break;
                }
            }
        }
    }

    private static void First_of_file(Courser courser) {
        courser.position = 0;
        Vim.Current_Piece = Vim.piece_table.First_Piece;
    }

    private static void End_of_file(Courser courser) {
        Vim.Current_Piece = Vim.piece_table.Last_Piece;
        courser.position = Vim.Current_Piece.length;

    }

    private static void First_of_word(Courser courser) {
        while (courser.position > 0 && (Vim.Current_Piece.text().charAt(courser.position - 1) == ' ' || Vim.Current_Piece.text().charAt(courser.position - 1) == '\n'))
            decrease_courser(courser);
        while (courser.position != 0 && (Vim.Current_Piece.text().charAt(courser.position - 1) != ' ' && Vim.Current_Piece.text().charAt(courser.position - 1) != '\n')) {
            courser.position--;
            if (courser.position <= 0) {
                if (Vim.Current_Piece.previous_piece != null) {
                    Vim.Current_Piece = Vim.Current_Piece.previous_piece;
                    courser.position = Vim.Current_Piece.length;
                } else {
                    courser.position = 0;
                    break;
                }
            }
        }
    }

    private static void End_of_word(Courser courser) {
        if (courser.position == 0)
            increase_courser(courser);
        while (courser.position > 0 && (Vim.Current_Piece.text().charAt(courser.position - 1) == ' ' || Vim.Current_Piece.text().charAt(courser.position - 1) == '\n'))
            increase_courser(courser);
        while (Vim.Current_Piece.text().charAt(courser.position - 1) != ' ' && Vim.Current_Piece.text().charAt(courser.position - 1) != '\n') {
            courser.position++;
            if (courser.position > Vim.Current_Piece.length) {
                if (Vim.Current_Piece.next_piece != null) {
                    Vim.Current_Piece = Vim.Current_Piece.next_piece;
                    courser.position = 1;
                } else {
                    courser.position--;
                    break;
                }
            }
        }
    }

    private static void Down(Courser courser) {
        int counter = 0;
        while (courser.position != 0 && Vim.Current_Piece.text().charAt(courser.position - 1) != '\n') {
            counter++;
            courser.position--;
            if (courser.position <= 0) {
                if (Vim.Current_Piece.previous_piece != null) {
                    Vim.Current_Piece = Vim.Current_Piece.previous_piece;
                    courser.position = Vim.Current_Piece.length;
                } else {
                    courser.position = 0;
                    break;
                }
            }
        }
        increase_courser(courser);
        End_of_term(courser);
        while (counter > 0) {
            counter--;
            increase_courser(courser);
            if (Vim.Current_Piece.text().charAt(courser.position - 1) == '\n')
                break;

        }
    }


    private static void Up(Courser courser) {
        int counter = 0;
        while (courser.position != 0 && Vim.Current_Piece.text().charAt(courser.position - 1) != '\n') {
            counter++;
            courser.position--;
            if (courser.position <= 0) {
                if (Vim.Current_Piece.previous_piece != null) {
                    Vim.Current_Piece = Vim.Current_Piece.previous_piece;
                    courser.position = Vim.Current_Piece.length;
                } else {
                    courser.position = 0;
                    break;
                }
            }
        }
        decrease_courser(courser);
        First_of_term(courser);
        while (counter > 0) {
            counter--;
            increase_courser(courser);
            if (Vim.Current_Piece.text().charAt(courser.position - 1) == '\n') {
                decrease_courser(courser);
                break;
            }

        }
    }

    static void Courser_Move(String input, Courser courser) {
        switch (input) {
            case ">":
                increase_courser(courser);
                break;
            case "<":
                decrease_courser(courser);
                break;
            case "0":
                First_of_term(courser);
                break;
            case "$":
                End_of_term(courser);
                break;
            case ":0":
                First_of_file(courser);
                break;
            case ":$":
                End_of_file(courser);
                break;
            case "w":
                End_of_word(courser);
                break;
            case "b":
                First_of_word(courser);
                First_of_word(courser);
                break;
            case "8":
                Up(courser);
                break;
            case "2":
                Down(courser);
                break;
            default:
                if (input.matches(":" + "[0-9]+")) {
                    StringBuilder sb;
                    sb = new StringBuilder(input);
                    sb.deleteCharAt(0);
                    int input_num = Integer.parseInt(sb.toString());
                    courser.position = 0;
                    Vim.Current_Piece = Vim.piece_table.First_Piece;
                    increase_courser(courser);
                    for (int i = 0; i < input_num - 1; i++) {
                        End_of_term(courser);
                    }

                }
                if (input.matches("[0-9]+" + "w")) {
                    StringBuilder sb;
                    sb = new StringBuilder(input);
                    sb.deleteCharAt(sb.length() - 1);
                    int input_num = Integer.parseInt(sb.toString());
                    for (int i = 0; i < input_num; i++) {
                        End_of_word(courser);
                    }
                }
                if (input.matches("[0-9]+" + "b")) {
                    StringBuilder sb;
                    sb = new StringBuilder(input);
                    sb.deleteCharAt(sb.length() - 1);
                    int input_num = Integer.parseInt(sb.toString());
                    for (int i = 0; i < input_num; i++) {
                        First_of_word(courser);
                    }
                }
                break;
        }

    }

}


// slm\nkhbi\nare mersi\nto khobi\nmnm khbobam che khbr\nhich jer