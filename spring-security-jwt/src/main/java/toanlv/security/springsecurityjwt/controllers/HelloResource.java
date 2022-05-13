package toanlv.security.springsecurityjwt.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import toanlv.security.springsecurityjwt.models.AuthenticationRequest;
import toanlv.security.springsecurityjwt.models.AuthenticationResponse;
import toanlv.security.springsecurityjwt.services.IDemoservice;
import toanlv.security.springsecurityjwt.util.JwtUtil;
import toanlv.security.springsecurityjwt.services.MyUserDetailsService;

@RestController
public class HelloResource {

    @Autowired
    public AuthenticationManager authenticationManager ;

    @Autowired
    private MyUserDetailsService userDetaiService;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @Autowired
    public IDemoservice demoservice;

    @RequestMapping("/hello")
    public String hello() {
        return demoservice.printHello();
    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest)
            throws Exception {
     try {
         authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                 authenticationRequest.getUsername(),authenticationRequest.getPassword()));
     }catch (BadCredentialsException e) {
         throw new Exception("Incorrect User and Password");
     }
     final UserDetails userDetails=userDetaiService.loadUserByUsername(authenticationRequest.getUsername());
     final String  jwt = jwtTokenUtil.generateToken(userDetails);
     return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }
}
