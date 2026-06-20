package com.balantes.property_management_system.util;

import com.balantes.property_management_system.exception.FuncErrorException;
import lombok.experimental.UtilityClass;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

@UtilityClass
public class FileUploadUtil {

    public static final long MAX_FILE_SIZE = 2 * 1024 * 1024;

    public static void assertAllowed(MultipartFile file) {

        if (file == null || file.isEmpty()) {
            throw new FuncErrorException("File is empty");
        }

        if (file.getSize() > MAX_FILE_SIZE) {
            throw new FuncErrorException("Max file size is 2MB");
        }

        String fileName = file.getOriginalFilename();
        if (fileName == null || fileName.isBlank()) {
            throw new FuncErrorException("Invalid file name");
        }

        String extension = FilenameUtils.getExtension(fileName);

        if (!extension.matches("(?i)jpg|jpeg|png|gif|bmp")) {
            throw new FuncErrorException("Only jpg, png, gif, bmp files are allowed");
        }
    }

    public static String getFileName(final String name) {
        String baseName = FilenameUtils.getBaseName(name);
        String extension = FilenameUtils.getExtension(name);
        String date = String.valueOf(System.currentTimeMillis());

        return String.format("%s_%s.%s", baseName, date, extension);
    }
}