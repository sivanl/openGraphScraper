
package views.html

import play.twirl.api._
import play.twirl.api.TemplateMagic._


     object index_Scope0 {
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

class index extends BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with play.twirl.api.Template0[play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/():play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](format.raw/*1.4*/("""
"""),format.raw/*2.1*/("""<!doctype html>

<html lang="en">
  <head>
    <meta charset="utf-8">
    <title>Play REST API</title>
  </head>

  <body>
    <h1>Play REST API</h1>

    <p>
      This is a placeholder page to show you the REST API.  Use <a href="https://httpie.org/">httpie</a> to post JSON to the application.
    </p>

    <p>
      To see all posts, you can do a GET:
    </p>


<pre>
    <code>http GET localhost:9000/v1/posts</code>
</pre>

    <p>
      To create new posts, do a post
    <p>

<pre>
    <code>http POST localhost:9000/v1/posts title="Some title" body="Some Body"</code>
</pre>

<p>
  You can always look at the API directly: <a href="/v1/posts">/v1/posts</a>
</p>

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
object index extends index_Scope0.index
              /*
                  -- GENERATED --
                  DATE: Thu Aug 09 11:17:52 IDT 2018
                  SOURCE: /Users/sivanlauf/OpenGraphScraper/app/views/index.scala.html
                  HASH: d2077324878a80a03b45845a5127a990a22d6df5
                  MATRIX: 738->1|834->3|861->4
                  LINES: 27->1|32->1|33->2
                  -- GENERATED --
              */
          