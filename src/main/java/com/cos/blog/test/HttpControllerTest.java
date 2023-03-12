package com.cos.blog.test;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// 사용자가 요청->응답(HTML)
// @Controller

//사용자가 요청->응답(Data)
@RestController
public class HttpControllerTest {
	
	private static final String TAG = "HttpControllerTest : ";
	
	@GetMapping("/http/lombok")
	public String lombokTest() {
		Member m = Member.builder().userName("hanseam").password("1234").email("michiguang-i@hanmail.net").build();
		System.out.println(TAG + "getter : " + m.getUserName());
		m.setUserName("babo");
		System.out.println(TAG + "getter : " + m.getUserName());
		return "lombok test 완료";
	}
	
	//인터넷 브라우저 요청은 무조건 get요청밖에 할 수 없다.
	//http://localhost:8081/http/get	
	@GetMapping("/http/get")
	public String getTest(Member m) {
		return "get 요청 : " + m.getId() + ", " + m.getUserName() + ", " + m.getPassword() + ", " + m.getEmail();
	}
	//http://localhost:8081/http/post
	@PostMapping("/http/post")
	public String postTest(@RequestBody Member m) { // MessageConverter(스프링부트)
		return "post 요청 : " + m.getId() + ", " + m.getUserName() + ", " + m.getPassword() + ", " + m.getEmail();
	}
	//http://localhost:8081/http/put
	@PutMapping("/http/put")
	public String putTest(@RequestBody Member m) {
		return "put 요청 : " + m.getId() + ", " + m.getUserName() + ", " + m.getPassword() + ", " + m.getEmail();
	}
	//http://localhost:8081/http/delete
	@DeleteMapping("/http/delete")
	public String deleteTest() {
		return "delete 요청";
	}

}
