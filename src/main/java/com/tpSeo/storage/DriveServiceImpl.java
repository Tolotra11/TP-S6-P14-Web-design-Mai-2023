package com.tpSeo.storage;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DriveServiceImpl{

    private static final String APPLICATION_NAME = "Mini project S6";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";
    private static final List<String> SCOPES = Collections.singletonList(DriveScopes.DRIVE_FILE);
    private static final String USER_IDENTIFIER_KEY = "user";

    private Drive driveService;

    public DriveServiceImpl() throws IOException, GeneralSecurityException {
        HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        InputStream inputStream = DriveServiceImpl.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        GoogleCredentials credentials = GoogleCredentials.fromStream(inputStream)
                .createScoped(Collections.singleton(DriveScopes.DRIVE_FILE));
        driveService = new Drive.Builder(new NetHttpTransport(), JacksonFactory.getDefaultInstance(), new HttpCredentialsAdapter(credentials))
                .setApplicationName("My Drive App")
                .build();
    }
    // ...

    public Drive getDriveService() {
        return driveService;
    }
}

