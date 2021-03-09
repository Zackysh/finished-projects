package edu.ncsu.monopoly;

public abstract class Cell implements IOwnable {
	private String Nombre;
	protected Player owner;
	private boolean available = true;

	public String getName() {
		return Nombre;
	}

	@Override
	public Player getOwner() {
		return owner;
	}
	
	public int getPrice() {
		return 0;
	}

	public abstract void playAction();

	void setName(String name) {
		this.Nombre = name;
	}

	@Override
	public void setOwner(Player owner) {
		this.owner = owner;
	}
    
    public String toString() {
        return Nombre;
    }

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}
}
