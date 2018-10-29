/*
 * 
 */
package crossflow.diagram.part;

import java.util.ArrayList;
import java.util.Collections;

import org.eclipse.gef.palette.PaletteContainer;
import org.eclipse.gef.palette.PaletteDrawer;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.palette.ToolEntry;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.tooling.runtime.part.DefaultLinkToolEntry;
import org.eclipse.gmf.tooling.runtime.part.DefaultNodeToolEntry;

import crossflow.diagram.providers.CrossflowElementTypes;

/**
 * @generated
 */
public class CrossflowPaletteFactory {

	/**
	 * @generated
	 */
	public void fillPalette(PaletteRoot paletteRoot) {
		paletteRoot.add(createObjects1Group());
		paletteRoot.add(createConnections2Group());
	}

	/**
	 * Creates "Objects" palette tool group
	 * @generated
	 */
	private PaletteContainer createObjects1Group() {
		PaletteDrawer paletteContainer = new PaletteDrawer(Messages.Objects1Group_title);
		paletteContainer.setId("createObjects1Group"); //$NON-NLS-1$
		paletteContainer.add(createConfiguration1CreationTool());
		paletteContainer.add(createCsvSink2CreationTool());
		paletteContainer.add(createCsvSource3CreationTool());
		paletteContainer.add(createField4CreationTool());
		paletteContainer.add(createQueue5CreationTool());
		paletteContainer.add(createSink6CreationTool());
		paletteContainer.add(createSource7CreationTool());
		paletteContainer.add(createTask8CreationTool());
		paletteContainer.add(createTopic9CreationTool());
		paletteContainer.add(createType10CreationTool());
		return paletteContainer;
	}

	/**
	 * Creates "Connections" palette tool group
	 * @generated
	 */
	private PaletteContainer createConnections2Group() {
		PaletteDrawer paletteContainer = new PaletteDrawer(Messages.Connections2Group_title);
		paletteContainer.setId("createConnections2Group"); //$NON-NLS-1$
		paletteContainer.add(createExtending1CreationTool());
		paletteContainer.add(createInput2CreationTool());
		paletteContainer.add(createOutput3CreationTool());
		paletteContainer.add(createType4CreationTool());
		return paletteContainer;
	}

	/**
	 * @generated
	 */
	private ToolEntry createConfiguration1CreationTool() {
		DefaultNodeToolEntry entry = new DefaultNodeToolEntry(Messages.Configuration1CreationTool_title,
				Messages.Configuration1CreationTool_desc,
				Collections.singletonList(CrossflowElementTypes.Configuration_2005));
		entry.setId("createConfiguration1CreationTool"); //$NON-NLS-1$
		entry.setSmallIcon(CrossflowElementTypes.getImageDescriptor(CrossflowElementTypes.Configuration_2005));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	* @generated
	*/
	private ToolEntry createCsvSink2CreationTool() {
		DefaultNodeToolEntry entry = new DefaultNodeToolEntry(Messages.CsvSink2CreationTool_title,
				Messages.CsvSink2CreationTool_desc, Collections.singletonList(CrossflowElementTypes.CsvSink_2010));
		entry.setId("createCsvSink2CreationTool"); //$NON-NLS-1$
		entry.setSmallIcon(CrossflowElementTypes.getImageDescriptor(CrossflowElementTypes.CsvSink_2010));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	* @generated
	*/
	private ToolEntry createCsvSource3CreationTool() {
		DefaultNodeToolEntry entry = new DefaultNodeToolEntry(Messages.CsvSource3CreationTool_title,
				Messages.CsvSource3CreationTool_desc, Collections.singletonList(CrossflowElementTypes.CsvSource_2009));
		entry.setId("createCsvSource3CreationTool"); //$NON-NLS-1$
		entry.setSmallIcon(CrossflowElementTypes.getImageDescriptor(CrossflowElementTypes.CsvSource_2009));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	* @generated
	*/
	private ToolEntry createField4CreationTool() {
		ArrayList<IElementType> types = new ArrayList<IElementType>(2);
		types.add(CrossflowElementTypes.Field_3001);
		types.add(CrossflowElementTypes.Field_2008);
		DefaultNodeToolEntry entry = new DefaultNodeToolEntry(Messages.Field4CreationTool_title,
				Messages.Field4CreationTool_desc, types);
		entry.setId("createField4CreationTool"); //$NON-NLS-1$
		entry.setSmallIcon(CrossflowElementTypes.getImageDescriptor(CrossflowElementTypes.Field_3001));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	* @generated
	*/
	private ToolEntry createQueue5CreationTool() {
		DefaultNodeToolEntry entry = new DefaultNodeToolEntry(Messages.Queue5CreationTool_title,
				Messages.Queue5CreationTool_desc, Collections.singletonList(CrossflowElementTypes.Queue_2002));
		entry.setId("createQueue5CreationTool"); //$NON-NLS-1$
		entry.setSmallIcon(CrossflowElementTypes.getImageDescriptor(CrossflowElementTypes.Queue_2002));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	* @generated
	*/
	private ToolEntry createSink6CreationTool() {
		DefaultNodeToolEntry entry = new DefaultNodeToolEntry(Messages.Sink6CreationTool_title,
				Messages.Sink6CreationTool_desc, Collections.singletonList(CrossflowElementTypes.Sink_2004));
		entry.setId("createSink6CreationTool"); //$NON-NLS-1$
		entry.setSmallIcon(CrossflowElementTypes.getImageDescriptor(CrossflowElementTypes.Sink_2004));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	* @generated
	*/
	private ToolEntry createSource7CreationTool() {
		DefaultNodeToolEntry entry = new DefaultNodeToolEntry(Messages.Source7CreationTool_title,
				Messages.Source7CreationTool_desc, Collections.singletonList(CrossflowElementTypes.Source_2003));
		entry.setId("createSource7CreationTool"); //$NON-NLS-1$
		entry.setSmallIcon(CrossflowElementTypes.getImageDescriptor(CrossflowElementTypes.Source_2003));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	* @generated
	*/
	private ToolEntry createTask8CreationTool() {
		DefaultNodeToolEntry entry = new DefaultNodeToolEntry(Messages.Task8CreationTool_title,
				Messages.Task8CreationTool_desc, Collections.singletonList(CrossflowElementTypes.Task_2006));
		entry.setId("createTask8CreationTool"); //$NON-NLS-1$
		entry.setSmallIcon(CrossflowElementTypes.getImageDescriptor(CrossflowElementTypes.Task_2006));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	* @generated
	*/
	private ToolEntry createTopic9CreationTool() {
		DefaultNodeToolEntry entry = new DefaultNodeToolEntry(Messages.Topic9CreationTool_title,
				Messages.Topic9CreationTool_desc, Collections.singletonList(CrossflowElementTypes.Topic_2001));
		entry.setId("createTopic9CreationTool"); //$NON-NLS-1$
		entry.setSmallIcon(CrossflowElementTypes.getImageDescriptor(CrossflowElementTypes.Topic_2001));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	* @generated
	*/
	private ToolEntry createType10CreationTool() {
		DefaultNodeToolEntry entry = new DefaultNodeToolEntry(Messages.Type10CreationTool_title,
				Messages.Type10CreationTool_desc, Collections.singletonList(CrossflowElementTypes.Type_2007));
		entry.setId("createType10CreationTool"); //$NON-NLS-1$
		entry.setSmallIcon(CrossflowElementTypes.getImageDescriptor(CrossflowElementTypes.Type_2007));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	 * @generated
	 */
	private ToolEntry createExtending1CreationTool() {
		DefaultLinkToolEntry entry = new DefaultLinkToolEntry(Messages.Extending1CreationTool_title,
				Messages.Extending1CreationTool_desc,
				Collections.singletonList(CrossflowElementTypes.TypeExtending_4004));
		entry.setId("createExtending1CreationTool"); //$NON-NLS-1$
		entry.setSmallIcon(CrossflowElementTypes.getImageDescriptor(CrossflowElementTypes.TypeExtending_4004));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	* @generated
	*/
	private ToolEntry createInput2CreationTool() {
		DefaultLinkToolEntry entry = new DefaultLinkToolEntry(Messages.Input2CreationTool_title,
				Messages.Input2CreationTool_desc, Collections.singletonList(CrossflowElementTypes.TaskInput_4002));
		entry.setId("createInput2CreationTool"); //$NON-NLS-1$
		entry.setSmallIcon(CrossflowElementTypes.getImageDescriptor(CrossflowElementTypes.TaskInput_4002));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	* @generated
	*/
	private ToolEntry createOutput3CreationTool() {
		DefaultLinkToolEntry entry = new DefaultLinkToolEntry(Messages.Output3CreationTool_title,
				Messages.Output3CreationTool_desc, Collections.singletonList(CrossflowElementTypes.TaskOutput_4003));
		entry.setId("createOutput3CreationTool"); //$NON-NLS-1$
		entry.setSmallIcon(CrossflowElementTypes.getImageDescriptor(CrossflowElementTypes.TaskOutput_4003));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	* @generated
	*/
	private ToolEntry createType4CreationTool() {
		DefaultLinkToolEntry entry = new DefaultLinkToolEntry(Messages.Type4CreationTool_title,
				Messages.Type4CreationTool_desc, Collections.singletonList(CrossflowElementTypes.StreamType_4001));
		entry.setId("createType4CreationTool"); //$NON-NLS-1$
		entry.setSmallIcon(CrossflowElementTypes.getImageDescriptor(CrossflowElementTypes.StreamType_4001));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

}
