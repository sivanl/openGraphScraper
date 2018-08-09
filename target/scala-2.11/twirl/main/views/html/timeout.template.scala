
package views.html

import play.twirl.api._
import play.twirl.api.TemplateMagic._


     object timeout_Scope0 {
import models._
import controllers._
import play.api.i18n._
import views.html._
import play.api.templates.PlayMagic._
import java.lang._
import java.util._
import scala.collection.JavaConversions._
import scala.collection.JavaConverters._
import play.core.j.PlayMagicForJava._
import play.mvc._
import play.data._
import play.api.data.Field
import play.mvc.Http.Context.Implicit._

class timeout extends BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with play.twirl.api.Template0[play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/():play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](format.raw/*1.4*/("""
"""),format.raw/*2.1*/("""<!DOCTYPE html>

<html>
  <head>
    <title>Timeout Page</title>
  </head>
  <body>
    <h1>Timeout Page</h1>

    Database timed out, so showing this page instead.
  </body>
</html>
"""))
      }
    }
  }

  def render(): play.twirl.api.HtmlFormat.Appendable = apply()

  def f:(() => play.twirl.api.HtmlFormat.Appendable) = () => apply()

  def ref: this.type = this

}


}

/**/
object timeout extends timeout_Scope0.timeout
              /*
                  -- GENERATED --
                  DATE: Thu Aug 09 11:17:52 IDT 2018
                  SOURCE: /Users/sivanlauf/OpenGraphScraper/app/views/timeout.scala.html
                  HASH: e1b66f6f389768a10087aa2f91a74d2e2bda9ea7
                  MATRIX: 742->1|838->3|865->4
                  LINES: 27->1|32->1|33->2
                  -- GENERATED --
              */
          