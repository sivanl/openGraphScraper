
// @GENERATOR:play-routes-compiler
// @SOURCE:/Users/sivanlauf/OpenGraphScraper/conf/routes
// @DATE:Thu Aug 09 11:23:32 IDT 2018

package controllers;

import router.RoutesPrefix;

public class routes {
  
  public static final controllers.ReverseScraperController ScraperController = new controllers.ReverseScraperController(RoutesPrefix.byNamePrefix());

  public static class javascript {
    
    public static final controllers.javascript.ReverseScraperController ScraperController = new controllers.javascript.ReverseScraperController(RoutesPrefix.byNamePrefix());
  }

}
