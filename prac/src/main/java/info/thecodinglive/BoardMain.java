package info.thecodinglive;

import java.util.Date;
import java.util.stream.IntStream;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import info.thecodinglive.model.Board;
import info.thecodinglive.model.BoardType;
import info.thecodinglive.model.User2;
import info.thecodinglive.repository.BoardRepository;
import info.thecodinglive.repository.UserRepository;

@SpringBootApplication//스프링부트 메인으로 만드는 에노테이션
public class BoardMain implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(BoardMain.class, args);

	}
	
	@Autowired
	BoardRepository boardRepository;
	
	@Autowired
	UserRepository userRepository;
	
	//implements CommandLineRunner을 해야 run을 할 수 있음
	

	
	@Override
	public void run(String... args) throws Exception {
		//할때마다 들어가므로 처음넣고 주석달기
//		
//		User2 user = userRepository.findByUsername("1");
//		 IntStream.rangeClosed(1, 200).forEach(index->
//		 boardRepository.save(Board.builder()//값을 넣는것 
//		.title("게시글 제목"+index)
//		 .subTitle("순서(부제목)"+index) .content("콘텐츠내용")
//		 .boardType(BoardType.free)
//		.wdate(new Date()) .user(user) .build()));
		 
		//범위안에 값을 넣는것 2번부터200까지 글을 넣는것
		//index괄호안에 액션이라는 타입이있으므로 람다식으로 작성
	}

}
