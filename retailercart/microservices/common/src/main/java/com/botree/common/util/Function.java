package com.botree.common.util;

import com.botree.common.constants.StringConstants;
import com.botree.common.exception.ServiceException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.poi.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.xml.bind.JAXBContext;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.net.URI;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Base64;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * This class is used to convert json to POJO.
 * @author vinodkumar.a
 */
public final class Function {

    /** LOG. */
    private static final Logger LOG = LoggerFactory.getLogger(Function.class);

    /**
     * Default constructor.
     */
    private Function() {

    }

    /**
     * Method to compress data.
     * @param data           data
     * @param enableCompress enableCompress
     * @return compressed data
     */
    public static Object compress(final Object data, final boolean enableCompress) {
        if (enableCompress) {
            var mapper = new ObjectMapper();
            try (var out = new ByteArrayOutputStream();
                 var gzip = new GZIPOutputStream(out)) {
                var osw = new OutputStreamWriter(gzip, StandardCharsets.UTF_8);
                osw.write(mapper.writeValueAsString(data));
                osw.close();
                return Base64.getEncoder().encodeToString(out.toByteArray());
            } catch (IOException e) {
                throw new ServiceException("compress exception ::", e);
            }
        } else {
            return data;
        }
    }

    /**
     * Method to decompress data.
     * @param input  input
     * @param clazz  clazz
     * @param isList isList
     * @param <T>    object
     * @return decompressed data
     */
    @SuppressWarnings("unchecked")
    public static <T> T decompress(final String input, final Class<?> clazz, final Boolean isList) {
        var mapper = new ObjectMapper();
        var builder = new StringBuilder();
        try (var is = new ByteArrayInputStream(Base64.getDecoder().decode(input));
             var gis = new GZIPInputStream(is)) {
            var data = new byte[StringConstants.BUFFER_SIZE];
            int bytesRead;
            while ((bytesRead = gis.read(data)) != -1) {
                builder.append(new String(data, 0, bytesRead));
            }
            if (Boolean.TRUE.equals(isList)) {
                JavaType collectionType = mapper.getTypeFactory().constructCollectionType(List.class, clazz);
                return mapper.readValue(builder.toString(), collectionType);
            } else {
                return (T) mapper.readValue(builder.toString(), clazz);
            }
        } catch (Exception e) {
            throw new ServiceException("decompress exception :: ", e);
        }
    }

    /**
     * Method to write the image.
     * @param image       image
     * @param imagePath   imagePath
     * @param processName processName
     * @param fileName    fileName
     * @return String imagepath
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static String writeImage(final String image, final String imagePath, final String processName,
                                    final String fileName) {
        try {
            var path = imagePath + File.separatorChar + processName + File.separatorChar;
            var destFileName = fileName + StringConstants.EXT_JPEG;
            var file = new File(path);
            if (!file.exists()) {
                file.mkdir();
            }
            var imgFile = new File(path + destFileName);
            Files.write(imgFile.toPath(), Base64.getMimeDecoder().decode(image));
            return destFileName;
        } catch (IOException e) {
            throw new ServiceException(processName + " save image conversion exception :: ", e);
        }
    }

    /**
     * This method is used to convert XML to POJO.
     * @param xml  XML string
     * @param type Object type
     * @param <T>  Class Type
     * @return POJO
     */
    public static <T> Object fromXML(final String xml, final Class<T> type) {
        try {
            var reader = new StringReader(xml);
            var jaxbContext = JAXBContext.newInstance(type);
            var unmarshaller = jaxbContext.createUnmarshaller();
            return unmarshaller.unmarshal(reader);
        } catch (Exception e) {
            throw new ServiceException(e);
        }

    }

    /**
     * Method to convert file to byte array.
     * @param uri uri
     * @return byte[]
     * @throws IOException IOException
     */
    public static byte[] convertFileToByteArray(final URI uri) throws IOException {
        InputStream in = new FileInputStream(uri.getPath());
        return IOUtils.toByteArray(in);
    }

    /**
     * Method to convert the mime type for a file.
     * @param uri uri
     * @return mime type
     */
    public static String convertMimeType(final URI uri) {
        var file = new File(uri.getPath());
        var fileNameMap = URLConnection.getFileNameMap();
        return fileNameMap.getContentTypeFor(file.getName());
    }

    /**
     * Method to covert image to base64 string.
     * @param imagePath imagePath
     * @return base64image
     */
    public static String encoder(final String imagePath) {
        var base64Image = "";
        var file = new File(imagePath);
        try (var imageInFile = new FileInputStream(file)) {
            var imageData = new byte[(int) file.length()];
            var count = imageInFile.read(imageData);
            LOG.info("byte count {}", count);
            base64Image = Base64.getEncoder().encodeToString(imageData);
        } catch (IOException e) {
            LOG.error("image not found", e);
        }
        return base64Image;
    }

    /**
     * Method to check integer value.
     * @param val val
     * @return integer value
     */
    public static Integer checkInteger(final String val) {
        if (val != null && !val.equals("")) {
            return Integer.valueOf(val);
        }
        return 0;
    }

    /**
     * Method to check double value.
     * @param val val
     * @return double value
     */
    public static Double checkDouble(final String val) {
        if (val != null && !val.equals("")) {
            return Double.valueOf(val);
        }
        return 0.0;
    }

    /**
     * Method to trim string value.
     * @param val val
     * @return double value
     */
    public static String trimString(final String val) {
        if (val != null && !val.equals("")) {
            return val.trim();
        }
        return val;
    }

    /**
     * Method to get the alphabetic column of excel.
     * @param length length
     * @return alphabet string
     */
    public static String getAlphabet(final int length) {
        var result = new String[length];
        var colName = "";
        for (var i = 0; i < length; i++) {
            var c = (char) ('A' + (i % StringConstants.CONST_INT_26));
            colName = c + "";
            if (i > StringConstants.CONST_INT_25) {
                colName = result[(i / StringConstants.CONST_INT_26) - 1] + "" + c;
            }
            result[i] = colName;
        }
        return result[result.length - 1];
    }

    /**
     * This method is get email id.
     * @param emails String
     * @return to
     */
    public static InternetAddress[] getInternetAddresses(final List<String> emails) {
        var to = new InternetAddress[emails.size()];
        try {
            for (var i = 0; i < emails.size(); i++) {
                to[i] = new InternetAddress(emails.get(i).trim());
            }
        } catch (AddressException e) {
            LOG.error("invalid address:: {} {}", emails, e.getMessage());
        }
        return to;
    }
}
