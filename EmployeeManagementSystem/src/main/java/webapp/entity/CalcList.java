package webapp.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.Data;


@Data
@Entity
@Table(name = "calc_list")
public class CalcList {

		/*電卓ID*/
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(name = "calc_id", nullable = false)
		private Integer calcId;
		
		/*変数1*/
		@Column(name = "val1", nullable = false)
		private BigDecimal val1;
		
		/*四則演算子*/
		@Column(name = "operator")
		private String operator;
		
		/*変数2*/
		@Column(name = "val2", nullable = false)
		private BigDecimal val2;
		
		/*解*/
		@Column(name = "result", nullable = false)
		private BigDecimal result;
		
		/*外部キーの社員ID*/
		@ManyToOne
	    @JoinColumn(name = "employee_id", nullable = false)
	    private EmployeeList employee;
	
}
