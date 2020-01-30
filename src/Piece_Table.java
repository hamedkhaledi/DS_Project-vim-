public class Piece_Table {
    Piece First_Piece;
    Piece Last_Piece;

    public void add(Piece piece, Piece previous) {
        if (First_Piece != null) {
            if (previous != null) {
                piece.previous_piece = previous;
                piece.next_piece = previous.next_piece;
                if (previous.next_piece != null)
                    previous.next_piece.previous_piece = piece;
                else
                    Last_Piece = piece;
                previous.next_piece = piece;
            } else {
                piece.next_piece = First_Piece;
                First_Piece.previous_piece = piece;
                First_Piece = piece;
                piece.previous_piece = null;
            }
        } else {
            First_Piece = piece;
            Last_Piece = piece;
            First_Piece.previous_piece = null;
            First_Piece.next_piece = null;
        }
    }

    public void remove(Piece piece) {
        if (piece == First_Piece)
            First_Piece = piece.next_piece;
        if (piece == Last_Piece)
            Last_Piece = piece.previous_piece;
        if (piece.previous_piece != null)
            piece.previous_piece.next_piece = piece.next_piece;
        if (piece.next_piece != null)
            piece.next_piece.previous_piece = piece.previous_piece;
        piece.next_piece = null;
        piece.previous_piece = null;
        piece = null;
    }
}
