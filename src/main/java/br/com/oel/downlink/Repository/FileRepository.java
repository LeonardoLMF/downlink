package br.com.oel.downlink.Repository;

import br.com.oel.downlink.Model.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<FileEntity, String> {
}
