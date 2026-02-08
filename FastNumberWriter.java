import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

public class FastNumberWriter {

    public static void main(String[] args) throws Exception {

        long start = 1_400_000_000L;
        long end = start + 99_999_999L; // 10 crore numbers

        long startTime = System.currentTimeMillis();

        try (FileOutputStream fos = new FileOutputStream("numbers.txt");
             FileChannel channel = fos.getChannel()) {

            // 4 MB buffer
            ByteBuffer buffer = ByteBuffer.allocateDirect(4 * 1024 * 1024);

            for (long i = start; i <= end; i++) {

                String line = "shagunbakliwal@gmail.com"+i + "\n";
                byte[] bytes = line.getBytes(StandardCharsets.US_ASCII);

                // If buffer full, flush
                if (buffer.remaining() < bytes.length) {
                    buffer.flip();
                    channel.write(buffer);
                    buffer.clear();
                }

                buffer.put(bytes);
            }

            // final flush
            buffer.flip();
            channel.write(buffer);
        }

        long endTime = System.currentTimeMillis();
        System.out.println("Time taken: " + (endTime - startTime) + " ms");
    }
}
