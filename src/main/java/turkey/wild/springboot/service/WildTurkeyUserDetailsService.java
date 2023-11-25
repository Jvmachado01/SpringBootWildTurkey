package turkey.wild.springboot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import turkey.wild.springboot.repository.WildTurkeyUserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WildTurkeyUserDetailsService implements UserDetailsService {
    private final WildTurkeyUserRepository wildTurkeyUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        return Optional.ofNullable(wildTurkeyUserRepository.findByUsername(username))
                .orElseThrow(() -> new UsernameNotFoundException("Wild user not found"));
    }
}
