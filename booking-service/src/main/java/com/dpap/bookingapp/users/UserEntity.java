package com.dpap.bookingapp.users;

import jakarta.persistence.*;
import java.util.List;


@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @SequenceGenerator(name = "users_id_seq", sequenceName = "seq_users", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_id_seq")
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String password;
    private String email;

    public UserEntity() {
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<RoleEntity> roles;

    public List<RoleEntity> getRoles() {
        return roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstname) {
        this.firstName = firstname;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastname) {
        this.lastName = lastname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserEntity(Long id, String username, String firstName, String lastname, String password, String email, List<RoleEntity> roles) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.roles = roles;
        this.lastName = lastname;
        this.password = password;
        this.email = email;
    }
    //

    public void addRole(RoleEntity role) {
        this.roles.add(role);
    }

    public void removeRole(RoleEntity role) {
        if (this.roles.contains(role)) {
            this.roles.remove(role);
        }
    }
}
//    public UserEntity(Long id, String username, String firstname, String lastname, String password, String email, Collection<Role> roles) {
//        this.id = id;
//        this.username = username;
//        this.firstname = firstname;
//        this.lastname = lastname;
//        this.password = password;
//        this.email = email;
//        this.roles = roles;
//    }
//}