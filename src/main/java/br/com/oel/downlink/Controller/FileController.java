package br.com.oel.downlink.Controller;

import br.com.oel.downlink.Service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping(name = "/")
public class FileController {

    @Autowired
    private FileService fileService;


    //upload the file then return the download link
    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file")MultipartFile file) throws IOException{
        String fileId = fileService.saveFile(file);
        String downloadUrl = "http://localhost:8080/download/" + fileId;

        //status 200 ok
        return ResponseEntity.ok("File updated. Download here: " + downloadUrl);
    }

    /* endpoint
    download the file by id */
    @GetMapping("/download/{id}")

    public ResponseEntity<Resource> downloadFile(@PathVariable String id) {
        return fileService.getFile(id)
                .map(file -> {

                    //cria um recurso a partir do conteudo do arquivo(bytes)
                    Resource resource = new ByteArrayResource(file.getData());

                    return ResponseEntity.ok()
                            .contentType(MediaType.parseMediaType(file.getContentType()))
                            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName() + "\"")
                            .body(resource);
                })

                //se  nao for encontrado, retorna 404
                .orElse(ResponseEntity.notFound().build());
    }

}
