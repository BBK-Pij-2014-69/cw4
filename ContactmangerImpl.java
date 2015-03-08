package cw4;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ContactmangerImpl implements ContactManager {
	private int meetingId = 0;
	private int contactId = 0;
	private List<Meeting> meetingsList = new ArrayList<Meeting>();
	private Set<Contact> contactSet = new HashSet<Contact>();

	@Override
	public int addFutureMeeting(Set<Contact> contacts, Calendar date) {
		if (date.before(Calendar.getInstance())) throw new IllegalArgumentException("Invalid time/date");
		if (!contactSet.containsAll(contacts)) throw new IllegalArgumentException("One or more contacts do not exist");
		meetingId ++;
		this.meetingsList.add(new FutureMeetingImpl(meetingId, date, contacts));
		return meetingId;
	}

	@Override
	public PastMeeting getPastMeeting(int id) {
		for (Meeting m : meetingsList){
			if (m.getId() == id && m.getDate().after(Calendar.getInstance())) throw new IllegalArgumentException("This id is for a future meeting");
			if (m.getId() == id) return (PastMeeting) m;
		}
		return null;
	}

	@Override 
	public FutureMeeting getFutureMeeting(int id) {
		for (Meeting m : meetingsList){
			if (m.getId() == id && m.getDate().before(Calendar.getInstance())) throw new IllegalArgumentException("This id is for a past meeting");
			if (m.getId() == id) return (FutureMeeting) m;
		}
		return null;
	}

	@Override
	public Meeting getMeeting(int id) {
		for (Meeting m : meetingsList){
			if (m.getId() == id) return m;
		}
		return null;
	}

	@Override
	public List<Meeting> getFutureMeetingList(Contact contact) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Meeting> getFutureMeetingList(Calendar date) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PastMeeting> getPastMeetingList(Contact contact) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addNewPastMeeting(Set<Contact> contacts, Calendar date, String text) {
		if (contacts.isEmpty()) throw new IllegalArgumentException("Contacts list is empty");
		if (!contactSet.containsAll(contacts)) throw new IllegalArgumentException("One or more contacts do not exist");
		if (contacts == null || date == null || text == null) throw new NullPointerException();
		meetingId++;
		meetingsList.add(new PastMeetingImpl(meetingId, date, contacts, text));
		
	}

	@Override
	public void addMeetingNotes(int id, String text) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addNewContact(String name, String notes) {
		if (name == null || notes == null) throw new NullPointerException();
		contactId++;
		Contact contactToAdd = new ContactImpl(contactId, name);
		contactToAdd.addNotes(notes);
		contactSet.add(contactToAdd);
	}

	@Override
	public Set<Contact> getContacts(int... ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Contact> getContacts(String name) {
		if (name == null) throw new NullPointerException();
		Set<Contact> returnSet = new HashSet<Contact>();
		for (Contact contact: contactSet){
			if (contact.getName() == name){
				returnSet.add(contact);
			}
		}
		return returnSet;
	}

	@Override
	public void flush() {
		// TODO Auto-generated method stub

	}

}
