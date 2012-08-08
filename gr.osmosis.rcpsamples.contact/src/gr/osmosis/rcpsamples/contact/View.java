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
package gr.osmosis.rcpsamples.contact;

import gr.osmosis.rcpsamples.contact.db.ContactsDAO;
import gr.osmosis.rcpsamples.contact.db.DAOFactory;
import gr.osmosis.rcpsamples.contact.model.Contact;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.part.ViewPart;

public class View extends ViewPart {
	public static final String ID = "gr.osmosis.rcpsamples.contact.view";

	private TableViewer viewer;

	/**
	 * The content provider class is responsible for providing objects to the
	 * view. It can wrap existing objects in adapters or simply return objects
	 * as-is. These objects may be sensitive to the current input of the view,
	 * or ignore it and always show the same content (like Task List, for
	 * example).
	 */
	class ViewContentProvider implements IStructuredContentProvider {
		public void inputChanged(Viewer v, Object oldInput, Object newInput) {
		}

		public void dispose() {
		}

		public Object[] getElements(Object parent) {
			DAOFactory f = DAOFactory.getDAOFactory(DAOFactory.DERBY);
			Contact[] c;
						
			c = f.getContactsDAO().selectContacts(ContactsDAO.SELECT_ALL);
			if (c!=null){
				return c;
			}else{
				return new Contact[] {};
			}
//			return new String[] { "One", "Two", "Three" };
		}
	}

	class ViewLabelProvider extends LabelProvider implements
			ITableLabelProvider {
		public String getColumnText(Object obj, int index) {
			return getText(obj);
		}

		public Image getColumnImage(Object obj, int index) {
			return getImage(obj);
		}

		public Image getImage(Object obj) {
			Image img = null;
					
			ImageDescriptor descriptor = ContactPlugin.getImageDescriptor("icons/com.linuxcult/crystal/16x16/apps/gif/personal.gif");
			img = descriptor.createImage(null);
			
//			try{
//				img = new Image(null,"kuser.gif");
//				System.out.print(img.toString());
//			}catch(Exception ex){
//				ex.printStackTrace();
//			}
			return img;
//			return PlatformUI.getWorkbench().getSharedImages().getImage(
//					ISharedImages.IMG_OBJ_ELEMENT);
		}
	}

	/**
	 * This is a callback that will allow us to create the viewer and initialize
	 * it.
	 */
	public void createPartControl(Composite parent) {
		
		viewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL
				| SWT.V_SCROLL);
		viewer.setContentProvider(new ViewContentProvider());
		viewer.setLabelProvider(new ViewLabelProvider());
		viewer.setInput(getViewSite());
		viewer.addDoubleClickListener(new IDoubleClickListener(){
			
			public void doubleClick(org.eclipse.jface.viewers.DoubleClickEvent event) {
				
				System.out.println("Double click on:" + event.getSelection().toString());

				try{	
					Contact c = null;
					try {
					
						c = (Contact) ((StructuredSelection)event.getSelection()).getFirstElement();
					}catch (Exception e){
						e.printStackTrace();
						c = null;
					}
					IEditorInput input = new ContactEditorInput(c);
										
					getViewSite().getWorkbenchWindow().getActivePage().openEditor(input, Editor.ID);
					
				}catch (Throwable e){
					e.printStackTrace();
				}

			};
		});
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		viewer.getControl().setFocus();
	}
	
	public void update(){
		viewer.setInput(getViewSite());
	}
	
	public Contact getSelection(){
		
		StructuredSelection sel = (StructuredSelection) this.viewer.getSelection();	
        
		if (sel.getFirstElement() == null){
            return null;
        }
        
        if ( sel.getFirstElement() instanceof Contact){
            return  (Contact) sel.getFirstElement();
        }else{
            return null;
        }   
        
	}
}