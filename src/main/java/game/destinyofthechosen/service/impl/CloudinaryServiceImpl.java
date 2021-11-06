package game.destinyofthechosen.service.impl;

import com.cloudinary.Cloudinary;
import game.destinyofthechosen.model.service.CloudinaryImage;
import game.destinyofthechosen.service.CloudinaryService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

@Service
public class CloudinaryServiceImpl implements CloudinaryService {

    private static final String URL = "url";
    private static final String PUBLIC_ID = "public_id";
    private static final String IMAGE_NOT_FOUND_URL = "https://bitsofco.de/content/images/2018/12/broken-1.png";

    private String folderName;

    private final Cloudinary cloudinary;

    public CloudinaryServiceImpl(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    @Override
    public CloudinaryImage upload(MultipartFile multipartFile) throws IOException {

        File tempFile = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        FileOutputStream fileOutputStream = new FileOutputStream(tempFile);
        fileOutputStream.write(multipartFile.getBytes());
        fileOutputStream.close();

        try {
            @SuppressWarnings("unchecked")
            Map<String, String> uploadResult = cloudinary
                    .uploader()
                    .upload(tempFile, Map.of(
                            "use_filename", true,
                            "unique_filename", false,
                            "folder", "DestinyOfTheChosen" + folderName
                    ));

            String url = uploadResult.getOrDefault(URL, IMAGE_NOT_FOUND_URL);
            String publicId = uploadResult.getOrDefault(PUBLIC_ID, "");

            return new CloudinaryImage().setPublicId(publicId).setUrl(url);
        } finally {
            tempFile.delete();
        }
    }

    @Override
    public Boolean delete(String publicId) {

        try {
            this.cloudinary.uploader().destroy(publicId, Map.of());
        } catch (IOException exception) {
            return false;
        }

        return true;
    }

    @Override
    public CloudinaryServiceImpl setFolderName(String folderName) {
        this.folderName = "/" + folderName;
        return this;
    }
}
