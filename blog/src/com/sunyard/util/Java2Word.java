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
	
	
	private Dispatch doc = null;//word文档
	private ActiveXComponent word; // word运行程序对象
	private Dispatch documents; //所有word文档

	public Java2Word() {
		saveOnExit = true;
		word = new ActiveXComponent("Word.Application");
		word.setProperty("Visible", new Variant(false));
		documents = word.getProperty("Documents").toDispatch();
	}

	/**
	 * 设置参数：退出时是否保存
	 * @param saveOnExit
	 * true-退出时保存文件，false-退出时不保存文件
	 */
	public void setSaveOnExit(boolean saveOnExit) {
		this.saveOnExit = saveOnExit;
	}

	/**
	 * 得到参数：退出时是否保存
	 * @return boolean true-退出时保存文件，false-退出时不保存文件
	 */
	public boolean getSaveOnExit() {
		return saveOnExit;
	}

	/**
	 * 打开文件
	 * @param inputDoc 要打开的文件，全路径
	 * @return Dispatch 打开的文件
	 */
	public Dispatch open(String inputDoc) {
		return Dispatch.call(documents, "Open", inputDoc).toDispatch();
	}
	
    public void openFile(String wordFilePath) {   
        documents = Dispatch.call(Dispatch.get(word, "Documents").toDispatch(), "Open", wordFilePath,   
                new Variant(true),new Variant(false)/* 是否只读 */).toDispatch();   
    }   

	/**
	 * 选定内容
	 * 
	 * @return Dispatch 选定的范围或插入点
	 */
	public Dispatch select() {
		return word.getProperty("Selection").toDispatch();
	}

	/**
	 * 把选定内容或插入点向上移动
	 * 
	 * @param selection
	 *            要移动的内容
	 * @param count
	 *            移动的距离
	 */
	public void moveUp(Dispatch selection, int count) {
		for (int i = 0; i < count; i++)
			Dispatch.call(selection, "MoveUp");
	}

	/**
	 * 把选定内容或插入点向下移动
	 * @param selection 要移动的内容
	 * @param count  移动的距离
	 */
	public void moveDown(Dispatch selection, int count) {
		for (int i = 0; i < count; i++)
			Dispatch.call(selection, "MoveDown");
	}

	/**
	 * 把选定内容或插入点向左移动
	 * @param selection 要移动的内容
	 * @param count 移动的距离
	 */
	public void moveLeft(Dispatch selection, int count) {
		for (int i = 0; i < count; i++)
			Dispatch.call(selection, "MoveLeft");
	}

	/**
	 * 把选定内容或插入点向右移动
	 * @param selection 要移动的内容
	 * @param count  移动的距离
	 */
	public void moveRight(Dispatch selection, int count) {
		for (int i = 0; i < count; i++)
			Dispatch.call(selection, "MoveRight");
	}

	
	/**
	 * 把插入点移动到文件首位置
	 * @param selection 插入点
	 */
	public void moveDocStart() {
		Dispatch selection = Dispatch.get(word, "Selection").toDispatch();
		Dispatch.call(selection, "HomeKey", new Variant(6));
	}
	
	/**
	 * 光标移动到文档最后
	 * @param selection 插入点
	 */
	public void moveDocEnd(){   
		Dispatch selection = Dispatch.get(word, "Selection").toDispatch();
        Dispatch.call(selection, "EndKey", new Variant(6));
    }  
	
	/**
	 * 光标下移N行
	 * @param count 行数
	 * @param selection 插入点
	 */
	public void moveNextNLine(int count,Dispatch selection){
	   Dispatch.call(selection, "MoveRight", "3", String.valueOf(count));
	}
	
	/**s
	 * 光标上移N行
	 * @param count 行数
	 * @param selection 插入点
	 */
	public void moveBackNLine(int count,Dispatch selection){
	   Dispatch.call(selection, "MoveLeft", "3", String.valueOf(count));
    }




	/**
	 * 从选定内容或插入点开始查找文本
	 * 
	 * @param selection
	 *            选定内容
	 * @param toFindText
	 *            要查找的文本
	 * @return boolean true-查找到并选中该文本，false-未查找到文本
	 */
	public boolean find(Dispatch selection, String toFindText) {
		// 从selection所在位置开始查询
		Dispatch find = Dispatch.call(selection, "Find").toDispatch();
		// 设置要查找的内容
		Dispatch.put(find, "Text", toFindText);
		// 向前查找
		Dispatch.put(find, "Forward", "True");
		// 设置格式
		Dispatch.put(find, "Format", "True");
		// 大小写匹配
		Dispatch.put(find, "MatchCase", "True");
		// 全字匹配
		Dispatch.put(find, "MatchWholeWord", "True");
		// 查找并选中
		return Dispatch.call(find, "Execute").getBoolean();
	}

	/**
	 * 把选定内容替换为设定文本
	 * 
	 * @param selection
	 *            选定内容
	 * @param newText
	 *            替换为文本
	 */
	public void replace(Dispatch selection, String newText) {
		// 设置替换文本
		Dispatch.put(selection, "Text", newText);
	}

	/**
	 * 全局替换
	 * 
	 * @param selection
	 *            选定内容或起始插入点
	 * @param oldText
	 *            要替换的文本
	 * @param newText
	 *            替换为文本
	 */
	@SuppressWarnings("unchecked")
	public void replaceAll(Dispatch selection, String oldText, Object replaceObj) {
		// 移动到文件开头
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
	 * 替换图片
	 * @param selection 图片的插入点
	 * @param imagePath 图片文件（全路径）
	 */
	public void replaceImage(Dispatch selection, String imagePath) {
		Dispatch.call(Dispatch.get(selection, "InLineShapes").toDispatch(), "AddPicture", imagePath);
	}

	/**
	 * 替换表格
	 * @param selection 插入点
	 * @param tableName 表格名称，形如table$1@1、table$2@1...table$R@N，R代表从表格中的第N行开始填充， N代表word文件中的第N张表
	 * @param fields    表格中要替换的字段与数据的对应表
	 */
	@SuppressWarnings({ "unchecked"})
	public void replaceTable(Dispatch selection, String tableName, List dataList) {

		if (dataList.size() <= 1) {
			System.out.println("Empty table!");
			return;
		}
		// 要填充的列
		String[] cols = (String[]) dataList.get(0);
		// 表格序号
		String tbIndex = tableName.substring(tableName.lastIndexOf("@") + 1);
		// 从第几行开始填充
		int fromRow = Integer.parseInt(tableName.substring(tableName.lastIndexOf("$") + 1, tableName.lastIndexOf("@")));
		// 所有表格
		Dispatch tables = Dispatch.get(doc, "Tables").toDispatch();
		// 要填充的表格
		Dispatch table = Dispatch.call(tables, "Item", new Variant(tbIndex)).toDispatch();
		// 表格的所有行
		Dispatch rows = Dispatch.get(table, "Rows").toDispatch();
		// 填充表格
		for (int i = 1; i < dataList.size(); i++) {
			// 某一行数据
			String[] datas = (String[]) dataList.get(i);
			// 在表格中添加一行
 			if (Dispatch.get(rows, "Count").getInt() < fromRow + i - 1)
				Dispatch.call(rows, "Add");
			// 填充该行的相关列
			for (int j = 0; j < datas.length; j++) {
				// 得到单元格
				Dispatch cell = Dispatch.call(table, "Cell", Integer.toString(fromRow + i - 1), cols[j]).toDispatch();
				// 选中单元格
				Dispatch.call(cell, "Select");
				// 设置格式
				Dispatch font = Dispatch.get(selection, "Font").toDispatch();
				Dispatch.put(font, "Bold", "0");
				Dispatch.put(font, "Italic", "0");
				// 输入数据
				Dispatch.put(selection, "Text", datas[j]);
			}
		}
	}

	/**
	 * 保存文件
	 * @param outputPath 输出文件（包含路径）
	 */
	public void save(String outputPath) {
		Dispatch.call(Dispatch.call(word, "WordBasic").getDispatch(), "FileSaveAs", outputPath);
	}

	/**
	 * 关闭文件
	 * 
	 * @param document
	 *            要关闭的文件
	 */
	public void close(Dispatch doc) {
		Dispatch.call(doc, "Close", new Variant(saveOnExit));
	}

	/**
	 * 退出程序
	 */
	public void quit() {
		word.invoke("Quit", new Variant[0]);
		ComThread.Release();
	}
	
	/**
	 * 关闭文档
	 */
    public void closeDocument() {   
        Dispatch.call(documents, "Close", new Variant(0));   
        documents = null;   
    }   
    
    /**
     * 关闭整个应用
     */
    public void closeWord() {   
        Dispatch.call(word, "Quit");   
        word = null;   
    }	

	/**
	 * 根据模板、数据生成word文件
	 * @param inputPath 模板文件（包含路径）
	 * @param outPath 输出文件（包含路径）
	 * @param data 数据包（包含要填充的字段、对应的数据）
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
	 * 在文件中画表格并插入数据
	 * @param tableTitle 表添加数据
	 * @param list 需要添加的数据
	 */
    public void insertTable(String tableTitle,List<String[]> list) {
    	
        Dispatch selection = Dispatch.get(word, "Selection").toDispatch(); // 输入内容需要的对象   
        Dispatch.call(selection, "TypeText", tableTitle); // 写入标题内容 // 标题格行   
        Dispatch.call(selection, "TypeParagraph"); // 空一行段落   
        Dispatch.call(selection, "TypeParagraph"); // 空一行段落   
        Dispatch.call(selection, "MoveDown"); // 游标往下一行   
        
        // 建立表格
        int row = list.size();//行
        int column = list.get(0).length;//列
        Dispatch tables = Dispatch.get(documents, "Tables").toDispatch();   
        Dispatch range = Dispatch.get(selection, "Range").toDispatch();// /当前光标位置或者选中的区域   
        // 设置row,column,表格外框宽度
        Dispatch newTable = Dispatch.call(tables, "Add", range, new Variant(row), new Variant(column), new Variant(1)).toDispatch();   
        Dispatch cols = Dispatch.get(newTable, "Columns").toDispatch(); // 此表的所有列，   
        int colCount = Dispatch.get(cols, "Count").changeType(Variant.VariantInt).getInt();// 一共有多少列 实际上这个数==column   
        
        for (int i = 1; i <= colCount; i++) { // 循环取出每一列   
            Dispatch col = Dispatch.call(cols, "Item", new Variant(i)).toDispatch();   
            Dispatch cells = Dispatch.get(col, "Cells").toDispatch();// 当前列中单元格   
            int cellCount = Dispatch.get(cells, "Count").changeType(Variant.VariantInt).getInt();// 当前列中单元格数 实际上这个数等于row   
            for (int j = 1; j <= cellCount; j++) {// 每一列中的单元格数   
            	
                putTxtToCell(newTable, j, i, list.get(j-1)[i-1]);// 与上面四句的作用相同   
            }   
        }   
    } 

    /**  
     * 在指定的单元格里填写数据  
     * @param tableIndex  
     * @param cellRowIdx 第几行
     * @param cellColIdx 第几列
     * @param txt  填写的值
     */  
    public void putTxtToCell(Dispatch table, int cellRowIdx, int cellColIdx,String txt) { 
    	
        Dispatch cell = Dispatch.call(table, "Cell", new Variant(cellRowIdx),new Variant(cellColIdx)).toDispatch();   
        Dispatch.call(cell, "Select");   
        Dispatch selection = Dispatch.get(word, "Selection").toDispatch(); // 输入内容需要的对象   
        Dispatch.put(selection, "Text", txt);   
        
    }
    
    /**
     * 
     * @param tagVals
     */
	public void insertTagValue(Map<String,String> tagVals){
		Dispatch bookMarks = word.call(documents, "Bookmarks").toDispatch();//Bookmarks是一个关键字
		for(String key:tagVals.keySet()){
			Dispatch.put(Dispatch.call(Dispatch.call(bookMarks, "Item",key).toDispatch() , "Range").toDispatch(),"Text",new Variant(tagVals.get(key)));//给文本框赋值
		}
		
	}
	
	

    
    
    public static void main(String[] args) {
    	Java2Word word= new Java2Word();
    	word.openFile("d:\\审计事实确认书.doc");
    	word.moveDocEnd();
    	List list= new ArrayList<String[]>();
    	String aa[]={"1","2","3"};
    	String bb[]={"1","2223","白3"};
    	list.add(aa);
    	list.add(bb);
    	
    	word.insertTable("数据2",list); 
    	word.save("d:\\table2.doc");
    	word.closeDocument();
    	word.closeWord();
    	
	}
}