package com.sanderik.storage;

import org.apache.tomcat.util.http.fileupload.InvalidFileNameException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class FileSystemStorageService implements StorageService {

    private static final String prefix = "ibrator-";
    private static final String extension = ".lua";

    private final Path rootLocation;

    @Autowired
    public FileSystemStorageService(StorageProperties properties) {
        this.rootLocation = Paths.get(properties.getLocation());
    }

    @Override
    public void store(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file " + file.getOriginalFilename());
            }
            if (!file.getOriginalFilename().endsWith(".lua")) {
                throw new InvalidFileNameException(".lua", "Cannot save file type, extension must be .lua");
            }
            Files.copy(file.getInputStream(), this.rootLocation.resolve(file.getOriginalFilename()));
        } catch (IOException e) {
            throw new StorageException("Failed to store file " + file.getOriginalFilename(), e);
        }
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.rootLocation, 1)
                    .filter(path -> !path.equals(this.rootLocation))
                    .map(path -> this.rootLocation.relativize(path));
        } catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }
    }

    @Override
    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    @Override
    public Resource getLatestVersion(int currentVersion) {
        try {
            int latestVersion = getLatestVersion(loadAll().collect(Collectors.toList()));

            if (currentVersion < latestVersion) {
                String filename = prefix + String.valueOf(latestVersion) + extension;

                UrlResource resource = new UrlResource(load(filename).toUri());
                if (resource.exists() || resource.isReadable()) {
                    return resource;
                } else {
                    throw new StorageFileNotFoundException("Could not read file: " + filename);

                }
            } else {
                return null;
            }
        } catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file for version: " + currentVersion, e);
        }
    }

    private int getLatestVersion(List<Path> files) {
        int latestVersion = 0;

        for (Path file : files) {
            String versionString = file.getFileName().toString();
            versionString = versionString
                    .replaceFirst(prefix, "")
                    .replace(extension, "");

            int version = Integer.parseInt(versionString);

            if (version > latestVersion) {
                latestVersion = version;
            }
        }

        return latestVersion;
    }

    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new StorageFileNotFoundException("Could not read file: " + filename);

            }
        } catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    @Override
    public void init() {
        try {
            Files.createDirectory(rootLocation);
        } catch (FileAlreadyExistsException e) {

        } catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }
}
