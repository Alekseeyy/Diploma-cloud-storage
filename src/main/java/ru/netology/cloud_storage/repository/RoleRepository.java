package ru.netology.cloud_storage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import ru.netology.cloud_storage.entity.Role;

@Service
public interface RoleRepository extends JpaRepository<Role, Long> {

}
