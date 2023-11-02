/**
 * 
 */
package nz.hmp.tither.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import nz.hmp.tither.entities.Income;
import nz.hmp.tither.exception.AppException;
import nz.hmp.tither.exception.NotFoundException;
import nz.hmp.tither.services.IncomeService;

/**
 * 
 */
@RestController
public class IncomeController extends BaseController{

	private static final long serialVersionUID = -4154770591389875295L;
	
	@Autowired
    private IncomeService incomeService;
	
//	@ApiOperation(value = "${swagger.tither.index}")
	@GetMapping("")
	public String index() {
//		log.debug("->index");
		
		return this.getClass()
				.getSimpleName()
				.replace("Controller", "")
				.toLowerCase();
	}

    @PostMapping("/income")
    public ResponseEntity<Income> saveIncome(
    		@RequestBody Income income) {
    	logger.info("saveIncome");
    	HttpStatus status = HttpStatus.CREATED;
//    	boolean success = false;
    	
    	try {
	    	income = incomeService.saveIncome(income);
//	    	success = income != null 
//	    			&& income.getId() != null ;
    	
    	} catch (Exception e) {
    		status = HttpStatus.INTERNAL_SERVER_ERROR;
    		income = null;
    		logger.error(e.getMessage(), e);
    	}
    	
        return new ResponseEntity<>(income, status);
    }
    
    @GetMapping(value = {"/income", "/income/{id}"})
    public List<Income> retrieveIncomes(
    		@PathVariable(name = "id", required = false) Long id) 
    				throws AppException {
    	logger.info("retrieveIncomes");
    	List<Income> lista = null;
		try {
			lista = incomeService.retrieveIncomes(id);
		} catch (AppException e) {
			if (!(e instanceof NotFoundException)) {
				logger.error(e.getMessage(), e);
			} else {
				logger.info("Register not found!");
			}
			
			throw e;
		}
    	
    	return lista;
    }    
}
