package com.springsecurity.tuto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.springsecurity.tuto.controller.LoginController;


@SpringBootTest
class TutoApplicationTests {
	@Autowired
	LoginController controller;
	@Test
	void contextLoads() {
		assertThat(controller).isNotNull();
	}

}
