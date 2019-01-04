import java.awt.EventQueue;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JTextArea;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.JCheckBoxMenuItem;

public class driver {

	private JFrame frame;
	private JTextField textField;
	static String[] Data=new String[200000];
	static String CurrFile;
	static FileNameExtensionFilter filter = new FileNameExtensionFilter(
			"Data File", "txt");
	static double[] values=new double[200000];
	static String[] candidate=new String[15];
	static double[] position=new double[100];
	static double[] candV=new double[15];
	static String input=null;
	static int answer;
	private static JTextArea textArea;
	private JMenuBar menuBar;
	private JMenu mnNewMenu;
	private JMenuItem mntmNewMenuItem;
	private JMenuItem mntmNewMenuItem_1;
	private static boolean sc=false;
	private JMenuItem mntmShowCorrectness;
	private int words=0;
    private String text;
	/**
	 * Launch the application.
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					driver window = new driver();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		OpenFile();
	}

	/**
	 * Create the application.
	 */
	public driver() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		textField = new JTextField();
		frame.getContentPane().add(textField, BorderLayout.NORTH);
		textField.setColumns(10);
		textField.getDocument().addDocumentListener(new DocumentListener() {
			  public void changedUpdate(DocumentEvent e) {
			    try {
					//warn();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			  }
			  public void removeUpdate(DocumentEvent e) {
			    try {
					warn();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			  }
			  public void insertUpdate(DocumentEvent e) {
			    try {
					warn();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			  }

			  public void warn() throws IOException {
				  String[] tmp;
			      if(textField.getText().length()>=1){
			    	  tmp=textField.getText().split(" ");
			    	  input=tmp[tmp.length-1];
			      }else{
			    	  tmp=new String[]{};
			    	  input=textField.getText();
			      }
			      start();
			      text=textField.getText();
			      if(text.length()>0){
			    	  if(text.charAt(text.length()-1)==' '&&input!=candidate[0]){
			    		  tmp[tmp.length-1]=candidate[0];
			    		  text="";
			    		  for(int i=0;i<tmp.length-1;i++){
			    			  text+=tmp[i]+" ";
			    			  System.out.println(" here  " +tmp.length);
			    		  }
			    		  text+=candidate[0]+" ";
			    		  highlight();
			    	  }
			      }
			      
			  }
			  private void highlight() {

				    Runnable doHighlight = new Runnable() {
				        @Override
				        public void run() {
//				        	System.out.println("sewe");
				            textField.setText(text);// your highlight code
				        }
				    };       
				    SwingUtilities.invokeLater(doHighlight);
				}
			});
		
		textArea = new JTextArea();
		frame.getContentPane().add(textArea, BorderLayout.CENTER);
		
		menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		mnNewMenu = new JMenu("Option");
		menuBar.add(mnNewMenu);
		
		mntmNewMenuItem = new JMenuItem("Open Dictionary");
		mnNewMenu.add(mntmNewMenuItem);
		
		mntmShowCorrectness = new JMenuItem("Show Correctness");
		mnNewMenu.add(mntmShowCorrectness);
		
		mntmNewMenuItem_1 = new JMenuItem("teach");
		mnNewMenu.add(mntmNewMenuItem_1);
		
		learn l=new learn();
		mntmNewMenuItem_1.addActionListener(l);
		
		openFile o=new openFile();
		mntmNewMenuItem.addActionListener(o);
		
		showC cc=new showC();
		mntmShowCorrectness.addActionListener(cc);
		textArea.setEditable(false);
		textArea.addKeyListener(new KeyListener(){
			public void keyTyped(KeyEvent e) {
		       try {
				change(e);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		    }

			private void change(KeyEvent e) throws IOException {
				int index=Integer.parseInt(""+e.getKeyChar())-1;
				System.out.println(index);// TODO Auto-generated method stub
				pick(index);
			}
			 public void pick(int index) throws IOException {
				  String[] tmp;
			      if(textField.getText().length()>=1){
			    	  tmp=textField.getText().split(" ");
			    	  input=tmp[tmp.length-1];
			      }else{
			    	  tmp=new String[]{};
			    	  input=textField.getText();
			      }
			      start();
			      text=textField.getText();
			      if(text.length()>0){
			    	  
			    		  tmp[tmp.length-1]=candidate[0];
			    		  text="";
			    		  for(int i=0;i<tmp.length-1;i++){
			    			  text+=tmp[i]+" ";
//			    			  System.out.println(" here  " +tmp.length);
			    		  }
			    		  text+=candidate[index];
			    		  textField.setText(text);
			    	  
			      }
			      answer=index;
	             
	        	    // get the user's input. note that if they press Cancel, 'name' will be null
	        	 if(answer==-1){// add a new word
	            	for(int i=1;i<Data.length;i++){
	            		if(Data[i]==null){
	            			System.out.println("i="+i);
	            			Data[i]=input;
	            			break;
	            		}
	            	}
	            }else{//learn the importance of the position
	            	learn(candidate[answer]);
	            }
	            SaveFile();
	            try {
					readFile(CurrFile);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			  }

			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
	}
	public class bt1 implements ActionListener {
        public void actionPerformed(ActionEvent VH) {
        	
        }
    }
	public class learn implements ActionListener {
        public void actionPerformed(ActionEvent VH) {
        	 JFrame frame = new JFrame("InputDialog Example #1");

        	    // prompt the user to enter their name
        	    String name = JOptionPane.showInputDialog(frame, "input the number of the correct word, input 0 to add this word");
              answer=Integer.parseInt(name);
              answer--;
        	    // get the user's input. note that if they press Cancel, 'name' will be null
        	if(answer==-1){// add a new word
            	for(int i=1;i<Data.length;i++){
            		if(Data[i]==null){
            			System.out.println("i="+i);
            			Data[i]=input;
            			break;
            		}
            	}
            }else{//learn the importance of the position
            	learn(candidate[answer]);
            }
            SaveFile();
            try {
				readFile(CurrFile);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    }
	
	public class openFile implements ActionListener {
        public void actionPerformed(ActionEvent VH) {
        	try {
				OpenFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    }
	public class showC implements ActionListener {
        public void actionPerformed(ActionEvent VH) {
        	sc=!sc;
        	if(sc){
        		mntmShowCorrectness.setText("Hide Correctness");
        	}else{
        		mntmShowCorrectness.setText("Show Correctness");
        	}
        	
        }
    }
	
	private static void start() throws IOException {//flow control
		
        proccess();
        findCandidates();
//        if(checkInDic()){
//        	System.out.println("your word is in the dictionary, here are some options for you to correct your word");
//        }else{
//        	System.out.println("your input was not in the dictionary, here are some options for you to correct your word");
//        }
        String output="";
        for(int i=0;i<15;i++){
        	output+=((i+1)+"."+candidate[i]);
        	if(sc){
        		output+=" correctness= "+candV[i];
        	}
        	output+="\n";
        }
        textArea.setText(output);
//        System.out.println("\ninput the number of the correct word you want, input 0 if you want to add your input to the dictionary");
//        answer=(s.nextInt()-1);
//        if(answer==-1){// add a new word
//        	for(int i=1;i<Data.length;i++){
//        		if(Data[i]==null){
//        			System.out.println("i="+i);
//        			Data[i]=input;
//        			break;
//        		}
//        	}
//        }else{//learn the importance of the position
//        	learn(candidate[answer]);
//        }
//        SaveFile();
	}

	private static boolean checkInDic() {//check if the word is in the dictionary
		for(int i=1;i<Data.length;i++){
			if(input.equals(Data[i])){
				return true;
			}
		}
		return false;
	}
	private static void learn(String string) {//learn the importance of the position
		double cur=0;
		for(int j=0;j<string.length();j++){
			cur=0;
			for(int i=0;i<input.length();i++){
				double tmp=0;
				if(input.charAt(i)==string.charAt(j)){
					tmp=normal(1,4.0*j/string.length(),4.0*i/input.length())/normal(1,1,1);//find the value of every letter
					if(tmp>cur){
						cur=tmp;
					}
				}
			}
			learnPosition(cur,j*1.0/(string.length()-1));
		}
	}
	
	private static void learnPosition(double cur,double posi) {//modify the position array
		double f=1-cur;
		double f1=1-cur;
		int i=0;
	    for(i=0;i<position.length;i++){// find mean
	    	if(posi<(i)*0.01){
	    		break;
	    	}
	    }
	    i--;
	    int i1=i;
	    for(;(i>0&&f>0);i--){//build the left triangle 
	    	position[i]+=f;
	    	f-=0.01;
	    }
	    for(;(i1<position.length&&f1>0);i1++){//build the right triangle
	    	position[i1]+=f1;
	    	f1-=0.01;
	    }
	}
	private static void findCandidates() {// find the candidates correction words 
		for(int j=0;j<15;j++){
		    double temp=0;
		    int tmpIndex=0;
			for(int i=0;i<values.length;i++){
			    if(values[i]>temp){
			    	temp=values[i];
			    	tmpIndex=i;
			    }
		    }
			if(tmpIndex!=0){
				candV[j]=values[tmpIndex];
				candidate[j]=Data[tmpIndex];
			    values[tmpIndex]=0;
			}
			
		}
	}
	private static void proccess() {//walk throught the dictionary
		for(int i=1;i<Data.length;i++){
			if(Data[i]==null||Data[i].length()==0){
				continue;
			}
				values[i]=valueOfWord(Data[i]);
		}
	}
	private static double valueOfWord(String string) {
		double sum=0;
		double cur=0;
		double positionTotal=0;
		for(int i=0;i<string.length();i++){
			positionTotal+=FindPostionValue(i,string.length());
		}
		for(int j=0;j<string.length();j++){
			cur=0;
			for(int i=0;i<input.length();i++){
				double tmp=0;
				
				if(input.charAt(i)==string.charAt(j)){
					tmp=normal(1,4.0*j/string.length(),4.0*i/input.length())/normal(1,1,1);
					if(tmp>cur){
						
						cur=tmp;// max rule
						
					}
				}
			}
			sum+=cur*FindPostionValue(j,string.length())/positionTotal;//add every single letter's value
		}
		if(string.length()>=input.length()){//length is weighted
			sum=(Math.sqrt((input.length()*1.0)/(string.length())))*sum;
		}else{
			sum=(Math.sqrt((string.length()*1.0)/(input.length())))*sum;
		}
		return sum;
	}
	private static double FindPostionValue(int curPosi, int length) {//find the posistion in the array
		double max=0;
		for(int i=0;i<position.length;i++){
			if(position[i]>max){
				max=position[i];
			}
		}
		for(int i=0;i<position.length;i++){
	    	if(curPosi*1.0/(length)<(i)*0.01){
	    		return (max*1.05)-position[i-1];
	    	}
	    }
		return 0;
	}
	static double normal(double sd, double mean, double value){// normal disturbution function
		double num=(1/(sd*Math.sqrt(Math.PI*2)))*Math.pow(Math.E, -Math.pow(value-mean, 2)/2*Math.pow(sd, 2)); 
		return num; 
		}
	private static void OpenFile() throws IOException{//make sure the address of the dictionary
		JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(filter);
        int state = fileChooser.showOpenDialog(null);
        if (state == fileChooser.APPROVE_OPTION) {
         CurrFile = fileChooser.getSelectedFile().getAbsolutePath();
        }
         readFile(CurrFile);
	}
	static void readFile(String fileName) throws IOException { //save the whole file in a string arraylist.
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        ArrayList<String> code = new ArrayList<String>();
        String[] tmp;
        try {
            String line = br.readLine();

            while (line != null) {
                code.add(line);
                line = br.readLine();          																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																									               }																																																																																																																																																																																																																																																																																																																	
        } finally {
            br.close();
        }
        tmp=code.get(0).split(",");
        if(tmp.length==100){//position array provided
        	System.out.println("position array found");
        	for(int i=0;i<position.length;i++){
        	    position[i]=Double.parseDouble(tmp[i]);
        	    for(int j=1;j<code.size();j++){
              		 Data[j]=code.get(j);
                }
            }
        }else{// no position array
        	System.out.println("position array not found, new array created");
        	for(int i=0;i<position.length;i++){
        		 position[i]=1;
        		 for(int j=0;j<code.size();j++){
              		 Data[j+1]=code.get(j);
                }
        	}
        } 
    }
	private static void SaveFile() {
		try {
			FileWriter fw = new FileWriter(CurrFile);
		    BufferedWriter bufw = new BufferedWriter(fw); 
		    bufw.write(""+position[0]);//save the posistion array
		    for(int i=1;i<position.length;i++){
    		bufw.write(","+position[i]);
    	    }
		    bufw.newLine();
		    for(int i=1;i<Data.length;i++){//save the words
		     if(Data[i]==null){
		    	 continue;
		     }
			 bufw.write(Data[i]);
			 bufw.newLine();
		    }
			bufw.flush();
			bufw.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
}
