package crossflow.diagram.edit.parts;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.GridData;
import org.eclipse.draw2d.GridLayout;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.Shape;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gmf.runtime.draw2d.ui.figures.WrappingLabel;
import org.eclipse.gmf.runtime.draw2d.ui.mapmode.IMapMode;

/**
 * @generated NOT
 */
public class StreamFigure extends Shape {

	private WrappingLabel fFigureTopicLabelFigure;
	private boolean isTopic;

	public StreamFigure(IMapMode mapMode, boolean isTopic) {

		this.isTopic = isTopic;

		GridLayout layoutThis = new GridLayout();
		layoutThis.numColumns = 1;
		layoutThis.makeColumnsEqualWidth = true;
		this.setLayoutManager(layoutThis);

		this.setBorder(new MarginBorder(mapMode.DPtoLP(5), mapMode.DPtoLP(5), mapMode.DPtoLP(5), mapMode.DPtoLP(5)));
		createContents();
	}

	private void createContents() {

		fFigureTopicLabelFigure = new WrappingLabel();

		fFigureTopicLabelFigure.setText(isTopic ? "Topic" : "Queue");

		GridData constraintFFigureTopicLabelFigure = new GridData();
		constraintFFigureTopicLabelFigure.verticalAlignment = GridData.CENTER;
		constraintFFigureTopicLabelFigure.horizontalAlignment = GridData.CENTER;
		constraintFFigureTopicLabelFigure.horizontalIndent = 0;
		constraintFFigureTopicLabelFigure.horizontalSpan = 1;
		constraintFFigureTopicLabelFigure.verticalSpan = 1;
		constraintFFigureTopicLabelFigure.grabExcessHorizontalSpace = true;
		constraintFFigureTopicLabelFigure.grabExcessVerticalSpace = true;
		this.add(fFigureTopicLabelFigure, constraintFFigureTopicLabelFigure);

	}

	public WrappingLabel getFigureTopicLabelFigure() {
		return fFigureTopicLabelFigure;
	}

	public WrappingLabel getFigureQueueLabelFigure() {
		return fFigureTopicLabelFigure;
	}

	protected void fillShape(Graphics graphics) {
		graphics.fillOval(getOptimizedBounds().x + getOptimizedBounds().width * 4 / 5, 
						  getOptimizedBounds().y,
						  getOptimizedBounds().width / 5, 
						  getOptimizedBounds().height);
		//
		graphics.fillArc(getOptimizedBounds().x, 
						 getOptimizedBounds().y, 
						 getOptimizedBounds().width / 5,
						 getOptimizedBounds().height, 90, 180);
		//
		graphics.fillRectangle(getOptimizedBounds().x + getOptimizedBounds().width * 1 / 10, 
							   getOptimizedBounds().y,
							   getOptimizedBounds().width * 8 / 10, 
							   getOptimizedBounds().height);
	}

	protected void outlineShape(Graphics graphics) {
		graphics.drawOval(getOptimizedBounds().x + getOptimizedBounds().width * 4 / 5, 
						  getOptimizedBounds().y,
						  getOptimizedBounds().width / 5, 
						  getOptimizedBounds().height);
		//
		graphics.drawArc(getOptimizedBounds().x, 
						 getOptimizedBounds().y, 
						 getOptimizedBounds().width / 5,
						 getOptimizedBounds().height, 90, 180);
		//
		graphics.drawLine(getOptimizedBounds().x + getOptimizedBounds().width * 9 / 10, 
						  getOptimizedBounds().y,
						  getOptimizedBounds().x + getOptimizedBounds().width * 1 / 10, 
						  getOptimizedBounds().y);

		graphics.drawLine(getOptimizedBounds().x + getOptimizedBounds().width * 1 / 10,
						  getOptimizedBounds().y + getOptimizedBounds().height,
						  getOptimizedBounds().x + getOptimizedBounds().width * 9 / 10,
						  getOptimizedBounds().y + getOptimizedBounds().height);
	}

	private Rectangle getOptimizedBounds() {
		float lineInset = Math.max(1.0f, getLineWidthFloat()) / 2.0f;

		int inset1 = (int) Math.floor(lineInset);
		int inset2 = (int) Math.ceil(lineInset);

		Rectangle r = Rectangle.SINGLETON.setBounds(getBounds());
		r.x += inset1;
		r.y += inset1;
		r.width -= inset1 + inset2;
		r.height -= inset1 + inset2;
		return r;
	}
}
