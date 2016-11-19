package conn.test;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.*;

public abstract class Book_MS_DB { // ���� �� ȸ�� ������� Ŭ����
	
	
	
	public static void bookSave_DB (String str1, String str2, String str3, String str4)  {  // DB�� ���������ϴ� �޼ҵ�
		if (str1.equals("") | str2.equals("") | str3.equals("") | str4.equals(""))  {
			System.out.println("����� ���������� �Է����ּ���.");
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
				int rowCount = BookGui.model.getRowCount(); // ���� ���̺� ȭ�� ������������ ����
				for (int cnt = 0; cnt < rowCount; cnt++)  {
					if (str4.equals(BookGui.model.getValueAt(cnt, 3)))  {
						BookGui.model.setValueAt(str1, cnt, 0);
						BookGui.model.setValueAt(str2, cnt, 1);
						BookGui.model.setValueAt(str3, cnt, 2);
						break;
					}
				}
				System.out.println(str4 + " �ڵ��� �������� ������.");
				return;
			}
			else  {
				String queryLang = "insert into booksinfo (name, writer, publisher, code, forrent) values('" + str1 + "', '" + str2 + "', '" + str3 + "', '" + str4 + "', '����');";
				int rowNum = stmt.executeUpdate(queryLang);
				System.out.println("���ο� ���� ��ϿϷ�.");
			}	
			Book.list.add(new Book(str1, str2, str3, str4, "����"));
			String rowData[] = {str1, str2, str3, str4, "����"};
			BookGui.model.addRow(rowData);
		}
		catch (ClassNotFoundException cnfe)  {
			System.out.println("�ش� Ŭ������ ã�� �� �����ϴ�." + cnfe.getMessage());
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
	
	public static void bookDelete_DB (String codeStr)  { // DB�� ����Ǿ� �ִ� ���� ���� �޼ҵ�
		if (codeStr.equals(""))  {
			System.out.println("������ �����ڵ带 �Է����ּ���.");
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
				System.out.println("�ش� �ڵ��� ������ �����ϴ�");
				return;
			}
			String queryLang = "delete from booksinfo where code = '" + codeStr + "';";
			int rowNum = stmt.executeUpdate(queryLang);
			System.out.printf("����: %s%n�ڵ�: %s%n%s", name, codeStr, "�����Ϸ�.");
			for (int cnt = 0; cnt < Book.list.size(); cnt++)  { // �ڷᱸ���� ����Ǿ� �ִ� ���� ������ ����
				Book obj = Book.list.get(cnt);
				if (obj.code.equals(codeStr))  {
					Book.list.remove(cnt);
					break;
				}
			}
			int rowCount = BookGui.model.getRowCount(); // ������ ������ ���� ���̺� Ʃ�û���
			for (int cnt = 0; cnt < rowCount; cnt++)  {
				if (BookGui.model.getValueAt(cnt, 3).equals(codeStr))
					BookGui.model.removeRow(cnt);
			}
		}
		catch (ClassNotFoundException cnfe)  {
			System.out.println("�ش� Ŭ������ ã�� �� �����ϴ�." + cnfe.getMessage());
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
	
	public static void memberSave_DB (String memberNameStr, String addressStr, String phoneStr)  { // DB�� ȸ������ �����ϴ� �޼ҵ�
		String str[] = {memberNameStr, addressStr, phoneStr};
		for (String a : str)  {
			if (a.equals(""))  {
				System.out.println("����� ȸ�������� �Է����ּ���");
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
				System.out.printf("�̸�: %s �ּ�: %s --> �̸�: %s �ּ�: %s �� �����Ǿ����ϴ�", name, address, memberNameStr, addressStr);
				return;
			}
			String queryLang = "insert into membersinfo (name, address, phone) values('" + memberNameStr + "', '" + addressStr + "', '" + phoneStr + "');";
			int rowNum = stmt.executeUpdate(queryLang);
			System.out.println("ȸ����� �Ϸ�");
			Member.list.add(new Member(memberNameStr, addressStr, phoneStr, null, null, null)); // �ڷᱸ���� ȸ�� ����
		}
		catch (ClassNotFoundException cnfe)  {
			System.out.println("�ش� Ŭ������ ã�� �� �����ϴ�." + cnfe.getMessage());
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
	
	public static void memberDelete_DB (String phoneStr)  { // DB�� ����Ǿ� �ִ� ȸ������ ���� �޼ҵ�
		if (phoneStr.equals(""))  {
			System.out.println("������ ȸ���� ��ȭ��ȣ�� �Է����ּ���");
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
				System.out.println("�ش� ��ȭ��ȣ�� ȸ���� �����ϴ�");
				return;
			}
			String queryLang = "delete from membersinfo where phone = '" + phoneStr + "';";
			int rowNum = stmt.executeUpdate(queryLang);
			System.out.printf("�̸�: %s%n��ȭ��ȣ: %s%n%s", name, phoneStr, "�����Ϸ�.");
			for (int cnt = 0; cnt < Member.list.size(); cnt++)  { // �ڷᱸ���� ����Ǿ� �ִ� ȸ�� ������ ����
				Member obj = Member.list.get(cnt);
				if (obj.phone.equals(phoneStr))  {
					Member.list.remove(cnt);
					break;
				}
			}	
		}
		catch (ClassNotFoundException cnfe)  {
			System.out.println("�ش� Ŭ������ ã�� �� �����ϴ�." + cnfe.getMessage());
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
	
	public static void printTable (JTable table)  { // ���α׷� ����� ���̺� �ʱ�ȭ �۾� �޼ҵ�
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
			System.out.println("�ش� Ŭ������ ã�� �� �����ϴ�." + cnfe.getMessage());
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
	
	public static void rentalBookIndex (String title, String writer, String publisher, JTable table)  { // ���� �˻���� �޼ҵ�
		if (title.equals("") && writer.equals("") && publisher.equals(""))  {
			System.out.println("�˻������� �Ѱ� �̻� �Է����ּ���");
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
				System.out.println("�˻��� ȸ�������� �����ϴ�.");
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
					System.out.println("�˻��� ȸ�������� �����ϴ�.");
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
					System.out.println("�˻��� ȸ�������� �����ϴ�.");
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
						System.out.println("�˻��� ���������� �����ϴ�.");
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
						System.out.println("�˻��� ȸ�������� �����ϴ�.");
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
			if (tempData2[0][0] == null)  { 	 //���� or ���ڸ� �Է�������					
				for (int cnt = 0; cnt < cntData1; cnt++)  {
					if (tempData1[cnt][0] == null)
						return;
					model.addRow(tempData1[cnt]);
				}
			}
			else  { // ���� and ���� �Է�������
				for (int cnt = 0; cnt < cntData2; cnt++)  {
					if (tempData2[cnt][0] == null)
						return;
					model.addRow(tempData2[cnt]);
				}
			}
		}	
	}
	
	public static void rentalBookIndex (String codeStr, DefaultTableModel model)  { // �ڵ�� �����˻� �޼ҵ�
		int cnt = 0;
		for (Book obj : Book.list)  {
			if (obj.code.equals(codeStr))  {
				String data[] = {obj.title, obj.writer, obj.publisher, obj.code, obj.forrent};
				model.addRow(data);
				cnt++;
			}
		}
		if (cnt == 0)  {
			System.out.println("�˻��� �����ڵ尡 �����ϴ�");
		}
	}
	
	public static String[] getTableData (JTable table)  { // ������ ���̺� ���� �����͸� �������� �޼ҵ�
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
	
	public static void rentalMemberIndex (String name, JTable table)  { // ȸ�� �˻���� �޼ҵ�
		if (name.equals(""))  {
			System.out.println("�˻��� �̸��� �Է����ּ���");
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
			System.out.println("�˻������ �����ϴ�");
		
	}
	
	public static void rental_DB (String selectBook[], String selectMember[])  { // �뿩ó���ϴ� �޼ҵ�
		Connection conn = null;
		Statement stmt = null;
		try  {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/book", "root", "5845440");
			stmt = conn.createStatement();
			stmt.executeUpdate("update booksinfo set forrent:= '�뿩��' where code = '" + selectBook[3] + "';");
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
			System.out.printf("�뿩��: %s%n����: %s(%s)%n%s", selectMember[0], selectBook[0], selectBook[3], "�뿩�۾� �Ϸ�");
			for (Book obj : Book.list)  { // �ڷᱸ���� �ִ� ���������� '�뿩��'���� �����۾�
				if (obj.code.equals(selectBook[3]))  {
					obj.setForrent("�뿩��");
					break;
				}
			}
			for (Member obj : Member.list)  { // �ڷᱸ���� �ִ� ȸ�������Ϳ� �뿩�� å �߰��ϴ� �޼ҵ�
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
			int rowCount = BookGui.model.getRowCount(); // ���� ���̺� ȭ�� �뿩������ ȭ�� ����
			for (int cnt = 0; cnt < rowCount; cnt++)  {
				if (selectBook[3].equals(BookGui.model.getValueAt(cnt, 3)))  {
					BookGui.model.setValueAt("�뿩��", cnt, 4);
					break;
				}
			}
		}
		catch (ClassNotFoundException cnfe)  {
			System.out.println("�ش� Ŭ������ ã�� �� �����ϴ�." + cnfe.getMessage());
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
	
	public static boolean mrb_list(String phone)  { // ȸ���� �뿩 ������ ��������(�뿩�Ǽ�. �ִ�3��) Ȯ���ϴ� �޼ҵ�
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
	
	public static void returnBook_DB (String code)  { // �ݳ� ó���ϴ� �޼ҵ�
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
				System.out.println("�����ڵ带 �߸� �Է��Ͽ����ϴ�");
				return;
			}
			else if (forrent.equals("����"))  {
				System.out.println("�̹� �ݳ��Ǿ� �ֽ��ϴ�");
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
				System.out.println("����");
				return;
			}
			stmt.executeUpdate("update booksinfo set forrent:='����' where code = '" + code + "';");
			System.out.printf("����: %s%n�ڵ�: %s%n%s%n", title, code, name + " ȸ������ �ݳ��Ϸ�");
			for (Book obj : Book.list)  { // �ڷᱸ���� �ִ� ���������� '����'���� �����۾�
				if (obj.code.equals(code))  {
					obj.setForrent("����");
					break;
				}
			}
			for (Member obj : Member.list)  { // �ڷᱸ���� �ִ� ȸ�������Ϳ��� �ݳ��ϴ� ȸ���� 3���� �뿩��Ͽ��� �ݳ����� ����
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
			int rowCount = BookGui.model.getRowCount(); // ���� ���̺� ȭ�� �뿩�������� ȭ�� ����
			for (int cnt = 0; cnt < rowCount; cnt++)  {
				if (code.equals(BookGui.model.getValueAt(cnt, 3)))  {
					BookGui.model.setValueAt("����", cnt, 4);
					break;
				}
			}
		}
		catch (ClassNotFoundException cnfe)  {
			System.out.println("�ش� Ŭ������ ã�� �� �����ϴ�." + cnfe.getMessage());
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
	
	public static void allRemoveRowData (DefaultTableModel model)  { // ���̺��� ��� Ʃ�� ���� �޼ҵ�
		int rowCount = model.getRowCount();
		for (int cnt = 0; cnt < rowCount; cnt++)  {
			model.removeRow(0);
		}
	}
	
	public static void rentalListAddRow (DefaultTableModel model)  { // �뿩���� ���������͸� ���̺� Ʃ�ÿ� �߰��ϴ� �޼ҵ�
		allRemoveRowData(model);
		for (Book obj : Book.list)  {
			if (obj.forrent.equals("�뿩��"))  {
				String data[] = {obj.title, obj.writer, obj.publisher, obj.code, obj.forrent};
				model.addRow(data);
			}	
		}
	}
	
	public static void allBookDataAddRow (DefaultTableModel model)  { // ��� ���� ������ ���̺� Ʃ�ÿ� �߰��ϴ� �޼ҵ�
		allRemoveRowData(model);
		for (Book obj : Book.list)  {
			String data[] = {obj.title, obj.writer, obj.publisher, obj.code, obj.forrent};
			model.addRow(data);
		}
	}
	
	public static void allMemberDataAddRow (DefaultTableModel model)  { // ��� ȸ�� ������ ���̺� Ʃ�ÿ� �߰��ϴ� �޼ҵ�
		for (Member obj : Member.list)  {
			String data[] = {obj.name, obj.address, obj.phone, obj.rentBook[0], obj.rentBook[1], obj.rentBook[2]};
			model.addRow(data);
		}
	}
}
