package webapp.service;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import webapp.entity.CalcList;
import webapp.entity.EmployeeList;
import webapp.repository.CalcListRepository;

@Service
public class CalcListService {

    @Autowired
    CalcListRepository calcRepository;
    
    /* 受け取ったoperator(四則演算子)がどれかによって処理を変える */
    public BigDecimal performCalculation(BigDecimal val1, String operator, BigDecimal val2) {
        switch (operator) {
            case "+":
                return val1.add(val2);
            case "-":
                return val1.subtract(val2);
            case "*":
                return val1.multiply(val2);
            case "/":
                if (val2.compareTo(BigDecimal.ZERO) == 0) {
                    throw new ArithmeticException("ゼロで割ることはできません");
                }
               /*小数点以下10桁まで計算し、四捨五入する*/
                return val1.divide(val2, 10, RoundingMode.HALF_UP);
            default:
                throw new IllegalArgumentException("不明な演算子: " + operator);
        }
    }
    
    /* 実際に計算した結果をデータ保存させるメソッド */
    public void saveCalculation(BigDecimal val1, String operator, BigDecimal val2, BigDecimal result, EmployeeList employeeId) {
        // 新しいCalcListエンティティを作成
        CalcList calc = new CalcList();
        /*計算式と結果と社員ID(employeeId)をセット*/
        calc.setVal1(val1);  
        calc.setOperator(operator);
        calc.setVal2(val2);
        calc.setResult(result);
        calc.setEmployee(employeeId); /* EmployeeListエンティティを保存*/

        /* リポジトリを通じてデータベースに保存*/
        calcRepository.save(calc);
    }
}
