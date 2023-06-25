package com.example.demo.util;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.CloudBlockBlob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.util.UUID;

@Component
public class ImageToAzureUtil {

    @Autowired
    private CloudBlobClient cloudBlobClient;

    @Value("${azure.storage.container-name}")
    private String containerName;

    @Value("${azure.storage.connection-string}")
    private String connectionString;

    // upload image len azure
    public String uploadImage(MultipartFile file) throws IOException, URISyntaxException, StorageException {
        String fileName = file.getOriginalFilename();
        CloudBlockBlob blob = cloudBlobClient.getContainerReference(containerName).getBlockBlobReference(fileName);
        blob.upload(file.getInputStream(), file.getSize());
        return blob.getUri().toString();
    }

    public String uploadImageToAzure(String imageUrl) throws URISyntaxException, StorageException, IOException, InvalidKeyException {
        CloudStorageAccount storageAccount = CloudStorageAccount.parse(connectionString);
        CloudBlobClient blobClient = storageAccount.createCloudBlobClient();
        CloudBlobContainer container = blobClient.getContainerReference(containerName);

        // Lấy tên tệp ảnh từ đường dẫn
        String fileName = getImageFileName(imageUrl);
        CloudBlockBlob blob = container.getBlockBlobReference(fileName);

        URL url = new URL(imageUrl);
        try (InputStream inputStream = url.openStream()) {
            blob.upload(inputStream, -1);
        }

        return blob.getUri().toString();
    }

    private String getImageFileName(String imageUrl) {
        // Lấy phần cuối cùng của đường dẫn làm tên tệp ảnh
        int lastIndexOfSlash = imageUrl.lastIndexOf("/");
        return imageUrl.substring(lastIndexOfSlash + 1);
    }

}
