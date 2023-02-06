package com.application.profile.service.rest.v1;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import com.application.profile.domain.model.Profile;

public class ExcelHelper {

	public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	static String[] HEADERs = { "Id", "Name", "Primary_Skill", "Location", "Availability", "Proposed_By", "Source" };
	static String SHEET = "Profiles";

	public static boolean hasExcelFormat(MultipartFile file) {

		if (!TYPE.equals(file.getContentType())) {
			return false;
		}

		return true;
	}

	public static List<Profile> excelToData(InputStream is) {
		try {
			Workbook workbook = new XSSFWorkbook(is);

			org.apache.poi.ss.usermodel.Sheet sheet = workbook.getSheet(SHEET);
			Iterator<Row> rows = sheet.iterator();

			List<Profile> excels = new ArrayList<>();

			int rowNumber = 0;
			while (rows.hasNext()) {
				Row currentRow = rows.next();

				if (rowNumber == 0) {
					rowNumber++;
					continue;
				}

				Iterator<Cell> cellsInRow = currentRow.iterator();

				Profile excelProfile = new Profile();

				int cellIdx = 0;
				while (cellsInRow.hasNext()) {
					Cell currentCell = cellsInRow.next();

					switch (cellIdx) {
					case 0:
						excelProfile.setId((long) currentCell.getNumericCellValue());
						break;

					case 1:
						excelProfile.setName(currentCell.getStringCellValue());
						break;

					case 2:
						excelProfile.setPrimarySkill(currentCell.getStringCellValue());
						break;
						
					case 3:
						excelProfile.setLocation(currentCell.getStringCellValue());
						break;

					case 4:
						excelProfile.setAvailability(currentCell.getStringCellValue());
						break;

					case 5:
						excelProfile.setProposedBy(currentCell.getStringCellValue());
						break;
					case 6:
						excelProfile.setSource(currentCell.getStringCellValue());
						break;

					default:
						break;
					}

					cellIdx++;
				}

				excels.add(excelProfile);
			}

			workbook.close();

			return excels;
		} catch (IOException e) {
			throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
		}
	}
}
