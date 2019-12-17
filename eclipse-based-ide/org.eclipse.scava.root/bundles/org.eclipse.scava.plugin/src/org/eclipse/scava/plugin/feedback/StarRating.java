/*********************************************************************
* Copyright c 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse PublicLicense 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.feedback;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

public class StarRating extends Composite {
	private Image image;
	private int hoverSelected = 0;
	private int selected = 0;
	private int nrOfImages = 5;
	private int alpha = 150;
	private int width;
	private int height;
	private boolean vertical = false;

	public StarRating(Composite parent, int style) {
		super(parent, style);

		/* Add dispose listener for the image */
		addListener(SWT.Dispose, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				if (image != null)
					image.dispose();
			}

		});

		/* Add custom paint listener that paints the stars */
		addListener(SWT.Paint, new Listener() {
			@Override
			public void handleEvent(Event e) {
				paintControl(e);
			}
		});

		/*
		 * Keep track of the mouse movements and highlight possible new selection
		 */
		addListener(SWT.MouseMove, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				int x = arg0.x;
				int y = arg0.y;

				/* Determine direction */
				int step = (vertical ? height : width) + 1;
				int location = vertical ? y : x;

				/* Determine current index */
				int current = (location / step);

				/* Redraw if necessary */
				if (current != hoverSelected) {
					hoverSelected = current;
					redraw();
				}
			}
		});

		/* On mouse exit, reset selection */
		addListener(SWT.MouseExit, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				hoverSelected = selected;
				redraw();
			}
		});

		/* On mouse up, set new selection based on hover selection */
		addListener(SWT.MouseUp, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				selected = hoverSelected;
			}
		});
	}

	private void paintControl(Event event) {
		GC gc = event.gc;

		if (image != null) {
			int stepX = vertical ? 0 : width + 1;
			int stepY = vertical ? height + 1 : 0;

			for (int i = 0; i < nrOfImages; i++) {
				if (i == hoverSelected + 1)
					gc.setAlpha(alpha);

				gc.drawImage(image, 1 + stepX * i, 1 + stepY * i);
			}

			/* Reset alpha value */
			gc.setAlpha(255);
		}
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = new Image(Display.getDefault(), image, SWT.IMAGE_COPY);
		width = image.getBounds().width;
		height = image.getBounds().height;
		redraw();
	}

	public void setNrOfStars(int nrOfStars) {
		if (nrOfStars < 1)
			throw new IllegalArgumentException(
					"Invalid value for number of stars. Minimum: 1, Selection: " + nrOfStars);
		else
			nrOfImages = nrOfStars;
	}

	public int getNrOfStars() {
		return nrOfImages;
	}

	public int getSelection() {
		return selected + 1;
	}

	public void setSelection(int selection) {
		if (selection < 0 || selection > nrOfImages)
			throw new IllegalArgumentException("Invalid value for star selection. Minimum: 0, Maximum: " + nrOfImages
					+ ", Selection: " + selection);
		else
			selected = selection - 1;

		hoverSelected = selected;
	}

	public void setAlpha(int alpha) {

		if (alpha < 0 || alpha > 255)
			throw new IllegalArgumentException("Invalid alpha value. Minimum: 0, Maximum: 255, Selection: " + alpha);
		else
			this.alpha = alpha;
	}

	public int getAlpha() {
		return alpha;
	}

	public void setVertical(boolean vertical) {
		this.vertical = vertical;
	}

	public boolean getVertical() {
		return vertical;
	}

	@Override
	public Point computeSize(int wHint, int hHint, boolean changed) {
		int overallWidth = 0;
		int overallHeight = 0;

		/* Determine the preferred dimensions of the widget */
		if (image != null) {
			overallWidth = vertical ? width : width * nrOfImages + nrOfImages - 1;
			overallHeight = vertical ? height * nrOfImages + nrOfImages - 1 : height;
		}

		/* Consider hints */
		if (wHint != SWT.DEFAULT && wHint < overallWidth)
			overallWidth = wHint;

		if (hHint != SWT.DEFAULT && hHint < overallHeight)
			overallHeight = hHint;

		/* Return computed dimensions plus border */
		return new Point(overallWidth + 2, overallHeight + 2);
	}

}
