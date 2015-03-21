package cw4;

import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * @see ContactManager
 * @author Kieren Millar
 *
 */
public class ContactManagerImpl implements ContactManager {
	private int meetingId = 0; // used to give meetings unique ids
	private int contactId = 0; // used to give contacts unique ids.
	private List<Meeting> meetingsList = new ArrayList<Meeting>();
	private Set<Contact> contactSet = new LinkedHashSet<Contact>();
	private File file = new File("contacts.txt");
	
	/**
	 * Constructor that creates a simple ContactManagerImpl or
	 * loads up a previously saved file.
	 */
	public ContactManagerImpl(){
		if (file.exists()){
			BufferedReader in = null;
			try {
				in = new BufferedReader(new FileReader(file)); 
				String line;
				String[] string;
				Calendar date;// used for readability
				Set<Contact> setOfContacts;//// used for readability
				//reads contacts from the file
				while ((line = in.readLine()) != null && !line.equals("PastMeetings")) {
					string = line.split(",",-1);
					contactSet.add(new ContactImpl(Integer.parseInt(string[0]),string[1],string[2]));
					contactId++;
				}
				//reads future meeting from the file
				while((line = in.readLine()) != null && !line.equals("FutureMeetings")) {
					string = line.split(",",-1);
					date = new GregorianCalendar(Integer.parseInt(string[2]),Integer.parseInt(string[3]),
							Integer.parseInt(string[4]),Integer.parseInt(string[5]),Integer.parseInt(string[6]));
					setOfContacts = new HashSet<Contact>();
					for (int i = 7; i < string.length; i = i + 3){
						setOfContacts.add(new ContactImpl(Integer.parseInt(string[i]), string[i+1], string[i+2]));
					}
					meetingsList.add(new PastMeetingImpl(Integer.parseInt(string[0]), date, setOfContacts, string[1]));
					meetingId ++;
				}
				//reads past meetings from the file
				while((line = in.readLine()) != null) {
					string = line.split(",",-1);
					date = new GregorianCalendar(Integer.parseInt(string[1]),Integer.parseInt(string[2]),
							Integer.parseInt(string[3]),Integer.parseInt(string[4]),Integer.parseInt(string[5]));
					setOfContacts = new HashSet<Contact>();
					for (int i = 6; i < string.length; i = i + 3){
						setOfContacts.add(new ContactImpl(Integer.parseInt(string[i]), string[i+1], string[i+2]));
					}
					meetingsList.add(new FutureMeetingImpl(Integer.parseInt(string[0]), date, setOfContacts));
					meetingId ++;
				}
				
			} catch (FileNotFoundException ex) { 
				System.out.println("File " + file + " does not exist.");
			} catch (IOException ex) { 
				ex.printStackTrace();
			} finally {
				closeReader(in);
			}
		}
		checkMeetingList();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int addFutureMeeting(Set<Contact> contacts, Calendar date) {
		if (date.before(Calendar.getInstance())) throw new IllegalArgumentException("Date is in the past");
		checkContacts(contacts);
		meetingId ++;
		this.meetingsList.add(new FutureMeetingImpl(meetingId, date, contacts));
		chronologicalReArrange();
		return meetingId;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PastMeeting getPastMeeting(int id) {
		checkMeetingList();
		for (Meeting m : meetingsList){
			if (m.getId() == id){
				 if (m.getDate().after(Calendar.getInstance())) throw new IllegalArgumentException("This id is for a future meeting");
				 return (PastMeeting) m;
			}
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override 
	public FutureMeeting getFutureMeeting(int id) {
		checkMeetingList();
		for (Meeting m : meetingsList){
			if (m.getId() == id){
				if (m.getDate().before(Calendar.getInstance())) throw new IllegalArgumentException("This id is for a past meeting");
				return (FutureMeeting) m;
			}
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Meeting getMeeting(int id) {
		checkMeetingList();
		for (Meeting m : meetingsList){
			if (m.getId() == id) return m;
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Meeting> getFutureMeetingList(Contact contact) {
		checkMeetingList();
		if (!contactSet.contains(contact)) throw new IllegalArgumentException("Contact does not exist");
		ArrayList<Meeting> returnList = new ArrayList<Meeting>();
		for (Meeting m : meetingsList){
			if (m.getContacts().contains(contact) && m instanceof FutureMeeting) returnList.add(m);
		}
		return returnList;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Meeting> getFutureMeetingList(Calendar date) {
		checkMeetingList();
		List<Meeting> returnList = new ArrayList<Meeting>();
		for (Meeting m : meetingsList){
			if (DatesAreEqual(m.getDate(), date)) returnList.add(m);
		}
		return returnList;
	}
	
	 /**
	 * Method to check if two dates (ignoring time of day) are the same.
	 * 
	 * @param Calendar, two calendars for comparing.
	 * @return True if the dates are the same, False otherwise
	 */
	//This method was written just for the sake of readability.
	private boolean DatesAreEqual(Calendar c1, Calendar c2){ 
		if (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR) && 
				c2.get(Calendar.DAY_OF_YEAR) == c2.get(Calendar.DAY_OF_YEAR)) return true;
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<PastMeeting> getPastMeetingList(Contact contact) {
		checkMeetingList();
		if (!contactSet.contains(contact)) throw new IllegalArgumentException("Contact does not exist");
		ArrayList<PastMeeting> returnList = new ArrayList<PastMeeting>();
		for (Meeting m : meetingsList){
			if (m.getContacts().contains(contact) && m instanceof PastMeeting) returnList.add((PastMeeting)m);
		}
		return returnList;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addNewPastMeeting(Set<Contact> contacts, Calendar date, String text) {
		checkContacts(contacts);
		checkIfArgumentsAreNull(text, date);
		meetingId++;
		meetingsList.add(new PastMeetingImpl(meetingId, date, contacts, text));
		chronologicalReArrange();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	// TODO notes
	public void addMeetingNotes(int id, String text) {
		checkIfArgumentsAreNull(text, id);
		PastMeetingImpl tempMeeting = null;
		int indexToRemove = 0;
		for(Meeting m : meetingsList){
			if (m.getId() == id){
				if (m.getDate().after(Calendar.getInstance())) throw new IllegalStateException("This id is for a future meeting");
				tempMeeting = new PastMeetingImpl(id, m.getDate(), m.getContacts(), text);
				indexToRemove = meetingsList.indexOf(m);
			}
		}
		if (tempMeeting == null) throw new IllegalArgumentException("Meeting does not exist");
		meetingsList.remove(indexToRemove);
		meetingsList.add(tempMeeting);
		chronologicalReArrange();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addNewContact(String name, String notes) {
		checkIfArgumentsAreNull(name, notes);
		contactId++;
		Contact contactToAdd = new ContactImpl(contactId, name);
		contactToAdd.addNotes(notes);
		contactSet.add(contactToAdd);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	//TODO notes
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<Contact> getContacts(String name) {
		checkIfArgumentsAreNull(name);
		Set<Contact> returnSet = new HashSet<Contact>();
		for (Contact contact: contactSet){
			if (contact.getName().contains(name)){
				returnSet.add(contact);
			}
		}
		return returnSet;
	}

	/**
	 * {@inheritDoc}
	 */
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
		PrintWriter out = null;
		try {
			out = new PrintWriter(file);
			for (Contact c : contactSet){
				out.println(c.getId() + "," + c.getName() + "," + c.getNotes() + ",");
			}
			out.println("PastMeetings");
			for (Meeting m : meetingsList){
				if (m instanceof PastMeeting){
					out.println(m.getId() + "," + ((PastMeeting) m).getNotes() + "," + dateToString(m.getDate()) + "," + contactSetToString(m.getContacts()));
				}
			}
			out.println("FutureMeetings");
			for (Meeting m : meetingsList){
				if (m instanceof FutureMeeting){
					out.println(m.getId() + "," + dateToString(m.getDate()) + "," + contactSetToString(m.getContacts()));
				}
			}
		} catch (FileNotFoundException ex) {
			System.out.println("Cannot write to file " + file + ".");
		} finally {
			out.close();
		}
	}
	
	/**
	 * Method that keeps the internal list of meetings in chronological order.
	 * 
	 * Implemented every time the internal list is changed.
	 */
	private void chronologicalReArrange(){
		Collections.sort(meetingsList, new Comparator<Meeting>() {
			public int compare(Meeting o1, Meeting o2) {
				return o1.getDate().compareTo(o2.getDate());
			}            
		}); 
	}
	
	
	/**
	 * Method to check for null arguments.
	 * 
	 * @param Objects an arbitrary number of objects
	 * @return NullPointerException any of the arguments is null
	 */
	private void checkIfArgumentsAreNull(Object... nullObjects){
		for (Object o : nullObjects){
			if (o == null) throw new NullPointerException("Null item found");
		}
	}
	
	/**
	 * Method for checking if a set of contacts is null, empty, or if any 
	 *  of the contacts do not exist.
	 * 
	 * @param Set the set of contacts to be checked
	 * @return Exceptions if a set of contacts is null, empty, or if any
	 *         of the contacts do not exist.
	 */
	private void checkContacts(Set<Contact> checkSet){
		if (checkSet == null) throw new NullPointerException("Contacts is null");
		if (!contactSet.containsAll(checkSet)) throw new IllegalArgumentException("One or more contacts do not exist");
		if (checkSet.isEmpty()) throw new IllegalArgumentException("Contacts list is empty");
	}
	
	 /**
	 * Method to convert existing future meetings into past meetings if their date is now in the past.
	 * 
	 * This method has been called for any method that checks the meetingsList for a particular meeting and the constructor.
	 */
	private void checkMeetingList(){
		for(Meeting m : meetingsList){
			if (m.getDate().before(Calendar.getInstance()) && m instanceof FutureMeeting) addMeetingNotes(m.getId(),"");
		}
		chronologicalReArrange();
	}
	
	/**
	 * Method for creating a useful string from a Calendar date,
	 * for the purpose of printing to a file
	 * @param Calendar 
	 * @return String of given date
	 */
	private String dateToString(Calendar c){
		String returnString = c.get(Calendar.YEAR) + "," + c.get(Calendar.MONTH) +
				"," + c.get(Calendar.DAY_OF_MONTH) + "," + c.get(Calendar.HOUR_OF_DAY) +
				"," + c.get(Calendar.MINUTE);
		return returnString;
	}
	
	/**
	 * Method for creating a string from a set of contacts,
	 * for use in printing to a file
	 * @param set
	 * @return a string of contacts
	 */
	private String contactSetToString(Set<Contact> set){
		String returnString = null;
		for(Contact c : set){
			if (returnString == null){
				returnString = c.getId() + "," + c.getName() + "," + c.getNotes();
			}else{
				returnString = "," + returnString + c.getId() + "," + c.getName() + "," + c.getNotes();
			}
		}
		return returnString;
	}
	
	/**
	 * Method for closing a reader,
	 * used for code readability.
	 * @param reader
	 */
	private void closeReader(Reader reader) { 
		try {
			if (reader != null) reader.close();
		} catch (IOException ex) {
	         ex.printStackTrace();
	    }
	}

}
