package com.cos.blog.test;

import java.util.List;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;


//html파일이 아니라 data를 리턴해주는 controller = @RestController
@RestController
public class DummyControllerTest {
	
	@Autowired // 의존성주입
	private UserRepository userRepository;
	
	// http://localhost:8000/blog/dummy
	@GetMapping("/dummy/user/all")
	public List<User> list() {
		return userRepository.findAll();
	}
	
	// 한페이지당 2건에 데이터를 리턴받아 볼 예정
	@GetMapping("/dummy/user")
	public Page<User> pageList(@PageableDefault(size=2, sort="id", direction=Sort.Direction.DESC) Pageable pageable){
		Page<User> pagingUser = userRepository.findAll(pageable);
		return pagingUser;
	}
	
	
	// {id} 주소로 파라미터를 전달 받을 수 있음.
	// http://localhost:8000/blog/dummy/user/3
	@GetMapping("/dummy/user/{id}")
	public User detail(@PathVariable int id) {
		// user/4를 찾으면 내가 데이터베스에서 못찾아오게 되면 user이 null이 될걸 아냐?
		// 그럼 return null이 리턴이 되잖아 그럼 프로그램에 문제가 있지 않겠니? 
		// 그래서 Optional로 너의 User객체를 감싸서 가져올데니 null인지 아닌지 판단해서 return해!
		User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {
			@Override
			public IllegalArgumentException get() {
				// TODO Auto-generated method stub
				return new IllegalArgumentException("해당 유저는 존재하지 않습니다. id : " + id);
			}
		});
//		람다식 사용 예
//		User user = userRepository.findById(id).orElseThrow(()->{
//			return new IllegalArgumentException("해당 유저는 존재하지 않습니다. id : " + id);
//		});
		
		// 요청:웹브라우저
		// user 객체 = 자바오브젝트
		// 변환(웹브라우저라 이해할 수 있는 데이터) -> json(Gson 라이브러리)
		// 스프링부트 = MessageConverter라는 애가 응답시에 자동 작동
		// 만약에 자바 오브젝트를 리턴하게 되면 MessageConverter가 Jackson라이브러리를 호출해서
		// user 오브젝트를 json으로 변환해서 브라우저에게 던져줌
		return user;
	}
	
	
	// http://localhost:8000/bolg/dummy/join(요청)
	// http의 body에 userName, psssword, email 데이터를 가지고(요청)
	@PostMapping("dummy/join")
	public String Join(User user) { // key=value (약속된 규칙)
		System.out.println("id : " + user.getId());
		System.out.println("userName : " + user.getUserName());
		System.out.println("password : " + user.getPassword());
		System.out.println("email : " + user.getEmail());
		System.out.println("role : " + user.getRole());
		System.out.println("createDate : " + user.getCreateDate());
		
		user.setRole(RoleType.USER);
		userRepository.save(user);
		return "회원가입이 완료되었습니다";
	}
}
