import com.Backend.Entities.Account;
import com.Backend.Entities.Client;
import com.Backend.Entities.Employee;
import com.Backend.Entities.Transaction;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

public class EntityCoverageTest {

    @Test
    public void testClientGettersAndSetters() {
        LocalDate dob = LocalDate.of(1990, 1, 1);
        Client client = new Client("John Doe", "john@test.com", "1234567890", "Pass123!", dob);
        
        assertEquals("John Doe", client.getName());
        assertEquals("john@test.com", client.getEmail());
        assertEquals("1234567890", client.getPhone());
        assertEquals("Pass123!", client.getPassword());
        assertEquals(dob, client.getDOB());
        
        // ID is auto-incremented, so just ensure it doesn't throw
        assertTrue(client.getId() >= 0);
        
        client.setId(100);
        assertEquals(100, client.getId());
        client.setName("Alice");
        assertEquals("Alice", client.getName());
        client.setEmail("alice@test.com");
        assertEquals("alice@test.com", client.getEmail());
        client.setPhone("111");
        assertEquals("111", client.getPhone());
        client.setPassword("NewPass");
        assertEquals("NewPass", client.getPassword());
        client.setDOB(LocalDate.of(2000, 1, 1));
        assertEquals(LocalDate.of(2000, 1, 1), client.getDOB());
        
        Client client2 = new Client(50, "Bob", "bob@test.com", "222", "Pass", dob);
        client2.update("Bobby", "bobby@test.com", "333", "New", dob);
        assertEquals("Bobby", client2.getName());
    }

    @Test
    public void testEmployeeGettersAndSetters() {
        Employee employee = new Employee("Admin", "admin@test.com", "0987654321", "AdminPass!", LocalDate.of(1990, 1, 1), 50000.0);
        
        assertEquals("Admin", employee.getName());
        assertEquals("admin@test.com", employee.getEmail());
        assertEquals("0987654321", employee.getPhone());
        assertEquals("AdminPass!", employee.getPassword());
        assertEquals(50000.0, employee.getSalary());
        employee.setSalary(60000.0);
        assertEquals(60000.0, employee.getSalary());
        assertTrue(employee.getId() >= 0);
    }

    @Test
    public void testTransactionGetters() {
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();
        Transaction transaction = new Transaction(1, 2, 500.0, date, time, 
                Transaction.TransactionType.Transfer, Transaction.statusType.Success, "Test Msg");
        
        assertEquals(1, transaction.getSenderId());
        assertEquals(2, transaction.getReceiverId());
        assertEquals(500.0, transaction.getAmount());
        assertEquals(date, transaction.getDate());
        assertEquals(time, transaction.getTime());
        assertEquals(Transaction.TransactionType.Transfer, transaction.getType());
        assertEquals(Transaction.statusType.Success, transaction.getStatus());
        assertEquals("Test Msg", transaction.getMessage());
        assertTrue(transaction.getId() >= 0);
    }

    @Test
    public void testAccountEdgeCases() {
        Account acc = new Account(1);
        assertEquals(1, acc.getClientId());
        assertNotNull(acc.getCardNumber());
        assertEquals(0.0, acc.getTIncome());
        assertEquals(0.0, acc.getTExpenses());
        assertTrue(acc.getId() >= 0);
        
        // Test transfer to a non-existent account or invalid state
        acc.setStatus(Account.AccountStatus.Verified);
        acc.deposit(100);
        
        // Transfer to non-existent account should return false
        assertFalse(acc.transfer("0000 0000 0000 0000", 50, "Fake"));
    }
}
