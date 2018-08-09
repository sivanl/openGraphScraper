
// @GENERATOR:play-routes-compiler
// @SOURCE:/Users/sivanlauf/OpenGraphScraper/conf/posts.routes
// @DATE:Thu Aug 09 11:19:28 IDT 2018


package posts {
  object RoutesPrefix {
    private var _prefix: String = "/"
    def setPrefix(p: String): Unit = {
      _prefix = p
    }
    def prefix: String = _prefix
    val byNamePrefix: Function0[String] = { () => prefix }
  }
}
