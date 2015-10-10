package com.example.android.top10downloader;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

/**
 * Created by emkayx on 30-09-2015.
 */
public class ParseApplications {
    private String xmlData;
    private ArrayList<Applications> applications;

    public ParseApplications(String xmlData) {
        this.xmlData = xmlData;
        applications = new ArrayList<Applications>();
    }

    public ArrayList<Applications> getApplications() {
        return applications;
    }
    public boolean process(){
        boolean status = true;
        Applications currentRecord =null;
        boolean inEntry = false;
        String textValue ="";
        try{
            XmlPullParserFactory factory =XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp= factory.newPullParser();
            xpp.setInput(new StringReader(this.xmlData));
            int eventType = xpp.getEventType();
            while(eventType!=XmlPullParser.END_DOCUMENT){
                String tagName =xpp.getName();
                switch (eventType){
                    case XmlPullParser.START_TAG:
                        Log.d("ParseApplications","Starting Tag for "+tagName);
                        if(tagName.equalsIgnoreCase("entry")){
                            inEntry = true;
                            currentRecord =new Applications();

                        } break;
                    case XmlPullParser.TEXT:
                        textValue = xpp.getText();
                        break;
                    case XmlPullParser.END_TAG:
                        Log.d("ParseApplications","Ending tag for "+ tagName);
                        if(inEntry){
                            if(tagName.equalsIgnoreCase("entry")){
                                applications.add(currentRecord);
                                inEntry = false;

                            }else if (tagName.equalsIgnoreCase("name")){
                                currentRecord.setName(textValue);
                            }else if(tagName.equalsIgnoreCase("artist")){
                                currentRecord.setArtist(textValue);
                            }else if (tagName.equalsIgnoreCase("releasedate")){
                                currentRecord.setReleaseDate(textValue);
                            }

                        }

                        break;
                    default:
                        //nothing else to do
                }
                eventType =xpp.next();

            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for(Applications app:applications){
            Log.d("parseApplications","********");
            Log.d("parseApplications","app name "+app.getName());
            Log.d("parseApplications","app artist "+app.getArtist());
            Log.d("parseApplications","app release date "+app.getReleaseDate());

        }
        return true;
    }
}
