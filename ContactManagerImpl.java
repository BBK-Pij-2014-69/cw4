package cw4;

import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ContactManagerImpl implements ContactManager {
	private int meetingId = 0;
	private int contactId = 0;
	private List<Meeting> meetingsList = new ArrayList<Meeting>();
	private Set<Contact> contactSet = new HashSet<Contact>();
	private File file = new File("contacts.txt");
	
	public ContactManagerImpl(){
		if (file.exists()){
			try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))){
				meetingsList = (ArrayList) in.readObject();
				contactSet = (HashSet) in.readObject();
				meetingId = (int) in.readObject();
				contactId = (int) in.readObject();	
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public int addFutureMeeting(Set<Contact> contacts, Calendar date) {
		if (date.before(Calendar.getInstance())) throw new IllegalArgumentException("Invalid time/date");
		checkContacts(contacts);
		meetingId ++;
		this.meetingsList.add(new FutureMeetingImpl(meetingId, date, contacts));
		chronologicalReArrange(meetingsList);
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
		if (!contactSet.contains(contact)) throw new IllegalArgumentException("Contact does not exist");
		ArrayList<Meeting> returnList = new ArrayList<Meeting>();
		for (Meeting m : meetingsList){
			if (m.getContacts().contains(contact) && m instanceof FutureMeeting) returnList.add(m);
		}
		return returnList;
	}

	@Override
	public List<Meeting> getFutureMeetingList(Calendar date) {
		List<Meeting> returnList = new ArrayList<Meeting>();
		for (Meeting m : meetingsList){
			if (m.getDate().get(Calendar.YEAR) == date.get(Calendar.YEAR) && m.getDate().get(Calendar.DAY_OF_YEAR) == date.get(Calendar.DAY_OF_YEAR)) returnList.add(m);
		}
		return returnList;
	}

	@Override
	public List<PastMeeting> getPastMeetingList(Contact contact) {
		if (!contactSet.contains(contact)) throw new IllegalArgumentException("Contact does not exist");
		ArrayList<PastMeeting> returnList = new ArrayList<PastMeeting>();
		for (Meeting m : meetingsList){
			if (m.getContacts().contains(contact) && m instanceof PastMeeting) returnList.add((PastMeeting)m);
		}
		return returnList;
	}

	@Override
	public void addNewPastMeeting(Set<Contact> contacts, Calendar date, String text) {
		checkContacts(contacts);
		if (date == null) throw new NullPointerException("Null item found");
		checkNull(text);
		meetingId++;
		meetingsList.add(new PastMeetingImpl(meetingId, date, contacts, text));
		chronologicalReArrange(meetingsList);
	}

	@Override
	public void addMeetingNotes(int id, String text) {
		checkNull(text);
		PastMeetingImpl tempMeeting = null;
		int indexToRemove = 0;
		for(Meeting m : meetingsList){
			if (m.getId() == id){
				if (m.getDate().after(Calendar.getInstance())) throw new IllegalStateException("Future Date Found");
				tempMeeting = new PastMeetingImpl(id, m.getDate(), m.getContacts(), text);
				indexToRemove = meetingsList.indexOf(m);
			}
		}
		if (tempMeeting == null) throw new IllegalArgumentException("Meeting does not exist");
		meetingsList.remove(indexToRemove);
		meetingsList.add(tempMeeting);
		chronologicalReArrange(meetingsList);
	}

	@Override
	public void addNewContact(String name, String notes) {
		checkNull(name, notes);
		contactId++;
		Contact contactToAdd = new ContactImpl(contactId, name);
		contactToAdd.addNotes(notes);
		contactSet.add(contactToAdd);
	}

	
	@Override
	public Set<Contact> getContacts(int... ids) {
		int noOfIds = ids.length;
		Set <Contact> returnSet = new HashSet<Contact>();
		for (Contact c : contactSet){
			for (int i : ids){
				if (c.getId() == i){
					returnSet.add(c);
					noOfIds--;
				}
			}
		}
		if (noOfIds != 0) throw new IllegalArgumentException("One or more id's are invalid");
		return returnSet;
	}

	@Override
	public Set<Contact> getContacts(String name) {
		checkNull(name);
		Set<Contact> returnSet = new HashSet<Contact>();
		for (Contact contact: contactSet){
			if (contact.getName().contains(name)){
				returnSet.add(contact);
			}
		}
		return returnSet;
	}

	
	@Override
	public void flush() {
		if (file.exists()){
			try{
				file.delete();
			}
			catch (Exception e){
				e.printStackTrace();
			}
		}
		try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))){
	         out.writeObject(meetingsList);
	         out.writeObject(contactSet);
	         out.writeObject(meetingId);
	         out.writeObject(contactId);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void chronologicalReArrange(List<Meeting> list){
		Collections.sort(meetingsList, new Comparator<Meeting>() {
			public int compare(Meeting o1, Meeting o2) {
				return o1.getDate().compareTo(o2.getDate());
			}            
		}); 
	}
	
	private void checkNull(String... nullString){
		for (String s : nullString){
			if (s == null) throw new NullPointerException("Null item found");
		}
	}
	
	private void checkContacts(Set<Contact> checkSet){
		if (checkSet == null) throw new NullPointerException("Contacts is null");
		if (!contactSet.containsAll(checkSet)) throw new IllegalArgumentException("One or more contacts do not exist");
		if (checkSet.isEmpty()) throw new IllegalArgumentException("Contacts list is empty");
	}

}
