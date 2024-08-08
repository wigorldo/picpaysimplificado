package com.picpaysimplificado;

import com.picpaysimplificado.services.UserService;
import com.picpaysimplificado.user.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@SpringBootTest
class PicpaysimplificadoApplicationTests {
	@Autowired
	private UserService userService;

	@Test
	void contextLoads() {
	}

}
