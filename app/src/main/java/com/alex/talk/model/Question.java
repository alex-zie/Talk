package com.alex.talk.model;

import java.util.ArrayList;
import java.util.List;

public class Question {
    private final String content;
    private List<Integer> askedPlayers = new ArrayList<>();

    public Question(String content){
        this.content = content;
    }

    public String getContent(){
        return content;
    }

    public void addPlayer(int playerId){
        askedPlayers.add(playerId);
    }

    public boolean alreadyAskedTo(int playerId){
        return askedPlayers.contains(playerId);
    }

    public boolean askedAtLeast(int nTimes){
        return askedPlayers.size() >= nTimes;
    }
}
