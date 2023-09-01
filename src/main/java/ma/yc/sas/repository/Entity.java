package ma.yc.sas.repository;

public interface Entity {


    public Object add(Object object);
    public Object delete(Object object);
    public Object modify(Object object);
    public Object searchBy(String searchBy , String value);
    

}
