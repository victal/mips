package registradores;

public class Reg {

	private boolean dirty;
	private int id;
	private Integer value;

	public Reg(int id) {
		this.id = id;
	}

	public void setDirty() {
		this.dirty = true;
		
	}

	public boolean isDirty() {
		return this.dirty;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public Integer getValue() {
		return this.value;
	}

	public void unsetDirty() {
		this.dirty = false;
	}
	
	

}
