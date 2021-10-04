package jp.co.sss.crud.util;

/**
 * SQL文の管理用クラス
 */
public class ConstantSQL {
	/** SQL文(全件検索) */
	public static String SQL_FIND_ALL = "SELECT emp_id, emp_name, gender, TO_CHAR(birthday, 'YYYY/MM/DD') AS birthday, dept_name FROM employee e INNER JOIN department d ON e.dept_id = d.dept_id ORDER BY emp_id ASC";

	/** SQL文(登録) */
	public static String SQL_INSERT = "INSERT INTO employee VALUES(seq_emp.NEXTVAL, ?, ?, ?, ?)";
	 
	/** SQL文（削除）*/
	public static String SQL_DELETE ="DELETE FROM employee WHERE emp_id = ?";
	
	/** SQL文（社員名検索）*/
	public static String SQL_SELECT_EMPNAME ="SELECT emp_name, emp_id, gender, TO_CHAR(birthday,'YYYY/MM/DD') AS birthday, dept_name FROM employee e INNER JOIN department d ON e.dept_id = d.dept_id WHERE emp_name LIKE ?";

	/** SQL文（部署ID検索）*/
	public static String SQL_SELECT_DEPTID ="SELECT e.dept_id, emp_id, emp_name, gender, TO_CHAR(birthday,'YYYY/MM/DD') AS birthday, dept_name FROM employee e INNER JOIN department d ON e.dept_id = d.dept_id WHERE e.dept_id = ?";

	/** SQL文（検索） */
	public static String SQL_SELECT = "SELECT * FROM employee WHERE dept_id = ?";
	
}
