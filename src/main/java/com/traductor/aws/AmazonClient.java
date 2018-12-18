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
import com.amazonaws.services.translate.AmazonTranslate;
import com.amazonaws.services.translate.AmazonTranslateClientBuilder;
import com.amazonaws.services.translate.model.TranslateTextResult;
import com.traductor.domain.Language;
import com.traductor.domain.Translate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class AmazonClient {

    private AmazonS3 s3client;
    private AmazonTranslate translateClient;
    private AmazonComprehend comprehendClient;

    @Value("${amazonProperties.endpointUrl}")
    private String endpointUrl;
    @Value("${amazonProperties.bucketName}")
    private String bucketName;
    @Value("${amazonProperties.accessKey}")
    private String accessKey;
    @Value("${amazonProperties.secretKey}")
    private String secretKey;

    @PostConstruct
    private void initializeAmazon() {
        AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);

        this.translateClient = AmazonTranslateClientBuilder
                .standard()
                .withRegion("us-east-1")
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .build();
        this.s3client = AmazonS3ClientBuilder
                .standard()
                .withRegion("us-east-1")
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .build();
        this.comprehendClient = AmazonComprehendClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion("us-east-1")
                .build();
    }

    private Language dominantLanguage(String text,Language source) {
        DetectDominantLanguageRequest request = new DetectDominantLanguageRequest().withText(text);
        DetectDominantLanguageResult result = comprehendClient.detectDominantLanguage(request);
        List<DominantLanguage> languages;

        languages = result.getLanguages();

        source.setLangCode(languages.get(0).getLanguageCode());

        return source;
    }

    public Translate translate(Translate trans) {
        TranslateTextResult result;
        if (trans.getSourceLanguage().getLangCode().equals("auto")) {
            trans.setSourceLanguage(dominantLanguage(trans.getText(),trans.getSourceLanguage()));
        }
        result = translateClient.translateText(trans.getRequest());
        trans.setTranslatedText(result.getTranslatedText());

        return trans;
    }
}

