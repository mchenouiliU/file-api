package com.chencorp.fileapi.dao;

import com.chencorp.fileapi.model.File;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public interface FileDao extends CrudRepository<File, Long> {
    @Query(
            value = "SELECT u FROM File u" +
                    " WHERE 1 = 1 " +
                    " AND (:category is null or lower(u.category) = lower(:category))" +
                    " AND (:name is null or lower(u.name) like lower(concat('%', :name, '%')))")
    List<File> findByNameAndCategory(@Param("name")String name, @Param("category") String category);
    List<File> findByName(String name);

}
