/**
 * 
 */
package nz.hmp.tither.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nz.hmp.tither.entities.User;
import nz.hmp.tither.repositories.UserRepository;

/**
 * 
 */
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
