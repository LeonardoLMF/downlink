package br.com.oel.downlink.repository;

import br.com.oel.downlink.model.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<FileEntity, String> {
}
