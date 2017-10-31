package com.upd.common.util;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
//import org.apache.poi.hssf.util.Region;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

public class ExcelUtils {

	public static final int maxRowNum = 65000;// 为了防止excel超界，每页插入65000行数据�����
	public static HSSFWorkbook excel(List<String> head, List<List<Object>> excelValue,
			String sheetName) {
		HSSFWorkbook wb = newWorkbook();

		int size = excelValue.size();
		int sheetCount = size / maxRowNum + 1;
		for (int i = 0; i < sheetCount; i++) {
			HSSFSheet sheet = newSheet(wb, sheetName + (i + 1));

			// ��ͷ
			int rowNum = 0;
			HSSFRow row = newRow(sheet, rowNum);
			int cellNum = 0;
			for (String cellVelue : head) {
				HSSFCell cell = newCell(row, (short) cellNum);
				setCell(cell, cellVelue);
				cellNum++;
			}
			rowNum++;

			// �����
			for (int j = i * maxRowNum; j < (i + 1) * maxRowNum && j < size; j++) {
				List<Object> rowVelue = excelValue.get(j);
				row = newRow(sheet, rowNum);
				cellNum = 0;
				for (Object cellVelue : rowVelue) {
					HSSFCell cell = newCell(row, (short) cellNum);
					setCell(cell, cellVelue);
					cellNum++;
				}
				rowNum++;
			}
		}
		return wb;
	}

	public static HSSFWorkbook newWorkbook() {
		HSSFWorkbook wb = new HSSFWorkbook();
		return wb;
	}

	public static HSSFSheet newSheet(HSSFWorkbook wb, String sheetName) {
		HSSFSheet sheet = wb.createSheet(sheetName);
		return sheet;
	}

	public static HSSFRow newRow(HSSFSheet sheet, int rowNum) {
		HSSFRow row = sheet.createRow(rowNum);
		return row;
	}

	public static  HSSFCell newCell(HSSFRow row, short cellNum) {
		HSSFCell cell = row.createCell(cellNum);
		return cell;
	}

	public static void setCell(HSSFCell cell, Object cellValue) {
		if (cellValue == null) {
			HSSFRichTextString hrts = new HSSFRichTextString("");
			cell.setCellValue(hrts);
		} else if (cellValue instanceof String) {
			HSSFRichTextString hrts = new HSSFRichTextString((String) cellValue);
			cell.setCellValue(hrts);
		} else if (cellValue instanceof Boolean) {
			cell.setCellValue((Boolean) cellValue);
		} else if (cellValue instanceof Calendar) {
			cell.setCellValue((Calendar) cellValue);
		} else if (cellValue instanceof Date) {
			cell.setCellValue((Date) cellValue);
		} else if (cellValue instanceof Double) {
			cell.setCellValue((Double) cellValue);
		} else if (cellValue instanceof Integer) {
			cell.setCellValue((Integer) cellValue);
		} else if (cellValue instanceof Float) {
			cell.setCellValue((Float) cellValue);
		} else {
			HSSFRichTextString hrts = new HSSFRichTextString(cellValue
					.toString());
			cell.setCellValue(hrts);
		}
	}

	public void outWorkbooks(HSSFWorkbook wb, String path) throws IOException {
		FileOutputStream fileOut = new FileOutputStream(path);
		wb.write(fileOut);
		fileOut.close();
	}

	public HSSFCellStyle newCellStyle(HSSFWorkbook wb) {
		HSSFCellStyle cellStyle = wb.createCellStyle();
		return cellStyle;
	}

	public HSSFCellStyle setDataFormat(HSSFCellStyle cellStyle) {
		cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy h:mm"));
		return cellStyle;
	}

	/**
	 * @param cellStyle
	 * @param align
	 *            HSSFCellStyle.ALIGN_CENTER;
	 *            HSSFCellStyle.ALIGN_CENTER_SELECTION;
	 *            HSSFCellStyle.ALIGN_FILL; HSSFCellStyle.ALIGN_GENERAL;
	 *            HSSFCellStyle.ALIGN_JUSTIFY; HSSFCellStyle.ALIGN_LEFT;
	 *            HSSFCellStyle.ALIGN_RIGHT;
	 * @return
	 */
	public HSSFCellStyle setAlignment(HSSFCellStyle cellStyle, short align) {
		cellStyle.setAlignment(align);
		return cellStyle;
	}

	/**
	 * �ӱ߿�Ĭ�ϣ�ϸ�ߣ���ɫ
	 * 
	 * @param cellStyle
	 * @return
	 */
	public HSSFCellStyle setBorders(HSSFCellStyle cellStyle) {
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBottomBorderColor(HSSFColor.BLACK.index);
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyle.setLeftBorderColor(HSSFColor.BLACK.index);
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cellStyle.setRightBorderColor(HSSFColor.BLACK.index);
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		cellStyle.setTopBorderColor(HSSFColor.BLACK.index);
		return cellStyle;
	}

	/**
	 * �ӱ߿򣬱߿�ֶȺ���ɫ��ͬ
	 * 
	 * @param cellStyle
	 * @param border
	 *            (HSSFCellStyle.BORDER_THIN;
	 *            HSSFCellStyle.BORDER_MEDIUM_DASHED)
	 * @param borderColor
	 *            (HSSFColor.BLACK.index; HSSFColor.GREEN.index;
	 *            HSSFColor.BLUE.index)
	 * @return
	 */
	public HSSFCellStyle setBorders(HSSFCellStyle cellStyle, short border,
			short borderColor) {
		cellStyle.setBorderBottom(border);
		cellStyle.setBottomBorderColor(borderColor);
		cellStyle.setBorderLeft(border);
		cellStyle.setLeftBorderColor(borderColor);
		cellStyle.setBorderRight(border);
		cellStyle.setRightBorderColor(borderColor);
		cellStyle.setBorderTop(border);
		cellStyle.setTopBorderColor(borderColor);
		return cellStyle;

	}

	/**
	 * �ӱ߿�
	 * 
	 * @param cellStyle
	 * @param borderTop
	 *            �ϱ߿�ֶ�(HSSFCellStyle.BORDER_)
	 * @param topBorderColor
	 *            �ϱ߿���ɫ��HSSFColor.XXX.index��
	 * @param borderBottom
	 *            �±߿�ֶ�
	 * @param bottomBorderColor
	 *            �±߿���ɫ
	 * @param borderLeft
	 *            ��߿�ֶ�
	 * @param leftBorderColor
	 *            ��߿���ɫ
	 * @param borderRight
	 *            �ұ߿�ֶ�
	 * @param rightBorderColor
	 *            �ұ߿���ɫ
	 * @return
	 */
	public HSSFCellStyle setBorders(HSSFCellStyle cellStyle, short borderTop,
			short topBorderColor, short borderBottom, short bottomBorderColor,
			short borderLeft, short leftBorderColor, short borderRight,
			short rightBorderColor) {
		cellStyle.setBorderTop(borderTop);
		cellStyle.setTopBorderColor(topBorderColor);
		cellStyle.setBorderBottom(borderBottom);
		cellStyle.setBottomBorderColor(bottomBorderColor);
		cellStyle.setBorderLeft(borderLeft);
		cellStyle.setLeftBorderColor(leftBorderColor);
		cellStyle.setBorderRight(borderRight);
		cellStyle.setRightBorderColor(rightBorderColor);
		return cellStyle;
	}

	/**
	 * �˷���������ɫ�д������⣨���Ǻ�ɫ��������ʹ��setForeground
	 * 
	 * @deprecated
	 * @param cellStyle
	 * @param backgroundColor
	 *            ����ɫ��HSSFColor.XXX.index��
	 * @param pattern
	 *            �����ʽ��HSSFCellStyle.BIG_SPOTS��HSSFCellStyle.SOLID_FOREGROUND��
	 * @return
	 */
	public HSSFCellStyle setBackground(HSSFCellStyle cellStyle,
			short backgroundColor, short pattern) {
		cellStyle.setFillBackgroundColor(backgroundColor);
		cellStyle.setFillPattern(pattern);
		return cellStyle;
	}

	/**
	 * @param cellStyle
	 * @param foregroundColor
	 *            ǰ��ɫ��HSSFColor.XXX.index��
	 * @param pattern
	 *            �����ʽ��HSSFCellStyle.BIG_SPOTS��HSSFCellStyle.SOLID_FOREGROUND��
	 * @return
	 */
	public HSSFCellStyle setForeground(HSSFCellStyle cellStyle,
			short foregroundColor, short pattern) {
		cellStyle.setFillForegroundColor(HSSFColor.ORANGE.index);
		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		return cellStyle;
	}

	/**
	 * @param sheet
	 * @param rowFrom
	 *            �ϲ���ʼ��
	 * @param colFrom
	 *            �ϲ���ʼ��
	 * @param rowTo
	 *            �ϲ�������
	 * @param colTo
	 *            �ϲ�������
	 * @return
	 */
	public HSSFSheet Mergecells(HSSFSheet sheet, int rowFrom, short colFrom,
			int rowTo, short colTo) {
//		sheet.addMergedRegion(new Region(rowFrom, colFrom, rowTo, colTo));
		return sheet;
	}

	/**
	 * the maximum number of unique fonts in a workbook is limited to 32767 (
	 * the maximum positive short). You should re-use fonts in your apllications
	 * instead of creating a font for each cell.
	 *
	 * @param font
	 * @param fontHeightInPoints
	 *            ����߶�
	 * @param fontName
	 *            �������
	 * @param italic
	 *            б��
	 * @param strikeout
	 *            ɾ����
	 * @return
	 */
	public HSSFFont setFont(HSSFFont font, short fontHeightInPoints,
			String fontName, boolean italic, boolean strikeout) {
		font.setFontHeightInPoints(fontHeightInPoints);
		font.setFontName(fontName);
		// font.setColor(HSSFColor.RED.index);
		font.setItalic(italic);
		font.setStrikeout(strikeout);
		return font;
	}

	/**
	 * �Զ�����ɫ���˹��ܻ����һ���о�
	 * 
	 * @deprecated
	 * @throws Exception
	 */
	public void customColors() throws Exception {
		// Custom colors
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet();
		HSSFRow row = sheet.createRow((short) 0);
		HSSFCell cell = row.createCell((short) 0);
		cell.setCellValue("Default Palette");

		// apply some colors from the standard palette,
		// as in the previous examples.
		// we'll use red text on a lime background

		HSSFCellStyle style = wb.createCellStyle();
		style.setFillForegroundColor(HSSFColor.LIME.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

		HSSFFont font = wb.createFont();
		font.setColor(HSSFColor.RED.index);
		style.setFont(font);

		cell.setCellStyle(style);

		// save with the default palette
		FileOutputStream out = new FileOutputStream("default_palette.xls");
		wb.write(out);
		out.close();

		// now, let's replace RED and LIME in the palette
		// with a more attractive combination
		// (lovingly borrowed from freebsd.org)

		cell.setCellValue("Modified Palette");

		// creating a custom palette for the workbook
		HSSFPalette palette = wb.getCustomPalette();

		// replacing the standard red with freebsd.org red
		palette.setColorAtIndex(HSSFColor.RED.index, (byte) 153, // RGB red
				// (0-255)
				(byte) 0, // RGB green
				(byte) 0 // RGB blue
				);
		// replacing lime with freebsd.org gold
		palette.setColorAtIndex(HSSFColor.LIME.index, (byte) 255, (byte) 204,
				(byte) 102);

		// save with the modified palette
		// note that wherever we have previously used RED or LIME, the
		// new colors magically appear
		out = new FileOutputStream("modified_palette.xls");
		wb.write(out);
		out.close();
	}

	public HSSFWorkbook readWorkbooks(String path)
			throws FileNotFoundException, IOException {
		POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(path));
		HSSFWorkbook wb = new HSSFWorkbook(fs);
		return wb;
	}
	/**
	 * slx 添加 excel输出的数据
	 * 
	 * @param exportObject 导出的数据
	 * @param displays 显示的字段
	 * @return
	 */
	public static   List<List<Object>> excelValues(
			List<Object> exportObject,List<String> displays) {
		List<List<Object>> result = new ArrayList<List<Object>>();
		for (Object obj : exportObject) {
			List<Object> resultvalue = new ArrayList<Object>();
			for(String property:displays){
				String displayValue="";
				try {
							String tempDisplayValue=PropertyUtils.getProperty(obj, property).toString();
							if(tempDisplayValue!=null){
                                displayValue=tempDisplayValue;
							}
				} catch (Exception e) {
				}
				resultvalue.add(displayValue);
			}	
			result.add(resultvalue);
		}
		return result;
	}
}
