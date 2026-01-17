package org.parkinglot.parkinglot.ejb;

import jakarta.ejb.EJBException;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.parkinglot.parkinglot.common.UserDto;
import org.parkinglot.parkinglot.entities.User;
import org.parkinglot.parkinglot.entities.UserGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

@Stateless
public class UserBean {
    private static final Logger LOG = Logger.getLogger(UserBean.class.getName());

    @PersistenceContext
    EntityManager entityManager;

    @Inject
    PasswordBean passwordBean; // Injected to handle SHA-256 conversion

    public List<UserDto> findAllUsers() {
        LOG.info("Finding all users");
        try {
            TypedQuery<User> typedQuery = entityManager.createQuery("SELECT u FROM User u", User.class);
            List<User> users = typedQuery.getResultList();
            return copyUsersToDto(users);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    private List<UserDto> copyUsersToDto(List<User> users) {
        List<UserDto> userDtoList = new ArrayList<>();
        for (User user : users) {
            UserDto userDto = new UserDto(user.getId(), user.getUsername(), user.getPassword(), user.getEmail());
            userDtoList.add(userDto);
        }
        return userDtoList;
    }

    public void createUser(String username, String email, String password, Collection<String> groups) {
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setEmail(email);
        // This line is critical for login to work!
        newUser.setPassword(passwordBean.convertToSha256(password));
        entityManager.persist(newUser);

        assignGroupsToUser(username, groups);
    }

    private void assignGroupsToUser(String username, Collection<String> groups) {
        LOG.info("assignGroupsToUser");
        for (String group : groups) {
            UserGroup userGroup = new UserGroup();
            userGroup.setUsername(username);
            // Ensure this maps to your 'USERGROUP' column
            userGroup.setUserGroup(group);
            entityManager.persist(userGroup);
        }
    }

    public void deleteUsersByIds(List<Long> userIds) {
        LOG.info("deleteUsersByIds: " + userIds);
        try {
            for (Long userId : userIds) {
                User user = entityManager.find(User.class, userId);
                if (user != null) {
                    // Clean up role mappings first
                    entityManager.createQuery("DELETE FROM UserGroup g WHERE g.username = :username")
                            .setParameter("username", user.getUsername())
                            .executeUpdate();
                    entityManager.remove(user);
                }
            }
        } catch (Exception e) {
            throw new EJBException(e);
        }
    }

    public UserDto findById(Long userId) {
        User user = entityManager.find(User.class, userId);
        return new UserDto(user.getId(), user.getUsername(), user.getPassword(), user.getEmail());
    }

    public void updateUser(Long userId, String username, String email, String password, Collection<String> groups) {
        LOG.info("updateUser");
        User user = entityManager.find(User.class, userId);
        user.setUsername(username);
        user.setEmail(email);

        // Only update and hash if a new password was provided
        if (password != null && !password.isEmpty()) {
            user.setPassword(passwordBean.convertToSha256(password));
        }

        // Remove old group associations before adding new ones
        entityManager.createQuery("DELETE FROM UserGroup g WHERE g.username = :uname")
                .setParameter("uname", user.getUsername())
                .executeUpdate();

        assignGroupsToUser(username, groups);
    }
}