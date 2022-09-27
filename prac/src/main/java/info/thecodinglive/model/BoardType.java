package info.thecodinglive.model;

public enum BoardType {
	
	notice("공지사항"),
	free("자유 게시판");
	
	private String value;
	
	private BoardType(String value) {
		this.value=value;
	}
	
	public String getValue() {
		return value;
	}
}
