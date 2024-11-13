package webapp.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import webapp.entity.EmployeeList;
import webapp.service.CalcListService;
import webapp.service.EmployeeListService;


/*  ↓ただ入力されてきた式を計算してデータベースに保存させてフロントエンドにJSON形式で結果を返すだけのクラス*/

@RestController
@RequestMapping("/api")
public class CalcListController {

    @Autowired
    CalcListService calcService;

    @Autowired
    EmployeeListService empService;

    @GetMapping("/calculate")
    public ResponseEntity<Map<String, Object>> calculate(
    		/*セッションから社員IDとフロントエンドから計算式を受け取る*/
            @SessionAttribute("employeeId") EmployeeList employeeId, 
            @RequestParam String expression) {

        Map<String, Object> response = new HashMap<>();

        /*expressionを分割して val1, operator, val2 に分解*/
        String[] parts = expression.split(" ");
        if (parts.length != 3) {
            response.put("error", "不正な式です");
            return ResponseEntity.badRequest().body(response);
        }
        
       
        String val1str = parts[0];
        String operator = parts[1];
        String val2str = parts[2];

        BigDecimal val1;
        BigDecimal val2;
        BigDecimal result;

        /* String を BigDecimal に変換して計算を行う*/
        try {
            val1 = new BigDecimal(val1str);
            val2 = new BigDecimal(val2str);
            
            /*サービスクラスで計算させる*/
            result = calcService.performCalculation(val1, operator, val2);
            /*0で除算しようとしたり不正な数字が入力された時用*/
        } catch (NumberFormatException e) {
            response.put("error", "数値が不正です");
            return ResponseEntity.badRequest().body(response);
        } catch (ArithmeticException e) {
            response.put("error", "計算エラーです");
            return ResponseEntity.badRequest().body(response);
        }

        /* 計算結果を保存*/
        calcService.saveCalculation(val1, operator, val2, result, employeeId);

        /* 結果をJSON形式で返す*/
        response.put("val1", val1);
        response.put("operator", operator);
        response.put("val2", val2);
        response.put("result", result.setScale(2, RoundingMode.HALF_UP)); /* 小数点以下2桁で表示*/
        return ResponseEntity.ok(response);
    }
}
