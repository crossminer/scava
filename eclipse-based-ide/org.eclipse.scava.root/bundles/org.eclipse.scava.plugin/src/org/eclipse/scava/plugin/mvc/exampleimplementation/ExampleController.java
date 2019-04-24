/*********************************************************************
* Copyright c 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse PublicLicense 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.mvc.exampleimplementation;

import java.util.Random;

import org.eclipse.scava.plugin.mvc.controller.Controller;
import org.eclipse.scava.plugin.mvc.controller.ModelViewController;
import org.eclipse.scava.plugin.mvc.event.routed.IRoutedEvent;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

public class ExampleController extends ModelViewController<ExampleModel, IExampleView>
		implements IExampleViewEventListener {

	public ExampleController(Controller parent, ExampleModel model, IExampleView view) {
		super(parent, model, view);
	}

	@Override
	public void onTimeRequest() {
		String currentTime = getModel().getCurrentTime();
		getView().Show("The current time is " + currentTime);
	}

	@Override
	public void onProcessTextRequest(String text) {
		getView().Show(text.toLowerCase() + " < " + text.toUpperCase());
	}

	@Override
	public void requestViewClose() {
		dispose();
	}

	@Override
	public void onNewViewPartView() {
		try {
			ExampleModel model = new ExampleModel();
			IExampleView view = ExampleViewPartView.open(ExampleViewPartView.ID, "Part_" + (new Random().nextLong()));
			ExampleController controller = new ExampleController(this, model, view);
			controller.init();
		} catch (PartInitException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onNewTitleAreaDialogView() {
		ExampleModel model = new ExampleModel();
		IExampleView view = new ExampleTitleAreaDialogView(
				PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell());
		ExampleController controller = new ExampleController(this, model, view);
		controller.init();
	}

	@Override
	public void onRaiseEventToParentRequest() {
		routeEventToParentController(new RandomNumberEvent(this));
	}

	@Override
	public void onRaiseEventToSubControllersRequest() {
		routeEventToSubControllers(new RandomNumberEvent(this));
	}

	@Override
	protected void onReceiveRoutedEventFromSubController(IRoutedEvent event, Controller forwarderController) {
		if (event instanceof RandomNumberEvent) {
			RandomNumberEvent randomNumberEvent = (RandomNumberEvent) event;
			getView().Show("Event received from Sub: " + randomNumberEvent.getNumber());
			if (randomNumberEvent.getNumber() % 2 != 0) {
				return;
			}
		}

		super.onReceiveRoutedEventFromSubController(event, forwarderController);
	}

	@Override
	protected void onReceiveRoutedEventFromParentController(IRoutedEvent event) {
		if (event instanceof RandomNumberEvent) {
			RandomNumberEvent randomNumberEvent = (RandomNumberEvent) event;
			getView().Show("Event received from Parent: " + randomNumberEvent.getNumber());
			if (randomNumberEvent.getNumber() % 2 != 0) {
				return;
			}
		}

		super.onReceiveRoutedEventFromParentController(event);
	}

}
