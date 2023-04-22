package com.company.enroller.controllers;

import com.company.enroller.model.Meeting;
import com.company.enroller.model.Participant;
import com.company.enroller.persistence.MeetingService;
import com.company.enroller.persistence.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/meeting_participant")
public class MeetingParticipantRestController {
    @Autowired
    MeetingService meetingService;
    @Autowired
    ParticipantService participantService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getMeetingParticipant(@PathVariable("id") Long id) {
        Meeting meeting = meetingService.findById(id);
        if (meeting == null) {
            return new ResponseEntity<String>(
                    "Unable to find meeting with specified id: " + id
                    , HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(meeting.getParticipants(), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public ResponseEntity<?> addParticipantToMeeting(@PathVariable("id") Long id, @RequestBody Participant addedPparticipant) {
        Meeting meeting = meetingService.findById(id);
        if (meeting == null) {
            return new ResponseEntity<String>(
                    "Unable to find meeting with specified id: " + id
                    , HttpStatus.NOT_FOUND);
        }
        Participant participant = participantService.findByLogin(addedPparticipant.getLogin());
        if (participant == null) {
            return new ResponseEntity<String>(
                    "Participant with login: " + addedPparticipant.getLogin() + " is not registered"
                    , HttpStatus.NOT_FOUND);
        }
        meeting.addParticipant(addedPparticipant);
        meetingService.update(meeting);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> removeParticipantFromMeeting(@PathVariable("id") Long id, @RequestBody Participant deletedParticipant) {
        Meeting meeting = meetingService.findById(id);
        if (meeting == null) {
            return new ResponseEntity<String>(
                    "Unable to find meeting with specified id: " + id
                    , HttpStatus.NOT_FOUND);
        }
        Participant participant = participantService.findByLogin(deletedParticipant.getLogin());
        if (participant == null) {
            return new ResponseEntity<String>(
                    "Unable to find user with specified login: " + participant.getLogin()
                    , HttpStatus.NOT_FOUND);
        }
        if(meeting.checkIfParticipantIsInMeeting(participant)){
            meeting.removeParticipant(participant);
        }
        else {
            return new ResponseEntity<String>(
                    "The specified user is not signed in on this meeting"
                    ,HttpStatus.NOT_FOUND);
        }
        meetingService.update(meeting);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
