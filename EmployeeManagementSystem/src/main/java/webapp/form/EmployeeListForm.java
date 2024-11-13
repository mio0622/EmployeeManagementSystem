package webapp.form;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import lombok.Data;

@Data
public class EmployeeListForm {
	/*氏名*/
	@Size(min=2,max=20)
	@NotEmpty
	private String employeeName;
	
	/*所属*/
	@Size(min=2, max=20)
	@NotEmpty
	private String office;
	
	/*生年月日*/
	@NotEmpty
	private String birthday;
	
	/*パスワード*/
	@NotEmpty
	@Size(min=4, max=16)
	private String password;
}
