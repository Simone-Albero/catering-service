package it.uniroma3.catering.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.catering.model.User;
import it.uniroma3.catering.repository.UserRepository;

@Service
public class UserService {

	@Autowired
    protected UserRepository userRepository;

    @Transactional
    public User findById(Long id) {
        Optional<User> result = this.userRepository.findById(id);
        return result.orElse(null);
    }
    
    @Transactional
    public User findByEmail(String email) {
        Optional<User> result = this.userRepository.findByEmail(email);
        return result.orElse(null);
    }

    @Transactional
    public User save(User user) {
        return this.userRepository.save(user);
    }

    @Transactional
    public List<User> findAll() {
        List<User> result = new ArrayList<>();
        Iterable<User> iterable = this.userRepository.findAll();
        for(User user : iterable)
            result.add(user);
        return result;
    }
}
