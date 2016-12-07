import java.io.IOException
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.ActorMaterializer
import play.api.libs.json._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}
import scala.sys.process._
import scala.util.Success

//share the resources, no need to duplicate
trait SharedResources {
  implicit val system = ActorSystem("main")
  implicit val materializer = ActorMaterializer()
}

object projectCommands extends SharedResources {
  //clone project
  def cloneProject(gitHubUrl: String, gitHubName: String): Future[Int] = Future {
    "git clone " + gitHubUrl + " ./GIT-PROJECTS/" + gitHubName !
  }

  //response future for github repos
  val getRepos: Future[HttpResponse] =
  Http().singleRequest(HttpRequest(uri = s"https://api.github.com/search/repositories?q=${Server.userSearchInput}+language:${Server.lang}&sort=stars&order=desc"))
}


//starts app
object Server extends SharedResources with App {

  //only language supported due ot using the eclipse parser
  var lang = "java"

  //min range for projects to download and check note skip 0 since it is elastic search and it takes long time
  var rangeMin = 1

  //max range for projects to download and check
  var rangeMax = 5

  var userSearchInput = scala.io.StdIn.readLine("user search keyword: ")

  if(userSearchInput.isEmpty){
    userSearchInput = ""
  }

  print("Enter number of projects to download: ")
  var max = scala.io.StdIn.readInt()
  if(max.isInstanceOf[Int]){
    rangeMax = max
  }

  val response = Await.result(projectCommands.getRepos, 15 seconds)
  projectCommands.getRepos.onComplete({
    case Success(x) => {

      //delete all entries in the database
      println("Deleting ALL entries in the database")
      Database.deleteSqlEntries

      //delete GTI-PROJECTS
      println("Deleting GIT-PROJECTS Folder")
      "cmd /C RMDIR \"GIT-PROJECTS\" /Q /S " !

      //iterate through json
      Unmarshal(x).to[String].flatMap { jsonStr =>

        //get actual json
        var json = Json.parse(jsonStr)

        //iterate through json (min and max specified top)
        for (a <- rangeMin to rangeMax) {
          val gitHubUrl = (json \ "items") (a) \ "clone_url"
          val gitHubName = (json \ "items") (a) \ "name"
          val gitHubSize = (json \ "items") (a) \ "size"
          val openIssues = (json \ "items") (a) \ "open_issues"

          println("Processing #" + a + " " + gitHubName)

          //insert each project in project table
          Database.insertProject(gitHubName.toString().replace("\"",""), gitHubSize.toString().toInt, openIssues.toString().toInt)

          //Clone latest version of project
          projectCommands.cloneProject(gitHubUrl.toString(), gitHubName.toString()).onComplete({
            case Success(x) => {

              //println(s"Getting files from ./GIT-PROJECTS/${gitHubName.toString().replace("\"","")}/")
              //process each project
              MyParser.processProject(gitHubName.toString().replace("\"",""))
            }
          })
        }
        Future.failed(new IOException("Error!"))
      }
    }
  })

  println("Press 1 to search for projects with MOST JDT function calls")
  println("Press 2 to search for projects with LEAST JDT function calls")
  println("Press 3 to search for projects by JDT function call")
  println("Press 4 to search for projects with MOST open issues")
  println("Press 5 to search for projects with LEAST open issues")
  println("Press 6 to search for project with BIGGEST size")
  println("Press 7 to search for project with SMALLEST size")



}