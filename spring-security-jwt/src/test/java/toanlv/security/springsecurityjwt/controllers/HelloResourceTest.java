package toanlv.security.springsecurityjwt.controllers;

import org.junit.jupiter.api.Test;
import toanlv.security.springsecurityjwt.models.AuthenticationRequest;

import static org.junit.jupiter.api.Assertions.*;

class HelloResourceTest {
    HelloResource helloResource = new HelloResource();
    @Test
    void hello() {

        String result = helloResource.hello();
        assertEquals(result,"Hello word");
    }

    @Test
    void createAuthenticationToken() {
        AuthenticationRequest authenticationRequest = new AuthenticationRequest();
        authenticationRequest.setUsername("foo");
        authenticationRequest.setPassword("foo");
        try {
            String response =  helloResource.createAuthenticationToken(authenticationRequest).toString();
            assertEquals(response.isEmpty(),false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}