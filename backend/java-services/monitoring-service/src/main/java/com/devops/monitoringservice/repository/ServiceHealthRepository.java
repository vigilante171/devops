package com.devops.monitoringservice.repository;

import com.devops.monitoringservice.model.ServiceHealth;
import com.devops.monitoringservice.model.ServiceStatus;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ServiceHealthRepository extends MongoRepository<ServiceHealth, String> {
    Optional<ServiceHealth> findByServiceName(String serviceName);
    List<ServiceHealth> findByStatus(ServiceStatus status);
}
