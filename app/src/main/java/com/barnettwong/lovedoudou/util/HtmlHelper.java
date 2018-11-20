package com.barnettwong.lovedoudou.util;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

/**
 * Created by cgb on 2017/5/10.
 */

public class HtmlHelper {

    private ArrayList<String> imgList;
    private Document doc;
    private ArrayList<String> videoList;

    public HtmlHelper(String html) {
        doc = Jsoup.parse(html);
        processImgTag();
        processVideoTag();
    }

    public void processImgTag() {
        imgList = new ArrayList();
        Elements imgs = doc.getElementsByTag("img");
        int i = 0;
        for (Element img : imgs) {
            img.attr("width", "100%")
                    .attr("height", "auto")
                    .attr("onclick","window.android.showPic("+i+")");
            String src = img.attr("src");
            imgList.add(src);
            i++;
        }
    }

    public void processVideoTag() {
        videoList = new ArrayList();
        Elements videos = doc.getElementsByTag("video");
        for (Element video : videos) {
            video.attr("width","100%")
                    .attr("height","auto");
            videoList.add(video.attr("link"));
        }
    }

    public ArrayList<String> getImgList() {
        return imgList;
    }

    public Document getDoc() {
        return doc;
    }

    public ArrayList<String> getVideoList() {
        return videoList;
    }
}
