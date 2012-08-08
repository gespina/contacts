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
package gr.osmosis.rcpsamples.contact.db.derby;

import gr.osmosis.rcpsamples.contact.db.ContactsDAO;
import gr.osmosis.rcpsamples.contact.db.DAOFactory;

import java.sql.Connection;
import java.sql.DriverManager;

import javax.sql.DataSource;

import org.apache.derby.jdbc.EmbeddedDataSource;
;

/**
 * @author root
 *
 */
public class DerbyDAOFactory extends DAOFactory{

	/*
	 * DERBY connection string example:
	 * 
	 * Open database named "contactdb" from c:\tmp
	 * (c:\tmp\contactdb\) where user name is "root"
	 * and passwd _blank_ (there is NO passwd).
	 * If database does not exist, create it.
	 * 
	 * "jdbc:derby:c:\\tmp\\contactdb;create=true;user=root";
	 */
  	
	public static final String DRIVER=
	      "org.apache.derby.jdbc.EmbeddedDriver";
	
	public static final String DBNAME = 
		"contactdb;create=true;user=root";
	public static final String DBURL=
		"jdbc:derby:" + DBNAME;

	
	private static DataSource ds = null;
	

	public static Connection getConnection() {
		try {
			
          Connection conn = null;

          // load database driver class
	        Class.forName(DRIVER);
			
	        conn = DriverManager.getConnection(DBURL);
	        conn.setAutoCommit(true);

          return conn;
          
      } catch (Exception ex) {
      	
          System.out.println("exception thrown:" + ex.getMessage());
          
		}
		
      return null;

	}
	
	public static DataSource getDataSource(){
		if (ds != null){
			return ds;
		}
			
		String jdbcDataSource = "org.apache.derby.jdbc.EmbeddedDataSource";
		try{
			Class nsDataSource = Class.forName(jdbcDataSource);
			
			ds = (DataSource) nsDataSource.newInstance();	
			((EmbeddedDataSource) ds).setDatabaseName(DBNAME);
			
		}catch(Exception ex){
			ex.printStackTrace();
		}

		return ds;
	}

	
	public ContactsDAO getContactsDAO() {
		return new DerbyContactsDAO();
	}

}
