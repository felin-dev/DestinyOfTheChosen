package game.destinyofthechosen.service;

import game.destinyofthechosen.model.service.CloudinaryImage;
import game.destinyofthechosen.service.impl.CloudinaryServiceImpl;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface CloudinaryService {

    CloudinaryImage upload(MultipartFile file) throws IOException;

    Boolean delete(String publicId);

    CloudinaryServiceImpl setFolderName(String folderName);
}
