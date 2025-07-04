package br.com.oel.downlink.service;

import br.com.oel.downlink.exception.FileNotFoundException;
import br.com.oel.downlink.model.FileEntity;
import br.com.oel.downlink.repository.FileRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class FileService {

    @Autowired
    private FileRepository fileRepository;

    // Guarda o arquivo no banco e retorna o id
    public String saveFile(MultipartFile file) throws IOException{
        String id = UUID.randomUUID().toString();

        FileEntity fileEntity = FileEntity.builder()
                .id(id)
                .fileName(file.getOriginalFilename())
                .size(file.getSize())
                .data(file.getBytes())
                .build();

        fileRepository.save(fileEntity);

        log.info("File updated: {}(ID: {}, Size: {} bytes, Type: {))", file.getOriginalFilename(), id, file.getSize(), file.getContentType());

        return id;
    }

    public FileEntity getFile(String id){
        FileEntity file = fileRepository.findById(id)
                .orElseThrow(() -> new FileNotFoundException("File with ID '" + id + "' not found."));

        log.info("File retrieved: {} (ID: {}, Size: {} bytes)", file.getFileName(), file.getId(), file.getSize());

        return file;
    }


}
