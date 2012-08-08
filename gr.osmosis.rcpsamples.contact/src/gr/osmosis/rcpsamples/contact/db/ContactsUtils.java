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
package gr.osmosis.rcpsamples.contact.db;

import gr.osmosis.rcpsamples.contact.model.Contact;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

/**
 * @author root
 * 
 * for msaccess see
 * @link http://support.microsoft.com/kb/q209037/
 */
public class ContactsUtils {

    
    public static Collection makeContactObjectsFromResultSet(ResultSet rs) {

        Collection result = new java.util.ArrayList();

        Contact c;

        try {

            while (rs.next()) {
                c = new Contact();
                
                c.setId(rs.getInt("ContactID"));
                c.setFname(rs.getString("FName"));
                c.setLname(rs.getString("LName"));
                c.setAddress(rs.getString("Address"));
                c.setCity(rs.getString("City"));
                c.setZip(rs.getString("Zip"));
                c.setPhone(rs.getString("Phone"));
                
                result.add(c);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return result;
    }

    
    /**
     * 
     * @param rs
     * @return -1 on error
     */
    public static int getCountFromResultSet(ResultSet rs) {

        int count = -1;

        try {
            if (!rs.next()) {
                return -1;
            }
            count = rs.getInt(1);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {

        }

        return count;
    }
}