/**
 * 
 */
package nz.hmp.tither.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import nz.hmp.tither.entities.User;

/**
 * 
 */
public interface UserRepository 
	extends JpaRepository<User, Long> {
	
	User findByUsername(String username);
}
