/*
 * Copyright (C) 2017 Luke Klinker
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package xyz.klinker.messenger.shared.util.xml;

import android.content.Context;
import android.content.res.XmlResourceParser;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import xyz.klinker.messenger.shared.R;

/**
 * Helper for parsing the faqs.xml resource into a usable array of strings that can
 * be displayed in an adapter.
 */
public class FaqsParser {

    private static final String ns = null;

    /**
     * Parses the faqs.xml file and returns a string array of each item.
     *
     * @param context the current application context.
     * @return an array of spanned strings for formatting and displaying.
     */
    public static String[] parse(Context context) {
        try {
            XmlResourceParser parser = context.getResources().getXml(R.xml.faqs);
            parser.next();
            parser.nextTag();
            return readChangelog(parser);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String[] readChangelog(XmlPullParser parser)
            throws XmlPullParserException, IOException {
        List<String> items = new ArrayList<>();

        parser.require(XmlPullParser.START_TAG, ns, "faqs");
        while (parser.next() != XmlPullParser.END_TAG) {
            String name = parser.getName();
            if ("item".equals(name)) {
                items.add(readVersion(parser));
            }
        }

        return items.toArray(new String[items.size()]);
    }

    private static String readVersion(XmlPullParser parser)
            throws XmlPullParserException, IOException {
        StringBuilder itemInfo = new StringBuilder();
        parser.require(XmlPullParser.START_TAG, ns, "item");
        itemInfo.append(readQuestion(parser));

        while (parser.next() != XmlPullParser.END_TAG) {
            String name = parser.getName();
            if ("answer".equals(name)) {
                itemInfo.append(readAnswer(parser));
            }
        }

        return itemInfo.toString();
    }

    private static String readQuestion(XmlPullParser parser)
            throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "item");
        String question = parser.getAttributeValue(null, "question");
        return "<b><u>" + question + "</b></u><br/><br/>";
    }

    private static String readAnswer(XmlPullParser parser)
            throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "answer");
        String text = readText(parser).replace("    ", "").replace("\n", " ");
        parser.require(XmlPullParser.END_TAG, ns, "answer");
        return text;
    }

    private static String readText(XmlPullParser parser)
            throws XmlPullParserException, IOException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }

}