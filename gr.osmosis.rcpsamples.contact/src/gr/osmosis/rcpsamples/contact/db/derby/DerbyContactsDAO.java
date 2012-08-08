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

import gr.osmosis.rcpsamples.contact.db.ContactsConstants;
import gr.osmosis.rcpsamples.contact.db.ContactsDAO;
import gr.osmosis.rcpsamples.contact.model.Contact;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;

/**
 * @author Stavros S. Kounis as root
 * 
 */
public class DerbyContactsDAO implements ContactsDAO {

	public DerbyContactsDAO() {

	}

	public boolean deleteContact(int id) {

		int rows = 0;

		try {
			StringBuffer sbDelete = new StringBuffer();

			sbDelete.append("DELETE FROM ");
			sbDelete.append(ContactsConstants.CONTACTS_TABLE_NAME);
			sbDelete.append(" WHERE ");
			sbDelete.append(ContactsConstants.CONTACTS_COL_ID + " = " + id);

			DataSource d = DerbyDAOFactory.getDataSource();
			QueryRunner run = new QueryRunner(d);

			rows = run.update(sbDelete.toString());

			if (rows != 1) {
				throw new SQLException("executeUpdate return value: " + rows);
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
			return false;
		}

		return true;

	}

	public Contact[] selectContacts(int id) {
		// Create Query
		// and where statement
		String whereStatement = "";
		if (id > -1) {
			whereStatement = " WHERE ID = " + id;
		}

		StringBuffer sbSelect = new StringBuffer();
		sbSelect.append("SELECT * ");
		sbSelect.append(" FROM ");
		sbSelect.append(ContactsConstants.CONTACTS_TABLE_NAME);
		sbSelect.append(whereStatement);

		// Create a QueryRunner that will use connections from
		// the given DataSource
		DataSource d = DerbyDAOFactory.getDataSource();
		QueryRunner run = new QueryRunner(d);

		ResultSetHandler h = new ResultSetHandler() {
			public Object handle(ResultSet rs) throws SQLException {

				BasicRowProcessor p = new BasicRowProcessor();

				List l = p.toBeanList(rs, Contact.class);

				return l;
			}
		};
		Object result;
		ArrayList list;
		Contact[] c = null;
		try {
			result = run.query(sbSelect.toString(), h);

			list = (ArrayList) result;

			c = new Contact[list.toArray().length];
			list.toArray(c);

			System.out.print(result.toString());

		} catch (SQLException sex) {
			sex.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return c;
	}

	public boolean updateContact(Contact contact) {
		if (contact == null) {
			throw new NullPointerException("contact parameter");
		}

		int rows = 0;

		try {
			StringBuffer sbUpdate = new StringBuffer();

			sbUpdate.append("UPDATE ");
			sbUpdate.append(ContactsConstants.CONTACTS_TABLE_NAME);
			sbUpdate.append(" SET ");
			sbUpdate.append(ContactsConstants.CONTACTS_COL_FNAME + " = '"
					+ contact.getFname() + "' , ");
			sbUpdate.append(ContactsConstants.CONTACTS_COL_LNAME + " = '"
					+ contact.getLname() + "' , ");
			sbUpdate.append(ContactsConstants.CONTACTS_COL_PHONE + " = '"
					+ contact.getPhone() + "' , ");
			sbUpdate.append(ContactsConstants.CONTACTS_COL_ADDRESS + " = '"
					+ contact.getAddress() + "' , ");
			sbUpdate.append(ContactsConstants.CONTACTS_COL_CITY + " = '"
					+ contact.getCity() + "' , ");
			sbUpdate.append(ContactsConstants.CONTACTS_COL_ZIP + " = '"
					+ contact.getZip() + "'  ");

			sbUpdate.append(" WHERE " + ContactsConstants.CONTACTS_COL_ID
					+ " = " + contact.getId());

			DataSource d = DerbyDAOFactory.getDataSource();
			QueryRunner run = new QueryRunner(d);

			rows = run.update(sbUpdate.toString());

			if (rows != 1) {
				throw new SQLException("executeUpdate return value: " + rows);
			}

		} catch (SQLException ex) {
			// throw new DAORuntimeException(ex);
			System.out.println(ex.getMessage());
			return false;
		}

		return true;

	}

	public boolean createContact(Contact contact) {
		if (contact == null) {
			throw new NullPointerException("contact parameter");
		}

		int rows = 0;

		try {
			StringBuffer sbInsert = new StringBuffer();

			sbInsert.append("INSERT INTO ");
			sbInsert.append(ContactsConstants.CONTACTS_TABLE_NAME);
			sbInsert.append("(");
			sbInsert.append(ContactsConstants.CONTACTS_COL_FNAME + ", ");
			sbInsert.append(ContactsConstants.CONTACTS_COL_LNAME + ", ");
			sbInsert.append(ContactsConstants.CONTACTS_COL_PHONE + ", ");
			sbInsert.append(ContactsConstants.CONTACTS_COL_ADDRESS + ", ");
			sbInsert.append(ContactsConstants.CONTACTS_COL_CITY + ", ");
			sbInsert.append(ContactsConstants.CONTACTS_COL_ZIP);
			sbInsert.append(")");
			sbInsert.append(" VALUES (");
			sbInsert.append(" '" + contact.getFname() + "' , ");
			sbInsert.append(" '" + contact.getLname() + "' , ");
			sbInsert.append(" '" + contact.getPhone() + "' , ");
			sbInsert.append(" '" + contact.getAddress() + "' , ");
			sbInsert.append(" '" + contact.getCity() + "' , ");
			sbInsert.append(" '" + contact.getZip() + "'  ");
			sbInsert.append(")");

			DataSource d = DerbyDAOFactory.getDataSource();
			QueryRunner run = new QueryRunner(d);

			rows = run.update(sbInsert.toString());

			if (rows != 1) {
				throw new SQLException("executeUpdate return value: " + rows);
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
			return false;
		}

		return true;

	}

	public boolean existContact(Contact contact) {
		Contact[] c;

		c = this.selectContacts(contact.getId());

		if (c == null) {
			return false;
		}

		if (c.length == 0) {
			return false;
		} else {
			return true;
		}

	}

	public int getMaxId() {

		StringBuffer sbSelect = new StringBuffer();
		sbSelect.append("SELECT MAX(ID) AS MAX_ID ");
		sbSelect.append(" FROM ");
		sbSelect.append(ContactsConstants.CONTACTS_TABLE_NAME);

		// Create a QueryRunner that will use connections from
		// the given DataSource
		DataSource d = DerbyDAOFactory.getDataSource();
		QueryRunner run = new QueryRunner(d);

		ResultSetHandler h = new ResultSetHandler() {
			public Object handle(ResultSet rs) throws SQLException {

				if (!rs.next()) {
					return null;
				}

				int max;

				max = rs.getInt("MAX_ID");

				Integer value = new Integer(max);

				return value;
			}
		};

		Object result = null;

		try {
			result = run.query(sbSelect.toString(), h);

		} catch (SQLException sex) {
			sex.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		int max;

		if (result != null) {
			max = ((Integer) result).intValue();
		} else {
			max = 0;
		}

		return max;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gr.osmosis.rcpsamples.contact.db.ContactsDAO#createTable()
	 */
	public boolean createTable() {

		try {
			StringBuffer sbCreate = new StringBuffer();

			sbCreate.append("CREATE TABLE APP.CONTACTS (");
			sbCreate
					.append("ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),");
			sbCreate.append("FNAME VARCHAR(30), ");
			sbCreate.append("LNAME VARCHAR(30),");
			sbCreate.append("PHONE VARCHAR(30),");
			sbCreate.append("ADDRESS VARCHAR(30),");
			sbCreate.append("CITY VARCHAR(30),");
			sbCreate.append("ZIP VARCHAR(30),");
			sbCreate.append("PRIMARY KEY(ID) )");

			DataSource d = DerbyDAOFactory.getDataSource();
			QueryRunner run = new QueryRunner(d);

			run.update(sbCreate.toString());

		} catch (SQLException ex) {
			ex.printStackTrace();
			return false;
		}

		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gr.osmosis.rcpsamples.contact.db.ContactsDAO#dropTable()
	 */
	public boolean dropTable() {

		try {
			StringBuffer sbDrop = new StringBuffer();

			sbDrop.append("DROP TABLE APP.CONTACTS");

			DataSource d = DerbyDAOFactory.getDataSource();
			QueryRunner run = new QueryRunner(d);

			run.update(sbDrop.toString());

		} catch (SQLException ex) {
			ex.printStackTrace();
			return false;
		}

		return true;
	}

}