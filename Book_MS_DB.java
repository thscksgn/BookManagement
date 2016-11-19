package conn.test;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.*;

public abstract class Book_MS_DB { // 도서 및 회원 관리기능 클래스
	
	
	
	public static void bookSave_DB (String str1, String str2, String str3, String str4)  {  // DB에 도서저장하는 메소드
		if (str1.equals("") | str2.equals("") | str3.equals("") | str4.equals(""))  {
			System.out.println("등록할 도서정보를 입력해주세요.");
			return;
		}		
		Connection conn = null;
		Statement stmt = null;
		try  {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/book", "root", "5845440");
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select code from booksinfo where code = '" + str4 + "';");
			if (rs.next())  {
				int rowNum = stmt.executeUpdate("update booksinfo set name:='" + str1 + "', writer:='" + str2 + "', publisher:='" + str3 + "' where code='" + str4 + "';");
				for (Book obj : Book.list)  {
					if (obj.code.equals(str4))  {
						obj.title = str1;
						obj.writer = str2;
						obj.publisher = str3;
						break;
					}
				}
				int rowCount = BookGui.model.getRowCount(); // 도서 테이블 화면 도서변경정보 갱신
				for (int cnt = 0; cnt < rowCount; cnt++)  {
					if (str4.equals(BookGui.model.getValueAt(cnt, 3)))  {
						BookGui.model.setValueAt(str1, cnt, 0);
						BookGui.model.setValueAt(str2, cnt, 1);
						BookGui.model.setValueAt(str3, cnt, 2);
						break;
					}
				}
				System.out.println(str4 + " 코드의 도서정보 수정됨.");
				return;
			}
			else  {
				String queryLang = "insert into booksinfo (name, writer, publisher, code, forrent) values('" + str1 + "', '" + str2 + "', '" + str3 + "', '" + str4 + "', '가능');";
				int rowNum = stmt.executeUpdate(queryLang);
				System.out.println("새로운 도서 등록완료.");
			}	
			Book.list.add(new Book(str1, str2, str3, str4, "가능"));
			String rowData[] = {str1, str2, str3, str4, "가능"};
			BookGui.model.addRow(rowData);
		}
		catch (ClassNotFoundException cnfe)  {
			System.out.println("해당 클래스를 찾을 수 없습니다." + cnfe.getMessage());
		}
		catch (SQLException se)  {
			System.out.println(se.getMessage());
		}
		finally  {
			try  {
				stmt.close();
			}
			catch (Exception a)  {
			}
			try  {
				conn.close();
			}
			catch (Exception a)  {
			}
		}
	}
	
	public static void bookDelete_DB (String codeStr)  { // DB에 저장되어 있는 도서 삭제 메소드
		if (codeStr.equals(""))  {
			System.out.println("삭제할 도서코드를 입력해주세요.");
			return;
		}
		Connection conn = null;
		Statement stmt = null;
		try  {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/book", "root", "5845440");
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select name from booksinfo where code = '" + codeStr + "';");
			String name = null;
			while (rs.next())  {
				name = rs.getString("name"); //****
			}
			if (name == null)  {
				System.out.println("해당 코드의 도서가 없습니다");
				return;
			}
			String queryLang = "delete from booksinfo where code = '" + codeStr + "';";
			int rowNum = stmt.executeUpdate(queryLang);
			System.out.printf("제목: %s%n코드: %s%n%s", name, codeStr, "삭제완료.");
			for (int cnt = 0; cnt < Book.list.size(); cnt++)  { // 자료구조에 저장되어 있는 도서 데이터 삭제
				Book obj = Book.list.get(cnt);
				if (obj.code.equals(codeStr))  {
					Book.list.remove(cnt);
					break;
				}
			}
			int rowCount = BookGui.model.getRowCount(); // 삭제한 도서와 같은 테이블 튜플삭제
			for (int cnt = 0; cnt < rowCount; cnt++)  {
				if (BookGui.model.getValueAt(cnt, 3).equals(codeStr))
					BookGui.model.removeRow(cnt);
			}
		}
		catch (ClassNotFoundException cnfe)  {
			System.out.println("해당 클래스를 찾을 수 없습니다." + cnfe.getMessage());
		}
		catch (SQLException se)  {
			System.out.println(se.getMessage());
		}
		finally  {
			try  {
				stmt.close();
			}
			catch (Exception e)  {	
			}
			try  {
				conn.close();
			}
			catch (Exception e)  {		
			}
		}
	}
	
	public static void memberSave_DB (String memberNameStr, String addressStr, String phoneStr)  { // DB에 회원정보 저장하는 메소드
		String str[] = {memberNameStr, addressStr, phoneStr};
		for (String a : str)  {
			if (a.equals(""))  {
				System.out.println("등록할 회원정보를 입력해주세요");
				return;
			}
		}	
		Connection conn = null;
		Statement stmt = null;
		try  {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/book", "root", "5845440");
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select name, address, phone from membersinfo where phone = '" + phoneStr + "';");
			String name = null;
			String address = null;
			String phone = null;
			while (rs.next())  {
				name = rs.getString("name");
				address = rs.getString("address");
				phone = rs.getString("phone");
			}
			if (!(name == null))  {	
				stmt.executeUpdate("update membersinfo set name:='" + memberNameStr + "', address:= '" + addressStr + "', phone:= '" + phoneStr + "' where phone = '" + phoneStr + "';");
				for (Member obj : Member.list)  {
					if (obj.phone.equals(phoneStr))  {
						obj.name = memberNameStr;
						obj.address = addressStr;
						break;
					}
				}
				System.out.printf("이름: %s 주소: %s --> 이름: %s 주소: %s 로 수정되었습니다", name, address, memberNameStr, addressStr);
				return;
			}
			String queryLang = "insert into membersinfo (name, address, phone) values('" + memberNameStr + "', '" + addressStr + "', '" + phoneStr + "');";
			int rowNum = stmt.executeUpdate(queryLang);
			System.out.println("회원등록 완료");
			Member.list.add(new Member(memberNameStr, addressStr, phoneStr, null, null, null)); // 자료구조에 회원 저장
		}
		catch (ClassNotFoundException cnfe)  {
			System.out.println("해당 클래스를 찾을 수 없습니다." + cnfe.getMessage());
		}
		catch (SQLException se)  {
			System.out.println(se.getMessage());
		}
		finally  {
			try  {
				stmt.close();
			}
			catch (Exception e)  {
			}
			try  {
				conn.close();
			}
			catch (Exception e)  {
			}
		}
	}
	
	public static void memberDelete_DB (String phoneStr)  { // DB에 저장되어 있는 회원정보 삭제 메소드
		if (phoneStr.equals(""))  {
			System.out.println("삭제할 회원의 전화번호를 입력해주세요");
			return;
		}
		Connection conn = null;
		Statement stmt = null;
		try  {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/book", "root", "5845440");
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select name from membersinfo where phone = '" + phoneStr + "';");
			String name = null;
			while (rs.next())  {
				name = rs.getString("name");
			}
			if (name == null)  {
				System.out.println("해당 전화번호의 회원이 없습니다");
				return;
			}
			String queryLang = "delete from membersinfo where phone = '" + phoneStr + "';";
			int rowNum = stmt.executeUpdate(queryLang);
			System.out.printf("이름: %s%n전화번호: %s%n%s", name, phoneStr, "삭제완료.");
			for (int cnt = 0; cnt < Member.list.size(); cnt++)  { // 자료구조에 저장되어 있는 회원 데이터 삭제
				Member obj = Member.list.get(cnt);
				if (obj.phone.equals(phoneStr))  {
					Member.list.remove(cnt);
					break;
				}
			}	
		}
		catch (ClassNotFoundException cnfe)  {
			System.out.println("해당 클래스를 찾을 수 없습니다." + cnfe.getMessage());
		}
		catch (SQLException se)  {
			System.out.println(se.getMessage());
		}
		finally  {
			try  {
				stmt.close();
			}
			catch (Exception e)  {
			}
			try  {
				conn.close();
			}
			catch (Exception e)  {
			}
		}
	}
	
	public static void printTable (JTable table)  { // 프로그램 실행시 테이블 초기화 작업 메소드
		Connection conn = null;
		Statement stmt = null;
		try  {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/book", "root", "5845440");
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select * from booksinfo;");
			DefaultTableModel model = (DefaultTableModel) table.getModel();
			String name, writer, publisher, code, forrent;
			while (rs.next())  {
				name = rs.getString("name");
				writer = rs.getString("writer");
				publisher = rs.getString("publisher");
				code = rs.getString("code");
				forrent = rs.getString("forrent");
				Book.list.add(new Book(name, writer, publisher, code, forrent));
				String str[] = {name, writer, publisher, code, forrent};
				model.addRow(str);	
			}
			rs = stmt.executeQuery("select * from membersinfo;");
			while (rs.next())  {
				Member.list.add(new Member(rs.getString("name"), rs.getString("address"), rs.getString("phone"), rs.getString("rentbook1"), rs.getString("rentbook2"), rs.getString("rentbook3")));
			}
		}
		catch (ClassNotFoundException cnfe)  {
			System.out.println("해당 클래스를 찾을 수 없습니다." + cnfe.getMessage());
		}
		catch (SQLException se)  {
			System.out.println(se.getMessage());
		}
		finally  {
			try  {
				stmt.close();
			}
			catch (Exception e)  {
			}
			try  {
				conn.close();
			}
			catch (Exception e)  {
			}
		}
	}
	
	public static void rentalBookIndex (String title, String writer, String publisher, JTable table)  { // 도서 검색기능 메소드
		if (title.equals("") && writer.equals("") && publisher.equals(""))  {
			System.out.println("검색조건을 한개 이상 입력해주세요");
			return;
		}
		int size = Book.list.size();
		String tempData1[][] = new String[size][5];
		String tempData2[][] = new String[size][5];
		int cntData1 = 0;
		int cntData2 = 0;
		DefaultTableModel model = (DefaultTableModel) table.getModel();	
		if (!(title.equals("")))  {
			for (Book obj:Book.list)  {
				if (!(-1 == obj.title.indexOf(title)))  {
					tempData1[cntData1][0] = obj.title;
					tempData1[cntData1][1] = obj.writer;
					tempData1[cntData1][2] = obj.publisher;
					tempData1[cntData1][3] = obj.code;
					tempData1[cntData1][4] = obj.forrent;
					cntData1++;
				}
			}
			if (tempData1[0][0] == null)  {
				System.out.println("검색한 회원정보가 없습니다.");
				return;
			}
		}
		
		if (!(writer.equals("")))  {
			if (tempData1[0][0] == null)  {
				for (Book obj:Book.list)  {
					if (!(-1 == obj.writer.indexOf(writer)))  {
						tempData1[cntData1][0] = obj.title;
						tempData1[cntData1][1] = obj.writer;
						tempData1[cntData1][2] = obj.publisher;
						tempData1[cntData1][3] = obj.code;
						tempData1[cntData1][4] = obj.forrent;
						cntData1++;
					}
				}
				if (tempData1[0][0] == null)  {
					System.out.println("검색한 회원정보가 없습니다.");
					return;
				}	
			}
			else  {
				for (int cnt = 0; cnt < cntData1; cnt++)  {
					if (!(-1 == tempData1[cnt][1].indexOf(writer)))  {
						tempData2[cntData2] = tempData1[cnt];
						cntData2++;
					}
				}
				if (tempData2[0][0] == null)  {
					System.out.println("검색한 회원정보가 없습니다.");
					return;
				}	
			}
		}
			
		if (!(publisher.equals("")))  {
			if (tempData2[0][0] == null)  {
				if (tempData1[0][0] == null)  {
					int count = 0;
					for (Book obj:Book.list)  {
						if (!(-1 == obj.publisher.indexOf(publisher)))  {
							String arr[] = {obj.title, obj.writer, obj.publisher, obj.code, obj.forrent};
							model.addRow(arr);
						}
					}
					if (count == 0)
						System.out.println("검색한 도서정보가 없습니다.");
				}
				else  {
					int count = 0;
					for (int cnt = 0; cnt < cntData1; cnt++)  {
						if (!(-1 == tempData1[cnt][2].indexOf(publisher)))  {
							model.addRow(tempData1[cnt]);
							count++;
						}
					}
					if (count == 0)
						System.out.println("검색한 회원정보가 없습니다.");
				}
			}
			else  {
				for (int cnt = 0; cnt < cntData2; cnt++)  {
					if (!(-1 == tempData2[cnt][2].indexOf(publisher)))
						model.addRow(tempData2[cnt]);
				}
			}
		}
		else  {
			if (tempData2[0][0] == null)  { 	 //제목 or 저자만 입력했을시					
				for (int cnt = 0; cnt < cntData1; cnt++)  {
					if (tempData1[cnt][0] == null)
						return;
					model.addRow(tempData1[cnt]);
				}
			}
			else  { // 제목 and 저자 입력했을시
				for (int cnt = 0; cnt < cntData2; cnt++)  {
					if (tempData2[cnt][0] == null)
						return;
					model.addRow(tempData2[cnt]);
				}
			}
		}	
	}
	
	public static void rentalBookIndex (String codeStr, DefaultTableModel model)  { // 코드로 도서검색 메소드
		int cnt = 0;
		for (Book obj : Book.list)  {
			if (obj.code.equals(codeStr))  {
				String data[] = {obj.title, obj.writer, obj.publisher, obj.code, obj.forrent};
				model.addRow(data);
				cnt++;
			}
		}
		if (cnt == 0)  {
			System.out.println("검색한 도서코드가 없습니다");
		}
	}
	
	public static String[] getTableData (JTable table)  { // 선택한 테이블 행의 데이터를 가져오는 메소드
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		int rowNum = table.getSelectedRow();
		if (rowNum == -1)
			return null;
		int colCount = table.getColumnCount();
		String tableData[] = new String[colCount];
		for (int cnt = 0; cnt < colCount; cnt++)  {
			Object obj = model.getValueAt(rowNum, cnt);
			tableData[cnt] = obj.toString();
		}
		return tableData;
	}
	
	public static void rentalMemberIndex (String name, JTable table)  { // 회원 검색기능 메소드
		if (name.equals(""))  {
			System.out.println("검색할 이름을 입력해주세요");
			return;
		}
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		int cnt = 0;
		for (Member obj : Member.list)  {
			if (!(-1 == obj.name.indexOf(name)))  {
				String data[] = {obj.name, obj.address, obj.phone};
				model.addRow(data);
				cnt++;
			}
		}
		if (cnt == 0)
			System.out.println("검색결과가 없습니다");
		
	}
	
	public static void rental_DB (String selectBook[], String selectMember[])  { // 대여처리하는 메소드
		Connection conn = null;
		Statement stmt = null;
		try  {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/book", "root", "5845440");
			stmt = conn.createStatement();
			stmt.executeUpdate("update booksinfo set forrent:= '대여중' where code = '" + selectBook[3] + "';");
			ResultSet rs = stmt.executeQuery("select rentbook1, rentbook2, rentbook3 from membersinfo where phone = '" + selectMember[2] + "';");
			String rentbook1 = null;
			String rentbook2 = null;
			String rentbook3 = null;
			while (rs.next())  {
				rentbook1 = rs.getString("rentbook1");
				rentbook2 = rs.getString("rentbook2");
				rentbook3 = rs.getString("rentbook3");
			}
			if (rentbook1 == null)  {
				stmt.executeUpdate("update membersinfo set rentbook1:= '" + selectBook[0] + "(" + selectBook[3] + ")' where phone = '" + selectMember[2] + "';");
			}
			else if (rentbook2 == null)  {
				stmt.executeUpdate("update membersinfo set rentbook2:= '" + selectBook[0] + "(" + selectBook[3] + ")' where phone = '" + selectMember[2] + "';");
			}
			else  {
				stmt.executeUpdate("update membersinfo set rentbook3:= '" + selectBook[0] + "(" + selectBook[3] + ")' where phone = '" + selectMember[2] + "';");
			}
			System.out.printf("대여자: %s%n제목: %s(%s)%n%s", selectMember[0], selectBook[0], selectBook[3], "대여작업 완료");
			for (Book obj : Book.list)  { // 자료구조에 있는 도서데이터 '대여중'으로 갱신작업
				if (obj.code.equals(selectBook[3]))  {
					obj.setForrent("대여중");
					break;
				}
			}
			for (Member obj : Member.list)  { // 자료구조에 있는 회원데이터에 대여한 책 추가하는 메소드
				if (obj.phone.equals(selectMember[2]))  {
					if (obj.rentBook[0] == null)
						obj.rentBook[0] = selectBook[0] + "(" + selectBook[3] + ")";
					else if (obj.rentBook[1] == null)
						obj.rentBook[1] = selectBook[0] + "(" + selectBook[3] + ")";
					else
						obj.rentBook[2] = selectBook[0] + "(" + selectBook[3] + ")";
					break;
				}
			}
			int rowCount = BookGui.model.getRowCount(); // 도서 테이블 화면 대여중으로 화면 갱신
			for (int cnt = 0; cnt < rowCount; cnt++)  {
				if (selectBook[3].equals(BookGui.model.getValueAt(cnt, 3)))  {
					BookGui.model.setValueAt("대여중", cnt, 4);
					break;
				}
			}
		}
		catch (ClassNotFoundException cnfe)  {
			System.out.println("해당 클래스를 찾을 수 없습니다." + cnfe.getMessage());
		}
		catch (SQLException se)  {
			System.out.println(se.getMessage());
		}
		finally  {
			try  {
				stmt.close();
			}
			catch (Exception a)  {
			}
			try  {
				conn.close();
			}
			catch (Exception a)  {
			}
		}
	}
	
	public static boolean mrb_list(String phone)  { // 회원이 대여 가능한 상태인지(대여권수. 최대3권) 확인하는 메소드
		String arr[] = new String[3];
		for (Member obj : Member.list)  {
			if (phone.equals(obj.phone))  {
				for (int cnt = 0; cnt < obj.rentBook.length; cnt++)  {
					arr[cnt] = obj.rentBook[cnt];
				}
				break;
			}
		}
		if (!(arr[0] == null) && !(arr[1] == null) && !(arr[2] == null))  {
			return false;
		}
		return true;
	}
	
	public static void returnBook_DB (String code)  { // 반납 처리하는 메소드
		Connection conn = null;
		Statement stmt = null;
		try  {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/book", "root", "5845440");
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("Select name, forrent from booksinfo where code = '" + code + "';");
			String forrent = null;
			String title = null;
			while (rs.next())  {
				forrent = rs.getString("forrent");
				title = rs.getString("name");
			}
			if (forrent == null)  {
				System.out.println("도서코드를 잘못 입력하였습니다");
				return;
			}
			else if (forrent.equals("가능"))  {
				System.out.println("이미 반납되어 있습니다");
				return;
			}
			rs = stmt.executeQuery("select name, phone, rentbook1, rentbook2, rentbook3 from membersinfo where rentbook1 like '%(" + code + ")' or " + "rentbook2 like '%(" + code + ")' or " + "rentbook3 like '%(" + code + ")';"); 
			String phone = null;
			String name = null;
			String rentbook1 = "null";
			String rentbook2 = "null";
			String rentbook3 = "null";
			while (rs.next())  {
				name = rs.getString("name");
				phone = rs.getString("phone");
				rentbook1 = rs.getString("rentbook1");
				rentbook2 = rs.getString("rentbook2");
				rentbook3 = rs.getString("rentbook3");
			}
			String a = title + "(" + code + ")";
			if (rentbook1.equals(a))
				stmt.executeUpdate("update membersinfo set rentbook1:= null where phone = '" + phone + "';");
			else if (rentbook2.equals(a))
				stmt.executeUpdate("update membersinfo set rentbook2:= null where phone = '" + phone + "';");
			else if (rentbook3.equals(a))
				stmt.executeUpdate("update membersinfo set rentbook3:= null where phone = '" + phone + "';");
			else  {
				System.out.println("에러");
				return;
			}
			stmt.executeUpdate("update booksinfo set forrent:='가능' where code = '" + code + "';");
			System.out.printf("제목: %s%n코드: %s%n%s%n", title, code, name + " 회원님의 반납완료");
			for (Book obj : Book.list)  { // 자료구조에 있는 도서데이터 '가능'으로 갱신작업
				if (obj.code.equals(code))  {
					obj.setForrent("가능");
					break;
				}
			}
			for (Member obj : Member.list)  { // 자료구조에 있는 회원데이터에서 반납하는 회원의 3개의 대여목록에서 반납도서 삭제
				if (obj.phone.equals(phone))  {
					if (obj.rentBook[0].equals(a))
						obj.rentBook[0] = null;
					else if (obj.rentBook[1].equals(a))
						obj.rentBook[1] = null;
					else
						obj.rentBook[2] = null;
					break;
				}
			}
			int rowCount = BookGui.model.getRowCount(); // 도서 테이블 화면 대여가능으로 화면 갱신
			for (int cnt = 0; cnt < rowCount; cnt++)  {
				if (code.equals(BookGui.model.getValueAt(cnt, 3)))  {
					BookGui.model.setValueAt("가능", cnt, 4);
					break;
				}
			}
		}
		catch (ClassNotFoundException cnfe)  {
			System.out.println("해당 클래스를 찾을 수 없습니다." + cnfe.getMessage());
		}
		catch (SQLException se)  {
			System.out.println(se.getMessage());
		}
		finally  {
			try  {
				stmt.close();
			}
			catch (Exception a)  {
			}
			try  {
				conn.close();
			}
			catch (Exception a)  {
			}
		}
	}
	
	public static void allRemoveRowData (DefaultTableModel model)  { // 테이블의 모든 튜플 삭제 메소드
		int rowCount = model.getRowCount();
		for (int cnt = 0; cnt < rowCount; cnt++)  {
			model.removeRow(0);
		}
	}
	
	public static void rentalListAddRow (DefaultTableModel model)  { // 대여중인 도서데이터만 테이블 튜플에 추가하는 메소드
		allRemoveRowData(model);
		for (Book obj : Book.list)  {
			if (obj.forrent.equals("대여중"))  {
				String data[] = {obj.title, obj.writer, obj.publisher, obj.code, obj.forrent};
				model.addRow(data);
			}	
		}
	}
	
	public static void allBookDataAddRow (DefaultTableModel model)  { // 모든 도서 데이터 테이블 튜플에 추가하는 메소드
		allRemoveRowData(model);
		for (Book obj : Book.list)  {
			String data[] = {obj.title, obj.writer, obj.publisher, obj.code, obj.forrent};
			model.addRow(data);
		}
	}
	
	public static void allMemberDataAddRow (DefaultTableModel model)  { // 모든 회원 데이터 테이블 튜플에 추가하는 메소드
		for (Member obj : Member.list)  {
			String data[] = {obj.name, obj.address, obj.phone, obj.rentBook[0], obj.rentBook[1], obj.rentBook[2]};
			model.addRow(data);
		}
	}
}
