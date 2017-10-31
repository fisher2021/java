package com.upd.business.utils;


import java.io.*;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.upd.business.constant.EducationType;
import com.upd.business.entity.PartyMembershipDues;
import com.upd.business.entity.User;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ExcelUtil {
	
	private static final String TITLE_USERNAME = "姓名";
	private static final String TITLE_PHONE = "手机号码";
	private static final String TITLE_SEX = "性别";
	private static final String TITLE_NATIVEPALCE = "籍贯";
	private static final String TITLE_NATION = "民族";
	private static final String TITLE_JOB = "岗位";
	private static final String TITLE_EDUCATION = "学历";
	private static final String TITLE_DUTY = "党内职务";
	private static final String TITLE_BIRTH = "出生年月";
	private static final String TITLE_PARTYTIME = "入党时间";
    private static final String TITLE_TRAIN = "党内培训记录";
    private static final String TITLE_AWARD = "奖励记录";
    private static final String TITLE_PUNISHMENT = "惩罚记录";
    private static final String TITLE_CONTACT = "紧急联系人姓名";
    private static final String TITLE_MOBILE = "紧急联系人电话";
    private static final String TITLE_IDCARD = "身份证号码";
    private static final String TITLE_ADRRESS = "现居住地";
    private static final String TITLE_NUMBER = "工号";

    private static final String TITLE_NEED = "应缴党费";
    private static final String TITLE_REAL = "实际缴纳党费";
    private static final String TITLE_MONTH = "年月";
    private static final String TITLE_JANUARY = "一月";
    private static final String TITLE_FEBRUARY = "二月";
    private static final String TITLE_MARCH = "三月";
    private static final String TITLE_APRIL = "四月";
    private static final String TITLE_MAY = "五月";
    private static final String TITLE_JUNE = "六月";
    private static final String TITLE_JULY = "七月";
    private static final String TITLE_AUGUST = "八月";
    private static final String TITLE_SEPTEMBER = "九月";
    private static final String TITLE_OCTOBER = "十月";
    private static final String TITLE_NOVEMBER = "十一月";
    private static final String TITLE_DECEMBER = "十二月";

//	public static void main(String[] args) {
//		try {
//			FileInputStream fis = new FileInputStream("D:\\DownLoad\\党员信息模板.xlsx");
//			List<User> users = readUserExcel(fis);
//			for(int i=0; i<users.size(); i++){
//				System.out.println(users.get(i));
//			}
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		}
////		writeExcel();
//	}

    /**
     * 读取用户信息
     * @param file
     * @return
     * @throws IOException
     * @throws InvalidFormatException
     */
	public static List<User> readUserExcel(MultipartFile file) throws IOException, InvalidFormatException {
		List<User> users = new ArrayList<>();
		Workbook book = WorkbookFactory.create(file.getInputStream());
//		try {
//			book = new XSSFWorkbook(is);
//		} catch (IOException e) {
//			try {
//				book = new HSSFWorkbook(is);
//			} catch (IOException e1) {
//				e1.printStackTrace();
//			}
//		}
		int sheetCount = book.getNumberOfSheets();
		for(int i=0; i<sheetCount; i++){
			Sheet sheet = book.getSheetAt(i);
			int rowNum = sheet.getLastRowNum();
			Row titleRow = sheet.getRow(0);
			Map<Integer, String> titleMap = new HashMap<>();
			for(int m=0; m<titleRow.getLastCellNum(); m++){
				Cell cell = titleRow.getCell(m);
				titleMap.put(m,cell.getStringCellValue());
			}
			for(int j=1; j<=rowNum; j++){
				Row row = sheet.getRow(j);
				User user = new User();
				for(int n=0; n < row.getLastCellNum(); n++){
					Cell cell = row.getCell(n);
					if (cell != null){
                        int type = cell.getCellType();
                        Object value = null;
                        if(type == Cell.CELL_TYPE_STRING){
                            value = cell.getStringCellValue();
                        } else if(type == Cell.CELL_TYPE_NUMERIC){
                            if(DateUtil.isCellDateFormatted(cell)){
                                value = cell.getDateCellValue();
                            } else {
                                value = cell.getNumericCellValue();
                            }
                        } else if(type == Cell.CELL_TYPE_BOOLEAN){
                            value = cell.getBooleanCellValue();
                        }
                        switch (titleMap.get(n)) {
                            case TITLE_USERNAME:
                                user.setNickname((String) value);
                                break;
                            case TITLE_PHONE:
                                try {
                                    user.setPhone((String) value);
                                }catch (Exception e){
                                    user.setPhone(new DecimalFormat("#").format(value));
                                }
                                break;
                            case TITLE_NUMBER:
                                try {
                                    user.setEmployeeNumber((String) value);
                                }catch (Exception e){
                                    user.setEmployeeNumber(new DecimalFormat("#").format(value));
                                }
                                break;
                            case TITLE_SEX:
                                user.setSex((String) value);
                                break;
                            case TITLE_NATIVEPALCE:
                                user.setNativePlace((String) value);
                                break;
                            case TITLE_NATION:
                                user.setNation((String) value);
                                break;
                            case TITLE_JOB:
                                user.setJob((String) value);
                                break;
                            case TITLE_EDUCATION:
                                for (EducationType e :EducationType.values()){
                                    if (e.getDesc().equals((String)value)){
                                        user.setEducation(e);
                                        break;
                                    }
                                }
                                break;
                            case TITLE_DUTY:
                                user.setDuty((String) value);
                                break;
                            case TITLE_BIRTH:
                                user.setBirth((Date) value);
                                break;
                            case TITLE_PARTYTIME:
                                user.setPartyTime((Date) value);
                                break;
                            case TITLE_TRAIN:
                                user.setTrain((String) value);
                                break;
                            case TITLE_AWARD:
                                user.setAward((String) value);
                                break;
                            case TITLE_PUNISHMENT:
                                user.setPunishment((String) value);
                                break;
                            case TITLE_IDCARD:
                                user.setIdCard((String) value);
                                break;
                            case TITLE_ADRRESS:
                                user.setAddress((String) value);
                                break;
                            case TITLE_CONTACT:
                                user.setContact((String) value);
                                break;
                            case TITLE_MOBILE:
                                user.setContactMobile((String) value);
                                break;
                        }
                    }

				}
				users.add(user);
			}
		}
		return users;
	}

    /**
     * 读取应缴党费信息
     * @param file
     * @return
     * @throws IOException
     * @throws InvalidFormatException
     */
    public static List<PartyMembershipDues> readDuesAmountExcel(MultipartFile file,String year) throws IOException, InvalidFormatException {
        List<PartyMembershipDues> partyMembershipDues = new ArrayList<>();
        Workbook book = WorkbookFactory.create(file.getInputStream());
        int sheetCount = book.getNumberOfSheets();
        for(int i=0; i<sheetCount; i++){
            Sheet sheet = book.getSheetAt(i);
            int rowNum = sheet.getLastRowNum();
            Row titleRow = sheet.getRow(0);
            Map<Integer, String> titleMap = new HashMap<>();
            for(int m=0; m<titleRow.getLastCellNum(); m++){
                Cell cell = titleRow.getCell(m);
                titleMap.put(m,cell.getStringCellValue());
            }
            for(int j=1; j<=rowNum; j++){
                Row row = sheet.getRow(j);
                User user = new User();
                for(int n=0; n < row.getLastCellNum(); n++){
                    PartyMembershipDues dues = new PartyMembershipDues();
                    BigDecimal amount = null;
                    String month = "";
                    Cell cell = row.getCell(n);
                    if (cell != null){
                        int type = cell.getCellType();
                        if (type != cell.CELL_TYPE_BLANK){
                            Object value = null;
                            if(type == Cell.CELL_TYPE_STRING){
                                value = cell.getStringCellValue();
                            } else if(type == Cell.CELL_TYPE_NUMERIC){
                                if(DateUtil.isCellDateFormatted(cell)){
                                    value = cell.getDateCellValue();
                                } else {
                                    value = cell.getNumericCellValue();
                                }
                            } else if(type == Cell.CELL_TYPE_BOOLEAN){
                                value = cell.getBooleanCellValue();
                            }
                            switch (titleMap.get(n)) {
//                                case TITLE_NUMBER:
//                                    try {
//                                        user.setEmployeeNumber((String) value);
//                                        user.setAccount((String) value);
//                                    }catch (Exception e){
//                                        user.setEmployeeNumber(new DecimalFormat("#").format(value));
//                                        user.setAccount(new DecimalFormat("#").format(value));
//                                    }
//                                    break;
                                case TITLE_PHONE:
                                    try {
                                        user.setPhone((String) value);
                                        user.setAccount((String) value);
                                    }catch (Exception e){
                                        user.setPhone(new DecimalFormat("#").format(value));
                                        user.setAccount(new DecimalFormat("#").format(value));
                                    }
                                    break;
                                case TITLE_JANUARY:
                                    month = year + "-01";
                                    amount = new BigDecimal(value.toString());
                                    break;
                                case TITLE_FEBRUARY:
                                    month = year + "-02";
                                    amount = new BigDecimal(value.toString());
                                    break;
                                case TITLE_MARCH:
                                    month = year + "-03";
                                    amount = new BigDecimal(value.toString());
                                    break;
                                case TITLE_APRIL:
                                    month = year + "-04";
                                    amount = new BigDecimal(value.toString());
                                    break;
                                case TITLE_MAY:
                                    month = year + "-05";
                                    amount = new BigDecimal(value.toString());
                                    break;
                                case TITLE_JUNE:
                                    month = year + "-06";
                                    amount = new BigDecimal(value.toString());
                                    break;
                                case TITLE_JULY:
                                    month = year + "-07";
                                    amount = new BigDecimal(value.toString());
                                    break;
                                case TITLE_AUGUST:
                                    month = year + "-08";
                                    amount = new BigDecimal(value.toString());
                                    break;
                                case TITLE_SEPTEMBER:
                                    month = year + "-09";
                                    amount = new BigDecimal(value.toString());
                                    break;
                                case TITLE_OCTOBER:
                                    month = year + "-10";
                                    amount = new BigDecimal(value.toString());
                                    break;
                                case TITLE_NOVEMBER:
                                    month = year + "-11";
                                    amount = new BigDecimal(value.toString());
                                    break;
                                case TITLE_DECEMBER:
                                    month = year + "-12";
                                    amount = new BigDecimal(value.toString());
                                    break;
                            }
                            dues.setUser(user);
                            if (n > 1){//从第三列开始
                                dues.setYear(month);
                                dues.setAmount(amount);
                                dues.setFeeReceived(new BigDecimal(0));
                                partyMembershipDues.add(dues);
                            }
                        }

                    }
                }

            }
        }
        return partyMembershipDues;
    }

    /**
     * 读取实缴党费信息
     * @param file
     * @return
     * @throws IOException
     * @throws InvalidFormatException
     */
    public static List<PartyMembershipDues> readDuesFeeReceivedExcel(MultipartFile file,String year) throws IOException, InvalidFormatException {
        List<PartyMembershipDues> partyMembershipDues = new ArrayList<>();
        Workbook book = WorkbookFactory.create(file.getInputStream());
        int sheetCount = book.getNumberOfSheets();
        for(int i=0; i<sheetCount; i++){
            Sheet sheet = book.getSheetAt(i);
            int rowNum = sheet.getLastRowNum();
            Row titleRow = sheet.getRow(0);
            Map<Integer, String> titleMap = new HashMap<>();
            for(int m=0; m<titleRow.getLastCellNum(); m++){
                Cell cell = titleRow.getCell(m);
                titleMap.put(m,cell.getStringCellValue());
            }
            for(int j=1; j<=rowNum; j++){
                Row row = sheet.getRow(j);
                User user = new User();
                for(int n=0; n < row.getLastCellNum(); n++){
                    PartyMembershipDues dues = new PartyMembershipDues();
                    BigDecimal feeReceived = null;
                    String month = "";
                    Cell cell = row.getCell(n);
                    if (cell != null){
                        int type = cell.getCellType();
                        if (type != cell.CELL_TYPE_BLANK){
                            Object value = null;
                            if(type == Cell.CELL_TYPE_STRING){
                                value = cell.getStringCellValue();
                            } else if(type == Cell.CELL_TYPE_NUMERIC){
                                if(DateUtil.isCellDateFormatted(cell)){
                                    value = cell.getDateCellValue();
                                } else {
                                    value = cell.getNumericCellValue();
                                }
                            } else if(type == Cell.CELL_TYPE_BOOLEAN){
                                value = cell.getBooleanCellValue();
                            }
                            switch (titleMap.get(n)) {
                                case TITLE_NUMBER:
                                    try {
                                        user.setEmployeeNumber((String) value);
                                        user.setAccount((String) value);
                                    }catch (Exception e){
                                        user.setEmployeeNumber(new DecimalFormat("#").format(value));
                                        user.setAccount(new DecimalFormat("#").format(value));
                                    }
                                    break;
                                case TITLE_JANUARY:
                                    month = year + "-01";
                                    feeReceived = new BigDecimal(value.toString());
                                    break;
                                case TITLE_FEBRUARY:
                                    month = year + "-02";
                                    feeReceived = new BigDecimal(value.toString());
                                    break;
                                case TITLE_MARCH:
                                    month = year + "-03";
                                    feeReceived = new BigDecimal(value.toString());
                                    break;
                                case TITLE_APRIL:
                                    month = year + "-04";
                                    feeReceived = new BigDecimal(value.toString());
                                    break;
                                case TITLE_MAY:
                                    month = year + "-05";
                                    feeReceived = new BigDecimal(value.toString());
                                    break;
                                case TITLE_JUNE:
                                    month = year + "-06";
                                    feeReceived = new BigDecimal(value.toString());
                                    break;
                                case TITLE_JULY:
                                    month = year + "-07";
                                    feeReceived = new BigDecimal(value.toString());
                                    break;
                                case TITLE_AUGUST:
                                    month = year + "-08";
                                    feeReceived = new BigDecimal(value.toString());
                                    break;
                                case TITLE_SEPTEMBER:
                                    month = year + "-09";
                                    feeReceived = new BigDecimal(value.toString());
                                    break;
                                case TITLE_OCTOBER:
                                    month = year + "-10";
                                    feeReceived = new BigDecimal(value.toString());
                                    break;
                                case TITLE_NOVEMBER:
                                    month = year + "-11";
                                    feeReceived = new BigDecimal(value.toString());
                                    break;
                                case TITLE_DECEMBER:
                                    month = year + "-12";
                                    feeReceived = new BigDecimal(value.toString());
                                    break;
                            }
                            dues.setUser(user);
                            if (n > 1){//从第三列开始
                                dues.setYear(month);
                                dues.setFeeReceived(feeReceived);
                                partyMembershipDues.add(dues);
                            }
                        }

                    }
                }

            }
        }
        return partyMembershipDues;
    }

    public static void downloadExcel(HttpServletResponse response, HttpServletRequest request) throws UnsupportedEncodingException {
        System.out.println("控制台输出：走入下载");
        response.setCharacterEncoding("utf-8");
        response.setContentType("multipart/form-data");
        //获取文件的路径
        String excelPath = request.getSession().getServletContext().getRealPath("/file/"+"用户.xls");
        response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode("用户.xls", "UTF-8"));
        try {
            String path="D:\\upload";
            InputStream inputStream = new FileInputStream(new File(excelPath));

            OutputStream os = response.getOutputStream();
            byte[] b = new byte[2048];
            int length;
            while ((length = inputStream.read(b)) > 0) {
                os.write(b, 0, length);
            }

            // 这里主要关闭。
            os.close();

            inputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
	
	private static void writeExcel(){
		Workbook book = new XSSFWorkbook();
		Sheet sheet = book.createSheet("数据测试");
		Row titleRow = sheet.createRow(0);
		Cell cell = titleRow.createCell(0, Cell.CELL_TYPE_STRING);
		cell.setCellValue(TITLE_USERNAME);
		cell = titleRow.createCell(1, Cell.CELL_TYPE_NUMERIC);
		cell.setCellValue(TITLE_PHONE);
		cell = titleRow.createCell(2, Cell.CELL_TYPE_STRING);
		cell.setCellValue(TITLE_SEX);
		List<User> models = new ArrayList<>();
		for(int i=0; i<models.size(); i++){
			User m = models.get(i);
			Row row = sheet.createRow(i+1);
			Cell c = titleRow.createCell(0, Cell.CELL_TYPE_STRING);
			c.setCellValue(m.getNickname());
			c = titleRow.createCell(1, Cell.CELL_TYPE_NUMERIC);
			c.setCellValue(m.getPhone());
			c = titleRow.createCell(2, Cell.CELL_TYPE_STRING);
			c.setCellValue(m.getSex());
		}
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream("d:/test.xlsx");
			book.write(fos);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fos.close();
				book.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
}
