package com.company.enroller.controllers;

import com.company.enroller.model.Meeting;
import com.company.enroller.model.Participant;
import com.company.enroller.persistence.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/meetings")
public class MeetingRestController{
    @Autowired
    MeetingService meetingService;

//    @RequestMapping(value = "", method = RequestMethod.GET)
//    public ResponseEntity<?> getMeetings(
//            @RequestParam(value = "sortBy", defaultValue = "")String sortBy,
//            @RequestParam(value = "sortOrder", defaultValue = "")String sortOrder,
//            @RequestParam(value = "key", defaultValue = "")String id
//    )
//    {
//        Collection<Meeting> meetingServices = meetingService.getAll(sortBy, sortOrder, id);
//        return new ResponseEntity<Collection<Participant>>(meetingServices, HttpStatus.OK);
//    }
}