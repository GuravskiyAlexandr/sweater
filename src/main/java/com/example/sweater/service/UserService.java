package com.example.sweater.service;

import com.example.sweater.domain.Role;
import com.example.sweater.domain.User;
import com.example.sweater.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private MailSender mailSender;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null)
            throw new SecurityException("Wrong login or password");
        return user;
    }

    public Boolean addUser(User user, String registration) {
        User userFromDB = userRepository.findByUsername(user.getUsername());
        if (userFromDB != null) {
            return false;
        }
        user.setActive(false);
        user.setRoles(Collections.singleton(Role.USER));
        user.setActivationCode(UUID.randomUUID().toString());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);


        sendMessege(user, registration);

        return true;
    }

    private void sendMessege(User user, String registration) {
        if (!StringUtils.isEmpty(user.getEmail())) {
            String message = String.format(
                    "Hello, %s! \n" +
                            "Welcome to Sweater. Please, " +
                            "visit next link: http://localhost:8084/activate/" + registration + "/%s",
                    user.getUsername(),
                    user.getActivationCode()
            );
            mailSender.send(user.getEmail(), "Activation code", message);
        }
    }

    public boolean isActivationUser(String code) {
        User user = userRepository.findByActivationCode(code);
        if (user == null) {
            return false;
        }
        user.setActivationCode(null);
        user.setActive(true);
        user.setPassword2(user.getPassword());
        userRepository.save(user);
        return true;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public void saveUser(String username, Map<String, String> form, User user) {
        user.setUsername(username);
        System.out.println(form);
        Set<String> roleSet = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());
        user.getRoles().clear();
        for (String key : form.keySet()) {
            System.out.println(key);
            if (roleSet.contains(key)) {
                user.getRoles().add(Role.valueOf(key));
            }
        }
        userRepository.save(user);
    }

    public void updateProfile(User user, String email, String password) {
        String updateProfile = "updateProfile";
        String userEmail = user.getEmail();
        Boolean isEmailChange = (email != null && email.equals(userEmail) ||
                userEmail != null && userEmail.equals(email) ||
                userEmail == null);

        if (isEmailChange) {
            user.setEmail(email);
            if (!StringUtils.isEmpty(email)) {
                user.setActivationCode(UUID.randomUUID().toString());
            }
        }
        if (!StringUtils.isEmpty(password))
            user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);

        if (isEmailChange)
            sendMessege(user, updateProfile);
    }
}
