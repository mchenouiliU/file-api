package com.chencorp.fileapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "FileData")
@Data
public class FileData extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int version;
    @Basic(fetch = FetchType.LAZY)
    @JsonIgnore
    @Lob
    private byte[] data;

    @ManyToOne
    @JsonIgnore
    private File file;

}
