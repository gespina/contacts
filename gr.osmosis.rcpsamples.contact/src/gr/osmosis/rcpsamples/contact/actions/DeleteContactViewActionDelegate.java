package gr.osmosis.rcpsamples.contact.actions;

import gr.osmosis.rcpsamples.contact.View;
import gr.osmosis.rcpsamples.contact.db.DAOFactory;
import gr.osmosis.rcpsamples.contact.model.Contact;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;

public class DeleteContactViewActionDelegate implements IViewActionDelegate {
	private IViewPart view;
	
	public void init(IViewPart view) {
		this.view = view;		
	}

	public void run(IAction action) {
		Contact c = null;
		
		c = ((View) view).getSelection();
		
		try{
			DAOFactory.getDAOFactory(DAOFactory.DERBY).getContactsDAO().deleteContact(c.getId());
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		((View) view).update();
				
	}

	public void selectionChanged(IAction action, ISelection selection) {

	}

}
