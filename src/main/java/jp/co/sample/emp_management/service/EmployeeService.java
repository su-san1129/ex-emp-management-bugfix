package jp.co.sample.emp_management.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import jp.co.sample.emp_management.domain.Employee;
import jp.co.sample.emp_management.repository.EmployeeRepository;

/**
 * 従業員情報を操作するサービス.
 * 
 * @author igamasayuki
 *
 */
@Service
@Transactional
public class EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;
	
	/**
	 * 従業員情報を全件取得します.
	 * 
	 * @return　従業員情報一覧
	 */
	public List<Employee> showList() {
		List<Employee> employeeList = employeeRepository.findAll();
		return employeeList;
	}
	
	/**
	 * 従業員情報を取得します.
	 * 
	 * @param id ID
	 * @return 従業員情報
	 * @throws 検索されない場合は例外が発生します
	 */
	public Employee showDetail(Integer id) {
		Employee employee = employeeRepository.load(id);
		return employee;
	}
	
	/**
	 * 従業員情報を更新します.
	 * 
	 * @param employee 更新した従業員情報
	 */
	public void update(Employee employee) {
		employeeRepository.update(employee);
	}
	
	/**
	 * 名前で検索した従業員情報を返します.
	 * 
	 * 引数が空のまま送られてきた場合、全従業員情報を返します。
	 * @param name 従業員名
	 * @return 検索された従業員リスト
	 */
	public List<Employee> findByNameFizzySerch(String name){
		if( "".equals(name)) {
			return employeeRepository.findAll();
		}
		return employeeRepository.findByName(name);
	}
	
	public List<String> employeeNameList(){
		List<String> nameList = new ArrayList<>();
		List<Employee> employeeList = employeeRepository.findAll();
		for (Employee employee : employeeList) {
			nameList.add(employee.getName());
		}
		return nameList;
	}
}
