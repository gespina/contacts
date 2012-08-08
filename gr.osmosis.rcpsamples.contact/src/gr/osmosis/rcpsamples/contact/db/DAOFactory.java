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

import gr.osmosis.rcpsamples.contact.db.derby.DerbyDAOFactory;

/**
 * @author Stavros S. Kounis
 * 
 */
public abstract class DAOFactory {

    public static final int DERBY = 1;

    public DAOFactory() {

    }

    /**
     * @param whichFactory DAOFactory.ODBC
     * @return DAOFacotry
     */
    public static DAOFactory getDAOFactory(int whichFactory) {

        switch (whichFactory) {
        case DERBY:
            return new DerbyDAOFactory();
        default:
            return null;
        }

    }


    public abstract ContactsDAO getContactsDAO();
    
}