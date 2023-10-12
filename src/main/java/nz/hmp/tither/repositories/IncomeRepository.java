/**
 * 
 */
package nz.hmp.tither.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import nz.hmp.tither.entities.Income;

/**
 * 
 */
public interface IncomeRepository 
	extends JpaRepository<Income, Long> {
	
}
