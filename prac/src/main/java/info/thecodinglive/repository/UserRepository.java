package info.thecodinglive.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import info.thecodinglive.model.User2;

public interface UserRepository extends JpaRepository<User2, String>{

	//사용할 model을 리턴값으로 그안에있는 요소로 찾는다.
	 public User2 findByUsername(String username);//우리가 만들어쓰는 메소드 규칙

	
}
