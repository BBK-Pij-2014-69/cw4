package cw4;


/**
 * A contact is a person we are making business with or may do in the future. 
 * Contacts have an ID (unique), a name (probably unique, but maybe not), and notes that the user may want to save about them.
 * Please Note that the uniqueness of the id of the contacts is controlled by ContactMangerImpl
 * 
 * @author Kieren Millar
 *
 */
public class ContactImpl implements Contact{
	
	private int ID;//uniqueness not controlled here
	private String name;
	private String notes;

	/**
	 * Constructor using just an id and a name.
	 * 
	 * @param ID
	 * @param name
	 */
	public ContactImpl(int id, String name){
		this.ID = id;
		this.name = name;
		notes = "";
	}
	
	/**
	 * Constructor that also accepts notes as a parameter.
	 * 
	 * @param id
	 * @param name
	 * @param notes
	 */
	public ContactImpl(int id, String name, String notes) {
		this.ID = id;
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

	/**
	 * Overridden equals method for comparing two contacts,
	 * only returns true if the id, name, and notes are equal.
	 * 
	 */
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
	
	/**
	 * Overridden hashcode method using id, name and notes as parameters
	 * to produce the hashcode. 
	 */
	@Override
    public int hashCode() {
		int hash = 5;
		hash = 83 * hash + ID;
		hash = 83 * hash + name.hashCode();
		hash = 83 * hash + notes.hashCode();
		return hash;
	}
}
