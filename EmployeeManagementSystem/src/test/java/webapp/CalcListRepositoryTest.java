package webapp;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import webapp.entity.CalcList;
import webapp.entity.EmployeeList;
import webapp.repository.CalcListRepository;
import webapp.service.EmployeeListService;

class CalcListRepositoryTest {

    @Mock
    private CalcListRepository calcListRepository;

    @InjectMocks
    private EmployeeListService employeeListService;

    private EmployeeList employee;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // テスト用の社員データを作成
        employee = new EmployeeList();
        employee.setEmployeeId(1);
        employee.setEmployeeName("John Doe");
        employee.setOffice("Engineering");
        employee.setPassword("password123");
    }

    @Test
    void test007() {
        // モックデータを作成
        CalcList calc1 = new CalcList();
        calc1.setCalcId(1);
        calc1.setVal1(BigDecimal.valueOf(10));
        calc1.setOperator("+");
        calc1.setVal2(BigDecimal.valueOf(20));
        calc1.setResult(BigDecimal.valueOf(30));
        calc1.setEmployee(employee);

        CalcList calc2 = new CalcList();
        calc2.setCalcId(2);
        calc2.setVal1(BigDecimal.valueOf(15));
        calc2.setOperator("*");
        calc2.setVal2(BigDecimal.valueOf(3));
        calc2.setResult(BigDecimal.valueOf(45));
        calc2.setEmployee(employee);

        // モックの振る舞いを定義
        when(calcListRepository.findByEmployee(any(EmployeeList.class))).thenReturn(Arrays.asList(calc1, calc2));

        // メソッドを実行
        List<CalcList> results = calcListRepository.findByEmployee(employee);

        // 結果を検証
        assertEquals(2, results.size());
        assertEquals(calc1.getCalcId(), results.get(0).getCalcId());
        assertEquals(calc2.getCalcId(), results.get(1).getCalcId());
        assertEquals(BigDecimal.valueOf(30), results.get(0).getResult());
        assertEquals(BigDecimal.valueOf(45), results.get(1).getResult());
    }
}
