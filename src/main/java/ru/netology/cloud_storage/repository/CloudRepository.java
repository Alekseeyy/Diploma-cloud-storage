package ru.netology.cloud_storage.repository;

import lombok.Data;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import ru.netology.cloud_storage.entity.FileToCloud;
import ru.netology.cloud_storage.exceptions.StorageException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Optional.ofNullable;

@Data
@Transactional
@Repository
public class CloudRepository {
    private final FileRepository fileRepository;
    private final UserRepository userRepository;

    private Map<String, UserDetails> tokenStorage = new ConcurrentHashMap<>();

    public CloudRepository(FileRepository fileRepository, UserRepository userRepository) {
        this.fileRepository = fileRepository;
        this.userRepository = userRepository;
    }

    public void login(String authToken, UserDetails userDetails) {
        tokenStorage.put(authToken, userDetails);
    }

    public Optional<UserDetails> logout(String authToken) {
        return ofNullable(tokenStorage.remove(authToken));
    }

    public Optional<FileToCloud> uploadFile(FileToCloud cloud, String authToken) {
        Optional<Long> userId = getUserId(authToken);
        if (userId.isPresent()) {
            cloud.setUserId(userId.get());
            return Optional.of(fileRepository.save(cloud));
        } else {
            throw new StorageException("Invalid auth-token");
        }
    }

    public void deleteFile(String authToken, String fileName) {
        Optional<Long> userId = getUserId(authToken);
        if (userId.isPresent()) {
            fileRepository.removeByUserIdAndName(userId.get(), fileName);
        } else {
            throw new StorageException("Invalid auth-token");
        }
    }

    public Optional<FileToCloud> downloadFile(String authToken, String fileName) {
        Optional<Long> userId = getUserId(authToken);
        if (userId.isPresent()) {
            return fileRepository.findByUserIdAndName(userId.get(), fileName);
        } else {
            throw new StorageException("Invalid auth-token");
        }
    }

    public Optional<FileToCloud> renameFile(String authToken, String fileName, String newFileName) {
        Optional<Long> userId = getUserId(authToken);
        if (userId.isPresent()) {
            Optional<FileToCloud> cloudFile = fileRepository.findByUserIdAndName(userId.get(), fileName);
            cloudFile.ifPresent(file -> file.setName(newFileName));
            return Optional.of(fileRepository.save(Objects.requireNonNull(cloudFile.orElse(null))));
        } else {
            throw new StorageException("Invalid auth-token");
        }
    }

    public Optional<List<FileToCloud>> getFiles(String authToken, int limit) {
        Optional<Long> userId = getUserId(authToken);
        if (userId.isPresent()) {
            return ofNullable(fileRepository.findAllByUserIdWithLimit(userId.get(), limit));
        } else {
            throw new StorageException("Invalid auth-token");
        }
    }

    private Optional<Long> getUserId(String authToken) {
        UserDetails user = tokenStorage.get(authToken.substring(7));
        return user != null ? ofNullable(Objects.requireNonNull(userRepository.findByLogin(user.getUsername()).
                orElse(null)).getId()) : Optional.empty();
    }
}
