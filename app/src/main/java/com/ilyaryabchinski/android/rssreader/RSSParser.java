package com.ilyaryabchinski.android.rssreader;


public class RSSParser {

    private Container container;

    public RSSParser() {
        this.container = new Container();
    }

    public Container parse(final String url) {
        try {
            container = new Container();
            RSSWorker worker = new RSSWorker();
            container = worker.execute(url).get();

        }catch (Exception e){
            //logging
        }
        return container;
    }



}



