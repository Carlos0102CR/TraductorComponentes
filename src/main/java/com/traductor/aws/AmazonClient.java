package com.traductor.aws;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.comprehend.AmazonComprehend;
import com.amazonaws.services.comprehend.AmazonComprehendClientBuilder;
import com.amazonaws.services.comprehend.model.DetectDominantLanguageRequest;
import com.amazonaws.services.comprehend.model.DetectDominantLanguageResult;
import com.amazonaws.services.comprehend.model.DominantLanguage;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.translate.AmazonTranslate;
import com.amazonaws.services.translate.AmazonTranslateClientBuilder;
import com.amazonaws.services.translate.model.TranslateTextResult;
import com.traductor.domain.Language;
import com.traductor.domain.Translate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.Date;
import java.util.List;

public class AmazonClient {

    private AmazonS3 s3client;
    private AmazonTranslate translateClient;
    private AmazonComprehend comprehendClient;
    private String region = "us-east-2";

    private String endpointUrl = "https://s3.us-east-2.amazonaws.com";
    private String bucketName = "traductor-documents-bucket";
    private String accessKey = "AKIAJQGGAOV5ESWA66PA";
    private String secretKey = "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";


    private Language dominantLanguage(String text, Language source) {
        initComprehendClient();
        DetectDominantLanguageRequest request = new DetectDominantLanguageRequest().withText(text);
        DetectDominantLanguageResult result = comprehendClient.detectDominantLanguage(request);
        List<DominantLanguage> languages;

        languages = result.getLanguages();

        source.setLangCode(languages.get(0).getLanguageCode());

        return source;
    }

    private void initComprehendClient() {
        AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);

        AWSStaticCredentialsProvider cred = new AWSStaticCredentialsProvider(credentials);
        this.comprehendClient = AmazonComprehendClientBuilder.standard()
                .withCredentials(cred)
                .withRegion(region)
                .build();
    }

    public Translate translate(Translate trans) {
        initTranslateClient();
        TranslateTextResult result;
        if (trans.getSourceLanguage().getLangCode().equals("auto")) {
            trans.setSourceLanguage(dominantLanguage(trans.getText(), trans.getSourceLanguage()));
        }
        result = translateClient.translateText(trans.getRequest());
        trans.setTranslatedText(result.getTranslatedText());

        return trans;
    }

    private void initTranslateClient() {
        AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);

        AWSStaticCredentialsProvider cred = new AWSStaticCredentialsProvider(credentials);
        this.translateClient = AmazonTranslateClientBuilder
                .standard()
                .withRegion(region)
                .withCredentials(cred)
                .build();
    }

    private File convertMultiPartToFile(MultipartFile file, Translate trans) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        trans.setText(new String(file.getBytes()));

        trans = translate(trans);
        fos.write(trans.getTranslatedText().getBytes());
        fos.close();
        return convFile;
    }

    private String generateFileName(MultipartFile multiPart) {
        return new Date().getTime() + "-" + multiPart.getOriginalFilename().replace(" ", "_");
    }

    private void uploadFileTos3bucket(String fileName, File file) {
        s3client.putObject(new PutObjectRequest(bucketName, fileName, file)
                .withCannedAcl(CannedAccessControlList.PublicRead));
    }

    public String uploadFile(MultipartFile multipartFile, Translate translate) {
        initS3();
        String fileUrl = "";
        try {
            File file = convertMultiPartToFile(multipartFile, translate);
            String fileName = generateFileName(multipartFile);
            fileUrl = endpointUrl + "/" + bucketName + "/" + fileName;
            uploadFileTos3bucket(fileName, file);
            file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileUrl;
    }

    private void initS3() {
        AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);

        AWSStaticCredentialsProvider cred = new AWSStaticCredentialsProvider(credentials);
        this.s3client = AmazonS3ClientBuilder
                .standard()
                .withRegion(region)
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .build();

    }
}

