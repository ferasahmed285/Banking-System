import com.Backend.DAO.AccountDAO;
import com.Backend.DAO.ClientDAO;
import com.Backend.DAO.EmployeeDAO;
import com.Backend.DAO.TransactionDAO;
import com.Backend.Database.Data;
import com.Backend.Entities.Account;
import com.Backend.Entities.Client;
import com.Backend.Entities.Employee;
import com.Backend.Entities.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DAOCoverageTest {

    @BeforeEach
    public void setup() {
        Data.clients.clear();
        Data.employees.clear();
        Data.transactions.clear();
        Data.accounts.clear();
    }

    @Test
    public void testClientDAO() {
        Client c = new Client("C Name", "c@test.com", "111", "pass", LocalDate.of(1990, 1, 1));
        ClientDAO.add(c);
        
        assertTrue(ClientDAO.verifyEmail("c@test.com", "pass"));
        assertFalse(ClientDAO.verifyEmail("c@test.com", "wrong"));
        assertFalse(ClientDAO.verifyEmail("wrong@test.com", "pass"));
        
        assertEquals(c, ClientDAO.getClientByEmail("c@test.com"));
        assertNull(ClientDAO.getClientByEmail("nonexistent@test.com"));
        
        assertEquals(c, ClientDAO.getClientById(c.getId()));
        assertNull(ClientDAO.getClientById(-999));
    }

    @Test
    public void testEmployeeDAO() {
        Employee e = new Employee("E Name", "e@test.com", "222", "epass", LocalDate.of(1980, 1, 1), 60000);
        EmployeeDAO.add(e);
        
        assertTrue(EmployeeDAO.verifyEmail("e@test.com", "epass"));
        assertFalse(EmployeeDAO.verifyEmail("e@test.com", "wrong"));
        assertFalse(EmployeeDAO.verifyEmail("wrong@test.com", "epass"));
        
        assertEquals(e, EmployeeDAO.getEmployeeByEmail("e@test.com"));
        assertNull(EmployeeDAO.getEmployeeByEmail("nonexistent@test.com"));
    }

    @Test
    public void testTransactionDAO() {
        Client c = new Client("C", "c", "1", "p", LocalDate.now());
        Client c2 = new Client("D", "d", "2", "p", LocalDate.now());
        
        Transaction t1 = new Transaction(c.getId(), c2.getId(), 100, LocalDate.now(), LocalTime.now(), Transaction.TransactionType.Transfer, Transaction.statusType.Success, "msg");
        TransactionDAO.add(t1);
        
        List<Transaction> list = TransactionDAO.getTransactionsforClient(c);
        assertNotNull(list);
        assertEquals(1, list.size());
        
        List<Transaction> list2 = TransactionDAO.getTransactionsforClient(new Client(-1, "a", "a", "a", "a", LocalDate.now()));
        assertNull(list2);
        
        assertEquals(1, TransactionDAO.getAll().size());
    }

    @Test
    public void testAccountDAO() {
        Client c = new Client("C", "c", "1", "p", LocalDate.now());
        Account a = AccountDAO.getAccountByClient(c);
        assertNotNull(a);
        
        Account a2 = AccountDAO.getAccountByCardNumber(a.getCardNumber());
        assertEquals(a, a2);
        
        assertTrue(AccountDAO.isCardNumberUsed(a.getCardNumber()));
        assertFalse(AccountDAO.isCardNumberUsed("Fake"));
        
        assertNull(AccountDAO.getAccountByCardNumber("Invalid"));
        
        assertTrue(AccountDAO.getAll().size() > 0);
    }
}
