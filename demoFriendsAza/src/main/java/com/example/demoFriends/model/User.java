package com.example.demoFriends.model;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(unique = true, nullable = false, length = 45, name = "username")
    private String username;
    @Column(nullable = false, length = 15, name = "password")
    private String password;
    @Column(nullable = false, length = 45, name = "first_name")
    private String first_name;
    @Column(nullable = false, length = 45, name = "last_name")
    private String last_name;
    @Column(unique = true, nullable = false, length = 45, name = "email")
    private String email;
    private boolean enabled;
    @Column(length = 45, name = "age")
    private Integer age;
    @Column(length = 45, name = "course")
    private Integer course;
    @Column(length = 45, name = "specialization")
    private String specialization;
    @Column(length = 10, name = "gender")
    private String gender;
    @Column(length = 64, name = "photos")
    private String photos;

    @Transient
    public String getPhotosImagePath() {
        if (photos == null || id == null) return null;

        return "/user-photos/" + id + "/" + photos;
    }

    public User(Integer id, String username, String password, String first_name, String last_name, String email, boolean enabled, Integer age, Integer course, String specialization, String gender, String photos) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.enabled = enabled;
        this.age = age;
        this.course = course;
        this.specialization = specialization;
        this.gender = gender;
        this.photos = photos;
    }

    public User(Integer id, String username, String password, String first_name, String last_name, String email, Boolean enabled) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.enabled = enabled;
    }

    public User(Integer id, String username, String password, String first_name, String last_name, String email, boolean enabled, Integer age, Integer course, String specialization, String gender) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.enabled = enabled;
        this.age = age;
        this.course = course;
        this.specialization = specialization;
        this.gender = gender;
    }

    public User() {

    }

    public String getPhotos() {
        return photos;
    }

    public void setPhotos(String photos) {
        this.photos = photos;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getCourse() {
        return course;
    }

    public void setCourse(Integer course) {
        this.course = course;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", email='" + email + '\'' +
                ", enabled=" + enabled +
                ", age=" + age +
                ", course=" + course +
                ", specialization='" + specialization + '\'' +
                ", gender='" + gender + '\'' +
                ", photos='" + photos + '\'' +
                '}';
    }
}
