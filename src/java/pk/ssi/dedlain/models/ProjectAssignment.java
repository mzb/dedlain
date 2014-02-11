package pk.ssi.dedlain.models;

import pk.ssi.dedlain.utils.BaseModel;

public class ProjectAssignment extends BaseModel {

  private Long userId;
  private Long projectId;
  
  public Long getUserId() {
    return userId;
  }
  public void setUserId(Long userId) {
    this.userId = userId;
  }
  public Long getProjectId() {
    return projectId;
  }
  public void setProjectId(Long projectId) {
    this.projectId = projectId;
  }
}
