package info.thecodinglive.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import info.thecodinglive.model.User2;
import info.thecodinglive.repository.UserRepository;

//유저컨트롤러
@Controller
//restController는 리턴값을 글자그대로라고 인식하고
//그냥 컨트롤러는 리턴값을 위치로 인식함
public class BasicController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@RequestMapping({"/",""})//주소창에 아무것도 안치거나 /만 쳐도 나옴
	public String home() {
		return "/index";
	}

	//로그인 페이지
	@RequestMapping("/loginForm")
	public String loginForm() {
		return "/loginForm";
	}
	
	//회원가입 폼페이지
	@RequestMapping("/joinForm")
	public String joinForm() {
		return "/joinForm";
	}
	
	//회원가입 처리페이지
	@RequestMapping(value = "/join", method = RequestMethod.POST)
	public String join(User2 user) {
		//System.out.println(user.toString());//제대로 콘솔에 나오는지 확인하려고함
		user.setRoll("ROLE_ADMIN");
		//roll값을 ROLE_USER로 넣음
		
		String rawPassword=user.getPassword();
		String encPassword = bCryptPasswordEncoder.encode(rawPassword);
		System.out.println(rawPassword.toString());//암호화됐는지 확인해보기
		user.setPassword(encPassword);
		userRepository.save(user);

		
		return "redirect:/loginForm";//redirect:이동하라는 의미 로그인폼으로 이동해라
	}


}

//@RequestMapping("/user")
//public @ResponseBody String user() {
//	return "user";
//}//매번 html만들기 힘드므로 rest컨트롤러처럼 사용해보자.
////@ResponseBody를 사용해서 사용
//
//
//@RequestMapping("/admin")
//public @ResponseBody String admin() {
//	return "admin";
//}
//
//@RequestMapping("/manager")
//public @ResponseBody String manager() {
//	return "manager";
//}
