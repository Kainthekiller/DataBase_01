package com.example.demo;

import com.example.demo.Repo.NickRepo;
import com.example.demo.Repo.PeopleRepo;
import com.example.demo.model.People;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import javax.transaction.Transactional;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class DemoApplicationTests {

	@Autowired
	MockMvc mvc;

	@Autowired
	NickRepo repo;
	@Autowired
	PeopleRepo repo1;

	@Test
	@Transactional
	@Rollback
	public void testCreated() throws Exception
	{
		MockHttpServletRequestBuilder request = post("/people")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"firstName\": \"Chloe\"}");

		this.mvc.perform(request)
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id",instanceOf(Number.class)));
	}

	@Test
	@Transactional
	@Rollback
	public void testList() throws Exception {
		People person = new People();
		person.setFirstName("Lou");
		repo1.save(person);

		MockHttpServletRequestBuilder request = get("/people")
				.contentType(MediaType.APPLICATION_JSON);

		this.mvc.perform(request)
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[6].id", equalTo(person.getId().intValue()) ));
	}


}
