package ru.netology.cloud_storage.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "repository")
public class FileToCloud {

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @JsonProperty("filename")
    @Column
    private String name;

    @JsonIgnore
    @Column
    private String type;

    @JsonIgnore
    @Lob
    private byte[] data;

    @Column
    private Long size;

    @JsonIgnore
    @Column
    private Long userId;

    public FileToCloud(String name, String type, byte[] data, long size) {
        this.name = name;
        this.type = type;
        this.data = data;
        this.size = size;
    }
}
