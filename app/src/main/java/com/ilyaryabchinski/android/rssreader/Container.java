package com.ilyaryabchinski.android.rssreader;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Container Wrapper for our RSS Items
 */

public class Container implements Iterable {

    private  String name;
    private List<RSSItem> list;

    public RSSItem getItem(int index){
        return list.get(index);
    }

    public Container(){
        list = new ArrayList<>();
    }

    public Container(String name){
        this();
        this.name = name;
    }

    public void add (RSSItem newItem){
        list.add(newItem);
    }

    @Override
    public Iterator iterator() {
        return list.iterator();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int size(){return list.size();}

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Title: ").append(name);
        for (RSSItem item: list) {
            sb.append("\nTitle: ").append(item.getTitle());
            sb.append("\nLink: ").append(item.getLink());
            sb.append("\nDescription: ").append(item.getDescription());
        }
        return sb.toString();
    }

    public  List getList(){ return list;}
}
