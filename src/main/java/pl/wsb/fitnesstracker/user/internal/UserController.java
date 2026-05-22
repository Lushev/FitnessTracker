package pl.wsb.fitnesstracker.user.internal;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.wsb.fitnesstracker.user.api.User;
import pl.wsb.fitnesstracker.user.api.UserDto;
import pl.wsb.fitnesstracker.user.api.UserNotFoundException;
import pl.wsb.fitnesstracker.user.api.UserProvider;
import pl.wsb.fitnesstracker.user.api.UserService;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final UserProvider userProvider;

    private final UserMapper userMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto addUser(@RequestBody UserDto userDto) {

        User user = userMapper.toUser(userDto);


        User createdUser = userService.createUser(user);

        return userMapper.toUserDto(createdUser);
    }

    @GetMapping
    public List<UserDto> getUsers() {

        return userProvider.findAllUsers()
                .stream()
                .map(userMapper::toUserDto)
                .toList();
    }

    @GetMapping("/simple")
    public List<UserDto> getSimpleUsers() {

        return userProvider.findAllUsers()
                .stream()
                .map(userMapper::toUserDto)
                .toList();
    }

    @GetMapping("/{id}")
    public UserDto getUser(@PathVariable Long id) {

        User user = userProvider.getUser(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        return userMapper.toUserDto(user);
    }

    @GetMapping("/email")
    public List<UserDto> getUserByEmail(@RequestParam String email) {

        return userProvider.searchUsersByEmail(email)
                .stream()
                .map(userMapper::toUserDto)
                .toList();
    }

    @GetMapping("/older/{time}")
    public List<UserDto> getOlderUsers(@PathVariable LocalDate time) {

        return userProvider.findAllUsers()
                .stream()
                .filter(user -> user.getBirthdate().isBefore(time))
                .map(userMapper::toUserDto)
                .toList();
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
    }

    @PutMapping("/{userId}")
    public UserDto updateUser(
            @PathVariable Long userId,
            @RequestBody UserDto userDto
    ) {

        User updatedUser = userService.updateUser(userId, userDto);

        return userMapper.toUserDto(updatedUser);
    }
}