package pk.ssi.dedlain.models;

import pk.ssi.dedlain.utils.BaseModel;

public class Task extends BaseModel {

  private Long projectId;
  private String name = "";
  private String description = "";
  private Long iterationId;
  private Long timeEstimation;
  private boolean done = false;
  
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public String getDescription() {
    return description;
  }
  public void setDescription(String description) {
    this.description = description;
  }
  public Long getIterationId() {
    return iterationId;
  }
  public void setIterationId(Long iterationId) {
    this.iterationId = iterationId;
  }
  public Long getTimeEstimation() {
    return timeEstimation;
  }
  public void setTimeEstimation(Long timeEstimation) {
    this.timeEstimation = timeEstimation;
  }
  public boolean isDone() {
    return done;
  }
  public void setDone(boolean done) {
    this.done = done;
  }
  public void setProjectId(Long projectId) {
    this.projectId = projectId;
  }
  public Long getProjectId() {
    return projectId;
  }
  
  @Override
  public String toString() {
    return getName();
  }
}
