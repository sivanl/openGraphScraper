package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import util.Scraper;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sivanlauf on 08/08/2018.
 */
public class ScraperController extends Controller {

    Map<Long, String> urlToId = new HashMap<>();

    public Result generateId() {

        String url = request().getQueryString("url");
        long id = Scraper.generateUniqId(url);
        urlToId.put(id, url);
        return ok(Long.toString(id));
    }

    public Result buildJson(Long id){

        String url = urlToId.get(id);
        if(url != null){
            String json = Scraper.buildJson(url);
            return ok(json);
        }
        return notFound("ID not found").as("text/html");
    }
}
