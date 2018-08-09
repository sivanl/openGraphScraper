package util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;


/**
 * Created by sivanlauf on 08/08/2018.
 */
public class Scraper {

    private final static String URL = "url";
    private final static String ID = "id";
    private final static String SCRAPE_STATUS = "scrape_status";
    private final static String CONTENT = "content";
    private final static String DONE = "done";
    private final static String ERROR = "error";

    public static void main(String[] args) {
        try{
//            Scraper scrap = new Scraper();

            String url = "http://ogp.me/";
            String html = Scraper.getHtmlHead(url);

            long id = generateUniqId(url);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonParser parser = new JsonParser();

            if( html != null){
                JSONObject data = Scraper.FillTags(html);
                data.put(URL, url);
                data.put(ID, id);
                data.put(SCRAPE_STATUS, DONE);
                JsonObject json = parser.parse(data.toString()).getAsJsonObject();

                System.out.println(gson.toJson(json));
            }

        }catch(Exception e){
            e.printStackTrace();
        }

    }

    public static String buildJson(String url){
        try{
            String html = Scraper.getHtmlHead(url);

            long id = generateUniqId(url);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonParser parser = new JsonParser();

            if( html != null){
                JSONObject data = Scraper.FillTags(html);
                data.put(URL, url);
                data.put(ID, id);
                data.put(SCRAPE_STATUS, DONE);
                JsonObject json = parser.parse(data.toString()).getAsJsonObject();

                System.out.println(gson.toJson(json));
                return gson.toJson(json);
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return "";
    }

    /**
     *
     * @param url
     * @return
     */
    private static String getHtmlHead(String url){
        try{

            URL pageURL = new URL(url);
            URLConnection siteConnection = pageURL.openConnection();
            BufferedReader dis = new BufferedReader(new InputStreamReader(siteConnection.getInputStream()));
            String inputLine;
            StringBuffer headContents = new StringBuffer();

            while ((inputLine = dis.readLine()) != null){

                //We only want the <head> part of the html, there is no need to go over the entire html
                if (inputLine.contains("</head>")){
                    inputLine = inputLine.substring(0, inputLine.indexOf("</head>") + 7);
                    inputLine = inputLine.concat("<body></body></html>");
                    headContents.append(inputLine + "\r\n");
                    break;
                }
                headContents.append(inputLine + "\r\n");
            }

            String headContentsStr = headContents.toString();
//            String t ="<head prefix=\"og: http://ogp.me/ns#\">\n" +
//                    "    <meta charset=\"utf-8\">\n" +
//                    "    <title>The Open Graph protocol</title>\n" +
//                    "    <meta name=\"description\" content=\"The Open Graph protocol enables any web page to become a rich object in a social graph.\">\n" +
//                    "    <script type=\"text/javascript\">var _sf_startpt=(new Date()).getTime()</script>\n" +
//                    "    <link rel=\"stylesheet\" href=\"base.css\" type=\"text/css\">\n" +
//                    "    <meta property=\"og:title\" content=\"Open Graph protocol\">\n" +
//                    "    <meta property=\"og:type\" content=\"website\">\n" +
//                    "    <meta property=\"og:url\" content=\"http://ogp.me/\">\n" +
//                    "    <meta property=\"og:image\" content=\"http://ogp.me/logo.png\">\n" +
//                    "    <meta property=\"og:image:type\" content=\"image/png\">\n" +
//                    "    <meta property=\"og:image:width\" content=\"300\">\n" +
//                    "    <meta property=\"og:image:height\" content=\"300\">\n" +
//                    "    <meta property=\"og:image:alt\" content=\"The Open Graph logo\">\n" +
//                    "    <meta property=\"og:image\" content=\"http://ogp.me/logo1.png\">\n" +
//                    "    <meta property=\"og:image:type\" content=\"image/png\">\n" +
//                    "    <meta property=\"og:image:width\" content=“200\">\n" +
//                    "    <meta property=\"og:image:height\" content=“200\">\n" +
//                    "\n" +
//                    "    <meta property=\"og:description\" content=\"The Open Graph protocol enables any web page to become a rich object in a social graph.\">\n" +
//                    "    <meta prefix=\"fb: http://ogp.me/ns/fb#\" property=\"fb:app_id\" content=\"115190258555800\">\n" +
//                    "    <link rel=\"alternate\" type=\"application/rdf+xml\" href=\"http://ogp.me/ns/ogp.me.rdf\">\n" +
//                    "    <link rel=\"alternate\" type=\"text/turtle\" href=\"http://ogp.me/ns/ogp.me.ttl\">\n" +
//                    "  </head><body></body></html>";
            return headContentsStr;

        }catch(Exception e){

            return null;
        }
    }

    /**
     *
     * @param html
     * @return
     */

    private static JSONObject FillTags(String html){

        HashMap<String,String> image = new HashMap<>();
        HashMap<String,String> video = new HashMap<>();
        HashMap<String,String> audio = new HashMap<>();

        JSONArray images = new JSONArray();
        JSONArray videos = new JSONArray();
        JSONArray audios = new JSONArray();
        JSONArray localeAlternates = new JSONArray();

        JSONObject ogData = new JSONObject();

        Document doc = Jsoup.parse(html);
        Elements metas = doc.select("meta");
        for(Element meta : metas){
            try{
                String property = meta.attr("property");
                if(property.contains("og:image")){
                    if(property.split(":").length == 2 && image.size()>0){
                        images.put(buildImage(image));
                        image = new HashMap<>();
                        image.put(property, meta.attr(CONTENT));
                    }
                    else{
                        image.put(property, meta.attr(CONTENT));
                    }
                }

                if(property.contains("og:video")){
                    if(property.split(":").length == 2 && video.size()>0){
                        videos.put(buildVideo(video));
                        video = new HashMap<>();
                        video.put(property, meta.attr(CONTENT));
                    }
                    else{
                        video.put(property, meta.attr(CONTENT));
                    }
                }

                if(property.contains("og:audio")){
                    if(property.split(":").length == 2 && audio.size()>0){
                        audios.put(buildVideo(video));
                        audio = new HashMap<>();
                        audio.put(property, meta.attr(CONTENT));
                    }
                    else{
                        audio.put(property, meta.attr(CONTENT));
                    }
                }

                if(property.contains("og:locale:alternate")){
                    JSONObject locale = new JSONObject();
                    locale.put(property, meta.attr(CONTENT));
                    localeAlternates.put(locale);

                }

                else if(property.contains("og:")){
                    ogData.put(property.split(":")[1], meta.attr(CONTENT));
                }
            }catch(Exception e){
                e.printStackTrace();
            }

        }
        if(image.size()>0)
            images.put(buildImage(image));
        if(video.size()>0)
            videos.put(buildVideo(video));
        if(audio.size()>0)
            audios.put(buildAudio(audio));

        try{
            if(images.length() > 0)
                ogData.put("image", images);
            if(videos.length() > 0)
                ogData.put("video", videos);
            if(audios.length() > 0)
                ogData.put("audio", audios);
            if(localeAlternates.length() > 0)
                ogData.put("localeAlternate", localeAlternates);
        }catch(Exception e){
            e.printStackTrace();
        }



        return ogData;
    }

    private static JSONObject buildImage(HashMap<String, String> image){
        JSONObject obj = new JSONObject();
        for(String img: image.keySet()){
            try{

                String[] imgData = img.split(":");
                if(imgData.length == 2){
                    obj.put(URL ,image.get(img));
                }
                else{
                    obj.put(imgData[2], image.get(img));
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        return obj;
    }

    private static JSONObject buildVideo(HashMap<String, String> video){
        JSONObject obj = new JSONObject();
        for(String vid: video.keySet()){
            try{

                String[] videoData = vid.split(":");
                if(videoData.length == 2){
                    obj.put(URL ,video.get(vid));
                }
                else{
                    obj.put(videoData[2], video.get(vid));
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        return obj;
    }

    private static JSONObject buildAudio(HashMap<String, String> audio){
        JSONObject obj = new JSONObject();
        for(String aud: audio.keySet()){
            try{
                String[] audioData = aud.split(":");
                if(audioData.length == 2){
                    obj.put(URL, audio.get(aud));
                }
                else{
                    obj.put(audioData[2], audio.get(aud));
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        return obj;
    }



    /**
     *
     * @param url
     * @return a unique id generated from the url
     */
    public static long generateUniqId(String url){
        long hash = 7;
        int strlen  = url.length();
        for (int i = 0; i < strlen; i++) {
            hash = hash*31 + url.charAt(i);
        }

        return hash;

    }
}
