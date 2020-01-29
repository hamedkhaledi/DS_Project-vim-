

public class TrieNode {

    private static final int ALPHABET_SIZE = 26;
    TrieNode[] children = new TrieNode[ALPHABET_SIZE];
    TrieNode() {
        for (int i = 0; i < ALPHABET_SIZE; i++)
            children[i] = null;
    }
}
