package nz.hmp.tither;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import nz.hmp.tither.entities.Income;
import nz.hmp.tither.repositories.IncomeRepository;

@DataJpaTest
class IncomeRepositoryTest {

    @Autowired
    private IncomeRepository incomeRepository;

    @Test
    void saveIncome() {
        // Create an instance of Income with sample data
        Income income = new Income();
        income.setAmount(1000.0);  // Sample amount

        // Save the income
        incomeRepository.save(income);

        // Check if it was saved successfully
        List<Income> incomes = 
        		incomeRepository.findAll();
        assertEquals(
        		1, incomes.size());
    }

    @Test
    void findById() {
    	// Create an instance of Income with sample data
        Income income = new Income();
        income.setAmount(2000.0);  // Sample amount

        // Save the income
        incomeRepository.save(income);

        // Try to find the saved income by ID
        Optional<Income> foundIncome = 
        		incomeRepository.findById(income.getId());

        // Check if the income was found and has the correct amount
        assertTrue(
        		foundIncome.isPresent());
        assertEquals(
        		2000.0, foundIncome.get().getAmount());
    }
}
