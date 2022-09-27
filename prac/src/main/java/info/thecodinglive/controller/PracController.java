package info.thecodinglive.controller;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.domain.Sort;
import info.thecodinglive.model.Board;
import info.thecodinglive.model.User2;
import info.thecodinglive.repository.BoardRepository;
import info.thecodinglive.repository.UserRepository;
import info.thecodinglive.service.BoardService;

//게시판 컨트롤러
@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
@Controller
@RequestMapping("/board")
public class PracController {
	
	@Autowired
	BoardService boardService;
	
	@Autowired
	BoardRepository boardRepository;
	
	@Autowired
	UserRepository userRepository;
	
	
	//등록할떄 폼
	@RequestMapping("/form")
	public String Fome() {
		
		return"board/form";
	}
	
	//넣어서 값넣는거
	@RequestMapping(value = "/insert",method = RequestMethod.POST)
	public ResponseEntity<Board> insert(@RequestBody Board board,Model model) {
		
		//유저아이디가져와서 글넣을때 아이디 넣는 코드
		UserDetails user = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		System.out.println("--------->"+user);		
		User2 ur = userRepository.findByUsername(user.getUsername());
		System.out.println("--------->"+ur.toString());
		board.setUsername(ur);	
		
		boardService.save(board);
		System.out.println("=========>"+board);
	
		return new ResponseEntity<Board>(board, HttpStatus.CREATED);
	}
	
	//리스트띄우는거
	@RequestMapping(value = "/list",method = RequestMethod.GET)
	public String list(Model model,@PageableDefault(
			size = 5, sort = "id", direction = Sort.Direction.DESC)
		Pageable pageable
	) {		
		System.out.println("=========>"+pageable);
		model.addAttribute("boardList",boardService.findBoardList(pageable));		
			
		
		UserDetails user = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();			
		User2 ur = userRepository.findByUsername(user.getUsername());
		model.addAttribute("username",ur);
		return "board/list";
	}

	//1개 게시물 보게하는거
	@RequestMapping("/view")
	public String board(Model model, //파람을 가져와서 글번호를 찍는것 기본값을 0 그것을 id로 받음
	@RequestParam(value = "id", defaultValue = "0")Long id) {	
	
	UserDetails user = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	System.out.println("--------->"+user);		
	User2 ur = userRepository.findByUsername(user.getUsername());
	System.out.println("--------->"+ur.toString());
		
	Board board =boardService.findBoardById(id);//이렇게 되야함
	
	
	model.addAttribute("board",board);
	model.addAttribute("username",ur);

	return "board/seeform";	
	}
	
	//1개의 게시물에서 보고 수정하려고 폼으로 가는거
	@RequestMapping("/test")
	public String ud(@RequestParam Long id,Model model) {
		Board bd = boardService.findBoardById(id);
		model.addAttribute("board",bd);
		
		UserDetails user = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();	
		User2 ur = userRepository.findByUsername(user.getUsername());
		model.addAttribute("username",ur);
		
		return "board/form";
	}
	
	//수정처리하는거
	@Transactional//디비에 값을 저장하는것을 간편하게 하는것
	@RequestMapping(value = "/update/{id}",method = RequestMethod.PUT)
	public ResponseEntity<Board> updateBoard(
			@PathVariable Long id, @RequestBody Board reqBoard)
	{
		
		Board board = boardService.findBoardById(id);
		board.setBoardType(reqBoard.getBoardType());
		board.setTitle(reqBoard.getTitle());
		board.setSubTitle(reqBoard.getSubTitle());
		board.setContent(reqBoard.getContent());
		
		return new ResponseEntity<Board>(board, HttpStatus.OK);
	}
	
	//삭제처리하는거
	@RequestMapping(value = "/delete/{id}",method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteBoard(
			@PathVariable Long id){
		try {
		boardService.deleteById(id);
		}catch(Exception e){
			System.out.println("예외처리==>"+e);
		}
		return new ResponseEntity<>("{}", HttpStatus.OK);
	}
	

	//검색
	@RequestMapping(value = "/search")
	   public String search(@RequestParam(value = "keyword") String keyword, Model m,
	         @PageableDefault(size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable p) {
	      PageRequest p2 = PageRequest.of(p.getPageNumber() <= 0 ? 0 : p.getPageNumber() - 1, p.getPageSize(),
	            p.getSort());
	      Page<Board> searchresult = boardRepository.findByTitleContaining(keyword, p2);
	      
	      m.addAttribute("list", searchresult);
	      m.addAttribute("keyword", keyword);
	      return "board/search";
	   }
	
}
