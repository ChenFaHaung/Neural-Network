import java.util.ArrayList;

public class Perceptron {
	ArrayList<double[]> data = new ArrayList();
	double learn;
	double convergence;
	double[][] dataTrain;
	double[][] dataTest;
	double[] weight;
	double maxD;
	double minD;
	double testl = 0.5;
	double testc = 100;

	public void initialData(double learnTemp, double convergenceTemp) {
		learn = learnTemp;
		convergence = convergenceTemp;
		
		
		double[] exchange1 = new double[data.get(0).length];
		double[] exchange2 = new double[data.get(0).length];
		for (int i = 0; i < data.size()*3; i++) {
			int index1 = (int)(Math.random()*data.size());
			int index2 = (int)(Math.random()*data.size());
			exchange1 = data.get(index1);
			exchange2 = data.get(index2);
			data.set(index1,exchange2);
			data.set(index2,exchange1);
		}
		
		dataTrain = new double[data.size() / 3 * 2][weight.length + 1];
		dataTest = new double[data.size() - data.size() / 3 * 2][weight.length + 1];
		maxD = -100;
		minD = 100;
		
		

		for (int i = 0; i < dataTrain.length; i++) {
			for (int j = 0; j < weight.length; j++) {
				dataTrain[i][0] = -1;
				dataTrain[i][j + 1] = data.get(i)[j];
			}
			if (dataTrain[i][dataTrain[i].length - 1] > maxD) {
				maxD = dataTrain[i][dataTrain[i].length - 1];
				// System.out.println("max:"+dataTrain[i][dataTrain[i].length-1]);
			}
			if (dataTrain[i][dataTrain[i].length - 1] < minD) {
				minD = dataTrain[i][dataTrain[i].length - 1];
				// System.out.println("min:"+
				// dataTrain[i][dataTrain[i].length-1]);
			}
		}
		for (int i = dataTrain.length; i < data.size(); i++) {
			for (int j = 0; j < weight.length; j++) {
				dataTest[i - dataTrain.length][0] = -1;
				dataTest[i - dataTrain.length][j + 1] = data.get(i)[j];
			}
			if (dataTest[i - dataTrain.length][dataTest[i - dataTrain.length].length - 1] > maxD) {
				maxD = dataTest[i - dataTrain.length][dataTest[i
						- dataTrain.length].length - 1];
				// System.out.println("max:"+maxD);
			}
			if (dataTest[i - dataTrain.length][dataTrain[i - dataTrain.length].length - 1] < minD) {
				minD = dataTest[i - dataTrain.length][dataTest[i
						- dataTrain.length].length - 1];
				// System.out.println("max:"+minD);
			}
		}
		
		
		

		

	}

	public void setData(ArrayList<double[]> temp) {
		data = temp;
		
		
		

		weight = new double[data.get(0).length];
		System.out.print("initial weight : ");
		print(weight);
	}

	public double[] train() {
		
		for (int i = 0; i < weight.length; i++) {
			weight[i] = (int) Math.random() * 2 - 1;
		}
		if(learn == 0){
			learn = testl;
		}
		if(convergence == 0){
			convergence = testc;
		}
		// test
		//System.out.print("learn : "+learn);
		//System.out.println("  convergence : "+convergence);
		boolean allGreen = false;
		
	
		double con = convergence;
		// System.out.println(maxD+","+minD);
		// for(int i = 0 ; i <dataTrain.length;i++){
		// for(int j = 0 ; j<weight.length;j++){
		// System.out.print(dataTrain[i][j]+" ");
		// }
		// System.out.println();
		// }

		while (con>0) {
			if(allGreen){
				System.out.println("all green!");
				break;
			}
			allGreen = true;
			con--;
			double d = 0;
			double temp = 0;
			for (int i = 0; i < dataTrain.length; i++) {
				temp = 0;
				for (int j = 0; j < dataTrain[0].length - 1; j++) {
					temp += weight[j] * dataTrain[i][j];
				}
				if (temp > 0) {
					d = maxD;
				} else {
					d = minD;
				}
				if (d != dataTrain[i][dataTrain[i].length - 1]) {
					double s =convergence-con;
					//System.out.println("turn is  :  "+s);
					//System.out.println("n is  :  "+i);
					allGreen = false;
					if (d == maxD) {
						//System.out.println("ans is min  :  ");
						print(weight);
						for (int j = 0; j < weight.length; j++) {
							
							weight[j] = weight[j]-dataTrain[i][j]* learn;
							
						}
						
						print(weight);
					} else {
						//System.out.println("ans is max  :  ");
						print(weight);
						for (int j = 0; j < weight.length; j++) {
							weight[j] = weight[j]+dataTrain[i][j]* learn;
						}
						
						print(weight);
					}
				}
			}
		}
		System.out.print("ending weight : ");
		print(weight);
		return weight;
	}

	public double test() {
		double d = 0;
		double temp = 0;
		double correct = 0;
		double wrong = 0;
		for (int i = 0; i < dataTest.length; i++) {
			temp = 0;
			for (int j = 0; j < dataTest[0].length - 1; j++) {
				temp += weight[j] * dataTest[i][j];
			}
			if (temp > 0) {
				d = maxD;
			} else {
				d = minD;
			}
			if (d != dataTest[i][dataTest[i].length - 1]) {
				//System.out.println(" d is : "+d + " data : "+dataTest[i][dataTest[i].length - 1]);
				wrong++;
			}else{
				correct++;
			}
		}
		System.out.println(correct+"  "+wrong);
		return correct/(correct+wrong)*100;
	}
	

	public static void print(double[] temp) {

		for (int i = 0; i < temp.length; i++) {
			System.out.print(temp[i] + " ");
		}
		System.out.println();
	}

}
