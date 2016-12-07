import scala.Int;
import java.util.HashMap;

public class ProjectInfo {
    private String projectName;
    private HashMap<String, Int> functionsHashMap;
    private String folderUrl;
    private Int numberOfCommits;

    public ProjectInfo(String projectName, HashMap<String, Int> hm, Int numberOfCommits){
        this.projectName = projectName;
        this.functionsHashMap = hm;
        this.folderUrl = "./GIT-PROJECTS/" + this.projectName;
        this.numberOfCommits = numberOfCommits;
    }

    public void setProjectName(String name){
        this.projectName = name;
    }
    public void setProjectHashMap(String name){
        this.projectName = name;
    }
    public void setNumberOfCommits(Int numberOfCommits){
        this.numberOfCommits = numberOfCommits;
    }
    public String getProjectName(){
        return this.projectName;
    }
    public HashMap<String,Int> getFunctionsHashMap(){
        return this. functionsHashMap;
    }
    public Int getNumberOfCommits(){
        return this.numberOfCommits;
    }
}
