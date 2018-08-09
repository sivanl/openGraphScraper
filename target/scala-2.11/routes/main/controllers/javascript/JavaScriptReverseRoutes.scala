
// @GENERATOR:play-routes-compiler
// @SOURCE:/Users/sivanlauf/OpenGraphScraper/conf/routes
// @DATE:Thu Aug 09 11:23:32 IDT 2018

import play.api.routing.JavaScriptReverseRoute
import play.api.mvc.{ QueryStringBindable, PathBindable, Call, JavascriptLiteral }
import play.core.routing.{ HandlerDef, ReverseRouteContext, queryString, dynamicString }


import _root_.controllers.Assets.Asset
import _root_.play.libs.F

// @LINE:1
package controllers.javascript {
  import ReverseRouteContext.empty

  // @LINE:1
  class ReverseScraperController(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:1
    def buildJson: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.ScraperController.buildJson",
      """
        function(id0) {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "stories/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[Long]].javascriptUnbind + """)("id", id0))})
        }
      """
    )
  
    // @LINE:2
    def generateId: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.ScraperController.generateId",
      """
        function() {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "stories"})
        }
      """
    )
  
  }


}
