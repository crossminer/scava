package org.eclipse.scava.plugin.preferences;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.scava.plugin.Activator;
import org.eclipse.scava.plugin.usermonitoring.event.Event;
import org.eclipse.scava.plugin.usermonitoring.event.IEvent;
import org.eclipse.scava.plugin.usermonitoring.metric.basicmetrics.BasedOn;
import org.eclipse.scava.plugin.usermonitoring.metric.metrics.Metric;
import org.eclipse.scava.plugin.usermonitoring.metric.metrics.MetricProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseTrackAdapter;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.preferences.ScopedPreferenceStore;
import org.eclipse.wb.swt.SWTResourceManager;
import org.reflections.Reflections;

import com.google.gson.Gson;

public class UserActivityEvents extends PreferencePage implements IWorkbenchPreferencePage {
	private static final Color COLOR_DEFAULT = SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND);
	private static final Color COLOR_DEPENDENCY = SWTResourceManager
			.getColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT);

	public UserActivityEvents() {
	}

	private Map<Class<?>, Button> eventButtons = new HashMap<>();
	private Map<Metric, Button> metricButtons = new HashMap<>();
	private UserActivityDisablements disablements;
	private Gson gson = new Gson();

	@Override
	public Control createContents(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		container.setLayout(new GridLayout(2, false));

		Label lblCollectedEvents = new Label(container, SWT.NONE);
		lblCollectedEvents.setFont(SWTResourceManager.getFont("Segoe UI", 13, SWT.NORMAL));
		lblCollectedEvents.setText("Collected events");
		new Label(container, SWT.NONE);

		Label lblEventsToBe = new Label(container, SWT.NONE);
		lblEventsToBe.setFont(SWTResourceManager.getFont("Segoe UI", 8, SWT.NORMAL));
		lblEventsToBe.setText("Events to be collected");

		Label lblMetricsToBe = new Label(container, SWT.NONE);
		lblMetricsToBe.setFont(SWTResourceManager.getFont("Segoe UI", 8, SWT.NORMAL));
		lblMetricsToBe.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		lblMetricsToBe.setText("Metrics to be computed");

		Composite collectedEventsComposite = new Composite(container, SWT.NONE);
		GridLayout gl_collectedEventsComposite = new GridLayout(1, true);
		gl_collectedEventsComposite.verticalSpacing = 0;
		collectedEventsComposite.setLayout(gl_collectedEventsComposite);
		collectedEventsComposite.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 1));

		Reflections reflections = new Reflections("org.eclipse.scava.plugin");
		List<Class<? extends IEvent>> eventTypes = reflections.getSubTypesOf(Event.class).stream()
				.sorted((a, b) -> a.getSimpleName().compareTo(b.getSimpleName())).collect(Collectors.toList());

		for (Class<?> eventType : eventTypes) {
			Composite buttonContainer = new Composite(collectedEventsComposite, SWT.NONE);
			GridLayout gl_buttonContainer = new GridLayout(1, false);
			gl_buttonContainer.marginHeight = 2;
			buttonContainer.setLayout(gl_buttonContainer);
			buttonContainer.setBackgroundMode(SWT.INHERIT_FORCE);
			buttonContainer.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

			Button btnCheckButton = new Button(buttonContainer, SWT.CHECK);
			btnCheckButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
			btnCheckButton.setText(eventType.getSimpleName());

			eventButtons.put(eventType, btnCheckButton);

			btnCheckButton.addSelectionListener(new SelectionAdapter() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					if (btnCheckButton.getSelection()) {
						disablements.enable(eventType);
					} else {
						disablements.disable(eventType);
					}
					resolveDependenciesAndUnusages();
					updateEventButtons();
					updateMetricButtons();
				}

			});

			MouseTrackAdapter mouseTrackerListener = new MouseTrackAdapter() {
				@Override
				public void mouseEnter(MouseEvent e) {
					highlightMetricsBasedOnEvent(eventType, true);
				}

				@Override
				public void mouseExit(MouseEvent e) {
					highlightMetricsBasedOnEvent(eventType, false);
				}
			};
			buttonContainer.addMouseTrackListener(mouseTrackerListener);
			btnCheckButton.addMouseTrackListener(mouseTrackerListener);
		}

		Composite computedMetricsComposite = new Composite(container, SWT.NONE);
		GridLayout gl_computedMetricsComposite = new GridLayout(1, true);
		gl_computedMetricsComposite.verticalSpacing = 0;
		computedMetricsComposite.setLayout(gl_computedMetricsComposite);
		computedMetricsComposite.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, true, false, 1, 1));

		for (Metric metric : MetricProvider.getMetricList()) {
			Composite buttonContainer = new Composite(computedMetricsComposite, SWT.NONE);
			GridLayout gl_buttonContainer = new GridLayout(1, false);
			gl_buttonContainer.marginHeight = 2;
			buttonContainer.setLayout(gl_buttonContainer);
			buttonContainer.setBackgroundMode(SWT.INHERIT_FORCE);
			buttonContainer.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

			Button btnCheckButton = new Button(buttonContainer, SWT.CHECK);
			btnCheckButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
			btnCheckButton.setText(metric.getID());

			metricButtons.put(metric, btnCheckButton);

			btnCheckButton.addSelectionListener(new SelectionAdapter() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					if (btnCheckButton.getSelection()) {
						disablements.enable(metric);

						BasedOn basedOn = metric.getBasicMetric().getClass().getDeclaredAnnotation(BasedOn.class);
						if (basedOn != null) {
							for (Class<?> usedEvent : basedOn.value()) {
								disablements.enable(usedEvent);
							}
						}
					} else {
						disablements.disable(metric);
					}
					resolveDependenciesAndUnusages();
					updateMetricButtons();
					updateEventButtons();
				}

			});

			btnCheckButton.addListener(SWT.MouseEnter, e -> {
				highlightBaseEventsOfMetric(metric, true);
			});
			btnCheckButton.addListener(SWT.MouseExit, e -> {
				highlightBaseEventsOfMetric(metric, false);
			});
		}

		initializeValues();

		return container;
	}

	private void resolveDependenciesAndUnusages() {
		String lastState = "just to be sure it is going to run at least once";
		String currentState = gson.toJson(disablements);

		while (!lastState.equals(currentState)) {
			// resolve metric dependencies
			metricButtons.forEach((metric, button) -> {
				BasedOn basedOn = metric.getBasicMetric().getClass().getDeclaredAnnotation(BasedOn.class);
				if (basedOn != null
						&& Arrays.stream(basedOn.value()).anyMatch(baseEvent -> disablements.isDisabled(baseEvent))) {
					disablements.disable(metric);
				}
			});

			// resolve unused events
			Set<Class<?>> unusedEvents = new HashSet<>(eventButtons.keySet());
			metricButtons.forEach((metric, button) -> {
				if (disablements.isDisabled(metric)) {
					return;
				}

				BasedOn basedOn = metric.getBasicMetric().getClass().getDeclaredAnnotation(BasedOn.class);
				if (basedOn != null) {
					for (Class<?> usedEvent : basedOn.value()) {
						unusedEvents.remove(usedEvent);
					}
				}
			});

			for (Class<?> unusedEvent : unusedEvents) {
				disablements.disable(unusedEvent);
			}

			// save state to see if we resolved anything
			lastState = currentState;
			currentState = gson.toJson(disablements);
		}
	}

	private void updateEventButtons() {
		for (Entry<Class<?>, Button> entry : eventButtons.entrySet()) {
			Class<?> eventClass = entry.getKey();
			Button button = entry.getValue();

			button.setSelection(!disablements.isDisabled(eventClass));
			button.setEnabled(!disablements.isDisabled(eventClass));
		}
	}

	private void updateMetricButtons() {
		for (Entry<Metric, Button> entry : metricButtons.entrySet()) {
			Metric metric = entry.getKey();
			Button button = entry.getValue();

			button.setSelection(!disablements.isDisabled(metric));
		}
	}

	private void highlightBaseEventsOfMetric(Metric metric, boolean toggle) {
		BasedOn basedOn = metric.getBasicMetric().getClass().getDeclaredAnnotation(BasedOn.class);
		if (basedOn != null) {
			for (Class<?> eventClass : basedOn.value()) {
				Button button = eventButtons.get(eventClass);
				if (button != null) {
					Color color = toggle ? COLOR_DEPENDENCY : COLOR_DEFAULT;
					button.getParent().setBackground(color);
				}
			}
		}
	}

	private void highlightMetricsBasedOnEvent(Class<?> eventType, boolean toggle) {
		for (Entry<Metric, Button> entry : metricButtons.entrySet()) {
			Metric metric = entry.getKey();
			Button button = entry.getValue();

			BasedOn basedOn = metric.getBasicMetric().getClass().getDeclaredAnnotation(BasedOn.class);
			if (basedOn != null) {
				boolean dependencyDisabled = Arrays.stream(basedOn.value()).anyMatch(b -> eventType.equals(b));
				if (dependencyDisabled) {
					Color color = toggle ? COLOR_DEPENDENCY : COLOR_DEFAULT;
					button.getParent().setBackground(color);
				}
			}
		}

	}

	public void init(IWorkbench workbench) {
		setPreferenceStore(new ScopedPreferenceStore(InstanceScope.INSTANCE, Activator.PLUGIN_ID));
		setDescription("User activity event collection settings");
	}

	@Override
	protected void performDefaults() {
		super.performDefaults();
		initializeDefaults();
	}

	@Override
	public boolean performOk() {
		storeValues();
		return super.performOk();
	}

	private void initializeValues() {
		IPreferenceStore store = getPreferenceStore();
		String json = store.getString(Preferences.USERMONITORING_DISABLEMENTS);
		disablements = gson.fromJson(json, UserActivityDisablements.class);
		resolveDependenciesAndUnusages();
		updateEventButtons();
		updateMetricButtons();
	}

	private void storeValues() {
		IPreferenceStore store = getPreferenceStore();
		store.setValue(Preferences.USERMONITORING_DISABLEMENTS, gson.toJson(disablements));
	}

	private void initializeDefaults() {
		IPreferenceStore store = getPreferenceStore();
		String json = store.getDefaultString(Preferences.USERMONITORING_DISABLEMENTS);
		disablements = gson.fromJson(json, UserActivityDisablements.class);
		resolveDependenciesAndUnusages();
		updateEventButtons();
		updateMetricButtons();
	}
}
