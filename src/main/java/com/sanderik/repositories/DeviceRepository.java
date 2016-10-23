package com.sanderik.repositories;

import com.sanderik.models.Device;
import com.sanderik.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

@SuppressWarnings("JpaQlInspection")
@Transactional
public interface DeviceRepository extends CrudRepository<Device, Long> {

    Device findOneByChipId(String chipId);

    Device findOneByConnectionToken(String connectionToken);
}

