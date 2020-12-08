package pip.pip4back.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pip.pip4back.dto.UserDto;
import pip.pip4back.dto.AuthenticationResponse;
import pip.pip4back.service.AuthService;
import pip.pip4back.service.ValidationService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private AuthService authService;
    private ValidationService validationService;

    @Autowired
    public AuthController(AuthService authService, ValidationService validationService) {
        this.authService = authService;
        this.validationService = validationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@Valid @RequestBody UserDto userDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errors = validationService.combineBindingResultErrors(bindingResult);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        } else {
            if (authService.isUsernameFree(userDto.getUsername())) {
                authService.signup(userDto);
                return new ResponseEntity<String>(HttpStatus.OK);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username " + userDto.getUsername() + " is taken");
            }
        }
    }

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody UserDto userDto, BindingResult bindingResult) {
        return authService.login(userDto);
    }

}
