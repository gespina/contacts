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

//import gr.osmosis.rcpsamples.contact.db.DAOFactory;
import gr.osmosis.rcpsamples.contact.db.DAOFactory;
import gr.osmosis.rcpsamples.contact.model.Contact;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPartConstants;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.forms.events.HyperlinkAdapter;
import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ImageHyperlink;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapData;
import org.eclipse.ui.forms.widgets.TableWrapLayout;
import org.eclipse.ui.part.EditorPart;

public class Editor extends EditorPart {

	public static String ID = "gr.osmosis.rcpsamples.contact.editor";

	private Contact contact;

	private FormToolkit toolkit;

	private Form form;

	private Text fnameText;

	private Text lnameText;

	private Text phoneText;

	private Text addressText;

	private Text cityText;

	private Text zipText;

	private TextKeyListener textKeyListener = new TextKeyListener();

	private boolean isDirty = false;

	public Editor() {
		super();
	}

	public void doSave(IProgressMonitor monitor) {

		this.updatesContact();
		
		DAOFactory f = DAOFactory.getDAOFactory(DAOFactory.DERBY);
		
		boolean exist;
		
		exist = f.getContactsDAO().existContact(this.contact);

		if (exist){
			f.getContactsDAO().updateContact(this.contact);
		}else{
			f.getContactsDAO().createContact(this.contact);
			int max = f.getContactsDAO().getMaxId();
			this.contact.setId(max);
		}
							
		MessageDialog.openInformation(this.getSite().getShell(), "Save",
				"Contact saved.");
		
		setDirty(false);
		
		IViewPart v = null;
		
		v = this.getSite().getPage().findView("gr.osmosis.rcpsamples.contact.view");
		
		if(v!=null){
			((View) v).update();
		}
	}

	public void doSaveAs() {

	}

	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
		// TODO Auto-generated method stub

		if (!(input instanceof IEditorInput))
			throw new PartInitException(
					"Invalid Input: Must be IFileEditorInput");

		if (input.getAdapter(Contact.class) != null) {
			contact = (Contact) input.getAdapter(Contact.class);
		} else {
			contact = null;
		}

		setSite(site);
		setInput(input);
	}

	public boolean isDirty() {
		return this.isDirty;
	}

	public boolean isSaveAsAllowed() {
		return false;
	}

	public void createPartControl(Composite parent) {

	
		toolkit = new FormToolkit(parent.getDisplay());
		
		form = toolkit.createForm(parent);
		form.setText("Contact form");
		/*
		 * 4 columns TableWrapLayout
		 */
		TableWrapLayout layout = new TableWrapLayout();
		layout.numColumns = 4;
		
		form.getBody().setLayout(layout);

		TableWrapData td;
		
		/*
		 * First Name label
		 * colspan = 2
		 */
		td = new TableWrapData();
		td.colspan = 2;
		Label label = toolkit.createLabel(form.getBody(), "First name:");
		label.setLayoutData(td);
		
		/*
		 * Last Name label
		 * colspan = 2
		 */
		td = new TableWrapData();
		td.colspan = 2;
		label = toolkit.createLabel(form.getBody(), "Last name:");
		label.setLayoutData(td);

		/*
		 * First Name text
		 * 
		 * style: FILL_GRAB
		 * colspan = 2
		 */
		td = new TableWrapData(TableWrapData.FILL_GRAB);
		td.colspan = 2;
		fnameText = toolkit.createText(form.getBody(), "Enter fname..",
				SWT.BORDER);
		fnameText.setLayoutData(td);
		fnameText.addKeyListener(textKeyListener);

		/*
		 * Last Name text
		 * 
		 * style: FILL_GRAB
		 * colspan = 2
		 */
		td = new TableWrapData(TableWrapData.FILL_GRAB);
		td.colspan = 2;
		lnameText = toolkit.createText(form.getBody(), "Enter lname..",
				SWT.BORDER);
		lnameText.setLayoutData(td);
		lnameText.addKeyListener(textKeyListener);

		/*
		 * Phones Section
		 * Expandable / Collapsible section
		 *
		 * style : TITLE_BAR
		 * layout style: FILL
		 * colspan = 2
		 * 
		 */
		Section section = toolkit.createSection(form.getBody(),
				Section.DESCRIPTION | Section.TITLE_BAR | Section.TWISTIE | Section.EXPANDED);
		
		td = new TableWrapData(TableWrapData.FILL);
		td.colspan = 2;
		section.setLayoutData(td);

		section.setText("Phones");
		section.setDescription("Phone numbers.");

		Composite sectionClient = toolkit.createComposite(section);
		sectionClient.setLayout(new GridLayout(2, false));

		/*
		 * Phone label
		 */
		GridData gd = new GridData(GridData.GRAB_HORIZONTAL
				| GridData.FILL_HORIZONTAL);
		label = toolkit.createLabel(sectionClient, "Phone number:");
		/*
		 * Phone text
		 */
		phoneText = toolkit.createText(sectionClient, "Enter phone..",
				SWT.BORDER);
		phoneText.setLayoutData(gd);
		phoneText.addKeyListener(textKeyListener);

		section.setClient(sectionClient);

		/*
		 * Address Section
		 * Expandable / Collapsible section
		 *
		 * style : TITLE_BAR
		 * layout style: FILL
		 * colspan = 2
		 * 
		 */
		section = toolkit.createSection(form.getBody(), Section.DESCRIPTION
				| Section.TWISTIE | Section.TITLE_BAR | Section.EXPANDED);
		td = new TableWrapData(TableWrapData.FILL);
		td.colspan = 2;
		section.setLayoutData(td);
		
		section.setText("Address");
		section.setDescription("Address information.");

		sectionClient = toolkit.createComposite(section);
		sectionClient.setLayout(new GridLayout(2, false));

		/*
		 * Address label
		 */
		gd = new GridData(GridData.FILL_HORIZONTAL);
		label = toolkit.createLabel(sectionClient, "Address:");
		/*
		 * Address text
		 */
		addressText = toolkit.createText(sectionClient, "Enter address..",
				SWT.BORDER);
		addressText.setLayoutData(gd);
		addressText.addKeyListener(textKeyListener);

		/*
		 * City label
		 */
		gd = new GridData(GridData.FILL_HORIZONTAL);
		label = toolkit.createLabel(sectionClient, "City:");
		/*
		 * City text
		 */
		cityText = toolkit.createText(sectionClient, "Enter city..", SWT.BORDER
				| SWT.FILL);
		cityText.setLayoutData(gd);
		cityText.addKeyListener(textKeyListener);

		/*
		 * ZIP label
		 */
		gd = new GridData(GridData.FILL_HORIZONTAL);
		label = toolkit.createLabel(sectionClient, "Zip Code:");
		/*
		 * ZIP text
		 */
		zipText = toolkit.createText(sectionClient, "Enter zip..", SWT.BORDER);
		zipText.setLayoutData(gd);
		zipText.addKeyListener(textKeyListener);

		section.setClient(sectionClient);

		/*
		 * Actions Section
		 * NO Expandable / Collapsible section
		 *
		 * layout style: FILL
		 * colspan = 4
		 * 
		 */
		section = toolkit.createSection(form.getBody(), Section.COMPACT);
		td = new TableWrapData(TableWrapData.FILL);
		td.colspan = 4;
		section.setLayoutData(td);

		section.setText("Actions");
		toolkit.createCompositeSeparator(section);
		section.setDescription("Save changes.");

		sectionClient = toolkit.createComposite(section);
		sectionClient.setLayout(new GridLayout(1, false));
		
		/*
		 * Save changes link
		 */
		ImageHyperlink savelink = toolkit.createImageHyperlink(sectionClient,
				SWT.WRAP);
		savelink.addHyperlinkListener(new HyperlinkAdapter() {

			public void linkActivated(org.eclipse.ui.forms.events.HyperlinkEvent e) {
				super.linkActivated(e);
				System.out.println("Link activated!");
				doSave(null);
			}
		});
		
		ImageHyperlink saveAsNewLink = toolkit.createImageHyperlink(sectionClient,
				SWT.WRAP);
		saveAsNewLink.addHyperlinkListener(new HyperlinkAdapter() {

			public void linkActivated(org.eclipse.ui.forms.events.HyperlinkEvent e) {
				super.linkActivated(e);
				System.out.println("Save as new link activated!");
//				doSave(null);
			}
		});		
		
		savelink.setText("Save changes...");
		Image img = null;		
		
		img =	ContactPlugin
				.getImageDescriptor("icons/org.eclipse.ui/icons/full/etool16/save_edit.gif")
				.createImage();
		
		savelink.setImage(img);
		
		saveAsNewLink.setText("Save as new ...");
		img = null;	
//		
//		descriptor = ContactPlugin.getDefault().getImageDescriptor("icons/org.eclipse.ui/icons/full/etool16/saveas_edit.gif");
//		img = descriptor.createImage(null);
		
		img =	ContactPlugin
				.getImageDescriptor("icons/org.eclipse.ui/icons/full/etool16/saveas_edit.gif")
				.createImage();
		
		saveAsNewLink.setImage(img);
		
		section.setClient(sectionClient);

		/*
		 * we have create the form, so its time to populate it
		 * with data from our POJO (Contact).
		 */
		populateForm();
		
		updatePartName();

	}

	public void setFocus() {
		// TODO Auto-generated method stub

	}

	protected void setPartName(String partName) {
		super.setPartName(partName);
	}

	private void populateForm() {

		this.fnameText.setText(contact.getFname());
		this.lnameText.setText(contact.getLname());
		this.phoneText.setText(contact.getPhone());
		this.addressText.setText(contact.getAddress());
		this.cityText.setText(contact.getCity());
		this.zipText.setText(contact.getZip());

	}
	
	private void updatesContact(){
		contact.setFname(this.fnameText.getText());
		contact.setLname(this.lnameText.getText());
		contact.setPhone(this.phoneText.getText());
		contact.setAddress(this.addressText.getText());
		contact.setCity(this.cityText.getText());
		contact.setZip(this.zipText.getText());
		
		updatePartName();
	}

	private void setDirty(boolean dirty) {
		this.isDirty = dirty;
		/* force platform to re-check if editor is dirty or not
		 * to remove (*) from editors TAB
		 */
		firePropertyChange(IWorkbenchPartConstants.PROP_DIRTY);
	}

	private class TextKeyListener implements KeyListener {
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub

		}

		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			System.out.println(e.widget.toString() + " character: "
					+ e.character);

			/* a key has pressed (and released) in one of Texts in
			 * our form. we have to change editor's state to dirty
			 * and force platform to re-check if editor is dirty or 
			 * not to add an (*) in editors TAB
			 */
			setDirty(true);
			
		}
	}
	
	/*
	 * Update editor's tab caption
	 */
	private void updatePartName(){
		/*
		 * Change editor's TAB Caption to
		 * show fname lname.
		 */
		setPartName(contact.toString());
	}

}
