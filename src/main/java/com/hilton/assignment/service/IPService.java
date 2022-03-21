package com.hilton.assignment.service;

import com.hilton.assignment.controller.IPController;
import com.hilton.assignment.model.IpData;
import com.hilton.assignment.repository.IpRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

/**
 *  @Copyright Any portion of this assignment's code are not allowed to use in business or production.
 *
 *  This service class is used to fetch data from api, cache or/and save to db.
 *
 * @author Anjana Yadav
 */
@Service
public class IPService {
    private IpRepo ipRepo;
    private final String API_URL = "http://ip-api.com/json/";
    Logger logger = LoggerFactory.getLogger(IPController.class);

    // This is used to call api to get data for ip
    RestTemplate restTemplate = new RestTemplate();

    @Autowired
    public IPService(IpRepo ipRepo) {
        this.ipRepo = ipRepo;
    }

    /**
     * Cache the data as ipCache::1.2.7.3.
     * If cache is not available then falls back to db call.
     * If data is not in db then falls back to api call.
     *
     * @param ip
     * @return instaceof#IpData
     */
    @Cacheable(value = "ipCache")
    public IpData getIpData(String ip) {
        logger.debug("Data not found in cache for ip "+ ip);
        IpData result =  ipRepo.findByQuery(ip);
        logger.debug("Data not found in db for ip "+ ip);
        if (result == null) {
            final String uriString = API_URL + ip;
            try{
                URI uri = new URI(uriString);
                logger.debug("Retrieving data for ip "+ ip);
                 result = restTemplate.getForObject(uri, IpData.class);
                 // async save doesn't block the response to client.
                 save(result);
            } catch (URISyntaxException ex){
                logger.error("Invalid uri "+ ex.getMessage());
                throw new RuntimeException("Invalid url.");
            }
        }
        if(result == null){
            logger.error("Failed to retrieve data for ip" + ip);
            throw new RuntimeException("Error while fetching data.");
        }
        return result;
    }

    /**
     * Save data asynchronous in db
     * @param ipData
     */
    @Async
    public void save(IpData ipData){
        logger.info("Saving data in db for ip " + ipData.getQuery());
        ipRepo.save(ipData);
    }
}
