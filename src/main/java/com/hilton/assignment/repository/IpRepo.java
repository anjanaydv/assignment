package com.hilton.assignment.repository;

import com.hilton.assignment.model.IpData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

/**
 * @Copyright Any portion of this assignment's code are not allowed to use in business or production.
 *
 * This repository is used to data persistence.
 *
 * @author Anjana Yadav
 */
@Repository
public interface IpRepo extends CrudRepository<IpData, Integer> {
    /**
     * Retrieve the data by ip address
     * @param query
     * @return instanceof#IpData
     */
    IpData findByQuery(String query);

    @Override
    @Async
    IpData save(IpData ipData);
}
