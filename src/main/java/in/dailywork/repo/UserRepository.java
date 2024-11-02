package in.dailywork.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import in.dailywork.model.UserDto;

public interface UserRepository extends JpaRepository<UserDto, Long> {
	
	public UserDto findByEmail(String email);

}
