package webapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import webapp.entity.CalcList;
import webapp.entity.EmployeeList;

public interface CalcListRepository extends JpaRepository<CalcList, Integer>{
	/*フロントエンドのプルダウンで氏名が選択されるので、選択された氏名で検索する*/
	List<CalcList> findByEmployee(EmployeeList name);
}
