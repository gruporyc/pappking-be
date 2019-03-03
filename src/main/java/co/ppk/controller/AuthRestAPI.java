package co.ppk.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import co.ppk.dto.SimpleResponseDto;
import co.ppk.dto.UserDto;
import co.ppk.message.response.JwtResponse;
import co.ppk.security.jwt.JwtProvider;
import co.ppk.service.BusinessManager;
import co.ppk.dto.UserDto;
import co.ppk.dto.LoginDto;
import co.ppk.enums.RoleName;
import co.ppk.service.BusinessManager;
import com.sun.xml.internal.bind.v2.TODO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.ppk.message.response.JwtResponse;
import co.ppk.model.Role;
import co.ppk.model.User;
import co.ppk.repository.RoleRepository;
import co.ppk.repository.UserRepository;
import co.ppk.security.jwt.JwtProvider;

import static co.ppk.utilities.Constants.TOKEN_TYPE;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
public class AuthRestAPI {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    BusinessManager businessManager;

    @PostMapping("/sign-in")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginDto loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateJwtToken(authentication);
        return ResponseEntity.ok(new SimpleResponseDto(){{
          setSuccess(true);
          setMessage(TOKEN_TYPE + " " + jwt);
        }});
    }

    @PostMapping("/sign-up")
    public ResponseEntity<SimpleResponseDto> registerUser(@Valid @RequestBody UserDto signUpRequest) {
        if(userRepository.existsByUsername(signUpRequest.getUsername())) {
          /* TODO: Perform customer update profile with data given */
            return new ResponseEntity<SimpleResponseDto>(new SimpleResponseDto(){{
              setSuccess(true);
              setMessage("Username is already taken!");
            }}, HttpStatus.OK);
        }

//        if(userRepository.existsByEmail(signUpRequest.getEmail())) {
//            return new ResponseEntity<String>("Email is already in use!",
//                    HttpStatus.OK);
//        }

        // Creating user's account
        User user = new User(signUpRequest.getName(), signUpRequest.getUsername(),
                signUpRequest.getEmail(), encoder.encode(signUpRequest.getPassword()));

        List<String> strRoles = signUpRequest.getRoles();
        Set<Role> roles = new HashSet<>();

        strRoles.forEach(role -> {
        	switch(role) {
	    		case "admin":
	    			Role adminRole = roleRepository.findByName(RoleName.ROLE_ADMIN)
	                .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not found."));
	    			roles.add(adminRole);
	    			
	    			break;
	    		case "cust":
	            	Role pmRole = roleRepository.findByName(RoleName.ROLE_CUST)
	                .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not found."));
	            	roles.add(pmRole);
	            	businessManager.signUp(signUpRequest);
	    			break;
	    		default:
	        		Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
	                .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not found."));
	        		roles.add(userRole);        			
        	}
        });
        
        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok().body(new SimpleResponseDto(){{
          setMessage("User registered successfully!");
          setSuccess(true);
        }});
    }
}