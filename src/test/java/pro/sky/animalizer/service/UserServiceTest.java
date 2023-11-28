package pro.sky.animalizer.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.animalizer.exceptions.UserNotFoundException;
import pro.sky.animalizer.model.User;
import pro.sky.animalizer.model.UserType;
import pro.sky.animalizer.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepositoryMock;
    @InjectMocks
    private UserService userService;


    User userTest = new User(0123L, "NikolayNick", "Nikolay Nikolaev", "9150010101");


    @Test
    void testCreateUser() {
        User actual = userService.createUser(new User(0123L, "NikolayNick", "Nikolay Nikolaev", "9150010101"));
        assertEquals(userRepositoryMock.save(userTest), actual);

    }

    @Test
    void testFindUserByIdWhenTheUserNotFound() {
        userTest = new User(0123L, "NikolayNick", "Nikolay Nikolaev", "9150010101");
        userTest.setId(3L);
        assertThrows(UserNotFoundException.class, () -> userService.findUserById(2L));

    }
    @Test
    void testFindUserByIdOk() {
        userTest = new User(0123L, "NikolayNick", "Nikolay Nikolaev", "9150010101");
        userTest.setId(3L);
        when(userRepositoryMock.findById(3L)).thenReturn(Optional.ofNullable(userTest));
        assertEquals(userService.findUserById(3L), userTest);
    }

    @Test
    void testGetAllUsers() {
        List<User> listUsersTest = new ArrayList<>();
        when(userRepositoryMock.findAll()).thenReturn(listUsersTest);
        assertEquals(userService.getAllUsers(), listUsersTest);
    }

    @Test
    void testEditUserWhichIsAbsentInBd() {
        User editedUser = new User();
        editedUser.setFullName("Ivan");
        assertThrows(UserNotFoundException.class, () -> userService.editUser(1L, editedUser));
    }

    @Test
    void testEditUser() {
        userTest = new User(067L, "Ivan", "Ivan Ivanov", "000040404059");
        userTest.setId(3L);
        Mockito.doReturn(Optional.ofNullable(userTest))
                .when(userRepositoryMock).findById(3L);
        assertEquals(Optional.of(userTest), userRepositoryMock.findById(3L));
    }

    @Test
    void testDeleteUserByIdWhichIsAbsentInBD() {
        userTest.setId(3L);
        assertThrows(UserNotFoundException.class, () -> userService.deleteUserById(3L));
    }
}