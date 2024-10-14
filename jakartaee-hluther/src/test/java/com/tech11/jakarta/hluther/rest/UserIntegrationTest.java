package com.tech11.jakarta.hluther.rest;

import java.time.LocalDate;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.google.gson.reflect.TypeToken;
import com.tech11.jakarta.hluther.dto.UserDto;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import jakarta.ws.rs.core.Response.Status;

public class UserIntegrationTest {

	private static final String LOCAL_SERVER_BASE_URL = "http://localhost:8080/jakartaee-hluther/rest";

	@Test
	public void getAllUserTest() {
		String url = LOCAL_SERVER_BASE_URL + "/user/list";
		Response response=RestAssured.get(url);		
		System.out.println(response.asPrettyString());
		Assert.assertEquals(response.getStatusCode(),Status.OK.getStatusCode());
		List<UserDto> responseDtos = GsonUtil.fromJson(response.asPrettyString(),  new TypeToken<List<UserDto>>() {
		}.getType());
		Assert.assertTrue(responseDtos.size()>0);
		Assert.assertTrue(responseDtos.get(0).getFirstname().equals("Henning"));
	}
	
	@Test
	public void getUserTest() {
		String url = LOCAL_SERVER_BASE_URL + "/user/1";
		Response response=RestAssured.get(url);		
		System.out.println(response.asPrettyString());
		Assert.assertEquals(response.getStatusCode(),Status.OK.getStatusCode());
		UserDto responseDto = GsonUtil.fromJson(response.asPrettyString(),  UserDto.class);
		Assert.assertTrue(responseDto.getFirstname().equals("Henning"));
	}
	
	@Test
	public void getUserBadIdTest() {
		String url = LOCAL_SERVER_BASE_URL + "/user/1000000000";
		Response response=RestAssured.get(url);		
		System.out.println(response.asPrettyString());
		Assert.assertEquals(response.getStatusCode(),Status.NOT_FOUND.getStatusCode());
	}
	
	@Test
	public void createUserTest() {
		String url = LOCAL_SERVER_BASE_URL + "/user/create";
		UserDto newUser = new UserDto(null, "Peter", "Lustig", "plustig@gmail.com", LocalDate.parse("1960-06-07"), "password");
		Response response=RestAssured.given().body(GsonUtil.toJson(newUser)).contentType(ContentType.JSON).post(url);		
		System.out.println(response.asPrettyString());
		Assert.assertEquals(response.getStatusCode(),Status.CREATED.getStatusCode());
		
		UserDto responseDto = GsonUtil.fromJson(response.asPrettyString(),  UserDto.class);
		Assert.assertTrue(responseDto.getFirstname().equals("Peter"));
	}
	
	@Test
	public void createUserBadIdTest() {
		String url = LOCAL_SERVER_BASE_URL + "/user/create";
		UserDto newUser = new UserDto(1l, "Peter", "Lustig", "plustig@gmail.com", LocalDate.parse("1960-06-07"), "password");
		Response response=RestAssured.given().body(GsonUtil.toJson(newUser)).contentType(ContentType.JSON).post(url);		
		System.out.println(response.statusLine());
		Assert.assertEquals(response.getStatusCode(),Status.BAD_REQUEST.getStatusCode());

	}
	
	@Test
	public void updateUserTest() {
		//first create fresh one
		String createUrl = LOCAL_SERVER_BASE_URL + "/user/create";
		UserDto newUser = new UserDto(null, "Update", "User", "uuser@gmail.com", LocalDate.parse("1990-06-07"), "password");
		Response createResponse=RestAssured.given().body(GsonUtil.toJson(newUser)).contentType(ContentType.JSON).post(createUrl);		
		System.out.println(createResponse.asPrettyString());
		Assert.assertEquals(createResponse.getStatusCode(),Status.CREATED.getStatusCode());
		
		UserDto createResponseDto = GsonUtil.fromJson(createResponse.asPrettyString(),  UserDto.class);
		Assert.assertEquals(createResponseDto.getFirstname(),"Update");
		
		
		//update the fersh created one
		String updateUrl = LOCAL_SERVER_BASE_URL + "/user/update";
		createResponseDto.setFirstname("Updated");
		createResponseDto.setBirthday(LocalDate.parse("1992-06-07"));
		
		Response updateResponse=RestAssured.given().body(GsonUtil.toJson(createResponseDto)).contentType(ContentType.JSON).put(updateUrl);		
		System.out.println(updateResponse.asPrettyString());
		Assert.assertEquals(updateResponse.getStatusCode(),200);
		UserDto updateResponseDto = GsonUtil.fromJson(updateResponse.asPrettyString(),  UserDto.class);
		Assert.assertEquals(updateResponseDto.getFirstname(),"Updated");
		Assert.assertEquals(updateResponseDto.getBirthday(),LocalDate.parse("1992-06-07"));
	}
}
