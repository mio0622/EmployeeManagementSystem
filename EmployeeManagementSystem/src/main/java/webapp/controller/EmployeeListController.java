package webapp.controller;

import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import webapp.entity.EmployeeList;
import webapp.form.EmployeeListForm;
import webapp.repository.CalcListRepository;
import webapp.service.EmployeeListService;

@Controller
public class EmployeeListController {
	@Autowired
	EmployeeListService empService;
	
	@Autowired 
	CalcListRepository calcRepository;
	
	
	
	/*ログイン画面表示*/
	@GetMapping("/")
	public String first() {
		return "login";
	}
	
	/*ログイン（氏名とパスワード記入後）*/
	@PostMapping("login")
	public String login(@RequestParam String employeeName, @RequestParam String password, HttpSession session, Model model) {
	    // ログイン処理
	    EmployeeList employee = empService.validateEmployee(employeeName, password);

	    /*入力したパスワードが一致するかのフラグがサービスクラスから帰ってくる*/
	    if (employee != null) {
	        /*一致したのでセッションに社員IDをセットする*/
	        session.setAttribute("employeeId", employee.getEmployeeId());
	        return "redirect:/menu";
	    } else {
	    	/*一致しなかった場合*/
	    	model.addAttribute("ErrorMessage", "氏名もしくはパスワードが一致しません");
	        return "login";
	    }
	}
	
	/*リダイレクトを受け取る*/
	@RequestMapping("/menu")
	public String showmenu() {
		return "menu";
	}
	
	/*新規登録画面に遷移する*/
	@GetMapping("register")
	public String regist(Model model, @ModelAttribute EmployeeListForm employeeListForm) {
		return "form";
	}
	
	/*新規登録ボタン押下で社員登録*/
	@PostMapping("add")
	public String addEmployeeinfo(@ModelAttribute @Validated EmployeeListForm employeeListForm, BindingResult result, Model model,@RequestParam String employeeName, @RequestParam String password) {
		
		/*validatepasswordメソッドを呼び出す*/
		boolean validate = empService.validatepassword(employeeName, password);
		
		/*パスワードが重複してないが、バリデーションに引っかかる場合*/
		if(validate) {
			if(result.hasErrors()) {
				return "form";
		}
			/*正常な入力の場合*/
			empService.save(employeeListForm);
			return "signup";
		}
		/*パスワードが重複している場合*/
		model.addAttribute("ErrorMessage", "パスワードが重複しているため登録できません");
		return "form";
	}
	
	/*戻る押下後*/
	@GetMapping("/login")
	public String returnlogin() {
		/*最初の画面に戻る*/
		return "login";
	}
	
	
	/*四則演算へボタン押下*/
	@GetMapping("calc")
	public String showcalc() {
		return "calc";
	}
	
	
	/*演算履歴閲覧ボタン押下*/
	@GetMapping("history")
	/*セッションにemployee_listの内容をセット*/
	public String showview(HttpSession session) {
		session.setAttribute("emps", empService.findAll() );
		/*履歴閲覧画面で社員名が選択できるようになる*/
		return "calchistory";
	}
	
	/*検索ボタン押下*/
	@GetMapping("search")
	/*フロントエンドから社員IDが送られてくるので受け取る*/
	public String searchhistory(Model model, @RequestParam Integer empId) {
		EmployeeList employee = new EmployeeList();
		employee.setEmployeeId(empId);
		/*ビューに渡す選択された社員のデータをセット*/
		model.addAttribute("calcs", calcRepository.findByEmployee(employee));
		return "calchistory";
	}
	
	
	/*社員情報閲覧ボタン押下*/
	@GetMapping("view")
	public String showemployeelist(Model model) {
		/*ここで登録してある社員情報を出力させる*/
		List<EmployeeList>employeeList = empService.findAll();
		model.addAttribute("EmployeeList", employeeList);
		/*さきほど取り出した社員情報をhtmlファイルに出力できるようにする*/
		return "employee_list";
	}
	
	/*戻る押下後*/
	@GetMapping("back")
	public String backpage() {
		/*最初の画面に戻る*/
		return "menu";
	}
	
	/*ログアウト機能*/
	
	@PostMapping("/logout")
    public String logout(HttpSession session) {
        // セッションからユーザーIDを削除
        session.invalidate();
        return "login";
    }
}
