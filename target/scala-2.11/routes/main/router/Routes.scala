
// @GENERATOR:play-routes-compiler
// @SOURCE:/Users/sivanlauf/OpenGraphScraper/conf/routes
// @DATE:Thu Aug 09 11:23:32 IDT 2018

package router

import play.core.routing._
import play.core.routing.HandlerInvokerFactory._
import play.core.j._

import play.api.mvc._

import _root_.controllers.Assets.Asset
import _root_.play.libs.F

class Routes(
  override val errorHandler: play.api.http.HttpErrorHandler, 
  // @LINE:1
  ScraperController_0: controllers.ScraperController,
  val prefix: String
) extends GeneratedRouter {

   @javax.inject.Inject()
   def this(errorHandler: play.api.http.HttpErrorHandler,
    // @LINE:1
    ScraperController_0: controllers.ScraperController
  ) = this(errorHandler, ScraperController_0, "/")

  import ReverseRouteContext.empty

  def withPrefix(prefix: String): Routes = {
    router.RoutesPrefix.setPrefix(prefix)
    new Routes(errorHandler, ScraperController_0, prefix)
  }

  private[this] val defaultPrefix: String = {
    if (this.prefix.endsWith("/")) "" else "/"
  }

  def documentation = List(
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """stories/""" + "$" + """id<[^/]+>""", """controllers.ScraperController.buildJson(id:Long)"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """stories""", """controllers.ScraperController.generateId()"""),
    Nil
  ).foldLeft(List.empty[(String,String,String)]) { (s,e) => e.asInstanceOf[Any] match {
    case r @ (_,_,_) => s :+ r.asInstanceOf[(String,String,String)]
    case l => s ++ l.asInstanceOf[List[(String,String,String)]]
  }}


  // @LINE:1
  private[this] lazy val controllers_ScraperController_buildJson0_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("stories/"), DynamicPart("id", """[^/]+""",true)))
  )
  private[this] lazy val controllers_ScraperController_buildJson0_invoker = createInvoker(
    ScraperController_0.buildJson(fakeValue[Long]),
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.ScraperController",
      "buildJson",
      Seq(classOf[Long]),
      "GET",
      """""",
      this.prefix + """stories/""" + "$" + """id<[^/]+>"""
    )
  )

  // @LINE:2
  private[this] lazy val controllers_ScraperController_generateId1_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("stories")))
  )
  private[this] lazy val controllers_ScraperController_generateId1_invoker = createInvoker(
    ScraperController_0.generateId(),
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.ScraperController",
      "generateId",
      Nil,
      "POST",
      """""",
      this.prefix + """stories"""
    )
  )


  def routes: PartialFunction[RequestHeader, Handler] = {
  
    // @LINE:1
    case controllers_ScraperController_buildJson0_route(params) =>
      call(params.fromPath[Long]("id", None)) { (id) =>
        controllers_ScraperController_buildJson0_invoker.call(ScraperController_0.buildJson(id))
      }
  
    // @LINE:2
    case controllers_ScraperController_generateId1_route(params) =>
      call { 
        controllers_ScraperController_generateId1_invoker.call(ScraperController_0.generateId())
      }
  }
}
