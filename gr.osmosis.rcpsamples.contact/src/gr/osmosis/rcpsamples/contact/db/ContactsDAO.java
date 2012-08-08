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
 */
package gr.osmosis.rcpsamples.contact.db;

import gr.osmosis.rcpsamples.contact.model.Contact;

/**
 * 
 * Contacts Data Access Object (DAO)
 * 
 * <a
 * href="http://java.sun.com/blueprints/corej2eepatterns/Patterns/DataAccessObject.html">
 * DAO pattern </a>
 * 
 * @author Stavros S. Kounis as root
 *  
 */
public interface ContactsDAO {
	
	public static int SELECT_ALL = -1;

    /***************************************************************************
     * 
     * @param id
     *            0 for select all
     * @return a non-null object
     *  
     */
    public Contact[] selectContacts(int id);

    /***************************************************************************
     * @param id
     */
    public boolean deleteContact(int id);

    /***************************************************************************
     * @param customer
     *            must be non-null
     * @return true or false
     */
    public boolean createContact(Contact contact);

    /***************************************************************************
     * @param customer
     *            must be non-null
     * @return true or false
     */
    public boolean updateContact(Contact contact);
    
    /***************************************************************************
     * @param customer
     *            must be non-null
     * @return true or false
     */
    public boolean existContact(Contact contact);    
    
    public int getMaxId();
    
    public boolean dropTable();
    
    public boolean createTable();
    
}