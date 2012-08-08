/**
 * Copyright 2005 Osmosis networks and consulting and contributors.
 * 
 * This code use code fragments under the GNU Public License and is
 * licensed under the Apache License, Version 2.0 (the "License")
 * plus some restrictions/modifications desribed below;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package gr.osmosis.rcpsamples.contact.model;

/*
 * A simple POJO (Contact)
 */
public class Contact {
	private int id;
	private String fname = "";
	private String lname = "";
	private String phone = "";
	private String address = "";
	private String city = "";
	private String zip = "";
		
	public Contact(){
		
	}
	
	public Contact(int id){
		this.id = id;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public Contact(String fname, String lname, String phone){
		this.fname = fname;
		this.lname = lname;
		this.phone = phone;
	}	
	
	public Contact(boolean dummyInitialization){
		if(dummyInitialization){
			this.fname = "enter your first name ...";
			this.lname = "enter your last name ...";
			this.phone = "enter a phone number ...";
			this.address = "enter an address ...";
			this.city = "enter a city ...";
			this.zip = "enter a ZIP code ...";
		}
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public String getLname() {
		return lname;
	}
	public void setLname(String lname) {
		this.lname = lname;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}

	public String toString() {
		return this.lname + " " + this.fname;
	}
			
}
