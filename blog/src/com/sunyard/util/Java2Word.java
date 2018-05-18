package com.sunyard.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;



public class Java2Word {
	private boolean saveOnExit;
	private static Log log = LogFactory.getLog(Java2Word.class);
	
	
	private Dispatch doc = null;//word�ĵ�
	private ActiveXComponent word; // word���г������
	private Dispatch documents; //����word�ĵ�

	public Java2Word() {
		saveOnExit = true;
		word = new ActiveXComponent("Word.Application");
		word.setProperty("Visible", new Variant(false));
		documents = word.getProperty("Documents").toDispatch();
	}

	/**
	 * ���ò������˳�ʱ�Ƿ񱣴�
	 * @param saveOnExit
	 * true-�˳�ʱ�����ļ���false-�˳�ʱ�������ļ�
	 */
	public void setSaveOnExit(boolean saveOnExit) {
		this.saveOnExit = saveOnExit;
	}

	/**
	 * �õ��������˳�ʱ�Ƿ񱣴�
	 * @return boolean true-�˳�ʱ�����ļ���false-�˳�ʱ�������ļ�
	 */
	public boolean getSaveOnExit() {
		return saveOnExit;
	}

	/**
	 * ���ļ�
	 * @param inputDoc Ҫ�򿪵��ļ���ȫ·��
	 * @return Dispatch �򿪵��ļ�
	 */
	public Dispatch open(String inputDoc) {
		return Dispatch.call(documents, "Open", inputDoc).toDispatch();
	}
	
    public void openFile(String wordFilePath) {   
        documents = Dispatch.call(Dispatch.get(word, "Documents").toDispatch(), "Open", wordFilePath,   
                new Variant(true),new Variant(false)/* �Ƿ�ֻ�� */).toDispatch();   
    }   

	/**
	 * ѡ������
	 * 
	 * @return Dispatch ѡ���ķ�Χ������
	 */
	public Dispatch select() {
		return word.getProperty("Selection").toDispatch();
	}

	/**
	 * ��ѡ�����ݻ����������ƶ�
	 * 
	 * @param selection
	 *            Ҫ�ƶ�������
	 * @param count
	 *            �ƶ��ľ���
	 */
	public void moveUp(Dispatch selection, int count) {
		for (int i = 0; i < count; i++)
			Dispatch.call(selection, "MoveUp");
	}

	/**
	 * ��ѡ�����ݻ����������ƶ�
	 * @param selection Ҫ�ƶ�������
	 * @param count  �ƶ��ľ���
	 */
	public void moveDown(Dispatch selection, int count) {
		for (int i = 0; i < count; i++)
			Dispatch.call(selection, "MoveDown");
	}

	/**
	 * ��ѡ�����ݻ����������ƶ�
	 * @param selection Ҫ�ƶ�������
	 * @param count �ƶ��ľ���
	 */
	public void moveLeft(Dispatch selection, int count) {
		for (int i = 0; i < count; i++)
			Dispatch.call(selection, "MoveLeft");
	}

	/**
	 * ��ѡ�����ݻ����������ƶ�
	 * @param selection Ҫ�ƶ�������
	 * @param count  �ƶ��ľ���
	 */
	public void moveRight(Dispatch selection, int count) {
		for (int i = 0; i < count; i++)
			Dispatch.call(selection, "MoveRight");
	}

	
	/**
	 * �Ѳ�����ƶ����ļ���λ��
	 * @param selection �����
	 */
	public void moveDocStart() {
		Dispatch selection = Dispatch.get(word, "Selection").toDispatch();
		Dispatch.call(selection, "HomeKey", new Variant(6));
	}
	
	/**
	 * ����ƶ����ĵ����
	 * @param selection �����
	 */
	public void moveDocEnd(){   
		Dispatch selection = Dispatch.get(word, "Selection").toDispatch();
        Dispatch.call(selection, "EndKey", new Variant(6));
    }  
	
	/**
	 * �������N��
	 * @param count ����
	 * @param selection �����
	 */
	public void moveNextNLine(int count,Dispatch selection){
	   Dispatch.call(selection, "MoveRight", "3", String.valueOf(count));
	}
	
	/**s
	 * �������N��
	 * @param count ����
	 * @param selection �����
	 */
	public void moveBackNLine(int count,Dispatch selection){
	   Dispatch.call(selection, "MoveLeft", "3", String.valueOf(count));
    }




	/**
	 * ��ѡ�����ݻ����㿪ʼ�����ı�
	 * 
	 * @param selection
	 *            ѡ������
	 * @param toFindText
	 *            Ҫ���ҵ��ı�
	 * @return boolean true-���ҵ���ѡ�и��ı���false-δ���ҵ��ı�
	 */
	public boolean find(Dispatch selection, String toFindText) {
		// ��selection����λ�ÿ�ʼ��ѯ
		Dispatch find = Dispatch.call(selection, "Find").toDispatch();
		// ����Ҫ���ҵ�����
		Dispatch.put(find, "Text", toFindText);
		// ��ǰ����
		Dispatch.put(find, "Forward", "True");
		// ���ø�ʽ
		Dispatch.put(find, "Format", "True");
		// ��Сдƥ��
		Dispatch.put(find, "MatchCase", "True");
		// ȫ��ƥ��
		Dispatch.put(find, "MatchWholeWord", "True");
		// ���Ҳ�ѡ��
		return Dispatch.call(find, "Execute").getBoolean();
	}

	/**
	 * ��ѡ�������滻Ϊ�趨�ı�
	 * 
	 * @param selection
	 *            ѡ������
	 * @param newText
	 *            �滻Ϊ�ı�
	 */
	public void replace(Dispatch selection, String newText) {
		// �����滻�ı�
		Dispatch.put(selection, "Text", newText);
	}

	/**
	 * ȫ���滻
	 * 
	 * @param selection
	 *            ѡ�����ݻ���ʼ�����
	 * @param oldText
	 *            Ҫ�滻���ı�
	 * @param newText
	 *            �滻Ϊ�ı�
	 */
	@SuppressWarnings("unchecked")
	public void replaceAll(Dispatch selection, String oldText, Object replaceObj) {
		// �ƶ����ļ���ͷ
		moveDocStart();
		if (oldText.startsWith("table") || replaceObj instanceof List) {
			replaceTable(selection, oldText, (List) replaceObj);
		} else {
			String newText = (String) replaceObj;
			if (oldText.indexOf("image") != -1 || newText.lastIndexOf(".bmp") != -1 || newText.lastIndexOf(".jpg") != -1
					|| newText.lastIndexOf(".gif") != -1)
				while (find(selection, oldText)) {
					replaceImage(selection, newText);
					Dispatch.call(selection, "MoveRight");
				}
			else
				while (find(selection, oldText)) {
					replace(selection, newText);
					Dispatch.call(selection, "MoveRight");
				}
		}
	}

	/**
	 * �滻ͼƬ
	 * @param selection ͼƬ�Ĳ����
	 * @param imagePath ͼƬ�ļ���ȫ·����
	 */
	public void replaceImage(Dispatch selection, String imagePath) {
		Dispatch.call(Dispatch.get(selection, "InLineShapes").toDispatch(), "AddPicture", imagePath);
	}

	/**
	 * �滻���
	 * @param selection �����
	 * @param tableName ������ƣ�����table$1@1��table$2@1...table$R@N��R����ӱ���еĵ�N�п�ʼ��䣬 N����word�ļ��еĵ�N�ű�
	 * @param fields    �����Ҫ�滻���ֶ������ݵĶ�Ӧ��
	 */
	@SuppressWarnings({ "unchecked"})
	public void replaceTable(Dispatch selection, String tableName, List dataList) {

		if (dataList.size() <= 1) {
			System.out.println("Empty table!");
			return;
		}
		// Ҫ������
		String[] cols = (String[]) dataList.get(0);
		// ������
		String tbIndex = tableName.substring(tableName.lastIndexOf("@") + 1);
		// �ӵڼ��п�ʼ���
		int fromRow = Integer.parseInt(tableName.substring(tableName.lastIndexOf("$") + 1, tableName.lastIndexOf("@")));
		// ���б��
		Dispatch tables = Dispatch.get(doc, "Tables").toDispatch();
		// Ҫ���ı��
		Dispatch table = Dispatch.call(tables, "Item", new Variant(tbIndex)).toDispatch();
		// ����������
		Dispatch rows = Dispatch.get(table, "Rows").toDispatch();
		// �����
		for (int i = 1; i < dataList.size(); i++) {
			// ĳһ������
			String[] datas = (String[]) dataList.get(i);
			// �ڱ�������һ��
 			if (Dispatch.get(rows, "Count").getInt() < fromRow + i - 1)
				Dispatch.call(rows, "Add");
			// �����е������
			for (int j = 0; j < datas.length; j++) {
				// �õ���Ԫ��
				Dispatch cell = Dispatch.call(table, "Cell", Integer.toString(fromRow + i - 1), cols[j]).toDispatch();
				// ѡ�е�Ԫ��
				Dispatch.call(cell, "Select");
				// ���ø�ʽ
				Dispatch font = Dispatch.get(selection, "Font").toDispatch();
				Dispatch.put(font, "Bold", "0");
				Dispatch.put(font, "Italic", "0");
				// ��������
				Dispatch.put(selection, "Text", datas[j]);
			}
		}
	}

	/**
	 * �����ļ�
	 * @param outputPath ����ļ�������·����
	 */
	public void save(String outputPath) {
		Dispatch.call(Dispatch.call(word, "WordBasic").getDispatch(), "FileSaveAs", outputPath);
	}

	/**
	 * �ر��ļ�
	 * 
	 * @param document
	 *            Ҫ�رյ��ļ�
	 */
	public void close(Dispatch doc) {
		Dispatch.call(doc, "Close", new Variant(saveOnExit));
	}

	/**
	 * �˳�����
	 */
	public void quit() {
		word.invoke("Quit", new Variant[0]);
		ComThread.Release();
	}
	
	/**
	 * �ر��ĵ�
	 */
    public void closeDocument() {   
        Dispatch.call(documents, "Close", new Variant(0));   
        documents = null;   
    }   
    
    /**
     * �ر�����Ӧ��
     */
    public void closeWord() {   
        Dispatch.call(word, "Quit");   
        word = null;   
    }	

	/**
	 * ����ģ�塢��������word�ļ�
	 * @param inputPath ģ���ļ�������·����
	 * @param outPath ����ļ�������·����
	 * @param data ���ݰ�������Ҫ�����ֶΡ���Ӧ�����ݣ�
	 */
	@SuppressWarnings("unchecked")
	public void toWord(String inputPath, String outPath, HashMap data) {
		String oldText;
		Object newValue;
		try {
			doc = open(inputPath);
			Dispatch selection = select();
			Iterator keys = data.keySet().iterator();
			while (keys.hasNext()) {
				oldText = (String) keys.next();
				newValue = data.get(oldText);
				if(null==newValue||"null".equalsIgnoreCase((String)newValue)){
					newValue="";
				}
				replaceAll(selection, oldText, newValue);
			}
			save(outPath);
		} catch (Exception e) {
			log.error(e.getMessage());

		} finally {
			if (doc != null)
				close(doc);
		}
	}
	
	
	/**
	 * ���ļ��л���񲢲�������
	 * @param tableTitle ���������
	 * @param list ��Ҫ��ӵ�����
	 */
    public void insertTable(String tableTitle,List<String[]> list) {
    	
        Dispatch selection = Dispatch.get(word, "Selection").toDispatch(); // ����������Ҫ�Ķ���   
        Dispatch.call(selection, "TypeText", tableTitle); // д��������� // �������   
        Dispatch.call(selection, "TypeParagraph"); // ��һ�ж���   
        Dispatch.call(selection, "TypeParagraph"); // ��һ�ж���   
        Dispatch.call(selection, "MoveDown"); // �α�����һ��   
        
        // �������
        int row = list.size();//��
        int column = list.get(0).length;//��
        Dispatch tables = Dispatch.get(documents, "Tables").toDispatch();   
        Dispatch range = Dispatch.get(selection, "Range").toDispatch();// /��ǰ���λ�û���ѡ�е�����   
        // ����row,column,��������
        Dispatch newTable = Dispatch.call(tables, "Add", range, new Variant(row), new Variant(column), new Variant(1)).toDispatch();   
        Dispatch cols = Dispatch.get(newTable, "Columns").toDispatch(); // �˱�������У�   
        int colCount = Dispatch.get(cols, "Count").changeType(Variant.VariantInt).getInt();// һ���ж����� ʵ���������==column   
        
        for (int i = 1; i <= colCount; i++) { // ѭ��ȡ��ÿһ��   
            Dispatch col = Dispatch.call(cols, "Item", new Variant(i)).toDispatch();   
            Dispatch cells = Dispatch.get(col, "Cells").toDispatch();// ��ǰ���е�Ԫ��   
            int cellCount = Dispatch.get(cells, "Count").changeType(Variant.VariantInt).getInt();// ��ǰ���е�Ԫ���� ʵ�������������row   
            for (int j = 1; j <= cellCount; j++) {// ÿһ���еĵ�Ԫ����   
            	
                putTxtToCell(newTable, j, i, list.get(j-1)[i-1]);// �������ľ��������ͬ   
            }   
        }   
    } 

    /**  
     * ��ָ���ĵ�Ԫ������д����  
     * @param tableIndex  
     * @param cellRowIdx �ڼ���
     * @param cellColIdx �ڼ���
     * @param txt  ��д��ֵ
     */  
    public void putTxtToCell(Dispatch table, int cellRowIdx, int cellColIdx,String txt) { 
    	
        Dispatch cell = Dispatch.call(table, "Cell", new Variant(cellRowIdx),new Variant(cellColIdx)).toDispatch();   
        Dispatch.call(cell, "Select");   
        Dispatch selection = Dispatch.get(word, "Selection").toDispatch(); // ����������Ҫ�Ķ���   
        Dispatch.put(selection, "Text", txt);   
        
    }
    
    /**
     * 
     * @param tagVals
     */
	public void insertTagValue(Map<String,String> tagVals){
		Dispatch bookMarks = word.call(documents, "Bookmarks").toDispatch();//Bookmarks��һ���ؼ���
		for(String key:tagVals.keySet()){
			Dispatch.put(Dispatch.call(Dispatch.call(bookMarks, "Item",key).toDispatch() , "Range").toDispatch(),"Text",new Variant(tagVals.get(key)));//���ı���ֵ
		}
		
	}
	
	

    
    
    public static void main(String[] args) {
    	Java2Word word= new Java2Word();
    	word.openFile("d:\\�����ʵȷ����.doc");
    	word.moveDocEnd();
    	List list= new ArrayList<String[]>();
    	String aa[]={"1","2","3"};
    	String bb[]={"1","2223","��3"};
    	list.add(aa);
    	list.add(bb);
    	
    	word.insertTable("����2",list); 
    	word.save("d:\\table2.doc");
    	word.closeDocument();
    	word.closeWord();
    	
	}
}