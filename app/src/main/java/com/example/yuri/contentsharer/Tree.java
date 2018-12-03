package com.example.yuri.contentsharer;

import java.util.ArrayList;

public class Tree {
    private ArrayList<ArrayList<Node>> MainTree;
    private int level;
    private String currentNodeId;

    public Tree(String content){
        MainTree = new ArrayList<ArrayList<Node>>();
        MainTree.add(new ArrayList<Node>());
        MainTree.add(new ArrayList<Node>());
        MainTree.get(0).add(new Node("-1","0",content));

        level = 0;
        currentNodeId = "0";
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setCurrentNodeId(String currentNodeId) {
        this.currentNodeId = currentNodeId;
    }

    public int getLevel() {
        return level;
    }

    public String getCurrentNodeId() {
        return currentNodeId;
    }

    public String getParentNodeId() {
        //return MainTree.get(level).get(currentNodeId).getParentId();
        String response = "";

        for (Node position : MainTree.get(level)) {
            if(position.getId().equals(this.getCurrentNodeId())){
                response = position.getParentId();
            }
        }

        return response;
    }

    public String getCurrentNodeContent(){
        String response = "";

        for (Node position : MainTree.get(level)) {
            System.out.println(position.getId() + " " + this.currentNodeId);
            response = position.getId().equals(this.currentNodeId) ? position.getContent() : response;
        }

        System.out.println(response);

        return response;
    }

    public void addChild(String content){
        if(MainTree.size() - 2 == level){
            MainTree.add(new ArrayList<Node>());
        }

        int newNodeId = 0;
        for(Node position : MainTree.get(level + 1)){
            if(position.getParentId().equals(this.currentNodeId)){
                newNodeId++;
            }
        }

        MainTree.get(level + 1).add(new Node(this.currentNodeId, this.currentNodeId + Integer.toString(newNodeId), content));
    }


    public Node removeChild(String id){
        String toBeRemoved = this.currentNodeId + id;
        int listPosition = -1;

        for(int i = 0; i < MainTree.get(level + 1).size(); i++){
            if(MainTree.get(level + 1).get(i).getId().equals(toBeRemoved)){
                listPosition = i;
                i = MainTree.get(level + 1).size();
            }
        }

        return hasChildren(toBeRemoved) || listPosition != MainTree.get(level + 1).size()-1 ? null : MainTree.get(level+1).remove(listPosition);
    }

    public ArrayList<String> getNodeContent(){
        ArrayList<String> response = new ArrayList<String>();

        for (Node position : MainTree.get(level+1)) {
            if(position.getParentId().equals(this.getCurrentNodeId())){
                response.add(position.getContent());
            }
        }

        return response;
    }

    private boolean hasChildren(String id){
        boolean response = false;

        for(Node position : MainTree.get(id.length())){
            if(position.getParentId().equals(id)){
                response = true;
            }
        }

        return response;
    }
}
