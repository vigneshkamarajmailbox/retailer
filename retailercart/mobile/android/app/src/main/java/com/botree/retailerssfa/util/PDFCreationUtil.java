package com.botree.retailerssfa.util;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.botree.retailerssfa.R;
import com.botree.retailerssfa.db.FileAccessUtil;
import com.botree.retailerssfa.fragmentmanager.ESFAFragTags;
import com.botree.retailerssfa.models.OrderBookingVO;
import com.botree.retailerssfa.support.EnglishNumberToWords;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.botree.retailerssfa.controller.constants.AppConstant.TAX_CGST;
import static com.botree.retailerssfa.controller.constants.AppConstant.TAX_IGST;
import static com.botree.retailerssfa.controller.constants.AppConstant.TAX_SGST;
import static com.botree.retailerssfa.controller.constants.AppConstant.TAX_UTGST;


public class PDFCreationUtil {
    private static final String SNO = "SNo";
    private static final String PRODUCT_CODE = "Product Code";
    private static final String PRODUCT_NAME = "Product Name";
    private static final String INVOICE_QUANTITY = "Invoice Qty";
    private static final String RATE = "Rate";
    private static final String QUANTITY = "Quantity";
    private static final String UOM = "UOM";
    private static final String GROSS_AMOUNT = "Gross Amount";
    private static final String DISCOUNT = "Discount";
    private static final String TAX = "Tax";
    private static final String NET_AMOUNT = "Net Amount";
    private static final String TAX_DESC = "Tax Desc";
    private static final String TAX_PERCENT = "Tax%";
    private static final String TAX_AMOUNT = "Tax Amt";
    private static final String TAXABLE_AMT = "Taxable Amt";
    private static final String TAG = PDFCreationUtil.class.getSimpleName();
    private static final float SPACING_10F = 10f;
    private static final float SPACING_30F = 30f;
    private static final int WIDTH_PERCENTAGE_100 = 100;
    private static final int WIDTH_PERCENTAGE_50 = 50;
    private static final float PADDING_5F = 5f;
    private static final String INVOICE_SUMMARY = "invoiceSummary";
    private static final float SPACING_5F = 5f;
    private static final float WIDTH_PERCENTAGE_40 = 40;
    private static PDFCreationUtil ourInstance = null;
    private static WeakReference<Context> activityRef;
    private Font catFont18 = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
    private Font catFontNormal12 = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL);
    private Font catFontBold12 = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD);

    private PDFCreationUtil() {
    }

    public static PDFCreationUtil getInstance() {
        return ourInstance;
    }

    public static void initPDFCreationUtil(Context context) {
        activityRef = new WeakReference<>(context);
        if (ourInstance == null) {
            ourInstance = new PDFCreationUtil();
        }
    }

    public File createCustomerSummaryPdf(String screenType, List<OrderBookingVO> childtem,
                                         OrderBookingVO parentList, boolean isPTRWithTax, boolean isPrimaryDiscountEnable) {
        File filePath = null;
        try {

            if (screenType.equalsIgnoreCase(INVOICE_SUMMARY))
                filePath = getFile("Inv_" + parentList.getRetailerName() + ".pdf");
            else
                filePath = getFile("Ord_" + parentList.getRetailerName() + ".pdf");
            Document document = createDocument();

            PdfPTable headingTable = getHeadingTable(screenType);
            PdfPTable headerTable = getHeaderTable(parentList, true);
            PdfPTable customerDataTable = getCustomerDataTableNew(childtem, parentList, screenType, isPTRWithTax,isPrimaryDiscountEnable);

            PdfWriter.getInstance(document, new FileOutputStream(filePath.getPath()));
            document.open();
            document.add(headingTable);
            document.add(headerTable);

            document.add(customerDataTable);
            if (screenType.equalsIgnoreCase(INVOICE_SUMMARY)) {
                PdfPTable invoiceTotal = getInvoiceTotalSummary(parentList);
                document.add(invoiceTotal);
            }

            PdfPTable declarationTable = getDeclarationTable(parentList);
            document.add(declarationTable);
            document.close();

        } catch (DocumentException | IOException e) {
            Log.e(TAG, "createCustomerSummaryPdf: " + e.getMessage(), e);
        }
        return filePath;
    }

    private PdfPTable getDeclarationTable(OrderBookingVO parentList) {
        PdfPTable mainTable = new PdfPTable(new float[]{5, 5});
        mainTable.setWidthPercentage(WIDTH_PERCENTAGE_100);
        mainTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);
        mainTable.getDefaultCell().setBorderColor(BaseColor.WHITE);
        mainTable.setSpacingBefore(5);

        String rupeesInWords = EnglishNumberToWords.convert((long) parentList.getTotalAmount().doubleValue());
        PdfPCell wordsCell = new PdfPCell(getTextPhrase("In Words : " + rupeesInWords.toUpperCase() + " ONLY", catFontNormal12));
        wordsCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        wordsCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        wordsCell.setColspan(2);
        wordsCell.setBorderColorBottom(BaseColor.WHITE);
        wordsCell.setPadding(5);
        wordsCell.setBorder(Rectangle.NO_BORDER);
        mainTable.addCell(wordsCell);
        mainTable.completeRow();
        mainTable.addCell(getTextPhrase("Declaration ", catFontNormal12));
        PdfPCell emptyCell = getBorderLessCell(Element.ALIGN_CENTER);
        emptyCell.setPhrase(getTextPhrase("  ", catFontNormal12));
        mainTable.addCell(emptyCell);
        mainTable.addCell(emptyCell);
        mainTable.addCell(emptyCell);
        mainTable.addCell(emptyCell);
        mainTable.addCell(emptyCell);
        mainTable.addCell(getTextPhrase("(E. & O.E.) ", catFontNormal12));

        PdfPCell distrNameCell = getBorderLessCell(Element.ALIGN_RIGHT);
        distrNameCell.setPhrase(getTextPhrase("For :" + parentList.getDistrCode(), catFontNormal12));
        mainTable.addCell(distrNameCell);
        return mainTable;
    }

    private PdfPTable getHeaderTable(OrderBookingVO parentList, boolean isAddressVisible) {
        PdfPTable mainTable = new PdfPTable(new float[]{5, 1, 5});
        mainTable.setWidthPercentage(WIDTH_PERCENTAGE_100);
        mainTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);
        mainTable.getDefaultCell().setBorderColor(BaseColor.WHITE);
        mainTable.setSpacingBefore(SPACING_30F);
        mainTable.addCell(getAddressTable(parentList, isAddressVisible));
        mainTable.addCell("");
        mainTable.addCell(getInvoiceNoTable(parentList, isAddressVisible));

        return mainTable;
    }

    private PdfPTable getAddressTable(OrderBookingVO parentList, boolean isAddressVisible) {
        PdfPTable addressTable = new PdfPTable(new float[]{3});
        addressTable.setWidthPercentage(75);
        addressTable.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
        addressTable.getDefaultCell().setBorderColor(BaseColor.WHITE);
        addressTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);

        if (isAddressVisible) {

            addressTable.addCell(getTextPhrase("To:", catFontBold12));
            addressTable.addCell(getTextPhrase(parentList.getRetailerName() + "\n" + parentList.getAddress() + "\n Ph : " + parentList.getMobileNo(), catFontNormal12));
        } else {
            SFASharedPref sfaSharedPref = SFASharedPref.getOurInstance();
            addressTable = new PdfPTable(new float[]{2, .5f, 3});
            addressTable.setWidthPercentage(75);
            addressTable.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
            addressTable.getDefaultCell().setBorderColor(BaseColor.WHITE);
            addressTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);

            addressTable.addCell(getTextPhrase("Date", catFontNormal12));
            addressTable.addCell(getTextPhrase(":", catFontNormal12));
            addressTable.addCell(getTextPhrase(DateUtil.convertDateStringFormat(parentList.getOrderDate()), catFontNormal12));

            addressTable.addCell(getTextPhrase("SM Name", catFontNormal12));
            addressTable.addCell(getTextPhrase(":", catFontNormal12));
            addressTable.addCell(getTextPhrase(sfaSharedPref.readString(SFASharedPref.PREF_USER_NAME), catFontNormal12));
        }

        return addressTable;
    }

    private PdfPTable getInvoiceNoTable(OrderBookingVO parentList, boolean isAddressVisible) {
        SFASharedPref sfaSharedPref = SFASharedPref.getOurInstance();
        PdfPTable invoiceTable = new PdfPTable(new float[]{2, .5f, 3});
        invoiceTable.setWidthPercentage(25);
        invoiceTable.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
        invoiceTable.getDefaultCell().setBorderColor(BaseColor.WHITE);
        invoiceTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);
        invoiceTable.setHorizontalAlignment(Element.ALIGN_RIGHT);


        /*invoiceTable.addCell(getTextPhrase("Ref No", catFontNormal12));
        invoiceTable.addCell(getTextPhrase(":", catFontNormal12));
        invoiceTable.addCell(getTextPhrase(parentList.getOrderInvoiceNo(), catFontNormal12));*/

        if (isAddressVisible) {

            invoiceTable.addCell(getTextPhrase("Date", catFontNormal12));
            invoiceTable.addCell(getTextPhrase(":", catFontNormal12));
            invoiceTable.addCell(getTextPhrase(DateUtil.convertDateStringFormat(parentList.getOrderDate()), catFontNormal12));

            invoiceTable.addCell(getTextPhrase("SM Name", catFontNormal12));
            invoiceTable.addCell(getTextPhrase(":", catFontNormal12));
            invoiceTable.addCell(getTextPhrase(sfaSharedPref.readString(SFASharedPref.PREF_USER_NAME), catFontNormal12));
        }
        invoiceTable.addCell(getTextPhrase("RT Name", catFontNormal12));
        invoiceTable.addCell(getTextPhrase(":", catFontNormal12));
        invoiceTable.addCell(getTextPhrase(parentList.getRetailerName(), catFontNormal12));

        invoiceTable.addCell(getTextPhrase("Route", catFontNormal12));
        invoiceTable.addCell(getTextPhrase(":", catFontNormal12));
        invoiceTable.addCell(getTextPhrase(parentList.getRouteName(), catFontNormal12));

        /*invoiceTable.addCell(getTextPhrase("Shipping Date", catFontNormal12));
        invoiceTable.addCell(getTextPhrase(":", catFontNormal12));
        invoiceTable.addCell(getTextPhrase(DateUtil.convertDateStringFormat(parentList.getOrderDate()), catFontNormal12));*/

        return invoiceTable;
    }

    @NonNull
    private PdfPTable getSalemanNameAndDate(Font catFont, String orderDate) {
        SFASharedPref sfaSharedPref = SFASharedPref.getOurInstance();
        PdfPTable headingTable = new PdfPTable(1);
        headingTable.setWidthPercentage(WIDTH_PERCENTAGE_100);
        PdfPCell salesmanNameCell = getBorderLessCell(Element.ALIGN_LEFT);
        salesmanNameCell.setPhrase(new Phrase("Salesman Name : " + sfaSharedPref.readString(SFASharedPref.PREF_USER_NAME), catFont));
        headingTable.addCell(salesmanNameCell);

        PdfPCell orderDateCell = getBorderLessCell(Element.ALIGN_LEFT);
        orderDateCell.setPhrase(new Phrase("Order Date : " + orderDate));
        headingTable.addCell(orderDateCell);
        headingTable.setSpacingAfter(SPACING_10F);
        headingTable.setSpacingBefore(SPACING_10F);
        return headingTable;
    }

    private PdfPTable getCustomerDataTableNew(List<OrderBookingVO> childObject,
                                              OrderBookingVO orderBookingVO,
                                              String screenType, boolean isPTRWithTax,boolean isPrimaryDiscountEnable) {

        PdfPTable table = new PdfPTable(new float[]{2, 4, 8, 3, 3, 4, 4, 4, 4, 4});
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.getDefaultCell().setPadding(PADDING_5F);
        table.setSpacingBefore(SPACING_10F);

        int percentWidth = (int) ((PageSize.A4.getWidth() * 5) / 100);
        table.setTotalWidth(PageSize.A4.getWidth() - percentWidth);
        table.setLockedWidth(true);

        table.addCell(getTextPhrase(SNO, catFontBold12));
        table.addCell(getTextPhrase(PRODUCT_CODE, catFontBold12));
        table.addCell(getTextPhrase(PRODUCT_NAME, catFontBold12));
        table.addCell(getTextPhrase(INVOICE_QUANTITY, catFontBold12));
        table.addCell(getTextPhrase(UOM, catFontBold12));
        table.addCell(getTextPhrase(RATE, catFontBold12));
        table.addCell(getTextPhrase(DISCOUNT, catFontBold12));

        if (isPTRWithTax && !screenType.equalsIgnoreCase(INVOICE_SUMMARY)) {
            table.addCell(getTextPhrase(TAXABLE_AMT, catFontBold12));
        } else {
            table.addCell(getTextPhrase(GROSS_AMOUNT, catFontBold12));
        }

        table.addCell(getTextPhrase(TAX, catFontBold12));
        table.addCell(getTextPhrase(NET_AMOUNT, catFontBold12));
        PdfPCell[] cells = table.getRow(0).getCells();
        for (PdfPCell cell : cells) {
            cell.setBackgroundColor(new BaseColor(ContextCompat.getColor(activityRef.get(), R.color.white)));
            cell.setUseVariableBorders(true);
            cell.setBorderColorLeft(BaseColor.WHITE);
            cell.setBorderColorRight(BaseColor.WHITE);
            cell.setBorderColorTop(BaseColor.BLACK);
            cell.setBorderColorBottom(BaseColor.BLACK);
        }
        for (int i = 0; i < childObject.size(); i++) {
            table.addCell(getTextPhrase(String.valueOf(i + 1), catFontNormal12));
            table.addCell(getTextPhrase(childObject.get(i).getProdCode(), catFontNormal12));
            table.addCell(getTextPhrase(childObject.get(i).getProdName(), catFontNormal12));
            table.addCell(getTextPhrase(OrderSupportUtil.getInstance().getStringQty(childObject.get(i).getQuantity()), catFontNormal12));
            table.addCell(getTextPhrase(String.valueOf(childObject.get(i).getUomId()), catFontNormal12));
            table.addCell(getTextPhrase(String.valueOf(childObject.get(i).getSellPrice()), catFontNormal12));
            table.addCell(getTextPhrase(MessageFormat.format("₹{0}", String.format(Locale.ENGLISH, "%.2f", childObject.get(i).getSchemeAmount())), catFontNormal12));

            if (isPTRWithTax && !screenType.equalsIgnoreCase(INVOICE_SUMMARY)) {
                table.addCell(getTextPhrase(MessageFormat.format("₹{0}", String.format(Locale.ENGLISH, "%.2f", childObject.get(i).getlGrossAmt())), catFontNormal12));
            } else {
                table.addCell(getTextPhrase(MessageFormat.format("₹{0}", String.format(Locale.ENGLISH, "%.2f", childObject.get(i).getOrderValue())), catFontNormal12));
            }

            table.addCell(getTextPhrase(MessageFormat.format("₹{0}", String.format(Locale.ENGLISH, "%.2f", childObject.get(i).getTax())), catFontNormal12));
            table.addCell(getTextPhrase(MessageFormat.format("₹{0}", String.format(Locale.ENGLISH, "%.2f", childObject.get(i).getTotalAmount())), catFontNormal12));
            setBorderLessCell(table, i + 1);
        }

        PdfPCell cellTotal = new PdfPCell(getTextPhrase("Total", catFontNormal12));
        cellTotal.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellTotal.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cellTotal.setColspan(6);
        table.addCell(cellTotal);

        if (isPTRWithTax && !screenType.equalsIgnoreCase(INVOICE_SUMMARY)) {

            double schemeAmount = getInvoiceSchemeAmount(orderBookingVO, isPrimaryDiscountEnable);

            table.addCell(getTextPhrase(MessageFormat.format("₹{0}", String.format(Locale.ENGLISH, "%.2f", schemeAmount)), catFontNormal12));
            table.addCell(getTextPhrase("₹" + AppUtils.getOurInstance().decimalFormatWithTwoDigit(orderBookingVO.getlGrossAmt().doubleValue()), catFontNormal12));
        } else {
            double schemeAmount = getOrderSchemeAmount(orderBookingVO, isPrimaryDiscountEnable);

            table.addCell(getTextPhrase(MessageFormat.format("₹{0}", String.format(Locale.ENGLISH, "%.2f", schemeAmount)), catFontNormal12));
            table.addCell(getTextPhrase("₹" + AppUtils.getOurInstance().decimalFormatWithTwoDigit(orderBookingVO.getOrderValue().doubleValue()), catFontNormal12));
        }

        table.addCell(getTextPhrase(MessageFormat.format("₹{0}", String.format(Locale.ENGLISH, "%.2f", orderBookingVO.getTax())), catFontNormal12));
        table.addCell(getTextPhrase(MessageFormat.format("₹{0}", String.format(Locale.ENGLISH, "%.2f", orderBookingVO.getTotalAmount())), catFontNormal12));
        table.completeRow();
        for (PdfPCell cell : table.getRow(childObject.size() + 1).getCells()) {
            if (cell != null) {
                cell.setBackgroundColor(new BaseColor(ContextCompat.getColor(activityRef.get(), R.color.white)));
                cell.setUseVariableBorders(true);
                cell.setBorderColorLeft(BaseColor.WHITE);
                cell.setBorderColorRight(BaseColor.WHITE);
                cell.setBorderColorTop(BaseColor.BLACK);
                cell.setBorderColorBottom(BaseColor.BLACK);
            }
        }
        return table;
    }

    private double getOrderSchemeAmount(OrderBookingVO orderBookingVO, boolean isPrimaryDiscountEnable) {
        double schemeAmount;
        if(isPrimaryDiscountEnable){
             schemeAmount = (orderBookingVO.getOrderValue().doubleValue() - orderBookingVO.getPrimDiscOrderValue().doubleValue())
                    + orderBookingVO.getSchemeAmount().doubleValue();
        }else {
            schemeAmount = orderBookingVO.getSchemeAmount().doubleValue();
        }
        return schemeAmount;
    }

    private double getInvoiceSchemeAmount(OrderBookingVO orderBookingVO, boolean isPrimaryDiscountEnable) {
        double schemeAmount;
        if(isPrimaryDiscountEnable){
            schemeAmount = (orderBookingVO.getlGrossAmt().doubleValue() - orderBookingVO.getPrimDiscOrderValue().doubleValue())
                    + orderBookingVO.getSchemeAmount().doubleValue();
        }else {
            schemeAmount = orderBookingVO.getSchemeAmount().doubleValue();
        }
        return schemeAmount;
    }

    private Phrase getTextPhrase(String productName, Font catFontBold12) {
        return new Phrase(productName, catFontBold12);
    }

    @NonNull
    private Document createDocument() {
        return new Document(PageSize.A4, 10, 10, 50, 50);
    }

    @NonNull
    private PdfPTable getHeadingTable(String screenType) {
        PdfPTable headingTable = new PdfPTable(1);
        headingTable.setWidthPercentage(WIDTH_PERCENTAGE_100);
        PdfPCell headingCell;
        headingCell = getBorderLessCell(Element.ALIGN_CENTER);
        if (screenType.equalsIgnoreCase(INVOICE_SUMMARY))
            headingCell.setPhrase(new Phrase("Invoice Summary", catFont18));
        else
            headingCell.setPhrase(new Phrase(ESFAFragTags.ORDER_SUMMARY.getValue(), catFont18));
        headingTable.addCell(headingCell);
        headingTable.setSpacingAfter(SPACING_10F);
        headingTable.setSpacingBefore(SPACING_10F);
        return headingTable;
    }


    @NonNull
    private PdfPCell getBorderLessCell(int alignment) {
        PdfPCell borderLessCell = new PdfPCell();
        borderLessCell.setBorder(Rectangle.NO_BORDER);
        borderLessCell.setBorderColor(BaseColor.WHITE);
        borderLessCell.setPadding(PADDING_5F);
        borderLessCell.setHorizontalAlignment(alignment);
        return borderLessCell;
    }


    private PdfPTable getInvoiceTotalSummary(OrderBookingVO parentList) {

        PdfPTable mainTable = new PdfPTable(2);
        mainTable.setWidthPercentage(WIDTH_PERCENTAGE_100);
        mainTable.addCell(getTaxBreakUpTable(parentList));
        mainTable.addCell(getPayableBreakUpTable(parentList));

        PdfPCell[] cells = mainTable.getRow(0).getCells();
        for (PdfPCell cell : cells) {
            cell.setBackgroundColor(new BaseColor(ContextCompat.getColor(activityRef.get(), R.color.white)));
            cell.setUseVariableBorders(true);
            cell.setBorderColorLeft(BaseColor.WHITE);
            cell.setBorderColorRight(BaseColor.WHITE);
            cell.setBorderColorTop(BaseColor.WHITE);
            cell.setBorderColorBottom(BaseColor.BLACK);
        }

        return mainTable;
    }

    @NonNull
    private PdfPTable getPayableBreakUpTable(OrderBookingVO parentList) {
        PdfPTable firstTableCell = new PdfPTable(new float[]{8, 2});
        firstTableCell.setWidthPercentage(WIDTH_PERCENTAGE_50);
        firstTableCell.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
        firstTableCell.getDefaultCell().setBorder(Rectangle.NO_BORDER);
        firstTableCell.getDefaultCell().setBorderColor(BaseColor.WHITE);
        firstTableCell.getDefaultCell().setPadding(PADDING_5F);
        firstTableCell.setSpacingBefore(SPACING_10F);
        Double totalGrossAmount = 0d;
        Double totalDiscountAmount = 0d;
        Double totalTaxAmount = 0d;
        Double totalNetAmount = 0d;
        totalGrossAmount = totalGrossAmount + parentList.getOrderValue().doubleValue();
        totalDiscountAmount = totalDiscountAmount + parentList.getSchemeAmount().doubleValue();
        totalTaxAmount = totalTaxAmount + parentList.getTax();
        totalNetAmount = totalNetAmount + parentList.getTotalAmount().doubleValue();
        PdfPCell pdfPCell = getBorderLessCell(Element.ALIGN_RIGHT);
        firstTableCell.addCell(getTextPhrase(GROSS_AMOUNT, catFontNormal12));
        pdfPCell.setPhrase(getTextPhrase(MessageFormat.format("₹{0}", String.format(Locale.ENGLISH, "%.2f", totalGrossAmount)), catFontBold12));
        firstTableCell.addCell(pdfPCell);
        firstTableCell.addCell(getTextPhrase("Scheme Disc Amt(-)", catFontNormal12));
        pdfPCell.setPhrase(getTextPhrase(MessageFormat.format("₹{0}", String.format(Locale.ENGLISH, "%.2f", totalDiscountAmount)), catFontBold12));
        firstTableCell.addCell(pdfPCell);
        firstTableCell.addCell(getTextPhrase("Tax Amount(+)", catFontNormal12));
        pdfPCell.setPhrase(getTextPhrase(MessageFormat.format("₹{0}", String.format(Locale.ENGLISH, "%.2f", totalTaxAmount)), catFontBold12));
        firstTableCell.addCell(pdfPCell);
        firstTableCell.addCell(getTextPhrase("Round Off", catFontNormal12));
        double roundOff = Math.ceil(totalNetAmount) - totalNetAmount;
        pdfPCell.setPhrase(getTextPhrase(MessageFormat.format("₹{0}", String.format(Locale.ENGLISH, "%.2f", roundOff)), catFontBold12));
        firstTableCell.addCell(pdfPCell);
        firstTableCell.addCell(getTextPhrase("Net Payable", catFontNormal12));
        pdfPCell.setPhrase(getTextPhrase(MessageFormat.format("₹{0}", String.format(Locale.ENGLISH, "%.2f", totalNetAmount + roundOff)), catFontBold12));
        firstTableCell.addCell(pdfPCell);
        return firstTableCell;
    }

    private PdfPTable getTaxBreakUpTable(OrderBookingVO parentList) {
        PdfPTable taxTable = new PdfPTable(new float[]{3, 3, 3, 3});
        taxTable.setWidthPercentage(WIDTH_PERCENTAGE_40);
        taxTable.setExtendLastRow(true);
        taxTable.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
        taxTable.getDefaultCell().setPadding(PADDING_5F);

        PdfPCell customerNameCell = new PdfPCell(getTextPhrase("Tax Details", catFontBold12));
        customerNameCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        customerNameCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        customerNameCell.setColspan(4);
        customerNameCell.setBorderColorBottom(BaseColor.WHITE);
        customerNameCell.setPadding(SPACING_5F);
        customerNameCell.setBorder(Rectangle.NO_BORDER);
        taxTable.addCell(customerNameCell);
        taxTable.completeRow();

        taxTable.addCell(getTextPhrase(TAX_DESC, catFontBold12));
        taxTable.addCell(getTextPhrase(TAX_PERCENT, catFontBold12));
        taxTable.addCell(getTextPhrase(TAXABLE_AMT, catFontBold12));
        taxTable.addCell(getTextPhrase(TAX_AMOUNT, catFontBold12));
        PdfPCell[] cells = taxTable.getRow(1).getCells();
        for (PdfPCell cell : cells) {
            cell.setBackgroundColor(new BaseColor(ContextCompat.getColor(activityRef.get(), R.color.white)));
            cell.setUseVariableBorders(true);
            cell.setBorderColorLeft(BaseColor.WHITE);
            cell.setBorderColorRight(BaseColor.WHITE);
            cell.setBorderColorTop(BaseColor.WHITE);
            cell.setBorderColorBottom(BaseColor.BLACK);
        }
        int row = 1;
        if (parentList.getCgstperc() != 0) {
            taxTable.addCell(getTextPhrase(TAX_CGST, catFontNormal12));
            taxTable.addCell(getTextPhrase(String.valueOf(parentList.getCgstperc()), catFontNormal12));
            taxTable.addCell(getTextPhrase(String.valueOf(parentList.getOrderValue().doubleValue()), catFontNormal12));
            taxTable.addCell(getTextPhrase(MessageFormat.format("₹{0}", String.format(Locale.ENGLISH, "%.2f", parentList.getCgstvalue())), catFontNormal12));
            row = row + 1;
            setBorderLessCell(taxTable, row);
        }
        if (parentList.getSgstPerc() != 0) {
            row = row + 1;
            taxTable.addCell(getTextPhrase(TAX_SGST, catFontNormal12));
            taxTable.addCell(getTextPhrase(String.valueOf(parentList.getSgstPerc()), catFontNormal12));
            taxTable.addCell(getTextPhrase(String.valueOf(parentList.getOrderValue().doubleValue()), catFontNormal12));
            taxTable.addCell(getTextPhrase(MessageFormat.format("₹{0}", String.format(Locale.ENGLISH, "%.2f", parentList.getSgstValue())), catFontNormal12));
            setBorderLessCell(taxTable, row);
        }
        if (parentList.getIgstPerc() != 0) {
            row = row + 1;
            taxTable.addCell(getTextPhrase(TAX_IGST, catFontNormal12));
            taxTable.addCell(getTextPhrase(String.valueOf(parentList.getIgstPerc()), catFontNormal12));
            taxTable.addCell(getTextPhrase(String.valueOf(parentList.getOrderValue().doubleValue()), catFontNormal12));
            taxTable.addCell(getTextPhrase(MessageFormat.format("₹{0}", String.format(Locale.ENGLISH, "%.2f", parentList.getIgstvalue())), catFontNormal12));
            setBorderLessCell(taxTable, row);
        }
        if (parentList.getUtgstPerc() != 0) {
            row = row + 1;
            taxTable.addCell(getTextPhrase(TAX_UTGST, catFontNormal12));
            taxTable.addCell(getTextPhrase(String.valueOf(parentList.getUtgstPerc()), catFontNormal12));
            taxTable.addCell(getTextPhrase(String.valueOf(parentList.getOrderValue().doubleValue()), catFontNormal12));
            taxTable.addCell(getTextPhrase(MessageFormat.format("₹{0}", String.format(Locale.ENGLISH, "%.2f", parentList.getUtgstValue())), catFontNormal12));
            setBorderLessCell(taxTable, row);
        }
        return taxTable;
    }

    private void setBorderLessCell(PdfPTable taxTable, int row) {
        for (PdfPCell cell : taxTable.getRow(row).getCells()) {
            cell.setUseVariableBorders(true);
            cell.setBackgroundColor(new BaseColor(ContextCompat.getColor(activityRef.get(), R.color.white)));
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setBorderColor(BaseColor.WHITE);
        }
    }

    public File createSalesmanSummaryPdf(String screenType, String salesmanCode,
                                         Map<String, List<OrderBookingVO>> childItems,
                                         List<String> parentCustomerArray,
                                         List<OrderBookingVO> parentList, boolean isPTRWithTax, boolean isPrimaryDicountEnanble) {
        File filePath = null;
        try {
            if (screenType.equalsIgnoreCase(INVOICE_SUMMARY))
                filePath = getFile(salesmanCode + "_InvoiceSummary" + ".pdf");
            else
                filePath = getFile(salesmanCode + "_OrderSummary" + ".pdf");
            List<Element> objects = new ArrayList<>();

            PdfPTable headingTable = getHeadingTable(screenType);

            for (int j = 0; j < parentCustomerArray.size(); j++) {
                if (!parentList.isEmpty()) {
                    PdfPTable headerTable = getHeaderTable(parentList.get(j), false);
                    objects.add(headerTable);
                }
                List<OrderBookingVO> childObject = childItems.get(parentCustomerArray.get(j));
                PdfPTable dataTableNew = getCustomerDataTableNew(childObject, parentList.get(j), screenType, isPTRWithTax,isPrimaryDicountEnanble);
                objects.add(dataTableNew);
                if (screenType.equalsIgnoreCase(INVOICE_SUMMARY)) {
                    PdfPTable totalSummary = getInvoiceTotalSummary(parentList.get(j));
                    objects.add(totalSummary);
                }
            }

            Document document = createDocument();
            PdfWriter.getInstance(document, new FileOutputStream(filePath.getPath()));
            document.open();
            document.add(headingTable);

            for (Element o : objects) {
                document.add(o);
            }
            document.close();


        } catch (DocumentException | IOException e) {
            Log.e(TAG, "createCustomerSummaryPdf: " + e.getMessage(), e);
        }
        return filePath;
    }

    @NonNull
    private PdfPTable getCustomerDataTable(Font catFont, List<OrderBookingVO> childObject, OrderBookingVO orderBookingVO) {
        PdfPTable table = new PdfPTable(new float[]{8, 3, 3, 4, 4, 4, 4});
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.getDefaultCell().setPadding(PADDING_5F);

        table.setSpacingAfter(SPACING_30F);

        int percentWidth = (int) ((PageSize.A4.getWidth() * 5) / 100);
        table.setTotalWidth(PageSize.A4.getWidth() - percentWidth);
        table.setLockedWidth(true);

        table.addCell(PRODUCT_NAME);
        table.addCell(QUANTITY);
        table.addCell(UOM);
        table.addCell(GROSS_AMOUNT);
        table.addCell(DISCOUNT);
        table.addCell(TAX);
        table.addCell(NET_AMOUNT);
        PdfPCell[] cells = table.getRow(0).getCells();
        for (PdfPCell cell : cells) {
            cell.setBackgroundColor(new BaseColor(ContextCompat.getColor(activityRef.get(), R.color.color_list_item_txt)));
        }
        for (int i = 0; i < childObject.size(); i++) {
            table.addCell(getTextPhrase(childObject.get(i).getProdName(), catFontNormal12));
            table.addCell(getTextPhrase(OrderSupportUtil.getInstance().getStringQty(childObject.get(i).getQuantity()), catFontNormal12));
            table.addCell(getTextPhrase(String.valueOf(childObject.get(i).getUomId()), catFontNormal12));
            table.addCell(getTextPhrase(MessageFormat.format("₹{0}", String.format(Locale.ENGLISH, "%.2f", childObject.get(i).getOrderValue())), catFontNormal12));
            table.addCell(getTextPhrase(MessageFormat.format("₹{0}", String.format(Locale.ENGLISH, "%.2f", childObject.get(i).getSchemeAmount())), catFontNormal12));
            table.addCell(getTextPhrase(MessageFormat.format("₹{0}", String.format(Locale.ENGLISH, "%.2f", childObject.get(i).getTax())), catFontNormal12));
            table.addCell(getTextPhrase(MessageFormat.format("₹{0}", String.format(Locale.ENGLISH, "%.2f", childObject.get(i).getTotalAmount())), catFontNormal12));
        }

        PdfPCell cellTotal = new PdfPCell(getTextPhrase("Total", catFontNormal12));
        cellTotal.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellTotal.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cellTotal.setColspan(3);
        table.addCell(cellTotal);
        table.addCell(getTextPhrase("₹" + AppUtils.getOurInstance().decimalFormatWithTwoDigit(orderBookingVO.getOrderValue().doubleValue()), catFontNormal12));
        table.addCell(getTextPhrase(MessageFormat.format("₹{0}", String.format(Locale.ENGLISH, "%.2f", orderBookingVO.getSchemeAmount())), catFontNormal12));
        table.addCell(getTextPhrase(MessageFormat.format("₹{0}", String.format(Locale.ENGLISH, "%.2f", orderBookingVO.getTax())), catFontNormal12));
        table.addCell(getTextPhrase(MessageFormat.format("₹{0}", String.format(Locale.ENGLISH, "%.2f", orderBookingVO.getTotalAmount())), catFontNormal12));
        table.completeRow();
        return table;
    }

    @NonNull
    private File getFile(String fileName) throws IOException {
        Date now = new Date();
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);
        File rootFolder = new File(FileAccessUtil.getInstance().getExternalStorageDirectory().toString()
                + File.separator +
                SFASharedPref.getOurInstance().readString(SFASharedPref.PREF_CMP_CODE));
        final File filePath = new File(rootFolder, fileName.replaceAll("[^a-zA-Z0-9._&]",""));
        if (!rootFolder.exists() && rootFolder.mkdir()) {
            //New FolderCreated
        }
        if (!filePath.exists()) {
            if (filePath.createNewFile()) {
                //New File Created
            }
        } else {
            FileAccessUtil.deleteFileIfExist(filePath);
            if (filePath.createNewFile()) {
                //New File Created
            }
        }
        return filePath;
    }

}
