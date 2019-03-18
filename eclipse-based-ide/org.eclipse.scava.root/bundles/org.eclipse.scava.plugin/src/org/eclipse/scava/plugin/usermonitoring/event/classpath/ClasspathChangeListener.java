package org.eclipse.scava.plugin.usermonitoring.event.classpath;

import org.eclipse.jdt.core.ElementChangedEvent;
import org.eclipse.jdt.core.IElementChangedListener;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaElementDelta;
import org.eclipse.scava.plugin.Activator;

public class ClasspathChangeListener implements IElementChangedListener {

	@Override
	public void elementChanged(ElementChangedEvent event) {

		visit(event.getDelta());
		
		
	}

	
	private void visit(IJavaElementDelta delta) {
		IJavaElement el = delta.getElement();
		switch (el.getElementType()) {
		case IJavaElement.JAVA_MODEL:
			visitChildren(delta);
			break;
		case IJavaElement.JAVA_PROJECT:
			if (isClasspathChanged(delta.getFlags())) {
				System.out.println();

				IJavaElementDelta[] affectedChildren = delta.getAffectedChildren();

				for (IJavaElementDelta iJavaElementDelta : affectedChildren) {

					if (iJavaElementDelta.getKind() == IJavaElementDelta.CHANGED
							|| iJavaElementDelta.getKind() == IJavaElementDelta.ADDED) {

						int flags = iJavaElementDelta.getFlags();
						if (isRemove(flags)) {
							Activator.getDefault().getMainController().getEventBus().post(new ClasspathChangeEvent(
									iJavaElementDelta.getElement().getElementName(), ClasspathChangeEventType.DELETED));
							
						}

						if (isAdd(flags)) {
							Activator.getDefault().getMainController().getEventBus().post(new ClasspathChangeEvent(
									iJavaElementDelta.getElement().getElementName(), ClasspathChangeEventType.ADDED));
						}

					}

				}
			}
			break;
		default:
			break;
		}
	}

	private boolean isClasspathChanged(int flags) {
		return 0 != (flags & (IJavaElementDelta.F_CLASSPATH_CHANGED | IJavaElementDelta.F_RESOLVED_CLASSPATH_CHANGED));

	}

	private boolean isRemove(int flags) {
		return 0 != (flags & IJavaElementDelta.F_REMOVED_FROM_CLASSPATH);

	}

	private boolean isAdd(int flags) {
		return 0 != (flags & (IJavaElementDelta.F_ADDED_TO_CLASSPATH));

	}

	public void visitChildren(IJavaElementDelta delta) {
		for (IJavaElementDelta c : delta.getAffectedChildren()) {
			visit(c);
		}
	}

}
