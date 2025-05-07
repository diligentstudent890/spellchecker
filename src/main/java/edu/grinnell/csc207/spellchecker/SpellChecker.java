package edu.grinnell.csc207.spellchecker;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * A spellchecker maintains an efficient representation of a dictionary for
 * the purposes of checking spelling and provided suggested corrections.
 */
public class SpellChecker {
    /** The number of letters in the alphabet. */
    private static final int NUM_LETTERS = 26;

    /** The path to the dictionary file. */
    private static final String DICT_PATH = "words_alpha.txt";

    /**
     * @param filename the path to the dictionary file
     * @return a SpellChecker over the words found in the given file.
     */
    public static SpellChecker fromFile(String filename) throws IOException {
        return new SpellChecker(Files.readAllLines(Paths.get(filename)));
    }

    /** A Node of the SpellChecker structure. */
    private class Node {
        // TODO: implement me!
        private char cur;
        private List<Node> next;
        public Node(char cur){
            this.cur = cur;
            this.next = new ArrayList<>();
        }

        public boolean nextExist(char chr){
            for (int iter = 0; iter < next.size(); iter++){
                if (next.get(iter).cur == chr){
                    return true;
                }
            }
            return false;
        }

        public Node next (char chr) {
            for (int iter = 0; iter < next.size(); iter++){
                if (next.get(iter).cur == chr){
                    return next.get(iter);
                }
            }
            return null;
        }

    }

    public boolean chrExist(char chr, List<Node> nodes){
        for (int iter = 0; iter < nodes.size(); iter++){
            if (nodes.get(iter).cur == chr){
                return true;
            }
        }
        return false;
    }

    /** The root of the SpellChecker */
    private List<Node> root;

    public SpellChecker(List<String> dict) {
        // TODO: implement me!
        this.root = new ArrayList<>();
        for(int iter = 0; iter < dict.size(); iter++) {
            add(dict.get(iter));
        }
    }

    public void add(String word) {
        char[] charword = word.toCharArray();
        if (charword.length == 0){
            return;
        }
        Node curNode = null;
        if (chrExist(charword[0], root)){
            curNode = new Node(charword[0]);
            root.add(curNode);
        } else {
            for(int iter = 0; iter < root.size(); iter++){
                if (root.get(iter).cur == charword[0]){
                    curNode = root.get(iter);
                }
            }
        }
        
        for(int iter = 1; iter < charword.length; iter++){
            if (iter + 1 < charword.length) {
                if (curNode.nextExist(charword[iter + 1])) {
                    curNode = curNode.next(charword[iter + 1]);
                } else {
                    curNode.next.add(new Node(charword[iter + 1]));
                    curNode = curNode.next(charword[iter + 1]);
                }
            }
        }
    }

    public boolean isWord(String word) {
        // TODO: implement me!
        char[] charword = word.toCharArray();
        
        Node curNode = null;
        if (chrExist(charword[0], root)){
            return false;
        } else {
            for(int iter = 0; iter < root.size(); iter++){
                if (root.get(iter).cur == charword[0]){
                    curNode = root.get(iter);
                }
            }
        }

        for(int iter = 1; iter < charword.length - 1; iter++) {
            if (curNode.nextExist(charword[iter])) {
                curNode = curNode.next(charword[iter]);
            }
            else {
                return false;
            }
        }
        return true;
    }

    public List<String> getOneCharCompletions(String word) {
        // TOOD: implement me!
        return null;
    }

    public List<String> getOneCharEndCorrections(String word) {
        // TODO: implement me!
        return null;
    }

    public List<String> getOneCharCorrections(String word) {
        // TODO: implement me!
        return null;
    }

    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            System.err.println("Usage: java SpellChecker <command> <word>");
            System.exit(1);
        } else {
            String command = args[0];
            String word = args[1];
            SpellChecker checker = SpellChecker.fromFile(DICT_PATH);
            switch (command) {
                case "check": {
                    System.out.println(checker.isWord(word) ? "correct" : "incorrect");
                    System.exit(0);
                }

                case "complete": {
                    List<String> completions = checker.getOneCharCompletions(word);
                    for (String completion : completions) {
                        System.out.println(completion);
                    }
                    System.exit(0);
                }

                case "correct": {
                    List<String> corrections = checker.getOneCharEndCorrections(word);
                    for (String correction : corrections) {
                        System.out.println(correction);
                    }
                    System.exit(0);
                }

                default: {
                    System.err.println("Unknown command: " + command);
                    System.exit(1);
                }
            }
        }
    }
}
