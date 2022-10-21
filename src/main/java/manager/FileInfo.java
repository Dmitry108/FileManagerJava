package manager;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Getter
@Setter
public class FileInfo {
    private String name;
    private Type type;
    private long size;
    private LocalDateTime last;

    public FileInfo(File file) {
        this.name = file.getName();
        this.type = file.isFile() ? Type.FILE : Type.DIRECTORY;
        this.size = file.length();
        this.last = LocalDateTime.ofInstant(Instant.ofEpochMilli(file.lastModified()), ZoneOffset.ofHours(0));
    }

    @Getter
    @AllArgsConstructor
    public enum Type {
        FILE("F"), DIRECTORY("D");
        private String name;
    }
}