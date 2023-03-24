package me.mirsowasvonegal.ka;

import me.mirsowasvonegal.utils.Stack;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;


public class KA {

    private ArrayList<Integer> states = new ArrayList<>(); // Zustandsliste von 1-X
    private int startState; // Ein bestimmter Zustand
    private Set<Integer> endStates = new TreeSet<>(); // Teilmenge der Zustände
    private Set<String> terminals = new HashSet<>();  // Kleinbuchstaben von a-z
    private ArrayList<KAPath> paths = new ArrayList<>();

    public KA(int stateCount, int startState, Set<Integer> endStates, Set<String> terminals, ArrayList<KAPath> paths) {
        for (int i = 0; i < stateCount; i++) {
            states.add(i);
        }
        this.startState = startState;
        this.endStates = endStates;
        this.terminals = terminals;
        this.paths = paths;
    }

    public boolean checkWord(String word) throws IllegalArgumentException {
        int state = startState;
        Stack stack = new Stack();
        stack.push("k0");
        String[] characters = word.split("");
        for (int i = 0; i < characters.length; i++) {
            if(!terminals.contains(characters[i])) {
                throw new IllegalArgumentException();
            }
            KAPath path = getPathByStateTerminalAndTop(state, characters[i], stack.top().toString());
            if(path == null) return false;
            state = path.getToState();
            if(path.getAction() == KAPath.Action.DELETE) {
                if(stack.top().toString().equals("k0")) return false;
                stack.pop();
            } else if(path.getAction() == KAPath.Action.ADD) {
                if(characters[i].equals("")) return false;
                stack.push(characters[i]);
            }
        }
        KAPath path = getPathByStateTerminalAndTop(state, "", stack.top().toString());
        if(path == null) return false;
        state = path.getToState();
        return endStates.contains(state);
    }

    public KAPath getPathByStateTerminalAndTop(int state, String terminal, String top) {
        for (KAPath path : paths) {
            if(path.getFromState() == state &&
                    path.getTerminal().equals(terminal) &&
                    path.getTop().equals(top)) {
                return path;
            }
        }
        return null;
    }


}
