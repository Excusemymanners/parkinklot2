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
    private EntityManager entityManager;

    @Inject
    private PasswordBean passwordBean; // Injected for SHA-256

    /**
     * Returnează toți utilizatorii din baza de date sub formă de DTO.
     */
    public List<UserDto> findAllUsers() {
        LOG.info("Finding all users");
        try {
            TypedQuery<User> typedQuery = entityManager.createQuery("SELECT u FROM User u", User.class);
            List<User> users = typedQuery.getResultList();
            return copyUsersToDto(users);
        } catch (Exception e) {
            throw new EJBException("Could not retrieve users: " + e.getMessage(), e);
        }
    }

    /**
     * Convertește o listă de Entități User în DTO-uri pentru a fi folosite în interfață.
     */
    private List<UserDto> copyUsersToDto(List<User> users) {
        List<UserDto> userDtoList = new ArrayList<>();
        for (User user : users) {
            UserDto userDto = new UserDto(user.getId(), user.getUsername(), user.getPassword(), user.getEmail());
            userDtoList.add(userDto);
        }
        return userDtoList;
    }

    /**
     * Creează un nou utilizator și îi asociază grupurile de securitate.
     * ATENȚIE: Ordinea parametrilor (username, password, email) se potrivește acum cu Servlet-ul.
     */
    public void createUser(String username, String password, String email, Collection<String> groups) {
        LOG.info("Creating user: " + username);
        try {
            User newUser = new User();
            newUser.setUsername(username);
            newUser.setEmail(email);
            // Criptare parolă folosind PasswordBean
            newUser.setPassword(passwordBean.convertToSha256(password));

            entityManager.persist(newUser);
            assignGroupsToUser(username, groups);
        } catch (Exception e) {
            throw new EJBException("Error saving user to database: " + e.getMessage(), e);
        }
    }

    /**
     * Inserează rolurile/grupurile pentru un utilizator în tabelul UserGroup.
     */
    private void assignGroupsToUser(String username, Collection<String> groups) {
        LOG.info("Assigning groups to user: " + username);
        if (groups != null) {
            for (String group : groups) {
                UserGroup userGroup = new UserGroup();
                userGroup.setUsername(username);
                userGroup.setUserGroup(group);
                entityManager.persist(userGroup);
            }
        }
    }

    /**
     * Șterge utilizatorii selectați și rolurile lor asociate.
     */
    public void deleteUsersByIds(List<Long> userIds) {
        LOG.info("deleteUsersByIds: " + userIds);
        try {
            for (Long userId : userIds) {
                User user = entityManager.find(User.class, userId);
                if (user != null) {
                    // 1. Ștergem rolurile din UserGroup (folosind username-ul)
                    entityManager.createQuery("DELETE FROM UserGroup g WHERE g.username = :username")
                            .setParameter("username", user.getUsername())
                            .executeUpdate();

                    // 2. Opțional: Dacă vrei să ștergi și mașinile lui automat
                    // Dacă ai cascade = CascadeType.ALL în entitatea User pe lista cars,
                    // se vor șterge automat la pasul 3.
                    // Dacă NU ai cascade, trebuie să le ștergi manual:
                /*
                for (Car car : user.getCars()) {
                    entityManager.remove(car);
                }
                */

                    // 3. Ștergem utilizatorul
                    entityManager.remove(user);
                }
            }
        } catch (Exception e) {
            throw new EJBException("Nu s-a putut șterge utilizatorul. Verifică dacă mai are mașini asociate. Eroare: " + e.getMessage());
        }
    }

    /**
     * Caută un singur utilizator după ID.
     */
    public UserDto findById(Long userId) {
        User user = entityManager.find(User.class, userId);
        if (user == null) return null;
        return new UserDto(user.getId(), user.getUsername(), user.getPassword(), user.getEmail());
    }

    /**
     * Actualizează datele unui utilizator și resetează grupurile.
     */
    public void updateUser(Long userId, String username, String email, String password, Collection<String> groups) {
        LOG.info("Updating user with ID: " + userId);
        try {
            User user = entityManager.find(User.class, userId);
            if (user == null) return;

            String oldUsername = user.getUsername();
            user.setUsername(username);
            user.setEmail(email);

            // Actualizăm parola doar dacă a fost introdusă una nouă în formular
            if (password != null && !password.trim().isEmpty()) {
                user.setPassword(passwordBean.convertToSha256(password));
            }

            // Ștergem grupurile vechi (bazat pe vechiul username) pentru a evita duplicate/orfani
            entityManager.createQuery("DELETE FROM UserGroup g WHERE g.username = :uname")
                    .setParameter("uname", oldUsername)
                    .executeUpdate();

            assignGroupsToUser(username, groups);
        } catch (Exception e) {
            throw new EJBException("Failed to update user: " + e.getMessage(), e);
        }
    }

    /**
     * Returnează o listă de nume de utilizatori pentru o listă de ID-uri (folosit la facturare/rapoarte).
     */
    public Collection<String> findUsernamesByUserIds(Collection<Long> userIds) {
        if (userIds == null || userIds.isEmpty()) return new ArrayList<>();
        return entityManager.createQuery("SELECT u.username FROM User u WHERE u.id IN :userIds", String.class)
                .setParameter("userIds", userIds)
                .getResultList();
    }

    /**
     * Returnează grupurile/rolurile unui utilizator.
     */
    public Collection<String> findUserGroups(String username) {
        return entityManager.createQuery("SELECT g.userGroup FROM UserGroup g WHERE g.username = :username", String.class)
                .setParameter("username", username)
                .getResultList();
    }
}