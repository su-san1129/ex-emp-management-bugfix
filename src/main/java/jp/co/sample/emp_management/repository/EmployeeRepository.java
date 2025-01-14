package jp.co.sample.emp_management.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import jp.co.sample.emp_management.domain.Employee;

/**
 * employeesテーブルを操作するリポジトリ.
 * 
 * @author igamasayuki
 * 
 */
@Repository
public class EmployeeRepository {

	/**
	 * Employeeオブジェクトを生成するローマッパー.
	 */
	private static final RowMapper<Employee> EMPLOYEE_ROW_MAPPER = (rs, i) -> {
		Employee employee = new Employee();
		employee.setId(rs.getInt("id"));
		employee.setName(rs.getString("name"));
		employee.setImage(rs.getString("image"));
		employee.setGender(rs.getString("gender"));
		employee.setHireDate(rs.getDate("hire_date"));
		employee.setMailAddress(rs.getString("mail_address"));
		employee.setZipCode(rs.getString("zip_code"));
		employee.setAddress(rs.getString("address"));
		employee.setTelephone(rs.getString("telephone"));
		employee.setSalary(rs.getInt("salary"));
		employee.setCharacteristics(rs.getString("characteristics"));
		employee.setDependentsCount(rs.getInt("dependents_count"));
		return employee;
	};

	@Autowired
	private NamedParameterJdbcTemplate template;

	/**
	 * 従業員一覧情報を入社日順で取得します.
	 * 
	 * @return 全従業員一覧 従業員が存在しない場合はサイズ0件の従業員一覧を返します
	 */
	public List<Employee> findAll() {
		String sql = "SELECT id,name,image,gender,hire_date,mail_address,zip_code,address,telephone,salary,characteristics,dependents_count FROM employees ORDER BY hire_date";

		List<Employee> developmentList = template.query(sql, EMPLOYEE_ROW_MAPPER);

		return developmentList;
	}

	/**
	 * 主キーから従業員情報を取得します.
	 * 
	 * @param id 検索したい従業員ID
	 * @return 検索された従業員情報
	 * @exception 従業員が存在しない場合は例外を発生します
	 */
	public Employee load(Integer id) {
		String sql = "SELECT id,name,image,gender,hire_date,mail_address,zip_code,address,telephone,salary,characteristics,dependents_count FROM employees WHERE id=:id";

		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);

		Employee development = template.queryForObject(sql, param, EMPLOYEE_ROW_MAPPER);

		return development;
	}

	/**
	 * 従業員情報を変更します.
	 */
	public void update(Employee employee) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(employee);

		String updateSql = "UPDATE employees SET dependents_count=:dependentsCount WHERE id=:id";
		template.update(updateSql, param);
	}
	
	/**
	 * 名前で検索して、該当する情報をリストで取得します.
	 * 
	 * @param name 検索したい名前
	 * @return 名前で検索した従業員情報
	 */
	public List<Employee> findByName(String name){
		String sql = "SELECT id, name, image, gender, hire_date, mail_address, zip_code, address, telephone, salary, characteristics, dependents_count FROM employees WHERE name like :name;";
		String escapeName = "%" + name + "%";
		SqlParameterSource param = new MapSqlParameterSource().addValue("name", escapeName);
		List<Employee> employeeList = template.query(sql, param, EMPLOYEE_ROW_MAPPER);
		return employeeList;
	}
	
	/**
	 * 全従業員数を取得.
	 * 
	 * @return 全従業員数
	 */
	public Integer findAllCount() {
		SqlParameterSource param = new MapSqlParameterSource();
		String countSql = "SELECT COUNT(*) FROM employees";
		Integer count = template.queryForObject(countSql,param ,Integer.class);
		return count;
	}
	
	/**
	 * 引数で取得したインデックスから10件刻みで情報を取得する.
	 * 
	 * @param pageIndex 取得したい10件
	 * @return 引数で指定したインデックス*10の10件を返す。
	 */
	public List<Employee> findeAllpageNumberEmployee(Integer pageIndex){
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT id, name, image, gender, hire_date, mail_address, zip_code, address, telephone, salary, characteristics, dependents_count ");
		sql.append("FROM employees ");
		sql.append("ORDER BY hire_date ");
		sql.append("LIMIT 10 OFFSET :pageIndex");
		String paramSql = sql.toString();
		
		if( pageIndex*10 - 11 < 0 ) {
			pageIndex = 0;
		}else {
			pageIndex = pageIndex*10 -11;
		}
		SqlParameterSource param = new MapSqlParameterSource().addValue( "pageIndex", pageIndex);
		List<Employee> employeeList = template.query(paramSql, param, EMPLOYEE_ROW_MAPPER);
		return employeeList;
	}
	
}
