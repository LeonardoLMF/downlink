package br.com.oel.downlink.service;

import br.com.oel.downlink.exception.FileNotFoundException;
import br.com.oel.downlink.model.FileEntity;
import br.com.oel.downlink.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

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

        return id;
    }

    public FileEntity getFile(String id){
        return fileRepository.findById(id)
                .orElseThrow(() -> new FileNotFoundException("File with ID '" + id + "' not found"));
    }


}
