import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;



public  class Calculator extends JFrame implements ActionListener{
	//** 计算器上的键的显示名字 */
	private final String[] KEYS = 
		   {"7", "8", "9", "/", "sqrt", "4", "5", "6","*", "%", 
			"1", "2", "3", "-", "1/x", "0", "+/-", ".", "+", "="};
	private final String[] COMMAND = {"Backspace","CE","C" };
	private final String[] M = {" ","MC","MR","MS","M+"};
	
	//** 计算器上键的按钮 */
	private JButton keys[] = new JButton[KEYS.length];
	private JButton commands[] = new JButton[COMMAND.length];
	private JButton m[] = new JButton[M.length];
	//** 计算结果文本框 */
	private JTextField resultText = new JTextField ("0");
	
	//** 标志用户按的是否整个表达式的第一个数字，或者是运算符后的第一个数字 */
	private boolean firstDight = true;
	//** 计算的中间结果 */
	private double resultNum = 0.0;
	//** 当前运算的运算符 */
	private String operator = "=";
	//** 操作是否合法 */
	private boolean operateValidFlag = true;
	
	
	public Calculator() 
	{
		super();
		init();
		this.setBackground(Color.LIGHT_GRAY);
		this.setTitle("计算器");
		this.setLocation(500,300);
		this.setResizable(true);
		this.pack();
		
	}
	
	private void init() {
		//** 文本框中的内容右对齐 、不可修改、背景颜色为白色 */
		resultText.setHorizontalAlignment(JTextField.RIGHT);
		resultText.setEditable(false);
		resultText.setBackground(Color.WHITE);
		
		//** 初始化数字键与运算键的按钮，将键放在一个画板内 */
		//** 用网格布局器，4行5列的网络 */
		//** 运算键
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
		
		//** 初始化功能键的按钮，将键放在一个画板内 */
		JPanel commandsPanel = new JPanel();
		commandsPanel.setLayout(new GridLayout(1,3,3,3));
		for(int i = 0; i < COMMAND.length; i++)
		{
			commands[i] = new JButton(COMMAND[i]);
			commandsPanel.add(commands[i]);
			commands[i].setForeground(Color.RED);
		}
		
		//** 初始化M键，用红色标示，将M键放在一个画板内
		JPanel calmsPanel = new JPanel();
		calmsPanel.setLayout(new GridLayout(5,1,3,3));
		for(int i = 0; i < M.length; i++)
		{
			m[i] = new JButton(M[i]);
			calmsPanel.add(m[i]);
			m[i].setForeground(Color.RED);
		}
		
		//** 下面进行整体布局，将calckeys和command画板放在计算器的中部，*/
		//** 将文本框放在北部，将calms画板放在计算机的中部。*/
		
		//** 新建一个大的画板，将上面建立的command和calckeys画板放在该画板内	
		JPanel panel1 = new JPanel();
		//** 画板采用边界布局管理器，画板里组件之间的水平和垂直方向上的间隔都为3像素
		panel1.setLayout(new BorderLayout(3,3));
		panel1.add("North",commandsPanel);
		panel1.add("Center",calckeysPanel);
		
		//** 建立一个画板放文本框commandsPanel
		JPanel top = new JPanel();
		top.setLayout(new BorderLayout());
		top.add("Center",resultText);
		
		//** 整体布局 */
		getContentPane().setLayout(new BorderLayout(3,5));
		getContentPane().add("North",top);
		getContentPane().add("Center",panel1);
		getContentPane().add("West",calmsPanel);
		
		//** 为各按钮添加事件监听器 */
		//** 都使用同一个时间监听器，即本对象。本类的声明中中implements ActionListener */
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
	
	//** 处理事件 */
	public void actionPerformed(ActionEvent e)
	{
		//** 获取事件源的标签 */
		String label = e.getActionCommand();
		if(label.equals(COMMAND[0])) {
			//** 用户按了"Backspace"键 */
			handleBackspace();
		}else if(label.equals(COMMAND[1])) {
			//** 用户按了“CE”键 */
			resultText.setText("0");
		}
		else if(label.equals(COMMAND[2])) {
			//** 用户按了“C”键 */
			resultText.setText("0");
			handleC();
		}else if("0123456789.".indexOf(label) >= 0) {
			//** 用户按了数字键或者小数点键 */
			resultText.setText("0");
			handleNumber(label);
		}else  {
			//** 用户按了运算符键 */
			handleOperator(label);
	}
 }
	
	//** 处理运算键被按下的事件 */
	private void handleOperator(String key) {
		// TODO 自动生成的方法存根
		if(operator.equals("/")){
			//** 除法运算 */
			//** 如果当前结果文本框中的值等于0 */
			if(getNumberFromText() == 0.0){
			//** 操作不合法
				operateValidFlag = false;
				resultText.setText("除数不能为0");	
			}else {
				resultNum = resultNum / getNumberFromText();
			}
		}else if (operator.equals("1/x")) {
			if (resultNum == 0.0) {
				operateValidFlag = false;
				resultText.setText("零没有倒数");
			}else {
				resultNum = 1 / resultNum;
			}
		}else if (operator.equals("+")) {
			//** 加法运算 */
			resultNum = resultNum + getNumberFromText();
		}else if (operator.equals("-")) {
			//** 减法运算 */
			resultNum = resultNum - getNumberFromText();
		}else if (operator.equals("*")) {
			//** 乘法运算 */
			resultNum = resultNum * getNumberFromText();
		}else if (operator.equals("sqrt")) {
			//** 平方根运算 */
			resultNum = Math.sqrt(resultNum);
		}else if (operator.equals("%")) {
			//** 百分号运算，除以100 */
			resultNum = resultNum / 100;
		}else if (operator.equals("+/-")) {
			resultNum = resultNum * (-1);
		}else if (operator.equals("=")) {
			resultNum = getNumberFromText();
		}
		
		if(operateValidFlag) {
			//** 双精度浮点型数的运算
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
		//** 运算符等于用户按的按钮
		operator = key;
		firstDight = true;
		operateValidFlag = true;
	}
	
	//** 从结果文本框获取数字 */
	private double getNumberFromText() {
		// TODO 自动生成的方法存根
		double result = 0;
		try {
			result = Double.valueOf(resultText.getText()).doubleValue();
		} catch (NumberFormatException e) {	
		}
		return result;
	}

	private void handleNumber(String key) {
		// TODO 自动生成的方法存根
		if(firstDight) {
			//** 输入的是第一个数字
			resultText.setText(key);
		}else if ((key.equals(".")) && (resultText.getText().indexOf(".") < 0)){
			//** 输入的是小数点，并且之前没有小数点的，则将数字附在结果文本框的后面
			resultText.setText(resultText.getText() + ".");
		}else if (!key.equals(".")) {
			//** 如果输入的不是小数点，则将数字附在结果文本框的后面
			resultText.setText(resultText.getText() + key);
		}
		//** 以后输入的肯定不是第一个数字了 */
		firstDight = false;
	}

	private void handleC() {
		// TODO 自动生成的方法存根
		resultText.setText("0");
		firstDight = true ;
		operator = "=";
	}

	private void handleBackspace() {
		// TODO 自动生成的方法存根
		String text = resultText.getText();
		int i = text.length();
		if(i > 0) 
		{
			//** 退格，将文本最后一个字符去掉 */
			text = text.substring(0,i - 1);
			if(text.length() == 0) 
			{
				//** 如果文本没有了内容，则初始化计算器的各种值
				resultText.setText("0");
				firstDight = true;
				operator = "=";
			}else {
				//** 显示新的文本
				resultText.setText(text);
			}
		}	
	}

	public static void main(String[] args) {
		Calculator	calculator1 = new Calculator();
		calculator1.setVisible(true);
		calculator1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
