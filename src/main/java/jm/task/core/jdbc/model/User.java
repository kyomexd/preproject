package jm.task.core.jdbc.model;

import javax.persistence.*;
/**
 * public class User
 *
 * User class represents a person entity, defined by their name, last name and age
 *
 * User can work with any JPA implementations, since persistence annotations are used
 */

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String name;

    @Column (name = "last_name")
    private String lastName;

    @Column
    private Byte age;

    public User() {
    }
    /**
     * @param name First name of a user
     * @param lastName Last name of a user
     * @param age Age of a user, represented by 1 byte
     */
    public User(String name, String lastName, Byte age) {
        this.name = name;
        this.lastName = lastName;
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Byte getAge() {
        return age;
    }

    public void setAge(Byte age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                '}';
    }
}
