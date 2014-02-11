package pk.ssi.dedlain.models;

import java.sql.Date;

import pk.ssi.dedlain.utils.BaseModel;

public class Iteration extends BaseModel {

  private Long projectId;
  private String name = "";
  private String description = "";
  private Date startDate;
  private Date endDate;
  
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
  public Date getStartDate() {
    return startDate;
  }
  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }
  public Date getEndDate() {
    return endDate;
  }
  public void setEndDate(Date endDate) {
    this.endDate = endDate;
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
