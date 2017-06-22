package com.ilyaryabchinski.android.rssreader;

/**
 * Class provides instances of RSS document
 */

public class RSSItem {
    private String title;
    private String description;
    private String link;

    public RSSItem() {
        title = null;
        description = null;
        link = null;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return title;
    }
}
