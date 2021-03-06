package com.ilyaryabchinski.android.rssreader;

import android.os.AsyncTask;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.URL;

/**
 * Created by ilya on 6/22/17.
 */

class RSSWorker extends AsyncTask<String, Void, Container> {
    @Override
    protected Container doInBackground(String... params) {
        Container container = new Container();
        RSSItem rssItem = null;
        try{
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(false);
            XmlPullParser xpp = factory.newPullParser();
            //XmlPullParser xpp=  Xml.newPullParser();

            InputStream stream = new URL(params[0]).openStream();
            xpp.setInput(stream, null);

            boolean insideItem = false;

            // Returns the type of current event: START_TAG, END_TAG, etc..
            int eventType = xpp.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (!insideItem && eventType == XmlPullParser.START_TAG && xpp.getName().equalsIgnoreCase("title"))
                    container.setName(xpp.nextText());
                if (eventType == XmlPullParser.START_TAG) {
                    if (xpp.getName().equalsIgnoreCase("item")) {
                        insideItem = true;
                        rssItem = new RSSItem();
                    } else if (xpp.getName().equalsIgnoreCase("title")) {
                        if (insideItem)
                            rssItem.setTitle(xpp.nextText());
                    } else if (xpp.getName().equalsIgnoreCase("link")) {
                        if (insideItem)
                            rssItem.setLink(xpp.nextText());
                    } else if (xpp.getName().equalsIgnoreCase("description")) {
                        if (insideItem)
                            rssItem.setDescription(xpp.nextText());;
                    }

                } else if (eventType == XmlPullParser.END_TAG && xpp.getName().equalsIgnoreCase("item")) {
                    insideItem = false;
                    container.add(rssItem);
                }

                eventType = xpp.next(); /// move to next element
            }

        }catch (Exception e) {
            e.printStackTrace();
        }
        return container;
    }

}
