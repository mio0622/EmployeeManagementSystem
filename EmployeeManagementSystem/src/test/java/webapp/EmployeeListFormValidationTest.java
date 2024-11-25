package webapp;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import webapp.form.EmployeeListForm;

class EmployeeListFormValidationTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void test001() {
        EmployeeListForm form = new EmployeeListForm();
        form.setEmployeeName(""); // 空文字
        form.setOffice("");
        form.setBirthday("");
        form.setPassword("");

        Set<ConstraintViolation<EmployeeListForm>> violations = validator.validate(form);
        assertFalse(violations.isEmpty()); // エラーが存在することを確認
    }

    @Test
    void test002() {
        EmployeeListForm form = new EmployeeListForm();
        form.setEmployeeName("a".repeat(20)); // 最大長
        form.setOffice("b".repeat(20));
        form.setBirthday("2000-01-01");
        form.setPassword("pass123");

        Set<ConstraintViolation<EmployeeListForm>> violations = validator.validate(form);
        assertTrue(violations.isEmpty()); // エラーがないことを確認
    }

    @Test
    void test003() {
        EmployeeListForm form = new EmployeeListForm();
        form.setEmployeeName("a".repeat(21)); // 最大長+1
        form.setOffice("b".repeat(21));
        form.setBirthday("2000-01-01");
        form.setPassword("pass123");

        Set<ConstraintViolation<EmployeeListForm>> violations = validator.validate(form);
        assertFalse(violations.isEmpty()); // エラーが存在することを確認
    }

    @Test
    void test004() {
        EmployeeListForm form = new EmployeeListForm();
        form.setEmployeeName("ab"); // 最小長
        form.setOffice("cd");
        form.setBirthday("2000-01-01");
        form.setPassword("abcd");

        Set<ConstraintViolation<EmployeeListForm>> violations = validator.validate(form);
        assertTrue(violations.isEmpty()); // エラーがないことを確認
    }

    @Test
    void test005() {
        EmployeeListForm form = new EmployeeListForm();
        form.setEmployeeName("a"); // 最小長-1
        form.setOffice("b");
        form.setBirthday("2000-01-01");
        form.setPassword("123");

        Set<ConstraintViolation<EmployeeListForm>> violations = validator.validate(form);
        assertFalse(violations.isEmpty()); // エラーが存在することを確認
    }
}
