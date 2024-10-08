package co.allconnected.fussiontech.organizationsservice.services;

import com.google.cloud.storage.Bucket;
import com.google.firebase.cloud.StorageClient;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Service
public class FirebaseService {

    public String uploadImg(String imageName, String extension, MultipartFile imageFile) throws IOException {
        InputStream inputStream = imageFile.getInputStream();
        Bucket bucket = StorageClient.getInstance().bucket();
        bucket.create("user_photos/"+imageName, inputStream, "image/"+extension);
        return bucket.get("user_photos/"+imageName)
                .signUrl(360, java.util.concurrent.TimeUnit.DAYS).toString();
    }

    public void deleteImg(String imageName) {
        Bucket bucket = StorageClient.getInstance().bucket();
        bucket.get("user_photos/"+imageName).delete();
    }
}
