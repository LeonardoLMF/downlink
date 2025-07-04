package br.com.oel.downlink.controller;

import br.com.oel.downlink.model.FileEntity;
import br.com.oel.downlink.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping(name = "/")
public class FileController {

    @Autowired
    private FileService fileService;


    //carrega o arquivo e gera o link de download
    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file")MultipartFile file) throws IOException{
        String fileId = fileService.saveFile(file);
        String downloadUrl = "http://localhost:8080/download/" + fileId;

        log.info("File available at: {}", downloadUrl);

        //status 200 ok
        return ResponseEntity.ok("File updated. Download here: " + downloadUrl);
    }

    /* endpoint
    download no arquivo pelo id */
    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String id) {
        FileEntity file = fileService.getFile(id); // now directly returns or throws

        Resource resource = new ByteArrayResource(file.getData());

        log.info("Serving file download: {}", file.getFileName());

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName() + "\"")
                .body(resource);
    }

}
