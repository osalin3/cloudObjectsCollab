import scala.collection.JavaConverters._
import org.eclipse.jdt.core.dom._

object MyParser {

  def parse(str: String, hashMap: java.util.HashMap[String, Int]) {
    val parser: ASTParser = ASTParser.newParser(AST.JLS8)
    parser.setSource(str.toCharArray)
    parser.setKind(ASTParser.K_COMPILATION_UNIT)
    parser.setResolveBindings(true)
    val cu: CompilationUnit = parser.createAST(null).asInstanceOf[CompilationUnit]
    cu.accept(new ASTVisitor() {

      //check methods invoked
      override def visit(node: MethodInvocation): Boolean = {
        //contained in the jdt
        if (node != null && node.getExpression != null && node.getLocationInParent.getNodeClass.toString.contains("jdt")) {

          if (!hashMap.containsKey(node.getName.toString)) {
            hashMap.put(node.getName.toString, 1)
          }
          else {
            var count = hashMap.get(node.getName.toString)
            hashMap.put(node.getName.toString, count + 1)
          }
        }
        true
      }
    })
  }

  def processProject(projName: String): Unit = {
    val filePaths = Reader.getFiles(s"./GIT-PROJECTS/${projName}").asScala.toList

    val hashMap = new java.util.HashMap[String, Int]()

    for(k <- filePaths) {
      if (k.getAbsolutePath.contains(".java")) {
        MyParser.parse(Reader.readFileToString(k.getAbsolutePath.replace("./", "/")), hashMap)
      }
    }

    if (hashMap.size() > 0) {
      val smap = hashMap.asScala.toMap
      for ((k, v) <- smap) if(!k.isEmpty) {Database.insertProjectFunctions(projName, k, v)}
    }
    hashMap.clear()
  }
}