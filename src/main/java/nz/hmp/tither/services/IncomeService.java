/**
 * 
 */
package nz.hmp.tither.services;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nz.hmp.tither.entities.Income;
import nz.hmp.tither.exception.AppException;
import nz.hmp.tither.exception.NotFoundException;
import nz.hmp.tither.repositories.IncomeRepository;

/**
 * 
 */
@Service
public class IncomeService extends BaseService{

	private static final long serialVersionUID = 4489416661212522099L;
	
	@Autowired
    private IncomeRepository incomeRepository;

    public Income saveIncome(Income income) {
        income = incomeRepository.save(income);
        return income;
    }
    
    public List<Income> retrieveIncomes(Long id) 
    		throws AppException {
    	List<Income> list = id != null ?
    			Stream.of(
    					incomeRepository.findById(id)
    						.orElseThrow(NotFoundException::new))
    							.toList() 
    			: incomeRepository.findAll();
    	return list;
    }

 
}
