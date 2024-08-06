package com.example.meetpro.entity.board;

import java.util.List;
import java.util.UUID;

import com.example.meetpro.entity.user.EntityDate;
import com.example.meetpro.exception.UnsupportedImageFormatException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Setter
public class Image extends EntityDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String uniqueName;

    @Column(nullable = false)
    private String originName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Board board;

    private final static List<String> supportedExtensions = List.of("jpg", "jpeg", "gif", "bmp", "png");

    private Image(final String originName) {
        this.originName = originName;
        this.uniqueName = generateUniqueName(extractExtension(originName));
    }

    public static Image from(final String originName) {
        return new Image(originName);
    }

    public void initBoard(final Board board) {
        if (this.board == null) {
            this.board = board;
        }
    }

    private String generateUniqueName(final String extension) {
        return UUID.randomUUID() + "." + extension;
    }

    private String extractExtension(final String originName) {
        String extension = originName.substring(originName.lastIndexOf(".") + 1);

        if (isSupportedFormat(extension)) {
            return extension;
        }

        throw new UnsupportedImageFormatException();
    }

    private boolean isSupportedFormat(final String extension) {
        return supportedExtensions.stream()
                .anyMatch(supportedExtension -> supportedExtension.equalsIgnoreCase(extension));
    }

    public boolean isSameImageId(final int id) {
        return this.getId() == id;
    }
}