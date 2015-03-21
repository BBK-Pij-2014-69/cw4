package cw4;

import java.io.Serializable;

/**
 * A contact is a person we are making business with or may do in the future. 
 * Contacts have an ID (unique), a name (probably unique, but maybe not), and notes that the user may want to save about them.
 * Please Note that the uniqueness of the id of the contacts is controlled by ContactMangerImpl
 * 
 * @author Kieren Millar
 *
 */
public class ContactImpl implements Contact, Serializable {
	
	private static final long serialVersionUID = -9114818729002509790L;
	private int ID;
	private String name;
	private String notes;

	public ContactImpl(int ID, String name){
		this.ID = ID;
		this.name = name;
		notes = "";
	}
	
	public ContactImpl(int i, String name, String notes) {
		this.ID = i;
		this.name = name;
		this.notes = notes;
	}

	/** 
	 * {@inheritDoc} 
	 */
	@Override
	public int getId() {
		return ID;
	}

	/** 
	 * {@inheritDoc} 
	 */
	@Override
	public String getName() {
		return name;
	}

	/** 
	 * {@inheritDoc} 
	 */
	@Override
	public String getNotes() {
		return notes;
	}

	/** 
	 * {@inheritDoc} 
	 */
	@Override
	public void addNotes(String note) {
		notes = note;
	}

	@Override
	public boolean equals(Object o){
		if (o == this) return false;
		if (!(o instanceof ContactImpl)) return false;
		ContactImpl c = (ContactImpl)o;
		if (this.getId() == c.getId() && this.getName().equals(c.getName()) && this.getNotes().equals(c.getNotes())){
			return true;
		}else{
			return false;
		}
	}
	
	@Override
    public int hashCode() {
		int hash = 5;
		hash = 83 * hash + ID;
		hash = 83 * hash + name.hashCode();
		hash = 83 * hash + notes.hashCode();
		return hash;
	}
}
