package jp.co.sss.crud.db;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jp.co.sss.crud.util.ConstantSQL;

/**
 * データベース操作用クラス
 */
public class DBController {
	/**
	 * 全件表示
	 *
	 * @throws ClassNotFoundException
	 *             ドライバクラスが存在しない場合に送出
	 * @throws SQLException
	 *             データベース操作時にエラーが発生した場合に送出
	 */
	public static void findAll() throws ClassNotFoundException, SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			// DBに接続
			connection = DBManager.getConnection();

			// ステートメントを作成
			preparedStatement = connection.prepareStatement(ConstantSQL.SQL_FIND_ALL);

			// SQL文を実行
			resultSet = preparedStatement.executeQuery();

			// レコードの行数を数えるための変数を用意
			int rowCount = 0;

			// レコードを出力
			System.out.println("社員ID\t社員名\t性別\t生年月日\t部署名");
			while (resultSet.next()) {
				System.out.print(resultSet.getString("emp_id") + "\t");
				System.out.print(resultSet.getString("emp_name") + "\t");

				int gender = Integer.parseInt(resultSet.getString("gender"));
				if (gender == 1) {
					System.out.print("男性\t");
				} else if (gender == 2) {
					System.out.print("女性\t");
				}

				System.out.print(resultSet.getString("birthday") + "\t");
				System.out.println(resultSet.getString("dept_name"));

				rowCount++;
			}

			System.out.println("");
		} finally {
			// ResultSetをクローズ
			DBManager.close(resultSet);
			// Statementをクローズ
			DBManager.close(preparedStatement);
			// DBとの接続を切断
			DBManager.close(connection);
		}
	}

	/**
	 * 登録
	 *
	 * @param empName
	 *            社員名
	 * @param gender
	 *            性別
	 * @param birthday
	 *            生年月日
	 * @param deptId
	 *            部署ID
	 * @throws ClassNotFoundException
	 *             ドライバクラスが存在しない場合に送出
	 * @throws SQLException
	 *             データベース操作時にエラーが発生した場合に送出
	 */
	public static void insert(String empName, String gender, String birthday, String deptId)
			throws ClassNotFoundException, SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			// DBに接続
			connection = DBManager.getConnection();

			// ステートメントを作成
			preparedStatement = connection.prepareStatement(ConstantSQL.SQL_INSERT);

			// 入力値をバインド
			preparedStatement.setString(1, empName);
			preparedStatement.setString(2, gender);
			preparedStatement.setString(3, birthday);
			preparedStatement.setString(4, deptId);

			// SQL文を実行
			preparedStatement.executeUpdate();

			// 登録完了メッセージを出力
			System.out.println("社員情報を登録しました");
		} finally {
			DBManager.close(preparedStatement);
			DBManager.close(connection);
		}
	}
	
	/**
	 * 更新
	 * @param empId
	 *            社員ID
	 * @param empName
	 *            社員名
	 * @param gender
	 *            性別
	 * @param birthday
	 *            生年月日
	 * @param deptId
	 *            部署ID
	 * @throws ClassNotFoundException
	 *             ドライバクラスが存在しない場合に送出
	 * @throws SQLException
	 *             データベース操作時にエラーが発生した場合に送出
	 */
	public static synchronized void update() 
			throws ClassNotFoundException, SQLException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			//更新したいレコードの主キーを入力
			System.out.println("社員 ID を入力してください:");
			String empId = br.readLine();
			//更新する値を入力
			System.out.println("社員名:");
			String empName = br.readLine();
			System.out.println("性別（男性：1、女性：2):");
			String gender = br.readLine();
			System.out.println("生年月日（西暦年/月/日):");
			String birthday = br.readLine();
			System.out.println("部署ID（1:営業部、2:経理部、3:総務部）:");
			String deptId = br.readLine();
			System.out.println("社員情報を更新しました:");
			//DB に接続
			connection = DBManager.getConnection();
			//SQL 文を準備
			String sql = "UPDATE employee SET emp_name = ?, gender = ?, birthday = ? ,dept_id = ? WHERE emp_id = ?";
			//ステートメントを作成
			preparedStatement = connection.prepareStatement(sql);
			//入力値をバインド
			preparedStatement.setString(5, empId);
			preparedStatement.setString(1, empName);
			preparedStatement.setString(2, gender);
			preparedStatement.setString(3, birthday);
			preparedStatement.setString(4, deptId);
			//SQL 文を実行
			preparedStatement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//クローズ処理
			DBManager.close(preparedStatement);
			DBManager.close(connection);
		}
	}

	//値が入力された列のみ更新
	//	public static synchronized void update(String empId, String empName, String gender, String birthday, String deptId)
	//			throws ClassNotFoundException, SQLException {
	//		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	//		Connection connection = null;
	//		PreparedStatement preparedStatement = null;
	//		ResultSet resultSet = null;
	//		try {
	//			//更新したいレコードの主キーを入力
	//			System.out.println("社員 ID を入力してください:");
	//		    empId = br.readLine();
	//			//検索メゾッドの呼び出し
	//		    select();
	//			
	//			
	//			//更新する値を入力
	//			System.out.println("社員名:");
	//			empName = br.readLine();
	//			System.out.println("性別（男性：1、女性：2):");
	//			gender = br.readLine();
	//			System.out.println("生年月日（西暦年/月/日):");
	//			birthday = br.readLine();
	//			System.out.println("部署ID（1:営業部、2:経理部、3:総務部）:");
	//			deptId = br.readLine();
	//			System.out.println("社員情報を更新しました:");
	//
	//			//ステートメントを作成）
	//			preparedStatement = connection.prepareStatement(ConstantSQL.SQL_UPDATE);
	//			
	//			// SQL文を実行
	//			resultSet = preparedStatement.executeQuery();
	//			
	//			//入力値をバインド
	//			preparedStatement.setString(5, empId);
	//			preparedStatement.setString(1, empName);
	//			preparedStatement.setString(2, gender);
	//			preparedStatement.setString(3, birthday);
	//			preparedStatement.setString(4, deptId);
	//			
	//			
	//			//前の値と今の値を比較
	//			
	//			
	//			
	//			
	//			
	//			//SQL 文を実行
	//			int cnt = preparedStatement.executeUpdate();
	//		} catch (Exception e) {
	//			e.printStackTrace();
	//		} finally {
	//			//クローズ処理
	//			DBManager.close(preparedStatement);
	//			DBManager.close(connection);
	//		}
	//	}
	//	
	//
	//	public static void select(String deptId)
	//			throws ClassNotFoundException, SQLException {
	//		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	//		Connection connection = null;
	//		PreparedStatement preparedStatement = null;
	//		ResultSet resultSet = null;
	//
	//		try {
	//			//DB に接続
	//			connection = DBManager.getConnection();
	//			//ステートメントを作成（SQL文から返ってくる値の数は5つ）
	//			preparedStatement = connection.prepareStatement(ConstantSQL.SQL_SELECT);
	//			//入力値をバインド
	//			preparedStatement.setString(1, deptId);
	//			//SQL 文を実行
	//			resultSet = preparedStatement.executeQuery();
	//
	//		} catch (Exception e) {
	//			e.printStackTrace();
	//		} finally {
	//			//クローズ処理
	//			DBManager.close(resultSet);
	//			DBManager.close(preparedStatement);
	//			DBManager.close(connection);
	//		}
	//
	//	}
	//	
	//	
	//	
	
	
	/**
	 * 削除
	 *
	 * @param empId
	 *            社員ID
	 * @throws ClassNotFoundException
	 *             ドライバクラスが存在しない場合に送出
	 * @throws SQLException
	 *             データベース操作時にエラーが発生した場合に送出
	 */
	public static void delete(String empId)
			throws ClassNotFoundException, SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			//DB に接続
			connection = DBManager.getConnection();
			//ステートメントを作成
			preparedStatement = connection.prepareStatement(ConstantSQL.SQL_DELETE);
			//入力値をバインド
			preparedStatement.setString(1, empId);
			//SQL 文を実行
			preparedStatement.executeUpdate();
			//削除完了メッセージを出力
			System.out.println("社員情報を削除しました。");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//クローズ処理
			DBManager.close(preparedStatement);
			DBManager.close(connection);
		}
	}

	/**
	 * 社員名検索
	 *
	 * @param empName
	 *            社員名
	 * @throws ClassNotFoundException
	 *             ドライバクラスが存在しない場合に送出
	 * @throws SQLException
	 *             データベース操作時にエラーが発生した場合に送出
	 */
	public static void selectByEmpName(String empName)
			throws ClassNotFoundException, SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			//DB に接続
			connection = DBManager.getConnection();
			//ステートメントを作成（SQL文から返ってくる値の数は5つ）
			preparedStatement = connection.prepareStatement(ConstantSQL.SQL_SELECT_EMPNAME);
			//入力値をバインド
			preparedStatement.setString(1, "%" + empName + "%");
			//SQL 文を実行
			resultSet = preparedStatement.executeQuery();
			// resultSetの中身を数えるための変数を用意
			int count = 0;

			// 検索結果を出力
			System.out.println("社員ID\t社員名\t性別\t生年月日\t部署名");
			while (resultSet.next()) {
				System.out.print(resultSet.getString("emp_id")  + "\t"+ "\t");
				System.out.print(resultSet.getString("emp_name")  + "\t");
				int gender = Integer.parseInt(resultSet.getString("gender"));
				if (gender == 1) {
					System.out.print("男性\t");
				} else if (gender == 2) {
					System.out.print("女性\t");
				}
				System.out.print(resultSet.getString("birthday") + "\t");
				System.out.println(resultSet.getString("dept_name"));

				count++;
			}
			
			if (count == 0) {
				System.out.println("該当する社員はいません");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//クローズ処理
			DBManager.close(resultSet);
			DBManager.close(preparedStatement);
			DBManager.close(connection);
		}

	}

	/**
	 * 部署ID検索
	 *
	 * @param deptId
	 *            部署ID
	 * @throws ClassNotFoundException
	 *             ドライバクラスが存在しない場合に送出
	 * @throws SQLException
	 *             データベース操作時にエラーが発生した場合に送出
	 */
	public static void selectByDeptId(String deptId)
			throws ClassNotFoundException, SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			//DB に接続
			connection = DBManager.getConnection();
			//ステートメントを作成（SQL文から返ってくる値の数は5つ）
			preparedStatement = connection.prepareStatement(ConstantSQL.SQL_SELECT_DEPTID);
			//入力値をバインド
			preparedStatement.setString(1, deptId);
			//SQL 文を実行
			resultSet = preparedStatement.executeQuery();

			// resultSetの中身を数えるための変数を用意
			int count = 0;

			// 検索結果を出力
			System.out.println("社員ID\t社員名\t性別\t生年月日\t部署名");
			while (resultSet.next()) {
				System.out.print(resultSet.getString("emp_id") + "\t");
				System.out.print(resultSet.getString("emp_name") + "\t");
				int gender = Integer.parseInt(resultSet.getString("gender"));
				if (gender == 1) {
					System.out.print("男性\t");
				} else if (gender == 2) {
					System.out.print("女性\t");
				}
				System.out.print(resultSet.getString("birthday") + "\t");
				System.out.println(resultSet.getString("dept_name"));

				count++;

			}

			if (count == 0) {
				System.out.println("該当する社員はいません");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//クローズ処理
			DBManager.close(resultSet);
			DBManager.close(preparedStatement);
			DBManager.close(connection);
		}

	}

}

