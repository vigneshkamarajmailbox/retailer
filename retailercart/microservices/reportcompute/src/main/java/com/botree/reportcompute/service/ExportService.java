package com.botree.reportcompute.service;

import com.botree.common.constants.ReportConstants;
import com.botree.common.constants.StringConstants;
import com.botree.common.dto.AttachmentDTO;
import com.botree.common.dto.EmailDTO;
import com.botree.common.dto.SheetDTO;
import com.botree.common.model.ExportModel;
import com.botree.common.service.EmailService;
import com.botree.common.service.IQueryService;
import com.botree.common.util.Function;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Export Service to extract the report in excel format.
 * @author vinodkumar.a
 */
@Component
public class ExportService {

    /** LOG. */
    private static final Logger LOG = LoggerFactory.getLogger(ExportService.class);
    /** queryService. */
    private final IQueryService queryService;
    /** emailService. */
    private final EmailService emailService;
    /** imageLocalFilePath. */
    @Value("${image.path}")
    private String imageLocalFilePath;
    /** from. */
    @Value("${mail.report.from}")
    private String from;
    /** subject. */
    @Value("${mail.report.subject}")
    private String subject;
    /** message. */
    @Value("${mail.report.message}")
    private String message;

    /**
     * Constructor Method.
     * @param queryServiceIn queryServiceIn
     * @param emailServiceIn emailServiceIn
     */
    public ExportService(final IQueryService queryServiceIn, final EmailService emailServiceIn) {
        this.queryService = queryServiceIn;
        this.emailService = emailServiceIn;
    }

    /**
     * Method the fetch the file related data.
     * @param model model
     */
    public final void extractReportFile(final ExportModel model) {
        var path = imageLocalFilePath + File.separatorChar + StringConstants.DOWNLOADFILE;
        var fileName = model.getLoginCode() + ReportConstants.UNDER_SCORE + model.getTitle()
                + ReportConstants.UNDER_SCORE + System.nanoTime() + ReportConstants.EXT_XLSX;
        var file = new File(path + File.separatorChar + fileName);
        try (Workbook workbook = new SXSSFWorkbook(); var out = new FileOutputStream(file)) {
            queryService.getReport(model.getTitle()).forEach(data -> {
                fetchReportData(model, data);
                createReportSheet(workbook, data, model);
            });
            workbook.write(out);
            if (model.getEmailId() != null && !model.getEmailId().isEmpty()) {
                sendReportMail(model, file);
            }
        } catch (Exception ex) {
            LOG.error("exception while extract the report file :: ", ex);
        }
    }

    /**
     * Method the send report in mail.
     * @param model model
     * @param file  file
     */
    private void sendReportMail(final ExportModel model, final File file) {
        new Thread(() -> {
            var toList = List.of(model.getEmailId());
            var emailDTO = new EmailDTO();
            emailDTO.setSubject(MessageFormat.format(subject, model.getTitle()));
            emailDTO.setMessage(MessageFormat.format(message, model.getUserName(), model.getTitle()));
            emailDTO.setFrom(from);
            emailDTO.setTo(toList);
            emailDTO.setHtml(Boolean.TRUE);
            List<AttachmentDTO> attachments = new ArrayList<>();
            attachments.add(new AttachmentDTO(file.getName(), file));
            emailDTO.setAttachments(attachments);
            emailService.sendMail(emailDTO);
        }).start();
    }

    /**
     * This method is used to create a excel sheet with the data provided.
     * @param workbook Workbook
     * @param sheetDTO sheetDTO
     * @param model    model
     */
    private void createReportSheet(final Workbook workbook, final SheetDTO sheetDTO, final ExportModel model) {
        var sheet = workbook.createSheet(sheetDTO.getSheetname());
        sheet.setDisplayGridlines(Boolean.FALSE);

        // Create header
        var rowNum = 0;
        var cellNum = 0;
        var header = sheet.createRow(rowNum);
        rowNum++;
        var style = getHeaderCellStyle(workbook);
        for (var s : model.getHeader()) {
            header.createCell(cellNum++).setCellValue(s);
            header.getCell(cellNum - 1).setCellStyle(style);
        }

        // Create data row
        for (var map : model.getExportData()) {
            cellNum = 0;
            var dataRow = sheet.createRow(rowNum++);
            for (var s : model.getField()) {
                createCell(cellNum++, dataRow, map.get(s), workbook);
            }
        }

        // Create summary row
        var summaryRow = sheet.createRow(rowNum);
        if (!model.getExportData().isEmpty()) {
            generateSummary(workbook, summaryRow, model, sheetDTO);
        } else {
            cellNum = 0;
            summaryRow.createCell(cellNum).setCellValue(ReportConstants.MSG_EMPTY_RECORD);
        }


        // Auto resize columns
        ((SXSSFSheet) sheet).trackAllColumnsForAutoSizing();
        for (var colNum = 0; colNum < header.getLastCellNum(); colNum++) {
            sheet.autoSizeColumn(colNum);
        }
    }

    /**
     * Method to generate the summary row.
     * @param workbook   workbook
     * @param summaryRow summaryRow
     * @param model      model
     * @param sheetDTO   sheetDTO
     */
    private void generateSummary(final Workbook workbook, final Row summaryRow, final ExportModel model,
                                 final SheetDTO sheetDTO) {
        var cellNum = 0;
        var alphabet = 1;
        var end = sheetDTO.getIndex() + model.getExportData().size() - 1;
        for (var s : model.getField()) {
            summaryRow.createCell(cellNum);
            summaryRow.getCell(cellNum).setCellStyle(getSummaryCellStyle(workbook));
            if (model.getSummary().contains(s)) {
                var formula =
                        ReportConstants.FUNC_SUM + ReportConstants.OPEN_BRACKET + Function.getAlphabet(alphabet)
                                + sheetDTO.getIndex() + ReportConstants.COLON_REGEX + Function.getAlphabet(alphabet)
                                + end + ReportConstants.CLOSE_BRACKET;
                summaryRow.getCell(cellNum).setCellFormula(formula);
            }
            alphabet++;
            cellNum++;
        }
    }

    /**
     * This method is used create header style.
     * @param wb Workbook
     * @return CellStyle
     */
    private CellStyle getHeaderCellStyle(final Workbook wb) {
        var style = setCellStyle(wb, IndexedColors.GREY_40_PERCENT.index, HorizontalAlignment.CENTER);
        createFont(wb, style);
        return style;
    }

    /**
     * This method is used create summary style.
     * @param workbook1 workbook1
     * @return CellStyle
     */
    private CellStyle getSummaryCellStyle(final Workbook workbook1) {
        var summaryStyle = setCellStyle(workbook1, IndexedColors.GREY_25_PERCENT.index, HorizontalAlignment.RIGHT);
        createFont(workbook1, summaryStyle);
        return summaryStyle;
    }

    /**
     * Method to set cell style.
     * @param workbook   workbook
     * @param colorIndex colorIndex
     * @param alignment  alignment
     * @return cell style
     */
    private CellStyle setCellStyle(final Workbook workbook, final short colorIndex,
                                   final HorizontalAlignment alignment) {
        var style = workbook.createCellStyle();
        var summaryCellStyle = (XSSFCellStyle) style;
        summaryCellStyle.setFillForegroundColor(colorIndex);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setAlignment(alignment);
        style.setBorderLeft(BorderStyle.MEDIUM);
        style.setBorderTop(BorderStyle.MEDIUM);
        style.setBorderRight(BorderStyle.MEDIUM);
        style.setBorderBottom(BorderStyle.MEDIUM);
        return style;
    }

    /**
     * Method to create font for header and summary.
     * @param workbook     workbook
     * @param summaryStyle summaryStyle
     */
    private void createFont(final Workbook workbook, final CellStyle summaryStyle) {
        var summaryFont = workbook.createFont();
        summaryFont.setBold(true);
        summaryFont.setFontName(ReportConstants.FONTNAME);
        summaryFont.setFontHeightInPoints((short) StringConstants.CONST_INT_11);
        summaryFont.setColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
        summaryStyle.setFont(summaryFont);
    }

    /**
     * This method is used to create cell.
     * @param cellNum  Cell Number
     * @param row      Row
     * @param value    cell value
     * @param workbook Workbook
     */
    private void createCell(final int cellNum, final Row row, final Object value, final Workbook workbook) {
        if (value instanceof Date) {
            row.createCell(cellNum).setCellValue((Date) value);
            var cellStyle = setCellStyle(workbook, IndexedColors.WHITE.index, HorizontalAlignment.CENTER);
            var createHelper = workbook.getCreationHelper();
            var dateFormat = createHelper.createDataFormat().getFormat(ReportConstants.DATEFORMAT_PATTERN);
            cellStyle.setDataFormat(dateFormat);
            row.getCell(cellNum).setCellStyle(cellStyle);
        } else if (value instanceof String) {
            row.createCell(cellNum).setCellValue((String) value);
            var cellStyle = setCellStyle(workbook, IndexedColors.WHITE.index, HorizontalAlignment.LEFT);
            row.getCell(cellNum).setCellStyle(cellStyle);
        } else if (value instanceof Double) {
            row.createCell(cellNum).setCellValue((Double) value);
            var cellStyle = setCellStyle(workbook, IndexedColors.WHITE.index, HorizontalAlignment.RIGHT);
            row.getCell(cellNum).setCellStyle(cellStyle);
        } else if (value instanceof BigDecimal) {
            row.createCell(cellNum).setCellValue(((BigDecimal) value).doubleValue());
            var cellStyle = setCellStyle(workbook, IndexedColors.WHITE.index, HorizontalAlignment.RIGHT);
            row.getCell(cellNum).setCellStyle(cellStyle);
        } else if (value instanceof Integer) {
            row.createCell(cellNum).setCellValue((Integer) value);
            var cellStyle = setCellStyle(workbook, IndexedColors.WHITE.index, HorizontalAlignment.RIGHT);
            row.getCell(cellNum).setCellStyle(cellStyle);
        } else if (value instanceof Long) {
            row.createCell(cellNum).setCellValue((Long) value);
            var cellStyle = setCellStyle(workbook, IndexedColors.WHITE.index, HorizontalAlignment.RIGHT);
            row.getCell(cellNum).setCellStyle(cellStyle);
        } else {
            row.createCell(cellNum).setCellValue("");
            var cellStyle = setCellStyle(workbook, IndexedColors.WHITE.index, HorizontalAlignment.LEFT);
            row.getCell(cellNum).setCellStyle(cellStyle);
        }
    }

    /**
     * Method to fetch report data.
     * @param model model
     * @param data  data
     */
    private void fetchReportData(final ExportModel model, final SheetDTO data) {
        model.setField(new ArrayList<>(Arrays.asList(
                Arrays.stream(data.getField().split(StringConstants.CONST_COMMA)).map(String::trim)
                        .toArray(String[]::new))));
        model.setHeader(new ArrayList<>(Arrays.asList(
                Arrays.stream(data.getHeader().split(StringConstants.CONST_COMMA)).map(String::trim)
                        .toArray(String[]::new))));
        model.setSummary(new ArrayList<>(Arrays.asList(
                Arrays.stream(data.getSummary().split(StringConstants.CONST_COMMA)).map(String::trim)
                        .toArray(String[]::new))));
        model.setExportData(new ArrayList<>(model.getExportDataMap().get(data.getQuery())));
    }
}
