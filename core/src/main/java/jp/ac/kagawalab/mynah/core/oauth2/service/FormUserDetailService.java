package jp.ac.kagawalab.mynah.core.oauth2.service;

import jp.ac.kagawalab.mynah.core.dto.mapper.DtoModelMapper;
import jp.ac.kagawalab.mynah.core.entity.User;
import jp.ac.kagawalab.mynah.core.oauth2.model.FormLoginUser;
import jp.ac.kagawalab.mynah.core.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Component;

@Component
public class FormUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;
    private final DtoModelMapper modelMapper;

    public FormUserDetailService(UserRepository userRepository, DtoModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String user_id) throws UsernameNotFoundException {
        User user = userRepository.findByUserId(user_id);
        if (user == null) throw new UsernameNotFoundException("User not found for login id: " + user_id);
        return new FormLoginUser(user, modelMapper);
    }
}
