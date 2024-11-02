package in.dailywork.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import in.dailywork.model.UserDto;
import in.dailywork.repo.UserRepository;

@RestController
//@RequestMapping("user/api/")
public class MyUserRestController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AuthenticationManager authManager;

	@PostMapping("/login")
	public ResponseEntity<String> loginCheck(@RequestBody UserDto userDto) {

		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDto.getEmail(),
				userDto.getPassword());

		try {
			Authentication authenticate = authManager.authenticate(token);
			if (authenticate.isAuthenticated()) {
				return new ResponseEntity<String>("Welcome to Spring Security", HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>("Invalid Credentials...!", HttpStatus.BAD_REQUEST);
	}

	@PostMapping("/register")
	public ResponseEntity<String> saveUser(@RequestBody UserDto userDto) {
		String encodePassword = passwordEncoder.encode(userDto.getPassword());
		userDto.setPassword(encodePassword);
		userRepository.save(userDto);
		return new ResponseEntity<>("User registered successful", HttpStatus.CREATED);
	}

}
