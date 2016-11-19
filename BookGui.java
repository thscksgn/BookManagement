package conn.test;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;

public class BookGui {
	static DefaultTableModel model;
	
	BookGui ()  {
		JFrame frame = new JFrame("�������� ���α׷�");
		frame.setLocation(130, 100);
		frame.setPreferredSize(new Dimension(800,500));
		Container contentPane = frame.getContentPane();
		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		JPanel panel3 = new JPanel();
		panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));
		JButton addBookButton = new JButton("�����߰�");
		JButton deleteBookButton = new JButton("��������");
		JButton addMemberButton = new JButton("ȸ�����");
		JButton deleteMemberButton = new JButton("ȸ������");
		JButton rentalBookButton = new JButton("�뿩");
		JButton returnBookButton = new JButton("�ݳ�");
		JButton rentalListButton = new JButton("�뿩����Ʈ");
		JButton allListButton = new JButton("��ü����Ʈ");
		JButton memberIndexButton = new JButton("ȸ������Ʈ");
		JTextField title = new JTextField(11);
		JTextField writer = new JTextField(4);
		JTextField publisher = new JTextField(9);
		JTextField code = new JTextField(4);
		JButton indexButton = new JButton("�˻�");
		panel1.add(addBookButton);
		panel1.add(deleteBookButton);
		panel1.add(addMemberButton);
		panel1.add(deleteMemberButton);
		contentPane.add(panel1, BorderLayout.EAST);
		panel2.add(rentalBookButton);
		panel2.add(returnBookButton);
		panel2.add(rentalListButton);
		panel2.add(allListButton);
		panel2.add(memberIndexButton);
		contentPane.add(panel2, BorderLayout.NORTH);
		panel3.add(new JLabel("����"));
		panel3.add(title);
		panel3.add(new JLabel("����"));
		panel3.add(writer);
		panel3.add(new JLabel("���ǻ�"));
		panel3.add(publisher);
		panel3.add(indexButton);
		panel3.add(new JLabel("�ڵ�"));
		panel3.add(code);
		contentPane.add(panel3, BorderLayout.SOUTH);
		String columNames[] = {"������", "����", "���ǻ�", "�ڵ�", "�뿩����"};
		DefaultTableModel model = new DefaultTableModel(columNames, 0);
		JTable table = new JTable(model);
		this.model = model;
		Book_MS_DB.printTable(table); // ���α׷� ���� �� ���̺� ȭ�� �ʱ�ȭ 
		contentPane.add(new JScrollPane(table));
		
		ActionListener obj1 = new ActionListener()  {
			public void actionPerformed (ActionEvent e)  {
				addBookButtonGui();
			}
		};
		addBookButton.addActionListener(obj1);
		
		ActionListener obj2 = new ActionListener()  {
			public void actionPerformed (ActionEvent e)  {
				deleteBookButtonGui();
			}
		};
		deleteBookButton.addActionListener(obj2);
		
		ActionListener obj3 = new ActionListener()  {
			public void actionPerformed (ActionEvent e)  {
				addMemberButtonGui();
			}
		};
		addMemberButton.addActionListener(obj3);
		
		ActionListener obj4 = new ActionListener()  {
			public void actionPerformed (ActionEvent e)  {
				deleteMemberButtonGui();
			}
		};
		deleteMemberButton.addActionListener(obj4);
		
		ActionListener obj5 = new ActionListener()  {
			public void actionPerformed (ActionEvent e)  {
				rentalBookButtonGui();
			}
		};
		rentalBookButton.addActionListener(obj5);
		
		ActionListener obj6 = new ActionListener()  {
			public void actionPerformed (ActionEvent e)  {
				returnBookGui();
			}
		};
		returnBookButton.addActionListener(obj6);
		
		ActionListener obj7 = new ActionListener()  {
			public void actionPerformed (ActionEvent e)  {
				Book_MS_DB.rentalListAddRow(model);
			}
		};
		rentalListButton.addActionListener(obj7);
		
		ActionListener obj8 = new ActionListener()  {
			public void actionPerformed (ActionEvent e)  {
				Book_MS_DB.allBookDataAddRow(model);
			}
		};
		allListButton.addActionListener(obj8);
		
		ActionListener obj9 = new ActionListener()  {
			public void actionPerformed (ActionEvent e)  {
				rentalMemberGui();
			}
		};
		memberIndexButton.addActionListener(obj9);
		
		ActionListener obj10 = new ActionListener()  {
			public void actionPerformed (ActionEvent e)  {
				String titleStr = title.getText();
				String writerStr = writer.getText();
				String publisherStr = publisher.getText();
				String codeStr = code.getText();
				if (titleStr.equals("") && writerStr.equals("") && publisherStr.equals(""))  {
					if (!codeStr.equals(""))  {
						Book_MS_DB.allRemoveRowData(model);
						Book_MS_DB.rentalBookIndex(codeStr, model);
						return;
					}	
					else  {
						System.out.println("�˻������� �Ѱ� �̻� �Է����ּ���");
						return;
					}
				}
				Book_MS_DB.allRemoveRowData(model);
				Book_MS_DB.rentalBookIndex(titleStr, writerStr, publisherStr, table);
			}
		};
		indexButton.addActionListener(obj10);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
	
	public void addBookButtonGui()  { // �����߰� ��ư ������ ������ GUI
		JFrame frame = new JFrame("�߰��� �������� �Է�");
		frame.setLocation(130,100);
		frame.setPreferredSize(new Dimension(400,200));
		Container contentPane = frame.getContentPane();
		JTextField bookName = new JTextField();
		JTextField writer = new JTextField();
		JTextField publisher = new JTextField();
		JTextField code = new JTextField();
		JButton button = new JButton("Ȯ��");
		JPanel panel1 = new JPanel();
		panel1.setLayout(new GridLayout(4, 2));
		panel1.add(new JLabel("������"));
		panel1.add(bookName);
		panel1.add(new JLabel("����"));
		panel1.add(writer);
		panel1.add(new JLabel("���ǻ�"));
		panel1.add(publisher);
		panel1.add(new JLabel("�ڵ�"));
		panel1.add(code);
		contentPane.add(panel1);
		contentPane.add(button, BorderLayout.SOUTH);
		
		ActionListener obj1 = new ActionListener()  {
			public void actionPerformed (ActionEvent e)  {
				String str1 = bookName.getText();
				String str2 = writer.getText();
				String str3 = publisher.getText();
				String str4 = code.getText();
				Book_MS_DB.bookSave_DB(str1, str2, str3, str4);
				frame.setVisible(false);
			}
		};
		button.addActionListener(obj1);
		
		frame.pack();
		frame.setVisible(true);
	}
	
	public void deleteBookButtonGui()  { // �������� ��ư ������ ������ GUI
		JFrame frame = new JFrame("������ �����ڵ� �Է�");
		frame.setLocation(130,100);
		frame.setPreferredSize(new Dimension(270,100));
		Container contentPane = frame.getContentPane();
		JTextField code = new JTextField();
		JButton button = new JButton("Ȯ��");
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1, 1));
		panel.add(new JLabel("�����ڵ�"));
		panel.add(code);
		contentPane.add(panel);
		contentPane.add(button, BorderLayout.SOUTH);
		
		ActionListener obj1 = new ActionListener()  {
			public void actionPerformed (ActionEvent e)  {
				String codeStr = code.getText();
				Book_MS_DB.bookDelete_DB(codeStr);
				frame.setVisible(false);
			}
		};
		button.addActionListener(obj1);
		
		frame.pack();
		frame.setVisible(true);
	}
	
	public void addMemberButtonGui()  { // ȸ����� ��ư�� ������ ������ GUI
		JFrame frame = new JFrame("����� ȸ������ �Է�");
		frame.setLocation(130, 100);
		frame.setPreferredSize(new Dimension(480,160));
		Container contentPane = frame.getContentPane();
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(3,2));
		JTextField memberName = new JTextField();
		JTextField address = new JTextField();
		JTextField phone = new JTextField();
		JButton button = new JButton("Ȯ��");
		panel.add(new JLabel("�̸�"));
		panel.add(memberName);
		panel.add(new JLabel("�ּ�"));
		panel.add(address);
		panel.add(new JLabel("�ڵ�����ȣ"));
		panel.add(phone);
		contentPane.add(panel);
		contentPane.add(button, BorderLayout.SOUTH);
		
		ActionListener obj1 = new ActionListener()  {
			public void actionPerformed (ActionEvent e)  {
				String memberNameStr = memberName.getText();
				String addressStr = address.getText();
				String phoneStr = phone.getText();
				Book_MS_DB.memberSave_DB(memberNameStr, addressStr, phoneStr);
				frame.setVisible(false);
			}
		};
		button.addActionListener(obj1);
		
		frame.pack();
		frame.setVisible(true);
	}
	
	public void deleteMemberButtonGui()  { // ȸ������ ��ư�� ������ ������ GUI
		JFrame frame = new JFrame("������ ȸ���� ��ȭ��ȣ �Է�");
		frame.setLocation(130, 100);
		frame.setPreferredSize(new Dimension(300,100));
		Container contentPane = frame.getContentPane();
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,2));
		JButton button = new JButton("Ȯ��");
		JTextField phone = new JTextField();
		panel.add(new JLabel("��ȭ��ȣ"));
		panel.add(phone);
		contentPane.add(panel);
		contentPane.add(button, BorderLayout.SOUTH);
		
		ActionListener obj1 = new ActionListener()  {
			public void actionPerformed (ActionEvent e)  {
				String phoneStr = phone.getText();
				Book_MS_DB.memberDelete_DB(phoneStr);
				frame.setVisible(false);
			}
		};
		button.addActionListener(obj1);
		
		frame.pack();
		frame.setVisible(true);
	}
	
	public void rentalBookButtonGui()  { // �뿩 ��ư�� ������ ������ GUI
		JFrame frame = new JFrame("�뿩(å ����)");
		frame.setLocation(130, 100);
		frame.setPreferredSize(new Dimension(700,200));
		Container contentPane = frame.getContentPane();
		JTextField title = new JTextField(15);
		JTextField writer = new JTextField(4);
		JTextField publisher = new JTextField(6);
		JButton indexButton = new JButton("�˻�");
		JButton button = new JButton("Ȯ��");
		JPanel panel = new JPanel();
		panel.add(new JLabel("������"));
		panel.add(title);
		panel.add(new JLabel("����"));
		panel.add(writer);
		panel.add(new JLabel("���ǻ�"));
		panel.add(publisher);
		panel.add(indexButton);
		contentPane.add(panel, BorderLayout.NORTH);
		String colNames[] = {"������", "����", "���ǻ�", "�ڵ�", "�뿩����"};
		DefaultTableModel model = new DefaultTableModel(colNames, 0);
		JTable table = new JTable(model);
		contentPane.add(new JScrollPane(table));
		contentPane.add(button, BorderLayout.SOUTH);
		
		ActionListener obj1 = new ActionListener()  {
			public void actionPerformed (ActionEvent e)  {
				int count = table.getRowCount();
				for (int cnt = 0; cnt < count; cnt++)  {
					model.removeRow(0);
				}
				Book_MS_DB.rentalBookIndex(title.getText(), writer.getText(), publisher.getText(), table);
			}
		};
		indexButton.addActionListener(obj1);
		
		ActionListener obj2 = new ActionListener()  {
			public void actionPerformed (ActionEvent e)  {
				String selectBook[] = Book_MS_DB.getTableData(table);
				if (selectBook == null)  {
					System.out.println("������ ������ �ּ���");
					return;
				}
				else if (selectBook[4].equals("�뿩��"))  {
					System.out.println("�̹� �뿩���� å�Դϴ�");
					return;
				}
				frame.setVisible(false);
				rentalMemberGui(selectBook);
			}
		};
		button.addActionListener(obj2);
		
		frame.pack();
		frame.setVisible(true);
	}
	
	public void rentalMemberGui(String selectBook[])  { // �뿩 ��ư -> ���� ���� �� ȯ���� ������ ������ GUI
		JFrame frame = new JFrame("�뿩(ȸ������)");
		frame.setLocation(130, 100);
		frame.setPreferredSize(new Dimension(700,200));
		Container contentPane = frame.getContentPane();
		JTextField name = new JTextField(4);
		JButton indexButton = new JButton("�˻�");
		JButton button = new JButton("Ȯ��");
		JPanel panel = new JPanel();
		panel.add(new JLabel("�̸�"));
		panel.add(name);
		panel.add(indexButton);
		contentPane.add(panel, BorderLayout.NORTH);
		String colNames[] = {"�̸�", "�ּ�", "��ȭ��ȣ"};
		DefaultTableModel model = new DefaultTableModel(colNames, 0);
		JTable table = new JTable(model);
		contentPane.add(new JScrollPane(table));
		contentPane.add(button, BorderLayout.SOUTH);
		
		ActionListener obj1 = new ActionListener()  {
			public void actionPerformed (ActionEvent e)  {
				int count = table.getRowCount();
				for (int cnt = 0; cnt < count; cnt++)  {
					model.removeRow(0);
				}
				String nameStr = name.getText();
				Book_MS_DB.rentalMemberIndex(nameStr, table);
			}
		};
		indexButton.addActionListener(obj1);
		
		ActionListener obj2 = new ActionListener()  {
			public void actionPerformed (ActionEvent e)  {
				String selectMember[] = Book_MS_DB.getTableData(table);
				boolean b = Book_MS_DB.mrb_list(selectMember[2]);
				if (!b)  {
					System.out.println("�뿩�Ұ� ȸ��(�뿩���� �� �ʰ�)");
					return;
				}
				Book_MS_DB.rental_DB(selectBook, selectMember);
				frame.setVisible(false);
			}
		};
		button.addActionListener(obj2);
		
		frame.pack();
		frame.setVisible(true);
	}
	
	public void returnBookGui()  { // �ݳ� ��ư�� ������ ������ GUI
		JFrame frame = new JFrame("�ݳ��� ������ �ڵ� �Է�");
		frame.setLocation(130,100);
		frame.setPreferredSize(new Dimension(270,100));
		Container contentPane = frame.getContentPane();
		JTextField code = new JTextField();
		JButton button = new JButton("Ȯ��");
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1, 1));
		panel.add(new JLabel("�����ڵ�"));
		panel.add(code);
		contentPane.add(panel);
		contentPane.add(button, BorderLayout.SOUTH);
		
		ActionListener obj1 = new ActionListener()  {
			public void actionPerformed (ActionEvent e)  {
				String codeStr = code.getText();
				Book_MS_DB.returnBook_DB(codeStr);
				frame.setVisible(false);
			}
		};
		button.addActionListener(obj1);
		
		frame.pack();
		frame.setVisible(true);
	}
	
	public void rentalMemberGui()  { // ȸ������Ʈ ��ư�� ������ ������ GUI
		JFrame frame = new JFrame("ȸ������Ʈ");
		frame.setLocation(130, 100);
		frame.setPreferredSize(new Dimension(1100,400));
		Container contentPane = frame.getContentPane();
		JTextField name = new JTextField(4);
		JButton indexButton = new JButton("�˻�");
		JButton button = new JButton("��ü����");
		JPanel panel = new JPanel();
		panel.add(new JLabel("�̸�"));
		panel.add(name);
		panel.add(indexButton);
		contentPane.add(panel, BorderLayout.NORTH);
		String colNames[] = {"�̸�", "�ּ�", "��ȭ��ȣ", "�뿩����1", "�뿩����2", "�뿩����3"};
		DefaultTableModel model = new DefaultTableModel(colNames, 0);
		Book_MS_DB.allMemberDataAddRow(model);
		JTable table = new JTable(model);
		contentPane.add(new JScrollPane(table));
		contentPane.add(button, BorderLayout.SOUTH);
		
		ActionListener obj1 = new ActionListener()  {
			public void actionPerformed (ActionEvent e)  {
				int count = table.getRowCount();
				for (int cnt = 0; cnt < count; cnt++)  {
					model.removeRow(0);
				}
				String nameStr = name.getText();
				Book_MS_DB.rentalMemberIndex(nameStr, table);
			}
		};
		indexButton.addActionListener(obj1);
		
		ActionListener obj2 = new ActionListener()  {
			public void actionPerformed (ActionEvent e)  {
				Book_MS_DB.allRemoveRowData(model);
				Book_MS_DB.allMemberDataAddRow(model);
			}
		};
		button.addActionListener(obj2);
		
		frame.pack();
		frame.setVisible(true);
	}
}
