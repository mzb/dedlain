package pk.ssi.dedlain.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class ORM<M extends BaseModel> {

  protected Database db;

  /**
   * Konstruktor.
   * @param db baza danych
   */
  public ORM(Database db) throws Database.Error {
    this.db = db;
  }

  /**
   * @return nazwa głowej tabeli, w której składowane są zarządzane modele.
   */
  public abstract String getTableName();

  /**
   * Buduje instancję modelu na podstawie wyników zapytania do bazy.
   * @param row wynik zapytania
   * @return instanja modelu
   * @throws Database.Error
   */
  public abstract M build(QueryResults row) throws Database.Error;

  /**
   * Buduje instancję modelu na podstawie podanego atrybutu.
   * @param attr atrybut niezbędny do otworzenia instancji modelu (arg. konstruktora).
   * @return instancja modelu
   */
  public abstract M build(String attr);

  /**
   * Dodaje do bazy wiersz reprezentujący podany model.
   * @param model model do zapisania
   * @throws Database.Error
   */
  public abstract void create(M model) throws Database.Error;

  /**
   * Uaktualnia w bazie zapisany model.
   * @param model model do aktualizacji
   * @throws Database.Error
   */
  public abstract void update(M model) throws Database.Error;

  /**
   * Wyszukuje w bazie listę modeli spełniających podane warunki.
   * @param conditions fragm SQL (klauzula WHERE)
   * @param order sortowanie wyników (klauzula ORDER)
   * @param limit ilość wyników (klauzula LIMIT)
   * @return lista modeli spełniających podane warunki
   * @throws Database.Error
   */
  public List<M> find(Object[] conditions, String order, String limit) throws Database.Error {
    String query = "select * from " + getTableName();
    if (conditions != null && conditions.length > 0) {
      query += " where (" + conditions[0] + ")";
    }
    if (order != null && !order.isEmpty()) {
      query += " order by " + order;
    }
    if (limit != null && !limit.isEmpty()) {
      query += " limit " + limit;
    }

    Object[] bindings = conditions != null ? 
        Arrays.copyOfRange(conditions, 1, conditions.length) : new Object[] {};
        QueryResults rows = db.query(query, bindings);

        List<M> models = new ArrayList<M>();
        while (rows.next()) {
          models.add(build(rows));
        }
        rows.close();

        return models;
  }

  /**
   * Wyszukuje pierwszy model spełniający podane warunki.
   * @param conditions klauzula WHERE
   * @param order klauzula ORDER
   * @return instancja modelu o podanym id lub null jesli nie znaleziono.
   * @throws Database.Error
   */
  public M findFirst(Object[] conditions, String order) throws ModelNotFoundError, Database.Error {
    List<M> results = find(conditions, order, "1");
    if (results.isEmpty()) {
      throw new ModelNotFoundError(getTableName());
    } else {
      return results.get(0);
    }
  }

  /**
   * Wyszukuje w bazie model o podanym ID.
   * @param id ID modelu
   * @return instancja modelu o podanym ID lub null
   * @throws Database.Error
   */
  public M findById(Long id) throws ModelNotFoundError, Database.Error {
    return findFirst(new Object[]{ getTableName() + ".id = ?", id }, null);
  }

  public M findById(String id) throws ModelNotFoundError, Database.Error {
    return findFirst(new Object[]{ getTableName() + ".id = ?", id }, null);
  }

  /**
   * Zwraca ilosc obiektow w bazie spelniajacych podane warunki.
   * @param conditions klauzula WHERE
   * @return ilość obiektów
   * @throws Database.Error
   */
  public long count(Object[] conditions) throws Database.Error {
    String query = "select count(*) as models_count from " + getTableName();
    if (conditions != null && conditions.length > 0) {
      query += " where (" + conditions[0] + ")";
    }
    Object[] bindings = conditions != null ? 
        Arrays.copyOfRange(conditions, 1, conditions.length) : new Object[]{};
    QueryResults rows = db.query(query, bindings);

    if (!rows.empty()) {
      return rows.getLong("models_count");
    }
    return 0;
  }

  /**
   * Zwraca obiekt w bazie posiadający kolumnę z podaną wartością lub tworzy taki obiekt, 
   * jeśli go nie znaleziono.
   * @param column nazwa kolumny
   * @param value wartość kolumny
   * @return znaleziona lub utworzona instancja modelu
   * @throws Database.Error
   */
  public M findOrCreateBy(String column, String value) throws Database.Error {
    List<M> found = find(new Object[]{ String.format("%s = ?", column), value }, null, "1");
    M model;
    if (found.isEmpty()) {
      model = build(value);
      create(model);
    } else {
      model = found.get(0);
    }
    return model;
  }

  /**
   * Zapisuje podany model do bazy - albo przez dodanie albo aktualizację - 
   * o ile powiodła się walidacja danych modelu.
   * @param model model do zapisania
   * @throws Database.Error
   * @throws ValidationErrors
   */
  public void save(M  model) throws Database.Error {
    if (model.getId() != null) {
      update(model);
    } else {
      create(model);
    }
  }

  /**
   * Usuwa z bazy modele spełniające podane warunki.
   * @param conditions klauzula WHERE
   * @throws Database.Error
   */
  public void delete(Object[] conditions) throws Database.Error {
    String stmt = "delete from " + getTableName();
    if (conditions != null && conditions.length > 0) {
      stmt += " where (" + conditions[0] + ")";
    }
    Object[] bindings = conditions != null ? 
        Arrays.copyOfRange(conditions, 1, conditions.length) : new Object[]{};
    db.update(stmt, bindings);
  }

  /**
   * Usuwa podany model z bazy.
   * @param model model do usunięcia
   * @throws Database.Error
   */
  public void delete(M model) throws Database.Error {
    delete(new Object[]{ "id = ?", model.getId() });
  }

  /**
   * Waliduje podany model. 
   * Klasy dziedziczące powinny przeciążyć tą metodę 
   * w celu wykonania walidacji przed zapisem do bazy.
   * @param model model do zwalidowania.
   * @throws ValidationErrors
   */
  public void validate(M model) throws Validation.Errors, Database.Error {

  }


  public static class ModelNotFoundError extends Exception {
    public ModelNotFoundError(String message) { super(message); }
  }
}
