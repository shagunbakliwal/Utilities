import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class S3ToMemory {
    public static void main(String[] args) throws IOException {
        String bucketName = "your-bucket-name";
        String objectKey = "your-file.csv";

        AmazonS3 s3Client = AmazonS3ClientBuilder.standard().build();
        S3Object s3Object = s3Client.getObject(bucketName, objectKey);
        S3ObjectInputStream inputStream = s3Object.getObjectContent();

        // Reading input stream into memory (ByteArrayOutputStream)
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            byteArrayOutputStream.write(buffer, 0, bytesRead);
        }

        // Get byte array from ByteArrayOutputStream
        byte[] dataInMemory = byteArrayOutputStream.toByteArray();
        
        // Optionally, convert the byte array to a String if needed
        String content = new String(dataInMemory);
        System.out.println("Content in memory:\n" + content);

        // Close the stream
        inputStream.close();
    }
}
