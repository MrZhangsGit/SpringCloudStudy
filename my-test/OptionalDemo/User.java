package OptionalDemo;


import java.util.Optional;

/**
 * @author zhangs
 * @Description
 * @createDate 2019/6/3
 */
public class User {
    private String mail;
    private String name;
    private String position;
    private Address address;
    private Long time;

    public User() {
    }

    public User(String mail, String name, String position) {
        this.mail = mail;
        this.name = name;
        this.position = position;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Optional<String> getPosition() {
        return Optional.ofNullable(position);
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Optional<Address> getAddress() {
        return Optional.ofNullable(address);
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }
}
