    package org.parkinglot.parkinglot.entities;

    import jakarta.persistence.*;

    @Entity
    @Table(name = "user_grups") // Ensure this matches your GlassFish Realm 'Group Table'
    public class UserGroup {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id", nullable = false)
        private Long id;

        @Column(name = "username", nullable = false)
        private String username;

        @Column(name = "user_group", nullable = false) // Ensure this matches your 'Group Name Column'
        private String userGroup;

        // Getters and Setters
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        public String getUserGroup() { return userGroup; }
        public void setUserGroup(String userGroup) { this.userGroup = userGroup; }
    }