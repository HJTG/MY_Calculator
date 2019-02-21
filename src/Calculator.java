import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;



public  class Calculator extends JFrame implements ActionListener{
	//** �������ϵļ�����ʾ���� */
	private final String[] KEYS = 
		   {"7", "8", "9", "/", "sqrt", "4", "5", "6","*", "%", 
			"1", "2", "3", "-", "1/x", "0", "+/-", ".", "+", "="};
	private final String[] COMMAND = {"Backspace","CE","C" };
	private final String[] M = {" ","MC","MR","MS","M+"};
	
	//** �������ϼ��İ�ť */
	private JButton keys[] = new JButton[KEYS.length];
	private JButton commands[] = new JButton[COMMAND.length];
	private JButton m[] = new JButton[M.length];
	//** �������ı��� */
	private JTextField resultText = new JTextField ("0");
	
	//** ��־�û������Ƿ���������ʽ�ĵ�һ�����֣��������������ĵ�һ������
	private boolean firstDight = true;
	//** ������м���
	private double resultNum = 0.0;
	//** ��ǰ����������
	private String operator = "=";
	//** �����Ƿ�Ϸ�
	private boolean operateValidFlag = true;
	
	
	public Calculator() 
	{
		super();
		init();
		this.setBackground(Color.LIGHT_GRAY);
		this.setTitle("������");
		this.setLocation(500,300);
		this.setResizable(false);
		this.pack();
		
	}
	
	private void init() {
		//** �ı����е������Ҷ��� �������޸ġ�������ɫΪ��ɫ */
		resultText.setHorizontalAlignment(JTextField.RIGHT);
		resultText.setEditable(false);
		resultText.setBackground(Color.WHITE);
		
		//** ��ʼ�����ּ���������İ�ť����������һ�������� */
		//** �����񲼾�����4��5�е����� */
		//** �����
		JPanel calckeysPanel = new JPanel();
		calckeysPanel.setLayout(new GridLayout(4,5,3,3));
		for (int i = 0; i < KEYS.length;i++ )
		{
			keys[i] = new JButton(KEYS[i]);
			calckeysPanel.add(keys[i]);
			keys[i].setForeground(Color.BLUE);
		}
		keys[3].setForeground(Color.RED);
		keys[8].setForeground(Color.RED);
		keys[13].setForeground(Color.RED);
		keys[18].setForeground(Color.RED);
		keys[19].setForeground(Color.RED);
		
		//** ��ʼ�����ܼ��İ�ť����������һ�������� */
		JPanel commandsPanel = new JPanel();
		commandsPanel.setLayout(new GridLayout(1,3,3,3));
		for(int i = 0; i < COMMAND.length; i++)
		{
			commands[i] = new JButton(COMMAND[i]);
			commandsPanel.add(commands[i]);
			commands[i].setForeground(Color.RED);
		}
		
		//** ��ʼ��M�����ú�ɫ��ʾ����M������һ��������
		JPanel calmsPanel = new JPanel();
		calmsPanel.setLayout(new GridLayout(1,1,3,3));
		for(int i = 0; i < M.length; i++)
		{
			m[i] = new JButton(M[i]);
			calmsPanel.add(m[i]);
			m[i].setForeground(Color.RED);
		}
		
		//** ����������岼�֣���calckeys��command������ڼ��������в���*/
		//** ���ı�����ڱ�������calms������ڼ�������в���*/
		
		//** �½�һ����Ļ��壬�����潨����command��calckeys������ڸû�����	
		JPanel panel1 = new JPanel();
		//** ������ñ߽粼�ֹ����������������֮���ˮƽ�ʹ�ֱ�����ϵļ����Ϊ3����
		panel1.setLayout(new BorderLayout(3,3));
		panel1.add("North",calckeysPanel);
		panel1.add("Center",commandsPanel);
		
		//** ����һ��������ı���
		JPanel top = new JPanel();
		top.setLayout(new BorderLayout());
		top.add("Center",resultText);
		
		//** ���岼�� */
		getContentPane().setLayout(new BorderLayout(3,5));
		getContentPane().add("North",top);
		getContentPane().add("Center",panel1);
		getContentPane().add("West",calmsPanel);
		
		//** Ϊ����ť�����¼������� */
		//** ��ʹ��ͬһ��ʱ����������������󡣱������������implements ActionListener
		for(int i = 0;i < KEYS.length; i++)
		{
			keys[i].addActionListener(this);
		}
		for(int i = 0;i < COMMAND.length; i++)
		{
			commands[i].addActionListener(this);
		}
		for(int i = 0;i <M.length; i++)
		{
			m[i].addActionListener(this);
		}
	
	}
	
	//** �����¼� */
	public void actionPerformed(ActionEvent e)
	{
		//** ��ȡ�¼�Դ�ı�ǩ */
		String label = e.getActionCommand();
		if(label.equals(COMMAND[0])) {
			//** �û�����"Backspace"�� */
			handleBackspace();
		}else if(label.equals(COMMAND[1])) {
			//** �û����ˡ�CE���� */
			resultText.setText("0");
		}
		else if(label.equals(COMMAND[2])) {
			//** �û����ˡ�C���� */
			resultText.setText("0");
			handleC();
		}else if("0123456789.".indexOf(label) >= 0) {
			//** �û��������ּ�����С����� */
			resultText.setText("0");
			handleNumber(label);
		}else  {
			//** �û������������ */
			handleOperator(label);
	}
 }
	

	private void handleOperator(String key) {
		// TODO �Զ����ɵķ������
		if(operator.equals("/")){
			//** �������� */
			//** �����ǰ����ı����е�ֵ����0 */
			if(getNumberFromText() == 0.0){
			//** �������Ϸ�
				operateValidFlag = false;
				resultText.setText("��������Ϊ0");	
			}else {
				resultNum = resultNum / getNumberFromText();
			}
		}else if (operator.equals("1/x")) {
			if (resultNum == 0.0) {
				operateValidFlag = false;
				resultText.setText("��û�е���");
			}else {
				resultNum = 1 / resultNum;
			}
		}else if (operator.equals("+")) {
			//** �ӷ����� */
			resultNum = resultNum + getNumberFromText();
		}else if (operator.equals("-")) {
			//** �������� */
			resultNum = resultNum - getNumberFromText();
		}else if (operator.equals("*")) {
			//** �˷����� */
			resultNum = resultNum * getNumberFromText();
		}else if (operator.equals("sqrt")) {
			//** ƽ�������� */
			resultNum = Math.sqrt(resultNum);
		}else if (operator.equals("%")) {
			//** �ٷֺ����㣬����100 */
			resultNum = resultNum / 100;
		}else if (operator.equals("+/-")) {
			resultNum = resultNum * (-1);
		}else if (operator.equals("=")) {
			resultNum = getNumberFromText();
		}
		if(operateValidFlag) {
			//** ˫���ȸ�������������
			long t1;
			double t2;
			t1 = (long) resultNum;
			t2 = resultNum - t1;
			if(t2 == 0) {
				resultText.setText(String.valueOf(t1));
			}else {
				resultText.setText(String.valueOf(resultNum));
			}
 		}
		//** ����������û����İ�ť
		operator = key;
		firstDight = true;
		operateValidFlag = true;
	}
	
	private double getNumberFromText() {
		// TODO �Զ����ɵķ������
		double result = 0;
		try {
			result = Double.valueOf(resultText.getText()).doubleValue();
		} catch (NumberFormatException e) {	
		}
		return result;
	}

	public static void main(String[] args) {
		Calculator	calculator1 = new Calculator();
		calculator1.setVisible(true);
		calculator1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	

	private void handleNumber(String key) {
		// TODO �Զ����ɵķ������
		if(firstDight) {
			//** ������ǵ�һ������
			resultText.setText(key);
		}else if ((key.equals(".")) && (resultText.getText().indexOf(".") < 0)){
			//** �������С���㣬����֮ǰû��С����ģ������ָ��ڽ���ı���ĺ���
			resultText.setText(resultText.getText() + ".");
		}else if (!key.equals(".")) {
			//** �������Ĳ���С���㣬�����ָ��ڽ���ı���ĺ���
			resultText.setText(resultText.getText() + key);
		}
		//** �Ժ�����Ŀ϶����ǵ�һ�������� */
		firstDight = false;
	}

	private void handleC() {
		// TODO �Զ����ɵķ������
		resultText.setText("0");
		firstDight = true ;
		operator = "=";
	}

	private void handleBackspace() {
		// TODO �Զ����ɵķ������
		String text = resultText.getText();
		int i = text.length();
		if(i > 0) 
		{
			//** �˸񣬽��ı����һ���ַ�ȥ�� */
			text = text.substring(0,i - 1);
			if(text.length() == 0) 
			{
				//** ����ı�û�������ݣ����ʼ���������ĸ���ֵ
				resultText.setText("0");
				firstDight = true;
				operator = "=";
			}else {
				//** ��ʾ�µ��ı�
				resultText.setText(text);
			}
		}	
	}

}