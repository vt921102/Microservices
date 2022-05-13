package toanlv.security.springsecurityjwt.services;

import org.springframework.stereotype.Service;

@Service
public class DemosevicesImpl implements IDemoservice{
    @Override
    public String printHello() {
        return ("Hello ServiceDemo");
    }
}
