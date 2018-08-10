package util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;


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

    private final static List<String> types = new ArrayList<>(Arrays.asList("article", "book", "profile"));
    private final static Map<String, List<String>> complexTypes = new HashMap<>();

    public static void main(String[] args) {
        System.out.println(buildJson("https://www.optimizesmart.com/how-to-use-open-graph-protocol/"));
        System.out.println(buildJson("https://www.ynet.co.il/articles/0,7340,L-5325758,00.html"));
    }

    static{
        initCoplexTypes();
    }

    private static void initCoplexTypes(){
        List<String> properties;
        properties = Arrays.asList("music:duration", "music:album", "music:album:disc", "music:album:track", "music:musician");
        complexTypes.put("music.song", properties);
        properties = Arrays.asList("music:song", "music:song:disc", "music:song:track", "music:musician", "music:release_date");
        complexTypes.put("music.album", properties);
        properties = Arrays.asList("music:song", "music:song:disc", "music:song:track", "music:creator");
        complexTypes.put("music.playlist", properties);
        properties = Arrays.asList("music:song", "music:song:disc", "music:song:track", "music:creator");
        complexTypes.put("music.playlist", properties);
        properties = Arrays.asList("music:creator");
        complexTypes.put("music.radio_station", properties);
    }



    public static String buildJson(String url){

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser parser = new JsonParser();
        long id = generateUniqId(url);

        try{
            String html = Scraper.getHtmlHead(url);
            if( html != null){
                JSONObject data = Scraper.FillTags(html);
                data.put(URL, url);
                data.put(ID, id);
                data.put(SCRAPE_STATUS, DONE);
                JsonObject json = parser.parse(data.toString()).getAsJsonObject();

                return gson.toJson(json);
            }

        }catch(Exception e){
            e.printStackTrace();
            return(buildErrorJson(url, id));
        }
        return(buildErrorJson(url, id));
    }

    private static String buildErrorJson(String url, long id){

        JSONObject data = new JSONObject();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser parser = new JsonParser();

        try {
            data.put(URL, url);
            data.put(ID, id);
            data.put(SCRAPE_STATUS, ERROR);
            JsonObject json = parser.parse(data.toString()).getAsJsonObject();
            return gson.toJson(json);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return ERROR;
    }

    /**
     *
     * @param url
     * @return
     */
    private static String getHtmlHead(String url){
        try{
            String inputLine;
            BufferedReader dis;
            StringBuffer headContents;
            URL pageURL = new URL(url);

            //Creating connection to a website WITH SSL certificate
            if(url.startsWith("https")){
                HttpsURLConnection siteConnection = (HttpsURLConnection)pageURL.openConnection();
                siteConnection.addRequestProperty("User-Agent", "Mozilla/4.76");
                dis = new BufferedReader(new InputStreamReader(siteConnection.getInputStream()));
                headContents = new StringBuffer();
            }
            //Creating connection to a website WITHOUT SSL certificate
            else{
                URLConnection siteConnection = pageURL.openConnection();
                siteConnection.addRequestProperty("User-Agent", "Mozilla/4.76");
                dis = new BufferedReader(new InputStreamReader(siteConnection.getInputStream()));
                headContents = new StringBuffer();
            }

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
            return headContentsStr;

        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param html
     * @return Json containing the Open Graph elements
     */

    private static JSONObject FillTags(String html){
        String type = "";

        HashMap<String,String> image = new HashMap<>();
        HashMap<String,String> video = new HashMap<>();
        HashMap<String,String> audio = new HashMap<>();

        JSONArray images = new JSONArray();
        JSONArray videos = new JSONArray();
        JSONArray audios = new JSONArray();
        JSONObject differentTypes = new JSONObject();
        JSONArray localeAlternates = new JSONArray();

        JSONObject ogData = new JSONObject();

        Document doc = Jsoup.parse(html);
        Elements metas = doc.select("meta");
        for(Element meta : metas){
            try{
                String property = meta.attr("property");
                if(property.contains("og:image")){

                    //Each time we get a new image element we push the one we already found
                    if(property.split(":").length == 2 && image.size()>0){
                        images.put(buildImage(image));
                        image = new HashMap<>();
                        image.put(property, meta.attr(CONTENT));
                    }
                    else{
                        image.put(property, meta.attr(CONTENT));
                    }
                }

                else if(property.contains("og:video")){
                    if(property.split(":").length == 2 && video.size()>0){
                        videos.put(buildVideo(video));
                        video = new HashMap<>();
                        video.put(property, meta.attr(CONTENT));
                    }
                    else{
                        video.put(property, meta.attr(CONTENT));
                    }
                }

                else if(property.contains("og:audio")){
                    if(property.split(":").length == 2 && audio.size()>0){
                        audios.put(buildVideo(video));
                        audio = new HashMap<>();
                        audio.put(property, meta.attr(CONTENT));
                    }
                    else{
                        audio.put(property, meta.attr(CONTENT));
                    }
                }

                else if(property.contains("og:locale:alternate")){
                    JSONObject locale = new JSONObject();
                    locale.put(property, meta.attr(CONTENT));
                    localeAlternates.put(locale);
                }

                else if(property.contains("og:") && (property.contains("time") || property.contains("date"))){
                    String time = meta.attr(CONTENT);
                    if(isNumeric(time)){
                        long longTime = Long.parseLong(time);
                        Date date = new Date(longTime);
                        ogData.put(property.split(":")[1], date);
                    }
                    else{
                        ogData.put(property.split(":")[1], time);
                    }
                }

                else if(types.contains(property.split(":")[0])){
                    differentTypes.put(property.split(":")[1], meta.attr(CONTENT));
                }

                else if(complexTypes.keySet().contains(property)){
                    differentTypes.put(property.split(":")[1], meta.attr(CONTENT));
                }

                else if(property.contains("og:")){
                    if(property.contains("type"))
                        type = meta.attr(CONTENT);
                    ogData.put(property.split(":")[1], meta.attr(CONTENT));
                }
            }catch(Exception e){
                e.printStackTrace();
            }

        }

        //Since we push elements only when we get a new one, the last one needs to be pushed separately at the end
        if(image.size() > 0)
            images.put(buildImage(image));
        if(video.size() > 0)
            videos.put(buildVideo(video));
        if(audio.size() > 0)
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
            if(types.contains(type) && differentTypes.length() > 0)
                ogData.put(type, differentTypes);
            if(complexTypes.containsKey(type) && differentTypes.length() > 0)
                ogData.put(type, differentTypes );

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

    private static boolean isNumeric(String strNum) {
        try {
            long d = Long.parseLong(strNum);
        } catch (NumberFormatException | NullPointerException nfe) {
            return false;
        }
        return true;
    }
}
