package pk.ssi.dedlain.utils;

public class BaseModel {

  private Long id;

  /**
   * @return the id
   */
  public Long getId() {
    return id;
  }

  /**
   * @param id the id to set
   */
  public void setId(Long id) {
    this.id = id;
  }
  
  @Override
  public boolean equals(Object obj) {
    if (obj == null || !obj.getClass().equals(getClass())) {
      return false;
    }
    BaseModel other = (BaseModel) obj;
    return other.getId() == getId();
  }
}
