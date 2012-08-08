package gr.osmosis.rcpsamples.contact.actions;

import gr.osmosis.rcpsamples.contact.View;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;

public class RefreshContactsViewActionDelegate implements IViewActionDelegate {

	private IViewPart view;
	
	public void init(IViewPart view) {
		this.view=view;
		
	}

	public void run(IAction action) {

		if (this.view!=null){
			((View) this.view).update();
		}
	}

	public void selectionChanged(IAction action, ISelection selection) {
		// TODO Auto-generated method stub

	}

}
