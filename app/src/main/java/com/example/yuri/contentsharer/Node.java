package com.example.yuri.contentsharer;

public class Node {
    private String parentId;
    private String id;
    private String content;

    public Node(String parentId, String id, String content){
        this.parentId = parentId;
        this.id = id;
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public String getParentId() {
        return parentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

}
