package cw4;

import java.util.Calendar;
import java.util.Set;

/**
 * 
 * @see PastMeeting
 * @author Kieren Millar
 *
 */
public class PastMeetingImpl implements PastMeeting{

	private int id;//id uniqueness is not controlled in this class, but by ContractManager
	private Calendar date;
	private Set<Contact> contacts;
	private String notes;
	
	/**
	 * Constructor for this meeting.
	 * @param id
	 * @param date
	 * @param contacts
	 * @param notes
	 */
	public PastMeetingImpl(int id, Calendar date, Set<Contact> contacts, String notes) {
		this.id = id;
		this.date = date;
		this.contacts = contacts;
		this.notes = notes;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getId() {
		return id;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Calendar getDate() {
		return date;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<Contact> getContacts() {
		return contacts;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getNotes() {
		return notes;
	}

}
