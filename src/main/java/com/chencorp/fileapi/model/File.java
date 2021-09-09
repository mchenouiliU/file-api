package com.chencorp.fileapi.model;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Singular;

import javax.persistence.*;
import java.util.List;

@Entity(name = "File")
@Data
public class File extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String category;

    @OneToMany
    @JoinColumn(name = "file_id")
    @Singular
    private List<FileData> dataList;
}
