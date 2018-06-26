import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

class GUI implements ActionListener {
	static ArrayList<double[]> data;
	JFrame frame = null;
	JLabel labelFile = null;
	JLabel labelConvergence = new JLabel("收斂次數");
	JLabel labelLearn = new JLabel("學習率 : ");
	JTextField inputConvergence = new JTextField(5);
	JTextField inputLearn = new JTextField(5);
	JLabel labalRate = new JLabel("辨識率 : ");
	JTextField outputRate = new JTextField(5);
	JPanel panelBOT = new JPanel();
	coodPaint canvas = new coodPaint();
	JFileChooser fileChooser = null;
	File file = null;
	Perceptron perceptron = new Perceptron();

	public GUI() {
		frame = new JFrame("Perceptron");
		Container contentPane = frame.getContentPane();
		//frame.setSize(640, 480);
		canvas.setSize(640,640);
		JButton btnSelect = new JButton("選擇檔案");
		JButton btnCal = new JButton("開始測試");
		
		outputRate.setEditable(false);
		outputRate.setText("0");
		inputLearn.setText("0.5");
		inputConvergence.setText("100");
		btnSelect.addActionListener(this);
		btnCal.addActionListener(this);
		panelBOT.add(labelLearn);
		panelBOT.add(inputLearn);
		panelBOT.add(labelConvergence);
		panelBOT.add(inputConvergence);
		panelBOT.add(labalRate);
		panelBOT.add(outputRate);
		panelBOT.add(btnSelect);
		panelBOT.add(btnCal);

		labelFile = new JLabel(" ", JLabel.CENTER);

		fileChooser = new JFileChooser();

		contentPane.add(labelFile, BorderLayout.NORTH);
		contentPane.add(canvas, BorderLayout.CENTER);
		contentPane.add(panelBOT, BorderLayout.SOUTH);

		frame.pack();
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}

	public void actionPerformed(ActionEvent e) {

		int result;
		if (e.getActionCommand().equals("開始測試")&&file!=null) {
			perceptron.initialData(Double.valueOf(inputLearn.getText()), Double.valueOf(inputConvergence.getText()));
			double[] w = perceptron.train();
			canvas.drawLine(w);
			String per  = "%";
			outputRate.setText(String.format("%.2f %s", perceptron.test(),per));
		}
		
		if (e.getActionCommand().equals("選擇檔案")) {
			fileChooser.setDialogTitle("選擇檔案");
			result = fileChooser.showOpenDialog(frame);

			if (result == JFileChooser.APPROVE_OPTION ) {
				file = fileChooser.getSelectedFile();
				labelFile.setText("檔案名稱：" + file.getName());
			
			try {
				data = new ArrayList();
				Scanner input = new Scanner(file);
				
				while (input.hasNext()) {
	
					String tempInput= input.nextLine();
					String[] tempStr = tempInput.split("\\s+");
				    double[] temp = null; 
				
				    //System.out.println("000:\""+tempInput+"\"");
					if(tempStr[0].equals("")){
						int space = 1;
					//	System.out.print("tempStr:\""+tempStr[space]+"\"");
						while(tempStr[space].equals("")){
						//	System.out.print("tempStr:\""+tempStr[space]+"\"");
							space++;
						}
						
						temp = new double[tempStr.length-space];
						for(int i = space ; i <tempStr.length ; i++){
							//System.out.println("I:\""+i+"\"");
							//System.out.println("space:\""+space+"\"");
							temp[i-space] = Double.valueOf(tempStr[i]);
						//	System.out.println("tempStr[i]:\""+i+"="+tempStr[i]+"\"");
						}
					}else if(!tempStr[0].equals("")){
						temp = new double[tempStr.length];
						for(int i = 0 ; i <tempStr.length ; i++){
						//	System.out.println("tempStr[i]:\""+i+"="+tempStr[i]+"\"");
							temp[i] = Double.valueOf(tempStr[i]);
						}
					}
					
					data.add(temp);
					
					
					
				}
				
				perceptron.setData(data);
					canvas.drawData(data);
		
				
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			}
		}
	}

	
}