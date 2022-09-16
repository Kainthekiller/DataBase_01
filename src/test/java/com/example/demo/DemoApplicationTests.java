package com.example.demo;

import com.example.demo.Repo.NickRepo;
import com.example.demo.Repo.PeopleRepo;
import com.example.demo.model.Nick;
import com.example.demo.model.People;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.catalina.filters.ExpiresFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.transaction.Transactional;

import java.time.LocalDate;
import java.util.HashMap;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class DemoApplicationTests {

	ObjectMapper objectMapper = new ObjectMapper();
	Nick nick = new Nick();
	Nick nick1 = new Nick();
	@Autowired
	MockMvc mvc;

	@Autowired
	NickRepo repo;
	@Autowired
	PeopleRepo repo1;



	@BeforeEach
	public void setUp()
	{


		nick.setDate(LocalDate.of(2022,1, 1));
		nick.setTitle("One");
		nick1.setDate(LocalDate.now());
		nick1.setTitle("Two");
	}


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
				.andExpect(jsonPath("$.id",instanceOf(Number.class)))
				.andExpect(jsonPath("$.firstName").value("Chloe"));
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

	@Test
	@Transactional
	@Rollback
	public  void getAllReturnsArrayWith() throws Exception
	{
		MockHttpServletRequestBuilder request = get("/Nick");

		this.repo.save(nick);
		this.repo.save(nick1);

		this.mvc.perform(request)
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].title").value("One"))
				.andExpect(jsonPath("$[1].id").value(nick1.getId().intValue()))
				.andExpect(jsonPath("$[1].title").value("Two"));



	}

	@Test
	@Transactional
	@Rollback
	public void postItems() throws Exception
	{
		this.repo.save(nick);
		HashMap<String, String> body = new HashMap<String, String>();
		body.put("title", "Nick Title");
		body.put("date" , "2021-08-10");
		String json = objectMapper.writeValueAsString(body);
		MockHttpServletRequestBuilder request = post("/Nick")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json);

		this.mvc.perform(request)
				.andExpect(jsonPath("$.title").value("Nick Title"))
				.andExpect(jsonPath("$.date").value("2021-08-10"));

		MockHttpServletRequestBuilder requestNicky = get("/Nick");
		this.mvc.perform(requestNicky)
				.andExpect(jsonPath("$[1].title").value("Nick Title"))
				.andExpect(jsonPath("$[0].title").value("One"))
				.andExpect(jsonPath("$[0].date").value("2022-01-01"));

	}


	//Delete
	@Test
	@Transactional
	@Rollback
	public void deleteItem() throws Exception
	{
		this.repo.save(nick);
		MockHttpServletRequestBuilder deleteRequest = delete(String.format("/Nick/%d", nick.getId()));

		this.mvc.perform(deleteRequest)
				.andExpect(status().isNoContent());

	}


}
