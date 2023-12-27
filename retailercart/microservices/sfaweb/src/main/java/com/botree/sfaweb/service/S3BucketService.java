package com.botree.sfaweb.service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.util.IOUtils;
import com.botree.common.constants.StringConstants;
import com.botree.common.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Base64;

/**
 * Class contains the S3 related transactions.
 * @author vinodkumara
 */
@Service
public class S3BucketService {

    /** LOG. */
    private static final Logger LOG = LoggerFactory.getLogger(S3BucketService.class);

    /** imageLocalFilePath. */
    @Value("${image.path}")
    private String imageLocalFilePath;

    /** s3BucketEnable. */
    @Value("${aws.s3bucket.enable}")
    private Boolean s3BucketEnable;

    /** accessKey. */
    @Value("${aws.accesskey}")
    private String accessKey;

    /** secretKey. */
    @Value("${aws.secretkey}")
    private String secretKey;

    /** awsregion. */
    @Value("${aws.region}")
    private String awsregion;

    /** bucketName. */
    @Value("${aws.bucketname}")
    private String bucketName;

    /** folderName. */
    @Value("${aws.foldername}")
    private String folderName;

    /**
     * Method to write the file.
     * @param filePath    filePath
     * @param fileName    fileName
     * @param content     content
     * @param processName processName
     * @return String file path
     */
    public final String writeFile(final String filePath, final String fileName, final String content,
                                  final String processName) {
        try {
            var path = filePath + File.separator + processName + File.separator;
            var file = new File(path);
            if (!file.exists()) {
                file.mkdir();
            }

            var destFile = new File(path + fileName);

            Files.write(destFile.toPath(), Base64.getMimeDecoder().decode(content));
            if (Boolean.TRUE.equals(s3BucketEnable)) {
                var destFileName = processName + StringConstants.FORWARD_SLASH + fileName;
                writeFileToS3(destFile.getAbsolutePath(), destFileName);
                Boolean fileStatus = Files.deleteIfExists(destFile.toPath());
                LOG.info("File Deleted :: {} ", fileStatus);
            }
            return fileName;
        } catch (IOException e) {
            LOG.error("write file exception :: {} {} ", processName, e.getMessage());
        }
        return null;
    }

    /**
     * Method to write file in S3 storage.
     * @param filePath filePath
     * @param fileName fileName
     */
    public final void writeFileToS3(final String filePath, final String fileName) {
        try {
            LOG.info("write file to s3 start ::: {} ", fileName);
            AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
            var s3client = AmazonS3ClientBuilder.standard()
                    .withCredentials(new AWSStaticCredentialsProvider(credentials)).withRegion(awsregion).build();
            if (filePath != null && !filePath.isEmpty()) {
                s3client.putObject(bucketName, folderName + StringConstants.FORWARD_SLASH + fileName,
                        new File(filePath));
            }
            LOG.info("write file to s3 end ::: {} ", fileName);
        } catch (Exception e) {
            LOG.error("write file to s3  :: {} ", fileName);
            throw new ServiceException(e);
        }
    }

    /**
     * Method to read file in S3 storage.
     * @param processName processName
     * @param fileName    fileName
     * @return uri
     */
    public final URI readImageFromS3(final String processName, final String fileName) {
        LOG.info("read image from s3 start ::: {} ", fileName);
        var path = imageLocalFilePath + File.separator + StringConstants.DOWNLOADFILE + File.separator;
        var tempFile = new File(path, System.nanoTime() + "_" + fileName.replace("/", "_"));
        try {
            AWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
            var s3client = AmazonS3ClientBuilder.standard()
                    .withCredentials(new AWSStaticCredentialsProvider(awsCredentials)).withRegion(awsregion).build();
            var s3Object = s3client.getObject(new GetObjectRequest(bucketName,
                    folderName + StringConstants.FORWARD_SLASH + processName + StringConstants.FORWARD_SLASH
                            + fileName));
            Files.copy(s3Object.getObjectContent(), tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            LOG.error("read image from s3 exception ::: {} ", e.getMessage());
            return null;
        }
        LOG.info("read image from s3 end ::: {} ", fileName);
        return tempFile.toURI();
    }

    /**
     * Method to download video from S3.
     * @param processName processName
     * @param fileName    fileName
     * @return byte[]
     */
    @Async
    public byte[] downloadFileFromS3(final String processName, final String fileName) {
        LOG.info("read file from s3 start ::: {} ", fileName);
        var arr = fileName.split(StringConstants.CONST_TILDE);
        var keyName = arr[0] + StringConstants.CONST_DOT + arr[1];
        byte[] content = null;
        AWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
        var amazonS3 = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials)).withRegion(awsregion).build();
        final var s3Object = amazonS3.getObject(new GetObjectRequest(bucketName,
                folderName + StringConstants.FORWARD_SLASH + processName + StringConstants.FORWARD_SLASH
                        + keyName));
        final var stream = s3Object.getObjectContent();
        try {
            content = IOUtils.toByteArray(stream);
            LOG.info("read file from s3 end ::: {} ", keyName);
            s3Object.close();
        } catch (final IOException ex) {
            LOG.error("read file from s3 exception ::: {} ", ex.getMessage());
        }
        return content;
    }
}
