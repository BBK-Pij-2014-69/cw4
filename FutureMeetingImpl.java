package cw4;

import java.util.Calendar;
import java.util.Set;

/**
 * 
 * @see FutureMeeting
 * @author Kieren Millar
 *
 */
public class FutureMeetingImpl implements FutureMeeting{
	
	private int id;//id uniqueness is not controlled in this class, but by ContractManager
	private Calendar date;
	private Set<Contact> contacts;
	
	/**
	 * Constructor for this meeting.
	 * @param id
	 * @param date
	 * @param contacts
	 */
	public FutureMeetingImpl(int id, Calendar date, Set<Contact> contacts) {
		this.id = id;
		this.date = date;
		this.contacts = contacts;
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

}
