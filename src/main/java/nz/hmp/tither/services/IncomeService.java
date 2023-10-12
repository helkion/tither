/**
 * 
 */
package nz.hmp.tither.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nz.hmp.tither.entities.Income;
import nz.hmp.tither.repositories.IncomeRepository;

/**
 * 
 */
@Service
public class IncomeService{
    @Autowired
    private IncomeRepository incomeRepository;

    public void saveIncome(Income income) {
        incomeRepository.save(income);
    }

 
}
