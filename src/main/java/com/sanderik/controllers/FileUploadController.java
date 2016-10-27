package com.sanderik.controllers;

import com.sanderik.models.Device;
import com.sanderik.repositories.DeviceRepository;
import com.sanderik.storage.StorageFileNotFoundException;
import com.sanderik.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.io.IOException;
import java.util.stream.Collectors;

@Controller
public class FileUploadController {

    private final StorageService storageService;

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    public FileUploadController(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping("/admin")
    public String listUploadedFiles(Model model) throws IOException {

        model.addAttribute("files", storageService
                .loadAll()
                .map(path ->
                        MvcUriComponentsBuilder
                                .fromMethodName(FileUploadController.class, "serveFile", path.getFileName().toString())
                                .build().toString())
                .collect(Collectors.toList()));

        return "uploadForm";
    }

    @GetMapping("/firmware/version/{currentVersion:.+}")
    @ResponseBody
    public ResponseEntity serveFile(@PathVariable String currentVersion, @RequestHeader(value = "Authorization") String connectionToken) {
        Device device = deviceRepository.findOneByConnectionToken(connectionToken);

        if(device == null)
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        try {
            Resource latestVersion = storageService.getLatestVersion(Double.parseDouble(currentVersion));

            if (latestVersion == null) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity
                        .ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + latestVersion.getFilename() + "\"")
                        .body(latestVersion);
            }
        } catch (Exception e) {
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping("/admin")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, Model model) {
        try {
            storageService.store(file);
            model.addAttribute("message", "You successfully uploaded " + file.getOriginalFilename() + "!");
        } catch (Exception e) {
            model.addAttribute("error", "Filename " + file.getOriginalFilename() + "already exist, try another one");
        }

        return "uploadForm";
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }

}