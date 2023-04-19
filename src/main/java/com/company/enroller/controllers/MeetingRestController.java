package com.company.enroller.controllers;

import com.company.enroller.model.Meeting;
import com.company.enroller.model.Participant;
import com.company.enroller.persistence.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/meetings")
public class MeetingRestController{
    @Autowired
    MeetingService meetingService;
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<?> getMeetings(
            @RequestParam(value = "sortBy", defaultValue = "")String sortBy,
            @RequestParam(value = "sortOrder", defaultValue = "")String sortOrder,
            @RequestParam(value = "key", defaultValue = "")String id
    )
    {
        Collection<Meeting> meetings = meetingService.getAll(sortBy, sortOrder, id);
        return new ResponseEntity<Collection<Meeting>>(meetings, HttpStatus.OK);
    }
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getMeeting(@PathVariable("id") Long id){
        Meeting meeting = meetingService.findById(id);
        if (meeting == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(meeting,HttpStatus.OK);
    }
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<?> addMeeting(@RequestBody Meeting meeting) {
        if (meetingService.alreadyExist(meeting)) {
            return new ResponseEntity<String>(
                    " Cannot create Meeting, beacuse meeting with title:  "+ meeting.getTitle() +" and date:" +
                            meeting.getDate() +"already exist",
                    HttpStatus.CONFLICT);
        }
        meetingService.add(meeting);
        return new ResponseEntity<Meeting>(meeting, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateMeeting(@PathVariable("id") Long id, @RequestBody Meeting updatedMeeting){
        Meeting meeting = meetingService.findById(id);
        if (meeting == null) {
            return new ResponseEntity<String>(
                    "Meeting with specified id: " +id + " does not exist"
                    , HttpStatus.CONFLICT);
        }
        meeting.setDate(updatedMeeting.getDate());
        meeting.setDescription(updatedMeeting.getDescription());
        meeting.setTitle(updatedMeeting.getTitle());
        meetingService.update(meeting);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteMeeting(@PathVariable("id") Long id){
        Meeting meeting = meetingService.findById(id);
        if (meeting == null) {
            return new ResponseEntity<String>(
                    "Meeting with specified id: " +id + " does not exist"
                    , HttpStatus.CONFLICT);
        }
        meetingService.delete(meeting);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}