package info.thecodinglive.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class User2 {
	
	
	
	@Id
	private String username;//아까 썼던거 고정 
	private String password;
	private String email;
	private String roll;
	//ROLL_USER, ROLL_MANAGER, ROLL_ADMIN등급이 존재
	//우리가 만드는 계정규칙(등급지정)
	
	@CreationTimestamp
	private Timestamp createDate;//회원가입일 시간저장

}
