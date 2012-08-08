package gr.osmosis.rcpsamples.contact.actions;

import gr.osmosis.rcpsamples.contact.ContactEditorInput;
import gr.osmosis.rcpsamples.contact.Editor;
import gr.osmosis.rcpsamples.contact.model.Contact;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.PartInitException;

public class NewContactViewActionDelegate implements IViewActionDelegate {

	private IViewPart view = null;
	
	public void init(IViewPart view) {
		this.view = view;
	}

	public void run(IAction action) {
		// TODO Auto-generated method stub
		
		if (view == null) return;
		
		Contact c = new Contact(true);
		ContactEditorInput input = new ContactEditorInput(c);
		
		try{
			view.getViewSite().getWorkbenchWindow().getActivePage().openEditor(input, Editor.ID);	
		}catch(PartInitException ex){
			ex.printStackTrace();
		}
				
	}

	public void selectionChanged(IAction action, ISelection selection) {
		// TODO Auto-generated method stub

	}

}
