package com.botree.retailerssfa.print;

import android.graphics.Bitmap;
import android.util.Log;

import com.botree.retailerssfa.db.FileAccessUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;


public class Printing {

    private static final String TAG = Printing.class.getSimpleName();

    public static void printPicture(Bitmap mBitmap, int nWidth, int nMode, OutputStream outputStream) {
        try {
            int idx;
            int currcount;
            byte[] reset = new byte[]{(byte) 27, (byte) 64};

            idx = 0;

            int width = (nWidth + 7) / 8 * 8;
            int height = mBitmap.getHeight() * width / mBitmap.getWidth();
            height = (height + 7) / 8 * 8;
            Bitmap rszBitmap = ImageProcessing.resizeImage(mBitmap, width, height);
            Bitmap grayBitmap = ImageProcessing.toGrayscale(rszBitmap);
            byte[] data = eachLinePixToCmd(1, grayBitmap, width, nMode);
            int nBytesPerLine = width / 8 + 8;
            byte nLinesPerTest = 30;

            while (data != null && idx < data.length) {
                if ((data.length - idx) > (nBytesPerLine * nLinesPerTest))
                    currcount = nBytesPerLine * nLinesPerTest;
                else
                    currcount = data.length - idx;

                outputStream.write(data, idx, currcount);
                outputStream.write(reset, 0, reset.length);

                idx += currcount;
            }
        } catch (Exception e) {
            Log.e(TAG, "printPicture: " + e.getMessage(), e);
        }
    }

    private static byte[] eachLinePixToCmd(int callFrom, Bitmap mBitmap, int nWidth, int nMode) {
        try {
            byte[] src;
            byte[] data;
            int[] pixels;

            if (callFrom == 1) {
                // Start Converting bitmapToBWPix
                pixels = new int[mBitmap.getWidth() * mBitmap.getHeight()];
                data = new byte[mBitmap.getWidth() * mBitmap.getHeight()];
                mBitmap.getPixels(pixels, 0, mBitmap.getWidth(), 0, 0, mBitmap.getWidth(), mBitmap.getHeight());
                ImageProcessing.formatKDither16X16(pixels, mBitmap.getWidth(), mBitmap.getHeight(), data);
                // End
            } else {
                // Start Converting to Threshold Format
                pixels = new int[mBitmap.getWidth() * mBitmap.getHeight()];
                data = new byte[mBitmap.getWidth() * mBitmap.getHeight()];
                mBitmap.getPixels(pixels, 0, mBitmap.getWidth(), 0, 0, mBitmap.getWidth(), mBitmap.getHeight());
                ImageProcessing.formatKThreshold(pixels, mBitmap.getWidth(), mBitmap.getHeight(), data);
            }

            src = data;
            int nHeight = src.length / nWidth;
            int nBytesPerLine = nWidth / 8;
            int k = 0;
            int[] p0 = new int[]{0, 128};
            int[] p1 = new int[]{0, 64};
            int[] p2 = new int[]{0, 32};
            int[] p3 = new int[]{0, 16};
            int[] p4 = new int[]{0, 8};
            int[] p5 = new int[]{0, 4};
            int[] p6 = new int[]{0, 2};
            byte[] sendData = new byte[nHeight * (8 + nBytesPerLine)];

            for (int i = 0; i < nHeight; ++i) {
                int var10 = i * (8 + nBytesPerLine);
                sendData[var10] = 29;
                sendData[var10 + 1] = 118;
                sendData[var10 + 2] = 48;
                sendData[var10 + 3] = (byte) (nMode & 1);
                sendData[var10 + 4] = (byte) (nBytesPerLine % 256);
                sendData[var10 + 5] = (byte) (nBytesPerLine / 256);
                sendData[var10 + 6] = 1;
                sendData[var10 + 7] = 0;

                for (int j = 0; j < nBytesPerLine; ++j) {
                    sendData[var10 + 8 + j] = (byte) (p0[src[k]] + p1[src[k + 1]] + p2[src[k + 2]] + p3[src[k + 3]] + p4[src[k + 4]] + p5[src[k + 5]] + p6[src[k + 6]] + src[k + 7]);
                    k += 8;
                }
            }

            return sendData;
        } catch (Exception e) {
            return new byte[0];
        }
    }

    public static void printQRcode(String strCodedata, int nWidthX, int nErrorCorrectionLevel, OutputStream outputStream) {
        try {
            byte[] gsWN = new byte[]{(byte) 29, (byte) 119, (byte) 3};
            byte[] gsKMVRNlNh = new byte[]{(byte) 29, (byte) 107, (byte) 97, (byte) 0, (byte) 2, (byte) 0, (byte) 0};


            if (!(nWidthX < 2 || nWidthX > 6 || nErrorCorrectionLevel < 1 || nErrorCorrectionLevel > 4)) {

                byte[] bCodeData1;
                bCodeData1 = strCodedata.getBytes("GBK");

                gsWN[2] = (byte) nWidthX;
                gsKMVRNlNh[4] = (byte) nErrorCorrectionLevel;
                gsKMVRNlNh[5] = (byte) (bCodeData1.length & 255);
                gsKMVRNlNh[6] = (byte) ((bCodeData1.length & '\uff00') >> 8);
                byte[] data = byteArraysToBytes(new byte[][]{gsWN, gsKMVRNlNh, bCodeData1});
                outputStream.write(data, 0, data.length);
            }
        } catch (Exception e) {

            Log.e(TAG, "printQRcode: " + e.getMessage(), e);
        }
    }

    static byte[] byteArraysToBytes(byte[][] data) {
        try {
            int length = 0;

            for (byte[] aData1 : data) length += aData1.length;

            byte[] bytesData = new byte[length];
            int k = 0;

            for (byte[] aData : data) {
                for (byte anAData : aData) bytesData[k++] = anAData;
            }

            return bytesData;
        } catch (Exception e) {
            return new byte[0];
        }
    }

    public static void printWholeTextLines(byte[] data, OutputStream outputStream) {
        try {
            byte[] reset = new byte[]{(byte) 27, (byte) 64};
            outputStream.write(data, 0, data.length);
            outputStream.write(reset, 0, reset.length);

        } catch (Exception e) {
            Log.e(TAG, "printWholeTextLines: " + e.getMessage(), e);
        }
    }

    public static byte[] printText(String srcData, int alignment, int nWidthTimes, int nHeightTimes, int nFontType, int nFontStyle,
                                   int lineHeight, int rightSpace, OutputStream outputStream) {
        try {
            byte[] orgx = new byte[]{(byte) 27, (byte) 36, (byte) 0, (byte) 0};
            byte[] align = new byte[]{(byte) 27, (byte) 97, (byte) 0};
            byte[] widthHeight = new byte[]{(byte) 29, (byte) 33, (byte) 0};
            byte[] fontSize = new byte[]{(byte) 27, (byte) 77, (byte) 0};
            byte[] fontStyleShift3 = new byte[]{(byte) 27, (byte) 69, (byte) 0};
            byte[] fontStyleEShift7 = new byte[]{(byte) 27, (byte) 45, (byte) 0};
            byte[] fontStyleFShift7 = new byte[]{(byte) 28, (byte) 45, (byte) 0};
            byte[] fontStyleShift9 = new byte[]{(byte) 27, (byte) 123, (byte) 0};
            byte[] fontStyleShift10 = new byte[]{(byte) 29, (byte) 66, (byte) 0};
            byte[] fontStyleShift12 = new byte[]{(byte) 27, (byte) 86, (byte) 0};
            byte[] lineHgt = new byte[]{(byte) 27, (byte) 51, (byte) 0};
            byte[] rgtSpace = new byte[]{(byte) 27, (byte) 32, (byte) 0};

            int nOrgx = PrintGlobals.PRT_ORG;
            if (nWidthTimes > 7 || nWidthTimes < 0 || nHeightTimes > 7 || nHeightTimes < 0 || nFontType < 0 || nFontType > 4 || srcData.length() == 0)
                return new byte[0];

            orgx[0] = (byte) (nOrgx % 0x100);
            orgx[1] = (byte) (nOrgx / 0x100);

            align[2] = (byte) alignment;
            widthHeight[2] = (byte) ((byte) nWidthTimes * (byte) 0x10 + (byte) nHeightTimes);
            if ((nFontType == 0) || (nFontType == 1))
                fontSize[2] = (byte) nFontType;
            else
                fontSize = new byte[0];

            fontStyleShift3[2] = (byte) ((nFontStyle >> 3) & 0x01);

            fontStyleEShift7[2] = (byte) ((nFontStyle >> 7) & 0x03);
            fontStyleFShift7[2] = (byte) ((nFontStyle >> 7) & 0x03);

            fontStyleShift9[2] = (byte) ((nFontStyle >> 9) & 0x01);

            fontStyleShift10[2] = (byte) ((nFontStyle >> 10) & 0x01);

            fontStyleShift12[2] = (byte) ((nFontStyle >> 12) & 0x01);

            lineHgt[2] = (byte) lineHeight;
            rgtSpace[2] = (byte) rightSpace;

            byte[] pbString;
            pbString = srcData.getBytes("GBK");

            return byteArraysToBytes(new byte[][]{orgx, align, widthHeight, fontSize, fontStyleShift3, fontStyleEShift7,
                    fontStyleFShift7, fontStyleShift9, fontStyleShift10, fontStyleShift12, lineHgt, rgtSpace, pbString});

        } catch (Exception e) {
            return new byte[0];
        }
    }

    public static byte[] printPaperFeed(OutputStream outputStream) {
        try {
            byte[] lf = new byte[]{(byte) 10};
            byte[] cr = new byte[]{(byte) 13};

            return byteArraysToBytes(new byte[][]{cr, lf});
        } catch (Exception e) {
            return new byte[0];
        }
    }

    public void printBarcode(String strCodedata, int nOrgx, int nType, int nWidthX, int nHeight,
                             int nHriFontType, int nHriFontPosition, OutputStream outputStream) {
        try {
            if (!(nOrgx < 0 || nOrgx > '\uffff' || nType < 65 || nType > 73 || nWidthX < 2 || nWidthX > 6 || nHeight < 1 || nHeight > 255)) {

                byte[] escdollorsnlnh = new byte[]{(byte) 27, (byte) 36, (byte) 0, (byte) 0};
                byte[] gsWN = new byte[]{(byte) 29, (byte) 119, (byte) 3};
                byte[] gsHN = new byte[]{(byte) 29, (byte) 104, (byte) -94};
                byte[] gsFN = new byte[]{(byte) 29, (byte) 102, (byte) 0};
                byte[] gsHn1 = new byte[]{(byte) 29, (byte) 72, (byte) 0};
                byte[] gsKMN = new byte[]{(byte) 29, (byte) 107, (byte) 65, (byte) 12};

                byte[] bCodeData1;
                bCodeData1 = strCodedata.getBytes("GBK");

                escdollorsnlnh[2] = (byte) (nOrgx % 256);
                escdollorsnlnh[3] = (byte) (nOrgx / 256);
                gsWN[2] = (byte) nWidthX;
                gsHN[2] = (byte) nHeight;
                gsFN[2] = (byte) (nHriFontType & 1);
                gsHn1[2] = (byte) (nHriFontPosition & 3);
                gsKMN[2] = (byte) nType;
                gsKMN[3] = (byte) bCodeData1.length;
                byte[] data = byteArraysToBytes(new byte[][]{escdollorsnlnh, gsWN, gsHN, gsFN, gsHn1, gsKMN, bCodeData1});
                outputStream.write(data, 0, data.length);

            }
        } catch (Exception e) {
            Log.e(TAG, "printBarcode: " + e.getMessage(), e);
        }
    }

    private void saveMyBitmap(Bitmap mBitmap, String name) {
        try {
            File f = new File(FileAccessUtil.getInstance().getExternalStorageDirectory().getPath(), name);
            boolean isNewFile = f.createNewFile();
            if (isNewFile)
                Log.i(TAG, "saveMyBitmap: New File Created");

            FileOutputStream fOut;

            fOut = new FileOutputStream(f);
            mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            fOut.flush();
            fOut.close();
        } catch (Exception e) {
            Log.e(TAG, "saveMyBitmap: " + e.getMessage(), e);
        }
    }

    public void printBinaryPic(Bitmap mBitmap, int nWidth, int nMode, OutputStream outputStream) {
        try {
            int idx;
            int currcount;
            byte[] reset = new byte[]{(byte) 27, (byte) 64};

            idx = 0;

            int width = (nWidth + 7) / 8 * 8;
            int height = mBitmap.getHeight() * width / mBitmap.getWidth();
            height = (height + 7) / 8 * 8;
            Bitmap rszBitmap = ImageProcessing.resizeImage(mBitmap, width, height);
            Bitmap grayBitmap = ImageProcessing.toGrayscale(rszBitmap);
            saveMyBitmap(grayBitmap, "gray.png");
            byte[] data = eachLinePixToCmd(2, grayBitmap, width, nMode);
            int nBytesPerLine = width / 8 + 8;
            byte nLinesPerTest = 1;

            while (data != null && idx < data.length) {
                if ((data.length - idx) > (nBytesPerLine * nLinesPerTest))
                    currcount = nBytesPerLine * nLinesPerTest;
                else
                    currcount = data.length - idx;

                outputStream.write(data, idx, currcount);
                outputStream.write(reset, 0, reset.length);

                idx += currcount;
            }
        } catch (Exception e) {
            Log.e(TAG, "printBinaryPic: " + e.getMessage(), e);
        }
    }


}
