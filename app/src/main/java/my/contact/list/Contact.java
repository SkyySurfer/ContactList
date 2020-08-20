package my.contact.list;


public class Contact {


    private long id;
    private String name;
    private String number;
    private long photo;

    public Contact(long id, String name, long photo, String number) {
        this.id = id;
        this.name = name;
        this.photo = photo;
        this.number = number;
    }


    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setPhoto(long photo) {
        this.photo = photo;
    }


    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }


    public long getPhoto() {
        return photo;
    }

}
