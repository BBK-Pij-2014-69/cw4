package cw4;

public class ContactImpl implements Contact {
	private int ID;
	private String name;
	private String notes;

	public ContactImpl(int ID, String name){
		this.ID = ID;
		this.name = name;
	}
	
	@Override
	public int getId() {
		return ID;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getNotes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addNotes(String note) {
		// TODO Auto-generated method stub
	}

}
