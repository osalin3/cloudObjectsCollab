import java.sql._

object Database {

  @throws[SQLException]
  def deleteSqlEntries = {
    val conn: Connection = dbConnection
    val deleteQuery1: String = "DELETE FROM projects.project"
    val deleteQuery2: String = "DELETE FROM projects.proj_jdtfunctions"
    //delete table 1
    val preparedStmt1: PreparedStatement = conn.prepareStatement(deleteQuery1)
    preparedStmt1.execute
    //delete table 2
    val preparedStmt2: PreparedStatement = conn.prepareStatement(deleteQuery2)
    preparedStmt2.execute
    //reset increment
    val resetIncrementQuery = "ALTER TABLE project AUTO_INCREMENT = 1"
    val preparedStmt3: PreparedStatement = conn.prepareStatement(resetIncrementQuery)
    preparedStmt3.execute
    conn.close()
  }

  @throws[SQLException]
  private def dbConnection: Connection = {
      // create our mysql database connection
      val myUrl: String = "jdbc:mysql://localhost/projects?autoReconnect=true&useSSL=false"
      //user and pass must be specified accordingly
      val conn: Connection = DriverManager.getConnection(myUrl, "root", "123321")
      conn
  }

  @throws[SQLException]
  private def getResultSet(query: String, conn: Connection): ResultSet = {
    if (conn != null && !query.isEmpty) {
      val st: Statement = conn.createStatement
      val rs: ResultSet = st.executeQuery(query)
      return rs
    }
    null
  }

  @throws[SQLException]
  def insertProject(name: String, size: Int, numberIssues: Int): Boolean = {
    val conn: Connection = dbConnection
    val rsResult: Int = getProjectId(name)
    //project exists
    if (rsResult != -1) return false
    val insertQuery: String = " insert into project (project_name, size, number_issues) values (?, ?, ?)"
    val preparedStmt: PreparedStatement = conn.prepareStatement(insertQuery)
    preparedStmt.setString(1, name)
    preparedStmt.setInt(2, size)
    preparedStmt.setInt(3, numberIssues)
    preparedStmt.execute
    conn.close()
    true
  }

  @throws[SQLException]
  def insertProjectFunctions(projectName: String, functionName: String, numberOfTimes: Int): Boolean = {
    val projId: Int = getProjectId(projectName)
    if (projId == -1) return false
    val conn: Connection = dbConnection
    val insertQuery: String = "INSERT INTO `projects`.`proj_jdtfunctions` (`proj_id`,`function`,`function_times`) VALUES(?, ?, ?);"
    val preparedStmt: PreparedStatement = conn.prepareStatement(insertQuery)
    preparedStmt.setInt(1, projId)
    preparedStmt.setString(2, functionName)
    preparedStmt.setInt(3, numberOfTimes)
    preparedStmt.execute
    conn.close()
    true
  }

  @throws[SQLException]
  private def getProjectId(name: String): Int = {
    val conn: Connection = dbConnection
    var projId: Int = -1
    val query: String = s"SELECT id FROM projects.project WHERE project_name = '${name}'"
    val rs: ResultSet = getResultSet(query, conn)
    if (rs != null) while (rs.next) projId = rs.getInt("id")
    conn.close()
    projId
  }
}
