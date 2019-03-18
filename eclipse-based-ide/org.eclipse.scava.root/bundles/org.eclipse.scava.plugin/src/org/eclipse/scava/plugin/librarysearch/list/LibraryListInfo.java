package org.eclipse.scava.plugin.librarysearch.list;

import org.eclipse.swt.graphics.Color;
import org.eclipse.wb.swt.SWTResourceManager;

import io.swagger.client.model.Artifact;

public class LibraryListInfo {
	public static final Color DEFAULT_BACKGROUND_COLOR = SWTResourceManager.getColor(248, 248, 255);
	public static final String DEFAULT_ACTION_LABEL = "";
	public static final Runnable DEFAULT_ACTION = () -> {
	};
	public static final String DEFAULT_COMMENT = "";
	public static final boolean DEFAULT_SHOW_COMPACT = false;

	private final Artifact library;
	private Color backgroundColor = DEFAULT_BACKGROUND_COLOR;
	private String comment = DEFAULT_COMMENT;
	private Runnable action = DEFAULT_ACTION;
	private String actionLabel = DEFAULT_ACTION_LABEL;
	private boolean showCompact = DEFAULT_SHOW_COMPACT;

	public LibraryListInfo(Artifact library) {
		super();
		this.library = library;
	}

	public Color getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Runnable getAction() {
		return action;
	}

	public void setAction(Runnable action) {
		this.action = action;
	}

	public String getActionLabel() {
		return actionLabel;
	}

	public void setActionLabel(String actionLabel) {
		this.actionLabel = actionLabel;
	}

	public Artifact getLibrary() {
		return library;
	}

	public boolean isShowCompact() {
		return showCompact;
	}

	public void setShowCompact(boolean showCompact) {
		this.showCompact = showCompact;
	}

}
