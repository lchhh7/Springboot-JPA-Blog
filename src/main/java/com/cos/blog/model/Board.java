package com.cos.blog.model;
import java.sql.Timestamp;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder //생성자 없이 클래스 생성을 도와줌->매개변수가 여러개 일때, 변수 순서를 착각하지 않고도 편하게 만들수있음
@Entity //클래스를 읽어 테이블을 자동생성
public class Board {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable = false,length = 100)
	private String title;
	
	@Lob //대용량 데이터
	private String content; //섬머노트 라이브러리 <html>태그가 섞여 디자인됨
	
	private int count;
	
	//연관관계설정 , Many = board , User = one
	//이유 :db는 오브젝트를 저장할수없어 fk를 사용한다. 그러나 객체지향 프로그램에선 object가 저장가능하다
	//여기서 db와 자바와 충돌이 생기기 때문에, 자바에서 db에 맞춰 오브젝트를 key값으로 맞춰 저장한다
	//그러나 ORM을 이용하면 오브젝트 ->키변환이 자유롭기 때문에 객체를 넣는다
	@ManyToOne(fetch =FetchType.EAGER)
	@JoinColumn(name="userId") //join컬럼명 지정
	private User user;
	
	@OneToMany(mappedBy = "board",fetch = FetchType.EAGER) //mappedBy 연관관계의 주인이 아니다(FK가 아니니까 DB컬럼을만들지 말라는뜻)
	//JoinColumn 이 필요가 없음 = FK가 필요가 없기 때문에
	private List<Reply> reply;
	
	@CreationTimestamp
	private Timestamp createDate;
}
