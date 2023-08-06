package vn.cmax.cafe.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface CustomUserDetailService extends UserDetailsService, UserDetailsPasswordService {
}
