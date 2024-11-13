package webapp.entity;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import lombok.Data;

@Data
@Entity
@Table(name="employee_list")
public class EmployeeList {
	/*社員のID*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "employee_id", nullable = false)
	private Integer employeeId;
	
	/*氏名*/
	@Column(name = "employee_name", nullable = false)
	private String employeeName;
	
	/*所属*/
	@Column(name = "office", nullable = false)
	private String office;
	
	/*生年月日*/
	@Column(name = "birthday", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date birthday;
	
	/*パスワード*/
	@Column(name = "password", nullable = false)
	private String password;
	
	@OneToMany(mappedBy = "employee")
    private List<CalcList> calculations;
}
