package info.thecodinglive.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "board")
public class Board implements Serializable{

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(name = "board_id")
		private Long id;
		private String title;
		private String subTitle;
		private String content;

		@Enumerated(EnumType.STRING)
		private BoardType boardType;
		
		private Date wdate;
		@PrePersist
		public void beforeCreate() {
			wdate=new Date();
		}
		@ManyToOne(fetch = FetchType.EAGER)
		private User2 username;
		
		
		
		@Builder
		public Board(String title,String subTitle,String content,
				BoardType boardType,Date wdate,
				 User2 user) {
			this.title=title;
			this.subTitle=subTitle;
			this.content=content;
			this.boardType=boardType;
			this.wdate=wdate;
			this.username=user;
		}
		

	}