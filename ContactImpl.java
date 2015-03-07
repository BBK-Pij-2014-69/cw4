package cw4;

public class ContactImpl implements Contact {
	private int ID;
	private String name;
	private String notes;

	public ContactImpl(int ID, String name){
		this.ID = ID;
		this.name = name;
		notes = "";
	}
	
	@Override
	public int getId() {
		return ID;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getNotes() {
		return notes;
	}

	@Override
	public void addNotes(String note) {
		if (getNotes() == ""){
			notes = note;
		}else{
			notes = notes + ", " + note;
		}
	}

	@Override
	public boolean equals(Object o){
		if (o == this) return false;
		if (!(o instanceof ContactImpl)) return false;
		ContactImpl c = (ContactImpl)o;
		if (this.getId() == c.getId()){
			return true;
		}else{
			return false;
		}
	}
	
	@Override
    public int hashCode() {
		int hash = 5;
		hash = 83 * hash + ID;
		return hash;
	}
}
