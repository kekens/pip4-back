package pip.pip4back.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pip.pip4back.dto.UserDto;
import pip.pip4back.dto.AuthenticationResponse;
import pip.pip4back.model.User;
import pip.pip4back.repository.UserRepository;
import pip.pip4back.security.JwtProvider;

import java.util.Optional;

@Service
public class AuthService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private JwtProvider jwtProvider;

    @Autowired
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager, JwtProvider jwtProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
    }

    public void signup(UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(encodePassword(userDto.getPassword()));
        userRepository.save(user);
    }

    public boolean isUsernameFree(String username) {
        return userRepository.countByUsername(username) == 0;
    }

    public AuthenticationResponse login(UserDto userDto) {
        Authentication authenticate = null;
        try {
            authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDto.getUsername(),
                    userDto.getPassword()));
        } catch (AuthenticationException authenticationException) {
            return AuthenticationResponse.builder()
                    .authenticationToken("")
                    .username("")
                    .build();
        }
        assert authenticate != null;
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String authenticationToken = jwtProvider.generateToken(authenticate);
        String username = userDto.getUsername();
        return AuthenticationResponse.builder()
                .authenticationToken(authenticationToken)
                .username(username)
                .build();
    }

    public User getCurrentUser() {
        org.springframework.security.core.userdetails.User principal =  (org.springframework.security.core.userdetails.User)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findByUsername(Optional.of(principal).get().getUsername()).
                orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

}
