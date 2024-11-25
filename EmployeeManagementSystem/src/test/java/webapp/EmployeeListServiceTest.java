package webapp;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.sql.Date;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import webapp.entity.EmployeeList;
import webapp.form.EmployeeListForm;
import webapp.repository.EmployeeListRepository;
import webapp.service.EmployeeListService;

class EmployeeListServiceTest {

    @Mock
    private EmployeeListRepository empRepository;

    @InjectMocks
    private EmployeeListService employeeListService;

    public EmployeeListServiceTest() {
        MockitoAnnotations.openMocks(this); // Mockの初期化
    }

    @Test
    void test006() {
        // テスト用のフォームデータ
        EmployeeListForm form = new EmployeeListForm();
        form.setEmployeeName("John Doe");
        form.setOffice("Engineering");
        form.setBirthday("2000-01-01");
        form.setPassword("password123");

        // リポジトリでの保存処理をモック
        EmployeeList employeeList = new EmployeeList();
        employeeList.setEmployeeName(form.getEmployeeName());
        employeeList.setOffice(form.getOffice());
        employeeList.setBirthday(Date.valueOf(form.getBirthday()));
        employeeList.setPassword(form.getPassword());

        // モックのセットアップ
        when(empRepository.save(any(EmployeeList.class))).thenReturn(employeeList);

        // テスト対象のメソッドを呼び出し
        employeeListService.save(form);

        // 保存メソッドが正しく呼び出されたことを確認
        verify(empRepository, times(1)).save(any(EmployeeList.class));
    }
    
/*
*
    @Test
    void test007() {
        // テスト用データ
        EmployeeList employee = new EmployeeList();
        employee.setEmployeeName("John Doe");
        employee.setOffice("Engineering");
        employee.setBirthday(Date.valueOf("2000-01-01"));
        employee.setPassword("password123");

        when(empRepository.findAll()).thenReturn(List.of(employee));

        List<EmployeeList> result = employeeListService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("John Doe", result.get(0).getEmployeeName());
    }

    @Test
    void testFindAll_NoRecords_ShouldReturnEmptyList() {
        when(empRepository.findAll()).thenReturn(Collections.emptyList());

        List<EmployeeList> result = employeeListService.findAll();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
    *
    */
}
