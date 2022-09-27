package info.thecodinglive.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import info.thecodinglive.model.Board;
import info.thecodinglive.repository.BoardRepository;

@Service
public class BoardService {
	
	private final BoardRepository boardRepository;
	
	public BoardService(BoardRepository boardRepository) {
		this.boardRepository=boardRepository;
	}
	
	public void save(Board board) {
		boardRepository.save(board);
	}
	
	public Board findBoardById(Long id) {
		return boardRepository.findById(id).orElse(new Board());
	}
	
	public Page<Board> findBoardList(Pageable pageable){
		pageable =PageRequest.of(pageable.getPageNumber()<=0?0:pageable.getPageNumber()-1
				,pageable.getPageSize(), pageable.getSort());
			//삼항연산자를 이용 /페이지번호,페이지사이즈,페이지 정렬방식
			//시작페이지가 1이여야함으로 컴터가 인식하도록 첫페이지를 0으로 해주는 삼항연산
			//0일경우 0으로 두고 아니면 -1을 해주자.		
			return boardRepository.findAll(pageable);
	}
	
	public void deleteById(Long id) {
		boardRepository.deleteById(id);
	}
	
	
}
