package com.company.enroller.controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.company.enroller.model.Participant;
import com.company.enroller.persistence.ParticipantService;

@RestController
@RequestMapping("/participants")
public class ParticipantRestController {

	@Autowired
	ParticipantService participantService;

	
	
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<?> getParticipants() {
		Collection<Participant> participants = participantService.getAll();
		return new ResponseEntity<Collection<Participant>>(participants, HttpStatus.OK);
	}
	
	
	
	
	
	 @RequestMapping(value = "", method = RequestMethod.POST)
	 
	 public ResponseEntity<?> registerParticipant(@RequestBody Participant participant){
	 
		 //sprawdzanie
		 if (participantService.findByLogin(participant.getLogin())!=null) {
		 return new ResponseEntity("There already is participant with that login: "
		 		+ ""+ participant.getLogin()+ "exist" , HttpStatus.CONFLICT);
	 }
	//dodawanie	 
	 participantService.addParticipant(participant);
	 //zwrot
	 return new ResponseEntity<Participant>(participant, HttpStatus.OK);
	 }
	 
	 
	 
	 
	 
	 //USUWANIE
	 @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	 
	 public ResponseEntity<?> deleteParticipant(@PathVariable("id")String login){
	
		 //sprawdzenie istnienia
		 if (participantService.findByLogin(login) == null) {
			 	return new ResponseEntity("Participant with login: " + login + "does not exists", HttpStatus.NOT_FOUND);
			 }
	// dodac
	 participantService.deleteParticipantByLogin(login);
	
	 // zwrocic
	 return new ResponseEntity<Participant>(HttpStatus.OK);
	 }
	 
	 
	 //GET
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getMeeting(@PathVariable("id") String login) {
	     Participant participant = participantService.findByLogin(login);
	     if (participant == null) {
	         return new ResponseEntity(HttpStatus.NOT_FOUND);
	     }
	     return new ResponseEntity<Participant>(participant, HttpStatus.OK);
	 }
	
	//UPDATE
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateMeeting(@PathVariable("id") String login, @RequestBody Participant updatedParticipant) {
	     Participant participant = participantService.findByLogin(login);
	     if (participant == null) {
	         return new ResponseEntity(HttpStatus.NOT_FOUND);
	     }
	     participant.setPassword(updatedParticipant.getPassword());
	     participantService.update(participant);
	     return new ResponseEntity<Participant>(participant, HttpStatus.OK);
	 }
	
	
	
	
}
