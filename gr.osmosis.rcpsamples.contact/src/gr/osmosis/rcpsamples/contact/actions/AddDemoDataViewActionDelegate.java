package gr.osmosis.rcpsamples.contact.actions;

import gr.osmosis.rcpsamples.contact.View;
import gr.osmosis.rcpsamples.contact.db.DAOFactory;
import gr.osmosis.rcpsamples.contact.model.Contact;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;

public class AddDemoDataViewActionDelegate implements IViewActionDelegate {

	private IViewPart view;
	
	public void init(IViewPart view) {
		// TODO Auto-generated method stub
		this.view = view;
	}

	public void run(IAction action) {
		// TODO Auto-generated method stub
		DAOFactory f = DAOFactory.getDAOFactory(DAOFactory.DERBY);
		f.getContactsDAO().dropTable();
		f.getContactsDAO().createTable();
		Contact c = new Contact("Stavros", "Kounis", "6973216110");
		f.getContactsDAO().createContact(c);
		
		((View) view).update();
	}

	public void selectionChanged(IAction action, ISelection selection) {
		// TODO Auto-generated method stub

	}

}
