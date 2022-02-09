package com.topakkaya.reading.model;

import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.*;

@SpringBootTest(classes = AuthenticationRequest.class)
public class AuthenticationRequestTest {

    AuthenticationRequest request;

    @Before
    public void setUp(){
        request = new AuthenticationRequest();
        request.setEmail("TestEmail");
        request.setPassword("TestPassword");
    }

    @Test
    public void getEmail() {
        assertEquals("TestEmail", request.getEmail());
    }

    @Test
    public void getPassword() {
        assertEquals("TestPassword", request.getPassword());
    }

    @Test
    public void setEmail() {
        request.setEmail("TestEmail2");
        assertEquals("TestEmail2", request.getEmail());
    }

    @Test
    public void setPassword() {
        request.setPassword("TestPassword2");
        assertEquals("TestPassword2", request.getPassword());
    }

    @Test
    public void testEquals() {
        assertEquals(request, request);
    }

    @Test
    public void canEqual() {
        assertTrue(request.canEqual(request));
    }

    @Test
    public void testHashCode() {
        request.hashCode();
    }

    @Test
    public void testToString() {
        assertEquals("AuthenticationRequest(email=TestEmail, password=TestPassword)", request.toString());
    }
}