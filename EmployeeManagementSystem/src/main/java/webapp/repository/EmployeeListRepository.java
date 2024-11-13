package webapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import webapp.entity.EmployeeList;

public interface EmployeeListRepository extends JpaRepository<EmployeeList, Integer>{
	/*ログイン時に氏名で検索をする*/
	EmployeeList findByEmployeeName(String name);
}
