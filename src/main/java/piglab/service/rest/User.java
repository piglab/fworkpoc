package piglab.service.rest;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * User represents an identified application user.
 * id is unique and auto-generated.
 * email also is unique
 */
@Entity
@Table(name = "t_user", uniqueConstraints = {@UniqueConstraint(columnNames = {"email"})})
public class User
{
    // An autogenerated id (unique for each user in the db)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    @Column(unique = true)
    private String email;

    @NotNull()
    private String lastname;

    @NotNull
    private String firstname;

    @NotNull
    private String nickname;

    public User() { }

    public User(long id) {
        this.id = id;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
