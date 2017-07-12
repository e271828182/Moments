package util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.ClientAnchor.AnchorType;

/**
 * excel表格生成
 * @author WSM
 *
 * @param <T>
 */

public class CreateExcel<T> {
	
	private Class<T> clz;
	
	public CreateExcel(Class<T> clz){
		this.clz = clz;
	}
	/**
	 * 
	 * @param list 要生成表格的pojo集合
	 * @param path 图片的路径，可以不传
	 * @return xls表格
	 * @throws Exception
	 */

	public HSSFWorkbook getExcel(List<T> list,String... path) throws Exception{
		
		BeanInfo beanInfo = Introspector.getBeanInfo(clz);
		PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
		
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet(clz.getSimpleName()+"_Info");
		HSSFRow row;
		HSSFCell cell;
		
		int cellNum=1;
		row = sheet.createRow(0);
		for(PropertyDescriptor pd : pds){
			String name = pd.getName();
			if (name.equals("class")) continue;
			//设置确保id在首列
			if (pd.getName().endsWith("id")||pd.getName().endsWith("Id")){
				cell = row.createCell(0);
				cell.setCellValue(name);
				continue;
			}
			cell = row.createCell(cellNum++);
			cell.setCellValue(name);
		}
		
		int rowNum = 0;	
		for(T t : list){
			String pic = null;
			int picRow=0;
			int picCol=0;
			cellNum = 1;
			rowNum += 1;
			row = sheet.createRow(rowNum);
			for(PropertyDescriptor pd : pds){
				if(pd.getName().equals("class")) continue;
				//设置确保id在首列
				if(pd.getName().endsWith("id")||pd.getName().endsWith("Id")){
					
					Method method = pd.getReadMethod();
					cell = row.createCell(0);
					Object result = method.invoke(t);
					if(pd.getPropertyType()==String.class){
						cell.setCellValue((String)result);
					}
					if(pd.getPropertyType()==Integer.class){
						cell.setCellValue((Integer)result);
					}
					continue;
				}
				
				Method method = pd.getReadMethod();
				cell = row.createCell(cellNum++);
				Object result = method.invoke(t);
				//判断是否有图片
				if(pd.getName().indexOf("pic")!=-1){
					//记录图片的行号和列号
					pic = (String) result;
					picRow = rowNum-1;
					picCol = cellNum-1;
					continue;
				}
				if(result!=null){
					
					if(pd.getPropertyType()==String.class){
						cell.setCellValue((String)result);
					}
					if(pd.getPropertyType()==Integer.class){
						cell.setCellValue((Integer)result);
					}
					if(pd.getPropertyType()==Long.class){
						cell.setCellValue((Long)result);
					}
					if(pd.getPropertyType()==Double.class){
						cell.setCellValue((Double)result);
					}
					if(pd.getPropertyType()==Date.class){
						cell.setCellValue((Date)result);
						HSSFCellStyle cellStyle = wb.createCellStyle();
						HSSFDataFormat dataFormat = wb.createDataFormat();
						cellStyle.setDataFormat(dataFormat.getFormat("yyyy-MM-dd"));
						cell.setCellStyle(cellStyle);
					}
				}
			}
			
			if(pic!=null && !"".equals(pic) && path.length>0){
				HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
				HSSFClientAnchor anchor;
				anchor = new HSSFClientAnchor(0,0,0,255,(short)(picCol),picRow+1,(short)(picCol+1),picRow+1);
				anchor.setAnchorType(AnchorType.MOVE_AND_RESIZE);
				patriarch.createPicture(anchor, loadPic(path[0] + pic , wb));
			}
		}
		return wb;
	}

	private int loadPic(String filePath, HSSFWorkbook wb) {
		try(FileInputStream in = new FileInputStream(filePath)){			
			FileChannel channel = in.getChannel();
			ByteBuffer buffer = ByteBuffer.allocate((int)channel.size());
			channel.read(buffer);
			byte[] array = buffer.array();
			return wb.addPicture(array, HSSFWorkbook.PICTURE_TYPE_PNG);
		}catch(IOException e){
			e.printStackTrace();
			return 0;
		}
	}
}
