package rocketseat.com.passin.services;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import rocketseat.com.passin.domain.attendee.Attendee;
import rocketseat.com.passin.domain.checkin.CheckIn;
import rocketseat.com.passin.domain.checkin.exceptions.CheckInAlreadyExistsException;
import rocketseat.com.passin.repositories.CheckinRepository;

@Service
@AllArgsConstructor
public class CheckInService{
	private final CheckinRepository checkinRepository;
	
	public void RegisterCheckiIn(Attendee attendee) {
		this.verifyCheckInExists(attendee.getId());
		CheckIn newCheckin = new CheckIn();
		newCheckin.setAttendee(attendee);
		newCheckin.setCreatedAt(LocalDateTime.now());
		this.checkinRepository.save(newCheckin);
	}
	
	private void verifyCheckInExists(String attendeeId) {
		Optional<CheckIn> isCheckedIn = this.getCheckIn(attendeeId);
		if(isCheckedIn.isPresent()) throw new CheckInAlreadyExistsException("Attendee already checked in");
	}
	
	public Optional<CheckIn> getCheckIn(String attendeeId){
		return this.checkinRepository.findByAttendeeId(attendeeId);
	}
	
}