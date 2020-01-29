
public class Trie_Table {
    public TrieNode root;

    void insert(String str) {
        int level;
        int length = str.length();
        int index;
        TrieNode temp_node = root;
        for (level = 0; level < length; level++) {
            index = str.charAt(level) - 'a';
            if (temp_node.children[index] == null)
                temp_node.children[index] = new TrieNode();
            temp_node = temp_node.children[index];
        }
    }
    boolean search(String str) {
        int level;
        int length = str.length();
        int index;
        TrieNode temp_node = root;
        for (level = 0; level < length; level++) {
            index = str.charAt(level) - 'a';
            if (temp_node.children[index] == null)
                return false;
            temp_node = temp_node.children[index];
        }
        return true;
    }


}
