package webapp.service;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import webapp.entity.EmployeeList;
import webapp.form.EmployeeListForm;
import webapp.repository.EmployeeListRepository;

@Service
public class EmployeeListService {
	@Autowired
	EmployeeListRepository empRepository;
	
	/*employee_listテーブルの中身検索*/
	
	public List<EmployeeList> findAll(){
		
		/*氏名(employeeName)で検索させる*/
		return empRepository.findAll();
	}
	
	/*employee_listテーブルへの登録*/
	
	public void save(EmployeeListForm employeeListForm) {
		EmployeeList employeeList = new EmployeeList();
		
		/*EmployeeListFormクラスに社員名、所属、生年月日、パスワードをセットする*/
		employeeList.setEmployeeName(employeeListForm.getEmployeeName());
		employeeList.setOffice(employeeListForm.getOffice());
		employeeList.setBirthday(Date.valueOf(employeeListForm.getBirthday()));
		employeeList.setPassword(employeeListForm.getPassword());
		
		/*上の情報をemployee_listに保存*/
		empRepository.save(employeeList);
	}
	
	
	/*ログインチェック*/
	public EmployeeList validateEmployee(String employeeName, String password) {
		
        // 社員名とパスワードで社員を検索
        EmployeeList employee = empRepository.findByEmployeeName(employeeName);

        // パスワードが一致するか確認
        if (employee != null && employee.getPassword().equals(password)) {
            return employee; // 認証成功
        }
        
        return null; 
    }
	
	/*パスワードが重複していないかチェック*/
	public boolean validatepassword(String employeeName, String password) {
        // 社員名とパスワードで社員を検索
        EmployeeList employee = empRepository.findByEmployeeName(employeeName);

        // パスワードがダブってないか確認
        if (employee != null && employee.getPassword().equals(password)) {
            return false; 
        }
        
        return true; 
    }
	
}

