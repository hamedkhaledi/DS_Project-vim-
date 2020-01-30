public class Piece {
    public int start;
    public int length;
    public int end;
    public Piece_Type type;
    public Piece next_piece;
    public Piece previous_piece;
//    public String text;

    public Piece(int start, int end, Piece_Type type) {
        this.start = start;
        this.end = end;
        this.length = end - start;
        this.type = type;
//        if (type.equals(Piece_Type.original))
//            text = Vim.original_text.substring(start, end);
//        if (type.equals(Piece_Type.add_text))
//            text = Vim.add_text.substring(start, end);

    }

    public String text() {
        String text = "";
        if (this.type.equals(Piece_Type.original))
            text = Vim.original_text.substring(this.start, this.end);
        if (this.type.equals(Piece_Type.add))
            text = Vim.add_text.substring(this.start, this.end);
        return text;
    }
}
