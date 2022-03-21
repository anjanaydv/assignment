package com.hilton.assignment.controller;

import com.hilton.assignment.annotation.ValidIP;
import com.hilton.assignment.model.IpData;
import com.hilton.assignment.service.IPService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

/**
 * @Copyright Any portion of this assignment's code are not allowed to use in business or production.
 *
 * This controller class is used to create rest api. The api like http(s)://host/info/ip=1.2.3.4 is used to get ip info.
 *
 * @author Anjana Yadav
 */
@RestController("/info")
@Validated
public class IPController {
    private IPService ipService;
    Logger logger = LoggerFactory.getLogger(IPController.class);

    @Autowired
    public IPController(IPService ipService){
        this.ipService = ipService;
    }

    /**
     * The RequestParam is validated using custom annotation @ValidIP which allows only ipv6 or ipv4 to enter inside service.
     *
     * @param ip
     * @return
     */
    @GetMapping("/{ip}")
    public ResponseEntity<Object> fetchIpDateDetails(@Valid @PathParam("ip") @ValidIP String ip){
        logger.info("Received request in controller for ip address {}", ip);
        IpData data = ipService.getIpData(ip);
        logger.info("Sending response data for ip {} data {}", ip, data.toString());
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
}
