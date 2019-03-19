package restpackage;

public class PropertyDetail {

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getHome_id() {
		return home_id;
	}

	public void setHome_id(String home_Id) {
		this.home_id = home_Id;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public long getValue() {
		return value;
	}

	public void setValue(long value) {
		this.value = value;
	}

	Address address = new Address();
	String home_id;
	String owner;
	long value;
}

class Address {
	String city;
	String state;

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}
